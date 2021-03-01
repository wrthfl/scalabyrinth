package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
//import play.api.i18n._
import play.api.Logger
import scala.util.Random

trait cell{
  var field:Int = 0
  var bot: Boolean = true
  var left: Boolean = true
  var right: Boolean = true
}

@Singleton
class LabyrinthController @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {

  def showFormOnly = Action {
    Ok(views.html.labyrinthView(size = 0, labyrinth = List.empty))
  }

  def drawLabyrinth(size: Int) = Action {
    val labyrinth = generateMaze(size)
    Ok(views.html.labyrinthView(size, labyrinth))
  }

  def getForm(size: String) = Action {
    Redirect(routes.LabyrinthController.drawLabyrinth(size.toInt))
  }

  def generateMaze(size: Int): List[cell] = {
    var res: List[cell] = List[cell]().empty
    var row: List[cell] = List[cell]().empty
    for(i<-0 to size){
      for (k <- 1 to size+1) {
        val newCell = new cell{
          field = k
          bot = true
          left = true
          right = true
        }
        row = row.appended(newCell)
      }
      row = carveFields(row)
      res = res.appended(row)
    }
    res
  }

  def genFields[cell](list: List[cell], chunks: Int): List[List[cell]] = {
    if (chunks == 0) Nil
    else if (chunks == 1) List(list)
    else {
      val avg = list.size / chunks
      val rand = (1.0 + Random.nextGaussian() / 3) * avg
      val index = (rand.toInt max 1) min (list.size - chunks)
      val (h, t) = list splitAt index
      h +: genFields(t, chunks - 1)
    }
  }
  def f[T](v: T) = v

  def carveFields(row: List[cell]): List[cell] = {
    val len = row.length
    val fieldNumber = Random.nextInt(len/2)
    var fields = genFields(row,fieldNumber)
    var res = List[cell]()
    for (field <- fields) {
      for (i <- 0 to field.length-1) {
        var c = field(i)
        f(c)
        c.field = i
        val len = field.length
        i match {
          case 0      => c.right = false
          case  `len` => c.left = false
          case _      => (c.left = false, c.right = false)
        }
      }
      field(Random.nextInt(field.length)).bot = false;
      res ::: field
    }
    res
  }
}