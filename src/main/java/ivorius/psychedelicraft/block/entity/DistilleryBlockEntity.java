/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft.block.entity;

import ivorius.psychedelicraft.blocks.DistilleryBlock;
import ivorius.psychedelicraft.fluids.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.*;
import net.minecraft.util.math.Direction.Axis;

/**
 * Created by lukas on 25.10.14.
 */
public class DistilleryBlockEntity extends FluidProcessingBlockEntity {
    public static final int DISTILLERY_CAPACITY = FlaskBlockEntity.FLASK_CAPACITY;

    public DistilleryBlockEntity(BlockPos pos, BlockState state) {
        super(PSBlockEntities.DISTILLERY, pos, state, DISTILLERY_CAPACITY, Processable.ProcessType.DISTILL);
    }

    @Override
    protected boolean canProcess(ServerWorld world, int timeNeeded) {
        return super.canProcess(world, timeNeeded)
                && getFacing().getAxis() != Axis.Y
                && DistilleryBlock.canConnectTo(world.getBlockState(getOutputPos()), getFacing())
                && world.getBlockEntity(getOutputPos()) instanceof FlaskBlockEntity;
    }

    @Override
    protected void onProcessCompleted(ServerWorld world, Resovoir tank, ItemStack results) {
        BlockPos outputPos = getOutputPos();
        if (world.getBlockEntity(outputPos) instanceof FlaskBlockEntity destination) {
            Block.dropStack(world, outputPos, destination.getTank(getFacing().getOpposite()).deposit(results));
        } else {
            Block.dropStack(world, outputPos, results);
        }
        super.onProcessCompleted(world, tank, results);
    }

    private BlockPos getOutputPos() {
        return getPos().offset(getFacing());
    }

    private Direction getFacing() {
        return getCachedState().get(DistilleryBlock.FACING);
    }
}
