package com.cobble.hyperscape.render

abstract class ModelRule {

	val floatsPerFace: Int

	val topFaces: Array[Int]

	val bottomFaces: Array[Int]

	val northFaces: Array[Int]

	val eastFaces: Array[Int]

	val southFaces: Array[Int]

	val westFaces: Array[Int]

}
