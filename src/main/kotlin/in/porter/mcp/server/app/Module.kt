package `in`.porter.mcp.server.app

import `in`.porter.mcp.server.service.addGetLatLonTool
import `in`.porter.mcp.server.service.addGetQuoteTool
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.streams.asInput
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.server.StdioServerTransport
import kotlinx.io.asSink
import kotlinx.io.buffered
import kotlinx.serialization.json.Json
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

fun createHttpClient() = HttpClient(CIO) {
  defaultRequest {
    contentType(ContentType.Application.Json)
  }
  engine {
    https {
      trustManager = object : X509TrustManager {
        override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}

        override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}

        override fun getAcceptedIssuers(): Array<X509Certificate>? = null
      }
    }
  }
  install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
}

fun createMCPServer(): Server {
  val httpClient = createHttpClient()
  val server = Server(
    Implementation(
      name = "Porter MCP",
      version = "1.0.0"
    ),
    ServerOptions(
      capabilities = ServerCapabilities(tools = ServerCapabilities.Tools(listChanged = true))
    )
  )

  server.addGetLatLonTool(httpClient)
  server.addGetQuoteTool(httpClient)

  return server
}

fun createTransport() = StdioServerTransport(
  System.`in`.asInput(),
  System.out.asSink().buffered()
)
