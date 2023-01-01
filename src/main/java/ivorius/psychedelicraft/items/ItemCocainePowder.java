package ivorius.psychedelicraft.items;

import ivorius.psychedelicraft.entities.drugs.DrugInfluence;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;

/**
 * Created by lukas on 14.11.14.
 */
public class ItemCocainePowder extends EdibleItem {
    public ItemCocainePowder(Settings settings, DrugInfluence influence) {
        super(settings, influence);
    }

    @Override
    public UseAction getUseAction(ItemStack par1ItemStack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }
}
