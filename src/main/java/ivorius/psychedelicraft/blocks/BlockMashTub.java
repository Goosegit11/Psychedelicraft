/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft.blocks;

import ivorius.psychedelicraft.block.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * Created by lukas on 27.10.14.
 */
public class BlockMashTub extends BlockWithFluid<MashTubBlockEntity> {
    public static final int SIZE = 15;
    public static final int BORDER_SIZE = 1;
    public static final int HEIGHT = 16;
    private static final int WIDTH = 32;

    // TODO: (Sollace) MushTub is a 3x3 multi-block and this voxel shape reflects that
    //           x from -15 to +30
    private static final VoxelShape SHAPE = VoxelShapes.union(
            createShape(-16, -0.5F, -16, 32, 16,  1),
            createShape(-16, -0.5F,  15, 32, 16,  1),
            createShape(-16, -0.5F, -16,  1, 16, 32),
            createShape(-16, -0.5F, -16,  1, 16, 32),
            createShape(-16, -0.5F, -16, 32,  1, 32)
    );
    private static VoxelShape createShape(double x, double y, double z, double width, double height, double depth) {
        return Block.createCuboidShape(x, y, z, x + width, y + height, z + depth);
    }

    public BlockMashTub(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected BlockEntityType<MashTubBlockEntity> getBlockEntityType() {
        return PSBlockEntities.MASH_TUB;
    }

    @Override
    protected ActionResult onInteract(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, MashTubBlockEntity blockEntity) {
        if (!blockEntity.solidContents.isEmpty()) {
            Block.dropStack(world, pos, blockEntity.solidContents);
            blockEntity.solidContents = ItemStack.EMPTY;
            blockEntity.markDirty();
            if (!world.isClient) {
                ((ServerWorld)world).getChunkManager().markForUpdate(pos);
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
