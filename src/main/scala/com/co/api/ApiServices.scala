package com.co.api

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.event.Logging
import akka.util.Timeout
import com.co.actors.CandidatesManagerActor
import com.co.actors.messages._
import com.co.configuration.AppConfig
import com.co.model.{NotFound, ProjectModel}
import com.co.persistence._
import com.typesafe.config.Config
import akka.pattern.ask
import com.co.boot.ApiDirectives

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

/**
  * Created by david on 28/06/2017.
  */
trait ApiServices extends ApiDirectives with Candidates with Products{

  val candidateManager: ActorRef = system.actorOf(Props[CandidatesManagerActor],"candidateManager")

  implicit def executionContext: ExecutionContext

  implicit def requestTimeout: Timeout

  def getCandidates: Future[Seq[CandidateModelWithVote]] = {
    selectCandidates.map{ candidates =>
        val response = candidates.map{ candidate =>
            CandidateModel(candidate._1,candidate._2,candidate._3,candidate._4)
        }
      response.map(candidate =>
        CandidateModelWithVote(candidate.id,candidate.name,candidate.lastname,candidate.username,getVotes(candidate.username))
      )
    }
  }

  def getVotes(name: String): Int ={
    val votesForCandidate: Future[Any] = candidateManager ? GetCandidate(name)
    val votes =  votesForCandidate.map{
      case votes: Int => votes
      case _ => 0
    }
    Await.result[Int](votes,Duration("5s"))
  }

  def vote(name: String): Future[Response] = {
    val message: Future[Any] = candidateManager ? VoteCandidate(name)
    message.map {
      case e: Response =>
        e
    }
  }

  def getProducts ={
    selectProducts.map{ products =>
      products.map{
        case (id,name,price,url) =>
          ProductsModel(id,name,price,url)
      }
    }
  }

  def selectProductById(id: Int): Future[ProjectModel] ={
    selectById(id).map{ products =>
      val productOption = products.headOption
      if (productOption.isDefined)
        productOption.map( product=> ProductsModel(product._1,product._2,product._3,product._4)).get
      else
        NotFound(s"No se han encontradro productos con id: $id")
    }
  }

  def startCandidates(): Unit ={

    selectCandidates.map { candidates =>
        candidates.foreach(candidate =>
          candidateManager ! CreateCandidate(candidate._4)
        )
    }

  }

}
