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

import java.util.function.Consumer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine buildable objects.
 *
 * @param <X> type of the self class.
 * @param <T> type of the item meta class.
 */
public interface Buildable<X, T extends ItemMeta> {

  /**
   * obtains the item meta.
   *
   * @return item meta.
   */
  @NotNull
  ItemMeta getItemMeta();

  /**
   * obtains the item stack.
   *
   * @return item stack.
   */
  @NotNull
  ItemStack getItemStack();

  /**
   * obtains the self instance for builder chain.
   *
   * @return self instance.
   */
  @NotNull
  X self();

  /**
   * sets the item stack.
   *
   * @param itemStack the item stack to set.
   *
   * @return {@link #self()} for builder chain.
   */
  @NotNull
  X setItemStack(@NotNull ItemStack itemStack);

  /**
   * updates item meta of {@link #getItemStack()}.
   *
   * @param action the action to run before the setting.
   *
   * @return {@link #self()} for builder chain.
   */
  @NotNull
  X update(@NotNull Consumer<T> action);
}
