package `in`.porter.mcp.server.service

import `in`.porter.mcp.server.usecases.getLatLon
import io.ktor.client.*
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import io.modelcontextprotocol.kotlin.sdk.server.Server
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.putJsonObject

fun Server.addGetLatLonTool(httpClient: HttpClient) {
  this.addTool(
    name = "get_lat_lon",
    description = """
      Get the Latitude and Longitude for a given address.
    """.trimIndent(),
    inputSchema = Tool.Input(
      properties = buildJsonObject {
        putJsonObject("address") {
          put("type", JsonPrimitive("string"))
          put("description", JsonPrimitive("The address to get the latitude and longitude for."))
        }
      },
      required = listOf("address")
    )
  ) { request ->
    val address = request.arguments["address"]?.jsonPrimitive?.content
    if (address == null) {
      return@addTool CallToolResult(
        content = listOf(TextContent("The 'address' parameter is required."))
      )
    }

    val latLons = httpClient.getLatLon(address)

    CallToolResult(content = latLons.map { TextContent(it) })
  }
}
