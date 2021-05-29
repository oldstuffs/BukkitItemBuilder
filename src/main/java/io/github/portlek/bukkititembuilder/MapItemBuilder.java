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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents leather armor item builders.
 * serialization:
 * <pre>
 * map: (main section)
 *   scaling: boolean (map is scaling or not) (for 8 and newer versions)
 *
 *   location: string (location name) (for 11 and newer versions)
 *
 *   color: 'red, green, blue' (for 11 and newer versions)
 *
 *   map-id: integer (map's id) (for 13 and newer versions)
 *
 *   view: (view section)
 *     scale: string (map's scale) (for 14 and newer versions)
 *
 *     world: string (map's world) (for 14 and newer versions)
 *
 *     locked: boolean (map is locked or not) (for 14 and newer versions)
 *
 *     tracking-position: boolean (is tracking position) (for 14 and newer versions)
 *
 *     unlimited-tracking: boolean (is unlimited tracking) (for 14 and newer versions)
 *
 *     center: (center section)
 *       x: integer (map center's x value) (for 14 and newer versions)
 *
 *       z: integer (map center's z value) (for 14 and newer versions)
 * </pre>
 */
public final class MapItemBuilder extends Builder<MapItemBuilder, MapMeta> {

  /**
   * the deserializer.
   */
  @Getter
  private static final Function<@NotNull Map<String, Object>, @NotNull Optional<MapItemBuilder>> deserializer =
    new Deserializer();

  /**
   * ctor.
   *
   * @param itemMeta the item meta.
   * @param itemStack the item stack.
   */
  MapItemBuilder(@NotNull final MapMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  /**
   * creates a new map item builder instance.
   *
   * @param itemMeta the item meta to create.
   * @param itemStack the item stack to create.
   *
   * @return a newly created map item builder instance.
   */
  @NotNull
  public static MapItemBuilder from(@NotNull final MapMeta itemMeta, @NotNull final ItemStack itemStack) {
    return new MapItemBuilder(itemMeta, itemStack);
  }

  /**
   * creates map item builder from serialized map.
   *
   * @param map the map to create.
   *
   * @return a newly created map item builder instance.
   */
  @NotNull
  public static MapItemBuilder from(@NotNull final Map<String, Object> map) {
    return MapItemBuilder.getDeserializer().apply(map).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given map is incorrect!\n%s", map)));
  }

  @NotNull
  @Override
  public MapItemBuilder getSelf() {
    return this;
  }

  @NotNull
  @Override
  public Map<String, Object> serialize() {
    final var serialized = super.serialize();
    final var map = new HashMap<String, Object>();
    final var itemMeta = this.getItemMeta();
    serialized.put(Buildable.MAP_KEYS[0], map);
    map.put(Buildable.SCALING_KEYS[0], itemMeta.isScaling());
    if (Builder.VERSION >= 11) {
      if (itemMeta.hasLocationName()) {
        map.put("location", itemMeta.getLocationName());
      }
      final var color = itemMeta.getColor();
      if (itemMeta.hasColor() && color != null) {
        map.put("color", String.format("%d, %d, %d",
          color.getRed(), color.getGreen(), color.getBlue()));
      }
    }
    if (Builder.VERSION >= 13) {
      map.put(Buildable.MAP_ID_KEYS[0], itemMeta.getMapId());
    }
    if (Builder.VERSION >= 14) {
      final var mapView = itemMeta.getMapView();
      if (itemMeta.hasMapView() && mapView != null) {
        final var view = new HashMap<>();
        map.put("view", view);
        view.put("scale", mapView.getScale().toString());
        final var world = mapView.getWorld();
        if (world != null) {
          view.put("world", world.getName());
        }
        view.put("locked", mapView.isLocked());
        view.put("tracking-position", mapView.isTrackingPosition());
        view.put("unlimited-tracking", mapView.isUnlimitedTracking());
        final var center = new HashMap<>();
        view.put("center", center);
        center.put("x", mapView.getCenterX());
        center.put("z", mapView.getCenterZ());
      }
    }
    return serialized;
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
    if (Builder.VERSION >= 11) {
      this.getItemMeta().setColor(color);
    }
    return this.getSelf();
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
    if (Builder.VERSION >= 11) {
      this.getItemMeta().setLocationName(name);
    }
    return this.getSelf();
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
    if (Builder.VERSION >= 13) {
      this.getItemMeta().setMapId(id);
    }
    return this.getSelf();
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
    if (Builder.VERSION >= 14) {
      this.getItemMeta().setMapView(mapView);
    }
    return this.getSelf();
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
    this.getItemMeta().setScaling(scaling);
    return this.getSelf();
  }

  /**
   * a class that represents deserializer of {@link MapMeta}.
   */
  private static final class Deserializer implements
    Function<@NotNull Map<String, Object>, @NotNull Optional<MapItemBuilder>> {

    @NotNull
    @Override
    public Optional<MapItemBuilder> apply(@NotNull final Map<String, Object> map) {
      final var itemStack = Builder.getDefaultItemStackDeserializer().apply(map);
      if (itemStack.isEmpty()) {
        return Optional.empty();
      }
      final var builder = ItemStackBuilder.from(itemStack.get()).toMap();
      Buildable.getOrDefault(map, Map.class, Buildable.MAP_KEYS)
        .map(m -> (Map<String, Object>) m)
        .ifPresent(mapSection -> {
          final var scaling = Buildable.getOrDefault(mapSection, Boolean.class, Buildable.SCALING_KEYS)
            .orElse(false);
          builder.setScaling(scaling);
          if (Builder.VERSION >= 11) {
            Buildable.getOrDefault(mapSection, String.class, Buildable.LOCATION_KEYS)
              .ifPresent(builder::setLocationName);
            Buildable.getOrDefault(mapSection, String.class, Buildable.COLOR_KEYS)
              .ifPresent(s -> builder.setColor(XItemStack.parseColor(s)));
          }
          if (Builder.VERSION >= 13) {
            Buildable.getOrDefault(mapSection, Number.class, Buildable.MAP_ID_KEYS)
              .map(Number::intValue)
              .ifPresent(builder::setMapId);
          }
          if (Builder.VERSION >= 14) {
            Buildable.getOrDefault(mapSection, Map.class, Buildable.VIEW_KEYS)
              .map(m -> (Map<String, Object>) m)
              .ifPresent(view -> Buildable.getOrDefault(view, String.class, Buildable.WORLD_KEYS)
                .flatMap(worldName -> Optional.ofNullable(Bukkit.getWorld(worldName)))
                .ifPresent(world -> {
                  final var scaleOptional = Buildable.getOrDefault(view, String.class, Buildable.SCALE_KEYS);
                  final var locked = Buildable.getOrDefault(view, Boolean.class, Buildable.LOCKED_KEYS)
                    .orElse(false);
                  final var trackingPosition = Buildable.getOrDefault(view, Boolean.class, Buildable.TRACKING_POSITION_KEYS)
                    .orElse(false);
                  final var unlimitedTracking = Buildable.getOrDefault(view, Boolean.class, Buildable.UNLIMITED_TRACKING_KEYS)
                    .orElse(false);
                  final var mapView = Bukkit.createMap(world);
                  mapView.setWorld(world);
                  MapView.Scale scale;
                  try {
                    scale = scaleOptional.map(MapView.Scale::valueOf).orElse(MapView.Scale.NORMAL);
                  } catch (final Exception e) {
                    scale = MapView.Scale.NORMAL;
                  }
                  mapView.setScale(scale);
                  mapView.setLocked(locked);
                  mapView.setTrackingPosition(trackingPosition);
                  mapView.setUnlimitedTracking(unlimitedTracking);
                  final var center = Buildable.getOrDefault(view, Map.class, Buildable.CENTER_KEYS)
                    .map(m -> (Map<String, Object>) m)
                    .orElse(new HashMap<>());
                  final var x = Buildable.getOrDefault(center, Number.class, Buildable.X_KEYS)
                    .orElse(0);
                  final var z = Buildable.getOrDefault(center, Number.class, Buildable.Z_KEYS)
                    .orElse(0);
                  mapView.setCenterX(x.intValue());
                  mapView.setCenterZ(z.intValue());
                  builder.setMapView(mapView);
                }));
          }
        });
      return Optional.of(Builder.getDefaultItemMetaDeserializer(builder).apply(map));
    }
  }
}
