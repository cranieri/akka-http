package com

import com.model._
import com.model.cycles._
import org.scalatest.{Inside, Matchers, WordSpec}

class PaymentSubmissionValidatorSpec extends WordSpec with Matchers with Inside with PaymentSubmissionValidator {

  "validate" should {
    val validData = PaymentSubmissionValue(900, "ref")
    val invalidData = validData.copy(amount = 1000)
    "returns a validated payment if the payment is valid" in {
      val payment = validate(validData)

      inside(payment) {
        case Right(ValidPaymentSubmission(data)) =>
          data.amount should be (validData.amount)
          data.reference should be (validData.reference)
      }
    }

    "returns an error if the payment is not valid" in {
      val payment = validate(invalidData)

      inside(payment) {
        case Left(InvalidPaymentSubmission(PaymentSubmissionValue(amount, reference, date))) =>
          amount should be (invalidData.amount)
          reference should be (invalidData.reference)
          date should not be None
      }
    }
  }
}

