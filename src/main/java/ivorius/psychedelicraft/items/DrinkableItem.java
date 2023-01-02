/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft.items;

import ivorius.psychedelicraft.fluids.ConsumableFluid;
import ivorius.psychedelicraft.fluids.FluidHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

/**
 * Created by Sollace on Jan 1 2023
 */
public class DrinkableItem extends Item implements FluidContainerItem {
    public static final int FLUID_PER_DRINKING = FluidHelper.MILLIBUCKETS_PER_LITER / 4;

    private final int capacity;

    private final int consumptionVolume;
    private final ConsumableFluid.ConsumptionType consumptionType;

    public DrinkableItem(Settings settings, int capacity, int consumptionVolume, ConsumableFluid.ConsumptionType consumptionType) {
        super(settings.maxCount(1));
        this.capacity = capacity;
        this.consumptionVolume = Math.min(capacity, consumptionVolume);
        this.consumptionType = consumptionType;
    }

    @Override
    public int getMaxCapacity() {
        return capacity;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return getFluid(stack) == Fluids.EMPTY ? UseAction.NONE
                : consumptionType == ConsumableFluid.ConsumptionType.DRINK
                ? UseAction.DRINK
                : UseAction.BOW;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entity) {
        ConsumableFluid.consume(stack, entity, consumptionVolume, !(entity instanceof PlayerEntity p && p.isCreative()), consumptionType);
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (ConsumableFluid.canConsume(stack, player, consumptionVolume, consumptionType)) {
            return TypedActionResult.consume(stack);
        }
        return super.use(world, player, hand);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public Text getName(ItemStack stack) {
        Fluid fluid = getFluid(stack);

        if (fluid != Fluids.EMPTY) {
            Identifier fluidId = Registries.FLUID.getId(fluid);
            return Text.translatable(getTranslationKey() + ".filled", Text.translatable(Util.createTranslationKey("fluid", fluidId)));
        }

        return super.getName(stack);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return getFluid(stack) != Fluids.EMPTY;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        int level = getFluidLevel(stack);
        return level == 0 ? 0 : 1 - (level / getMaxCapacity());
    }
}
