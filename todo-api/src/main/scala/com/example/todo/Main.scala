package com.example.todo.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.example.todo.models.{Todo, TodoProtocol}
import com.example.todo.repositories.TodoRepository
import scala.concurrent.ExecutionContext

class TodoRoutes(repository: TodoRepository)(implicit ec: ExecutionContext) extends TodoProtocol {
  val routes: Route = pathPrefix("todos") {
    concat(
      pathEnd {
        concat(
          get {
            complete(repository.getAll)
          },
          post {
            entity(as[Todo]) { todo =>
              complete(repository.create(todo))
            }
          }
        )
      },
      path(LongNumber) { id =>
        concat(
          get {
            rejectEmptyResponse {
              complete(repository.getById(id))
            }
          },
          put {
            entity(as[Todo]) { todo =>
              complete(repository.update(id, todo))
            }
          },
          delete {
            complete(repository.delete(id))
          },
          path("complete") {
            post {
              complete(repository.complete(id))
            }
          }
        )
      }
    )
  }
}