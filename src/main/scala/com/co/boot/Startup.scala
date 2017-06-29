package com.co.boot

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.co.api.Api
import com.co.configuration.AppConfig
import com.typesafe.config.Config

import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.concurrent.forkjoin.ForkJoinPool
import scala.concurrent.{ExecutionContext, Future}

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
    val port = system.settings.config.getInt("http.port")
    implicit val materializer = ActorMaterializer()
    val bindingFuture: Future[ServerBinding] =
      Http().bindAndHandle(api, host, port)

    val log = Logging(system.eventStream, "akka_project")
    bindingFuture.map { serverBinding =>
      log.info(s"Server Started on ${serverBinding.localAddress} ")
    }.onFailure {
      case ex: Exception =>
        log.error(ex, s"Server Bind Failed cause $ex", host, port)
        system.terminate()
    }
  }

  def configuredRequestTimeout(config: Config): Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}
