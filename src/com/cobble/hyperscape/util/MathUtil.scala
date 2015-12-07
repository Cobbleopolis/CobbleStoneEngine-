package com.cobble.hyperscape.util

import org.lwjgl.util.vector.Vector3f

object MathUtil {
    val PI = Math.PI.toFloat
    val PI180 = PI / 180f
    val PI360 = PI / 360f
    val TAU = PI * 2

    /**
     * Adds two vectors
     * @param vec1 The first vector to be added
     * @param vec2 The second vector to be added
     * @return The resultant vector from the addition of vec1 and vec2
     */
    def addVectors(vec1: Vector3f, vec2: Vector3f): Vector3f = {
        new Vector3f(vec1.getX + vec2.getX, vec1.getY + vec2.getY, vec1.getZ + vec2.getZ)
    }

    /**
     * Subtracts two vectors
     * @param vec1 The first vector to be subtracted
     * @param vec2 The second vector to be subtracted
     * @return The resultant vector from the subtraction of vec1 and vec2
     */
    def subtractVectors(vec1: Vector3f, vec2: Vector3f): Vector3f = {
        new Vector3f(vec1.getX - vec2.getX, vec1.getY - vec2.getY, vec1.getZ - vec2.getZ)
    }
    
    def getNegative(vector: Vector3f): Vector3f = {
        new Vector3f(-vector.getX, -vector.getY, -vector.getZ)
    }

    /**
     * Returns the greatest integer less than or equal to the double argument
     * @param float The float to floor
     */
    def floor_float(float: Float): Int = {
        val i: Int = float.toInt
        if (float < i.toFloat) i - 1 else i
    }

    /**
     * Copies the values from vecB to vecA
     * @param vecA The vector who's values will be changed the values in vecB
     * @param vecB The vector who's values will be assigned to vecA
     */
    def copyVec(vecA: Vector3f, vecB: Vector3f): Unit = {
        vecA.set(vecB)
    }
}
