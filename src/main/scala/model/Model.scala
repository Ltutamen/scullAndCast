package model {

  import indigo.shared.constants.Key
  import indigo.{Batch, Point, Radians, Seconds}

  final case class Model(center: Point, sculls: Batch[Scull], wizzard: Wizzard) {
    def addDot(dot: Scull): Model =
      this.copy(sculls = dot :: sculls)

    def update(timeDelta: Seconds): Model =
      this.copy(sculls = sculls.map(_.update(timeDelta)))
  }

  object Model {
    def initial(center: Point): Model = Model(center, Batch.empty, Wizzard())
  }

  final case class Scull(orbitDistance: Int, angle: Radians) {
    def update(timeDelta: Seconds): Scull =
      this.copy(angle = angle + Radians.fromSeconds(timeDelta))
  }

  final case class Wizzard(position: Point = Point(5, 7)) {
    

  }
}
