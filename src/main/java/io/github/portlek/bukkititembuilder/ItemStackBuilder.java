/*
 * MIT License
 *
 * Copyright (c) 2020 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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

    /**
     * @return {@link CrossbowItemBuilder}
     * @throws UnsupportedOperationException if server version less than 1.14
     */
    @NotNull
    public CrossbowItemBuilder crossbow() {
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
