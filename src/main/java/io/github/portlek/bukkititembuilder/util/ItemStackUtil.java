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

import com.cryptomorin.xseries.XMaterial;
import io.github.portlek.bukkititembuilder.BannerItemBuilder;
import io.github.portlek.bukkititembuilder.BookItemBuilder;
import io.github.portlek.bukkititembuilder.Buildable;
import io.github.portlek.bukkititembuilder.Builder;
import io.github.portlek.bukkititembuilder.CrossbowItemBuilder;
import io.github.portlek.bukkititembuilder.FireworkItemBuilder;
import io.github.portlek.bukkititembuilder.ItemStackBuilder;
import io.github.portlek.bukkititembuilder.LeatherArmorItemBuilder;
import io.github.portlek.bukkititembuilder.MapItemBuilder;
import io.github.portlek.bukkititembuilder.PotionItemBuilder;
import io.github.portlek.bukkititembuilder.SkullItemBuilder;
import io.github.portlek.bukkititembuilder.SpawnEggItemBuilder;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains utility methods for {@link ItemStack}.
 */
public final class ItemStackUtil {

  /**
   * ctor.
   */
  private ItemStackUtil() {
  }

  /**
   * deserializes the given holder into item stack.
   *
   * @param holder the holder to serialize.
   *
   * @return serialized object.
   */
  @NotNull
  public static Optional<ItemStack> deserialize(@NotNull final KeyUtil.Holder<?> holder) {
    return Builder.getSimpleItemStackDeserializer().apply(holder)
      .flatMap(builder -> {
        if (builder.isFirework()) {
          return FireworkItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isLeatherArmor()) {
          return LeatherArmorItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isMap()) {
          return MapItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isPotion()) {
          return PotionItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isBanner()) {
          return BannerItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isBook()) {
          return BookItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isCrossbow()) {
          return CrossbowItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isSkull()) {
          return SkullItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isSpawnEgg()) {
          return SpawnEggItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        return ItemStackBuilder.getDeserializer().apply(holder)
          .map(Buildable::getItemStack);
      });
  }

  /**
   * parses the given material string into a new material.
   *
   * @param materialString the material string to parse.
   *
   * @return parsed material.
   */
  @NotNull
  public static Optional<Material> parseMaterial(@NotNull final String materialString) {
    if (Builder.VERSION <= 7) {
      return Optional.ofNullable(Material.getMaterial(materialString));
    }
    final var xMaterial = XMaterial.matchXMaterial(materialString);
    if (xMaterial.isEmpty()) {
      return Optional.empty();
    }
    final var material = Optional.ofNullable(xMaterial.get().parseMaterial());
    if (material.isEmpty()) {
      return Optional.empty();
    }
    return material;
  }

  /**
   * serializes the given item stack into map.
   *
   * @param itemStack the item stack to serialize.
   * @param holder the holder to serialize.
   */
  public static void serialize(@NotNull final ItemStack itemStack, @NotNull final KeyUtil.Holder<?> holder) {
    final var builder = ItemStackBuilder.from(itemStack);
    if (builder.isFirework()) {
      builder.asFirework().serialize(holder);
    } else if (builder.isLeatherArmor()) {
      builder.asLeatherArmor().serialize(holder);
    } else if (builder.isMap()) {
      builder.asMap().serialize(holder);
    } else if (builder.isPotion()) {
      builder.asPotion().serialize(holder);
    } else if (builder.isBanner()) {
      builder.asBanner().serialize(holder);
    } else if (builder.isBook()) {
      builder.asBook().serialize(holder);
    } else if (builder.isCrossbow()) {
      builder.asCrossbow().serialize(holder);
    } else if (builder.isSkull()) {
      builder.asSkull().serialize(holder);
    } else if (builder.isSpawnEgg()) {
      builder.asSpawnEgg().serialize(holder);
    } else {
      builder.serialize(holder);
    }
  }
}
