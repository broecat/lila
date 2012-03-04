package lila.chess

import scala.math.{ min, max }

sealed case class Pos private (x: Int, y: Int, piotr: Char) {

  import Pos.posAt

  lazy val up: Option[Pos] = this ^ 1
  lazy val down: Option[Pos] = this v 1
  lazy val right: Option[Pos] = this > 1
  lazy val left: Option[Pos] = this < 1
  lazy val upLeft: Option[Pos] = up flatMap (_ left)
  lazy val upRight: Option[Pos] = up flatMap (_ right)
  lazy val downLeft: Option[Pos] = down flatMap (_ left)
  lazy val downRight: Option[Pos] = down flatMap (_ right)

  def ^(n: Int): Option[Pos] = posAt(x, y + n)
  def v(n: Int): Option[Pos] = posAt(x, y - n)
  def >(n: Int): Option[Pos] = posAt(x + n, y)
  def <(n: Int): Option[Pos] = posAt(x - n, y)
  def >|(stop: Pos ⇒ Boolean): List[Pos] = |<>|(stop, _.right)
  def |<(stop: Pos ⇒ Boolean): List[Pos] = |<>|(stop, _.left)
  def |<>|(stop: Pos ⇒ Boolean, dir: Direction): List[Pos] = dir(this) map { p ⇒
    p :: (if (stop(p)) Nil else p.|<>|(stop, dir))
  } getOrElse Nil

  def ?<(other: Pos) = x < other.x
  def ?>(other: Pos) = x > other.x
  def ?|(other: Pos) = x == other.x

  def <->(other: Pos): Iterable[Pos] =
    min(x, other.x) to max(x, other.x) map { posAt(_, y) } flatten

  lazy val file = Pos xToString x
  lazy val rank = y.toString
  lazy val key = file + rank

  override def toString = key
}

object Pos {

  def posAt(x: Int, y: Int): Option[Pos] = allCoords get (x, y)

  def posAt(key: String): Option[Pos] = allKeys get key

  def xToString(x: Int) = (96 + x).toChar.toString

  def piotr(c: Char): Option[Pos] = allPiotrs get c

  val A1 = Pos(1, 1, 'a')
  val B1 = Pos(2, 1, 'b')
  val C1 = Pos(3, 1, 'c')
  val D1 = Pos(4, 1, 'd')
  val E1 = Pos(5, 1, 'e')
  val F1 = Pos(6, 1, 'f')
  val G1 = Pos(7, 1, 'g')
  val H1 = Pos(8, 1, 'h')
  val A2 = Pos(1, 2, 'i')
  val B2 = Pos(2, 2, 'j')
  val C2 = Pos(3, 2, 'k')
  val D2 = Pos(4, 2, 'l')
  val E2 = Pos(5, 2, 'm')
  val F2 = Pos(6, 2, 'n')
  val G2 = Pos(7, 2, 'o')
  val H2 = Pos(8, 2, 'p')
  val A3 = Pos(1, 3, 'q')
  val B3 = Pos(2, 3, 'r')
  val C3 = Pos(3, 3, 's')
  val D3 = Pos(4, 3, 't')
  val E3 = Pos(5, 3, 'u')
  val F3 = Pos(6, 3, 'v')
  val G3 = Pos(7, 3, 'w')
  val H3 = Pos(8, 3, 'x')
  val A4 = Pos(1, 4, 'y')
  val B4 = Pos(2, 4, 'z')
  val C4 = Pos(3, 4, 'A')
  val D4 = Pos(4, 4, 'B')
  val E4 = Pos(5, 4, 'C')
  val F4 = Pos(6, 4, 'D')
  val G4 = Pos(7, 4, 'E')
  val H4 = Pos(8, 4, 'F')
  val A5 = Pos(1, 5, 'G')
  val B5 = Pos(2, 5, 'H')
  val C5 = Pos(3, 5, 'I')
  val D5 = Pos(4, 5, 'J')
  val E5 = Pos(5, 5, 'K')
  val F5 = Pos(6, 5, 'L')
  val G5 = Pos(7, 5, 'M')
  val H5 = Pos(8, 5, 'N')
  val A6 = Pos(1, 6, 'O')
  val B6 = Pos(2, 6, 'P')
  val C6 = Pos(3, 6, 'Q')
  val D6 = Pos(4, 6, 'R')
  val E6 = Pos(5, 6, 'S')
  val F6 = Pos(6, 6, 'T')
  val G6 = Pos(7, 6, 'U')
  val H6 = Pos(8, 6, 'V')
  val A7 = Pos(1, 7, 'W')
  val B7 = Pos(2, 7, 'X')
  val C7 = Pos(3, 7, 'Y')
  val D7 = Pos(4, 7, 'Z')
  val E7 = Pos(5, 7, '0')
  val F7 = Pos(6, 7, '1')
  val G7 = Pos(7, 7, '2')
  val H7 = Pos(8, 7, '3')
  val A8 = Pos(1, 8, '4')
  val B8 = Pos(2, 8, '5')
  val C8 = Pos(3, 8, '6')
  val D8 = Pos(4, 8, '7')
  val E8 = Pos(5, 8, '8')
  val F8 = Pos(6, 8, '9')
  val G8 = Pos(7, 8, '!')
  val H8 = Pos(8, 8, '?')

  val allKeys: Map[String, Pos] = Map("a1" -> A1, "a2" -> A2, "a3" -> A3, "a4" -> A4, "a5" -> A5, "a6" -> A6, "a7" -> A7, "a8" -> A8, "b1" -> B1, "b2" -> B2, "b3" -> B3, "b4" -> B4, "b5" -> B5, "b6" -> B6, "b7" -> B7, "b8" -> B8, "c1" -> C1, "c2" -> C2, "c3" -> C3, "c4" -> C4, "c5" -> C5, "c6" -> C6, "c7" -> C7, "c8" -> C8, "d1" -> D1, "d2" -> D2, "d3" -> D3, "d4" -> D4, "d5" -> D5, "d6" -> D6, "d7" -> D7, "d8" -> D8, "e1" -> E1, "e2" -> E2, "e3" -> E3, "e4" -> E4, "e5" -> E5, "e6" -> E6, "e7" -> E7, "e8" -> E8, "f1" -> F1, "f2" -> F2, "f3" -> F3, "f4" -> F4, "f5" -> F5, "f6" -> F6, "f7" -> F7, "f8" -> F8, "g1" -> G1, "g2" -> G2, "g3" -> G3, "g4" -> G4, "g5" -> G5, "g6" -> G6, "g7" -> G7, "g8" -> G8, "h1" -> H1, "h2" -> H2, "h3" -> H3, "h4" -> H4, "h5" -> H5, "h6" -> H6, "h7" -> H7, "h8" -> H8)

  val allCoords: Map[(Int, Int), Pos] = {
    for {
      x ← 1 to 8
      xString = xToString(x)
      y ← 1 to 8
      key = xString + y.toString
    } yield (x, y) -> allKeys(key)
  } toMap

  val allPiotrs: Map[Char, Pos] = all map { pos ⇒ (pos.piotr, pos) } toMap

  def all = allKeys.values
}
