package `in`.porter.mcp.server.usecases

import `in`.porter.mcp.server.app.ApplicationConfigs.LocationIq
import `in`.porter.mcp.server.entities.LatLon
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

suspend fun HttpClient.getLatLon(address: String): List<String> {
  val apiKey = LocationIq.API_KEY
  val url = "${LocationIq.BASE_URL}/v1/search?key=$apiKey&q=$address&format=json"
  val response = this.get(url).body<List<LatLon>>()
  return response.map { latLng ->
    """
      Latitude: ${latLng.lat}
      Longitude: ${latLng.lon}
    """.trimIndent()
  }
}
