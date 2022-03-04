package com.scalamandra.libgdx.routing

import com.badlogic.gdx.{Gdx, Screen}

trait Router {

  def moveTo[T <: Screen](route: Route[T]): Unit
  
  final def moveTo[T <: Screen, R <: Navigator[T]](navigator: R): Unit =
    moveTo(navigator.route)

}