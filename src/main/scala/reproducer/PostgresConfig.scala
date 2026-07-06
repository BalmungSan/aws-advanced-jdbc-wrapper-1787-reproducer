package reproducer

final case class PostgresConfig(
    host: String,
    port: Int,
    dbName: String,
    username: String,
    password: String
)
