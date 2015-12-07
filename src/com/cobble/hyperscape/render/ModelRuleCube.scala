package com.cobble.hyperscape.render

class ModelRuleCube extends ModelRule {

	val floatsPerFace: Int = Vertex.ELEMENT_COUNT

	/** Faces to add when the top side of the block is open */
	val topFaces: Array[Int] = Array[Int](2, 7)

	/** Faces to add when the bottom side of the block is open */
	val bottomFaces: Array[Int] = Array[Int](0, 1)

	/** Faces to add when the north side of the block is open */
	val northFaces: Array[Int] = Array[Int](6, 11)

	/** Faces to add when the east side of the block is open */
	val eastFaces: Array[Int] = Array[Int](3, 8)

	/** Faces to add when the south side of the block is open */
	val southFaces: Array[Int] = Array[Int](4, 9)

	/** Faces to add when the west side of the block is open */
	val westFaces: Array[Int] = Array[Int](5, 10)

}
