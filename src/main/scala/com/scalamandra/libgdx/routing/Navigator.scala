package com.scalamandra.libgdx.routing

import com.badlogic.gdx.Screen

trait Navigator[T <: Screen] {
  
  final val route = Route[T]

}