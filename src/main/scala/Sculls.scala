import indigo.*

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame")
object HelloIndigo extends IndigoSandbox[Unit, Model] {

  private val magnification = 3
  private val scullAssetName = AssetName("scull")
  private val wizzardAssetName = AssetName("wizzard")
  private val model = Model.initial(Point.zero)

  val config: GameConfig =
    GameConfig.default.withMagnification(magnification)
      .withClearColor(RGBA.Indigo)


  val animations: Set[Animation] =
    Set()

  val assets: Set[AssetType] =
    Set(
      AssetType.Image(scullAssetName, AssetPath("assets/scull.png")),
      AssetType.Image(wizzardAssetName, AssetPath("assets/wizard-idle.png"))
    )

  val fonts: Set[FontInfo] =
    Set()

  val shaders: Set[Shader] =
    Set()

  def setup(
             assetCollection: AssetCollection,
             dice: Dice
           ): Outcome[Startup[Unit]] =
    Outcome(Startup.Success(()))

  def initialModel(startupData: Unit): Outcome[Model] =
    Outcome(Model.initial(viewportWithMag()))


  private def viewportWithMag(): Point = config.viewport.giveDimensions(magnification).center

  def updateModel(
                   context: FrameContext[Unit],
                   model: Model
                 ): GlobalEvent => Outcome[Model] = {
    case MouseEvent.Click(clickPoint) =>
      val adjustedPosition = clickPoint - model.center

      Outcome(
        model.addDot(
          Scull(
            Point.distanceBetween(model.center, clickPoint).toInt,
            Radians(
              Math.atan2(
                adjustedPosition.x.toDouble,
                adjustedPosition.y.toDouble
              )
            )
          )
        )
      )

    case FrameTick =>
      Outcome(model.update(context.delta))

    case _ =>
      Outcome(model)
  }

  def present(
               context: FrameContext[Unit],
               model: Model
             ): Outcome[SceneUpdateFragment] =
    Outcome(
      SceneUpdateFragment(
        Graphic(Rectangle(0, 0, 32, 32), 1, Material.Bitmap(wizzardAssetName)) ::
          drawSculls(model.center, model.dots)
      )
    )


  private def drawSculls(center: Point, dots: Batch[Scull]): Batch[Graphic[_]] =
    dots.map { dot =>
      val position = Point(
        x = (Math.sin(dot.angle.toDouble) * dot.orbitDistance + center.x).toInt,
        y = (Math.cos(dot.angle.toDouble) * dot.orbitDistance + center.y).toInt
      )

      Graphic(Rectangle(0, 0, 32, 32), 1, Material.Bitmap(scullAssetName))
        .moveTo(position)
    }
}

final case class Model(center: Point, dots: Batch[Scull]) {
  def addDot(dot: Scull): Model =
    this.copy(dots = dot :: dots)

  def update(timeDelta: Seconds): Model =
    this.copy(dots = dots.map(_.update(timeDelta)))
}

object Model {
  def initial(center: Point): Model = Model(center, Batch.empty)
}

final case class Scull(orbitDistance: Int, angle: Radians) {
  def update(timeDelta: Seconds): Scull =
    this.copy(angle = angle + Radians.fromSeconds(timeDelta))
}