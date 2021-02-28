package controllers

import models.DatabaseExecutionContext

import javax.inject.Inject
import play.api.db.Database

import scala.concurrent.Future

class LabyrinthDatabase @Inject() (db: Database, databaseExecutionContext: DatabaseExecutionContext) {
  def updateSomething(): Unit = {
    Future {
      db.withConnection { conn =>
        // do whatever you need with the db connection
      }
    }(databaseExecutionContext)
  }
}
