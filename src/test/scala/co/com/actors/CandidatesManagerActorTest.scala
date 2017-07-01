package co.com.actors

import akka.actor.{ActorSystem, Props}
import akka.testkit.TestKit
import com.co.actors.CandidatesManagerActor
import com.co.actors.messages.{CreateCandidate, Response, VoteCandidate}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import akka.pattern.ask

/**
  * Created by david on 1/07/2017.
  */
class CandidatesManagerActorTest extends TestKit(ActorSystem("MySpec"))
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "Candidate Manager Test" must {

    val actor = system.actorOf(Props[CandidatesManagerActor])

    "Create a candidate actor and supervise it" in {

      actor ! CreateCandidate("test")

      expectNoMsg()

    }
    
  }
}
