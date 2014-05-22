/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import ivorius.psychedelicraft.blocks.PSBlocks;
import ivorius.psychedelicraft.client.rendering.DrugEffectInterpreter;
import ivorius.psychedelicraft.client.rendering.SmoothCameraHelper;
import ivorius.psychedelicraft.client.rendering.shaders.DrugShaderHelper;
import ivorius.psychedelicraft.entities.DrugHelper;
import ivorius.psychedelicraft.entities.EntityRealityRift;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

/**
 * Created by lukas on 18.02.14.
 */
public class PSEventHandler
{
    public void register()
    {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent event)
    {
        if ((event.type == TickEvent.Type.CLIENT || event.type == TickEvent.Type.SERVER) && event.phase == TickEvent.Phase.END)
        {
            for (FMLInterModComms.IMCMessage message : FMLInterModComms.fetchRuntimeMessages(Psychedelicraft.instance))
            {
                Psychedelicraft.communicationHandler.onIMCMessage(message, event.type == TickEvent.Type.SERVER, true);
            }
        }

        if (event.type == TickEvent.Type.CLIENT && event.phase == TickEvent.Phase.START)
        {
            DrugShaderHelper.update();
        }

        if (event.type == TickEvent.Type.RENDER && event.phase == TickEvent.Phase.START)
        {
            PSBlocks.psycheLeaves.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END)
        {
            DrugHelper drugHelper = DrugHelper.getDrugHelper(event.player);

            drugHelper.updateDrugEffects(event.player);

            if (!event.player.getEntityWorld().isRemote && Psychedelicraft.spawnRifts)
            {
                if (event.player.getRNG().nextInt(20 * 60 * 100) == 0)
                {
                    spawnRiftAtPlayer(event.player);
                }
            }
        }
    }

    public static void spawnRiftAtPlayer(EntityPlayer player)
    {
        EntityRealityRift rift = new EntityRealityRift(player.getEntityWorld());

        double xP = (player.getRNG().nextDouble() - 0.5) * 100.0;
        double yP = (player.getRNG().nextDouble() - 0.5) * 100.0;
        double zP = (player.getRNG().nextDouble() - 0.5) * 100.0;

        rift.setPosition(player.posX + xP, player.posY + yP, player.posZ + zP);
        player.getEntityWorld().spawnEntityInWorld(rift);
    }

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event)
    {
        Object[] args = event.component.getFormatArgs();

        if (args.length > 1)
        {
            DrugHelper drugHelper = DrugHelper.getDrugHelper(event.player);

            if (drugHelper != null)
            {
                String modified = drugHelper.drugMessageDistorter.distortOutgoingMessage(drugHelper, event.player, event.player.getRNG(), event.message);
                args[1] = modified;
            }
        }
    }

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event)
    {
        if (event.message instanceof ChatComponentText)
        {
            ChatComponentText text = (ChatComponentText) event.message;

            EntityLivingBase renderEntity = Minecraft.getMinecraft().renderViewEntity;
            DrugHelper drugHelper = DrugHelper.getDrugHelper(renderEntity);

            if (drugHelper != null)
            {
                String message = text.getUnformattedTextForChat();
                drugHelper.receiveChatMessage(renderEntity, message);
                String modified = drugHelper.drugMessageDistorter.distortIncomingMessage(drugHelper, renderEntity, renderEntity.getRNG(), message);

                event.message = new ChatComponentText(modified);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerSleep(PlayerSleepInBedEvent event)
    {
        DrugHelper drugHelper = DrugHelper.getDrugHelper(event.entityLiving);

        EntityPlayer.EnumStatus status = drugHelper.getDrugSleepStatus();

        if (status != null)
        {
            event.result = status;
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        DrugHelper.getDrugHelper(event.entity); // Initialize drug helper
    }

    @SubscribeEvent
    public void onEntityConstruction(EntityEvent.EntityConstructing event)
    {
        DrugHelper.initInEntity(event.entity);
    }

    @SubscribeEvent
    public void getBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        DrugHelper drugHelper = DrugHelper.getDrugHelper(event.entity);

        if (drugHelper != null)
        {
            event.newSpeed = event.newSpeed * drugHelper.getDigSpeedModifier(event.entityLiving);
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Pre event)
    {
        if (event.type == RenderGameOverlayEvent.ElementType.PORTAL)
        {
            Minecraft mc = Minecraft.getMinecraft();
            EntityLivingBase renderEntity = mc.renderViewEntity;
            DrugHelper drugHelper = DrugHelper.getDrugHelper(renderEntity);

            if (drugHelper != null && drugHelper.drugRenderer != null)
            {
                drugHelper.drugRenderer.renderOverlaysAfterShaders(event.partialTicks, renderEntity, renderEntity.ticksExisted, event.resolution.getScaledWidth(), event.resolution.getScaledHeight(), drugHelper);
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START)
        {
            Minecraft mc = Minecraft.getMinecraft();

            if (mc != null && !mc.isGamePaused())
            {
                DrugHelper drugHelper = DrugHelper.getDrugHelper(mc.renderViewEntity);

                if (drugHelper != null)
                {
                    SmoothCameraHelper.instance.update(mc.gameSettings.mouseSensitivity, DrugEffectInterpreter.getSmoothVision(drugHelper));
                }
            }
        }
    }
}
