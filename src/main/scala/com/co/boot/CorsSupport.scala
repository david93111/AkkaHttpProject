package com.co.boot

import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.headers.{HttpOrigin, _}
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive0, Route}
import com.co.configuration.AppConfig

/**
  * Created by david on 29/06/2017.
  */
trait CorsSupport {
    lazy val allowedOrigin: HttpOrigin = {
      val config = AppConfig.config
      val allowedOrigin = config.getString("app.cors.allowed-origins")
      HttpOrigin(allowedOrigin)
    }

    //this directive adds access control headers to normal responses
    private def addAccessControlHeaders: Directive0 = {
      respondWithHeaders(
        `Access-Control-Allow-Origin`(allowedOrigin),
        `Access-Control-Allow-Credentials`(true),
        `Access-Control-Allow-Headers`("Authorization", "Content-Type", "X-Requested-With")
      )
    }

    //this handles preflight OPTIONS requests.
    private def preflightRequestHandler: Route = options {
      complete(HttpResponse(StatusCodes.OK).withHeaders(`Access-Control-Allow-Methods`(OPTIONS, POST, PUT, GET, DELETE)))
    }

    def corsHandler(r: Route) = addAccessControlHeaders {
      preflightRequestHandler ~ r
    }

}


