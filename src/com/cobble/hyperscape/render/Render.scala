package com.cobble.hyperscape.render

import org.lwjgl.BufferUtils

object Render {

    /** The buffer used to upload to the GPU. Max is 1048576 floats */
    val uploadBuffer = BufferUtils.createFloatBuffer(1048576)

    /** The Camera that renders they game */
    val mainCamera = new Camera
    
}
