package test

import scala.util.{Failure, Success, Try}

object PaymentSubmissionService {

  type ValidatePaymentSubmission = UnvalidatedPaymentSubmission => Try[ValidatedPaymentSubmission[Valid.type]]
  type SanitisePaymentSubmission = ValidatedPaymentSubmission[Valid.type] => Try[SanitisedPaymentSubmission[Sanitised.type]]

  def submissionOp[A, B](a:A, b:B): A => Try[B] = a => Try(b)

  val validate = submissionOp[UnvalidatedPaymentSubmission, ValidatedPaymentSubmission[Valid.type]]

  val validatePaymentSubmission: ValidatePaymentSubmission = {
    (paymentSubmission: UnvalidatedPaymentSubmission) => Try(ValidatedPaymentSubmission[Valid.type](paymentSubmission.amount, "ref", Valid))

  }

  val sanitisePaymentSubmission: SanitisePaymentSubmission = {
    (paymentSubmission: ValidatedPaymentSubmission[Valid.type]) => {
      Try(SanitisedPaymentSubmission(paymentSubmission.amount, "ref", Sanitised))
    }
  }

  val paymentSubmission = UnvalidatedPaymentSubmission(1000, "ref1", Unvalidated)
  val sanitisedPaymentSubmission: Try[SanitisedPaymentSubmission[Sanitised.type]] = for {
//    validatedPaymentSubmission <- validatePaymentSubmission(paymentSubmission)
    validatedPaymentSubmission <- validate(paymentSubmission, ValidatedPaymentSubmission[Valid.type](paymentSubmission.amount, "ref", Valid))
    sanitisedPaymentSubmission <- sanitisePaymentSubmission(validatedPaymentSubmission)
  } yield sanitisedPaymentSubmission

  val submitPayment = sanitisedPaymentSubmission match {
    case Success(SanitisedPaymentSubmission(_,_,status)) => status
    case Failure(exception) => exception
  }
}



