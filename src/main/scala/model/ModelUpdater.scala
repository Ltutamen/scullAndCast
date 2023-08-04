package model {

  import indigo.Point
  import indigo.shared.Outcome
  import indigo.shared.constants.Key
  import indigo.shared.datatypes.{Radians, Vector2}
  import model.*
  import model.entity.Spell

  object ModelUpdater:

    def onKeyDown(model: Model, keyPress: Key): Model =
      keyPress match
        case Key.SPACE => createScull(model: Model)
        case Key.KEY_W | Key.KEY_S | Key.KEY_A | Key.KEY_D => moveWizard(model, keyPress)
        case _ => model

    def createScull(model: Model): Model =
      val oldSculls = model.sculls
      val dice = model.dice
      model.addScull(Scull(dice.roll(260), Radians(dice.rollDouble)))

    def moveWizard(model: Model, keyPress: Key): Model =
      val oldWizzard = model.wizzard
      val shift = keyPress match
        case Key.KEY_W => Point(0, -10)
        case Key.KEY_S => Point(0, 10)
        case Key.KEY_A => Point(-10, 0)
        case Key.KEY_D => Point(10, 0)
        case _ => Point.zero;
      model.copy(wizzard = oldWizzard.copy(position = oldWizzard.position + shift))

    def onMouseMove(model: Model, mousePosition: Point): Model =
      val oldWizzard = model.wizzard
      model.copy(wizzard = oldWizzard.copy(aim = mousePosition))


    def castSpell(model: Model, clickPoint: Point): Model =

      val directionPoint = clickPoint - model.wizzard.position
      val direction: Vector2 = Vector2(directionPoint.x, directionPoint.y)

      val spellCastPosition = Wizzard.getCenter(model)

      model.addSpell(Spell.create(spellCastPosition, direction))
}