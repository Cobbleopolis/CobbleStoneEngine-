package com.cobble.hyperscape.render

/**
 *
 */
object VirtualResolution {

    private var guiScale: Float = 1f

    /**
     * @return Returns the stored scale
     */
    def getScale: Float = {
        guiScale
    }

    /**
     * Sets the virtual scale
     * @param scale The scale to be stored
     */
    def setScale(scale: Float): Unit = {
        guiScale = scale
    }

    //    def calculateValues(): Unit = {
    //        //TODO implement
    //    }

    //    def getVirtualCoord(x: Float, y: Float): (Float, Float) = {
    //        //TODO implement
    //    }

    //    def getRealCoord(x: Float, y: Float): (Float, Float) = {
    //        //TODO implement
    //    }
}
