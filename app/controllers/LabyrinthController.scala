//v1.0.0

package controllers

import javax.inject._
import play.api.mvc._
import play.api.Logger
import scala.util.Random


class cell {
  var fieldID: Int = 0
  var bot: String = ""
  var left: String = ""
  var right: String = ""
}

@Singleton
class LabyrinthController @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {
  val logger: Logger = Logger(this.getClass)

  def showFormOnly = Action {
    Redirect(routes.LabyrinthController.drawLabyrinth(10))
  }

  def getForm(size: String) = Action {
    Redirect(routes.LabyrinthController.drawLabyrinth(size.toInt))
  }

  def drawLabyrinth(size: Int) = Action {
    val labyrinth = generateMaze(size)
    Ok(views.html.labyrinthView(size, labyrinth))
  }

  def generateMaze(size: Int): List[cell] = {
    var res:List[cell] = List[cell]()
    val lab: List[List[cell]] = List.fill(size)(List.fill(size)(new cell))
    for(row<-lab){
      res = res :++ carveFields(row)
    }
    res
  }

  def carveFields(row: List[cell]): List[cell] = {
    val len = row.length
    val start = len / 2 - len / 5
    val end = len / 2 + 1
    val rnd = start + Random.nextInt(end - start)
    val fieldCol = genFields(row, rnd)

    var res = List[cell]()
    for (fields <- fieldCol) {
      val fieldId = fields.head.fieldID
      var field = List[cell]().empty
      if (fields.size == 1) {
        fields.head.bot = "bot"
        field = field :++ fields
      } else if (fields.size == 2) {
        fields.head.right = "right"
        fields(1).left = "left"
        fields(1).fieldID = fieldId
        fields(Random.nextInt(2)).bot = "bot"
        field = field :++ fields
      } else {
        val center = fields.tail.take(row.size - 2)
        for (cell <- center) {
          cell.left = "left"
          cell.right = "right"
          cell.fieldID = fieldId
        }
        val head = fields.head
        head.right = "right"
        head.fieldID = fieldId
        val last = fields.reverse.head
        last.left = "left"
        last.fieldID = fieldId
        field = field :+ head :++ center :+ last
        field(Random.nextInt(field.size)).bot = "bot"
      }
      res = res :++ field
    }
    res
  }

  def genFields[cell](list: List[cell], chunks: Int): List[List[cell]] = {
    if (chunks == 0) List.empty
    else if (chunks == 1) List(list)
    else {
      val index = 1 + Random.nextInt(list.size / chunks)
      val (h, t) = list splitAt index
      h +: genFields(t, chunks - 1)
    }
  }
}
