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

import com.cryptomorin.xseries.XMaterial;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import org.bukkit.Material;
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

/**
 * a class that represents regular item stack builders.
 * <p>
 * serialization:
 * <pre>
 * material: string (item's type) (for 8 and newer versions)
 *
 * amount: integer (item's amount) (for 8 and newer versions)
 *
 * damage: integer (item's damage. known as durability) (for 8 and newer versions)
 *
 * data: integer (item's data) (for 12 and older versions)
 *
 * name: string (item's name) (for 8 and newer versions)
 *
 * lore: string list (item's name) (for 8 and newer versions)
 *   - 'test lore'
 *
 * enchants: (enchantment section) (for 8 and newer versions)
 *   DAMAGE_ALL: integer (enchantment's level)
 *
 * flags: (string list) (for 8 and newer versions)
 *   - 'HIDE_ENCHANTS'
 * </pre>
 */
public final class ItemStackBuilder extends Builder<ItemStackBuilder, ItemMeta> {

  /**
   * the item stack deserializer.
   */
  private static final Deserializer DESERIALIZER = new Deserializer();

  /**
   * ctor.
   *
   * @param itemStack the item stack.
   */
  private ItemStackBuilder(@NotNull final ItemStack itemStack) {
    super(Objects.requireNonNull(itemStack.getItemMeta(), String.format("ItemMeta of %s couldn't get!", itemStack)),
      itemStack);
  }

  /**
   * creates item stack builder from {@link XMaterial}.
   *
   * @param material the material to create.
   *
   * @return a newly created item stack builder instance.
   */
  @NotNull
  public static ItemStackBuilder from(@NotNull final XMaterial material) {
    final var parsed = material.parseMaterial();
    if (parsed == null) {
      throw new IllegalStateException(String.format("Material from the %s cannot be null!",
        material.name()));
    }
    return ItemStackBuilder.from(parsed);
  }

  /**
   * creates item stack builder from {@link Material}.
   *
   * @param material the material to create.
   *
   * @return a newly created item stack builder instance.
   */
  @NotNull
  public static ItemStackBuilder from(@NotNull final Material material) {
    return ItemStackBuilder.from(new ItemStack(material));
  }

  /**
   * creates item stack builder from {@link ItemStack}.
   *
   * @param itemStack the item stack to create.
   *
   * @return a newly created item stack builder instance.
   */
  @NotNull
  public static ItemStackBuilder from(@NotNull final ItemStack itemStack) {
    return new ItemStackBuilder(itemStack);
  }

  /**
   * creates item stack builder from NBT Json string.
   *
   * @param nbtJson the nbt json to create.
   *
   * @return a newly created item stack builder instance.
   */
  @NotNull
  public static ItemStackBuilder from(@NotNull final String nbtJson) {
    return ItemStackBuilder.from(NBTEditor.getItemFromTag(NBTEditor.getNBTCompound(nbtJson)));
  }

  /**
   * creates item stack builder from serialized map.
   *
   * @param map the map to create.
   *
   * @return a newly created item stack builder instance.
   */
  @NotNull
  public static ItemStackBuilder from(@NotNull final Map<String, Object> map) {
    return ItemStackBuilder.getDeserializer().apply(map).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given map is incorrect!\n%s", map)));
  }

  /**
   * obtains the deserializer.
   *
   * @return deserializer.
   */
  @NotNull
  public static Deserializer getDeserializer() {
    return ItemStackBuilder.DESERIALIZER;
  }

  @Override
  @NotNull
  public ItemStackBuilder getSelf() {
    return this;
  }

  /**
   * creates a new {@link BannerItemBuilder} instance.
   *
   * @return a newly created {@link BannerItemBuilder} instance.
   */
  @NotNull
  public BannerItemBuilder toBanner() {
    return new BannerItemBuilder(this.validateMeta(BannerMeta.class), this.getItemStack());
  }

  /**
   * creates a new {@link BookItemBuilder} instance.
   *
   * @return a newly created {@link BookItemBuilder} instance.
   */
  @NotNull
  public BookItemBuilder toBook() {
    return new BookItemBuilder(this.validateMeta(BookMeta.class), this.getItemStack());
  }

  /**
   * creates a new {@link CrossbowItemBuilder} instance.
   *
   * @return a newly created {@link CrossbowItemBuilder} instance.
   *
   * @throws IllegalStateException if server version less than 1.14
   */
  @NotNull
  public CrossbowItemBuilder toCrossbow() {
    if (Builder.VERSION < 14) {
      throw new IllegalStateException("The method called #toCrosbow() can only use 1.14 and later!");
    }
    return new CrossbowItemBuilder(this.validateMeta(CrossbowMeta.class), this.getItemStack());
  }

  /**
   * creates a new {@link FireworkItemBuilder} instance.
   *
   * @return a newly created {@link FireworkItemBuilder} instance.
   */
  @NotNull
  public FireworkItemBuilder toFirework() {
    return new FireworkItemBuilder(this.validateMeta(FireworkMeta.class), this.getItemStack());
  }

  /**
   * creates a new {@link LeatherArmorItemBuilder} instance.
   *
   * @return a newly created {@link LeatherArmorItemBuilder} instance.
   */
  @NotNull
  public LeatherArmorItemBuilder toLeatherArmor() {
    return new LeatherArmorItemBuilder(this.validateMeta(LeatherArmorMeta.class), this.getItemStack());
  }

  /**
   * creates a new {@link MapItemBuilder} instance.
   *
   * @return a newly created {@link MapItemBuilder} instance.
   */
  @NotNull
  public MapItemBuilder toMap() {
    return new MapItemBuilder(this.validateMeta(MapMeta.class), this.getItemStack());
  }

  /**
   * creates a new {@link PotionItemBuilder} instance.
   *
   * @return a newly created {@link PotionItemBuilder} instance.
   */
  @NotNull
  public PotionItemBuilder toPotion() {
    return new PotionItemBuilder(this.validateMeta(PotionMeta.class), this.getItemStack());
  }

  /**
   * creates a new {@link SkullItemBuilder} instance.
   *
   * @return a newly created {@link SkullItemBuilder} instance.
   */
  @NotNull
  public SkullItemBuilder toSkull() {
    return new SkullItemBuilder(this.validateMeta(SkullMeta.class), this.getItemStack());
  }

  /**
   * creates a new {@link SpawnEggItemBuilder} instance.
   *
   * @return a newly created {@link SpawnEggItemBuilder} instance.
   */
  @NotNull
  public SpawnEggItemBuilder toSpawnEgg() {
    if (Builder.VERSION < 11) {
      throw new IllegalStateException("The method called #toSpawnEgg() can only use 1.11 and later!");
    }
    return new SpawnEggItemBuilder(this.validateMeta(SpawnEggMeta.class), this.getItemStack());
  }

  /**
   * validates the {@link #getItemStack()} if the given item meta class applicable.
   *
   * @param meta the meta to validate.
   * @param <T> type of the item meta.
   *
   * @return validated item meta instance.
   *
   * @throws IllegalArgumentException if the given meta is no assignable from the {@link #getItemMeta()} class.
   */
  @NotNull
  private <T extends ItemMeta> T validateMeta(@NotNull final Class<T> meta) {
    if (!meta.isAssignableFrom(this.getItemMeta().getClass())) {
      throw new IllegalArgumentException(String.format("%s's meta is not a %s!",
        this.getItemStack(), meta.getSimpleName()));
    }
    //noinspection unchecked
    return (T) this.getItemMeta();
  }

  public static final class Deserializer implements
    Function<@NotNull Map<String, Object>, @NotNull Optional<ItemStackBuilder>> {

    @NotNull
    @Override
    public Optional<ItemStackBuilder> apply(@NotNull final Map<String, Object> map) {
      return Builder.getDefaultItemStackDeserializer().apply(map)
        .map(itemStack ->
          Builder.getDefaultItemMetaDeserializer(ItemStackBuilder.from(itemStack)).apply(map));
    }
  }
}
