package pizza

import SlicerStrategy._

trait SlicerStrategy {
  def startX: Int
  def startY: Int
  def sliceSize: SliceSize
}

object SlicerStrategy {
  sealed trait SliceSize
  case object MaxSlice extends SliceSize
  case object MinSlice extends SliceSize

  case object UpperLeftMaxSize extends SlicerStrategy {
    override val startX = 0
    override val startY = 0
    override val sliceSize = MaxSlice
  }
}
