package com

import com.model._
import com.model.cycles._
import com.model.statuses._
import org.scalatest.{Inside, Matchers, WordSpec}

import scala.util.{Failure, Success}

class PaymentSubmissionServiceSpec extends WordSpec with Matchers with Inside {

  "submitPayment" should {
    val validData = PaymentSubmissionValue(900, "ref")
    val invalidAmount = PaymentSubmissionValue(1000, "ref")
    val invalidRef = PaymentSubmissionValue(900, "ref1")

    "returns the submitted payment if the payment is valid and sanitised" in {
      val payment = PaymentSubmissionService.submitPayment(UnvalidatedPaymentSubmission(validData))

      inside(payment) {
        case Success(SubmittedPaymentSubmission(data)) =>
          data.amount should be (validData.amount)
          data.reference should be (validData.reference)
      }
    }

    "returns an error if the payment is not valid" in {
      val payment = PaymentSubmissionService.submitPayment(UnvalidatedPaymentSubmission(invalidAmount))

      inside(payment) {
        case message: Failure[_] =>
          message.exception.getMessage should be (Invalid.toString)
      }
    }

    "returns an error if the payment cannot be sanitised" in {
      val payment = PaymentSubmissionService.submitPayment(UnvalidatedPaymentSubmission(invalidRef))

      inside(payment) {
        case message: Failure[_] =>
          message.exception.getMessage should be (NotSanitised.toString)
      }
    }
  }
}

