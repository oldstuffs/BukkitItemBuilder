package io.github.portlek.bukkititembuilder;

import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BookItemBuilder {

    @NotNull
    private final ItemStackBuilder builder;

    @NotNull
    private final BookMeta bookMeta;

    public BookItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final BookMeta bookMeta) {
        this.builder = builder;
        this.bookMeta = bookMeta;
    }

    @NotNull
    public BookItemBuilder title(@Nullable final String title) {
        this.bookMeta.setTitle(title);
        this.change();
        return this;
    }

    private void change() {
        this.builder.setItemMeta(this.bookMeta);
    }

    @NotNull
    public BookItemBuilder generation(@Nullable final BookMeta.Generation generation) {
        this.bookMeta.setGeneration(generation);
        this.change();
        return this;
    }

    @NotNull
    public BookItemBuilder setPage(final int page, @NotNull final String text) {
        this.bookMeta.setPage(page, text);
        this.change();
        return this;
    }

    @NotNull
    public BookItemBuilder addPages(@NotNull final String... list) {
        this.bookMeta.addPage(list);
        this.change();
        return this;
    }

    @NotNull
    public BookItemBuilder setPages(@NotNull final String... list) {
        return this.setPages(Arrays.asList(list));
    }

    @NotNull
    public BookItemBuilder setPages(@NotNull final List<String> list) {
        this.bookMeta.setPages(list);
        this.change();
        return this;
    }

    @NotNull
    public BookItemBuilder author(@Nullable final String author) {
        this.bookMeta.setAuthor(author);
        this.change();
        return this;
    }

    @NotNull
    public ItemStack build() {
        return this.builder.build();
    }

}
