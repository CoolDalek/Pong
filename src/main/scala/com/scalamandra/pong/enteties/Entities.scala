package com.scalamandra.pong.enteties

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.physics.box2d.*
import Position.*
import com.scalamandra.libgdx.screens.{Update, View}

case class Entities(
                     leftPlayer: Player,
                     rightPlayer: Player,
                     ball: Ball,
                   ) extends View with Update[Unit] {

  def player(position: PlayerPosition): Player =
    position match {
      case Left =>
        leftPlayer
      case Right =>
        rightPlayer
    }

  def resetScore(): Unit = {
    leftPlayer.resetScore()
    rightPlayer.resetScore()
  }

  override def view(): Unit = {
    leftPlayer.view()
    rightPlayer.view()
    ball.view()
  }

  override def update(delta: Float): Unit = {
    leftPlayer.update(delta)
    rightPlayer.update(delta)
  }

}
object Entities {
  
  def make(screenWidth: Float,
           screenHeight: Float,
           world: World,
           shapeRenderer: ShapeRenderer,
           playerHit: Sound,
           wallHit: Sound,
           scoreIncrease: Sound): Entities = {
    def makePlayer(position: PlayerPosition) = Player.make(
      world = world,
      screenWidth = screenWidth,
      screenHeight = screenHeight,
      position = position,
      shapeRenderer = shapeRenderer,
      playerHit,
      scoreIncrease,
    )
    val leftPlayer = makePlayer(Left)
    val rightPlayer = makePlayer(Right)
    val ball = Ball.make(world, screenWidth, screenHeight, shapeRenderer)
    Wall.makeAll(world, screenWidth, screenHeight, wallHit)
    Entities(leftPlayer, rightPlayer, ball)
  }
  
}