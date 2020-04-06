package io.github.portlek.bukkititembuilder;

import io.github.portlek.bukkititembuilder.base.Builder;
import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BookItemBuilder extends Builder<BookItemBuilder, BookMeta> {

    public BookItemBuilder(@NotNull final ItemStack item, @NotNull final BookMeta meta) {
        super(item, meta);
    }

    @NotNull
    public BookItemBuilder title(@Nullable final String title) {
        return this.update(meta ->
            meta.setTitle(title));
    }

    @NotNull
    public BookItemBuilder generation(@Nullable final BookMeta.Generation generation) {
        return this.update(meta ->
            meta.setGeneration(generation));
    }

    @NotNull
    public BookItemBuilder setPage(final int page, @NotNull final String text) {
        return this.update(meta ->
            meta.setPage(page, text));
    }

    @NotNull
    public BookItemBuilder addPages(@NotNull final String... list) {
        return this.update(meta ->
            meta.addPage(list));
    }

    @NotNull
    public BookItemBuilder setPages(@NotNull final String... list) {
        return this.setPages(Arrays.asList(list));
    }

    @NotNull
    public BookItemBuilder setPages(@NotNull final List<String> list) {
        return this.update(meta ->
            meta.setPages(list));
    }

    @NotNull
    public BookItemBuilder author(@Nullable final String author) {
        return this.update(meta ->
            meta.setAuthor(author));
    }

    @NotNull
    @Override
    public BookItemBuilder chain() {
        return this;
    }

}
