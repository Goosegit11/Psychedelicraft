/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft;

import ivorius.psychedelicraft.entity.drugs.DrugProperties;

/**
 * Created by lukas on 24.05.14.
 */
public class PSProxy {

    private static PSProxy INSTANCE = new PSProxy();

    public static PSProxy getInstance() {
        return INSTANCE;
    }

    protected PSProxy() {
        INSTANCE = this;
    }

    public void createDrugRenderer(DrugProperties drugProperties) {

    }
}
