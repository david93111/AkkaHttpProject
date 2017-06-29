package co.com.api

import akka.util.Timeout
import co.com.persistence.{CandidateModel, Candidates}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by david on 28/06/2017.
  */
trait ApiServices extends Candidates{

  implicit def executionContext: ExecutionContext

  implicit def requestTimeout: Timeout

  def getCandidates: Future[Seq[CandidateModel]] = {
    selectCandidates.map{ candidates =>
        candidates.map{
          case (id,name,lastname,username) =>
            CandidateModel(id,name,lastname,username)
        }
    }
  }

}
