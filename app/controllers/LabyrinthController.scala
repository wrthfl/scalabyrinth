package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
//import play.api.i18n._
import play.api.Logger
import scala.util.Random

class Cell{
  //def id: Int
  var field:Int = 0
  var bot: Boolean = true
  var left: Boolean = true
  var right: Boolean = true
}

@Singleton
class LabyrinthController @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {
      val logger:Logger = Logger(this.getClass)

  def showFormOnly = Action {
    Redirect(routes.LabyrinthController.drawLabyrinth(10))
  }

  def drawLabyrinth(size: Int) = Action {
    val labyrinth = generateMaze(size)
    Ok(views.html.labyrinthView(size, labyrinth))// List.fill(size*size)(cell)))
  }

  def getForm(size: String) = Action {
    Redirect(routes.LabyrinthController.drawLabyrinth(size.toInt))
  }

  def generateMaze(size: Int): List[Cell] = {
    var res: List[Cell] = List.empty
    for(i<-1 to size+1){
      val defaultCell = new Cell{
        field = i
        bot = true
        left = true
        right = true
      }
      for (k <- 0 to size) {
        var row = List.fill(size)(defaultCell)
        row = carveFields(row)
        res = res ++ row
      }
      row = carveFields(row)
      res = res.appended(row)
    }
    
    res
  }

  def genFields[Cell](list: List[Cell], chunks: Int): List[List[Cell]] = {
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

  def carveFields(row: List[Cell]): List[Cell] = {
    val len = row.length
    val fieldnumber = Random.nextInt(len/2)
    var fields = genFields(row,fieldnumber)
    var res = List[Cell]()
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
      res =res ++ field
    }
    res
  }
}
