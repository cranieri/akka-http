package test
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


trait StatusRoutes {

  // we leave these abstract, since they will be provided by the App

  case class Status(message: String)
  implicit val statusFormat = jsonFormat1(Status)



  val statusRoutes: Route =
    get {
      path("status") {
        val statusFuture: Future[Status] = Future { Status("ok") }

        onComplete(statusFuture) { status =>
          complete(status)
        }
      }
    }
}
