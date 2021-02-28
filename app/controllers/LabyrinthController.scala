package controllers

import javax.inject._
import play.api.mvc._
//import play.api.i18n._

import scala.util.Random

@Singleton
class LabyrinthController @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {

  def cellCreator(openings: Int): List[Boolean] = {
    val x = List.fill(openings)(true)
    val y = List.fill(4 - openings)(false)

    Random.shuffle(x ++ y)
  }
  def drawLabyrinth: Action[AnyContent] = Action {
    Ok(views.html.labyrinthView())
  }
  def drawCells(): Action[AnyContent] = TODO
}
