package com

import java.time.LocalDateTime

import com.model._
import com.model.cycles._

trait PaymentSubmissionSanitiser {

  def sanitise: UnvalidatedPaymentSubmission => CycleResponseType[NotSanitisedPaymentSubmission, SanitisedPaymentSubmission] = {
    case UnvalidatedPaymentSubmission(paymentSub) if paymentSub.reference == "ref" =>
      println(s"ref: ${paymentSub.reference}")
      Right(SanitisedPaymentSubmission(paymentSub))
    case UnvalidatedPaymentSubmission(paymentSub)  =>
      val value = PaymentSubmissionValue(paymentSub.amount, paymentSub.reference, Some(LocalDateTime.now()))
      Left(NotSanitisedPaymentSubmission(value))
  }
}



