package com.bumpay.travelsimplified.util;

import com.bumpay.travelsimplified.TravelSimplifiedMod;
import com.bumpay.travelsimplified.blocks.BlockItemBase;
import com.bumpay.travelsimplified.blocks.SteeringWheel;
import com.bumpay.travelsimplified.items.ItemBase;
import com.bumpay.travelsimplified.items.PortLicense;
import com.bumpay.travelsimplified.network.TraSimPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, TravelSimplifiedMod.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, TravelSimplifiedMod.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, TravelSimplifiedMod.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, TravelSimplifiedMod.MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TraSimPacketHandler.registerMessages();
    }

    //Items
    public static final RegistryObject<Item> TRAVEL_MAP = ITEMS.register("travel_map", ItemBase::new);
    public static final RegistryObject<Item> MEASURING_TOOL = ITEMS.register("measuring_tool", ItemBase::new);
    public static final RegistryObject<Item> PORT_LICENSE = ITEMS.register("port_license", PortLicense::new);

    //Blocks
    public static final RegistryObject<Block> STEERING_WHEEL = BLOCKS.register("steering_wheel", SteeringWheel::new);

    //Block Items
    public  static final RegistryObject<Item> STEERING_WHEEL_ITEM = ITEMS.register("steering_wheel", () -> new BlockItemBase(STEERING_WHEEL.get()));
}
