import scalafx.scene.paint.Color
import scala.util.Random

trait Particle {
    def x: Int
    def y: Int
    def color: Color 
    def update(grid: Grid) : Particle
}

case class Sand(x: Int, y: Int, random: Random) extends Particle {
    val color : Color= {
        val r = random.nextInt(156) + 100
        val g = random.nextInt(56) + 50
        val b = random.nextInt(56) 
        Color.rgb(r, g, b)
    }

    def update(grid: Grid) : Particle = {
        // end condition
        if (y == grid.height - 1 || (grid.hasParticle(x, y + 1) && grid.hasParticle(x - 1, y + 1) && grid.hasParticle(x + 1, y + 1))) {
            this
          // check downwards
        } else if (!grid.hasParticle(x, y + 1)) {
            Sand(x, y + 1, random)
        } else {
            // check sides
            (grid.hasParticle(x - 1, y + 1), grid.hasParticle(x + 1, y + 1)) match {
                case (false, true) => Sand(x - 1, y + 1, random)
                case (true, false) => Sand(x + 1, y + 1, random)
                case (false, false) => 
                    if (random.nextBoolean()) Sand(x - 1, y + 1, random) else Sand(x + 1, y + 1, random)
                case _ => this
            }
        }
    }
}
