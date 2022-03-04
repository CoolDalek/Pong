package com.scalamandra.libgdx.utils

import com.badlogic.gdx.audio.{Music, Sound}
import com.badlogic.gdx.{Audio, Gdx}
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter

object Files {

  inline def internal(name: String): FileHandle = Gdx.files.classpath(s"assets/$name")

  inline def texture(name: String): Texture = Texture(internal(name))

  private inline def audio[T](name: String)(get: Audio => FileHandle => T): T =
    get(Gdx.audio)(internal(name))

  inline def sound(name: String): Sound = audio(name)(_.newSound)

  inline def music(name: String): Music = audio(name)(_.newMusic)
  
  inline def ftFont(name: String, size: Int): BitmapFont = {
    val params = FreeTypeFontParameter()
    params.size = size
    ftFont(name, params)
  }

  inline def ftFont(name: String, params: FreeTypeFontParameter = FreeTypeFontParameter()): BitmapFont = {
    val generator = new FreeTypeFontGenerator(internal("font.ttf"))
    val font = generator.generateFont(params)
    generator.dispose()
    font
  }

}