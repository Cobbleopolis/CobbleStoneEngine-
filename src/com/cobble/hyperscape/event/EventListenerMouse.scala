package com.cobble.hyperscape.event

trait EventListenerMouse {

    /**
     * Called when the mouse moves
     * @param x The x location of the mouse
     * @param y The y location of the mouse
     * @param deltaX The change in the x position
     * @param deltaY The change in the y position
     */
    def onMouseMove(x: Int, y: Int, deltaX: Int, deltaY: Int): Unit = {}

    /**
     * Called while a mouse button is down
     * @param x The x location of the mouse
     * @param y The y location of the mouse
     * @param deltaX The change in the x position
     * @param deltaY The change in the y position
     * @param mouseButton The button that was pressed
     */
    def mouseDown(x: Int, y: Int, deltaX: Int, deltaY: Int, mouseButton: Int): Unit = {}

    /**
     * Called when the mouse is pressed
     * @param x The x location of the mouse
     * @param y The y location of the mouse
     * @param deltaX The change in the x position
     * @param deltaY The change in the y position
     * @param mouseButton The button that was pressed
     */
    def onMouseDown(x: Int, y: Int, deltaX: Int, deltaY: Int, mouseButton: Int): Unit = {}

    /**
     * Called when the mouse button is released
     * @param x The x location of the mouse
     * @param y The y location of the mouse
     * @param deltaX The change in the x position
     * @param deltaY The change in the y position
     * @param mouseButton The button that was pressed
     */
    def onMouseUp(x: Int, y: Int, deltaX: Int, deltaY: Int, mouseButton: Int): Unit = {}

}