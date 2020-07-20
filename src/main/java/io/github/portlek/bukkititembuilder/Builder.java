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

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Multimap;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import io.github.portlek.bukkititembuilder.util.BukkitVersion;
import io.github.portlek.bukkititembuilder.util.ColorUtil;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Builder<X extends Builder<X, T>, T extends ItemMeta> implements Buildable<X, T> {

    protected static final int VERSION = new BukkitVersion()
        .minor();

    @NotNull
    private final T meta;

    @NotNull
    private ItemStack itemstack;

    protected Builder(@NotNull final ItemStack itemstack, @NotNull final T meta) {
        this.itemstack = itemstack;
        this.meta = meta;
    }

    @Override
    @NotNull
    public final ItemStack itemStack() {
        return this.itemstack;
    }

    @Override
    public final X itemStack(@NotNull final ItemStack itemstack) {
        this.itemstack = itemstack;
        return this.get();
    }

    @Override
    @NotNull
    public final ItemMeta meta() {
        return this.meta;
    }

    @Override
    @NotNull
    public final X update(@NotNull final Consumer<T> consumer) {
        consumer.accept(this.meta);
        this.itemstack.setItemMeta(this.meta);
        return this.get();
    }

    @NotNull
    public final X customData(@NotNull final Object value, @NotNull final Object... keys) {
        final NBTEditor.NBTCompound itemNBTTag = NBTEditor.getNBTCompound(this.itemStack());
        itemNBTTag.set(value, "tag", keys);
        return this.itemStack(NBTEditor.getItemFromTag(itemNBTTag));
    }

    @NotNull
    public final X material(@NotNull final Material material) {
        this.itemstack.setType(material);
        return this.get();
    }

    @NotNull
    public final X localizedName(@Nullable final String name) {
        if (Builder.VERSION < 12) {
            return this.get();
        }
        return this.update(meta ->
            meta.setLocalizedName(name));
    }

    @NotNull
    public final X customModelData(@Nullable final Integer data) {
        if (Builder.VERSION < 14) {
            return this.get();
        }
        return this.update(itemMeta ->
            itemMeta.setCustomModelData(data));
    }

    @NotNull
    public final X unbreakable(final Boolean unbreakable) {
        if (Builder.VERSION < 11) {
            return this.itemStack(NBTEditor.set(
                this.itemStack(),
                unbreakable
                    ? new Integer(1).byteValue()
                    : new Integer(0).byteValue(),
                "Unbreakable"));
        }
        return this.update(itemMeta ->
            itemMeta.setUnbreakable(unbreakable));
    }

    @NotNull
    public final X glow() {
        return Optional.ofNullable(XMaterial.BOW.parseMaterial())
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(material ->
                this.glow(this.itemStack().getType() != material
                    ? Enchantment.ARROW_INFINITE
                    : Enchantment.LUCK))
            .orElse(this.get());
    }

    @NotNull
    public final X glow(@NotNull final Enchantment enchantment) {
        return this.glow(enchantment, 1);
    }

    @NotNull
    public final X glow(@NotNull final Enchantment enchantment, final int level) {
        this.flag(ItemFlag.HIDE_ENCHANTS);
        return this.enchantments(enchantment, level);
    }

    @NotNull
    public final X addAttributeModifier(@NotNull final Attribute attribute,
                                        @NotNull final AttributeModifier modifier) {
        if (Builder.VERSION < 14) {
            return this.get();
        }
        return this.update(itemMeta ->
            itemMeta.addAttributeModifier(attribute, modifier));
    }

    @NotNull
    public final X addAttributeModifier(@NotNull final Multimap<Attribute, AttributeModifier> map) {
        if (Builder.VERSION < 14) {
            return this.get();
        }
        return this.update(itemMeta ->
            itemMeta.setAttributeModifiers(map));
    }

    @NotNull
    public final X removeAttributeModifier(@NotNull final Attribute attribute) {
        if (Builder.VERSION < 14) {
            return this.get();
        }
        return this.update(itemMeta ->
            itemMeta.removeAttributeModifier(attribute));
    }

    @NotNull
    public final X removeAttributeModifier(@NotNull final EquipmentSlot slot) {
        if (Builder.VERSION < 14) {
            return this.get();
        }
        return this.update(itemMeta ->
            itemMeta.removeAttributeModifier(slot));
    }

    @NotNull
    public final X removeAttributeModifier(@NotNull final Attribute attribute,
                                           @NotNull final AttributeModifier modifier) {
        if (Builder.VERSION < 14) {
            return this.get();
        }
        return this.update(itemMeta ->
            itemMeta.removeAttributeModifier(attribute, modifier));
    }

    @NotNull
    public final X version(final int version) {
        if (Builder.VERSION < 14) {
            return this.get();
        }
        return this.update(itemMeta ->
            itemMeta.setVersion(version));
    }

    @NotNull
    public final X amount(final int size) {
        this.itemStack().setAmount(size);
        return this.get();
    }

    @NotNull
    public final X flag(@NotNull final ItemFlag... flags) {
        return this.update(itemMeta ->
            itemMeta.addItemFlags(flags));
    }

    @NotNull
    public final X name(@NotNull final String name) {
        return this.name(name, true);
    }

    @NotNull
    public final X name(@NotNull final String name, final boolean colored) {
        return this.update(itemMeta ->
            itemMeta.setDisplayName(colored ? ColorUtil.colored(name) : name));
    }

    @NotNull
    public final X data(final byte data) {
        final MaterialData materialData = this.itemStack().getData();
        materialData.setData(data);
        return this.data(materialData);
    }

    @NotNull
    public final X data(@NotNull final MaterialData data) {
        this.itemStack().setData(data);
        return this.get();
    }

    @NotNull
    public final X damage(final short damage) {
        this.itemStack().setDurability(damage);
        return this.get();
    }

    @NotNull
    public final X lore(@NotNull final String... lore) {
        this.update(itemMeta -> itemMeta.setLore(new ArrayList<>()));
        return this.addLore(lore);
    }

    @NotNull
    public final X lore(@NotNull final List<String> lore) {
        this.update(itemMeta -> itemMeta.setLore(new ArrayList<>()));
        return this.addLore(lore);
    }

    @NotNull
    public final X lore(@NotNull final List<String> lore, final boolean colored) {
        this.update(itemMeta -> itemMeta.setLore(new ArrayList<>()));
        return this.addLore(lore, colored);
    }

    @NotNull
    public final X addLore(@NotNull final String... lore) {
        return this.addLore(Arrays.asList(lore), true);
    }

    @NotNull
    public final X addLore(@NotNull final List<String> lore) {
        return this.addLore(lore, true);
    }

    @NotNull
    public final X addLore(@NotNull final List<String> lore, final boolean colored) {
        return this.update(itemMeta -> {
            final List<String> join = Optional.ofNullable(itemMeta.getLore())
                .orElse(new ArrayList<>());
            join.addAll(colored ? ColorUtil.colored(lore) : lore);
            itemMeta.setLore(join);
        });
    }

    @NotNull
    public final X enchantments(@NotNull final String... enchantments) {
        Arrays.stream(enchantments).forEach(enchstring -> {
            final String[] split = enchstring.split(":");
            final String enchantment;
            final AtomicInteger level = new AtomicInteger();
            if (split.length == 1) {
                enchantment = split[0];
                level.set(1);
            } else {
                enchantment = split[0];
                try {
                    level.set(Integer.parseInt(split[1]));
                } catch (final NumberFormatException ignored) {
                }
            }
            XEnchantment.matchXEnchantment(enchantment).ifPresent(xEnchantment ->
                this.enchantments(xEnchantment, level.get()));
        });
        return this.get();
    }

    @NotNull
    public final X enchantments(@NotNull final XEnchantment enchantment, final int level) {
        return Optional.ofNullable(enchantment.parseEnchantment()).map(value ->
            this.enchantments(value, level)).orElse(this.get());
    }

    @NotNull
    public final X enchantments(@NotNull final Enchantment enchantment, final int level) {
        final Map<Enchantment, Integer> map = new HashMap<>();
        map.put(enchantment, level);
        return this.enchantments(map);
    }

    @NotNull
    public final X enchantments(@NotNull final Map<Enchantment, Integer> enchantments) {
        this.itemStack().addUnsafeEnchantments(enchantments);
        return this.get();
    }

}
