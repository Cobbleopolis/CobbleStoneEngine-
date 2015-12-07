package com.cobble.hyperscape.block

import com.cobble.hyperscape.render.BlockRenderType
import com.cobble.hyperscape.render.BlockRenderType.BlockRenderType

trait Block {
    val blockID: Int

    val uv: (Float, Float)

	var renderType: BlockRenderType = BlockRenderType.NORMAL
}
