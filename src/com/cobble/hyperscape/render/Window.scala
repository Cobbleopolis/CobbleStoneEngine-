package com.cobble.hyperscape.render

import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.{GLFWWindowSizeCallback, GLFWVidMode, GLFWKeyCallback, GLFWErrorCallback}
import org.lwjgl.opengl.GL
import org.lwjgl.system.MemoryUtil
import org.lwjgl.glfw.GLFWWindowSizeCallback.SAM

object Window {

    private var errorCallback: GLFWErrorCallback = null
    private var keyCallback: GLFWKeyCallback = null
    private var windowSizeCallback: GLFWWindowSizeCallback = null

    def init(width: Int, height: Int, title: String, isFullscreen: Boolean): Unit = {
        errorCallback = GLFWErrorCallback.createPrint(System.err)

        keyCallback = new GLFWKeyCallback {
            override def invoke(l: Long, i: Int, i1: Int, i2: Int, i3: Int): Unit = {}

        }

        glfwSetErrorCallback(errorCallback)

        if (glfwInit() != GLFW_TRUE)
            throw new IllegalStateException("Unable to create GLFW")

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        Render.window = glfwCreateWindow(width, height, title, if (isFullscreen) glfwGetPrimaryMonitor() else MemoryUtil.NULL, MemoryUtil.NULL)
        if (Render.window == MemoryUtil.NULL)
            throw new RuntimeException("Failed to create GLFW window")

        glfwSetKeyCallback(Render.window, keyCallback)

        val vidMode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())

        glfwSetWindowPos(
            Render.window,
            (vidMode.width() - width) / 2,
            (vidMode.height() - height) / 2
        )



        glfwMakeContextCurrent(Render.window)
        glfwSwapInterval(1)
        glfwShowWindow(Render.window)

        GL.createCapabilities()
    }

    def destroy(): Unit = {
        glfwDestroyWindow(Render.window)
        keyCallback.release()

        glfwTerminate()
        errorCallback.release()
    }

    
}
