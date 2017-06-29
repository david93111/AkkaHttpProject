package co.com.configuration
import slick.jdbc.PostgresProfile.api._
/**
  * Created by david on 28/06/2017.
  */
object DbConfig {
  val db = Database.forConfig("app.db",AppConfig.config)
}

trait DbAccess {
  val db = DbConfig.db
}
