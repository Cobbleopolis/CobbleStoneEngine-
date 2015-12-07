package com.cobble.hyperscape.gui

import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.render.{Render, FontModel, GuiModel}
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.util.vector.{Matrix4f, Vector4f}

/**
 * All elements should be added in the constructor.
 */
abstract class GuiScreen(name: String, size: Float = 360f, bevel: Float = 34f, color: Vector4f = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f)) {

    val z: Float = -2f

    val verts: Array[Float] = Array(
        -size, size - bevel, z, color.getX, color.getY, color.getZ, color.getW, // Top Bevel Bottom Left
        -size + bevel, size - bevel, z, color.getX, color.getY, color.getZ, color.getW, // Top Bevel Bottom Right
        -size + bevel, size, z, color.getX, color.getY, color.getZ, color.getW, // Top Bevel Top Right

        -size + bevel, size - bevel, z, color.getX, color.getY, color.getZ, color.getW, // Top Strip Bottom Left
        size, size, z, color.getX, color.getY, color.getZ, color.getW, // Top Strip Top Right
        -size + bevel, size, z, color.getX, color.getY, color.getZ, color.getW, // Top Strip Top Left

        -size + bevel, size - bevel, z, color.getX, color.getY, color.getZ, color.getW, // Top Strip Bottom Left
        size, size - bevel, z, color.getX, color.getY, color.getZ, color.getW, // Top Strip Bottom Right
        size, size, z, color.getX, color.getY, color.getZ, color.getW, // Top Strip Top Right

        -size, -size + bevel, z, color.getX, color.getY, color.getZ, color.getW, // Center Bottom Left
        size, size - bevel, z, color.getX, color.getY, color.getZ, color.getW, // Center Top Right
        -size, size - bevel, z, color.getX, color.getY, color.getZ, color.getW, // Center Top Left

        -size, -size + bevel, z, color.getX, color.getY, color.getZ, color.getW, // Center Bottom Left
        size, -size + bevel, z, color.getX, color.getY, color.getZ, color.getW, // Center Bottom Right
        size, size - bevel, z, color.getX, color.getY, color.getZ, color.getW, // Center Top Right

        size - bevel, -size, z, color.getX, color.getY, color.getZ, color.getW, // Bottom Bevel Bottom Left
        size, -size + bevel, z, color.getX, color.getY, color.getZ, color.getW, // Bottom Bevel Top Right
        size - bevel, -size + bevel, z, color.getX, color.getY, color.getZ, color.getW, // Bottom Bevel Top Left

        -size, -size, z, color.getX, color.getY, color.getZ, color.getW, // Bottom Strip Bottom Left
        size - bevel, -size + bevel, z, color.getX, color.getY, color.getZ, color.getW, // Bottom Strip Top Right
        -size, -size + bevel, z, color.getX, color.getY, color.getZ, color.getW, // Bottom Strip Top Left

        -size, -size, z, color.getX, color.getY, color.getZ, color.getW, // Bottom Strip Bottom Left
        size - bevel, -size, z, color.getX, color.getY, color.getZ, color.getW, // Bottom Strip Bottom Right
        size - bevel, -size + bevel, z, color.getX, color.getY, color.getZ, color.getW // Bottom Strip Top Right
    )

    val guiModel: GuiModel = new GuiModel(verts)
    /** The Font Model for the text at the top of the GUI **/
    val fontModel: FontModel = new FontModel(name, -size + bevel + 30.0f, size - bevel - (Reference.Font.FONT_HEIGHT / 2), ((bevel - Reference.Font.FONT_HEIGHT) / Reference.Font.FONT_HEIGHT).asInstanceOf[Int], textColor = (0.25f, 0.25f, 0.25f, 1.0f))
    /** A list of the element in the Gui **/
    var elementList: List[GuiButton] = List()

    /**
     * Renders the screen
     */
    def render(): Unit = {
        ShaderRegistry.bindShader("gui")
        Render.mainCamera.uploadView()
        val modelMatrix = new Matrix4f()
        //        modelMatrix.translate(new Vector3f(0, 0, 0))
        GLUtil.uploadModelMatrix(modelMatrix)
        //        guiModel.render()
        fontModel.render()
        elementList.foreach(elem => {
            elem.render()
        })

    }

    /**
     * Called when one of the elements in the Gui Screen is clicked
     * @param elementIndex The index of the element that was clicked
     */
    def onClick(elementIndex: Int): Unit = {}


    /**
     * Destroies the GUI and removes all the models from the Graphics card.
     */
    def destroy(): Unit = {
        guiModel.destroy()
        elementList.foreach(button => {
            button.destroy()
        })
    }
}
