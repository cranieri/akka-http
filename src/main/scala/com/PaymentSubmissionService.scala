package com

import akka.http.scaladsl.model.HttpResponse
import cats.data.EitherT
import com.model.{PaymentSubmissionValue, ServiceResponseType}
import com.model.cycles.{ValidPaymentSubmission, _}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

trait PaymentSubmissionService extends PaymentSubmissionSanitiser with PaymentSubmissionValidator {
  import scala.concurrent.ExecutionContext.Implicits.global._
  import cats.instances.future._
  private def checkPayment(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission)(implicit ec: ExecutionContext): EitherT[Future, InvalidPaymentSubmission, SubmittedPaymentSubmission] = {
    val valid = for {
      sanitisedPaymentSubmission <- sanitise(unvalidatedPaymentSubmission)
      validatedPaymentSubmission <- validate(sanitisedPaymentSubmission)
    } yield validatedPaymentSubmission

    valid match {
      case Right(v) => for {
        submittedPaymentSubmission <- submit(v)
        updatedPaymentSubmission <- updatePaymentSubmission(submittedPaymentSubmission)
      } yield updatedPaymentSubmission
      case _ => {
        EitherT.left(Future {InvalidPaymentSubmission(unvalidatedPaymentSubmission.data)} )
      }
    }
  }

  def submitPayment(unvalidatedPaymentSubmission: UnvalidatedPaymentSubmission)(implicit ec: ExecutionContext): Unit =
    checkPayment(unvalidatedPaymentSubmission).value.onComplete({
      case Success(submittedPaymentSubmission) => SubmittedPaymentSubmission(PaymentSubmissionValue(900, "ref"))
      case Failure(invalidPaymentSubmission) => SubmittedPaymentSubmission(PaymentSubmissionValue(900, "ref"))
    })

  def submit(v: ValidPaymentSubmission)(implicit ec: ExecutionContext): EitherT[Future, InvalidPaymentSubmission, SubmittedPaymentSubmission] = {
    if (v.data.amount == 100) EitherT.right(Future { SubmittedPaymentSubmission(PaymentSubmissionValue(900, "ref")) }) else {
      EitherT.left(Future.successful { InvalidPaymentSubmission(PaymentSubmissionValue(900, "ref")) })
    }
  }
  def updatePaymentSubmission(submittedPaymentSubmission: SubmittedPaymentSubmission)(implicit ec: ExecutionContext): EitherT[Future, InvalidPaymentSubmission, SubmittedPaymentSubmission] = {
    if (submittedPaymentSubmission.data.amount == 100) EitherT.fromEither(Right(SubmittedPaymentSubmission(PaymentSubmissionValue(900, "ref")))) else EitherT.fromEither(Left(InvalidPaymentSubmission(PaymentSubmissionValue(900, "ref"))))
  }


}

case class User(id: Long, name: String)

// In actual code, probably more than 2 errors
sealed trait Error
object Error {
  final case class UserNotFound(userId: Long) extends Error
  final case class ConnectionError(message: String) extends Error
}
object UserRepo {
  def followers(userId: Long): Either[Error, List[User]] = ???
}

object PaymentSubmissionService extends PaymentSubmissionService



