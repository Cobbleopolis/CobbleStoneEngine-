package com.cobble.hyperscape.registry

import com.cobble.hyperscape.render.Shader
import org.lwjgl.opengl.{GL11, GL20}

object ShaderRegistry {
    private var programs: Map[String, Shader] = Map()
    private var currShader = ""

    /**
     * Loads a shader and stores it in the shader registry with the name provided
     * @param vertPath Path to the vertex shader
     * @param fragPath Path to the fragment shader
     * @param shaderName Name to store the shader with
     */
    def loadShader(vertPath: String, fragPath: String, shaderName: String, atribLocation: Array[(Int, String)]): Unit = {
        // Load the vertex shader
        val vsId = this.loadShader(vertPath, GL20.GL_VERTEX_SHADER)
        // Load the fragment shader
        val fsId = this.loadShader(fragPath, GL20.GL_FRAGMENT_SHADER)

        // Create a new shader program that links both shaders
        val pId = GL20.glCreateProgram()
        GL20.glAttachShader(pId, vsId)
        GL20.glAttachShader(pId, fsId)

        // Position information will be attribute 0
        atribLocation.foreach(loc => {
            GL20.glBindAttribLocation(pId, loc._1, loc._2)
        })
        //        GL20.glBindAttribLocation(pId, 0, "in_Position")
        //        // Color information will be attribute 1
        //        GL20.glBindAttribLocation(pId, 1, "in_TextureCoord")

        GL20.glLinkProgram(pId)
        GL20.glValidateProgram(pId)

        val errorCheckValue = GL11.glGetError()
        if (errorCheckValue != GL11.GL_NO_ERROR) {
            System.out.println("ERROR - Could not create the shaders: " + Integer.toHexString(errorCheckValue))
            System.exit(-1)
        }
        val newShader = new Shader(pId, vsId, fsId)
        newShader.inputs = atribLocation
        programs += (shaderName -> newShader)
        println("\tShader Id | " + shaderName + " | " + pId + ", " + vsId + ", " + fsId)
        val shader = programs(shaderName)
        println("\t\tShader | " + shader.getProgramId + ", " + shader.getVertexId + ", " + shader.getFragmentId)
    }


    private def loadShader(filename: String, shaderType: Int): Int = {
        val shaderSource = scala.io.Source.fromFile(filename).mkString

        val shaderID = GL20.glCreateShader(shaderType)
        GL20.glShaderSource(shaderID, shaderSource)
        GL20.glCompileShader(shaderID)
        val log = GL20.glGetShaderInfoLog(shaderID, 32767)
        if (!log.isEmpty) {
            println("Shader Log:")
            println(log)
        }
        val err = GL11.glGetError()
        if (err != 0) {
            println("Error in " + filename + " | " + err)
            System.exit(1)
        }
        shaderID
    }

    /**
     * Binds a shader
     * @param shaderName Name of the shader to bind
     */
    def bindShader(shaderName: String): Unit = {
        //        println(shaderName + " | " + currShader + " | " + (shaderName != currShader))
        if (shaderName != currShader) {
            programs(shaderName).bind()
            currShader = shaderName
        }
        val err = GL11.glGetError()
        if (err != 0) {
            println("In Shader Registry No Change | " + shaderName + " | " + err)
            System.exit(12)
        }
    }

    /**
     * Returns the currently bound shader object
     * @return Currently bound shader
     */
    def getCurrentShader: Shader = {
        programs(currShader)
    }

    def getShader(shaderName: String): Shader = {
        programs(shaderName)
    }

    /**
     * Destroys all loaded shaders
     */
    def destroyAllShaders(): Unit = {
        println("Destroying all registered shaders...")
        for (shader <- programs) {
            destroyShader(shader._1)
        }
        println("Finished destroying all registered shaders")
    }

    /**
     * Destroys a shader
     * @param shaderName Name of the shader to destroy
     */
    def destroyShader(shaderName: String): Unit = {
        println("Destroying shader " + shaderName + "...")
        programs(shaderName).destroy()
        programs = programs.filter(shader => !shader._2.equals(programs(shaderName)))
        println("Finished destroying shader " + shaderName + "...")
    }

    def printAllShaders(): Unit = {
        for (shader <- programs) {
            println(shader._1 + " | " + shader._2.toString)
        }
    }
}
