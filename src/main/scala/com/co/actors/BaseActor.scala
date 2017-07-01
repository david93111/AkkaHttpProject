package com.co.actors

import akka.actor.Actor
import akka.event.Logging
import akka.util.Timeout

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.concurrent.duration.{Duration, FiniteDuration}

/**
  * Created by david on 30/06/2017.
  */
trait BaseActor extends Actor {

  val log = Logging(context.system, this)
  val d = Duration("5s")
  implicit val timeout = Timeout(FiniteDuration(d.length, d.unit))
  implicit val exec: ExecutionContextExecutor = ExecutionContext.Implicits.global

}
