package com

import com.model._
import com.model.cycles._
import org.scalatest.{Inside, Matchers, WordSpec}

class PaymentSubmissionSanitiserSpec extends WordSpec with Matchers with Inside with PaymentSubmissionSanitiser {

  "sanitise" should {
    val validData = PaymentSubmissionValue(900, "ref")
    val invalidData = validData.copy(reference = "ref1")

    "returns a validated payment if the payment is valid" in {
      val payment = sanitise(validData)

      inside(payment) {
        case Right(SanitisedPaymentSubmission(data)) =>
          data.amount should be (validData.amount)
          data.reference should be (validData.reference)
      }
    }

    "returns an error if the payment is not sanitiser" in {
      val payment = sanitise(invalidData)
      inside(payment) {
        case Left(NotSanitisedPaymentSubmission(data)) =>
          data.amount should be(invalidData.amount)
          data.reference should be(invalidData.reference)
      }
    }
  }
}

