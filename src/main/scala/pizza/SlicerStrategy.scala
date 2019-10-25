package pizza

import SlicerStrategy._

final case class SlicerStrategy(
  sliceSize: SliceSize,
  directionPreference: DirectionPreference,
)

object SlicerStrategy {
  sealed trait SliceSize
  case object MaxSlice extends SliceSize
  case object MinSlice extends SliceSize
  case object RandomSlice extends SliceSize

  sealed trait DirectionPreference
  case object Horizontal extends DirectionPreference
  case object Vertical extends DirectionPreference
}
