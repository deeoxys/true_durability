package me.r333mo.TrueDurability.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Inject(at = @At(value = "RETURN"), method = "render", cancellable = true)
    public void render(MatrixStack matrices, float float_1, CallbackInfo info) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        if (mc.player.getMainHandStack().isDamageable()) {
            ItemStack is = mc.player.getMainHandStack();
            if (is.getOrCreateNbt().contains("dmg")) {
                mc.textRenderer.drawWithShadow(matrices,
                        Formatting.GRAY + "True Durability: " + Formatting.RESET + is.getNbt().get("dmg").asString(),
                        mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth("True Durability: " + is.getNbt().get("dmg").asString()),
                        mc.getWindow().getScaledHeight() - 10,
                        0xFF0000AA);
            }
        }
    }
}
