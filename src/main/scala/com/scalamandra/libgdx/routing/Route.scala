package com.scalamandra.libgdx.routing

import java.util.concurrent.atomic.AtomicInteger

opaque type Route[T] = Int
object Route {
  private val counter = AtomicInteger(0)
  private[routing] def apply[T]: Route[T] = counter.incrementAndGet()
}
extension[T] (self: Route[T]) {

  private[routing] inline def internal: Int = self
  
}