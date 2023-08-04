import indigo.shared.assets.{AssetPath, AssetType}
import indigo.{AssetName, Graphic, Material, Point, Rectangle}
import indigo.shared.collections.Batch
import indigo.shared.datatypes.Radians
import indigo.shared.scenegraph.SceneUpdateFragment
import model.{Model, Scull, Wizzard}
import model.entity.Spell

object DrawSerivce {

  private val scullAssetName = AssetName("scull")
  private val crosshairAssetName = AssetName("crosshair")
  private val spellAssetName = AssetName("spell")

  def assets(): Set[AssetType] = Set(
    AssetType.Image(scullAssetName, AssetPath("assets/scull.png")),
    Wizzard.asset,
    AssetType.Image(crosshairAssetName, AssetPath("assets/staff-crosshair.png")),
    AssetType.Image(spellAssetName, AssetPath("assets/spell.png"))
  )

  def sceneUpdateFragment(model: Model): SceneUpdateFragment =
    SceneUpdateFragment(
      drawWizzard(model.wizzard)
        ++ drawSculls(model.center, model.sculls)
        ++ drawSpells(model.spells)
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
