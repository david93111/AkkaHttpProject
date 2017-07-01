package com.co.actors.messages

/**
  * Created by david on 30/06/2017.
  */
trait ProjectMessages

case class CreateCandidate(name: String) extends ProjectMessages
case class GetCandidate(name: String) extends ProjectMessages
case class VoteCandidate(name: String) extends ProjectMessages
case class Vote() extends ProjectMessages
case class GiveVotes() extends ProjectMessages
case class Response(message: String) extends ProjectMessages
