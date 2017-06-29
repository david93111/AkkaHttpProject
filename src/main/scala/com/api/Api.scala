package com.api

import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, StatusCode}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

/**
  * Created by david on 24/06/2017.
  */
trait Api extends ApiServices with ApiMarshallers{

  def pathApi: Route = pathPrefix("ces3") {
    path("ping") {
      pathEndOrSingleSlash {
        get {
          onSuccess(getPing) { events =>
            complete(OK, events)
          }

        }
      }
    } ~ path("vote" / "candidates") {
      pathEndOrSingleSlash {
        get {
          onSuccess(getCandidates) { candidates =>
            complete(createHttpResponse(OK, Json.toJson(candidates)))
          }
        }
      }
    }
  }

  private def getPing = Future {
    val p = getClass.getPackage
    val version = p.getImplementationVersion
    s"Status OK"
  }

  def createHttpResponse(statusCode: StatusCode, entity: JsValue): HttpResponse = {
    HttpResponse(statusCode, entity = HttpEntity(`application/json`, entity.toString))
  }

}
