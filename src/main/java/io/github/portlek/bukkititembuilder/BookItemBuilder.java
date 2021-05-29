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

import io.github.portlek.bukkititembuilder.util.KeyUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents book item builders.
 * <p>
 * serialization:
 * <pre>
 * book: (main section)
 *   title: string (book's title) (for 8 and newer versions)
 *
 *   author: string (book's author) (for 8 and newer versions)
 *
 *   generation: string (book's generation) (for 10 and newer versions)
 *
 *   pages: (string list) (for 8 and newer versions)
 *     - 'page 1'
 * </pre>
 */
public final class BookItemBuilder extends Builder<BookItemBuilder, BookMeta> {

  /**
   * the deserializer.
   */
  private static final Deserializer DESERIALIZER = new Deserializer();

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
   * creates a new book item builder instance.
   *
   * @param itemMeta the item meta to create.
   * @param itemStack the item stack to create.
   *
   * @return a newly created book item builder instance.
   */
  @NotNull
  public static BookItemBuilder from(@NotNull final BookMeta itemMeta, @NotNull final ItemStack itemStack) {
    return new BookItemBuilder(itemMeta, itemStack);
  }

  /**
   * creates book item builder from serialized map.
   *
   * @param map the map to create.
   *
   * @return a newly created book item builder instance.
   */
  @NotNull
  public static BookItemBuilder from(@NotNull final Map<String, Object> map) {
    return BookItemBuilder.getDeserializer().apply(map).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given map is incorrect!\n%s", map)));
  }

  /**
   * obtains the deserializer.
   *
   * @return deserializer.
   */
  @NotNull
  public static Deserializer getDeserializer() {
    return BookItemBuilder.DESERIALIZER;
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

  @NotNull
  @Override
  public Map<String, Object> serialize() {
    final var map = super.serialize();
    final var book = new HashMap<String, Object>();
    final var itemMeta = this.getItemMeta();
    map.put(KeyUtil.BOOKS_KEYS[0], book);
    book.put(KeyUtil.TITLE_KEYS[0], itemMeta.getTitle());
    book.put(KeyUtil.AUTHOR_KEYS[0], itemMeta.getAuthor());
    if (Builder.VERSION >= 10) {
      final var generation = itemMeta.getGeneration();
      if (generation != null) {
        book.put(KeyUtil.GENERATION_KEYS[0], generation.toString());
      }
    }
    book.put(KeyUtil.PAGES_KEYS[0], itemMeta.getPages());
    return map;
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
    if (Builder.VERSION >= 10) {
      this.getItemMeta().setGeneration(generation);
    }
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

  /**
   * a class that represents deserializer of {@link BookMeta}.
   */
  public static final class Deserializer implements
    Function<@NotNull Map<String, Object>, @NotNull Optional<BookItemBuilder>> {

    @NotNull
    @Override
    public Optional<BookItemBuilder> apply(@NotNull final Map<String, Object> map) {
      final var itemStack = Builder.getItemStackDeserializer().apply(map);
      if (itemStack.isEmpty()) {
        return Optional.empty();
      }
      final var builder = ItemStackBuilder.from(itemStack.get()).asBook();
      KeyUtil.getOrDefault(map, Map.class, KeyUtil.BOOKS_KEYS)
        .map(m -> (Map<String, Object>) m)
        .ifPresent(book -> {
          final var title = KeyUtil.getOrDefault(book, String.class, KeyUtil.TITLE_KEYS)
            .orElse(null);
          final var author = KeyUtil.getOrDefault(book, String.class, KeyUtil.AUTHOR_KEYS)
            .orElse(null);
          final var pages = KeyUtil.getOrDefault(book, List.class, KeyUtil.PAGES_KEYS)
            .map(collection -> (List<String>) collection)
            .orElse(Collections.emptyList());
          builder.setTitle(title);
          builder.setAuthor(author);
          builder.setPages(pages);
          if (Builder.VERSION >= 10) {
            KeyUtil.getOrDefault(book, String.class, KeyUtil.GENERATION_KEYS).ifPresent(generationString -> {
              BookMeta.Generation generation;
              try {
                generation = BookMeta.Generation.valueOf(generationString);
              } catch (final Exception e) {
                generation = null;
              }
              builder.setGeneration(generation);
            });
          }
        });
      return Optional.of(Builder.getItemMetaDeserializer(builder).apply(map));
    }
  }
}
