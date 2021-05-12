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

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents leather armor item builders.
 */
public final class MapItemBuilder extends Builder<MapItemBuilder, MapMeta> {

  /**
   * ctor.
   *
   * @param itemMeta the item meta.
   * @param itemStack the item stack.
   */
  MapItemBuilder(@NotNull final MapMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  @NotNull
  @Override
  public MapItemBuilder self() {
    return this;
  }

  /**
   * sets color of the map.
   *
   * @param color the color to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public MapItemBuilder setColor(@Nullable final Color color) {
    return this.update(meta -> meta.setColor(color));
  }

  /**
   * sets location name of the map.
   *
   * @param name the name to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public MapItemBuilder setLocationName(@Nullable final String name) {
    return this.update(meta -> meta.setLocationName(name));
  }

  /**
   * sets map id of the map.
   *
   * @param id the id to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  @Deprecated
  public MapItemBuilder setMapId(final int id) {
    return this.update(meta -> meta.setMapId(id));
  }

  /**
   * sets map view of the map.
   *
   * @param mapView the map view to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public MapItemBuilder setMapView(@NotNull final MapView mapView) {
    return this.update(meta -> meta.setMapView(mapView));
  }

  /**
   * sets scaling of the map.
   *
   * @param scaling the scaling to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public MapItemBuilder setScaling(final boolean scaling) {
    return this.update(meta -> meta.setScaling(scaling));
  }
}
