package com.co.actors

import com.co.actors.messages.{GiveVotes, Vote}

/**
  * Created by david on 30/06/2017.
  */
class CandidateActor extends BaseActor{

  var votes = 0

  override def receive: Receive = {
    case Vote =>
      votes += 1
      log.info(s"Se incrementan los votos a $getVotes")
    case GiveVotes =>
      sender() ! getVotes
  }

  def getVotes: Int = votes
}
