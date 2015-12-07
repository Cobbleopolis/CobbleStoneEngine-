package com.cobble.hyperscape.util

import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.render.Render
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL20}
import org.lwjgl.util.vector.Matrix4f

object GLUtil {

    /**
     * Uploads a model matrix
     * @param matrix The model matrix to be uploaded
     */
    def uploadModelMatrix(matrix: Matrix4f): Unit = {
        Render.uploadBuffer.clear()
        matrix.store(Render.uploadBuffer)
        Render.uploadBuffer.flip()
        val modelMatrixLoc = ShaderRegistry.getCurrentShader.getUniformLocation("modelMatrix")
        GL20.glUniformMatrix4(modelMatrixLoc, false, Render.uploadBuffer)
    }

    /**
     * Wraps the font's width and height
     * @param text The text to get the size of
     * @param scale The scale of the font
     * @return The size of the font, order is width and then height
     */
    def getFontSize(text: String, scale: Int): (Float, Float) = (getFontWidth(text, scale), getFontHeight(scale))

    /**
     * Calculates the font's width
     * @param text The text to find the width of
     * @param scale The scale of the font
     * @return The width of the font
     */
    def getFontWidth(text: String, scale: Int): Float = text.length.asInstanceOf[Float] * scale.asInstanceOf[Float] * Reference.Font.FONT_WIDTH

    /**
     * Calculates the height of the font. Only works with single line text
     * @param scale The scale of the text.
     * @return The height of the text
     */
    def getFontHeight(scale: Int): Float = scale.asInstanceOf[Float] * Reference.Font.FONT_HEIGHT

    /**
     * Checks if there is an error on the graphics card
     * @param prependedText The text that will be prepended when printing
     * @param exit The game will exit if this is set to true and there is an error. Default is false
     */
    def checkGLError(prependedText: String, exit: Boolean = false): Unit = {
        val err = GL11.glGetError()
        if (err != 0) {
            println(prependedText + " | Error in shader " + ShaderRegistry.getCurrentShader + " | " + err)
            //            if (exit) System.exit(1)
        }
    }

}
