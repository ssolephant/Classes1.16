package com.qsided.classesmod.classes;

import com.qsided.classesmod.ClassesMod;
import harmonised.pmmo.config.Config;
import harmonised.pmmo.skills.Skill;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.HashMap;
import java.util.Map;

import static harmonised.pmmo.skills.Skill.*;
import static net.minecraft.item.Items.*;

public class ClassSelected extends ClassesMod.GuiElement {
    public ClassSelected(ClassesMod instance) {
        super(instance, 2);
    }

    static Map<Skill, Double> classXpBooster = new HashMap<>();

    public static void setClassLJ(Map<String, Object> dependencies1) {
        Entity entity = (Entity) dependencies1.get("entity");
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
            ItemStack giveAxe = new ItemStack(STONE_AXE, (int) (1));
            ItemStack giveLogs = new ItemStack(OAK_LOG, (int) (1));
            if (ClassesSavedData.get(serverPlayer.server).getPlayerClass(serverPlayer) == "NO_CLASS") {
                serverPlayer.sendStatusMessage(new StringTextComponent(TextFormatting.GRAY + "You selected class: " + TextFormatting.GOLD + "Lumberjack"), false);
                Skill.WOODCUTTING.addLevel(serverPlayer.getUniqueID(), 10, "selectedClassLJ", false, true);
                Skill.CRAFTING.addLevel(serverPlayer.getUniqueID(), 5, "selectedClassLJ", false, true);
                giveAxe.setCount((int) 1);
                ItemHandlerHelper.giveItemToPlayer((serverPlayer), giveAxe);
                giveLogs.setCount((int) 12);
                ItemHandlerHelper.giveItemToPlayer((serverPlayer), giveLogs);
                ClassesSavedData.get(serverPlayer.server).setPlayerClass(serverPlayer, "LUMBERJACK");
                classXpBooster.put(WOODCUTTING, 10.0);
                Config.setPlayerXpBoost(serverPlayer, "classes.lumberjack", classXpBooster);
                serverPlayer.closeScreen();
            } else {
                serverPlayer.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "You have already selected a class."), false);
                serverPlayer.closeScreen();
            }
        }
    }

    public static void setClassM(Map<String, Object> dependencies1) {
        Entity entity = (Entity) dependencies1.get("entity");
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
            ItemStack givePickaxe = new ItemStack(Items.STONE_PICKAXE, (int) (1));
            ItemStack giveTorches = new ItemStack(Items.TORCH, (int) (1));
            if (ClassesSavedData.get(serverPlayer.server).getPlayerClass(serverPlayer) == "NO_CLASS") {
                if (entity instanceof PlayerEntity) {
                    serverPlayer.sendStatusMessage(new StringTextComponent(TextFormatting.GRAY + "You selected class: " + TextFormatting.AQUA + "Miner"), false);
                    MINING.addLevel(serverPlayer.getUniqueID(), 10, "something", false, true);
                    Skill.EXCAVATION.addLevel(serverPlayer.getUniqueID(), 5, "something1", false, true);
                    givePickaxe.setCount((int) 1);
                    ItemHandlerHelper.giveItemToPlayer((serverPlayer), givePickaxe);
                    giveTorches.setCount((int) 16);
                    ItemHandlerHelper.giveItemToPlayer((serverPlayer), giveTorches);
                    ClassesSavedData.get(serverPlayer.server).setPlayerClass(serverPlayer, "MINER");
                    classXpBooster.put(MINING, 10.0);
                    Config.setPlayerXpBoost(serverPlayer, "classes.miner", classXpBooster);
                    serverPlayer.closeScreen();

                }
            } else {
                serverPlayer.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "You have already selected a class."), false);
                serverPlayer.closeScreen();
            }
        }
    }

    public static void setClassW(Map<String, Object> dependencies1) {
        Entity entity = (Entity) dependencies1.get("entity");
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
            ItemStack giveStoneSword = new ItemStack(STONE_SWORD, (int) (1));
            ItemStack giveShield = new ItemStack(SHIELD, (int) (1));
            if (ClassesSavedData.get(serverPlayer.server).getPlayerClass(serverPlayer) == "NO_CLASS") {
                if (entity instanceof PlayerEntity) {
                    serverPlayer.sendStatusMessage(new StringTextComponent(TextFormatting.GRAY + "You selected class: " + TextFormatting.RED + "Warrior"), false);
                    Skill.COMBAT.addLevel(serverPlayer.getUniqueID(), 10, "something", false, true);
                    Skill.ENDURANCE.addLevel(serverPlayer.getUniqueID(), 5, "something1", false, true);
                    giveStoneSword.setCount((int) 1);
                    ItemHandlerHelper.giveItemToPlayer((serverPlayer), giveStoneSword);
                    giveShield.setCount((int) 1);
                    ItemHandlerHelper.giveItemToPlayer((serverPlayer), giveShield);
                    ClassesSavedData.get(serverPlayer.server).setPlayerClass(serverPlayer, "WARRIOR");
                    classXpBooster.put(COMBAT, 10.0);
                    Config.setPlayerXpBoost(serverPlayer, "classes.warrior", classXpBooster);
                    Float speed = serverPlayer.abilities.getWalkSpeed();
                    serverPlayer.abilities.setWalkSpeed((float) (speed - 0.05));
                    ClassesMod.LOGGER.info("Successfully set player's speed to " + "[" + speed + "]");
                    serverPlayer.closeScreen();
                }
            } else {
                serverPlayer.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "You have already selected a class."), false);
                serverPlayer.closeScreen();
            }
        }
    }

    public static void setClassA(Map<String, Object> dependencies1) {
        Entity entity = (Entity) dependencies1.get("entity");
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
            ItemStack giveBow = new ItemStack(Items.BOW, (int) (1));
            ItemStack giveArrows = new ItemStack(Items.ARROW, (int) (1));
            if (ClassesSavedData.get(serverPlayer.server).getPlayerClass(serverPlayer) == "NO_CLASS") {
                if (entity instanceof PlayerEntity) {
                    serverPlayer.sendStatusMessage(new StringTextComponent(TextFormatting.GRAY + "You selected class: " + TextFormatting.YELLOW + "Archer"), false);
                    Skill.ARCHERY.addLevel(serverPlayer.getUniqueID(), 10, "something", false, true);
                    Skill.AGILITY.addLevel(serverPlayer.getUniqueID(), 5, "something1", false, true);
                    giveBow.setCount((int) 1);
                    ItemHandlerHelper.giveItemToPlayer((serverPlayer), giveBow);
                    giveArrows.setCount((int) 16);
                    ItemHandlerHelper.giveItemToPlayer((serverPlayer), giveArrows);
                    ClassesSavedData.get(serverPlayer.server).setPlayerClass(serverPlayer, "ARCHER");
                    classXpBooster.put(ARCHERY, 10.0);
                    Config.setPlayerXpBoost(serverPlayer, "classes.archer", classXpBooster);
                    serverPlayer.closeScreen();
                }
            } else {
                serverPlayer.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "You have already selected a class."), false);
                serverPlayer.closeScreen();
            }
        }
    }
    public static void setClassF(Map<String, Object> dependencies1) {
        Entity entity = (Entity) dependencies1.get("entity");
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
            ItemStack givePotatoes = new ItemStack(POTATO, (int) (1));
            ItemStack giveHoe = new ItemStack(STONE_HOE, (int) (1));
            if (ClassesSavedData.get(serverPlayer.server).getPlayerClass(serverPlayer) == "NO_CLASS") {
                if (entity instanceof PlayerEntity) {
                    serverPlayer.sendStatusMessage(new StringTextComponent(TextFormatting.GRAY + "You selected class: " + TextFormatting.GREEN + "Farmer"), false);
                    Skill.FARMING.addLevel(serverPlayer.getUniqueID(), 10, "something", false, true);
                    Skill.COOKING.addLevel(serverPlayer.getUniqueID(), 5, "something1", false, true);
                    giveHoe.setCount((int) 1);
                    ItemHandlerHelper.giveItemToPlayer((serverPlayer), giveHoe);
                    givePotatoes.setCount((int) 12);
                    ItemHandlerHelper.giveItemToPlayer((serverPlayer), givePotatoes);
                    ClassesSavedData.get(serverPlayer.server).setPlayerClass(serverPlayer, "FARMER");
                    classXpBooster.put(FARMING, 10.0);
                    Config.setPlayerXpBoost(serverPlayer, "classes.farmer", classXpBooster);
                    serverPlayer.closeScreen();
                }
            } else {
                serverPlayer.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "You have already selected a class."), false);
                serverPlayer.closeScreen();
            }
        }
    }
    public static void setClassB(Map<String, Object> dependencies1) {
        Entity entity = (Entity) dependencies1.get("entity");
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
            ItemStack giveIronIngots = new ItemStack(IRON_INGOT, (int) (1));
            ItemStack giveAnvil = new ItemStack(ANVIL, (int) (1));
            if (ClassesSavedData.get(serverPlayer.server).getPlayerClass(serverPlayer) == "NO_CLASS") {
                if (entity instanceof PlayerEntity) {
                    serverPlayer.sendStatusMessage(new StringTextComponent(TextFormatting.GRAY + "You selected class: " + TextFormatting.WHITE + "Blacksmith"), false);
                    Skill.SMITHING.addLevel(serverPlayer.getUniqueID(), 10, "something", false, true);
                    Skill.CRAFTING.addLevel(serverPlayer.getUniqueID(), 10, "something1", false, true);
                    giveAnvil.setCount((int) 1);
                    ItemHandlerHelper.giveItemToPlayer((serverPlayer), giveAnvil);
                    giveIronIngots.setCount((int) 12);
                    ItemHandlerHelper.giveItemToPlayer((serverPlayer), giveIronIngots);
                    ClassesSavedData.get(serverPlayer.server).setPlayerClass(serverPlayer, "BLACKSMITH");
                    classXpBooster.put(SMITHING, 10.0);
                    Config.setPlayerXpBoost(serverPlayer, "classes.smithing", classXpBooster);
                    serverPlayer.closeScreen();
                }
            } else {
                serverPlayer.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "You have already selected a class."), false);
                serverPlayer.closeScreen();
            }
        }
    }
}
