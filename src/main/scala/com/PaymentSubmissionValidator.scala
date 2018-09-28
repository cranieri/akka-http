package com

import java.time.LocalDateTime

import com.model._
import com.model.cycles._

trait PaymentSubmissionValidator {

  val amountLimit = 1000

  def validate: PartialFunction[SanitisedPaymentSubmission, CycleResponseType[InvalidPaymentSubmission, ValidPaymentSubmission]] = {
    case SanitisedPaymentSubmission(paymentSubmission) =>
      if (paymentSubmission.amount < amountLimit)
        Right(ValidPaymentSubmission(paymentSubmission))
      else {
        val value = PaymentSubmissionValue(paymentSubmission.amount, paymentSubmission.reference, Some(LocalDateTime.now()))
        Left(InvalidPaymentSubmission(value))
      }
  }
}



