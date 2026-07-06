package reproducer

import org.testcontainers.localstack.LocalStackContainer
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.regions.Region


object LocalstackContainer {
  def start(): LocalstackConfig =
    val container =
      new LocalStackContainer("localstack/localstack")
        .withServices("rds")
        .withExposedPorts(4510, 4566, 5432)
        .withEnv("LOCALSTACK_AUTH_TOKEN", System.getenv("LOCALSTACK_AUTH_TOKEN"))

    container.start()

    LocalstackConfig(
      endpoint = container.getEndpoint,
      region = Region.of(container.getRegion),
      credentialsProvider = StaticCredentialsProvider.create(
        AwsBasicCredentials.create(container.getAccessKey, container.getSecretKey)
      ),
      rdsPort = container.getMappedPort(4510)
    )
  end start
}
