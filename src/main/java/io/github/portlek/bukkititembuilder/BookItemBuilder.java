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

import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents book item builders.
 */
public final class BookItemBuilder extends Builder<BookItemBuilder, BookMeta> {

  /**
   * ctor.
   *
   * @param itemMeta the item meta.
   * @param itemStack the item stack.
   */
  BookItemBuilder(@NotNull final BookMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  /**
   * adds page to the book.
   *
   * @param list the list to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder addPages(@NotNull final String... list) {
    this.getItemMeta().addPage(list);
    return this.getSelf();
  }

  @NotNull
  @Override
  public BookItemBuilder getSelf() {
    return this;
  }

  /**
   * sets author of the book.
   *
   * @param author the author to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder setAuthor(@Nullable final String author) {
    this.getItemMeta().setAuthor(author);
    return this.getSelf();
  }

  /**
   * sets generation of the book.
   *
   * @param generation the generation to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder setGeneration(@Nullable final BookMeta.Generation generation) {
    this.getItemMeta().setGeneration(generation);
    return this.getSelf();
  }

  /**
   * sets page of the book.
   *
   * @param page the page to set.
   * @param text the text to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder setPage(final int page, @NotNull final String text) {
    this.getItemMeta().setPage(page, text);
    return this.getSelf();
  }

  /**
   * sets pages of the book.
   *
   * @param list the list to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder setPages(@NotNull final String... list) {
    return this.setPages(Arrays.asList(list));
  }

  /**
   * sets pages of the book.
   *
   * @param list the list to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder setPages(@NotNull final List<String> list) {
    this.getItemMeta().setPages(list);
    return this.getSelf();
  }

  /**
   * sets title of the book.
   *
   * @param title the book to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder setTitle(@Nullable final String title) {
    this.getItemMeta().setTitle(title);
    return this.getSelf();
  }
}
