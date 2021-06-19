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

import io.github.portlek.bukkititembuilder.util.KeyUtil;
import io.github.portlek.replaceable.RpList;
import io.github.portlek.replaceable.RpString;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an interface to determine buildable objects.
 *
 * @param <X> type of the self class.
 * @param <T> type of the item meta class.
 */
public interface Buildable<X extends Buildable<X, T>, T extends ItemMeta> {

  /**
   * creates a new {@link BannerItemBuilder} instance.
   *
   * @return a newly created {@link BannerItemBuilder} instance.
   */
  @NotNull
  default BannerItemBuilder asBanner() {
    return new BannerItemBuilder(this.validateMeta(BannerMeta.class), this.getItemStack(false));
  }

  /**
   * creates a new {@link BookItemBuilder} instance.
   *
   * @return a newly created {@link BookItemBuilder} instance.
   */
  @NotNull
  default BookItemBuilder asBook() {
    return new BookItemBuilder(this.validateMeta(BookMeta.class), this.getItemStack(false));
  }

  /**
   * creates a new {@link CrossbowItemBuilder} instance.
   *
   * @return a newly created {@link CrossbowItemBuilder} instance.
   *
   * @throws IllegalStateException if server version less than 1.14
   */
  @NotNull
  default CrossbowItemBuilder asCrossbow() {
    if (Builder.VERSION < 14) {
      throw new IllegalStateException("This method is for only 14 and newer versions!");
    }
    return new CrossbowItemBuilder(this.validateMeta(CrossbowMeta.class), this.getItemStack(false));
  }

  /**
   * creates a new {@link FireworkItemBuilder} instance.
   *
   * @return a newly created {@link FireworkItemBuilder} instance.
   */
  @NotNull
  default FireworkItemBuilder asFirework() {
    return new FireworkItemBuilder(this.validateMeta(FireworkMeta.class), this.getItemStack(false));
  }

  /**
   * creates a new {@link LeatherArmorItemBuilder} instance.
   *
   * @return a newly created {@link LeatherArmorItemBuilder} instance.
   */
  @NotNull
  default LeatherArmorItemBuilder asLeatherArmor() {
    return new LeatherArmorItemBuilder(this.validateMeta(LeatherArmorMeta.class), this.getItemStack(false));
  }

  /**
   * creates a new {@link MapItemBuilder} instance.
   *
   * @return a newly created {@link MapItemBuilder} instance.
   */
  @NotNull
  default MapItemBuilder asMap() {
    return new MapItemBuilder(this.validateMeta(MapMeta.class), this.getItemStack(false));
  }

  /**
   * creates a new {@link PotionItemBuilder} instance.
   *
   * @return a newly created {@link PotionItemBuilder} instance.
   */
  @NotNull
  default PotionItemBuilder asPotion() {
    return new PotionItemBuilder(this.validateMeta(PotionMeta.class), this.getItemStack(false));
  }

  /**
   * creates a new {@link SkullItemBuilder} instance.
   *
   * @return a newly created {@link SkullItemBuilder} instance.
   */
  @NotNull
  default SkullItemBuilder asSkull() {
    return new SkullItemBuilder(this.validateMeta(SkullMeta.class), this.getItemStack(false));
  }

  /**
   * creates a new {@link SpawnEggItemBuilder} instance.
   *
   * @return a newly created {@link SpawnEggItemBuilder} instance.
   */
  @NotNull
  default SpawnEggItemBuilder asSpawnEgg() {
    if (Builder.VERSION < 11) {
      throw new IllegalStateException("This method is for only 11 and newer versions!");
    }
    return new SpawnEggItemBuilder(this.validateMeta(SpawnEggMeta.class), this.getItemStack(false));
  }

  /**
   * obtains the dynamic lore.
   *
   * @return dynamic lore.
   */
  @Nullable
  RpList getDynamicLore();

  /**
   * obtains the dynamic name.
   *
   * @return dynamic name.
   */
  @Nullable
  RpString getDynamicName();

  /**
   * obtains the item meta.
   *
   * @return item meta.
   */
  @NotNull
  T getItemMeta();

  /**
   * obtains the item stack.
   * <p>
   * if {@link #getItemMeta()} not equals to the current item stack's item meta updates it.
   *
   * @return item stack.
   */
  @NotNull
  default ItemStack getItemStack() {
    return this.getItemStack(Collections.emptyMap());
  }

  /**
   * sets the item stack.
   *
   * @param itemStack the item stack to set.
   *
   * @return {@link #getSelf()} for builder chain.
   */
  @NotNull
  X setItemStack(@NotNull ItemStack itemStack);

  /**
   * obtains the item stack.
   * <p>
   * if {@link #getItemMeta()} not equals to the current item stack's item meta updates it.
   *
   * @param entries the entries to get.
   *
   * @return item stack.
   */
  @NotNull
  default ItemStack getItemStack(@NotNull final Map<String, Supplier<String>> entries) {
    return this.getItemStack(entries, entries);
  }

  /**
   * obtains the item stack.
   * <p>
   * if {@link #getItemMeta()} not equals to the current item stack's item meta updates it.
   *
   * @param nameEntries the name entries to get.
   * @param loreEntries the lore entries to get.
   *
   * @return item stack.
   */
  @NotNull
  default ItemStack getItemStack(@NotNull final Map<String, Supplier<String>> nameEntries,
                                 @NotNull final Map<String, Supplier<String>> loreEntries) {
    return this.getItemStack(nameEntries, loreEntries, true);
  }

  /**
   * obtains the item stack.
   * <p>
   * if the given update is true and if {@link #getItemMeta()} not equals to the current item stack's item meta, updates
   * it.
   *
   * @param update the update to get.
   *
   * @return item stack.
   */
  @NotNull
  default ItemStack getItemStack(final boolean update) {
    return this.getItemStack(Collections.emptyMap(), update);
  }

  /**
   * obtains the item stack.
   * <p>
   * if the given update is true and if {@link #getItemMeta()} not equals to the current item stack's item meta, updates
   * it.
   *
   * @param entries the entries to get.
   * @param update the update to get.
   *
   * @return item stack.
   */
  @NotNull
  default ItemStack getItemStack(@NotNull final Map<String, Supplier<String>> entries, final boolean update) {
    return this.getItemStack(entries, entries, update);
  }

  /**
   * obtains the item stack.
   * <p>
   * if the given update is true and if {@link #getItemMeta()} not equals to the current item stack's item meta, updates
   * it.
   *
   * @param nameEntries the name entries to get.
   * @param loreEntries the lore entries to get.
   * @param update the update to get.
   *
   * @return item stack.
   */
  @NotNull
  ItemStack getItemStack(@NotNull Map<String, Supplier<String>> nameEntries,
                         @NotNull Map<String, Supplier<String>> loreEntries, boolean update);

  /**
   * obtains the self instance for builder chain.
   *
   * @return self instance.
   */
  @NotNull
  X getSelf();

  /**
   * checks if the {@link BannerMeta} class is assignable from {@link #getItemMeta()}'s class.
   *
   * @return {@code true} if the {@link BannerMeta} class is assignable from the item meta's class.
   */
  default boolean isBanner() {
    return this.isMeta(BannerMeta.class);
  }

  /**
   * checks if the {@link BookMeta} class is assignable from {@link #getItemMeta()}'s class.
   *
   * @return {@code true} if the {@link BookMeta} class is assignable from the item meta's class.
   */
  default boolean isBook() {
    return this.isMeta(BookMeta.class);
  }

  /**
   * checks if the {@link CrossbowMeta} class is assignable from {@link #getItemMeta()}'s class.
   *
   * @return {@code true} if the {@link CrossbowMeta} class is assignable from the item meta's class.
   */
  default boolean isCrossbow() {
    return Builder.VERSION >= 14 && this.isMeta(CrossbowMeta.class);
  }

  /**
   * checks if the {@link FireworkMeta} class is assignable from {@link #getItemMeta()}'s class.
   *
   * @return {@code true} if the {@link FireworkMeta} class is assignable from the item meta's class.
   */
  default boolean isFirework() {
    return this.isMeta(FireworkMeta.class);
  }

  /**
   * checks if the {@link LeatherArmorMeta} class is assignable from {@link #getItemMeta()}'s class.
   *
   * @return {@code true} if the {@link LeatherArmorMeta} class is assignable from the item meta's class.
   */
  default boolean isLeatherArmor() {
    return this.isMeta(LeatherArmorMeta.class);
  }

  /**
   * checks if the {@link MapMeta} class is assignable from {@link #getItemMeta()}'s class.
   *
   * @return {@code true} if the {@link MapMeta} class is assignable from the item meta's class.
   */
  default boolean isMap() {
    return this.isMeta(MapMeta.class);
  }

  /**
   * checks if the given meta class is assignable from {@link #getItemMeta()}'s class.
   *
   * @param meta the meta to check.
   * @param <I> type of the item meta.
   *
   * @return {@code true} if the given meta is assignable from the item meta's class.
   */
  default <I extends ItemMeta> boolean isMeta(@NotNull final Class<I> meta) {
    return meta.isAssignableFrom(this.getItemMeta().getClass());
  }

  /**
   * checks if the {@link PotionMeta} class is assignable from {@link #getItemMeta()}'s class.
   *
   * @return {@code true} if the {@link PotionMeta} class is assignable from the item meta's class.
   */
  default boolean isPotion() {
    return this.isMeta(PotionMeta.class);
  }

  /**
   * checks if the {@link SkullMeta} class is assignable from {@link #getItemMeta()}'s class.
   *
   * @return {@code true} if the {@link SkullMeta} class is assignable from the item meta's class.
   */
  default boolean isSkull() {
    return this.isMeta(SkullMeta.class);
  }

  /**
   * checks if the {@link SpawnEggMeta} class is assignable from {@link #getItemMeta()}'s class.
   *
   * @return {@code true} if the {@link SpawnEggMeta} class is assignable from the item meta's class.
   */
  default boolean isSpawnEgg() {
    return Builder.VERSION >= 11 && this.isMeta(SpawnEggMeta.class);
  }

  /**
   * serializes the {@link #getItemStack()} into a map.
   *
   * @param holder the holder to serialize.
   */
  default void serialize(@NotNull final KeyUtil.Holder<?> holder) {
    final var itemStack = this.getItemStack();
    holder.add(KeyUtil.MATERIAL_KEY, itemStack.getType().toString(), String.class);
    if (itemStack.getAmount() != 1) {
      holder.add(KeyUtil.AMOUNT_KEY, itemStack.getAmount(), int.class);
    }
    if ((int) itemStack.getDurability() != 0) {
      holder.add(KeyUtil.DAMAGE_KEY, itemStack.getDurability(), short.class);
    }
    if (Builder.VERSION < 13) {
      Optional.ofNullable(itemStack.getData())
        .filter(materialData -> (int) materialData.getData() != 0)
        .ifPresent(materialData ->
          holder.add(KeyUtil.DATA_KEY, materialData.getData(), byte.class));
    }
    final var itemMeta = itemStack.getItemMeta();
    final var dynamicName = this.getDynamicName();
    final var dynamicLore = this.getDynamicLore();
    if (itemMeta != null) {
      if (dynamicName != null) {
        holder.add(KeyUtil.DISPLAY_NAME_KEY, dynamicName.getValue(), String.class);
      } else if (itemMeta.hasDisplayName()) {
        holder.add(KeyUtil.DISPLAY_NAME_KEY, itemMeta.getDisplayName().replace("§", "&"), String.class);
      }
      final var lore = itemMeta.getLore();
      if (dynamicLore != null) {
        holder.addAsCollection(KeyUtil.LORE_KEY, dynamicLore.getValue(), String.class);
      } else if (lore != null) {
        holder.addAsCollection(KeyUtil.LORE_KEY,
          lore.stream()
            .map(s -> s.replace("§", "&"))
            .collect(Collectors.toList()), String.class);
      }
      final var flags = itemMeta.getItemFlags();
      if (!flags.isEmpty()) {
        holder.addAsCollection(KeyUtil.FLAG_KEY, flags.stream()
          .map(Enum::name)
          .collect(Collectors.toList()), String.class);
      }
      final var enchants = itemMeta.getEnchants();
      if (!enchants.isEmpty()) {
        final var enchantments = new HashMap<String, Integer>();
        enchants.forEach((enchantment, integer) ->
          enchantments.put(enchantment.getName(), integer));
        holder.addAsMap(KeyUtil.ENCHANTMENT_KEY, enchantments, String.class, Integer.class);
      }
    } else {
      if (dynamicName != null) {
        holder.add(KeyUtil.DISPLAY_NAME_KEY, dynamicName.getValue(), String.class);
      }
      if (dynamicLore != null) {
        holder.addAsCollection(KeyUtil.LORE_KEY, dynamicLore.getValue(), String.class);
      }
    }
  }

  /**
   * validates the {@link #getItemStack()} if the given item meta class applicable.
   *
   * @param meta the meta to validate.
   * @param <I> type of the item meta.
   *
   * @return validated item meta instance.
   *
   * @throws IllegalArgumentException if the given meta is no assignable from the {@link #getItemMeta()} class.
   */
  @NotNull
  default <I extends ItemMeta> I validateMeta(@NotNull final Class<I> meta) {
    if (!this.isMeta(meta)) {
      throw new IllegalArgumentException(String.format("%s's meta is not a %s!",
        this.getItemStack(false), meta.getSimpleName()));
    }
    //noinspection unchecked
    return (I) this.getItemMeta();
  }
}
