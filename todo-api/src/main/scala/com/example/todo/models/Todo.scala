package com.example.todo.models

import java.time.LocalDateTime
import slick.jdbc.PostgresProfile.api._

case class Todo(
  id: Option[Long] = None,
  title: String,
  description: Option[String] = None,
  isCompleted: Boolean = false,
  createdAt: LocalDateTime = LocalDateTime.now(),
  updatedAt: LocalDateTime = LocalDateTime.now()
)

class Todos(tag: Tag) extends Table[Todo](tag, "todos") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def description = column[Option[String]]("description")
  def isCompleted = column[Boolean]("is_completed")
  def createdAt = column[LocalDateTime]("created_at")
  def updatedAt = column[LocalDateTime]("updated_at")
  
  def * = (id.?, title, description, isCompleted, createdAt, updatedAt) <> (Todo.tupled, Todo.unapply)
}