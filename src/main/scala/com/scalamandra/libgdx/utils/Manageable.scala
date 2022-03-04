package com.scalamandra.libgdx.utils

import com.badlogic.gdx.utils.Disposable

trait Manageable extends Disposable {

  private val disposables: GdxArray[Disposable] = GdxArray[Disposable]()

  protected def use[T <: Disposable](resource: T): T = {
    disposables.add(resource)
    resource
  }

  protected def useAll(resources: Disposable*): Unit =
    disposables.addAll(resources: _*)

  def dispose(): Unit = disposables.foreach(_.dispose)

}