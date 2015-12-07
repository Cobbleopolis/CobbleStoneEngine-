package com.cobble.hyperscape.render

import com.cobble.hyperscape.registry.ShaderRegistry
import org.lwjgl.opengl.{GL11, GL15, GL20, GL30}

/**
 * Creates a renderable model
 * @param verts An array of floats used to define a model object (must be ordered x, y, z, u, v, normalX, normalY, normalZ)
 */
class RenderModel(verts: Array[Float]) extends Model(verts) {

    Render.uploadBuffer.clear()
    Render.uploadBuffer.put(verts)
    Render.uploadBuffer.flip()

    val vao = GL30.glGenVertexArrays()
    GL30.glBindVertexArray(vao)

    val vbo = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Render.uploadBuffer, GL15.GL_STATIC_DRAW)

    GL20.glVertexAttribPointer(0, Vertex.VERTEX_SIZE, GL11.GL_FLOAT, false, Vertex.SIZE_IN_BYTES, Vertex.VERTEX_OFFSET)
    GL20.glVertexAttribPointer(1, Vertex.UV_SIZE, GL11.GL_FLOAT, false, Vertex.SIZE_IN_BYTES, Vertex.UV_OFFSET_IN_BYTES)
    GL20.glVertexAttribPointer(2, Vertex.NORMAL_SIZE, GL11.GL_FLOAT, false, Vertex.SIZE_IN_BYTES, Vertex.NORMAL_OFFSET_IN_BYTES)

    GL30.glBindVertexArray(0)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)

    /**
     * Renders the model
     */
    def render(drawLines: Boolean = false): Unit = {
        // Bind to the VAO that has all the information about the quad vertices
        GL30.glBindVertexArray(vao)
        ShaderRegistry.getCurrentShader.inputs.foreach(input => GL20.glEnableVertexAttribArray(input._1))
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)

        // Draw the vertices
        if (drawLines)
            GL11.glDrawArrays(GL11.GL_LINES, 0, verticies.length / Vertex.ELEMENT_COUNT)
        else
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verticies.length / Vertex.ELEMENT_COUNT)

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
        ShaderRegistry.getCurrentShader.inputs.foreach(input => GL20.glEnableVertexAttribArray(input._1))

        // Delete the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
        GL15.glDeleteBuffers(vbo)

        // Delete the VAO
        GL30.glBindVertexArray(0)
        GL30.glDeleteVertexArrays(vao)
    }

    override def copy: RenderModel = {
        new RenderModel(verticies)
    }
}
