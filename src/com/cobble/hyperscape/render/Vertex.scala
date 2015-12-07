package com.cobble.hyperscape.render

object Vertex {

    /** Amount of bytes in a float */
    val BYTES_IN_FLOAT = 4

    /** The amount of elements in one vertex */
    val ELEMENT_COUNT = 8

    /** The amount of elements in the x, y, z location */
    val VERTEX_SIZE = 3

    /** The amount of elements in one uv element */
    val UV_SIZE = 2

    /** The amount of elements in one uv element */
    val UV_OFFSET = 3

    /** The amount of elements in one normal element */
    val NORMAL_SIZE = 3

    /** The offset for the x, y, z elements in bytes */
    val VERTEX_OFFSET = 0

    /** The offset for the UV elements in bytes */
    val UV_OFFSET_IN_BYTES = UV_OFFSET * BYTES_IN_FLOAT

    /** The offset for the normal in bytes */
    val NORMAL_OFFSET_IN_BYTES = 5 * BYTES_IN_FLOAT

    /** The size of one vertex in bytes */
    val SIZE_IN_BYTES = ELEMENT_COUNT * BYTES_IN_FLOAT

    //Color GUI
    /** The amount of elements in one gui vertex */
    val GUI_ELEMENT_COUNT = 7

    /** The offset for color in bytes */
    val COLOR_OFFSET = VERTEX_SIZE * BYTES_IN_FLOAT

    /** The amount of elements in one color element */
    val COLOR_SIZE = 4

    /** The amount of bytes in a color gui vertex */
    val COLOR_VERTEX_SIZE_IN_BYTES = GUI_ELEMENT_COUNT * BYTES_IN_FLOAT


    //Texture GUI

    /** The amount of elements in a texture gui vertex */
    val TEXTURE_ELEMENT_COUNT = 5

    /** The amount of bytes in a textured gui vertex */
    val TEXTURE_VERTEX_SIZE_IN_BYTES = TEXTURE_ELEMENT_COUNT * BYTES_IN_FLOAT

    /** The offset for texture in bytes */
    val TEXTURE_OFFSET = VERTEX_SIZE * BYTES_IN_FLOAT


    //Font

    /** The amount of elements in a font vertex **/
    val FONT_ELEMENT_COUNT = 8

    val FONT_COLOR_OFFSET = (VERTEX_SIZE + UV_SIZE) * BYTES_IN_FLOAT

    /** The size of a vertex in bytes **/
    val FONT_VERTEX_SIZE_IN_BYTES = FONT_ELEMENT_COUNT * BYTES_IN_FLOAT

    val FONT_COLOR_SIZE = 3

    //Drop Shadow

    val DROP_SHADOW_COUNT = 5

    val DROP_SHADOW_SIZE_IN_BYTES = DROP_SHADOW_COUNT * BYTES_IN_FLOAT

    val DROP_SHADOW_Z_INDEX_OFFSET = (VERTEX_SIZE + UV_SIZE) * BYTES_IN_FLOAT

    val DROP_SHADOW_Z_INDEX_SIZE = 1

    val DROP_SHADOW_Z_INDEX_SIZE_IN_BYTES = DROP_SHADOW_Z_INDEX_SIZE * BYTES_IN_FLOAT
}
