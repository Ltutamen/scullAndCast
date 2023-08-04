package model.entity {

  import indigo.{Point, Radians, Seconds, Vector2}

  final case class Spell(id: Int, direction: Vector2) {
    private val speed = 0.01

    def update(timeDelta: Seconds): Unit =
      val shift = direction * speed
      Spell.move(id, shift)

  }

  object Spell {

    private val entityService = EntityService.createEntityService(128)

    def create(position: Point, radians: Vector2): Spell =
      val id = entityService.create(position.x, position.y)
      Spell(id, radians)

    def getPosition(id: Int): Point =
      entityService.getPosition(id)

    def move(id: Int, shift: Vector2): Unit =
      entityService.move(id, shift.x.toInt, shift.y.toInt)
      1
  }

}