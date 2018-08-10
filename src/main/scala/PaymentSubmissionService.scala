package test

import scala.util.{Failure, Success, Try}

object PaymentSubmissionService {
  def sanitisePaymentSubmission(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission): Try[SanitisedPaymentSubmission[Sanitised.type]] = for {
    validatedPaymentSubmission <- PaymentSubmissionValidator.validate(unvalidatedPaymentSubmission)
    sanitisedPaymentSubmission <- PaymentSubmissionSanitiser.sanitise(validatedPaymentSubmission)
  } yield sanitisedPaymentSubmission

  def submitPayment(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission) = sanitisePaymentSubmission(unvalidatedPaymentSubmission) match {
    case Success(paymentSubmission) => paymentSubmission
    case Failure(exception) => exception.getMessage
  }
}



