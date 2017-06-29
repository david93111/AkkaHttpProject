package com.co.api

import akka.util.Timeout
import com.co.persistence.{CandidateModel, Candidates, Products, ProductsModel}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by david on 28/06/2017.
  */
trait ApiServices extends Candidates with Products{

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

}
