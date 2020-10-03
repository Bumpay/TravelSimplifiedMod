package com.bumpay.travelsimplified.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class SteeringWheel extends Block {
    public SteeringWheel() {
        super(Block.Properties.create(Material.WOOD)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.WOOD)
                .harvestLevel(0)
                .harvestTool(ToolType.AXE));
    }
}
