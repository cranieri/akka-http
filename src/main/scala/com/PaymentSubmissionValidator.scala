package com

import com.model.{UnvalidatedPaymentSubmission, Valid, ValidatedPaymentSubmission}

import scala.util.{Failure, Success, Try}

trait PaymentSubmissionValidator {

  def validate(paymentSubmission: UnvalidatedPaymentSubmission): Try[ValidatedPaymentSubmission[Valid.type]] = {
    if (paymentSubmission.amount < 1000)
      Success(ValidatedPaymentSubmission(paymentSubmission.amount, paymentSubmission.reference, Valid))
    else
      Failure(new Exception("invalid payment submission"))
  }
}



