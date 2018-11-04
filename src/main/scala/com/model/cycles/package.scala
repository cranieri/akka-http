package com.model

import cats.data.EitherT
import com.model.statuses._

import scala.concurrent.Future

package object cycles {

//  type PaymentSubmissionWithStatusType = PaymentSubmissionWithStatus[PaymentSubmission, _ <: PaymentStatus]

//  type CycleResponseType[A <: PaymentSubmissionWithStatusType, B <: PaymentSubmissionWithStatusType] = EitherT[Future, A, B]

  trait PaymentSubmission {
    def amount: Int
    def reference: String
  }

  sealed class PaymentSubmissionWithStatus[A >: PaymentStatus](val status: A, val data: PaymentSubmission)

  case class UnvalidatedPaymentSubmission(override val data: PaymentSubmission) extends PaymentSubmissionWithStatus(Unvalidated, data)
  case class ValidPaymentSubmission(override val data: PaymentSubmission) extends PaymentSubmissionWithStatus(Valid, data)
  case class InvalidPaymentSubmission(override val data: PaymentSubmission) extends PaymentSubmissionWithStatus(Invalid, data)
  case class SanitisedPaymentSubmission(override val data: PaymentSubmission) extends PaymentSubmissionWithStatus(Sanitised, data)
  case class NotSanitisedPaymentSubmission(override val data: PaymentSubmission) extends PaymentSubmissionWithStatus(NotSanitised, data)
  case class SubmittedPaymentSubmission(override val data: PaymentSubmission) extends PaymentSubmissionWithStatus(Submitted, data)

}
