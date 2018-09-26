package com

package object model {

  sealed trait PaymentSubmissionStatus {
    def status: String
    override val toString: String = status
  }

  sealed abstract class ValidatedPaymentStatus(val status: String) extends PaymentSubmissionStatus
  object Valid extends ValidatedPaymentStatus("valid")
  object Invalid extends ValidatedPaymentStatus("invalid")

  sealed abstract class SanitisedPaymentStatus(val status: String) extends PaymentSubmissionStatus
  case object Sanitised extends SanitisedPaymentStatus("sanitised")
  object NotSanitised extends SanitisedPaymentStatus("not_sanitised")

  sealed abstract class SubmittedPaymentStatus(val status: String) extends PaymentSubmissionStatus
  object Submitted extends SubmittedPaymentStatus("submitted")

  sealed abstract class UnvalidatedPaymentStatus(val status: String) extends PaymentSubmissionStatus
  object Unvalidated extends UnvalidatedPaymentStatus("unvalidated")

}
