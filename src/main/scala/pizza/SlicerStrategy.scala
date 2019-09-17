package pizza

trait SlicerStrategy {
  def startX: Int
  def startY: Int
}

object SlicerStrategy {

  case object UpperLeft extends SlicerStrategy {
    override val startX = 0
    override val startY = 0
  }
}
