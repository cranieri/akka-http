import org.scalatest.{Matchers, WordSpec}
import test._
import org.scalatest.Inside


class PaymentSubmissionServiceSpec extends WordSpec with Matchers with Inside {

  "submitPayment" should {
    "returns the submitted payment if the payment is valid and sanitised" in {
      val unvalidatedPaymentSubmission = UnvalidatedPaymentSubmission(900, "ref", Unvalidated)
      val payment = PaymentSubmissionService.submitPayment(unvalidatedPaymentSubmission)

      inside(payment) { case SanitisedPaymentSubmission(amount, ref, status) =>
        amount should be (900)
        ref should be ("ref")
        status should be (Sanitised)
      }
    }

    "returns an error if the payment is not valid" in {
      val unvalidatedPaymentSubmission = UnvalidatedPaymentSubmission(1000, "ref", Unvalidated)
      val payment = PaymentSubmissionService.submitPayment(unvalidatedPaymentSubmission)

      inside(payment) { case message =>
        message should be ("invalid payment submission")
      }
    }

    "returns an error if the payment cannot be sanitised" in {
      val unvalidatedPaymentSubmission = UnvalidatedPaymentSubmission(900, "ref1", Unvalidated)
      val payment = PaymentSubmissionService.submitPayment(unvalidatedPaymentSubmission)

      inside(payment) { case message =>
        message should be ("not sanitised payment submission")
      }
    }
  }
}

