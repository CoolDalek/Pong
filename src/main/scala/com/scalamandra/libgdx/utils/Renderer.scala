package com.scalamandra.libgdx.utils

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

trait Renderer[T] extends Scoped[T] {

  def setProjectionMatrix(self: T, projection: Matrix4): Unit

}
object Renderer extends Summoner[Renderer]
given [T <: Batch]: Renderer[T] = new Renderer[T] {

  override inline def setProjectionMatrix(self: T, projection: Matrix4): Unit =
    self.setProjectionMatrix(projection)

  override inline def begin(self: T): Unit =
    self.begin()

  override inline def end(self: T): Unit =
    self.end()

  override inline def scoped[R](self: T)(action: => R): R = {
    begin(self)
    val result = action
    end(self)
    result
  }

}
given (using shape: ShapeRenderer.ShapeType): Renderer[ShapeRenderer] = new Renderer[ShapeRenderer] {

  override inline def setProjectionMatrix(self: ShapeRenderer, projection: Matrix4): Unit =
    self.setProjectionMatrix(projection)

  override inline def begin(self: ShapeRenderer): Unit =
    self.begin(shape)

  override inline def end(self: ShapeRenderer): Unit =
    self.end()

  override inline def scoped[R](self: ShapeRenderer)(action: => R): R = {
    begin(self)
    val result = action
    end(self)
    result
  }

}
extension[T: Renderer](self: T) {

  inline def setProjectionMatrix(projection: Matrix4): Unit =
    Renderer[T].setProjectionMatrix(self, projection)

}