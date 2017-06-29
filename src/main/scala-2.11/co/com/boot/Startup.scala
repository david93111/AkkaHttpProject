package co.com.boot

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.util.Timeout
import co.com.api.Api
import co.com.configuration.AppConfig
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.concurrent.forkjoin.ForkJoinPool

/**
  * Created by david on 24/06/2017.
  */
object Startup extends App with Api{
  val config = AppConfig.config
  implicit val system = ActorSystem("akkaSystem", config)

  implicit def executionContext: ExecutionContext = ExecutionContext.fromExecutorService(new ForkJoinPool)
  implicit def requestTimeout: Timeout = configuredRequestTimeout(config)

  startUp(pathApi)

  def startUp(api: Route)(implicit system: ActorSystem): Unit = {
    implicit val ec = system.dispatcher
    val host = system.settings.config.getString("http.host")
    // Gets the host and a port from the configuration
    val port = system.settings.config.getInt("http.port")

    // bindAndHandle requires an implicit ExecutionContext
    implicit val materializer = ActorMaterializer()
    val bindingFuture: Future[ServerBinding] =
      Http().bindAndHandle(api, host, port) // Starts the HTTP server

    val log = Logging(system.eventStream, "poc_akka_remote")
    bindingFuture.map { serverBinding =>
      log.info(s"RestApi bound to ${serverBinding.localAddress} ")
    }.onFailure {
      case ex: Exception =>
        log.error(ex, "Failed to bind to {}:{}!", host, port)
        system.terminate()
    }
  }

  def configuredRequestTimeout(config: Config): Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}
