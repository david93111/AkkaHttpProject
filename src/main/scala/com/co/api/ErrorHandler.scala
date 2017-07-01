package com.co.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import com.co.boot.ApiDirectives
import com.co.model.ProjectError
import play.api.libs.json.Json

/**
  * Created by david on 30/06/2017.
  */
trait ErrorHandler extends ApiDirectives with ApiMarshallers{

  val exceptionHandler = ExceptionHandler {
      case e: Exception =>
        log.error(e,"Ha ocurrido un error")
        complete(
         createHttpResponse(
           StatusCodes.InternalServerError,
           Json.toJson(
             ProjectError("An unexpected error has occurred, please check the log",
             StatusCodes.InternalServerError)
           )
         )
       )

  }

  def handleException(api: Route): Route ={
    handleExceptions(exceptionHandler){
      api
    }
  }

}

