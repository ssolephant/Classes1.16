package com.qsided.classesmod.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsided.classesmod.events.ClassEvents;
import net.minecraft.util.text.StringTextComponent;

import com.qsided.classesmod.ClassesMod;
import com.qsided.classesmod.classes.ClassSelected;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@ClassesMod.GuiElement.Tag
public class ClassSelectorGui extends ClassesMod.GuiElement {
    public static HashMap guistate = new HashMap();
    private static ContainerType<GuiContainerMod> containerType = null;
    public ClassSelectorGui(ClassesMod instance) {
        super(instance, 1);
        guiElements.addNetworkMessage(ButtonPressedMessage.class, ButtonPressedMessage::buffer, ButtonPressedMessage::new,
                ButtonPressedMessage::handler);
        guiElements.addNetworkMessage(GUISlotChangedMessage.class, GUISlotChangedMessage::buffer, GUISlotChangedMessage::new,
                GUISlotChangedMessage::handler);
        containerType = new ContainerType<>(new GuiContainerModFactory());
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
    }

    @OnlyIn(Dist.CLIENT)
    public void initElements() {
        DeferredWorkQueue.runLater(() -> ScreenManager.registerFactory(containerType, GuiWindow::new));
    }

    @SubscribeEvent
    public void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(containerType.setRegistryName("class_selector"));
    }
    public static class GuiContainerModFactory implements IContainerFactory {
        public GuiContainerMod create(int id, PlayerInventory inv, PacketBuffer extraData) {
            return new GuiContainerMod(id, inv, extraData);
        }
    }

    public static class GuiContainerMod extends Container implements Supplier<Map<Integer, Slot>> {
        private World world;
        private PlayerEntity entity;
        private int x, y, z;
        private IItemHandler internal;
        private Map<Integer, Slot> customSlots = new HashMap<>();
        private boolean bound = false;
        public GuiContainerMod(int id, PlayerInventory inv, PacketBuffer extraData) {
            super(containerType, id);
            this.entity = inv.player;
            this.world = inv.player.world;
            this.internal = new ItemStackHandler(0);
            BlockPos pos = null;
            if (extraData != null) {
                pos = extraData.readBlockPos();
                this.x = pos.getX();
                this.y = pos.getY();
                this.z = pos.getZ();
            }
        }

        public Map<Integer, Slot> get() {
            return customSlots;
        }

        @Override
        public boolean canInteractWith(PlayerEntity player) {
            return true;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class GuiWindow extends ContainerScreen<GuiContainerMod> {
        private World world;
        private int x, y, z;
        private PlayerEntity entity;
        public GuiWindow(GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
            super(container, inventory, text);
            this.world = container.world;
            this.x = container.x;
            this.y = container.y;
            this.z = container.z;
            this.entity = container.entity;
            this.xSize = 363;
            this.ySize = 229;
        }

        @Override
        public boolean isPauseScreen() {
            return true;
        }

        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            this.renderBackground(matrixStack);
            super.render(matrixStack, mouseX, mouseY, partialTicks);
            this.renderHoveredToolTip(mouseX, mouseY);
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        private void renderHoveredToolTip(int mouseX, int mouseY) {
        }

        @Override
        public boolean keyPressed(int key, int b, int c) {
            if (key == 256) {
                this.getMinecraft().player.closeScreen();
                return true;
            }
            return super.keyPressed(key, b, c);
        }

        @Override
        public void tick() {
            super.tick();
        }

        @Override
        protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
            this.font.drawString(matrixStack, "Please select a class. You cannot change classes later.", 41, 16, -6684775);
            this.font.drawString(matrixStack, "+10 Woodcutting", 37, 64, -26368);
            this.font.drawString(matrixStack, "+1 Stone Axe", 44, 74, -26368);
            this.font.drawString(matrixStack, "+10 Mining", 159, 63, -10027009);
            this.font.drawString(matrixStack, "+1 Stone Pickaxe", 144, 72, -10027009);
            this.font.drawString(matrixStack, "+10 Combat", 267, 54, -52429);
            this.font.drawString(matrixStack, "+1 Stone Sword", 257, 74, -52429);
            this.font.drawString(matrixStack, "+10 Archery", 41, 134, -205);
            this.font.drawString(matrixStack, "+5 Agility", 49, 124, -205);
            this.font.drawString(matrixStack, "+1 Bow", 56, 144, -205);
            this.font.drawString(matrixStack, "+16 Arrows", 45, 154, -205);
            this.font.drawString(matrixStack, "+5 Crafting", 47, 54, -26368);
            this.font.drawString(matrixStack, "+16 Torches", 152, 82, -10027009);
            this.font.drawString(matrixStack, "+5 Endurance", 262, 64, -52429);
            this.font.drawString(matrixStack, "+5 Excavation", 149, 54, -10027009);
            this.font.drawString(matrixStack, "+5 Cooking", 157, 124, -16715008);
            this.font.drawString(matrixStack, "+10 Farming", 154, 134, -16715008);
            this.font.drawString(matrixStack, "+1 Stone Hoe", 152, 144, -16715008);
            this.font.drawString(matrixStack, "+12 Potatoes", 152, 154, -16715008);
            this.font.drawString(matrixStack, "+5 Crafting", 262, 124, -1);
            this.font.drawString(matrixStack, "+10 Smithing", 259, 134, -1);
            this.font.drawString(matrixStack, "+12 Iron Ingots", 252, 144, -1);
        }

        //@Override
        //public void removed() {
        //    super.removed();
        //    Minecraft.getInstance().keyboardListener.enableRepeatEvents(false);
        //}

        @Override
        public void init(Minecraft minecraft, int width, int height) {
            super.init(minecraft, width, height);
            minecraft.keyboardListener.enableRepeatEvents(true);
            this.addButton(new Button(this.guiLeft + 24, this.guiTop + 32, 100, 20, new StringTextComponent("Lumberjack"), e -> {
                ClassesMod.PACKET_HANDLER.sendToServer(new ButtonPressedMessage(0, x, y, z));
                handleButtonAction(entity, 0, x, y, z);
            }));
            this.addButton(new Button(this.guiLeft + 132, this.guiTop + 32, 100, 20, new StringTextComponent("   Miner  "), e -> {
                ClassesMod.PACKET_HANDLER.sendToServer(new ButtonPressedMessage(1, x, y, z));
                handleButtonAction(entity, 1, x, y, z);
            }));
            this.addButton(new Button(this.guiLeft + 239, this.guiTop + 32, 100, 20, new StringTextComponent("  Warrior "), e -> {
                ClassesMod.PACKET_HANDLER.sendToServer(new ButtonPressedMessage(2, x, y, z));
                handleButtonAction(entity, 2, x, y, z);
            }));
            this.addButton(new Button(this.guiLeft + 24, this.guiTop + 102, 100, 20, new StringTextComponent("  Archer  "), e -> {
                ClassesMod.PACKET_HANDLER.sendToServer(new ButtonPressedMessage(3, x, y, z));
                handleButtonAction(entity, 3, x, y, z);
            }));
            this.addButton(new Button(this.guiLeft + 132, this.guiTop + 102, 100, 20, new StringTextComponent("  Farmer  "), e -> {
                ClassesMod.PACKET_HANDLER.sendToServer(new ButtonPressedMessage(4, x, y, z));
                handleButtonAction(entity, 4, x, y, z);
            }));
            this.addButton(new Button(this.guiLeft + 239, this.guiTop + 102, 100, 20, new StringTextComponent("Blacksmith"), e -> {
                ClassesMod.PACKET_HANDLER.sendToServer(new ButtonPressedMessage(5, x, y, z));
                handleButtonAction(entity, 5, x, y, z);
            }));
        }
    }

    public static class ButtonPressedMessage {
        int buttonID, x, y, z;
        public ButtonPressedMessage(PacketBuffer buffer) {
            this.buttonID = buffer.readInt();
            this.x = buffer.readInt();
            this.y = buffer.readInt();
            this.z = buffer.readInt();
        }

        public ButtonPressedMessage(int buttonID, int x, int y, int z) {
            this.buttonID = buttonID;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public static void buffer(ButtonPressedMessage message, PacketBuffer buffer) {
            buffer.writeInt(message.buttonID);
            buffer.writeInt(message.x);
            buffer.writeInt(message.y);
            buffer.writeInt(message.z);
        }

        public static void handler(ButtonPressedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                PlayerEntity entity = context.getSender();
                int buttonID = message.buttonID;
                int x = message.x;
                int y = message.y;
                int z = message.z;
                handleButtonAction(entity, buttonID, x, y, z);
            });
            context.setPacketHandled(true);
        }
    }

    public static class GUISlotChangedMessage {
        int slotID, x, y, z, changeType, meta;
        public GUISlotChangedMessage(int slotID, int x, int y, int z, int changeType, int meta) {
            this.slotID = slotID;
            this.x = x;
            this.y = y;
            this.z = z;
            this.changeType = changeType;
            this.meta = meta;
        }

        public GUISlotChangedMessage(PacketBuffer buffer) {
            this.slotID = buffer.readInt();
            this.x = buffer.readInt();
            this.y = buffer.readInt();
            this.z = buffer.readInt();
            this.changeType = buffer.readInt();
            this.meta = buffer.readInt();
        }

        public static void buffer(GUISlotChangedMessage message, PacketBuffer buffer) {
            buffer.writeInt(message.slotID);
            buffer.writeInt(message.x);
            buffer.writeInt(message.y);
            buffer.writeInt(message.z);
            buffer.writeInt(message.changeType);
            buffer.writeInt(message.meta);
        }

        public static void handler(GUISlotChangedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                PlayerEntity entity = context.getSender();
                int slotID = message.slotID;
                int changeType = message.changeType;
                int meta = message.meta;
                int x = message.x;
                int y = message.y;
                int z = message.z;
                handleSlotAction(entity, slotID, changeType, meta, x, y, z);
            });
            context.setPacketHandled(true);
        }
    }
    private static void handleButtonAction(PlayerEntity entity, int buttonID, int x, int y, int z) {
        World world = entity.world;
        // security measure to prevent arbitrary chunk generation
        if (!world.isBlockLoaded(new BlockPos(x, y, z)))
            return;
        if (buttonID == 0) {
            {
                Map<String, Object> dependencies1 = new HashMap<>();
                dependencies1.put("entity", entity);
                ClassSelected.setClassLJ(dependencies1);
            }
        }
        if (buttonID == 1) {
            {
                Map<String, Object> dependencies1 = new HashMap<>();
                dependencies1.put("entity", entity);
                ClassSelected.setClassM(dependencies1);
            }
        }
        if (buttonID == 2) {
            {
                Map<String, Object> dependencies1 = new HashMap<>();
                dependencies1.put("entity", entity);
                ClassSelected.setClassW(dependencies1);
            }
        }
        if (buttonID == 3) {
            {
                Map<String, Object> dependencies1 = new HashMap<>();
                dependencies1.put("entity", entity);
                ClassSelected.setClassA(dependencies1);
            }
        }
        if (buttonID == 4) {
            {
                Map<String, Object> dependencies1 = new HashMap<>();
                dependencies1.put("entity", entity);
                ClassSelected.setClassF(dependencies1);
            }
        }
        if (buttonID == 5) {
            {
                Map<String, Object> dependencies1 = new HashMap<>();
                dependencies1.put("entity", entity);
                ClassSelected.setClassB(dependencies1);
            }
        }
    }

    private static void handleSlotAction(PlayerEntity entity, int slotID, int changeType, int meta, int x, int y, int z) {
        World world = entity.world;
        // security measure to prevent arbitrary chunk generation
        if (!world.isBlockLoaded(new BlockPos(x, y, z)))
            return;
    }
}
