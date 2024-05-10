import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color
import scalafx.scene.input.MouseEvent
import scala.util.Random

object Main extends JFXApp3 {
  val width = 100  
  val height = 100 
  val pixel_size = 5

  val grid = Grid.empty(width, height)
  val random = new Random()

  override def start(): Unit = {
    val canvas = new Canvas(width * pixel_size, height * pixel_size)
    val gc = canvas.graphicsContext2D

    stage = new JFXApp3.PrimaryStage {
      title.value = "Falling Sand Simulator"
      scene = new Scene {
        content = canvas

        onMouseClicked = (me: MouseEvent) => {
          val x = (me.x / pixel_size).toInt
          val y = (me.y / pixel_size).toInt
          createSand(x, y)
          drawGrid(gc)
        }
      }
    }

    drawGrid(gc)

    scalafx.animation.AnimationTimer { _ =>
      updateGrid()
      drawGrid(gc)
    }.start()
  }

  def drawGrid(gc: scalafx.scene.canvas.GraphicsContext): Unit = {
    gc.clearRect(0, 0, width * pixel_size, height * pixel_size) 
    for (y <- 0 until height; x <- 0 until width) {
      grid.cells(y)(x) match {
        case Some(p) => gc.fill = p.color
        case None => gc.fill = Color.White
      }
      gc.fillRect(x * pixel_size, y * pixel_size, pixel_size, pixel_size)
    }
  }

  def updateGrid(): Unit = {
    for (y <- 0 until height; x <- 0 until width) {
      grid.cells(y)(x) match {
        case Some(particle) =>
          val newParticle = particle.update(grid)
          grid.updateParticle(particle, newParticle)
        case None => 
      }
    }
  }

  def createSand(x: Int, y: Int): Unit = {
    val radius = 2 
    for (dx <- -radius to radius; dy <- -radius to radius if dx * dx + dy * dy <= radius * radius) {
      val nx = x + dx
      val ny = y + dy
      if (nx >= 0 && nx < width && ny >= 0 && ny < height && grid.cells(ny)(nx).isEmpty) {
        grid.cells(ny)(nx) = Some(Sand(nx, ny, random))
      }
    }
  }

}
