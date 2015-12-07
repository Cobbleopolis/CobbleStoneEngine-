package com.cobble.hyperscape.core.gamestate

import com.cobble.hyperscape.gui.GuiScreen
import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.render.Render
import com.cobble.hyperscape.util.MathUtil
import com.cobble.hyperscape.world.World
import org.lwjgl.util.vector.Vector3f

trait GameState {

    /**
     * The default shader used by the shader when it is switched to.
     */
    val defaultShader: String

    /**
     * The current gui used by the game state. null by default and when there is no gui
     */
    var currentGui: GuiScreen = null

    var world: World

    /**
     * Called when the game switches to the game state.
     */
    def changeTo(): Unit = {
        ShaderRegistry.bindShader(defaultShader)
    }

    /**
     * Changes the current gui
     * @param gui The gui to change to (can be null).
     */
    def changeGui(gui: GuiScreen): Unit = {
        //        if (currentGui != null) currentGui.destroy()
        currentGui = gui
        //        if (currentGui != null) currentGui.initGui()
    }

    /**
     * Called when the game ticks
     */
    def tick(): Unit = {
        world.tick()
    }

    /**
     * Called when the game renders with a perspective matrix. The correct matrix has already been uploaded. This renders before the orthographic render.
     */
    def perspectiveRender(): Unit = {
        Render.mainCamera.view.setIdentity()
        Render.mainCamera.view.rotate(world.player.rotation.getX, new Vector3f(1.0f, 0.0f, 0.0f))
        Render.mainCamera.view.rotate(world.player.rotation.getY, new Vector3f(0.0f, 1.0f, 0.0f))
        Render.mainCamera.view.rotate(world.player.rotation.getZ, new Vector3f(0.0f, 0.0f, 1.0f))
        Render.mainCamera.view.translate(MathUtil.getNegative(world.player.position))
        world.render()
    }

    /**
     * Called when the game renders with an orthographic matrix. The correct matrix has already been uploaded. This renders after the perspective render.
     */
    def orthographicRender(): Unit = {

    }

    /**
     * Called when the game switches to a different state or closes.
     */
    def destroy(): Unit = {
        if (currentGui != null) currentGui.destroy()
        world.destroy()
    }
}
