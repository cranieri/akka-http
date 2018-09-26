package com

import com.model.{Sanitised, SanitisedPaymentSubmission, UnvalidatedPaymentSubmission}

import scala.util.Try

object PaymentSubmissionService extends PaymentSubmissionSanitiser with PaymentSubmissionValidator {

  private def sanitisePaymentSubmission(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission): Try[SanitisedPaymentSubmission[Sanitised.type]] = for {
    validatedPaymentSubmission <- validate(unvalidatedPaymentSubmission)
    sanitisedPaymentSubmission <- sanitise(validatedPaymentSubmission)
  } yield sanitisedPaymentSubmission

  def submitPayment(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission): Try[SanitisedPaymentSubmission[Sanitised.type]] =
    sanitisePaymentSubmission(unvalidatedPaymentSubmission)
}



