package io.github.portlek.bukkititembuilder;

import com.cryptomorin.xseries.XMaterial;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

public final class SkullItemBuilder extends Builder<SkullItemBuilder, SkullMeta> {

    public SkullItemBuilder(@NotNull final ItemStack itemstack, @NotNull final SkullMeta meta) {
        super(itemstack, meta);
    }

    @NotNull
    public SkullItemBuilder owner(@NotNull final String uniqueId) {
        return this.owner(UUID.fromString(uniqueId));
    }

    @NotNull
    public SkullItemBuilder owner(@NotNull final UUID uniqueId) {
        return this.owner(Bukkit.getOfflinePlayer(uniqueId));
    }

    @NotNull
    public SkullItemBuilder owner(@NotNull final OfflinePlayer player) {
        return this.update(meta -> {
            if (XMaterial.isNewVersion()) {
                meta.setOwningPlayer(player);
            } else {
                meta.setOwner(player.getUniqueId().toString());
            }
        });
    }

    @NotNull
    @Override
    public SkullItemBuilder chain() {
        return this;
    }

}
