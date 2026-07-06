package reproducer

import java.util.Properties
import javax.sql.DataSource
import org.postgresql.ds.PGSimpleDataSource
import org.slf4j.Logger
import software.amazon.jdbc.Driver
import software.amazon.jdbc.ds.AwsWrapperDataSource

object PostgresDataSource {
  def make(logger: Logger, config: PostgresConfig, schema: String): DataSource =
    // Use the AWS advanced-jdbc-wrapper DataSource.
    val dataSource = new AwsWrapperDataSource()
    val targetDataSourceProps = new Properties()
    dataSource.setTargetDataSourceProperties(targetDataSourceProps)
    targetDataSourceProps.setProperty("wrapperPlugins", "initialConnection,auroraConnectionTracker,failover2,efm2")

    // Specify PostgreSQL as the underlying driver.
    dataSource.setTargetDataSourceClassName(classOf[PGSimpleDataSource].getCanonicalName)
    dataSource.setJdbcProtocol("jdbc:postgresql:")
    targetDataSourceProps.setProperty("wrapperDialect", "rds-pg")

    // Configure connection details.
    dataSource.setServerName(config.host)
    dataSource.setServerPort(config.port.toString)
    dataSource.setDatabase(config.dbName)
    dataSource.setUser(config.username)
    dataSource.setPassword(config.password)

    // Configure the schema.
    targetDataSourceProps.setProperty("currentSchema", schema)
    Driver.setConnectionInitFunc { (connection, _, _, props) =>
      logger.info(s"PROPS SCHEMA: ${props.getProperty("currentSchema")}")
      logger.info(s"CONNECTION SCHEMA: ${connection.getSchema()}")
    }

    // Return the configured DataSource.
    dataSource
  end make
}
