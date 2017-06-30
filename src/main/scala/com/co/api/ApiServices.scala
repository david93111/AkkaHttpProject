package com.co.api

import akka.actor.ActorSystem
import akka.event.Logging
import akka.util.Timeout
import com.co.configuration.AppConfig
import com.co.persistence.{CandidateModel, Candidates, Products, ProductsModel}
import com.typesafe.config.Config

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by david on 28/06/2017.
  */
trait ApiServices extends Candidates with Products{

  val config: Config = AppConfig.config
  implicit val system = ActorSystem("akkaSystem", config)
  val log = Logging(system.eventStream, "akka_project")

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

  def getProducts ={
    selectProducts.map{ products =>
      products.map{
        case (id,name,price,url) =>
          ProductsModel(id,name,price,url)
      }
    }
  }

  def selectProductById(id: Int): Future[ProductsModel] ={
    selectById(id).map{ products =>
      val product = products.head
      ProductsModel(product._1,product._2,product._3,product._4)
    }
  }

}
