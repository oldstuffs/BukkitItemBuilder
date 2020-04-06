package io.github.portlek.bukkititembuilder;

import io.github.portlek.bukkititembuilder.base.Builder;
import io.github.portlek.bukkititembuilder.base.ItemStackBuilder;
import org.bukkit.Color;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MapItemBuilder extends Builder<MapItemBuilder, MapMeta> {

    public MapItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final MapMeta meta) {
        super(builder, meta);
    }

    @NotNull
    @Deprecated
    public MapItemBuilder mapId(final int id) {
        return this.change(meta ->
            meta.setMapId(id));
    }

    @NotNull
    public MapItemBuilder mapView(@NotNull final MapView mapView) {
        return this.change(meta ->
            meta.setMapView(mapView));
    }

    @NotNull
    public MapItemBuilder scaling(final boolean scaling) {
        return this.change(meta ->
            meta.setScaling(scaling));
    }

    @NotNull
    public MapItemBuilder locationName(@Nullable final String name) {
        return this.change(meta ->
            meta.setLocationName(name));
    }

    @NotNull
    public MapItemBuilder color(@Nullable final Color color) {
        return this.change(meta ->
            meta.setColor(color));
    }

    @NotNull
    @Override
    protected MapItemBuilder get() {
        return this;
    }

}
