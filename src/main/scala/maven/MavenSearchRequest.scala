package maven

import java.time.{Duration, ZonedDateTime}

import org.http4s.Uri
import org.http4s.implicits._

case class MavenSearchRequest(rowStart: Int, rowCount: Int, updatedWithin: Duration) {
  private def minMillis = ZonedDateTime.now()
    .minus(updatedWithin)
    .toInstant
    .toEpochMilli

  def uri: Uri = uri"https://search.maven.org/solrsearch/select"
    .withQueryParam("q", s"timestamp:[$minMillis TO *]")
    .withQueryParam("rows", rowCount)
    .withQueryParam("start", rowStart)
    .withQueryParam("sort", "desc,timestamp")
    .withQueryParam("wt", "json")
}
