import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import spray.json.DefaultJsonProtocol.jsonFormat1
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.stream.ActorMaterializer
import akka.testkit.TestKit
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future

//#set-up
class StatusRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest{

  // we leave these abstract, since they will be provided by the App
  case class Status(message: String)
  implicit val statusFormat = jsonFormat1(Status)



  val smallRoute =
    get {
      path("status") {
        val statusFuture: Future[Status] = Future { Status("ok") }

        onComplete(statusFuture) { status =>
          complete(status)
        }
      }
    }


  //#actual-test
  "StatusRoutes" should {
    "return status (GET /status)" in {
      // note that there's no need for the host part in the uri:
      Get("/status") ~> smallRoute ~> check {
        responseAs[String] shouldEqual "Captain on the bridge!"
      }
    }
    //#actual-test


  }
}

