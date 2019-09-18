package pizza

class SliceCutter(pizzaConfig: PizzaConfig) {

  /**
    *  code taken and adjusted from [[https://kostyukov.net/posts/combinatorial-algorithms-in-scala/]]
    */
  private def xvariations[A](l: List[A])(n: Int): List[List[A]] = {
    def mixmany(
      x: A,
      ll: List[List[A]],
    ): List[List[A]] = ll match {
      case hd :: tl => foldone(x, hd) ::: mixmany(x, tl)
      case _        => Nil
    }

    def foldone(
      x: A,
      ll: List[A],
    ): List[List[A]] =
      (1 to ll.length).foldLeft(List(x :: ll))((a, i) => (mixone(i, x, ll)) :: a)

    def mixone(
      i: Int,
      x: A,
      ll: List[A],
    ): List[A] =
      ll.slice(0, i) ::: (x :: ll.slice(i, ll.length))

    if (n > l.size) Nil
    else
      l match {
        case _ :: _ if n == 1 => l.map(List(_))
        case hd :: tl         => mixmany(hd, xvariations(tl)(n - 1)) ::: xvariations(tl)(n)
        case _                => Nil
      }
  }

  val allShapes: Seq[Shape] = {
    val minElements = pizzaConfig.minIngredientPerSlice * 2
    val maxElements = pizzaConfig.maxCellsPerSlice

    println(s"maxelements=$maxElements")

    val l = (0 until maxElements).toList
    val variants = xvariations(l)(2)
    val samePairs = l.zip(l).map { case (a, b) => List(a, b) }

    (variants ::: samePairs).map { l =>
      val List(a: Int, b: Int) = l
      Shape(a, b)
    }.filter { s =>
      val r = (s.a + 1) * (s.b + 1)
      r >= minElements && r <= maxElements
    }
  }

  def allSlices(pizza: Pizza)(upperLeft: Coords): Seq[Slice] = {
    ???
  }
}
