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
    val labyrinth = mapping(
      "size"->size,
      "fields"->generateMaze(size)
    )
    Ok(views.html.labyrinthView(labyrinth))
  }
  def getForm(size:String)= Action{
    // val sizeasint = size.toInt
    // Ok(s"$size größe")
    Redirect(routes.LabyrinthController.drawLabyrinth(size.toInt))
  }
  def generateMaze(size:Int):List ={
    var res 
    for(i<-0 to size){
      var row = list.fill(size)(cell)
      row = carveFields(row)
      res = res.append(row)
    } 

  }
  def carveFields(row:List):List = {
    fieldnumber = Random.nextInt(row.length/2)
    
  }

  
}
// object Labyrinth extends Controller {

//   val labyrinthData = mapping("size"->number.verifying(min(10),max(100))
//   )
//   (Labyrinth.apply)(Labyrinth.unapply)
// }