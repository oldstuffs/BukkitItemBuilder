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

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Multimap;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import io.github.portlek.bukkititembuilder.util.ColorUtil;
import io.github.portlek.bukkititembuilder.util.MaterialUtil;
import io.github.portlek.bukkitversion.BukkitVersion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an abstract builder class to simplify creation of different item meta builders.
 * <p>
 * serialization:
 * <pre>
 *  material: string (item's type) (for 8 and newer versions)
 *
 *  amount: integer (item's amount) (for 8 and newer versions)
 *
 *  damage: integer (item's damage. known as durability) (for 8 and newer versions)
 *
 *  data: integer (item's data) (for 12 and older versions)
 *
 *  name: string (item's name) (for 8 and newer versions)
 *
 *  lore: string list (item's name) (for 8 and newer versions)
 *    - 'test lore'
 *
 *  enchants: (enchantment section) (for 8 and newer versions)
 *    DAMAGE_ALL: integer (enchantment's level)
 *
 *  flags: (string list) (for 8 and newer versions)
 *    - 'HIDE_ENCHANTS'
 * </pre>
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
   * the default item stack deserializer.
   */
  @Getter(AccessLevel.PROTECTED)
  private static final DefaultItemStackDeserializer defaultItemStackDeserializer = new DefaultItemStackDeserializer();

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
  private ItemStack itemStack;

  /**
   * creates a new item meta deserializer.
   *
   * @param <X> type of the builder class.
   *
   * @return a newly created item meta deserializer.
   */
  @NotNull
  static <X extends Builder<?, ?>> DefaultItemMetaDeserializer<X> getDefaultItemMetaDeserializer(
    @NotNull final X builder) {
    return new DefaultItemMetaDeserializer<>(builder);
  }

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
    if (Builder.VERSION >= 14) {
      this.itemMeta.addAttributeModifier(attribute, modifier);
    }
    return this.self();
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
    if (Builder.VERSION >= 14) {
      this.itemMeta.setAttributeModifiers(map);
    }
    return this.self();
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
    final var compound = NBTEditor.getNBTCompound(this.itemStack);
    compound.set(value, "tag", keys);
    return this.setItemStack(NBTEditor.getItemFromTag(compound));
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
    return this.addEnchantments(Map.of(enchantment, level));
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
    this.itemMeta.addItemFlags(flags);
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
  public final X addFlags(@NotNull final Collection<String> flags) {
    flags.stream()
      .map(ItemFlag::valueOf)
      .forEach(this::addFlag);
    return this.self();
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
    final var join = Optional.ofNullable(this.itemMeta.getLore())
      .orElse(new ArrayList<>());
    join.addAll(colored ? ColorUtil.colored(lore) : lore);
    this.itemMeta.setLore(join);
    return this.self();
  }

  /**
   * adds unsafe enchantment to the item.
   *
   * @param enchantments the enchantments to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final X addSerializedEnchantments(@NotNull final Map<String, Integer> enchantments) {
    enchantments.forEach((enchantmentString, level) ->
      XEnchantment.matchXEnchantment(String.valueOf(enchantmentString))
        .flatMap(enchant -> Optional.ofNullable(enchant.parseEnchantment()))
        .ifPresent(enchantment -> this.addEnchantments(enchantment, level)));
    return this.self();
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
    if (Builder.VERSION >= 14) {
      this.itemMeta.removeAttributeModifier(attribute);
    }
    return this.self();
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
    if (Builder.VERSION >= 14) {
      this.itemMeta.removeAttributeModifier(slot);
    }
    return this.self();
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
    if (Builder.VERSION >= 14) {
      this.itemMeta.removeAttributeModifier(attribute, modifier);
    }
    return this.self();
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
    this.itemMeta.removeItemFlags(flags);
    return this.self();
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
    if (Builder.VERSION >= 14) {
      this.itemMeta.setCustomModelData(data);
    }
    return this.self();
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

  @NotNull
  @Override
  public final ItemStack getItemStack(final boolean update) {
    if (update &&
      !Objects.equals(this.itemStack.getItemMeta(), this.itemMeta)) {
      this.itemStack.setItemMeta(this.itemMeta);
    }
    return this.itemStack;
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
    if (Builder.VERSION >= 12) {
      this.itemMeta.setLocalizedName(name);
    }
    return this.self();
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
    this.itemMeta.setLore(colored ? ColorUtil.colored(lore) : lore);
    return this.self();
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
    this.itemMeta.setDisplayName(colored ? ColorUtil.colored(name) : name);
    return this.self();
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
      return this.setItemStack(NBTEditor.set(this.itemStack, unbreakable ? (byte) 1 : (byte) 0, "Unbreakable"));
    }
    this.itemMeta.setUnbreakable(unbreakable);
    return this.self();
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
    if (Builder.VERSION >= 14) {
      this.itemMeta.setVersion(version);
    }
    return this.self();
  }

  /**
   * a class that represents default deserializer of {@link ItemMeta}.
   *
   * @param <B> type of the builder class.
   */
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class DefaultItemMetaDeserializer<B extends Builder<?, ?>> implements
    Function<@NotNull Map<String, Object>, @NotNull B> {

    /**
     * the builder.
     */
    @NotNull
    private final B builder;

    @NotNull
    @Override
    public B apply(@NotNull final Map<String, Object> map) {
      final var itemStack = this.builder.getItemStack(false);
      final var itemMeta = itemStack.getItemMeta();
      if (itemMeta == null) {
        return this.builder;
      }
      if (itemMeta instanceof SkullMeta) {
        Buildable.getOrDefault(map, String.class, Buildable.SKULL_TEXTURE_KEYS).ifPresent(s ->
          SkullUtils.applySkin(itemMeta, s));
      }
      Buildable.getOrDefault(map, String.class, Buildable.DISPLAY_NAME_KEYS)
        .map(ColorUtil::colored)
        .ifPresent(this.builder::setName);
      Buildable.getOrDefault(map, Collection.class, Buildable.LORE_KEYS)
        .map(list -> (Collection<String>) list)
        .map(ColorUtil::colored)
        .ifPresent(this.builder::setLore);
      Buildable.getOrDefault(map, Map.class, Buildable.ENCHANTMENT_KEYS)
        .map(enchantments -> (Map<String, Integer>) enchantments)
        .ifPresent(this.builder::addSerializedEnchantments);
      Buildable.getOrDefault(map, Collection.class, Buildable.FLAG_KEYS)
        .map(flags -> (Collection<String>) flags)
        .ifPresent(this.builder::addFlags);
      itemStack.setItemMeta(itemMeta);
      return this.builder;
    }
  }

  /**
   * a class that represents default deserializers of {@link ItemStack}.
   */
  public static final class DefaultItemStackDeserializer implements
    Function<@NotNull Map<String, Object>, @NotNull Optional<ItemStack>> {

    @NotNull
    @Override
    public Optional<ItemStack> apply(@NotNull final Map<String, Object> map) {
      final var materialOptional = Buildable.getOrDefault(map, String.class, Buildable.MATERIAL_KEYS)
        .flatMap(MaterialUtil::parseMaterial);
      if (materialOptional.isEmpty()) {
        return Optional.empty();
      }
      final var material = materialOptional.get();
      final int amount = Buildable.getOrDefault(map, Number.class, Buildable.AMOUNT_KEYS)
        .map(Number::intValue)
        .orElse(1);
      final ItemStack itemStack;
      if (Builder.VERSION < 13) {
        itemStack = new ItemStack(material, amount);
        Buildable.getOrDefault(map, Number.class, Buildable.DAMAGE_KEYS)
          .map(Number::shortValue)
          .ifPresent(itemStack::setDurability);
        Buildable.getOrDefault(map, Number.class, Buildable.DATA_KEYS)
          .map(Number::byteValue)
          .map(material::getNewData)
          .ifPresent(itemStack::setData);
      } else {
        itemStack = new ItemStack(material, amount);
        Buildable.getOrDefault(map, Number.class, Buildable.DAMAGE_KEYS).ifPresent(integer ->
          itemStack.setDurability(integer.shortValue()));
      }
      return Optional.of(itemStack);
    }
  }
}
