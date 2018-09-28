package com

import com.model.ServiceResponseType
import com.model.cycles._

import scala.util.{Failure, Success, Try}

trait PaymentSubmissionService extends PaymentSubmissionSanitiser with PaymentSubmissionValidator {

  private def checkPayment(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission): ServiceResponseType[ValidPaymentSubmission] = for {
    sanitisedPaymentSubmission <- sanitise(unvalidatedPaymentSubmission)
    validatedPaymentSubmission <- validate(sanitisedPaymentSubmission)
  } yield validatedPaymentSubmission

  def submitPayment(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission): Try[SubmittedPaymentSubmission] =
    checkPayment(unvalidatedPaymentSubmission) match {
      case Right(ValidPaymentSubmission(value)) =>
        Success(SubmittedPaymentSubmission(value))
      case Left(value) =>
        Failure(new Exception(value.status.toString))
      case _ =>
        // log error
        Failure(new Exception("Internal service error"))
    }
}

object PaymentSubmissionService extends PaymentSubmissionService



