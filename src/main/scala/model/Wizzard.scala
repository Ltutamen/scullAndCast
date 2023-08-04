package model {

  import indigo.shared.assets.AssetName
  import indigo.shared.assets.AssetType.Image
  import indigo.{AssetPath, AssetType, Point}

  final case class Wizzard(position: Point = Point(5, 7), aim: Point = Point.zero) {
  }

  object Wizzard {

    val assetName: AssetName = AssetName("wizzard")
    val asset: Image = AssetType.Image(assetName, AssetPath("assets/wizard-idle.png"))

    def getCenter(model: Model): Point =
      model.wizzard.position + Point(16, 16)
  }
}