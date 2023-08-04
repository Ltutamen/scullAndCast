package model {

  import indigo.shared.constants.Key
  import indigo.shared.dice.Dice
  import indigo.{Batch, Point, Radians, Seconds}
  import model.entity.Spell

  final case class Model(dice: Dice, center: Point, sculls: Batch[Scull], wizzard: Wizzard, spells: Batch[Spell]) {
    def addScull(scull: Scull): Model =
      this.copy(sculls = scull :: sculls)

    def addSpell(spell: Spell): Model =
      this.copy(spells = spell :: spells)

    def update(timeDelta: Seconds): Model =
      spells.map(_.update(timeDelta))

      this.copy(
        sculls = sculls.map(_.update(timeDelta)))

  }

  object Model {
    def initial(dice: Dice, center: Point): Model = Model(dice, center, Batch.empty, Wizzard(aim = center), Batch.empty)
  }
}
