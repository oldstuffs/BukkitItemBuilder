/*
 * MIT License
 *
 * Copyright (c) 2020 Hasan Demirtaş
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
    public BookItemBuilder get() {
        return this;
    }

}
