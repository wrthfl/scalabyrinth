package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
//import play.api.i18n._

import scala.util.Random

trait cell{
  def id: Int
  def field:Int
  // def top: Boolean = true
  def bot: Boolean = true
  def left: Boolean = true
  def right: Boolean = true
}

@Singleton
class LabyrinthController @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {

  
  def showFormOnly = Action{
    Ok(views.html.labyrinthView(0))
  }
  def drawLabyrinth(size:Int) = Action{
    // val sizeasint = size.toInt
    val labyrinth = generateMaze(size)
    
    Ok(views.html.labyrinthView(size,labyrinth))
  }
  def getForm(size:String)= Action{
    // val sizeasint = size.toInt
    // Ok(s"$size größe")
    Redirect(routes.LabyrinthController.drawLabyrinth(size.toInt))
  }
   def generateMaze(size:Int):List[cell] ={
    var res: List[cell] = List[cell]()
    for(i<-1 to size+1){
      val defaultCell = new cell{
        override def field: Int = i
      }
      for(k<-0 to size){
        var row = List.fill(size)(defaultCell)
        row = carveFields(row)
        res = res.++(row)
      }
    }
    res
  }

  def genFields[cell](list: List[cell], chunks: Int): List[List[cell]] ={
  if (chunks == 0) Nil
  else if (chunks == 1) List(list)
  else {
    val avg = list.size / chunks
    val rand = (1.0 + Random.nextGaussian / 3) * avg
    val index = (rand.toInt max 1) min (list.size - chunks)
    val (h, t) = list splitAt index
    h +: genFields(t, chunks - 1)
    }
  }


  def carveFields(row:List[cell]):List[cell] = {
    val len = row.length
    var fieldnumber = Random.nextInt(len/2)
    var fields = genFields(row,fieldnumber)
    var res = List[cell]()
    for(field<-fields){
      for(i<- 0 to field.length){
        var c = field(i)
        c.field = i
        i match{
          case 0 => c.right = false
          case field.length => c.left = false
          case _ => (c.left = false, c.right=false)
        }
      }
      field(Random.nextInt(field.length)).bot=false;
      res ::: field
    }
    
  }

}
// object Labyrinth extends Controller {

//   val labyrinthData = mapping("size"->number.verifying(min(10),max(100))
//   )
//   (Labyrinth.apply)(Labyrinth.unapply)
// }