import Dependencies.*

lazy val root =
  project
    .in(file("."))
    .settings(
      name := "aws-advanced-jdbc-wrapper-1787-reproducer",
      scalaVersion := "3.8.3",
      libraryDependencies ++=
        aws.all ++
        database.all ++
        flyway.all ++
        logging.all ++
        netty.all ++
        containers.all,
      run / fork := true
    )
