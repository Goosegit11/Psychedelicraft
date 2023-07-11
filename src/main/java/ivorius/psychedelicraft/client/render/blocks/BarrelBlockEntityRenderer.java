/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft.client.render.blocks;

import ivorius.psychedelicraft.block.BarrelBlock;
import ivorius.psychedelicraft.block.entity.BarrelBlockEntity;
import ivorius.psychedelicraft.client.render.RenderUtil;
import ivorius.psychedelicraft.fluid.*;
import ivorius.psychedelicraft.fluid.container.Resovoir;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class BarrelBlockEntityRenderer implements BlockEntityRenderer<BarrelBlockEntity> {
    private final BarrelModel model;

    public BarrelBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        model = new BarrelModel(BarrelModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(BarrelBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
        matrices.push();
        matrices.translate(0.5F, 0, 0.5F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180 - entity.getCachedState().get(BarrelBlock.FACING).asRotation()));

        model.setRotationAngles(entity);
        model.render(matrices, vertices.getBuffer(model.getLayer(getBarrelTexture(entity))), light, overlay, 1, 1, 1, 1);

        Resovoir tank = entity.getTank(Direction.UP);

        SimpleFluid fluid = tank.getFluidType();
        if (!fluid.isEmpty()) {
            Identifier symbol = fluid.getSymbol(tank.getStack());

            if (MinecraftClient.getInstance().getResourceManager().getResource(symbol).isPresent()) {
                matrices.translate(0, 0.5, 0);
                float barrelZ = -0.4376F;
                float iconSize = 0.5F;
                VertexConsumer buffer = vertices.getBuffer(model.getLayer(symbol));

                for (int i = 0; i < 2; i++) {
                    RenderUtil.vertex(buffer, matrices, -iconSize, -iconSize, barrelZ, 1, 1, overlay, light);
                    RenderUtil.vertex(buffer, matrices, -iconSize,  iconSize, barrelZ, 1, 0, overlay, light);
                    RenderUtil.vertex(buffer, matrices,  iconSize,  iconSize, barrelZ, 0, 0, overlay, light);
                    RenderUtil.vertex(buffer, matrices,  iconSize, -iconSize, barrelZ, 0, 1, overlay, light);

                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                }
            }
        }

        matrices.pop();
    }

    public static Identifier getBarrelTexture(BarrelBlockEntity barrel) {
        BlockState state = barrel.getCachedState();
        Identifier id = Registries.BLOCK.getId(state.getBlock());
        return new Identifier(id.getNamespace(), "textures/entity/barrel/" + id.getPath() + ".png");
    }
}
