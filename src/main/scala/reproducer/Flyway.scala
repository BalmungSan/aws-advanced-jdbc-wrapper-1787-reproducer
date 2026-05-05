package reproducer

import javax.sql.DataSource
import org.flywaydb.core.api.output.MigrateResult

object Flyway {
  def migrate(dataSource: DataSource, schema: String): MigrateResult =
    org.flywaydb.core.Flyway
      .configure
      .dataSource(dataSource)
      .schemas(schema)
      .createSchemas(true)
      .executeInTransaction(true)
      .cleanDisabled(false)
      .load
      .migrate()
  end migrate
}
