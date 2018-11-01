package com.model

package object statuses {

  sealed trait PaymentStatus {
    def status: String
    override val toString: String = status
  }

  sealed abstract class ValidatedPaymentStatus(val status: String) extends PaymentStatus
  case class  Valid() extends ValidatedPaymentStatus("valid")
  case class  Invalid() extends ValidatedPaymentStatus("invalid")

  sealed abstract class SanitisedPaymentStatus(val status: String) extends PaymentStatus
  case class Sanitised() extends SanitisedPaymentStatus("sanitised")
  case class NotSanitised() extends SanitisedPaymentStatus("not_sanitised")

  sealed abstract class SubmittedPaymentStatus(val status: String) extends PaymentStatus
  case class  Submitted() extends SubmittedPaymentStatus("submitted")

  sealed abstract class UnvalidatedPaymentStatus(val status: String) extends PaymentStatus
  case class Unvalidated() extends UnvalidatedPaymentStatus("unvalidated")

}
