package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkContext, util}

import scala.collection.mutable.ArrayBuffer

object SuperheroDegreeOfSeparation {
  var hitCounter: Option[util.LongAccumulator] = None

  val startHeroId: Int = 5306
  val targetHeroID: Int = 14

  type BFSData = (Array[Int], Int, String)
  type BFSNode = (Int, BFSData)

  def convertToBFS(line: String): BFSNode = {
    val fields = line.split("\\s+")

    val heroId = fields(0).toInt

    var connections: ArrayBuffer[Int] = ArrayBuffer()

    for (connection <- 1 until fields.length) {
      connections += fields(connection).toInt
    }

    var distance: Int = 9999
    var color: String = "WHITE"

    if (heroId == startHeroId) {
      distance = 0
      color = "GRAY"
    }

    (heroId, (connections.toArray, distance, color))
  }


  /** Expands a BFSNode into this node and its children */
  def bfsMap(node: BFSNode): Array[BFSNode] = {

    // Extract data from the BFSNode
    val characterID: Int = node._1
    val data: BFSData = node._2

    val connections: Array[Int] = data._1
    val distance: Int = data._2
    var color: String = data._3

    // This is called from flatMap, so we return an array
    // of potentially many BFSNodes to add to our new RDD
    var results: ArrayBuffer[BFSNode] = ArrayBuffer()

    // Gray nodes are flagged for expansion, and create new
    // gray nodes for each connection
    if (color == "GRAY") {
      for (connection <- connections) {
        val newCharacterID = connection
        val newDistance = distance + 1
        val newColor = "GRAY"

        // Have we stumbled across the character we're looking for?
        // If so increment our accumulator so the driver script knows.
        if (targetHeroID == connection) {
          if (hitCounter.isDefined) {
            hitCounter.get.add(1)
          }
        }

        // Create our new Gray node for this connection and add it to the results
        val newEntry: BFSNode = (newCharacterID, (Array(), newDistance, newColor))
        results += newEntry
      }

      // Color this node as black, indicating it has been processed already.
      color = "BLACK"
    }

    // Add the original node back in, so its connections can get merged with
    // the gray nodes in the reducer.
    val thisEntry: BFSNode = (characterID, (connections, distance, color))
    results += thisEntry

    results.toArray
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "SuperheroDegreeOfSeparation")

    val lines = sc.textFile("./data/Marvel-graph.txt")

    var heroes = lines.map(convertToBFS)

    hitCounter = Some(sc.longAccumulator("Hit Counter"))

    for (idx <- 1 to 10) {
      println("Running BFS Iteration # " + idx)

      val mapped = heroes.flatMap(bfsMap)



      // Note that mapped.count() action here forces the RDD to be evaluated, and
      // that's the only reason our accumulator is actually updated.
      println("Processing " + mapped.count() + " values.")

      if (hitCounter.isDefined) {
        val hitCount = hitCounter.get.value
        if (hitCount > 0) {
          println("Hit the target character! From " + hitCount +
            " different direction(s).")
          return
        }
      }

      // Reducer combines data for each character ID, preserving the darkest
      // color and shortest path.
      heroes = mapped.reduceByKey(bfsReduce)
    }
  }


  /** Combine nodes for the same heroID, preserving the shortest length and darkest color. */
  def bfsReduce(data1: BFSData, data2: BFSData): BFSData = {

    // Extract data that we are combining
    val edges1: Array[Int] = data1._1
    val edges2: Array[Int] = data2._1
    val distance1: Int = data1._2
    val distance2: Int = data2._2
    val color1: String = data1._3
    val color2: String = data2._3

    // Default node values
    var distance: Int = 9999
    var color: String = "WHITE"
    var edges: ArrayBuffer[Int] = ArrayBuffer()

    // See if one is the original node with its connections.
    // If so preserve them.
    if (edges1.nonEmpty) {
      edges ++= edges1
    }
    if (edges2.nonEmpty) {
      edges ++= edges2
    }

    // Preserve minimum distance
    if (distance1 < distance) {
      distance = distance1
    }
    if (distance2 < distance) {
      distance = distance2
    }

    // Preserve darkest color
    if (color1 == "WHITE" && (color2 == "GRAY" || color2 == "BLACK")) {
      color = color2
    }
    if (color1 == "GRAY" && color2 == "BLACK") {
      color = color2
    }
    if (color2 == "WHITE" && (color1 == "GRAY" || color1 == "BLACK")) {
      color = color1
    }
    if (color2 == "GRAY" && color1 == "BLACK") {
      color = color1
    }
    if (color1 == "GRAY" && color2 == "GRAY") {
      color = color1
    }
    if (color1 == "BLACK" && color2 == "BLACK") {
      color = color1
    }

    (edges.toArray, distance, color)
  }
}
