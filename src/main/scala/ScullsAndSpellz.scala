import indigo.*
import model.entity.{EntityService, Spell}
import model.*

import scala.math.{acos, asin}
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame")
object ScullsAndSpellz extends IndigoSandbox[StartUpData, Model] {


  private val magnification = 1
  private val scullAssetName = AssetName("scull")
  private val crosshairAssetName = AssetName("crosshair")
  private val spellAssetName = AssetName("spell")

  val config: GameConfig =
    GameConfig.default
      //.withMagnification(magnification)
      .withClearColor(RGBA.Indigo)
      .withViewport(GameViewport.at720p)


  val animations: Set[Animation] =
    Set()

  val assets: Set[AssetType] =
    Set(
      AssetType.Image(scullAssetName, AssetPath("assets/scull.png")),
      Wizzard.asset,
      AssetType.Image(crosshairAssetName, AssetPath("assets/staff-crosshair.png")),
      AssetType.Image(spellAssetName, AssetPath("assets/spell.png"))
    )

  val fonts: Set[FontInfo] =
    Set()

  val shaders: Set[Shader] =
    Set()

  def setup(assetCollection: AssetCollection, dice: Dice): Outcome[Startup[StartUpData]] = Outcome(Startup.Success(StartUpData(dice)))

  def initialModel(startupData: StartUpData): Outcome[Model] =
    Outcome(Model.initial(startupData.dice, viewportWithMag()))


  private def viewportWithMag(): Point = config.viewport.giveDimensions(magnification).center

  def updateModel(context: FrameContext[StartUpData], model: Model): GlobalEvent => Outcome[Model] = {
    case MouseEvent.Move(position) => Outcome(ModelUpdater.onMouseMove(model, position))

    case MouseEvent.Click(clickPoint) => Outcome(ModelUpdater.castSpell(model, clickPoint))

    case FrameTick =>
      Outcome(model.update(context.delta))

    case KeyboardEvent.KeyDown(keyCode) =>
      Outcome(ModelUpdater.onKeyDown(model, keyCode))

    case _ =>
      Outcome(model)
  }

  def present(context: FrameContext[StartUpData], model: Model): Outcome[SceneUpdateFragment] =
    Outcome(
      SceneUpdateFragment(
        drawWizzard(model.wizzard)
          ++ drawSculls(model.center, model.sculls)
          ++ drawSpells(model.spells)
      )
    )


  private def drawWizzard(wizzard: Wizzard): Batch[Graphic[_]] =
    val wizzardBody = Graphic(Rectangle(0, 0, 32, 32), 1, Material.Bitmap(Wizzard.assetName))
      .moveTo(wizzard.position)

    //  val wizzardStaff = ;
    val wizzardCrosshair = Graphic(Rectangle(0, 0, 32, 32), 1, Material.Bitmap(crosshairAssetName))
      .moveTo(wizzard.aim)

    Batch.apply(wizzardBody, wizzardCrosshair)

  private def drawSpells(spells: Batch[Spell]): Batch[Graphic[_]] =
    spells.map(spell => {
      val pos = Spell.getPosition(spell.id)
      val rotate: Radians = Radians(
        Math.atan2(spell.direction.x, spell.direction.y))


      Graphic(Rectangle(0, 0, 16, 16), 1, Material.Bitmap(spellAssetName))
        .rotateBy(rotate)
        .moveTo(pos)
    })


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

class StartUpData(val dice: Dice) {}


