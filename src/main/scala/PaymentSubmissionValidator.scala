package test

import scala.util.Try

object PaymentSubmissionValidator {

  def validate(paymentSubmission: UnvalidatedPaymentSubmission): Try[ValidatedPaymentSubmission[Valid.type]] = {
    if (paymentSubmission.amount < 1000)
      Try(ValidatedPaymentSubmission[Valid.type](paymentSubmission.amount, paymentSubmission.reference, Valid))
    else
      Try(throw new Exception("invalid payment submission"))
  }
}



