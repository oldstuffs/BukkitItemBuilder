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

package io.github.portlek.bukkititembuilder.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains utility methods for coloring strings and lists.
 */
@UtilityClass
public class ColorUtil {

  /**
   * colors the given texts.
   *
   * @param texts the texts to color.
   *
   * @return colored list.
   */
  @NotNull
  public List<String> colored(@NotNull final String... texts) {
    return ColorUtil.colored(List.of(texts));
  }

  /**
   * colors the given list.
   *
   * @param list the list to color.
   *
   * @return colored list.
   */
  @NotNull
  public List<String> colored(@NotNull final Collection<String> list) {
    return list.stream()
      .map(ColorUtil::colored)
      .collect(Collectors.toList());
  }

  /**
   * colors the given text.
   *
   * @param text the text to color.
   *
   * @return colored text.
   */
  @NotNull
  public String colored(@NotNull final String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }
}
