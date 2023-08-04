import indigo.*
import model.entity.{EntityService, Spell}
import model.*

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame")
object ScullsAndSpellz extends IndigoSandbox[StartUpData, Model] {


  private val magnification = 1

  val config: GameConfig =
    GameConfig.default
      //.withMagnification(magnification)
      .withClearColor(RGBA.Indigo)
      .withViewport(GameViewport.at720p)


  val animations: Set[Animation] =
    Set()

  val assets: Set[AssetType] = DrawSerivce.assets()

  val fonts: Set[FontInfo] =
    Set()

  val shaders: Set[Shader] =
    Set()

  def setup(assetCollection: AssetCollection, dice: Dice): Outcome[Startup[StartUpData]] = Outcome(Startup.Success(StartUpData(dice)))

  def initialModel(startupData: StartUpData): Outcome[Model] =
    Outcome(Model.initial(startupData.dice, viewportWithMag()))


  private def viewportWithMag(): Point = config.viewport.giveDimensions(magnification).center

  def updateModel(context: FrameContext[StartUpData], model: Model): GlobalEvent => Outcome[Model] = {
    case MouseEvent.Move(position) => Outcome(ModelService.onMouseMove(model, position))

    case MouseEvent.Click(clickPoint) => Outcome(ModelService.castSpell(model, clickPoint))

    case FrameTick =>
      Outcome(model.update(context.delta))

    case KeyboardEvent.KeyDown(keyCode) =>
      Outcome(ModelService.onKeyDown(model, keyCode))

    case _ =>
      Outcome(model)
  }

  def present(context: FrameContext[StartUpData], model: Model): Outcome[SceneUpdateFragment] =
    Outcome(
      DrawSerivce.sceneUpdateFragment(model))
  
}

class StartUpData(val dice: Dice) {}


