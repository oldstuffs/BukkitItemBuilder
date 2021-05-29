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

import com.cryptomorin.xseries.XItemStack;
import io.github.portlek.bukkititembuilder.util.KeyUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents crossbow item builders.
 * <p>
 * serialization:
 * <pre>
 * power: integer (firework's power) (for 8 and newer versions)
 *
 * firework: (main section)
 *   0:
 *     type: string (the effect's type) (for 8 and newer versions)
 *
 *     flicker: boolean (the effect flick or not) (for 8 and newer versions)
 *
 *     trail: boolean (the effect has trail or not) (for 8 and newer versions)
 *
 *     colors: (colors section)
 *       base: string list (the effect's base colors) (for 8 and newer versions)
 *         - 'red, green, blue'
 *
 *       fade: string list (the effect's fade colors) (for 8 and newer versions)
 *         - 'red, green, blue'
 * </pre>
 */
public final class FireworkItemBuilder extends Builder<FireworkItemBuilder, FireworkMeta> {

  /**
   * the deserializer.
   */
  private static final Deserializer DESERIALIZER = new Deserializer();

  /**
   * ctor.
   *
   * @param itemMeta the item meta.
   * @param itemStack the item stack.
   */
  FireworkItemBuilder(@NotNull final FireworkMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  /**
   * creates a new firework item builder instance.
   *
   * @param itemMeta the item meta to create.
   * @param itemStack the item stack to create.
   *
   * @return a newly created firework item builder instance.
   */
  @NotNull
  public static FireworkItemBuilder from(@NotNull final FireworkMeta itemMeta, @NotNull final ItemStack itemStack) {
    return new FireworkItemBuilder(itemMeta, itemStack);
  }

  /**
   * creates firework item builder from serialized map.
   *
   * @param map the map to create.
   *
   * @return a newly created firework item builder instance.
   */
  @NotNull
  public static FireworkItemBuilder from(@NotNull final Map<String, Object> map) {
    return FireworkItemBuilder.getDeserializer().apply(map).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given map is incorrect!\n%s", map)));
  }

  /**
   * obtains the deserializer.
   *
   * @return deserializer.
   */
  @NotNull
  public static Deserializer getDeserializer() {
    return FireworkItemBuilder.DESERIALIZER;
  }

  /**
   * adds effect to the firework.
   *
   * @param effect the effect to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public FireworkItemBuilder addEffect(@NotNull final FireworkEffect effect) {
    this.getItemMeta().addEffect(effect);
    return this.getSelf();
  }

  /**
   * adds effect to the firework.
   *
   * @param effects the effects to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public FireworkItemBuilder addEffects(@NotNull final FireworkEffect... effects) {
    this.getItemMeta().addEffects(effects);
    return this.getSelf();
  }

  /**
   * adds effect to the firework.
   *
   * @param effects the effects to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public FireworkItemBuilder addEffects(@NotNull final Iterable<FireworkEffect> effects) {
    this.getItemMeta().addEffects(effects);
    return this.getSelf();
  }

  /**
   * clears effects of the firework.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public FireworkItemBuilder clearEffects() {
    this.getItemMeta().clearEffects();
    return this.getSelf();
  }

  @NotNull
  @Override
  public FireworkItemBuilder getSelf() {
    return this;
  }

  @NotNull
  @Override
  public Map<String, Object> serialize() {
    final var map = super.serialize();
    final var itemMeta = this.getItemMeta();
    final var firework = new HashMap<>();
    map.put(KeyUtil.FIREWORK_KEYS[0], firework);
    map.put(KeyUtil.POWER_KEYS[0], itemMeta.getPower());
    final var effects = itemMeta.getEffects();
    IntStream.range(0, effects.size()).forEach(index -> {
      final var effect = effects.get(index);
      final var section = new HashMap<>();
      firework.put(index, section);
      section.put(KeyUtil.TYPE_KEYS[0], effect.getType().name());
      section.put(KeyUtil.FLICKER_KEYS[0], effect.hasFlicker());
      section.put(KeyUtil.TRAIL_KEYS[0], effect.hasTrail());
      final var fwBaseColors = effect.getColors();
      final var fwFadeColors = effect.getFadeColors();
      final var colors = new HashMap<>();
      section.put(KeyUtil.COLORS_KEYS[0], colors);
      final var baseColors = fwBaseColors.stream()
        .map(color -> String.format("%d, %d, %d", color.getRed(), color.getGreen(), color.getBlue()))
        .collect(Collectors.toCollection(() -> new ArrayList<>(fwBaseColors.size())));
      final var fadeColors = fwFadeColors.stream()
        .map(color -> String.format("%d, %d, %d", color.getRed(), color.getGreen(), color.getBlue()))
        .collect(Collectors.toCollection(() -> new ArrayList<>(fwFadeColors.size())));
      colors.put(KeyUtil.BASE_KEYS[0], baseColors);
      colors.put(KeyUtil.FADE_KEYS[0], fadeColors);
    });
    return map;
  }

  /**
   * removes effect from the firework.
   *
   * @param effectId the effect id to remove.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public FireworkItemBuilder removeEffect(final int effectId) {
    this.getItemMeta().removeEffect(effectId);
    return this.getSelf();
  }

  /**
   * sets power of the firework.
   *
   * @param power the power to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public FireworkItemBuilder setPower(final int power) {
    this.getItemMeta().setPower(power);
    return this.getSelf();
  }

  /**
   * a class that represents deserializer of {@link FireworkMeta}.
   */
  public static final class Deserializer implements
    Function<@NotNull Map<String, Object>, @NotNull Optional<FireworkItemBuilder>> {

    @NotNull
    @Override
    public Optional<FireworkItemBuilder> apply(@NotNull final Map<String, Object> map) {
      final var itemStack = Builder.getItemStackDeserializer().apply(map);
      if (itemStack.isEmpty()) {
        return Optional.empty();
      }
      final var builder = ItemStackBuilder.from(itemStack.get()).asFirework();
      final var power = KeyUtil.getOrDefault(map, Number.class, KeyUtil.POWER_KEYS)
        .orElse(1);
      builder.setPower(power.intValue());
      KeyUtil.getOrDefault(map, Map.class, KeyUtil.FIREWORK_KEYS)
        .map(m -> (Map<String, Object>) m)
        .ifPresent(firework -> {
          final var fireworkBuilder = FireworkEffect.builder();
          firework.forEach((key, value) -> {
            final var fw = (Map<String, Object>) value;
            final var flicker = KeyUtil.getOrDefault(fw, Boolean.class, KeyUtil.FLICKER_KEYS)
              .orElse(false);
            final var trail = KeyUtil.getOrDefault(fw, Boolean.class, KeyUtil.TRAIL_KEYS)
              .orElse(false);
            final var type = KeyUtil.getOrDefault(fw, String.class, KeyUtil.TYPE_KEYS)
              .map(s -> s.toUpperCase(Locale.ROOT));
            FireworkEffect.Type effectType;
            try {
              effectType = type.map(s -> FireworkEffect.Type.valueOf(s)).orElse(FireworkEffect.Type.STAR);
            } catch (final Exception e) {
              effectType = FireworkEffect.Type.STAR;
            }
            fireworkBuilder.flicker(flicker)
              .trail(trail)
              .with(effectType);
            KeyUtil.getOrDefault(fw, Map.class, KeyUtil.COLORS_KEYS)
              .map(m -> (Map<String, Object>) m)
              .ifPresent(colorSection -> {
                final var baseColors = KeyUtil.getOrDefault(colorSection, Collection.class, KeyUtil.BASE_KEYS)
                  .map(collection -> (Collection<String>) collection)
                  .map(strings -> strings.stream()
                    .map(XItemStack::parseColor)
                    .collect(Collectors.toSet()))
                  .orElse(Collections.emptySet());
                final var fadeColors = KeyUtil.getOrDefault(colorSection, Collection.class, KeyUtil.BASE_KEYS)
                  .map(collection -> (Collection<String>) collection)
                  .map(strings -> strings.stream()
                    .map(XItemStack::parseColor)
                    .collect(Collectors.toSet()))
                  .orElse(Collections.emptySet());
                fireworkBuilder.withColor(baseColors);
                fireworkBuilder.withFade(fadeColors);
              });
            builder.addEffect(fireworkBuilder.build());
          });
        });
      return Optional.of(Builder.getItemMetaDeserializer(builder).apply(map));
    }
  }
}
