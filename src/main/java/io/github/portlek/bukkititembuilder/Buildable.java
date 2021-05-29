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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine buildable objects.
 *
 * @param <X> type of the self class.
 * @param <T> type of the item meta class.
 */
public interface Buildable<X extends Buildable<X, T>, T extends ItemMeta> {

  /**
   * the amount keys.
   */
  String[] AMOUNT_KEYS = {"amount", "quantity", "miktar"};

  /**
   * the base effect keys.
   */
  String[] BASE_EFFECT_KEYS = {"base-effect", "temel-efekt"};

  /**
   * the center keys.
   */
  String[] CENTER_KEYS = {"center", "merkez"};

  /**
   * the color keys.
   */
  String[] COLOR_KEYS = {"color", "renk"};

  /**
   * the creature keys.
   */
  String[] CREATURE_KEYS = {"creature", "yaratik"};

  /**
   * the custom effects keys.
   */
  String[] CUSTOM_EFFECTS_KEYS = {"custom-effects", "ozel-efektler"};

  /**
   * the damage key.
   */
  String[] DAMAGE_KEYS = {"damage", "durability"};

  /**
   * the data keys.
   */
  String[] DATA_KEYS = {"data"};

  /**
   * the display name keys.
   */
  String[] DISPLAY_NAME_KEYS = {"name", "display", "display-name", "isim", "ad"};

  /**
   * the enchantment keys.
   */
  String[] ENCHANTMENT_KEYS = {"enchants", "enchantments", "enchant", "enchantment", "büyü", "büyüler"};

  /**
   * the flag keys.
   */
  String[] FLAG_KEYS = {"flags", "flag"};

  /**
   * the level keys.
   */
  String[] LEVEL_KEYS = {"level", "seviye"};

  /**
   * the location keys.
   */
  String[] LOCATION_KEYS = {"location", "lokasyon"};

  /**
   * the locked keys.
   */
  String[] LOCKED_KEYS = {"locked", "kitli"};

  /**
   * the lore keys.
   */
  String[] LORE_KEYS = {"lore", "açıklama", "aciklama"};

  /**
   * the map id keys.
   */
  String[] MAP_ID_KEYS = {"map-id"};

  /**
   * the map keys.
   */
  String[] MAP_KEYS = {"map"};

  /**
   * the material keys.
   */
  String[] MATERIAL_KEYS = {"material", "mat", "esya", "eşya", "id"};

  /**
   * the scale keys.
   */
  String[] SCALE_KEYS = {"scale", "olcek"};

  /**
   * the scaling keys.
   */
  String[] SCALING_KEYS = {"scaling", "olcekleme"};

  /**
   * the skull texture keys.
   */
  String[] SKULL_TEXTURE_KEYS = {"skull", "skull-texture", "texture", "skin"};

  /**
   * the tracking position keys.
   */
  String[] TRACKING_POSITION_KEYS = {"tracking-position"};

  /**
   * the unlimited tracking keys.
   */
  String[] UNLIMITED_TRACKING__KEYS = {"unlimited-tracking"};

  /**
   * the view keys.
   */
  String[] VIEW_KEYS = {"view"};

  /**
   * the world keys.
   */
  String[] WORLD_KEYS = {"world"};

  /**
   * the x keys.
   */
  String[] X_KEYS = {"x"};

  /**
   * the z keys.
   */
  String[] Z_KEYS = {"z"};

  /**
   * gets value at the given keys from the map.
   *
   * @param map the map to get.
   * @param tClass the t class to get.
   * @param keys the keys to get.
   * @param <T> type of the getting value.
   *
   * @return value.
   */
  @NotNull
  static <T> Optional<T> getOrDefault(@NotNull final Map<String, Object> map, @NotNull final Class<T> tClass,
                                      @NotNull final String... keys) {
    return Buildable.getOrDefault(map, tClass, new ArrayDeque<>(List.of(keys)));
  }

  /**
   * gets value at the given keys from the map.
   *
   * @param map the map to get.
   * @param tClass the t class to get.
   * @param keys the keys to get.
   * @param <T> type of the getting value.
   *
   * @return value.
   */
  @NotNull
  static <T> Optional<T> getOrDefault(@NotNull final Map<String, Object> map, @NotNull final Class<T> tClass,
                                      @NotNull final Deque<String> keys) {
    final var key = keys.poll();
    if (key == null) {
      return Optional.empty();
    }
    if (!map.containsKey(key)) {
      return Buildable.getOrDefault(map, tClass, keys);
    }
    final var object = map.get(key);
    if (tClass.isAssignableFrom(object.getClass())) {
      // noinspection unchecked
      return Optional.of((T) object);
    }
    return Buildable.getOrDefault(map, tClass, keys);
  }

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
    return this.getItemStack(true);
  }

  /**
   * sets the item stack.
   *
   * @param itemStack the item stack to set.
   *
   * @return {@link #self()} for builder chain.
   */
  @NotNull
  X setItemStack(@NotNull ItemStack itemStack);

  /**
   * obtains the item stack.
   * <p>
   * if the given update is true and if {@link #getItemMeta()} not equals to the current item stack's item meta updates
   * it.
   *
   * @param update the update to obtain.
   *
   * @return item stack.
   */
  @NotNull
  ItemStack getItemStack(final boolean update);

  /**
   * obtains the self instance for builder chain.
   *
   * @return self instance.
   */
  @NotNull
  X self();

  /**
   * serializes the {@link #getItemStack()} into a map.
   *
   * @return serialized map.
   */
  @NotNull
  default Map<String, Object> serialize() {
    final var map = new HashMap<String, Object>();
    final var materialKey = Buildable.MATERIAL_KEYS[0];
    final var amountKey = Buildable.AMOUNT_KEYS[0];
    final var damageKey = Buildable.DAMAGE_KEYS[0];
    final var dataKey = Buildable.DATA_KEYS[0];
    final var displayNameKey = Buildable.DISPLAY_NAME_KEYS[0];
    final var loreKey = Buildable.LORE_KEYS[0];
    final var enchantmentKey = Buildable.ENCHANTMENT_KEYS[0];
    final var flagKey = Buildable.FLAG_KEYS[0];
    final var itemStack = this.getItemStack();
    map.put(materialKey, itemStack.getType().toString());
    if (itemStack.getAmount() != 1) {
      map.put(amountKey, itemStack.getAmount());
    }
    if ((int) itemStack.getDurability() != 0) {
      map.put(damageKey, (int) itemStack.getDurability());
    }
    if (Builder.VERSION < 13) {
      Optional.ofNullable(itemStack.getData())
        .filter(materialData -> (int) materialData.getData() != 0)
        .ifPresent(materialData ->
          map.put(dataKey, (int) materialData.getData()));
    }
    Optional.ofNullable(itemStack.getItemMeta()).ifPresent(itemMeta -> {
      if (itemMeta.hasDisplayName()) {
        map.put(displayNameKey, itemMeta.getDisplayName().replace("§", "&"));
      }
      Optional.ofNullable(itemMeta.getLore()).ifPresent(lore ->
        map.put(loreKey,
          lore.stream()
            .map(s -> s.replace("§", "&"))
            .collect(Collectors.toList())));
      final var flags = itemMeta.getItemFlags();
      if (!flags.isEmpty()) {
        map.put(flagKey, flags.stream()
          .map(Enum::name)
          .collect(Collectors.toList()));
      }
    });
    final var enchants = itemStack.getEnchantments();
    if (!enchants.isEmpty()) {
      final var enchantments = new HashMap<String, Integer>();
      enchants.forEach((enchantment, integer) ->
        enchantments.put(enchantment.getName(), integer));
      map.put(enchantmentKey, enchantments);
    }
    return map;
  }
}
