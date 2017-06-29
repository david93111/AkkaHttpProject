package co.com.configuration

import com.typesafe.config.{Config, ConfigFactory}

/**
  * Created by david on 29/06/2017.
  */
object AppConfig {
  val config: Config = ConfigFactory.load("config")
}
