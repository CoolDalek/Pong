package com.scalamandra.pong.enteties

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.scalamandra.libgdx.utils.BodyFabric
import com.scalamandra.pong.enteties.Ball.*

class Ball(
            originX: Float,
            originY: Float,
            body: Body,
            shapeRenderer: ShapeRenderer
          ) extends RenderBody(ballWidth, ballHeight, body, shapeRenderer) {
  import scala.util.Random.*
  import scala.math.*

  def launch(): Unit = {
    val x = if(nextBoolean()) {
      between(-32d, -24d)
    } else between(24.001, 32d)
    val y = {
      val pos = sqrt(1024 - pow(x, 2))
      if(nextBoolean()) -pos else pos
    }
    body.setLinearVelocity(x.toFloat, y.toFloat)
  }

  def reset(): Unit = {
    body.setTransform(originX, originY, 0)
    body.setAngularVelocity(0)
    body.setLinearVelocity(0, 0)
  }

}
object Ball {

  def make(world: World,
           screenWidth: Float,
           screenHeight: Float,
           shapeRenderer: ShapeRenderer): Ball = {
    val x = screenWidth / 2 - halfWidth
    val y = screenHeight / 2 - halfHeight
    val body = BodyFabric.makeRectangle(
      x = x,
      y = y,
      width = ballWidth,
      height = ballHeight,
      world = world,
      `type` = BodyDef.BodyType.DynamicBody,
      density = 0.5f,
      friction = 0.1f,
      restitution = 1.25f,
    )
    val ball = Ball(x, y, body, shapeRenderer)
    body.setUserData(ball)
    body.setFixedRotation(true)
    ball
  }

  private inline val ballHeight = 8f
  private inline val halfHeight = ballHeight / 2
  private inline val ballWidth = 8f
  private inline val halfWidth = ballWidth / 2

}