package com

import java.time.LocalDateTime

import cats.data.EitherT
import com.model.cycles.{PaymentSubmission}

import scala.concurrent.Future

package object model {

//  type ServiceResponseType[B <: PaymentSubmissionWithStatusType] = EitherT[Future, PaymentSubmissionWithStatusType, B]

  case class PaymentSubmissionValue(amount: Int, reference: String, date: Option[LocalDateTime] = None) extends PaymentSubmission

}
