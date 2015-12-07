package com.cobble.hyperscape.reference

object Reference {

    object Camera {

        val PERSPECTIVE_MODE: Int = 0

        val ORTHOGRAPHIC_MODE: Int = 1
    }

    object GameState {

        val MAIN_MENU: String = "mainMenu"
    }

    object VirtualResolution {

        val NORMAL_SCALE: Float = 1f

        val SMALL_SCALE: Float = .5f

        val LARGE_SCALE: Float = 2f

    }

    object Font {

        val FONT_WIDTH = 6

        val FONT_HEIGHT = 8
    }

    object Mouse {

        val MOUSE_BUTTON_LEFT = 0

        val MOUSE_BUTTON_RIGHT = 1
    }

    object World {

        val WORLD_SIZE = 16777215

        val CHUNK_SIZE = 261648
    }

}
