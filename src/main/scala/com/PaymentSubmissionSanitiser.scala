package com

import java.time.LocalDateTime

import cats.data.EitherT
import com.model._
import com.model.cycles._
import com.model.statuses.{NotSanitised, PaymentStatus}

import scala.concurrent.{ExecutionContext, Future}
import cats.implicits._

trait PaymentSubmissionSanitiser {
  import cats.instances.future._
  def sanitise(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission)(implicit ec: ExecutionContext): EitherT[Future, _ <: PaymentSubmissionWithStatus[_ >: PaymentStatus], SanitisedPaymentSubmission] =
    unvalidatedPaymentSubmission match {
    case UnvalidatedPaymentSubmission(paymentSub) if paymentSub.reference == "ref" =>
      println(s"ref: ${paymentSub.reference}")
      EitherT.right(Future.successful(SanitisedPaymentSubmission(paymentSub)))
    case UnvalidatedPaymentSubmission(paymentSub)  =>
      val value = PaymentSubmissionValue(paymentSub.amount, paymentSub.reference, Some(LocalDateTime.now()))
      EitherT.left(Future.successful(NotSanitisedPaymentSubmission(value))).leftWiden[PaymentSubmissionWithStatus[_ >: PaymentStatus]]
  }
}



