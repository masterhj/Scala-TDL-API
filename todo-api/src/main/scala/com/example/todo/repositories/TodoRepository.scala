package com.example.todo.repositories

import com.example.todo.models.{Todo, Todos}
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}

class TodoRepository(db: Database)(implicit ec: ExecutionContext) {
  val todos = TableQuery[Todos]

  def getAll: Future[Seq[Todo]] = db.run(todos.result)
  
  def getById(id: Long): Future[Option[Todo]] = 
    db.run(todos.filter(_.id === id).result.headOption)
  
  def create(todo: Todo): Future[Todo] = 
    db.run((todos returning todos.map(_.id) into ((todo, id) => todo.copy(id = Some(id)))) += todo)
  
  def update(id: Long, todo: Todo): Future[Int] = {
    val todoToUpdate = todo.copy(id = Some(id), updatedAt = java.time.LocalDateTime.now())
    db.run(todos.filter(_.id === id).update(todoToUpdate))
  }
  
  def delete(id: Long): Future[Int] = 
    db.run(todos.filter(_.id === id).delete)
  
  def complete(id: Long): Future[Int] = 
    db.run(
      todos.filter(_.id === id)
        .map(t => (t.isCompleted, t.updatedAt))
        .update((true, java.time.LocalDateTime.now())))
}