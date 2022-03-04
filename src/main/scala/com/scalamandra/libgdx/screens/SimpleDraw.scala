package com.scalamandra.libgdx.screens

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.OrthographicCamera
import com.scalamandra.libgdx.utils.{*, given}

trait SimpleDraw {
  this: Screen =>
  
  protected def camera: OrthographicCamera
  
  protected inline final def draw[T: Renderer](via: T)(drawAction: => Unit): Unit = {
    camera.update()
    via.setProjectionMatrix(camera.combined)
    via.scoped {
      drawAction
    }
  }

}