package com.cobble.hyperscape.render

import com.cobble.hyperscape.registry.ShaderRegistry
import org.lwjgl.opengl.{GL11, GL15, GL20, GL30}
import org.lwjgl.util.vector.Matrix4f

/**
 * The model useed in rendering GUI's. These models can not be manipulated after initialization
 * @param verts an array of verts used to define the model for a GUI must be in format of (x, y, z, u, v)
 */
class TexturedGuiRenderModel(verts: Array[Float]) {
    println("Creating Gui Model...")
    val modelMatrix = new Matrix4f()

    Render.uploadBuffer.clear()
    Render.uploadBuffer.put(verts)
    Render.uploadBuffer.flip()

    val vao = GL30.glGenVertexArrays()
    GL30.glBindVertexArray(vao)

    val vbo = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Render.uploadBuffer, GL15.GL_STATIC_DRAW)

    GL20.glVertexAttribPointer(0, Vertex.VERTEX_SIZE, GL11.GL_FLOAT, false, Vertex.COLOR_VERTEX_SIZE_IN_BYTES, Vertex.VERTEX_OFFSET)
    GL20.glVertexAttribPointer(1, Vertex.UV_SIZE, GL11.GL_FLOAT, false, Vertex.TEXTURE_VERTEX_SIZE_IN_BYTES, Vertex.TEXTURE_OFFSET)

    GL30.glBindVertexArray(0)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    println("Created Gui Model")

    /**
     * Renders the model
     */
    def render(drawLines: Boolean = false): Unit = {
        // Bind to the VAO that has all the information about the quad vertices
        GL30.glBindVertexArray(vao)
        ShaderRegistry.getCurrentShader.inputs.foreach(input => GL20.glEnableVertexAttribArray(input._1))
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)

        // Draw the vertices
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verts.length / Vertex.GUI_ELEMENT_COUNT)
        //        GL11.glDrawArrays(if (drawLines) GL11.GL_LINES else GL11.GL_TRIANGLES, 0, verts.length / Vertex.GUI_ELEMENT_COUNT)

        // Put everything back to default (deselect)
        ShaderRegistry.getCurrentShader.inputs.foreach(input => GL20.glDisableVertexAttribArray(input._1))
        GL30.glBindVertexArray(0)
    }


    /**
     * Destroys the model
     */
    def destroy(): Unit = {
        GL30.glBindVertexArray(vao)
        // Disable the VBO index from the VAO attributes list
        ShaderRegistry.getCurrentShader.inputs.foreach(input => GL20.glDisableVertexAttribArray(input._1))

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
    def copy: TexturedGuiRenderModel = {
        new TexturedGuiRenderModel(verts)
    }
}