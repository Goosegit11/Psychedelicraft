/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft;

import ivorius.psychedelicraft.blocks.PSBlocks;
import ivorius.psychedelicraft.commands.*;
import ivorius.psychedelicraft.config.PSConfig;
import ivorius.psychedelicraft.entities.PSEntityList;
import ivorius.psychedelicraft.gui.PSItemGroups;
import ivorius.psychedelicraft.items.PSItems;
import ivorius.psychedelicraft.worldgen.PSWorldGen;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Psychedelicraft implements ModInitializer {
    public static final String MODID = "psychedelicraft";
    public static final String NAME = "Psychedelicraft";
    public static final String VERSION = "1.5.2";

    public static PSProxy proxy;

    public static final Logger logger = LogManager.getLogger();

    public static final String filePathTextures = "textures/mod/";
    public static final String filePathModels = "models/";
    public static final String filePathOther = "other/";
    public static final String filePathShaders = "shaders/";
    public static final String modBase = "psychedelicraft:";

    public static int blockWineGrapeLatticeRenderType;

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }

    @Override
    public void onInitialize() {
        PSConfig.loadConfig(null);

        proxy.preInit();

        PSBlocks.bootstrap();
        PSItems.bootstrap();
        PSItemGroups.bootstrap();
        PSEntityList.bootstrap();
        PSWorldGen.initWorldGen();
        PSCommands.bootstrap();
    }
}