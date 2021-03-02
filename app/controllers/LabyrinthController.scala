package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
//import play.api.i18n._
import play.api.Logger
import scala.util.Random
import scala.collection._
class cell {
  var id: Int = 0
  var field: Int = 0
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

  def drawLabyrinth(size: Int) = Action {
    val labyrinth = generateMaze(size)
    Ok(
      views.html.labyrinthView(size, labyrinth)
    ) // List.fill(size*size)(cell)))
  }

  def getForm(size: String) = Action {
    Redirect(routes.LabyrinthController.drawLabyrinth(size.toInt))
  }

  def generateMaze(size: Int): List[cell] = {
    // var res: List[cell] = List[cell]()
    // var row: List[cell] = List[cell]()
    // var cellcounter = 0;
    
    // for (i <- 1 to size -1) {
    //   for (k <- 1 to size - 1) {
    //     val newCell = new cell {
    //       id = cellcounter
    //       field = (i * size + k)
    //       bot = ""
    //       left = ""
    //       right = ""
    //     }
    //     cellcounter +=1
    //     row :+= newCell
    //   }
    //   row = carveFields(row)
    //   res = res ++ row
    // }
    // res.tail
    var res:List[cell] = List[cell]()
    var lab: List[List[cell]] = List.fill(size)(List.fill(size)(new cell))
    for(row<-lab){
      res = res :++ carveFields(row)
    }
    res    
  }

  def genFields[cell](list: List[cell], chunks: Int): List[List[cell]] = {
    if (chunks == 0) List()
    else if (chunks == 1) List(list)
    else {
      // val avg = list.size / chunks
      // val rand = (1.0 + Random.nextGaussian() / 3) * avg
      val index = 1 + Random.nextInt(list.size / chunks)
      val (h, t) = list splitAt index
      h +: genFields(t, chunks - 1)
    }
  }
  def f[T](v: T) = v

  def carveFields(row: List[cell]): List[cell] = {
    val len = row.length
    // var l = List.empty ++= row

    var start = len / 2 - len / 5
    var end = len / 2 + 1
    var rnd = start + Random.nextInt((end - start) + 1)
    var fieldCol = genFields(row, rnd)

    var res = List[cell]()
    for (fields <- fieldCol) {
      var fieldId = fields(0).field
      var field = List[cell]()
      if (fields.size == 1) {
        fields(0).bot = "bot"
        field = field :++ fields
      } else if (fields.size == 2) {
        fields(0).right = "right"
        fields(1).left = "left"
        fields(1).field = fieldId
        fields(Random.nextInt(2)).bot = "bot"
        field = field :++ fields
      } else {
        var middle = fields.tail.take(row.size - 2)
        for (cell <- middle) {
          cell.left = "left"
          cell.right = "right"
          cell.field = fieldId
        }
        var head = fields.head
        head.right = "right"
        head.field = fieldId
        var last = fields.reverse.take(1)
        last(0).left = "left"
        last(0).field = fieldId

        field = field :+ head :++ middle :++ last
        field(Random.nextInt(field.size)).bot = "bot"
      }

      res = res :++ field
    }
    res.toList
  }
}
