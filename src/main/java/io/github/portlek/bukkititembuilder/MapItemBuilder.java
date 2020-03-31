package io.github.portlek.bukkititembuilder;

import org.bukkit.inventory.meta.MapMeta;
import org.jetbrains.annotations.NotNull;

public final class MapItemBuilder extends Builder<MapItemBuilder, MapMeta> {

    public MapItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final MapMeta meta) {
        super(builder, meta);
    }

    @NotNull
    @Deprecated
    public MapItemBuilder mapId(final int id) {
        return this.change(() ->
            this.meta.setMapId(id));
    }

    @Override
    protected MapItemBuilder get() {
        return this;
    }

}
