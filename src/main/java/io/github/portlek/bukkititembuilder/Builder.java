/*
 * MIT License
 *
 * Copyright (c) 2021 Hasan Demirtaş
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
import io.github.portlek.bukkititembuilder.util.ColorUtil;
import io.github.portlek.bukkitversion.BukkitVersion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
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

/**
 * an abstract builder class to simplify creation of different item meta builders.
 *
 * @param <X> type of the self class.
 * @param <T> type of the item meta class.
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Builder<X extends Builder<X, T>, T extends ItemMeta> implements Buildable<X, T> {

  /**
   * the bukkit version.
   */
  public static final int VERSION = new BukkitVersion().getMinor();

  /**
   * the meta.
   */
  @NotNull
  @Getter
  private final T itemMeta;

  /**
   * the item stack.
   */
  @NotNull
  @Getter
  private ItemStack itemStack;

  /**
   * adds attribute modifier to the item.
   *
   * @param attribute the attribute to add.
   * @param modifier the modifier to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addAttributeModifier(@NotNull final Attribute attribute,
                                      @NotNull final AttributeModifier modifier) {
    if (Builder.VERSION < 14) {
      return this.self();
    }
    return this.update(itemMeta -> itemMeta.addAttributeModifier(attribute, modifier));
  }

  /**
   * adds attribute modifiers to the item.
   *
   * @param map the map to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addAttributeModifier(@NotNull final Multimap<Attribute, AttributeModifier> map) {
    if (Builder.VERSION < 14) {
      return this.self();
    }
    return this.update(itemMeta -> itemMeta.setAttributeModifiers(map));
  }

  /**
   * adds custom data to the item.
   *
   * @param value the value to add.
   * @param keys the keys to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addCustomData(@NotNull final Object value, @NotNull final Object... keys) {
    final var itemNBTTag = NBTEditor.getNBTCompound(this.itemStack);
    itemNBTTag.set(value, "tag", keys);
    return this.setItemStack(NBTEditor.getItemFromTag(itemNBTTag));
  }

  /**
   * adds unsafe enchantment to the item.
   * <p>
   * the strings have to look like enchantment-id:enchantment-level
   *
   * @param enchantments the enchantments to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addEnchantments(@NotNull final String... enchantments) {
    for (final var enchantmentString : enchantments) {
      final var split = enchantmentString.split(":");
      final var level = new AtomicInteger();
      final String enchantment;
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
        this.addEnchantments(xEnchantment, level.get()));
    }
    return this.self();
  }

  /**
   * adds unsafe enchantment to the item.
   *
   * @param enchantment the enchantment to add.
   * @param level the level to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addEnchantments(@NotNull final XEnchantment enchantment, final int level) {
    return Optional.ofNullable(enchantment.parseEnchantment())
      .map(value -> this.addEnchantments(value, level))
      .orElse(this.self());
  }

  /**
   * adds unsafe enchantment to the item.
   *
   * @param enchantment the enchantment to add.
   * @param level the level to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addEnchantments(@NotNull final Enchantment enchantment, final int level) {
    return this.addEnchantments(new HashMap<>() {{
      this.put(enchantment, level);
    }});
  }

  /**
   * adds unsafe enchantment to the item.
   *
   * @param enchantments the enchantments to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addEnchantments(@NotNull final Map<Enchantment, Integer> enchantments) {
    this.itemStack.addUnsafeEnchantments(enchantments);
    return this.self();
  }

  /**
   * adds item flag to the item.
   *
   * @param flags the flags to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addFlag(@NotNull final ItemFlag... flags) {
    return this.update(itemMeta -> itemMeta.addItemFlags(flags));
  }

  /**
   * adds glow effect to the item.
   * <p>
   * adds the given enchantment to the item..
   * alsoi adds {@link ItemFlag#HIDE_ENCHANTS} to hide enchantment.
   *
   * @param enchantment the enchantment to add.
   * @param level the level to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addGlowEffect(@NotNull final Enchantment enchantment, final int level) {
    this.addFlag(ItemFlag.HIDE_ENCHANTS);
    return this.addEnchantments(enchantment, level);
  }

  /**
   * adds glow effect to the item.
   * <p>
   * adds 1 level of the given enchantment to the item.
   * also, adds {@link ItemFlag#HIDE_ENCHANTS} to hide enchantment.
   *
   * @param enchantment the enchantment to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addGlowEffect(@NotNull final Enchantment enchantment) {
    return this.addGlowEffect(enchantment, 1);
  }

  /**
   * adds glow effect to the item.
   * <p>
   * adds 1 level of {@link Enchantment#ARROW_INFINITE} if the item is BOw otherwise adds {@link Enchantment#LUCK} to
   * the item.
   * also, adds {@link ItemFlag#HIDE_ENCHANTS} to hide enchantment.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addGlowEffect() {
    return Optional.ofNullable(XMaterial.BOW.parseMaterial())
      .map(material ->
        this.addGlowEffect(this.itemStack.getType() != material
          ? Enchantment.ARROW_INFINITE
          : Enchantment.LUCK))
      .orElse(this.self());
  }

  /**
   * adds lore to the item.
   *
   * @param lore the lore to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addLore(@NotNull final String... lore) {
    return this.addLore(true, lore);
  }

  /**
   * adds lore to the item.
   *
   * @param colored the colored to add.
   * @param lore the lore to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addLore(final boolean colored, @NotNull final String... lore) {
    return this.addLore(List.of(lore), colored);
  }

  /**
   * adds lore to the item.
   *
   * @param lore the lore to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addLore(@NotNull final List<String> lore) {
    return this.addLore(lore, true);
  }

  /**
   * adds lore to the item.
   *
   * @param lore the lore to add.
   * @param colored the colored to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addLore(@NotNull final List<String> lore, final boolean colored) {
    return this.update(itemMeta -> {
      final var join = Optional.ofNullable(itemMeta.getLore())
        .orElse(new ArrayList<>());
      join.addAll(colored ? ColorUtil.colored(lore) : lore);
      itemMeta.setLore(join);
    });
  }

  /**
   * removes attribute modifier from the item.
   * <p>
   * runs if {@link #VERSION} is 14 or bigger.
   *
   * @param attribute the attribute to remove.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X removeAttributeModifier(@NotNull final Attribute attribute) {
    if (Builder.VERSION < 14) {
      return this.self();
    }
    return this.update(itemMeta -> itemMeta.removeAttributeModifier(attribute));
  }

  /**
   * removes attribute modifier from the item.
   * <p>
   * runs if {@link #VERSION} is 14 or bigger.
   *
   * @param slot the slot to remove.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X removeAttributeModifier(@NotNull final EquipmentSlot slot) {
    if (Builder.VERSION < 14) {
      return this.self();
    }
    return this.update(itemMeta -> itemMeta.removeAttributeModifier(slot));
  }

  /**
   * removes attribute modifier from the item.
   * <p>
   * runs if {@link #VERSION} is 14 or bigger.
   *
   * @param attribute the attribute to remove.
   * @param modifier the modifier to remove.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X removeAttributeModifier(@NotNull final Attribute attribute,
                                         @NotNull final AttributeModifier modifier) {
    if (Builder.VERSION < 14) {
      return this.self();
    }
    return this.update(itemMeta -> itemMeta.removeAttributeModifier(attribute, modifier));
  }

  /**
   * removes item flag from the item.
   *
   * @param flags the flags to remove.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X removeFlag(@NotNull final ItemFlag... flags) {
    return this.update(itemMeta -> itemMeta.removeItemFlags(flags));
  }

  /**
   * sets amount of the item.
   *
   * @param amount the amount to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setAmount(final int amount) {
    this.itemStack.setAmount(amount);
    return this.self();
  }

  /**
   * sets custom model of the item.
   * <p>
   * runs if {@link #VERSION} is 14 or bigger.
   *
   * @param data the data to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setCustomModelData(@Nullable final Integer data) {
    if (Builder.VERSION < 14) {
      return this.self();
    }
    return this.update(itemMeta -> itemMeta.setCustomModelData(data));
  }

  /**
   * sets data of the item.
   *
   * @param data data to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setData(final byte data) {
    return this.setData(this.itemStack.getType().getNewData(data));
  }

  /**
   * sets data of the item.
   *
   * @param data the data to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setData(@NotNull final MaterialData data) {
    this.itemStack.setData(data);
    return this.self();
  }

  /**
   * sets durability of the item.
   *
   * @param durability the durability to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setDurability(final short durability) {
    this.itemStack.setDurability(durability);
    return this.self();
  }

  /**
   * sets item instance itself.
   *
   * @param itemStack the item stack to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  @Override
  public final X setItemStack(@NotNull final ItemStack itemStack) {
    this.itemStack = itemStack;
    return this.self();
  }

  /**
   * updates the item meta.
   *
   * @param action the consumer to update.
   *
   * @return {@code this} for builder chain.
   */
  @Override
  @NotNull
  public final X update(@NotNull final Consumer<T> action) {
    action.accept(this.itemMeta);
    this.itemStack.setItemMeta(this.itemMeta);
    return this.self();
  }

  /**
   * sets localized name of the item.
   * <p>
   * runs if {@link #VERSION} is 12 or bigger.
   *
   * @param name the name to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setLocalizedName(@Nullable final String name) {
    if (Builder.VERSION < 12) {
      return this.self();
    }
    return this.update(meta -> meta.setLocalizedName(name));
  }

  /**
   * sets lore to the item.
   *
   * @param lore the lore to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setLore(@NotNull final String... lore) {
    return this.setLore(true, lore);
  }

  /**
   * sets lore to the item.
   *
   * @param colored the colored to set.
   * @param lore the lore to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setLore(final boolean colored, @NotNull final String... lore) {
    return this.setLore(List.of(lore), colored);
  }

  /**
   * sets lore to the item.
   *
   * @param lore the lore to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setLore(@NotNull final List<String> lore) {
    return this.setLore(lore, true);
  }

  /**
   * sets lore to the item.
   *
   * @param lore the lore to add.
   * @param colored the colored to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setLore(@NotNull final List<String> lore, final boolean colored) {
    return this.update(itemMeta -> itemMeta.setLore(colored ? ColorUtil.colored(lore) : lore));
  }

  /**
   * sets material of the item.
   *
   * @param material the material to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setMaterial(@NotNull final Material material) {
    this.itemStack.setType(material);
    return this.self();
  }

  /**
   * sets name of the item.
   *
   * @param name the name to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setName(@NotNull final String name) {
    return this.setName(name, true);
  }

  /**
   * sets name of the item.
   *
   * @param name the name to set.
   * @param colored the colored to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setName(@NotNull final String name, final boolean colored) {
    return this.update(itemMeta -> itemMeta.setDisplayName(colored ? ColorUtil.colored(name) : name));
  }

  /**
   * sets type of the item.
   *
   * @param material the material to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setType(@NotNull final Material material) {
    this.itemStack.setType(material);
    return this.self();
  }

  /**
   * sets unbreakable to item.
   * <p>
   * uses nbt editor if {@link #VERSION} is less than 11 otherwise, uses {@link ItemMeta#setUnbreakable(boolean)}.
   *
   * @param unbreakable the unbreakable to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setUnbreakable(final boolean unbreakable) {
    if (Builder.VERSION < 11) {
      return this.setItemStack(NBTEditor.set(
        this.itemStack,
        unbreakable
          ? (byte) 1
          : (byte) 0,
        "Unbreakable"));
    }
    return this.update(itemMeta -> itemMeta.setUnbreakable(unbreakable));
  }

  /**
   * sets version of the item.
   * <p>
   * runs if {@link #VERSION} is 14 or bigger.
   *
   * @param version the version to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X setVersion(final int version) {
    if (Builder.VERSION < 14) {
      return this.self();
    }
    return this.update(itemMeta -> itemMeta.setVersion(version));
  }
}
