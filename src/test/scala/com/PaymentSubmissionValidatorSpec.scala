package com

import org.scalatest.{Inside, Matchers, WordSpec}
import com.model.{Unvalidated, UnvalidatedPaymentSubmission, Valid, ValidatedPaymentSubmission}

import scala.util.{Failure, Success}


class PaymentSubmissionValidatorSpec extends WordSpec with Matchers with Inside with PaymentSubmissionValidator {

  "validate" should {
    "returns a validated payment if the payment is valid" in {
      val unvalidatedPaymentSubmission = UnvalidatedPaymentSubmission(900, "ref", Unvalidated)
      val payment = validate(unvalidatedPaymentSubmission)


      inside(payment) {
        case Success(ValidatedPaymentSubmission(amount, ref, status)) =>
          amount should be (900)
          ref should be ("ref")
          status should be (Valid)
      }
    }

    "returns an error if the payment is not valid" in {
      val unvalidatedPaymentSubmission = UnvalidatedPaymentSubmission(1000, "ref", Unvalidated)
      val payment = validate(unvalidatedPaymentSubmission)

      inside(payment) {
        case message: Failure[_] =>
          message.exception.getMessage should be ("invalid payment submission")
      }
    }
  }
}

