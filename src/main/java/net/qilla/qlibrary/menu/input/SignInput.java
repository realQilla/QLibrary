package net.qilla.qlibrary.menu.input;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.player.EnhancedPlayer;
import net.qilla.qlibrary.util.tools.CoordUtil;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.block.CraftSign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class SignInput extends PlayerInput {

    private static final int BLOCK_Y_OFFSET = 7;
    private final EnhancedPlayer player;
    private final List<String> signText;
    private final BlockPos blockPos;

    public SignInput(@NotNull Plugin plugin, @NotNull PlayerData<? extends EnhancedPlayer> playerData, @NotNull List<String> signText) {
        super(plugin, playerData);
        Preconditions.checkNotNull(signText, "List cannot be null");
        this.player = playerData.getPlayer();
        this.signText = signText;
        this.blockPos = calcBlockPos(this.player);
    }

    @Override
    public void init(Consumer<String> onComplete) {
        this.openMenu();
        CompletableFuture.supplyAsync(this::awaitResponse, getExecutor())
                .thenAccept(onComplete).thenRun(this::resetBlockState);
    }

    private void resetBlockState() {
        Bukkit.getScheduler().runTask(super.getPlugin(), () -> {
            player.sendPacket(new ClientboundBlockUpdatePacket(blockPos, player.getHandle().serverLevel().getBlockState(blockPos)));
        });
    }

    private void openMenu() {
        CraftSign<SignBlockEntity> sign = createSign(signText);

        Bukkit.getScheduler().runTask(super.getPlugin(), () -> {
            player.sendPacket(new ClientboundBlockUpdatePacket(blockPos, sign.getHandle()));
            player.sendPacket(new ClientboundBlockEntityDataPacket(blockPos, BlockEntityType.SIGN, sign.getUpdateNBT()));
            player.sendPacket(new ClientboundOpenSignEditorPacket(blockPos, true));
        });
    }

    private @NotNull  CraftSign<SignBlockEntity> createSign(@NotNull List<String> text) {
        Preconditions.checkNotNull(text, "Text cannot be null");

        CraftSign<SignBlockEntity> sign = new CraftSign<>(player.getWorld(), new SignBlockEntity(blockPos, Blocks.OAK_SIGN.defaultBlockState()));
        for(int i = 0; i <= 3 && i < text.size(); i++) {
            sign.setLine(i + 1, text.get(i));
        }
        sign.update();
        return sign;
    }

    private @NotNull  BlockPos calcBlockPos(@NotNull Player player) {
        Preconditions.checkNotNull(player, "DPlayer cannot be null");

        return CoordUtil.getBlockPos(player.getLocation()).offset(0, BLOCK_Y_OFFSET, 0);
    }
}