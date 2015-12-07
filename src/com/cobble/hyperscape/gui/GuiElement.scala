package com.cobble.hyperscape.gui

/**
 * Used by various GUI elements so that they can be used by GUIs
 */
abstract class GuiElement {

    var isDown: Boolean = false

    /**
     * Detects if the point that is passed is contained within the element
     * @param x The x location of the position
     * @param y The y location of the position
     * @return True if the point is inside the element. False otherwise
     */
    def containsPoint(x: Int, y: Int): Boolean

    /**
     * Renders the Element
     */
    def render(): Unit

    /**
     * Destories the Element from vRAM
     */
    def destroy(): Unit
}
