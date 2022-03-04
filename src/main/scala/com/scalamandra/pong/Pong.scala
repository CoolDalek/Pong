package com.scalamandra.pong

import com.badlogic.gdx.graphics.g2d.{BitmapFont, GlyphLayout, SpriteBatch}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.graphics.{Color, OrthographicCamera}
import com.scalamandra.pong.Pong.*
import com.scalamandra.pong.Pong.State.*
import com.scalamandra.pong.enteties.*
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.{Gdx, Input}
import com.scalamandra.libgdx.routing.Navigator
import com.scalamandra.libgdx.screens.{GameScreen, SimpleDraw, Text}
import com.scalamandra.libgdx.utils.{BodyFabric, Files, given}

class Pong(
            shape: ShapeRenderer,
            batch: SpriteBatch,
            bigFont: BitmapFont,
            smallFont: BitmapFont,
            val world: World,
          ) extends GameScreen with SimpleDraw with BodyFabric {

  private val playerHit = use(Files.sound("paddle_hit.wav"))
  private val wallHit = use(Files.sound("wall_hit.wav"))
  private val scoreIncrease = use(Files.sound("score.wav"))

  private val texts = Texts.make(
    screenWidth = screenWidth,
    screenHeight = screenHeight,
    batch = batch,
    bigFont = bigFont,
    smallFont = smallFont,
  )
  private val entities = Entities.make(
    screenWidth = screenWidth,
    screenHeight = screenHeight,
    world = world,
    shapeRenderer = shape,
    playerHit = playerHit,
    wallHit = wallHit,
    scoreIncrease = scoreIncrease,
  )

  override val camera: OrthographicCamera = OrthographicCamera()
  camera.setToOrtho(
    false,
    screenWidth,
    screenHeight,
  )

  object CollisionHandler extends ContactListener {

    override def beginContact(contact: Contact): Unit = {
      def userData(get: Contact => Fixture) = get(contact).getBody.getUserData
      val a = userData(_.getFixtureA)
      val b = userData(_.getFixtureB)

      def wallHit(wall: Wall): Unit = {

        def checkScore(): Unit = {
          var moveToState = Pause
          def checkScore(position: PlayerPosition): Unit =
            if(entities.player(position).getScore == maxScore) {
              texts.setWinner(position)
              moveToState = End
            }
          checkScore(Position.Left)
          checkScore(Position.Right)
          state = moveToState
        }

        wall.position match {
          case position: PlayerPosition =>
            val opposite = position.opposite
            val player = entities.player(opposite)
            player.increaseScore()
            val scoreView = texts.score(opposite)
            scoreView.setText(player.getScore.toString)
            checkScore()
          case _ =>
            wall.collide()
        }
      }

      (a, b) match {
        case (player: Player, _: Ball) =>
          player.collide()
        case (_: Ball, player: Player) =>
          player.collide()
        case (_: Ball, wall: Wall) =>
          wallHit(wall)
        case (wall: Wall, _: Ball) =>
          wallHit(wall)
        case (_, _) => ()
      }
    }

    override def endContact(contact: Contact): Unit = ()

    override def preSolve(contact: Contact, oldManifold: Manifold): Unit = ()

    override def postSolve(contact: Contact, impulse: ContactImpulse): Unit = ()

  }

  world.setContactListener(CollisionHandler)

  private var state: State = Start

  override def show(): Unit = ()

  given ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Filled

  override def view(): Unit = {
    ScreenUtils.clear(Color.BLACK)
    state match {
      case Start =>
        draw(batch) {
          texts.title.view()
          texts.start.view()
        }
      case InGame =>
        draw(shape) {
          shape.setColor(Color.WHITE)
          entities.view()
        }
        draw(batch) {
          texts.scoreView()
        }
      case End =>
        draw(batch) {
          texts.winner.view()
          texts.start.view()
        }
      case Pause =>
        draw(batch) {
          texts.scoreView()
          texts.continue.view()
        }
    }
  }

  def spacePressed: Boolean = Gdx.input.isKeyPressed(Input.Keys.SPACE)

  def resetState(): Unit = {
    entities.ball.reset()
    entities.ball.launch()
    state = InGame
  }

  def resetScore(): Unit = {
    entities.resetScore()
    texts.resetScore()
  }

  override def update(delta: Float): Unit =
    state match {
      case Start | Pause =>
        if(spacePressed) {
          resetState()
        }
      case InGame =>
        entities.update(delta)
        world.step(delta, 6, 2)
      case End =>
        if(spacePressed) {
          resetScore()
          resetState()
        }
    }

}
object Pong extends Navigator[Pong] {

  enum State {
    case InGame extends State
    case Start extends State
    case Pause extends State
    case End extends State
  }

  private inline val ballHeight = 8f
  private inline val ballWidth = 8f
  private inline val screenWidth = 256f
  private inline val screenHeight = 144f
  private inline val maxScore = 10
}