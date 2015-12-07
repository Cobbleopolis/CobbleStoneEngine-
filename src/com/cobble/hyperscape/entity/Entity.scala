package com.cobble.hyperscape.entity

import com.cobble.hyperscape.world.World
import org.lwjgl.util.vector.Vector3f

abstract class Entity(worldObj: World) {

    val position: Vector3f = new Vector3f()

    val rotation: Vector3f = new Vector3f()
    val speed: Float = 0.5f
    var maxLookDown: Float = Math.toRadians(90).toFloat
    var maxLookUp: Float = Math.toRadians(-90).toFloat

    def moveEntity(x: Float, y: Float, z: Float): Unit = {
        val direction = new Vector3f()
        val yaw = rotation.getY
        val sin = Math.sin(yaw).toFloat
        val cos = Math.cos(yaw).toFloat
        direction.translate(x * cos, y, x * sin)
        direction.translate(z * sin, 0, z * -cos)
        moveEntityAlongAxis(direction)
    }

    def moveEntityAlongAxis(translationVector: Vector3f): Unit = {
        moveEntityAlongAxis(translationVector.getX, translationVector.getY, translationVector.getZ)
    }

    def moveEntityAlongAxis(x: Float, y: Float, z: Float): Unit = {
        position.translate(x, y, z)
    }

    def rotateEntity(rotationVector: Vector3f): Unit = {
        rotateEntity(rotationVector.getX, rotationVector.getY, rotationVector.getZ)
    }

    def rotateEntity(pitch: Float, yaw: Float, roll: Float): Unit = {
        rotation.translate(pitch, yaw, roll)
        rotation.setX(Math.min(maxLookDown, Math.max(maxLookUp, rotation.getX)))
    }

}
