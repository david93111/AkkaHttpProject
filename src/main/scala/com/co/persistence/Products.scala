package com.co.persistence

import com.co.configuration.DbAccess
import com.co.model.ProjectModel
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

/**
  * Created by david on 29/06/2017.
  */

case class ProductsModel(id: Int,name: String, price: Double, imageUrl:String) extends ProjectModel

trait Products extends DbAccess{

  class Product(tag: Tag) extends Table[(Int, String, Double, String)](tag, "products") {
    def id = column[Int]("id", O.PrimaryKey) // This is the primary key column
    def name = column[String]("name")
    def price = column[Double]("price")
    def image = column[String]("image")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, name, price , image)
  }

  private val products = TableQuery[Product]

  def selectProducts : Future[Seq[(Int, String, Double, String)]] = {
    db.run(products.result)
  }

  def selectById(id: Int): Future[Seq[(Int, String, Double, String)]] = {
    db.run(
      products.filter(_.id === id).result
    )
  }

}
