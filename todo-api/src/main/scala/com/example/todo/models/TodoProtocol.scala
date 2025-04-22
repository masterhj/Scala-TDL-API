package com.example.todo.models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import java.time.LocalDateTime
import spray.json._

trait TodoProtocol extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val localDateTimeFormat = new JsonFormat[LocalDateTime] {
    def write(dt: LocalDateTime) = JsString(dt.toString)
    def read(value: JsValue) = value match {
      case JsString(dt) => LocalDateTime.parse(dt)
      case _ => throw DeserializationException("Expected LocalDateTime as JsString")
    }
  }
  
  implicit val todoFormat = jsonFormat6(Todo)
}