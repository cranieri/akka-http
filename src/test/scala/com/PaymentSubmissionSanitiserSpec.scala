package com

import org.scalatest.TryValues._
import org.scalatest.{Inside, Matchers, WordSpec}
import com.model.{Sanitised, SanitisedPaymentSubmission, Valid, ValidatedPaymentSubmission}

import scala.util.Success


class PaymentSubmissionSanitiserSpec extends WordSpec with Matchers with Inside with PaymentSubmissionSanitiser {

  "sanitise" should {
    "returns a validated payment if the payment is valid" in {
      val validPaymentSubmission = ValidatedPaymentSubmission(900, "ref", Valid)
      val payment = sanitise(validPaymentSubmission)


      inside(payment) { case Success(SanitisedPaymentSubmission(amount, ref, status)) =>
        amount should be (900)
        ref should be ("ref")
        status should be (Sanitised)
      }
    }

    "returns an error if the payment is not sanitiser" in {
      val validPaymentSubmission = ValidatedPaymentSubmission(900, "ref1", Valid)
      val payment = sanitise(validPaymentSubmission)
      payment.isFailure should be (true)
      payment.failure.exception.getMessage should be ("not sanitised payment submission")
//      inside(payment) { case message =>
//        message should be ("invalid payment submission")
//      }
    }
  }
}

