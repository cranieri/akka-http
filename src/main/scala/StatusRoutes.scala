
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future


trait StatusRoutes {

  // we leave these abstract, since they will be provided by the App
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  case class Status(message: String)
  implicit val statusFormat = jsonFormat1(Status)
  implicit val executionContext = system.dispatcher

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
