package pizza

import SlicerStrategy._

final case class SlicerStrategy(
  sliceSize: SliceSize,
  direction: Direction,
)

object SlicerStrategy {
  sealed trait SliceSize
  case object MaxSlice extends SliceSize
  case object MinSlice extends SliceSize
  case object RandomSlice extends SliceSize

  sealed trait Direction
  case object GoLeft extends Direction
  case object GoDown extends Direction
}
