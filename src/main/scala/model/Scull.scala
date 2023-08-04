package model {

  import indigo.{Radians, Seconds}

  final case class Scull(orbitDistance: Int, angle: Radians) {
    def update(timeDelta: Seconds): Scull =
      this.copy(angle = angle + Radians.fromSeconds(timeDelta))
  }
}