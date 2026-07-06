package reproducer

import org.slf4j.LoggerFactory

object Main {
  def main(args: Array[String]): Unit =
    val logger = LoggerFactory.getLogger("reproducer")

    val schema = "test_schema"
    logger.info(s"START: ${schema}")

    val localstackConfig = LocalstackContainer.start()
    logger.info(s"LOCALSTACK CONFIG: ${localstackConfig}")

    val postgresConfig = RDSCluster.make(logger, localstackConfig)
    logger.info(s"POSTGRES CONFIG: ${postgresConfig}")

    val dataSource = PostgresDataSource.make(postgresConfig, schema)
    logger.info(s"POSTGRES DATASOURCE: ${dataSource}")

    val migrationResult = Flyway.migrate(dataSource, schema)
    logger.info(s"FLYWAY MIGRATION RESULT: ${migrationResult.schemaName} - ${migrationResult.success}")

    Test.run(logger, dataSource, details = "reproducer")
  end main
}
