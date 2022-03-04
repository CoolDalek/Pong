package com.scalamandra.pong.enteties

import Wall.*
import com.badlogic.gdx.physics.box2d.{BodyDef, World}
import com.badlogic.gdx.audio.Sound
import com.scalamandra.libgdx.utils.BodyFabric

class Wall(val position: WallPosition, collisionSound: Sound) extends SoundOnCollision(collisionSound)
object Wall {
  import Position.*

  def makeAll(world: World,
              screenWidth: Float,
              screenHeight: Float,
              collisionSound: Sound): Unit = {
    def makeWall(position: WallPosition) = make(world, position, screenWidth, screenHeight, collisionSound)
    IArray(Top, Right, Bottom, Left).foreach(makeWall)
  }

  def make(world: World,
           position: WallPosition,
           screenWidth: Float,
           screenHeight: Float,
           collisionSound: Sound): Wall = {
    def makeWall(x: Float, y: Float, width: Float, height: Float) = {
      val wall = Wall(position, collisionSound)
      BodyFabric.makeRectangle(
        x = x,
        y = y,
        width = width,
        height = height,
        world = world,
        `type` = BodyDef.BodyType.StaticBody,
        density = 1f,
        friction = 0f,
        restitution = 0f,
      ).setUserData(wall)
      wall
    }

    position match {
      case Top =>
        makeWall(0f, screenHeight, screenWidth, 0.1f)
      case Right =>
        makeWall(screenWidth, 0f, 0.1f, screenHeight)
      case Bottom =>
        makeWall(0f, 0f, screenWidth, 0.1f)
      case Left =>
        makeWall(0f, 0f, 0.1f, screenHeight)
    }
  }

}