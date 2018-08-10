package test

import scala.util.Try

object PaymentSubmissionSanitiser {

  def sanitise(paymentSubmission: ValidatedPaymentSubmission[Valid.type]): Try[SanitisedPaymentSubmission[Sanitised.type]] = {
    val ref = paymentSubmission.reference
    println(s"ref: $ref")
    if (paymentSubmission.reference == "ref")
      Try(SanitisedPaymentSubmission(paymentSubmission.amount, "ref", Sanitised))
    else
      Try(throw new Exception("not sanitised payment submission"))
  }
}



