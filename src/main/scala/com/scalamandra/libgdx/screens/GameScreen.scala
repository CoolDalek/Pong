package com.scalamandra.libgdx.screens

import com.badlogic.gdx.Screen
import com.badlogic.gdx.utils.Disposable
import com.scalamandra.libgdx.utils.{*, given}

abstract class GameScreen extends Screen with UpdatableView[Unit] with Manageable {

  override def render(delta: Float): Unit = {
    view()
    update(delta)
  }

  override def resize(width: Int, height: Int): Unit = ()

  override def pause(): Unit = ()

  override def resume(): Unit = ()

  override def hide(): Unit = ()

}