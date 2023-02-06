/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft.fluid;

import ivorius.psychedelicraft.Psychedelicraft;
import ivorius.psychedelicraft.entity.drug.DrugType;
import ivorius.psychedelicraft.entity.drug.influence.DrugInfluence;
import ivorius.psychedelicraft.fluid.SimpleFluid.Settings;

/**
 * Created by lukas on 22.10.14.
 */
public interface PSFluids {
    SimpleFluid EMPTY = new SimpleFluid(SimpleFluid.EMPTY_KEY, new Settings().color(0xFFFFFFFF));
    AlcoholicFluid WHEAT_HOP = new AlcoholicFluid(Psychedelicraft.id("wheat_hop"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.25, 1.7, 0.1)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoWheatHop)
            .variants(DrinkTypes.BEER, DrinkTypes.MATURABLE_ICONS)
            .color(0xaafeaa08)
    );
    AlcoholicFluid WHEAT = new AlcoholicFluid(Psychedelicraft.id("wheat"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.25, 1.7, 0.1)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoWheat)
            .variants(DrinkTypes.VODKA_WHISKEY, DrinkTypes.MATURABLE_ICONS)
            .color(0xaafeaa08)
    );
    AlcoholicFluid POTATO = new AlcoholicFluid(Psychedelicraft.id("potato"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.45, 1.9, 0.15)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoPotato)
            .variants(DrinkTypes.BEER_VODKA_WHISKEY, DrinkTypes.MATURABLE_ICONS)
            .color(0xaafeaa08)
    );
    AlcoholicFluid RED_GRAPES = new AlcoholicFluid(Psychedelicraft.id("red_grapes"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.55, 1.7, 0.2)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoRedGrapes)
            .distilledColor(0x993f0822)
            .matureColor(0xee3f0822)
            .variants(DrinkTypes.RED_WINE, DrinkTypes.MATURABLE_ICONS)
            .color(0xaafeaa08)
    );
    AlcoholicFluid RICE = new AlcoholicFluid(Psychedelicraft.id("rice"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.25, 1.7, 0.1)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoRice)
            .matureColor(0x88D6BC90)
            .variants(DrinkTypes.RICE_WINE, DrinkTypes.CLEAR_ICONS)
            .color(0xeecac4b2)
    );
    AlcoholicFluid JUNIPER = new AlcoholicFluid(Psychedelicraft.id("juniper"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.4, 1.7, 0.1)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoJuniper)
            .variants(DrinkTypes.JENEVER, DrinkTypes.MATURABLE_ICONS)
            .color(0xcc704E21)
    );
    AlcoholicFluid HONEY = new AlcoholicFluid(Psychedelicraft.id("honey"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.35, 1.7, 0.1)
            .distilledColor(0x99e9ae3b)
            .matureColor(0xaaD1984D)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoHoney)
            .variants(DrinkTypes.MEAD, DrinkTypes.MATURABLE_ICONS)
            .color(0xbbe9ae3b)
    );
    AlcoholicFluid SUGAR_CANE = new AlcoholicFluid(Psychedelicraft.id("sugar_cane"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.35, 1.7, 0.1)
            .variants(DrinkTypes.RUM, DrinkTypes.MATURABLE_ICONS)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoSugarCane)
            .color(0xaafeaa08)
    );
    AlcoholicFluid CORN = new AlcoholicFluid(Psychedelicraft.id("corn"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.25, 1.7, 0.1)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoCorn)
            .variants(DrinkTypes.BEER_VODKA_WHISKEY, DrinkTypes.MATURABLE_ICONS)
            .color(0xaafeaa08)
    );
    AlcoholicFluid APPLE = new AlcoholicFluid(Psychedelicraft.id("apple"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.35, 1.7, 0.1)
            .distilledColor(0x66EDC13B)
            .matureColor(0x88EDC13B)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoApple)
            .color(0x99EDC13B)
    );
    AlcoholicFluid PINEAPPLE = new AlcoholicFluid(Psychedelicraft.id("pineapple"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.35, 1.7, 0.1)
            .distilledColor(0x66EDC13B)
            .matureColor(0x88EDC13B)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoPineapple)
            .color(0x99EDC13B)
    );
    AlcoholicFluid BANANA = new AlcoholicFluid(Psychedelicraft.id("banana"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.35, 1.7, 0.1)
            .distilledColor(0x99e9ae3b)
            .matureColor(0xaaD1984D)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoBanana)
            .color(0xbbe9ae3b)
    );
    AlcoholicFluid MILK = new AlcoholicFluid(Psychedelicraft.id("milk"), (AlcoholicFluid.Settings)new AlcoholicFluid.Settings()
            .alcohol(0.35, 1.7, 0.1)
            //.distilledColor(0x77cac4b2)
            .matureColor(0x88D6BC90)
            .tickRate(() -> Psychedelicraft.getConfig().balancing.fluidAttributes.alcInfoMilk)
            .color(0xeecac4b2)
            // TODO: (Sollace) Color for milk was set twice. Second time set to 0x77cac4b2 Should it be the distilled colour?
    );

    DrugFluid COFFEE = new CoffeeFluid(Psychedelicraft.id("coffee"), (DrugFluid.Settings)new DrugFluid.Settings()
            .drinkable()
            .color(0xffa77d55)
    );
    DrugFluid COCA_TEA = new DrugFluid(Psychedelicraft.id("coca_tea"), (DrugFluid.Settings)new DrugFluid.Settings()
            .drinkable()
            .influence(new DrugInfluence(DrugType.COCAINE, 60, 0.005, 0.002, 0.2f))
            .color(0x44787a36)
    );
    DrugFluid CANNABIS_TEA = new DrugFluid(Psychedelicraft.id("cannabis_tea"), (DrugFluid.Settings)new DrugFluid.Settings()
            .drinkable()
            .influence(new DrugInfluence(DrugType.CANNABIS, 60, 0.005, 0.002, 0.25f))
            .color(0x446d6f3c)
    );
    DrugFluid PEYOTE_JUICE = new DrugFluid(Psychedelicraft.id("peyote_juice"), (DrugFluid.Settings)new DrugFluid.Settings()
            .drinkable()
            .influence(new DrugInfluence(DrugType.PEYOTE, 15, 0.005, 0.003, 2.0f))
            .color(0x779bab62)
    );

    DrugFluid COCAINE = new DrugFluid(Psychedelicraft.id("cocaine"), (DrugFluid.Settings)new DrugFluid.Settings()
            .injectable()
            .influence(new DrugInfluence(DrugType.COCAINE, 0, 0.005, 0.01, 50.0f))
            .color(0x44e8f4f8)
    );
    DrugFluid CAFFEINE = new DrugFluid(Psychedelicraft.id("caffeine"), (DrugFluid.Settings)new DrugFluid.Settings()
            .injectable()
            .influence(new DrugInfluence(DrugType.CAFFEINE, 0, 0.005, 0.01, 85.0f))
            .color(0x66eee2d3)
    );

    SimpleFluid SLURRY = new SlurryFluid(Psychedelicraft.id("slurry"), new SimpleFluid.Settings().color(0xcc704E21));

    static void bootstrap() {}
}
