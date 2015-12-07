package com.cobble.hyperscape.world

import com.cobble.hyperscape.block.Block
import com.cobble.hyperscape.entity.EntityPlayer
import org.lwjgl.util.vector.Vector3f

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


abstract class World {

    val chunks: mutable.HashMap[Int, Chunk] = new mutable.HashMap[Int, Chunk]

    val player: EntityPlayer = new EntityPlayer(this)

    var activeChunks: Array[Int] = Array[Int]()

    def getChunkXZFromIndex(index: Int): (Int, Int) = {
        ((index >> 4) & 15, (index & 15).toShort)
    }

    def setBlock(x: Int, y: Int, z: Int, block: Block): Unit = {
        if (x < 0 || x > 255 || y < 0 || y > 255 || z < 0 || z > 255) {
            println("X, Y, and Z must be between 0 and 255. Returning.")
            return
        }
        val chunkIndex: Int = getChunkIndexFromXZ(x / 16, z / 16)

        if (!chunks.contains(chunkIndex)) {
            //            val (chunkX, chunkZ) = getChunkXZFromIndex(chunkIndex)
            chunks.put(chunkIndex, new Chunk(x / 16, z / 16, this))
        }

        if (chunks(chunkIndex).getBlock(x % 16, y, z % 16) != null)
            println("Resetting a block")

        chunks(chunkIndex).setBlock(x % 16, y, z % 16, block)
    }

    def getChunkIndexFromXZ(chunkX: Int, chunkZ: Int): Int = {
        chunkX << 4 | chunkZ
    }

    def getBlock(x: Int, y: Int, z: Int): Block = {
        if (x < 0 || x > 255 || y < 0 || y > 255 || z < 0 || z > 255) {
//            println("X, Y, and Z must be between 0 and 255. Returning null.")
            return null
        }
        val chunk = chunks.getOrElse(getChunkIndexFromXZ(x / 16, z / 16), null)

        if (chunk != null)
            chunk.getBlock(x % 16, y, z % 16)
        else
            null
    }

	def getSurroundingBlocks(x: Int, y: Int, z: Int): Array[Block] = {
		val blocks = new ArrayBuffer[Block]
		blocks += getBlock(x, y + 1, z)// Top
		blocks += getBlock(x, y - 1, z)// Bottom
		blocks += getBlock(x + 1, y, z)// North
		blocks += getBlock(x, y, z + 1)// East
		blocks += getBlock(x - 1, y, z)// South
		blocks += getBlock(x, y, z - 1)// West
		blocks.toArray
	}
    
    def tick(): Unit = {
        activeChunks = getActiveChunks(player.position, 5)
    }

    def getActiveChunks(position: Vector3f, radius: Int): Array[Int] = {
        var activeChunks: Array[Int] = Array[Int]()
        for (x <- -radius to radius)
            for (z <- -radius to radius) {
                val cappedX = Math.min(Math.max((position.getX / 16) + x, 0), 16).toInt
                val cappedZ = Math.min(Math.max((position.getZ / 16) + z, 0), 16).toInt

                val index = getChunkIndexFromXZ(cappedX, cappedZ)
                if (chunks.contains(index))
                    activeChunks = activeChunks :+ index
            }
        activeChunks
    }

    def render(): Unit = {
        activeChunks.foreach(index => chunks(index).render())
    }

    def destroy(): Unit = {
        chunks.foreach(chunk => chunk._2.destroy())
    }
}
