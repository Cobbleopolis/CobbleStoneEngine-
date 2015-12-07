package com.cobble.hyperscape.render

import java.nio.IntBuffer

import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW

object Render {

    /** The buffer used to upload to the GPU. Max is 1048576 floats */
    val uploadBuffer = BufferUtils.createFloatBuffer(1048576)

    /** The Camera that renders they game */
    val mainCamera = new Camera

	// The window handle
	var window: Long = 0

	def getWindowWidth: Int = {
		getWindowSize._1
	}

	def getWindowHeight: Int = {
		getWindowSize._2
	}

	def getWindowSize: (Int, Int) = {
		val w:IntBuffer = BufferUtils.createIntBuffer(1)
		val h:IntBuffer = BufferUtils.createIntBuffer(1)
		GLFW.glfwGetWindowSize(window, w, h)
		(w.get(0), h.get(0))
	}
}
