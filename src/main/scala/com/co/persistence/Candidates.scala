package com.co.persistence

import com.co.configuration.DbAccess
import com.co.model.ProjectModel
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

/**
  * Created by david on 28/06/2017.
  */
case class CandidateModel(id: Int, name: String, lastname: String, username: String) extends ProjectModel

case class CandidateModelWithVote(id: Int, name: String, lastname: String, username: String,votes: Int) extends ProjectModel

trait Candidates extends DbAccess{

  class Candidate(tag: Tag) extends Table[(Int, String, String, String)](tag, "candidates") {
    def id = column[Int]("id", O.PrimaryKey) // This is the primary key column
    def name = column[String]("name")
    def lastname = column[String]("lastname")
    def username = column[String]("username")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, name, lastname, username)
  }

  private val candidates = TableQuery[Candidate]

  def selectCandidates : Future[Seq[(Int, String, String, String)]] = {
    db.run(candidates.result)
  }

}

