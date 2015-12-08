package com.cobble.hyperscape.render

import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.{GLFWWindowSizeCallback, GLFWVidMode, GLFWKeyCallback, GLFWErrorCallback}
import org.lwjgl.opengl.{GL11, GL}
import org.lwjgl.system.MemoryUtil
import org.lwjgl.glfw.GLFWWindowSizeCallback.SAM

object Window {

    private var errorCallback: GLFWErrorCallback = null
    private var keyCallback: GLFWKeyCallback = null
    private var windowSizeCallback: GLFWWindowSizeCallback = null

	private var initalized = false

	private var width: Int = 0
	private var height: Int = 0

	private var wasResized: Boolean = false

	// The window handle
	private var windowID: Long = 0

    def init(width: Int, height: Int, title: String, isFullscreen: Boolean): Unit = {
        errorCallback = GLFWErrorCallback.createPrint(System.err)

        keyCallback = new GLFWKeyCallback {
            override def invoke(l: Long, i: Int, i1: Int, i2: Int, i3: Int): Unit = {}

        }

	    windowSizeCallback = new GLFWWindowSizeCallback {
		    override def invoke(window: Long, newWidth: Int, newHeight: Int): Unit = {
				wasResized = true
			    setSize(newWidth, newHeight)
		    }
	    }

        glfwSetErrorCallback(errorCallback)

        if (glfwInit() != GLFW_TRUE)
            throw new IllegalStateException("Unable to create GLFW")

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        windowID = glfwCreateWindow(width, height, title, if (isFullscreen) glfwGetPrimaryMonitor() else MemoryUtil.NULL, MemoryUtil.NULL)
        if (windowID == MemoryUtil.NULL)
            throw new RuntimeException("Failed to create GLFW window")

        glfwSetKeyCallback(windowID, keyCallback)

        val vidMode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())

        glfwSetWindowPos(
	        windowID,
            (vidMode.width() - width) / 2,
            (vidMode.height() - height) / 2
        )



        glfwMakeContextCurrent(windowID)
        glfwSwapInterval(1)
        glfwShowWindow(windowID)

        GL.createCapabilities()
	    initalized = true
	    setSize(width, height)
    }

	private def setWidth(newWidth: Int): Unit = width = newWidth

	private def setHeight(newHeight: Int): Unit = height = newHeight

	private def setSize(newWidth: Int, newHeight: Int): Unit = {
		width = newWidth
		height = newHeight
	}

    def getWidth: Int = width

	def getHeight: Int = height

	def getSize: (Int, Int) = (width, height)

	def getWindowID: Long = windowID

	def tick(): Unit = {
		if (wasResized) {
		    GL11.glViewport(0, 0, width, height)
		    Render.mainCamera.updatePerspective()
		    Render.mainCamera.uploadPerspective()
		}
		glfwSwapBuffers(windowID)
		glfwPollEvents()
		wasResized = false
	}

	def destroy(): Unit = {
        glfwDestroyWindow(windowID)
        keyCallback.release()
		windowSizeCallback.release()

		glfwTerminate()
		errorCallback.release()
    }

    
}
