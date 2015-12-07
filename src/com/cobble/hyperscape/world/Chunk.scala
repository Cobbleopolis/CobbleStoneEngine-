package com.cobble.hyperscape.world

import com.cobble.hyperscape.block.Block
import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.{BlockRegistry, ModelRegistry, ShaderRegistry}
import com.cobble.hyperscape.render.ChunkModel
import org.lwjgl.opengl.GL20
import org.lwjgl.util.vector.Vector3f

class Chunk(xCoord: Int, zCoord: Int, worldObj: World) {

    val blocks: Array[Int] = new Array[Int](Reference.World.CHUNK_SIZE)

    val chunkModel = new ChunkModel()
    chunkModel.modelMatrix.translate(new Vector3f(xCoord.toFloat * 16f, 0f, zCoord.toFloat * 16f))

    var isDirty: Boolean = false

    def setBlock(x: Int, y: Int, z: Int, block: Block): Unit = {
        blocks(getBlockIndexFromXYZ(x, y, z)) = block.blockID
        isDirty = true
    }

    def getBlock(x: Int, y: Int, z: Int): Block = {
        BlockRegistry.getBlock(blocks(getBlockIndexFromXYZ(x, y, z)))
    }

    def getBlockIndexFromXYZ(x: Int, y: Int, z: Int): Int = {
        y << 8 | x << 4 | z
    }

    def render(): Unit = {
        if (isDirty)
            generateModel()

        ShaderRegistry.bindShader("terrain")
        val colorLoc = ShaderRegistry.getCurrentShader.getUniformLocation("chunkColor")
        val (r: Float, g: Float, b: Float) = {
            if ((xCoord + zCoord) % 2 == 0)
                (1f, 0f, .3f)
            else
                (0f, 1f, .3f)
        }
        GL20.glUniform4f(colorLoc, r, g, b, 1f)

//        val fogLoc = ShaderRegistry.getCurrentShader.getUniformLocation("drawFog")
//        GL20.glUniform1i(fogLoc, if (HyperScape.drawFog) 1 else 0)

        chunkModel.render()
    }

    def generateModel(): Unit = {
        var i = 0

        chunkModel.clearVerts()


        blocks.foreach(blockID => {
            if (BlockRegistry.getBlock(blockID) != null) {
                val (model, modelRule) = ModelRegistry.getModelWithRule("cube")
	            val (x: Int, y: Int, z: Int) = getBlockXYZFromIndex(i)
	            val (u: Float, v: Float) = BlockRegistry.getBlock(blockID).uv

	            model.translate(x, y, z)
	            model.translateUV(u * 0.0625f, v * 0.0625f)




//	            surroundingBlocks.foreach(block => {
//		            if (block == null) {
//
//		            }
//	            })

	            if (modelRule != null) {

		            val surroundingBlocks = worldObj.getSurroundingBlocks(x + (xCoord * 16), y, z + (zCoord * 16))

		            val print = x + (xCoord * 16) == 16 && y == 2 && z  + (zCoord * 16) == 0
//			                    x + (xCoord * 16) == 15 && y == 2 && z  + (zCoord * 16) == 0

//		            if (print) {
//			            println(xCoord + " | " + zCoord)
//			            worldObj.getSurroundingBlocks(16, 2, 0).foreach(block =>
//				            println(if (block != null) block.blockID else 0)
//			            )
//		            }

		            var side: Int = 0

	                surroundingBlocks.foreach(block => {
			            if (block == null) {
				            val sidesToAdd: Array[Int] =
					            side match {
						            case 0 => modelRule.topFaces
						            case 1 => modelRule.bottomFaces
						            case 2 => modelRule.northFaces
						            case 3 => modelRule.eastFaces
						            case 4 => modelRule.southFaces
						            case 5 => modelRule.westFaces
						            case _ => Array[Int]()
					            }

				            sidesToAdd.foreach(side => {
					            chunkModel.addVerts(model.getFace(side, modelRule.floatsPerFace))
				            })
			            }
			            side += 1
		            })
	            } else
		            chunkModel.addVerts(model.getVertices)
            }
            i += 1
        })


        chunkModel.uploadVerts()
        isDirty = false
    }

    def getBlockXYZFromIndex(index: Int): (Int, Int, Int) = {
        ((index >> 4) & 15, (index >> 8) & 255, index & 15)
    }

    def destroy(): Unit = {
        chunkModel.destroy()
    }
}
