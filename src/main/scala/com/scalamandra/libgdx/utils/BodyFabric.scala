package com.scalamandra.libgdx.utils

import com.badlogic.gdx.physics.box2d.*
trait BodyFabric {

  protected def world: World

  protected def sharedBodyDef: BodyDef = BodyFabric.sharedBodyDef

  protected def sharedFixtureDef: FixtureDef = BodyFabric.sharedFixtureDef

  protected final def makeBody(centerX: Float,
                               centerY: Float,
                               shape: Shape,
                               `type`: BodyDef.BodyType,
                               density: Float,
                               friction: Float,
                               restitution: Float): Body =
    BodyFabric.makeBody(
      centerX,
      centerY,
      shape,
      world,
      `type`,
      density,
      friction,
      restitution,
      sharedBodyDef,
      sharedFixtureDef,
    )

  protected final def makeRectangle(x: Float,
                                    y: Float,
                                    width: Float,
                                    height: Float,
                                    `type`: BodyDef.BodyType,
                                    density: Float,
                                    friction: Float,
                                    restitution: Float): Body =
    BodyFabric.makeRectangle(
      x,
      y,
      width,
      height,
      world,
      `type`,
      density,
      friction,
      restitution,
      sharedBodyDef,
      sharedFixtureDef,
    )

}
object BodyFabric {

  private val sharedBodyDef = BodyDef()
  private val sharedFixtureDef = FixtureDef()

  def makeBody(
                centerX: Float,
                centerY: Float,
                shape: Shape,
                world: World,
                `type`: BodyDef.BodyType,
                density: Float,
                friction: Float,
                restitution: Float,
                bodyDef: BodyDef = sharedBodyDef,
                fixtureDef: FixtureDef = sharedFixtureDef,
              ): Body = {
    bodyDef.`type` = `type`
    bodyDef.position.set(centerX, centerY)
    val body = world.createBody(bodyDef)
    fixtureDef.shape = shape
    fixtureDef.density = density
    fixtureDef.friction = friction
    fixtureDef.restitution = restitution
    body.createFixture(fixtureDef)
    body
  }

  def makeRectangle(
                     x: Float,
                     y: Float,
                     width: Float,
                     height: Float,
                     world: World,
                     `type`: BodyDef.BodyType,
                     density: Float,
                     friction: Float,
                     restitution: Float,
                     bodyDef: BodyDef = sharedBodyDef,
                     fixtureDef: FixtureDef = sharedFixtureDef,
                   ): Body = {
    val halfWidth = width / 2
    val halfHeight = height / 2
    val centerX = x + halfWidth
    val centerY = y + halfHeight
    val rectangle = PolygonShape()
    rectangle.setAsBox(halfWidth, halfHeight)
    val body = makeBody(
      centerX,
      centerY,
      rectangle,
      world,
      `type`,
      density,
      friction,
      restitution,
      bodyDef,
      fixtureDef,
    )
    rectangle.dispose()
    body
  }

}