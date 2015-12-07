package com.cobble.hyperscape.render

import com.cobble.hyperscape.registry.{ShaderRegistry, TextureRegistry}
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.opengl.{GL11, GL15, GL20, GL30}
import org.lwjgl.util.vector.Matrix4f

class ChunkModel {

    val modelMatrix: Matrix4f = new Matrix4f()
    val shader = "terrain"
    var verts: Array[Float] = Array[Float]()
    var vao: Int = -1

    var vbo: Int = -1

    def addVerts(newVerts: Array[Float]): Unit = {
        verts = verts ++ newVerts
    }

    def clearVerts(): Unit = {
        verts = Array[Float]()
    }

    def uploadVerts(): Unit = {
        println("Uploading...")
        destroy()

        vao = GL30.glGenVertexArrays()
        vbo = GL15.glGenBuffers()

        Render.uploadBuffer.clear()
        Render.uploadBuffer.put(verts)
        Render.uploadBuffer.flip()

        GL30.glBindVertexArray(vao)

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Render.uploadBuffer, GL15.GL_STATIC_DRAW)

        GL20.glVertexAttribPointer(0, Vertex.VERTEX_SIZE, GL11.GL_FLOAT, false, Vertex.SIZE_IN_BYTES, Vertex.VERTEX_OFFSET)
        GL20.glVertexAttribPointer(1, Vertex.UV_SIZE, GL11.GL_FLOAT, false, Vertex.SIZE_IN_BYTES, Vertex.UV_OFFSET_IN_BYTES)
        GL20.glVertexAttribPointer(2, Vertex.NORMAL_SIZE, GL11.GL_FLOAT, false, Vertex.SIZE_IN_BYTES, Vertex.NORMAL_OFFSET_IN_BYTES)

        GL30.glBindVertexArray(0)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
        println("Uploaded")
    }

    def destroy(): Unit = {
        if (vao != -1) {
            GL30.glBindVertexArray(vao)
            // Disable the VBO index from the VAO attributes list
            ShaderRegistry.getShader(shader).inputs.foreach(input => GL20.glDisableVertexAttribArray(input._1))

            // Delete the VAO
            GL30.glBindVertexArray(0)
            GL30.glDeleteVertexArrays(vao)
        }

        if (vbo != -1) {
            // Delete the VBO
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
            GL15.glDeleteBuffers(vbo)
        }


    }

    def render(): Unit = {
        GLUtil.checkGLError("Chunk Render | " + vao + " | " + vbo)
        if (vao != -1 && vbo != -1) {
            ShaderRegistry.bindShader(shader)
            Render.mainCamera.uploadPerspective()
            Render.mainCamera.uploadView()
            GLUtil.uploadModelMatrix(modelMatrix)
            TextureRegistry.bindTexture("terrain")

            GL30.glBindVertexArray(vao)

            ShaderRegistry.getCurrentShader.inputs.foreach(input => GL20.glEnableVertexAttribArray(input._1))

            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verts.length / Vertex.ELEMENT_COUNT)

            ShaderRegistry.getCurrentShader.inputs.foreach(input => GL20.glDisableVertexAttribArray(input._1))

            GL30.glBindVertexArray(0)
        }
    }
    
}
