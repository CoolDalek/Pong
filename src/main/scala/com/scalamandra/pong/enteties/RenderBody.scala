package com.scalamandra.pong.enteties

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.scalamandra.libgdx.screens.View

trait RenderBody(
                  width: Float,
                  height: Float,
                  body: Body,
                  shapeRenderer: ShapeRenderer,
                ) extends View {
  private val halfWidth: Float = width / 2
  private val halfHeight: Float = height / 2

  protected def position(): Vector2 = {
    val center = body.getPosition
    center.x -= halfWidth
    center.y -= halfHeight
    center
  }

  override def view(): Unit = {
    val pos = position()
    shapeRenderer.rect(pos.x, pos.y, width, height)
  }

}