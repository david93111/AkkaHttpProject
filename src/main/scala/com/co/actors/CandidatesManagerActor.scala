package com.co.actors

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import com.co.actors.messages._
import akka.pattern.ask

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * Created by david on 30/06/2017.
  */
class CandidatesManagerActor extends BaseActor{

  override def receive: Receive = {
    case candidate: CreateCandidate =>
        val child = context.actorOf(Props[CandidateActor],candidate.name)
        context.watch(child)
        log.info(s"Se ha creado satisfactoriamente el candidato ${candidate.name}")
    case select: VoteCandidate =>
        log.info("Se inicia deposito del voto")
        val candidateActor: Option[ActorRef] = context.child(select.name)
        val response = candidateActor.map{ candidate =>
          candidate ! Vote
          log.info("Se deposita satisfactoriamente el voto")
          Response("Se ha depositado el voto satisfactoriamente")
        }
        sender() ! response.getOrElse(Response("No se ha podido realizar el voto, verifique el candidato intente nuevamente"))
    case select: GetCandidate =>
      log.info("Se inicia obtencion del voto")
      val candidateActor: Option[ActorRef] = context.child(select.name)
      val response = candidateActor.map{ candidate =>
        val votes: Future[Any] = ask(candidate, GiveVotes)(timeout)
        Await.result[Any](votes,Duration("5s"))
      }
      log.info(s"El voto obtenido es $response")
      sender() ! response.getOrElse(0)
  }

}
