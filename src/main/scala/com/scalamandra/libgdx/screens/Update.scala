package com.scalamandra.libgdx.screens

trait Update[T] {
  
  def update(delta: Float): T

}