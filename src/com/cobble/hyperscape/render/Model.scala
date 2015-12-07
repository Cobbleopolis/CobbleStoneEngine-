package com.cobble.hyperscape.render

import java.util

import org.lwjgl.util.vector.Vector3f

/**
 * Creates a model object. This can not be rendered.
 * @param verts An array of floats used to define a model object (must be ordered x, y, z, u, v, normalX, normalY, normalX)
 */
class Model(verts: Array[Float]) {
    var verticies = util.Arrays.copyOf(verts, verts.length)

    /**
     * Rotates the model
     * @param vec A vector containing the x, y, z components of the rotation in degrease
     */
    def rotate(vec: Vector3f): Unit = {
        rotate(vec.getX, vec.getY, vec.getZ)
    }

    /**
     * Rotates the model
     * @param roll The degrease to rotate the model on the x axis
     * @param pitch The degrease to rotate the model on the y axis
     * @param yaw The degrease to rotate the model on the z axis
     */
    def rotate(roll: Float, pitch: Float, yaw: Float): Unit = {
        val avg = new Vector3f()
        for (i <- 0 until verticies.length by Vertex.ELEMENT_COUNT) {
            avg.translate(verticies(i), verticies(i + 1), verticies(i + 2))
        }
        val vertCount = verticies.length / Vertex.ELEMENT_COUNT
        avg.set(avg.getX / vertCount, avg.getY / vertCount, avg.getZ / vertCount)
        val moveVec = avg.negate().asInstanceOf[Vector3f]
        translate(moveVec)
        for (i <- 0 until verticies.length by Vertex.ELEMENT_COUNT) {
            verticies.update(i, verticies(i) + Math.sin(Math.toRadians(roll)).toFloat)
            verticies.update(i + 1, verticies(i + 1) + Math.sin(Math.toRadians(pitch)).toFloat)
            verticies.update(i + 2, verticies(i + 2) + Math.sin(Math.toRadians(yaw)).toFloat)
        }
        translate(avg)
    }

    def translate(vector: Vector3f): Unit = {
        translate(vector.getX, vector.getY, vector.getX)
    }

    /**
     * Translates the model relative to the objects origin
     * @param x Amount to translate in the x
     * @param y Amount to translate in the y
     * @param z Amount to translate in the z
     */
    def translate(x: Float, y: Float, z: Float): Unit = {
        for (i <- 0 until verticies.length by Vertex.ELEMENT_COUNT) {
            verticies.update(i, verticies(i) + x)
            verticies.update(i + 1, verticies(i + 1) + y)
            verticies.update(i + 2, verticies(i + 2) + z)
        }
    }

    /**
     * Translates the model's UVs
     * @param u Amount of block textures to move on the x-axis
     * @param v Amount of block textures to move on the y-axis
     */
    def translateUV(u: Float, v: Float): Unit = {
        for (i <- 0 until verticies.length by Vertex.ELEMENT_COUNT) {
            verticies.update(i + 3, verticies(i + 3) + u)
            verticies.update(i + 4, verticies(i + 4) + v)
        }
    }

	def getFace(faceIndex: Int, floatsPerFace: Int): Array[Float] = {
		val faces = verticies.grouped(floatsPerFace * 3).toArray
		faces(faceIndex)
	}

    /**
     * Gets the model's vertices
     * @return The model's vertices
     */
    def getVertices: Array[Float] = verticies

    /**
     * Gets a copy of the model
     * @return A copy of the model
     */
    def copy: Model = {
        new Model(verticies)
    }
}