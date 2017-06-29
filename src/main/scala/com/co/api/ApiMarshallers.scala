package com.co.api

import com.co.persistence.{CandidateModel, ProductsModel}
import play.api.libs.json.{Json, Writes}

/**
  * Created by david on 28/06/2017.
  */
trait ApiMarshallers {
  implicit val candidatesMarshaller: Writes[CandidateModel] = Json.writes[CandidateModel]
  implicit val productsMarshaller: Writes[ProductsModel] = Json.writes[ProductsModel]
}
