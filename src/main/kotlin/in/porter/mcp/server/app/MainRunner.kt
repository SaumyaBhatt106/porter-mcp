package `in`.porter.mcp.server.app

import io.ktor.server.engine.*
import io.modelcontextprotocol.kotlin.sdk.server.mcp
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch

fun runUsingSSE(port: Int): Unit = runBlocking {
  println("Starting sse server on http://localhost:$port/sse")

  embeddedServer(io.ktor.server.cio.CIO, host = "0.0.0.0", port = port) {
    mcp {
      return@mcp createMCPServer()
    }
  }.start(wait = true)
}

fun runUsingStdIO() = runBlocking {
  val server = createMCPServer()
  val transport = createTransport()
  val latch = CountDownLatch(1)

  Runtime.getRuntime().addShutdownHook(Thread {
    runBlocking { server.close() }
    latch.countDown()
  })

  println("Starting MCP server on STD I/O")
  server.connect(transport)
  latch.await()
}

fun main(args: Array<String>) {
  val command = args.firstOrNull() ?: "--stdio"
  val port = args.getOrNull(1)?.toIntOrNull() ?: 3001
  when (command) {
    "--stdio" -> runUsingStdIO()
    "--sse-server" -> runUsingSSE(port)
    else -> {
      System.err.println("Unknown command: $command")
    }
  }
}
