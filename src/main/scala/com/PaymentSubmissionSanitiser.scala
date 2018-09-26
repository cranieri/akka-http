package com

import com.model.{Sanitised, SanitisedPaymentSubmission, Valid, ValidatedPaymentSubmission}

import scala.util.{Failure, Success, Try}

trait PaymentSubmissionSanitiser {

  def sanitise(paymentSubmission: ValidatedPaymentSubmission[Valid.type]): Try[SanitisedPaymentSubmission[Sanitised.type]] = {
    println(s"ref: ${paymentSubmission.reference}")
    if (paymentSubmission.reference == "ref")
      Success(SanitisedPaymentSubmission(paymentSubmission.amount, "ref", Sanitised))
    else
      Failure(new Exception("not sanitised payment submission"))
  }
}



