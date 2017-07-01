package com.co.api

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.co.model.ProjectError
import com.co.persistence.ProductsModel
import play.api.libs.json.Json

import scala.concurrent.Future

/**
  * Created by david on 24/06/2017.
  */
trait Api extends ApiServices with ApiMarshallers {

  def pathApi: Route = pathPrefix("ces3") {
    path("ping") {
      pathEndOrSingleSlash {
        get {
          onSuccess(getPing) { events =>
            complete(OK, events)
          }

        }
      }
    }~ path("vote" / Segment) { name =>
        put {
          onSuccess(vote(name)) { response =>
            complete(createHttpResponse(OK, Json.toJson(response)))
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
    } ~ path("shop" / "products" / IntNumber){ id =>
        get {
            onSuccess(selectProductById(id)) {
              case error: ProjectError =>
                complete(createHttpResponse(error.code, Json.toJson(error)))
              case product: ProductsModel =>
              complete(createHttpResponse(OK, Json.toJson(product)))
            }
        }
    } ~ path("shop" / "products") {
      pathEndOrSingleSlash {
        get{
            log.info("Se inicia consulta de productos")
            onSuccess(getProducts) { products =>
              log.info("Consulta de productos exitosa")
              complete(createHttpResponse(OK, Json.toJson(products)))
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
}
