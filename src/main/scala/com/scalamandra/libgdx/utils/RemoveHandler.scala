package com.scalamandra.libgdx.utils

opaque type RemoveHandler[T] = GdxIterator[T]
object RemoveHandler {
  inline def apply[T](iterator: GdxIterator[T]): RemoveHandler[T] = iterator
}
extension [T](self: RemoveHandler[T]) {
  
  inline def apply(): Unit = self.remove()
  
}