package com.scalamandra.pong

import com.badlogic.gdx.*
import com.badlogic.gdx.audio.*
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.{Rectangle, Vector2, Vector3}
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.physics.box2d.World
import com.scalamandra.libgdx.routing.GameRouter
import com.scalamandra.libgdx.utils.Files
import scala.annotation.threadUnsafe

class App extends GameRouter {
  @threadUnsafe private lazy val shape = use(ShapeRenderer())
  @threadUnsafe private lazy val batch = use(SpriteBatch())
  @threadUnsafe private lazy val bigFont = use(Files.ftFont("font.ttf"))
  @threadUnsafe private lazy val smallFont = use(Files.ftFont("font.ttf", 8))
  @threadUnsafe private lazy val world = use(World(Vector2(0, 0), true))

  override def create(): Unit = {
    addScreen(Pong) {
      Pong(
        shape = shape,
        batch = batch,
        bigFont = bigFont,
        smallFont = smallFont,
        world = world,
      )
    }
    moveTo(Pong)
  }
  
}