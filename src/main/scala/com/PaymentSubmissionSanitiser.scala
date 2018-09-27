package com

import java.time.LocalDateTime

import com.model._
import com.model.cycles._

trait PaymentSubmissionSanitiser {

  def sanitise(paymentSubmission: PaymentSubmission): CycleResponseType[NotSanitisedPaymentSubmission, SanitisedPaymentSubmission] = {
    paymentSubmission match {
      case paymentSub if paymentSub.reference == "ref" =>
        println(s"ref: ${paymentSub.reference}")
        Right(SanitisedPaymentSubmission(paymentSub))
      case paymentSub =>
        val value = PaymentSubmissionValue(paymentSub.amount, paymentSub.reference, Some(LocalDateTime.now()))
        Left(NotSanitisedPaymentSubmission(value))
    }
  }
}



