package com.scalamandra.libgdx.routing

import com.badlogic.gdx.{ApplicationListener, Game, Gdx, Screen}
import com.badlogic.gdx.utils.{Disposable, IntMap, TimeUtils}
import com.scalamandra.libgdx.utils.Manageable

import scala.collection.mutable

abstract class GameRouter extends ApplicationListener with Router with Manageable {
  private var current: Screen | Null = null
  private val screens = IntMap[() => Screen]()

  private inline def withScreen(action: Screen => Unit): Unit =
    if(current != null) action(current)

  use {
    new Disposable {
      override def dispose(): Unit = withScreen(_.dispose())
    }
  }

  protected def addScreen[T <: Screen](route: Route[T])(factory: => T): Unit =
    screens.put(route.internal, () => factory)
    
  protected def addScreen[T <: Screen, R <: Navigator[T]](navigator: R)(factory: => T): Unit =
    addScreen(navigator.route)(factory)

  override def moveTo[T <: Screen](route: Route[T]): Unit =
    screens.get(route.internal) match {
      case null =>
        throw NoSuchScreen(route)
      case factory =>
        setScreen(factory())
    }

  def getScreen: Screen | Null = current

  def setScreen(screen: Screen | Null): Unit = {
    withScreen { s =>
      s.hide()
      s.dispose()
    }
    current = screen
    withScreen { s =>
      s.show()
      s.resize(
        Gdx.graphics.getWidth,
        Gdx.graphics.getHeight,
      )
    }
  }

  override def resize(width: Int, height: Int): Unit =
    withScreen(_.resize(width, height))

  override def render(): Unit =
    withScreen(_.render(Gdx.graphics.getDeltaTime))

  override def pause(): Unit =
    withScreen(_.pause)

  override def resume(): Unit =
    withScreen(_.resume)
}