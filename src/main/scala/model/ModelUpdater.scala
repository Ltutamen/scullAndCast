package model {

  import indigo.Point
  import indigo.shared.Outcome
  import indigo.shared.constants.Key
  import model._

  object ModelUpdater:

    def onKeyDown(model: Model, keyPress: Key): Model =
      val oldWizzard = model.wizzard
      val shift = keyPress match
        case Key.KEY_W => Point(0, -10)
        case Key.KEY_S => Point(0, 10)
        case Key.KEY_A => Point(-10, 0)
        case Key.KEY_D => Point(10, 0)
        case _ => Point.zero;
      model.copy(wizzard = oldWizzard.copy(position = oldWizzard.position + shift))
}