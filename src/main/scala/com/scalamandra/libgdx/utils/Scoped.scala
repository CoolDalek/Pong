package com.scalamandra.libgdx.utils

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.DelayedRemovalArray

trait Scoped[T] {

  def begin(self: T): Unit

  def end(self: T): Unit

  def scoped[R](self: T)(action: => R): R

}

object Scoped extends Summoner[Scoped]

inline def withScope[T: Scoped, R](self: T)(action: => R): R =
  self.scoped(action)

given [T]: Scoped[DelayedRemovalArray[T]] = new Scoped[DelayedRemovalArray[T]] {

  override inline def begin(self: DelayedRemovalArray[T]): Unit =
    self.begin()

  override inline def end(self: DelayedRemovalArray[T]): Unit =
    self.end()

  override inline def scoped[R](self: DelayedRemovalArray[T])(action: => R): R = {
    begin(self)
    val result = action
    end(self)
    result
  }

}

extension[T: Scoped](self: T) {

  inline def begin(): Unit = Scoped[T].begin(self)

  inline def end(): Unit = Scoped[T].end(self)

  inline def scoped[R](action: => R) = Scoped[T].scoped(self)(action)

}