import scala.collection.mutable

case class Grid(val width: Int, val height: Int, cells: mutable.ArrayBuffer[mutable.ArrayBuffer[Option[Particle]]]) {
    def updateParticle(old_particle: Particle, new_particle: Particle) : Unit = {
        cells(old_particle.y)(old_particle.x) = None
        if (new_particle.y < height && new_particle.x >= 0 && new_particle.x < width) {
            cells(new_particle.y)(new_particle.x) = Some(new_particle)
        }
    }
    
    def hasParticle(x: Int, y: Int) : Boolean = {
        x >= 0 && x < width && y >= 0 && y < height && cells(y)(x).isDefined
    }
} 

object Grid {
    def empty(width: Int, height: Int) : Grid = Grid(width, height, mutable.ArrayBuffer.fill(height)(mutable.ArrayBuffer.fill(width)(None: Option[Particle])))
}
