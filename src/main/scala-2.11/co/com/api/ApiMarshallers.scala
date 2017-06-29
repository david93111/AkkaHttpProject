package co.com.api

import co.com.persistence.CandidateModel
import play.api.libs.json.{Json, Writes}

/**
  * Created by david on 28/06/2017.
  */
trait ApiMarshallers {
  implicit val candidatesMarshaller: Writes[CandidateModel] = Json.writes[CandidateModel]
}
