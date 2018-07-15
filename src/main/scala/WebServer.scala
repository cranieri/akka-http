import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol.jsonFormat1

import scala.io.StdIn

//#main-class
object WebServer extends App with StatusRoutes {



  val bindingFuture = Http().bindAndHandle(statusRoutes, "localhost", 8080)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ ⇒ system.terminate()) // and shutdown when done




}

