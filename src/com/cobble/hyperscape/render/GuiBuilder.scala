package com.cobble.hyperscape.render

trait GuiBuilder {

    def generateGuiButton(x: Int, y: Int, height: Int, width: Int, red: Float, green: Float, blue: Float, text: String): Array[Float]

    def generateBackground(x: Int, y: Int, height: Int, width: Int, red: Float, green: Float, blue: Float): Array[Float]
}
