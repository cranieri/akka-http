package com

import org.scalatest.{Inside, Matchers, WordSpec}
import com.model.{Sanitised, SanitisedPaymentSubmission, Unvalidated, UnvalidatedPaymentSubmission}

import scala.util.{Failure, Success, Try}

class PaymentSubmissionServiceSpec extends WordSpec with Matchers with Inside {

  "submitPayment" should {
    "returns the submitted payment if the payment is valid and sanitised" in {
      val unvalidatedPaymentSubmission = UnvalidatedPaymentSubmission(900, "ref", Unvalidated)
      val payment = PaymentSubmissionService.submitPayment(unvalidatedPaymentSubmission)

      inside(payment) {
        case Success(SanitisedPaymentSubmission(amount, ref, status)) =>
          amount should be (900)
          ref should be ("ref")
          status should be (Sanitised)
        case _ =>
          fail("Wrong response")
      }
    }

    "returns an error if the payment is not valid" in {
      val unvalidatedPaymentSubmission = UnvalidatedPaymentSubmission(1000, "ref", Unvalidated)
      val payment = PaymentSubmissionService.submitPayment(unvalidatedPaymentSubmission)

      inside(payment) {
        case message: Failure[_] =>
          message.exception.getMessage should be ("invalid payment submission")
      }
    }

    "returns an error if the payment cannot be sanitised" in {
      val unvalidatedPaymentSubmission = UnvalidatedPaymentSubmission(900, "ref1", Unvalidated)
      val payment: Try[SanitisedPaymentSubmission[model.Sanitised.type]] = PaymentSubmissionService.submitPayment(unvalidatedPaymentSubmission)

      inside(payment) {
        case message: Failure[_] =>
          message.exception.getMessage should be ("not sanitised payment submission")
      }
    }
  }
}

