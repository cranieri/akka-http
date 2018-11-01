package com

import java.time.LocalDateTime

import com.model.cycles.{PaymentSubmission, PaymentSubmissionWithStatusType}

package object model {

  type ServiceResponseType[B <: PaymentSubmissionWithStatusType] = Either[PaymentSubmissionWithStatusType, B]

  case class PaymentSubmissionValue(amount: Int, reference: String, date: Option[LocalDateTime] = None) extends PaymentSubmission

}
