package com.scalamandra.pong.enteties

import com.badlogic.gdx.audio.Sound

trait SoundOnCollision(sound: Sound) {
  
  def collide(): Unit = sound.play()

}