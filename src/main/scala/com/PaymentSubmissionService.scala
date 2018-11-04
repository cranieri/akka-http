package com

import cats.data.EitherT
import com.model.PaymentSubmissionValue
import com.model.cycles.{ValidPaymentSubmission, _}
import com.model.statuses.PaymentStatus
import cats.implicits._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

trait PaymentSubmissionService extends PaymentSubmissionSanitiser with PaymentSubmissionValidator {
  private def checkPayment(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission)(implicit ec: ExecutionContext): EitherT[Future, PaymentSubmissionWithStatus[_ >: com.model.statuses.PaymentStatus], SubmittedPaymentSubmission] = {
    for {
      sanitisedPaymentSubmission <- sanitise(unvalidatedPaymentSubmission)
      validatedPaymentSubmission <- validate(sanitisedPaymentSubmission)
      submittedPaymentSubmission <- submit(validatedPaymentSubmission)
      updatedPaymentSubmission <- updatePaymentSubmission(submittedPaymentSubmission)
    } yield updatedPaymentSubmission
  }

  def submit(v: ValidPaymentSubmission)(implicit ec: ExecutionContext): EitherT[Future, PaymentSubmissionWithStatus[_ >: com.model.statuses.PaymentStatus], SubmittedPaymentSubmission] = {
    if (v.data.amount == 100) EitherT.right(Future {
      SubmittedPaymentSubmission(PaymentSubmissionValue(900, "ref"))
    }) else {
      EitherT.left(Future.successful {
        InvalidPaymentSubmission(PaymentSubmissionValue(900, "ref"))
      }).leftWiden[PaymentSubmissionWithStatus[_ >: PaymentStatus]]
    }
  }

  def updatePaymentSubmission(submittedPaymentSubmission: SubmittedPaymentSubmission)(implicit ec: ExecutionContext): EitherT[Future, PaymentSubmissionWithStatus[_ >: com.model.statuses.PaymentStatus], SubmittedPaymentSubmission] = {
    if (submittedPaymentSubmission.data.amount == 100) {
      EitherT.right(Future {
        SubmittedPaymentSubmission(PaymentSubmissionValue(900, "ref"))
      })
    } else {
      EitherT.left(Future.successful {
        InvalidPaymentSubmission(PaymentSubmissionValue(900, "ref"))
      }).leftWiden[PaymentSubmissionWithStatus[_ >: PaymentStatus]]
    }
  }

  def submitPayment(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission)(implicit ec: ExecutionContext): Unit =
    checkPayment(unvalidatedPaymentSubmission).value.onComplete({
      case Success(submittedPaymentSubmission) => SubmittedPaymentSubmission(PaymentSubmissionValue(900, "ref"))
      case Failure(invalidPaymentSubmission) => SubmittedPaymentSubmission(PaymentSubmissionValue(900, "ref"))
    })
}

object PaymentSubmissionService extends PaymentSubmissionService



