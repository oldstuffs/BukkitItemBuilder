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

package io.github.portlek.bukkititembuilder.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains utility methods for getting elements from key of {@link Map}.
 */
public final class KeyUtil {

  /**
   * the amount keys.
   */
  public static final String[] AMOUNT_KEYS = {"amount", "quantity", "miktar"};

  /**
   * the author keys.
   */
  public static final String[] AUTHOR_KEYS = {"author"};

  /**
   * the base effect keys.
   */
  public static final String[] BASE_EFFECT_KEYS = {"base-effect", "temel-efekt"};

  /**
   * the base keys.
   */
  public static final String[] BASE_KEYS = {"base"};

  /**
   * the book keys.
   */
  public static final String[] BOOKS_KEYS = {"book"};

  /**
   * the center keys.
   */
  public static final String[] CENTER_KEYS = {"center", "merkez"};

  /**
   * the colors keys.
   */
  public static final String[] COLORS_KEYS = {"colors"};

  /**
   * the color keys.
   */
  public static final String[] COLOR_KEYS = {"color", "renk"};

  /**
   * the creature keys.
   */
  public static final String[] CREATURE_KEYS = {"creature", "yaratik"};

  /**
   * the custom effects keys.
   */
  public static final String[] CUSTOM_EFFECTS_KEYS = {"custom-effects", "ozel-efektler"};

  /**
   * the damage key.
   */
  public static final String[] DAMAGE_KEYS = {"damage", "durability"};

  /**
   * the data keys.
   */
  public static final String[] DATA_KEYS = {"data"};

  /**
   * the display name keys.
   */
  public static final String[] DISPLAY_NAME_KEYS = {"name", "display", "display-name", "isim", "ad"};

  /**
   * the enchantment keys.
   */
  public static final String[] ENCHANTMENT_KEYS = {"enchants", "enchantments", "enchant", "enchantment", "büyü", "büyüler"};

  /**
   * the fade keys.
   */
  public static final String[] FADE_KEYS = {"fade"};

  /**
   * the firework keys.
   */
  public static final String[] FIREWORK_KEYS = {"firework"};

  /**
   * the flag keys.
   */
  public static final String[] FLAG_KEYS = {"flags", "flag"};

  /**
   * the flicker keys.
   */
  public static final String[] FLICKER_KEYS = {"flicker"};

  /**
   * the generation keys.
   */
  public static final String[] GENERATION_KEYS = {"generation"};

  /**
   * the level keys.
   */
  public static final String[] LEVEL_KEYS = {"level", "seviye"};

  /**
   * the location keys.
   */
  public static final String[] LOCATION_KEYS = {"location", "lokasyon"};

  /**
   * the locked keys.
   */
  public static final String[] LOCKED_KEYS = {"locked", "kitli"};

  /**
   * the lore keys.
   */
  public static final String[] LORE_KEYS = {"lore", "açıklama", "aciklama"};

  /**
   * the map id keys.
   */
  public static final String[] MAP_ID_KEYS = {"map-id"};

  /**
   * the map keys.
   */
  public static final String[] MAP_KEYS = {"map"};

  /**
   * the material keys.
   */
  public static final String[] MATERIAL_KEYS = {"material", "mat", "esya", "eşya", "id"};

  /**
   * the pages keys.
   */
  public static final String[] PAGES_KEYS = {"pages"};

  /**
   * the patterns keys.
   */
  public static final String[] PATTERNS_KEYS = {"patterns"};

  /**
   * the power keys.
   */
  public static final String[] POWER_KEYS = {"power"};

  /**
   * the projectiles key.
   */
  public static final String[] PROJECTILES_KEY = {"projectiles"};

  /**
   * the scale keys.
   */
  public static final String[] SCALE_KEYS = {"scale", "olcek"};

  /**
   * the scaling keys.
   */
  public static final String[] SCALING_KEYS = {"scaling", "olcekleme"};

  /**
   * the skull texture keys.
   */
  public static final String[] SKULL_TEXTURE_KEYS = {"skull", "skull-texture", "texture", "skin"};

  /**
   * the title keys.
   */
  public static final String[] TITLE_KEYS = {"title"};

  /**
   * the tracking position keys.
   */
  public static final String[] TRACKING_POSITION_KEYS = {"tracking-position"};

  /**
   * the trail keys.
   */
  public static final String[] TRAIL_KEYS = {"trail"};

  /**
   * the type keys.
   */
  public static final String[] TYPE_KEYS = {"type"};

  /**
   * the unlimited tracking keys.
   */
  public static final String[] UNLIMITED_TRACKING_KEYS = {"unlimited-tracking"};

  /**
   * the view keys.
   */
  public static final String[] VIEW_KEYS = {"view"};

  /**
   * the world keys.
   */
  public static final String[] WORLD_KEYS = {"world"};

  /**
   * the x keys.
   */
  public static final String[] X_KEYS = {"x"};

  /**
   * the z keys.
   */
  public static final String[] Z_KEYS = {"z"};

  /**
   * ctor.
   */
  private KeyUtil() {
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
  public static <T> Optional<T> getOrDefault(@NotNull final Map<String, Object> map, @NotNull final Class<T> tClass,
                                             @NotNull final String... keys) {
    return KeyUtil.getOrDefault(map, tClass, new ArrayDeque<>(List.of(keys)));
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
  public static <T> Optional<T> getOrDefault(@NotNull final Map<String, Object> map, @NotNull final Class<T> tClass,
                                             @NotNull final Deque<String> keys) {
    final var key = keys.poll();
    if (key == null) {
      return Optional.empty();
    }
    if (!map.containsKey(key)) {
      return KeyUtil.getOrDefault(map, tClass, keys);
    }
    final var object = map.get(key);
    if (tClass.isAssignableFrom(object.getClass())) {
      // noinspection unchecked
      return Optional.of((T) object);
    }
    return KeyUtil.getOrDefault(map, tClass, keys);
  }
}
