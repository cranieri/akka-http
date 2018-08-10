package test

sealed trait PaymentSubmissionStatus

sealed abstract class ValidatedPaymentStatus extends PaymentSubmissionStatus
case object Valid extends ValidatedPaymentStatus {
  override val toString = "valid"
}
case object Invalid extends ValidatedPaymentStatus{
  override val toString = "invalid"
}

sealed abstract class SanitisedPaymentStatus extends PaymentSubmissionStatus
case object Sanitised extends SanitisedPaymentStatus {
  override val toString = "sanitised"
}
case object NotSanitised extends SanitisedPaymentStatus {
  override val toString = "not_sanitised"
}

sealed trait SubmittedPaymentStatus extends PaymentSubmissionStatus
case object Submitted extends SubmittedPaymentStatus{
  override val toString = "submitted"
}

sealed trait UnvalidatedPaymentStatus extends PaymentSubmissionStatus
case object Unvalidated extends UnvalidatedPaymentStatus{
  override val toString = "unvalidated"
}