package com

import java.time.LocalDateTime


import com.model._
import com.model.cycles.{PaymentSubmissionWithStatus, _}

import scala.concurrent.{ExecutionContext, Future}
//import cats.instances.future._
import cats.data._
import cats.implicits._
import com.model.statuses.{PaymentStatus}
trait PaymentSubmissionValidator {

  val amountLimit = 1000

  def validate(sanitisedPaymentSubmission: SanitisedPaymentSubmission)(implicit ec: ExecutionContext): EitherT[Future, PaymentSubmissionWithStatus[_ >: com.model.statuses.PaymentStatus], ValidPaymentSubmission] =
    sanitisedPaymentSubmission match {
    case SanitisedPaymentSubmission(paymentSubmission) =>
      if (paymentSubmission.amount < amountLimit)
        EitherT.right(Future { ValidPaymentSubmission(paymentSubmission) })
      else {
        val value = PaymentSubmissionValue(paymentSubmission.amount, paymentSubmission.reference, Some(LocalDateTime.now()))
        val e = EitherT.leftT[Future, ValidPaymentSubmission]( InvalidPaymentSubmission(value))
        e.leftWiden[PaymentSubmissionWithStatus[_ >: PaymentStatus]]
      }
  }
}



