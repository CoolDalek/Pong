package com.scalamandra.pong.enteties

import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.physics.box2d.*
import com.scalamandra.libgdx.screens.Update
import com.scalamandra.libgdx.utils.BodyFabric
import com.scalamandra.pong.enteties.Player.*

class Player(
              screenWidth: Float,
              screenHeight: Float,
              body: Body,
              shapeRenderer: ShapeRenderer,
              scoreSound: Sound,
              collisionSound: Sound,
              upKey: Int,
              downKey: Int,
            )
  extends RenderBody(playerWidth, playerHeight, body, shapeRenderer)
    with Update[Unit] with SoundOnCollision(collisionSound) {

  private var score = 0

  def increaseScore(): Unit = {
    scoreSound.play()
    score += 1
  }

  def resetScore(): Unit = score = 0
  
  def getScore: Int = score

  private val velocity = 48

  override def update(delta: Float): Unit = {

    def move(sign: Float): Unit = {
      val center = body.getPosition
      val top = center.y + halfHeight
      val bottom = center.y - halfHeight
      if((sign > 0 && top < screenHeight) || (bottom > 0 && sign < 0)) {
        center.add(0f, sign * velocity * delta)
        body.setTransform(center, 0)
      }
    }

    if(Gdx.input.isKeyPressed(upKey)) move(1f)

    if(Gdx.input.isKeyPressed(downKey)) move(-1f)
  }

}
object Player {
  import Position.*

  def make(world: World,
           screenWidth: Float,
           screenHeight: Float,
           position: PlayerPosition,
           shapeRenderer: ShapeRenderer,
           scoreSound: Sound,
           collisionSound: Sound): Player = {
    val x = {
      val quarter = screenWidth / 8
      val rawX = if(position == Left) quarter else quarter * 7
      rawX - halfWidth
    }
    val y = screenHeight / 2 - halfHeight
    val body = BodyFabric.makeRectangle(
      x = x,
      y = y,
      width = playerWidth,
      height = playerHeight,
      world = world,
      `type` = BodyDef.BodyType.KinematicBody,
      density = 1f,
      friction = 0.1f,
      restitution = 0f,
    )
    val upKey = position match {
      case Left =>
        Input.Keys.W
      case Right =>
        Input.Keys.UP
    }
    val downKey = position match {
      case Left =>
        Input.Keys.S
      case Right =>
        Input.Keys.DOWN
    }
    val player = Player(
      screenWidth = screenWidth,
      screenHeight = screenHeight,
      body = body,
      shapeRenderer = shapeRenderer,
      scoreSound = scoreSound,
      collisionSound = collisionSound,
      upKey = upKey,
      downKey = downKey,
    )
    body.setUserData(player)
    player
  }

  private inline val playerHeight = 32f
  private inline val halfHeight = playerHeight / 2
  private inline val playerWidth = 4f
  private inline val halfWidth = playerWidth / 2

}