package com.scalamandra.libgdx.utils

import com.badlogic.gdx.utils.Array as Unaliased

import scala.reflect._

type GdxArray[T] = Unaliased[T]
type GdxIterator[T] = Unaliased.ArrayIterator[T]
type GdxIterable[T] = Unaliased.ArrayIterable[T]
object GdxArray {

  private inline def fix[T: ClassTag](array: IArray[T]): Array[Object & T] =
    array.asInstanceOf[Array[Object & T]]

  inline def apply[T: ClassTag](capacity: Int = 16, ordered: Boolean = false): GdxArray[T] =
    Unaliased[T](ordered, capacity, classTag[T].runtimeClass)

  inline def apply[T: ClassTag](array: IArray[T]): GdxArray[T] =
    apply[T](array, 0)

  inline def apply[T: ClassTag](array: IArray[T], start: Int): GdxArray[T] =
    apply[T](array, start, array.length)

  inline def apply[T: ClassTag](array: IArray[T], start: Int, count: Int): GdxArray[T] =
    apply[T](array, start, count, false)

  inline def apply[T: ClassTag](array: IArray[T], start: Int, count: Int, ordered: Boolean): GdxArray[T] =
    Unaliased[T](ordered, fix(array), start, count)

}
extension [T](self: GdxArray[T]) {

  inline def foreach(body: T => Unit): Unit = {
    val iterator = self.iterator
    while(iterator.hasNext) {
      body(iterator.next)
    }
  }

  inline def foreachRemove(body: (T, RemoveHandler[T]) => Unit): Unit = {
    val iterator = self.iterator
    while(iterator.hasNext) {
      body(iterator.next, RemoveHandler(iterator))
    }
  }

}