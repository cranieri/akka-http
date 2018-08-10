import org.scalatest.{Matchers, WordSpec}
import test._
import org.scalatest.Inside

import scala.util.Success


class PaymentSubmissionSanitiser extends WordSpec with Matchers with Inside {

  "sanitise" should {
    "returns a validated payment if the payment is valid" in {
      val validPaymentSubmission = ValidatedPaymentSubmission(900, "ref", Valid)
      val payment = PaymentSubmissionSanitiser.sanitise(validPaymentSubmission)


      inside(payment) { case Success(SanitisedPaymentSubmission(amount, ref, status)) =>
        amount should be (900)
        ref should be ("ref")
        status should be (Sanitised)
      }
    }

    "returns an error if the payment is not sanitiser" in {
      val validPaymentSubmission = ValidatedPaymentSubmission(900, "ref1", Valid)
      val payment = PaymentSubmissionSanitiser.sanitise(validPaymentSubmission)

      inside(payment) { case message =>
        message should be ("invalid payment submission")
      }
    }
  }
}
