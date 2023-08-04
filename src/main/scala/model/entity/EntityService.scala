package model.entity

import indigo.{Point, Vector2}

import scala.collection.mutable.Set as MutableSet
import scala.compiletime.ops.int.*

private val dimensions = 2

/**
 * stores global coordinates of entities in single array
 */
class EntityService private(position: Array[Int], emptyIds: MutableSet[Int]) {
  def getPosition(id: Int): Point =
    Point(position(getXIndex(id)), position(getYIndex(id)))

  private def getSize: Int = position.length / 2

  private def getEmptyId: Int =
    emptyIds.head

  private def put(x: Int, y: Int, id: Int) =
    position(getXIndex(id)) = x
    position(getYIndex(id)) = y
    emptyIds.remove(id)

  private def getXIndex(id: Int) = id * dimensions

  private def getYIndex(id: Int) = getXIndex(id) + 1

  def create(x: Int, y: Int): Int =
    val id = getEmptyId
    put(x, y, id)
    id

  def move(id: Int, xShift: Int, yShift: Int): Unit =
    val oldValue = getPosition(id)
    position(getXIndex(id)) += xShift
    position(getYIndex(id)) += yShift

  def remove(id: Int): Unit =
    emptyIds += id
}

/*class EntityService(var position: Point) {
  def getPosition(id :Int): Point = position

  def create(x: Int, y: Int): Int =
    position = Point(x, y)
    1

  def move(id: Int, xShift: Double, yShift: Double): Unit = 1



}*/

object EntityService:
  def createEntityService(maxEntityCount: Int): EntityService =
    EntityService(new Array(maxEntityCount * dimensions), MutableSet.from(Range(0, maxEntityCount)))
    //EntityService(Point(1, 2))

