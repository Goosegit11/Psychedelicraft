package ivorius.psychedelicraft.client.render;

import ivorius.psychedelicraft.block.PSBlocks;
import ivorius.psychedelicraft.block.entity.*;
import ivorius.psychedelicraft.client.render.blocks.*;
import ivorius.psychedelicraft.entity.*;
import ivorius.psychedelicraft.item.PSItems;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

/**
 * @author Sollace
 * @since 1 Jan 2023
 */
public interface PSRenderers {
    static void bootstrap() {
        EntityRendererRegistry.register(PSEntities.MOLOTOV_COCKTAIL, context -> new FlyingItemEntityRenderer<>(context, 1, true));
        EntityRendererRegistry.register(PSEntities.REALITY_RIFT, RealityRiftEntityRenderer::new);

        BlockEntityRendererRegistry.register(PSBlockEntities.DISTILLERY, FlaskBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(PSBlockEntities.FLASK, FlaskBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(PSBlockEntities.MASH_TUB, MashTubBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(PSBlockEntities.BARREL, BarrelBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(PSBlockEntities.DRYING_TABLE, DryingTableBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(PSBlockEntities.RIFT_JAR, RiftJarBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(PSBlockEntities.BOTTLE_RACK, BottleRackBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(PSBlockEntities.PEYOTE, PeyoteBlockEntityRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), PSBlocks.DISTILLERY, PSBlocks.FLASK);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
                PSBlocks.JUNIPER_SAPLING, PSBlocks.JUNIPER_LEAVES, PSBlocks.FRUITING_JUNIPER_LEAVES, PSBlocks.LATTICE, PSBlocks.WINE_GRAPE_LATTICE,
                PSBlocks.CANNABIS, PSBlocks.HOP, PSBlocks.TOBACCO, PSBlocks.COCA, PSBlocks.COFFEA,
                PSBlocks.MASH_TUB);

        BuiltinItemRendererRegistry.INSTANCE.register(PSItems.RIFT_JAR, RiftJarBlockEntityRenderer::renderStack);
    }
}
