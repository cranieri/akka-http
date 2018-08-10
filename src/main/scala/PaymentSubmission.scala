package test

import test._

sealed trait PaymentSubmission {
  def amount: Int
  def reference: String
  def status: PaymentSubmissionStatus
}

case class UnvalidatedPaymentSubmission(amount: Int, reference: String, status: UnvalidatedPaymentStatus) extends PaymentSubmission
case class ValidatedPaymentSubmission[A <: ValidatedPaymentStatus](amount: Int, reference: String, status: A) extends PaymentSubmission
case class SanitisedPaymentSubmission[A <: SanitisedPaymentStatus](amount: Int, reference: String, status: A) extends PaymentSubmission
case class SentPaymentSubmission(amount: Int, reference: String, status: SubmittedPaymentStatus) extends PaymentSubmission

