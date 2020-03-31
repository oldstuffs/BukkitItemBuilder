package io.github.portlek.bukkititembuilder;

import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

public final class BookItemBuilder {

    @NotNull
    private final ItemStackBuilder builder;

    @NotNull
    private final BookMeta bookMeta;

    public BookItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final BookMeta bookMeta) {
        this.builder = builder;
        this.bookMeta = bookMeta;
    }

}
