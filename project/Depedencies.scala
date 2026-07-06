import sbt.*

object Dependencies {
  object aws {
    private val awsSdkVersion = "2.44.4"
    val auth                  = "software.amazon.awssdk" % "auth"       % awsSdkVersion
    val awsCore               = "software.amazon.awssdk" % "aws-core"       % awsSdkVersion
    val rds                   = "software.amazon.awssdk" % "rds"       % awsSdkVersion
    val regions               = "software.amazon.awssdk" % "regions"       % awsSdkVersion
    val sdkCore               = "software.amazon.awssdk" % "sdk-core"       % awsSdkVersion
    val sts                   = "software.amazon.awssdk" % "sts"       % awsSdkVersion
    val utils                 = "software.amazon.awssdk" % "utils"          % awsSdkVersion


    val all: Seq[ModuleID] = Seq(auth, awsCore, rds, regions, sdkCore, sts, utils)
  }

  object database {
    private val postgresVersion = "42.7.12"
    val postgresDriver          = "org.postgresql" % "postgresql" % postgresVersion

    private val awsAdvancedJdbcVersion = "4.1.0"
    val awsAdvancedJdbcDriver          = "software.amazon.jdbc" % "aws-advanced-jdbc-wrapper" % awsAdvancedJdbcVersion

    val all: Seq[ModuleID] = Seq(postgresDriver, awsAdvancedJdbcDriver)
  }

  object flyway {
    private val flywayVersion = "12.9.0"
    val core                  = "org.flywaydb" % "flyway-core"                % flywayVersion
    val postgres              = "org.flywaydb" % "flyway-database-postgresql" % flywayVersion

    val all: Seq[ModuleID] = Seq(core, postgres)
  }

  object logging {
    private val logbackVersion = "1.5.32"
    val logback     = "ch.qos.logback" % "logback-classic" % logbackVersion

    val all: Seq[ModuleID] = Seq(logback)
  }

  object netty {
    private val nettyVersion = "2.0.80.Final"
    val nettyTCNative        = "io.netty" % "netty-tcnative" % nettyVersion

    val all: Seq[ModuleID] = Seq(nettyTCNative)
  }

  object containers {
    private val testContainersVersions = "2.0.5"
    val core                           = "org.testcontainers" % "testcontainers" % testContainersVersions
    val localstack                     = "org.testcontainers" % "testcontainers-localstack" % testContainersVersions

    val all = Seq(core, localstack)
  }
}
