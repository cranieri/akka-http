import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import akka.http.scaladsl.model.StatusCodes

class PaymentSubmitterSpec extends WordSpec with Matchers {

  "Pay" should {
    "return status (GET /status)" in {
      Get("/status") ~> statusRoutes ~> check {
        status shouldEqual StatusCodes.OK
        entityAs[String] should ===("""{"message":"ok"}""")
      }
    }
  }
}

