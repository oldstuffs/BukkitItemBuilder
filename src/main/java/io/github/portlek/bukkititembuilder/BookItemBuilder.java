package io.github.portlek.bukkititembuilder;

import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BookItemBuilder extends Builder<BookItemBuilder, BookMeta> {

    public BookItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final BookMeta meta) {
        super(builder, meta);
    }

    @NotNull
    public BookItemBuilder title(@Nullable final String title) {
        return this.change(() ->
            this.meta.setTitle(title));
    }

    @NotNull
    public BookItemBuilder generation(@Nullable final BookMeta.Generation generation) {
        return this.change(() ->
            this.meta.setGeneration(generation));
    }

    @NotNull
    public BookItemBuilder setPage(final int page, @NotNull final String text) {
        return this.change(() ->
            this.meta.setPage(page, text));
    }

    @NotNull
    public BookItemBuilder addPages(@NotNull final String... list) {
        return this.change(() ->
            this.meta.addPage(list));
    }

    @NotNull
    public BookItemBuilder setPages(@NotNull final String... list) {
        return this.setPages(Arrays.asList(list));
    }

    @NotNull
    public BookItemBuilder setPages(@NotNull final List<String> list) {
        return this.change(() ->
            this.meta.setPages(list));
    }

    @NotNull
    public BookItemBuilder author(@Nullable final String author) {
        return this.change(() ->
            this.meta.setAuthor(author));
    }

    @Override
    protected BookItemBuilder get() {
        return this;
    }

}
