package com.co.api

import com.co.actors.messages.Response
import com.co.model.ProjectError
import com.co.persistence.{CandidateModel, CandidateModelWithVote, ProductsModel}
import play.api.libs.json.{Json, Writes}

/**
  * Created by david on 28/06/2017.
  */
trait ApiMarshallers {
  implicit val candidatesMarshaller: Writes[CandidateModel] = Json.writes[CandidateModel]
  implicit val candidateVoteMarshaller: Writes[CandidateModelWithVote] = Json.writes[CandidateModelWithVote]
  implicit val productsMarshaller: Writes[ProductsModel] = Json.writes[ProductsModel]
  implicit val responseMarshaller: Writes[Response] = Json.writes[Response]
  implicit val errorMarshaller: Writes[ProjectError] =  Writes { projectError =>
    Json.obj(
      "error" -> projectError.message
    )
  }
}
