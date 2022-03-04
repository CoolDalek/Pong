package com.scalamandra.pong.enteties

sealed trait Position
sealed trait WallPosition extends Position
sealed trait PlayerPosition extends Position {
  def opposite: PlayerPosition
}
object Position {
  case object Top extends WallPosition
  case object Bottom extends WallPosition
  case object Left extends WallPosition with PlayerPosition {
    override def opposite: PlayerPosition = Right
  }
  case object Right extends WallPosition with PlayerPosition {
    override def opposite: PlayerPosition = Left
  }
}