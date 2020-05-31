package io.github.portlek.bukkititembuilder;

import com.cryptomorin.xseries.XMaterial;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import java.util.Objects;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.NotNull;

public final class ItemStackBuilder extends Builder<ItemStackBuilder, ItemMeta> {

    private ItemStackBuilder(@NotNull final ItemStack item) {
        super(item,
            Objects.requireNonNull(item.getItemMeta(), "ItemMeta of " + item + " couldn't get!"));
    }

    @NotNull
    public static ItemStackBuilder from(@NotNull final XMaterial material) {
        return ItemStackBuilder.from(
            Optional.ofNullable(material.parseMaterial()).orElseThrow(() ->
                new IllegalStateException("Material from the " + material.name() + " cannot be null!")));
    }

    @NotNull
    public static ItemStackBuilder from(@NotNull final Material material) {
        return ItemStackBuilder.from(new ItemStack(material));
    }

    @NotNull
    public static ItemStackBuilder from(@NotNull final ItemStack from) {
        return new ItemStackBuilder(from);
    }

    @NotNull
    public static ItemStackBuilder from(@NotNull final String nbtjson) {
        return ItemStackBuilder.from(NBTEditor.getItemFromTag(NBTEditor.getNBTCompound(nbtjson)));
    }

    @Override
    @NotNull
    public ItemStackBuilder get() {
        return this;
    }

    @NotNull
    public CrossbowItemBuilder crossbow() throws UnsupportedOperationException {
        if (Builder.VERSION < 14) {
            throw new UnsupportedOperationException("The method called #crosbow() can only use 1.14 and later!");
        }
        return new CrossbowItemBuilder(this.itemStack(), this.validateMeta(CrossbowMeta.class));
    }

    @NotNull
    public MapItemBuilder map() {
        return new MapItemBuilder(this.itemStack(), this.validateMeta(MapMeta.class));
    }

    @NotNull
    public SkullItemBuilder skull() {
        return new SkullItemBuilder(this.itemStack(), this.validateMeta(SkullMeta.class));
    }

    @NotNull
    public BannerItemBuilder banner() {
        return new BannerItemBuilder(this.itemStack(), this.validateMeta(BannerMeta.class));
    }

    @NotNull
    public BookItemBuilder book() {
        return new BookItemBuilder(this.itemStack(), this.validateMeta(BookMeta.class));
    }

    @NotNull
    public FireworkItemBuilder firework() {
        return new FireworkItemBuilder(this.itemStack(), this.validateMeta(FireworkMeta.class));
    }

    @NotNull
    private <T extends ItemMeta> T validateMeta(@NotNull final Class<T> meta) {
        if (!meta.isAssignableFrom(this.meta().getClass())) {
            throw new IllegalStateException(this.itemStack() + "'s meta is not a " + meta.getSimpleName() + '!');
        }
        //noinspection unchecked
        return (T) this.meta();
    }

}
