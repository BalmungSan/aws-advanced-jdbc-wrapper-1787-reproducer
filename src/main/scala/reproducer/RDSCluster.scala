package reproducer

import software.amazon.awssdk.services.rds.RdsClient
import org.slf4j.Logger

object RDSCluster {
  def make(logger: Logger, config: LocalstackConfig): PostgresConfig =
    val clusterId = "reproducer"
    val dbName = "local-aurora-db"
    val username = "local-aurora-user"
    val password = "local-aurora-user-password"

    val client =
      RdsClient
        .builder
        .endpointOverride(config.endpoint)
        .region(config.region)
        .credentialsProvider(config.credentialsProvider)
        .build()

    val response = client.createDBCluster { request =>
      request
        .dbClusterIdentifier(clusterId)
        .engine("aurora-postgresql")
        .engineVersion("16.14")
        .engineMode("serverless")
        .databaseName(dbName)
        .masterUsername(username)
        .masterUserPassword(password)
        .port(4510)
    }
    logger.info(s"CREATE CLUSTER RESPONSE: ${response}")

    var clusterReady = false
    while (!clusterReady) {
      val clusterStatus = client.describeDBClusters { request =>
        request.dbClusterIdentifier(clusterId)
      }.dbClusters.get(0).status

      logger.info(s"CLUSTER STATUS: ${clusterStatus}")

      if (clusterStatus == "available") then
        clusterReady = true
      else
        Thread.sleep(2 * 1000)
    }

    Thread.sleep(10 * 1000)

    PostgresConfig(
      host = response.dbCluster.endpoint,
      port = config.rdsPort,
      dbName,
      username,
      password
    )
  end make
}
