package com.scalamandra.libgdx.screens

import com.badlogic.gdx.graphics.g2d.{BitmapFont, GlyphLayout, SpriteBatch}
import com.badlogic.gdx.math.Vector2
import TextAlign.*

class Text(
            var x: Float,
            var y: Float,
            val layout: GlyphLayout,
            font: BitmapFont,
            batch: SpriteBatch,
            align: TextAlign,
          ) extends View {

  override def view(): Unit = font.draw(batch, layout, x, y)

  def setText(text: String): Unit = {
    val prevWidth = layout.width
    layout.setText(font, text)
    val currWidth = layout.width
    val diff = currWidth - prevWidth
    align match {
      case Right =>
        x -= diff
      case Center =>
        x -= diff / 2
      case Left => ()
    }
  }

}
object Text {

  inline def apply(x: Float,
                   y: Float,
                   layout: GlyphLayout,
                   font: BitmapFont,
                   batch: SpriteBatch,
                   align: TextAlign): Text = {
    new Text(x, y, layout, font, batch, align)
  }

  inline def apply(text: String,
                   font: BitmapFont,
                   batch: SpriteBatch,
                   align: TextAlign = Center)
                  (position: GlyphLayout => Vector2): Text = {
    val layout = GlyphLayout(font, text)
    val coordinates = position(layout)
    Text(
      coordinates.x,
      coordinates.y,
      layout,
      font,
      batch,
      align
    )
  }
}