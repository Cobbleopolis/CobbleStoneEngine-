package com.cobble.hyperscape.render

import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.{ShaderRegistry, TextureRegistry}
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.opengl.{GL11, GL15, GL20, GL30}
import org.lwjgl.util.vector.{Matrix4f, Vector2f}

//6 * 8
//0.0625
class FontModel(text: String, x: Float, y: Float, scale: Int = 2, zIndex: Float = 0.5f, textColor: (Float, Float, Float, Float) = (1.0f, 1.0f, 1.0f, 1.0f)) {

    val shader: String = "font"

    val modelMatrix = new Matrix4f()
    val vao = GL30.glGenVertexArrays()

    println("Creating Font Model...")
    val vbo = GL15.glGenBuffers()
    var verts: Array[Float] = Array[Float]()
    modelMatrix.translate(new Vector2f(x, y))
    var i = 0

    text.foreach(char => {
        val charVal = Math.min(char.asInstanceOf[Int], 255)
        val xLoc: Int = charVal % 16
        val yLoc: Int = charVal / 16

        val UV: Float = 0.0625f

        val UVx: Float = xLoc * UV

        val UVy: Float = yLoc * UV

        val width = Reference.Font.FONT_WIDTH * scale

        val height = Reference.Font.FONT_HEIGHT * scale

        val xOff = width * i
        val localVerts: Array[Array[Float]] = Array(
            Array(xOff, 0, zIndex, UVx, UVy + UV), //Bottom Left
            Array(xOff, height, zIndex, UVx, UVy), //Top Left
            Array(xOff + width, height, zIndex, UVx + UV, UVy), //Top Right
            Array(xOff + width, 0, zIndex, UVx + UV, UVy + UV) //Bottom Right
        )

        val order: Array[Int] = Array(0, 2, 1, 0, 3, 2)

        order.foreach(index => verts = verts ++ localVerts(index))
        i += 1
    })


    Render.uploadBuffer.clear()
    Render.uploadBuffer.put(verts)
    Render.uploadBuffer.flip()
    GL30.glBindVertexArray(vao)

    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Render.uploadBuffer, GL15.GL_STATIC_DRAW)

    GL20.glVertexAttribPointer(0, Vertex.VERTEX_SIZE, GL11.GL_FLOAT, false, Vertex.TEXTURE_VERTEX_SIZE_IN_BYTES, Vertex.VERTEX_OFFSET)
    GL20.glVertexAttribPointer(1, Vertex.UV_SIZE, GL11.GL_FLOAT, false, Vertex.TEXTURE_VERTEX_SIZE_IN_BYTES, Vertex.TEXTURE_OFFSET)

    GL30.glBindVertexArray(0)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    println("Created Font Model")

    /**
     * @return the width of the text in GLmeters
     */
    def getTextWidth: Int = {
        Reference.Font.FONT_WIDTH * scale * text.length
    }

    /**
     * @return the height of the text in GLmeters
     */
    def getTextHeight: Int = {
        Reference.Font.FONT_HEIGHT * scale
    }

    /**
     * Renders the model
     */
    def render(drawLines: Boolean = false): Unit = {
        // Bind the font shader
        ShaderRegistry.bindShader(shader)
        Render.mainCamera.uploadPerspective()
        Render.mainCamera.uploadView()
        GLUtil.uploadModelMatrix(modelMatrix)
        TextureRegistry.bindTexture("font")
        // Bind to the VAO that has all the information about the quad vertices
        GL30.glBindVertexArray(vao)
        ShaderRegistry.getCurrentShader.inputs.foreach(input => GL20.glEnableVertexAttribArray(input._1))
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)

        val loc = ShaderRegistry.getCurrentShader.getUniformLocation("textColor")
        GL20.glUniform4f(loc, textColor._1, textColor._2, textColor._3, textColor._4)

        // Draw the vertices
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verts.length / Vertex.TEXTURE_ELEMENT_COUNT)
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
    def copy: FontModel = {
        new FontModel(text, x, y, scale, zIndex, textColor)
    }
}
