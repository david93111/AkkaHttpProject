package com.co.model

import akka.http.scaladsl.model.{StatusCode, StatusCodes}

/**
  * Created by david on 30/06/2017.
  */
trait ProjectModel

case class ProjectError(message:String,code: StatusCode) extends ProjectModel

object NotFound {def apply(message: String) = ProjectError(message,StatusCodes.NotFound)}