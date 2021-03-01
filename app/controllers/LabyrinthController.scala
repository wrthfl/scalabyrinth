package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
//import play.api.i18n._
import play.api.Logger
import scala.util.Random

class cell{
  //def id: Int
  var field:Int = 0
  // def top: Boolean = true
  var bot: Boolean = true
  var left: Boolean = true
  var right: Boolean = true
}

@Singleton
class LabyrinthController @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {

  def showFormOnly = Action {
    Ok(views.html.labyrinthView(0))
  }
  def drawLabyrinth(size: Int) = Action {
    // val sizeasint = size.toInt
    val labyrinth = generateMaze(size)
    Ok(views.html.labyrinthView(size, labyrinth))
  }
  def getForm(size: String) = Action {
    // val sizeasint = size.toInt
    // Ok(s"$size größe")
    Redirect(routes.LabyrinthController.drawLabyrinth(size.toInt))
  }

  def generateMaze(size: Int): List[cell] = {
    var res: List[cell] = List[cell]()
    for(i<-1 to size+1){
      val defaultCell = new cell{
        field = i
        bot = true
        left = true
        right = true
      }
      for (k <- 0 to size) {
        var row = List.fill(size)(defaultCell)
        row = carveFields(row)
        res = res.++(row)
      }
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
    val fieldnumber = Random.nextInt(len/2)
    var fields = genFields(row,fieldnumber)
    var res = List[cell]()
    for (field <- fields) {
      for (i <- 0 to field.length-1) {
        var c = field(i)
        f(c)
        c.field = i
        val len = field.length
        i match {
          case 0            => c.right = false
          case  `len` => c.left = false
          case _            => (c.left = false, c.right = false)
        }
      }
      field(Random.nextInt(field.length)).bot = false;
      res ::: field
    }

    res
  }

}
// object Labyrinth extends Controller {

//   val labyrinthData = mapping("size"->number.verifying(min(10),max(100))
//   )
//   (Labyrinth.apply)(Labyrinth.unapply)
// }
