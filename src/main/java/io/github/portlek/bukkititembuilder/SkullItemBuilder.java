package io.github.portlek.bukkititembuilder;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

public final class SkullItemBuilder extends Builder<SkullItemBuilder, SkullMeta> {

    public SkullItemBuilder(@NotNull final ItemStack itemstack, @NotNull final SkullMeta meta) {
        super(itemstack, meta);
    }

    @NotNull
    public SkullItemBuilder owner(@NotNull final String texture) {
        return this.update(meta ->
            SkullUtils.applySkin(meta, texture));
    }

    @NotNull
    public SkullItemBuilder removeOwner() {
        return this.update(meta -> {
            if (XMaterial.isNewVersion()) {
                meta.setOwningPlayer(null);
            } else {
                meta.setOwner(null);
            }
        });
    }

    @NotNull
    @Override
    public SkullItemBuilder get() {
        return this;
    }

}
