package reproducer

import javax.sql.DataSource
import java.util.UUID
import org.slf4j.Logger

object Test {
  private val insertStatement = "INSERT INTO reproducer VALUES (?, ?)"
  private val selectStatement = "SELECT details FROM reproducer WHERE id = ? LIMIT 1"

  def run(logger: Logger, dataSource: DataSource, details: String): Unit =
    val uuid = UUID.randomUUID()
    logger.info(s"TEST: ${uuid}")

    val connection = dataSource.getConnection()

    val preparedInsert = connection.prepareStatement(insertStatement)
    preparedInsert.setObject(1, uuid)
    preparedInsert.setString(2, details)
    val rowsInserted = preparedInsert.executeUpdate()

    assert(rowsInserted == 1, "INSERT FAILED")
    logger.info("INSERT WORKED")

    val preparedSelect = connection.prepareStatement(selectStatement)
    preparedSelect.setObject(1, uuid)
    val selectResult = preparedSelect.executeQuery()

    assert(selectResult.next(), "SELECT RETURNED NO ROWS")
    assert(selectResult.getString("details") == details, "SELECT RETURNED DIFFERENT DATA")
    logger.info("SELECT WORKED")
  end run
}
