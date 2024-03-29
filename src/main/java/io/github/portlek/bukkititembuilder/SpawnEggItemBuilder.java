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
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents spawn egg item builders.
 * <p>
 * serialization:
 * <pre>
 * creature: string (entity type) (for 11 and newer versions)
 * </pre>
 */
public final class SpawnEggItemBuilder extends Builder<SpawnEggItemBuilder, SpawnEggMeta> {

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
  SpawnEggItemBuilder(@NotNull final SpawnEggMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  /**
   * creates a new spawn egg item builder instance.
   *
   * @param itemMeta the item meta to create.
   * @param itemStack the item stack to create.
   *
   * @return a newly created spawn egg item builder instance.
   */
  @NotNull
  public static SpawnEggItemBuilder from(@NotNull final SpawnEggMeta itemMeta, @NotNull final ItemStack itemStack) {
    return new SpawnEggItemBuilder(itemMeta, itemStack);
  }

  /**
   * creates spawn egg item builder from serialized holder.
   *
   * @param holder the holder to create.
   *
   * @return a newly created spawn egg item builder instance.
   */
  @NotNull
  public static SpawnEggItemBuilder from(@NotNull final KeyUtil.Holder<?> holder) {
    return SpawnEggItemBuilder.getDeserializer().apply(holder).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given holder is incorrect!\n%s", holder)));
  }

  /**
   * obtains the deserializer.
   *
   * @return deserializer.
   */
  @NotNull
  public static Deserializer getDeserializer() {
    return SpawnEggItemBuilder.DESERIALIZER;
  }

  @NotNull
  @Override
  public SpawnEggItemBuilder getSelf() {
    return this;
  }

  @Override
  public void serialize(@NotNull final KeyUtil.Holder<?> holder) {
    super.serialize(holder);
    final var name = this.getItemMeta().getSpawnedType().getName();
    if (name != null) {
      holder.add(KeyUtil.CREATURE_KEY, name, String.class);
    }
  }

  /**
   * sets spawned type of the spawn egg.
   *
   * @param type the type ot set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  @Deprecated
  public SpawnEggItemBuilder setSpawnedType(@NotNull final EntityType type) {
    this.getItemMeta().setSpawnedType(type);
    return this.getSelf();
  }

  /**
   * sets spawned type of the spawn egg.
   *
   * @param type the type ot set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  @Deprecated
  public SpawnEggItemBuilder setSpawnedType(@NotNull final String type) {
    EntityType entityType;
    try {
      entityType = EntityType.valueOf(type.toUpperCase(Locale.ROOT));
    } catch (final Exception e) {
      entityType = EntityType.BAT;
    }
    return this.setSpawnedType(entityType);
  }

  /**
   * a class that represents deserializer of {@link SpawnEggMeta}.
   */
  public static final class Deserializer implements
    Function<KeyUtil.@NotNull Holder<?>, @NotNull Optional<SpawnEggItemBuilder>> {

    @NotNull
    @Override
    public Optional<SpawnEggItemBuilder> apply(@NotNull final KeyUtil.Holder<?> holder) {
      final var itemStack = Builder.getItemStackDeserializer().apply(holder);
      if (itemStack.isEmpty()) {
        return Optional.empty();
      }
      final var builder = ItemStackBuilder.from(itemStack.get()).asSpawnEgg();
      holder.get(KeyUtil.CREATURE_KEY, String.class)
        .ifPresent(builder::setSpawnedType);
      return Optional.of(Builder.getItemMetaDeserializer(builder).apply(holder));
    }
  }
}
