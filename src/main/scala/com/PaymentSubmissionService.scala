package com

import com.model.ServiceResponseType
import com.model.cycles._

import scala.util.{Failure, Success, Try}

trait PaymentSubmissionService extends PaymentSubmissionSanitiser with PaymentSubmissionValidator {

  private def checkPayment(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission): ServiceResponseType[SanitisedPaymentSubmission] = for {
    validatedPaymentSubmission <- validate(unvalidatedPaymentSubmission.data)
    sanitisedPaymentSubmission <- sanitise(validatedPaymentSubmission.data)
  } yield sanitisedPaymentSubmission

  def submitPayment(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission): Try[SubmittedPaymentSubmission] =
    checkPayment(unvalidatedPaymentSubmission) match {
      case Right(SanitisedPaymentSubmission(value)) =>
        Success(SubmittedPaymentSubmission(value))
      case Left(value) =>
        Failure(new Exception(value.status.toString))
    }
}

object PaymentSubmissionService extends PaymentSubmissionService



