package com.scalamandra.libgdx.utils

trait Summoner[Typeclass[_]] {
  
  final inline def apply[T: Typeclass]: Typeclass[T] = summon[Typeclass[T]]

}