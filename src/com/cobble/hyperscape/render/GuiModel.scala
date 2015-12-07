package com.cobble.hyperscape.render

import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.opengl.{GL11, GL15, GL20, GL30}
import org.lwjgl.util.vector.{Matrix4f, Vector2f, Vector3f}

/**
 * The model used in rendering GUI's. These models can not be manipulated after initialization
 * @param verts an array of verts used to define the model for a GUI must be in format of (x, y, z, r, g, b, a)
 * @param x The default x location of the element
 * @param y The default y location of the element
 */
class GuiModel(verts: Array[Float], x: Float = 0f, y: Float = 0f) {

    val shader: String = "gui"

    val modelMatrix = new Matrix4f()

    println("Creating Gui Model...")
    val vao = GL30.glGenVertexArrays()
    modelMatrix.translate(new Vector2f(x, y))

    Render.uploadBuffer.clear()
    Render.uploadBuffer.put(verts)
    Render.uploadBuffer.flip()
    val vbo = GL15.glGenBuffers()
    GL30.glBindVertexArray(vao)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Render.uploadBuffer, GL15.GL_STATIC_DRAW)

    GL20.glVertexAttribPointer(0, Vertex.VERTEX_SIZE, GL11.GL_FLOAT, false, Vertex.COLOR_VERTEX_SIZE_IN_BYTES, Vertex.VERTEX_OFFSET)
    GL20.glVertexAttribPointer(1, Vertex.COLOR_SIZE, GL11.GL_FLOAT, false, Vertex.COLOR_VERTEX_SIZE_IN_BYTES, Vertex.COLOR_OFFSET)

    GL30.glBindVertexArray(0)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    //    println(verts.grouped(7).map(x => x.take(3).mkString(", ")).mkString("\n"))
    println("Created Gui Model")

    /**
     * Renders the model
     */
    def render(isHilighted: Boolean = false, isDown: Boolean = false): Unit = {
        // Bind the gui shader
        ShaderRegistry.bindShader(shader)

        Render.mainCamera.uploadPerspective()
        Render.mainCamera.uploadView()
        GLUtil.uploadModelMatrix(modelMatrix)

        if (verts(Vertex.GUI_ELEMENT_COUNT - 1) < 1.0f) GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

        val loc = ShaderRegistry.getCurrentShader.getUniformLocation("elementColor")
        val subtractColorLoc = ShaderRegistry.getCurrentShader.getUniformLocation("subtractColor")

        if (isHilighted) {
            GL20.glUniform3f(loc, .25f, .25f, .25f)
        } else {
            GL20.glUniform3f(loc, 0f, 0f, 0f)
        }

        if (isDown) {
            GL20.glUniform3f(loc, .05f, .05f, .05f)
            GL20.glUniform1i(subtractColorLoc, 1)
        } else {
            GL20.glUniform1i(subtractColorLoc, 0)
            //            Render.uploadBuffer.put(Array(0f, 0f, 0f, 1f))
        }


        // Bind to the VAO that has all the information about the quad vertices
        GL30.glBindVertexArray(vao)
        GL20.glEnableVertexAttribArray(0)
        GL20.glEnableVertexAttribArray(1)
        ShaderRegistry.getCurrentShader.inputs.foreach(input => GL20.glEnableVertexAttribArray(input._1))
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)

        // Draw the vertices
        //        GL11.glDisable(GL11.GL_CULL_FACE)
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verts.length / Vertex.GUI_ELEMENT_COUNT)
        //        GL11.glDrawArrays(if (drawLines) GL11.GL_LINES else GL11.GL_TRIANGLES, 0, verts.length / Vertex.GUI_ELEMENT_COUNT)

        if (verts(Vertex.GUI_ELEMENT_COUNT - 1) < 1.0f) GL11.glDisable(GL11.GL_BLEND)

        // Put everything back to default (deselect)
        ShaderRegistry.getCurrentShader.inputs.foreach(input => GL20.glDisableVertexAttribArray(input._1))
        GL30.glBindVertexArray(0)
    }

    /**
     * Translates the GUI model
     * @param translationVector A vector containing the x, y, z components to translate by
     */
    def translate(translationVector: Vector3f): Unit = modelMatrix.translate(translationVector)

    /**
     * Translates the GUI model
     * @param translationVector A vector containing the x, y components to translate by
     */
    def translate(translationVector: Vector2f): Unit = modelMatrix.translate(translationVector)

    /**
     * Destroys the model
     */
    def destroy(): Unit = {
        GL30.glBindVertexArray(vao)
        // Disable the VBO index from the VAO attributes list
        ShaderRegistry.getShader(shader).inputs.foreach(input => GL20.glDisableVertexAttribArray(input._1))

        // Delete the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
        GL15.glDeleteBuffers(vbo)

        // Delete the VAO
        GL30.glBindVertexArray(0)
        GL30.glDeleteVertexArrays(vao)
    }

    /**
     * Gets a copy of the model
     * @return A copy of the model
     */
    def copy: GuiModel = {
        new GuiModel(verts, x, y)
    }
}