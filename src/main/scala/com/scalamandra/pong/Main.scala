package com.scalamandra.pong

import com.badlogic.gdx.backends.lwjgl3.*

object Main {

  def main(args: Array[String]): Unit = {
    val config = Lwjgl3ApplicationConfiguration()
    config.setTitle("Pong")
    config.setResizable(false)
    config.setFullscreenMode(
      Lwjgl3ApplicationConfiguration.getDisplayMode
    )
    Lwjgl3Application(new App, config)
  }

}