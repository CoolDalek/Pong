package com.scalamandra.libgdx.routing

import com.badlogic.gdx.Screen

case class NoSuchScreen[T <: Screen](route: Route[T]) extends RuntimeException(s"No such screen, route: $route")