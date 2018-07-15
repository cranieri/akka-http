import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import akka.http.scaladsl.model.StatusCodes

class StatusRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest with StatusRoutes {

  "StatusRoutes" should {
    "return status (GET /status)" in {
      Get("/status") ~> statusRoutes ~> check {
        status shouldEqual StatusCodes.OK
        entityAs[String] should ===("""{"message":"ok"}""")
      }
    }
  }
}

