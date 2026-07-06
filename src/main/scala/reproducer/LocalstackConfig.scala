package reproducer

import java.net.URI
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider

final case class LocalstackConfig(
  endpoint: URI,
  region: Region,
  credentialsProvider: AwsCredentialsProvider,
  rdsPort: Int
)
