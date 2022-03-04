package com.scalamandra.pong.enteties

import com.badlogic.gdx.graphics.g2d.{BitmapFont, GlyphLayout, SpriteBatch}
import com.badlogic.gdx.math.Vector2
import Position.*
import com.scalamandra.libgdx.screens.Text

case class Texts(
                  winner: Text,
                  leftScore: Text,
                  rightScore: Text,
                  title: Text,
                  continue: Text,
                  start: Text,
                ) {

  def scoreView(): Unit = {
    leftScore.view()
    rightScore.view()
  }

  def setWinner(position: PlayerPosition): Unit =
    winner.setText(s"$position player win")

  def score(position: PlayerPosition): Text =
    position match {
      case Position.Left => leftScore
      case Position.Right => rightScore
    }

  def resetScore(): Unit = {
    def reset(score: Text): Unit = score.setText("0")
    reset(leftScore)
    reset(rightScore)
  }

}
object Texts {

  def make(screenWidth: Float,
           screenHeight: Float,
           batch: SpriteBatch,
           bigFont: BitmapFont,
           smallFont: BitmapFont): Texts = {
    val position = Vector2()
    val titleY = screenHeight / 8f * 7f
    val widthHalf = screenWidth / 2

    def textX(layout: GlyphLayout, offset: Float = widthHalf) = offset - layout.width / 2

    def score(offset: Float) = Text("0", bigFont, batch) { layout =>
      position.set(textX(layout, offset), titleY)
    }

    val title = Text("Pong", bigFont, batch) { layout =>
      position.set(textX(layout), titleY)
    }

    def pressTo(to: String) = Text(s"Press space to $to", smallFont, batch) { layout =>
      val y = titleY - title.layout.height - layout.height
      position.set(textX(layout), y)
    }

    val winner = Text("", bigFont, batch) { layout =>
      position.set(textX(layout), titleY)
    }
    val continue = pressTo("continue")
    val start = pressTo("start")
    val widthQuarter = widthHalf / 2f
    val leftScore = score(widthQuarter)
    val rightScore = score(widthQuarter * 3f)

    Texts(
      leftScore = leftScore,
      rightScore = rightScore,
      title = title,
      continue = continue,
      start = start,
      winner = winner,
    )
  }

}