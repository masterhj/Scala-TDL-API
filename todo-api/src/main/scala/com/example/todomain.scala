package com.example.todo

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import com.example.todo.repositories.TodoRepository
import com.example.todo.routes.TodoRoutes
import com.typesafe.config.ConfigFactory
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Main {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "TodoApi")
    implicit val executionContext: ExecutionContext = system.executionContext
    
    // Initialize database
    val db = Database.forConfig("db", config)
    val todoRepository = new TodoRepository(db)
    val todoRoutes = new TodoRoutes(todoRepository)
    
    // Start HTTP server
    val host = "0.0.0.0"
    val port = 8080
    
    val serverBinding = Http().newServerAt(host, port).bind(todoRoutes.routes)
    
    serverBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info(s"Server online at http://${address.getHostString}:${address.getPort}")
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }
    
    // Add shutdown hook
    sys.addShutdownHook {
      serverBinding
        .flatMap(_.unbind())
        .onComplete(_ => system.terminate())
    }
  }
}