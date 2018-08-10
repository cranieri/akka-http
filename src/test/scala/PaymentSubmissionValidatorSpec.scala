import org.scalatest.{Matchers, WordSpec}
import test._
import org.scalatest.Inside

import scala.util.Success


class PaymentSubmissionValidator extends WordSpec with Matchers with Inside {

  "validate" should {
    "returns a validated payment if the payment is valid" in {
      val unvalidatedPaymentSubmission = UnvalidatedPaymentSubmission(900, "ref", Unvalidated)
      val payment = PaymentSubmissionValidator.validate(unvalidatedPaymentSubmission)


      inside(payment) { case Success(ValidatedPaymentSubmission(amount, ref, status)) =>
        amount should be (900)
        ref should be ("ref")
        status should be (Valid)
      }
    }

    "returns an error if the payment is not valid" in {
      val unvalidatedPaymentSubmission = UnvalidatedPaymentSubmission(1000, "ref", Unvalidated)
      val payment = PaymentSubmissionService.submitPayment(unvalidatedPaymentSubmission)

      inside(payment) { case message =>
        message should be ("invalid payment submission")
      }
    }
  }
}

