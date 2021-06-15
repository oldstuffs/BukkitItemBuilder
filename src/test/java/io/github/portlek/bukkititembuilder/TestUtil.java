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

import com.cryptomorin.xseries.XMaterial;
import io.github.portlek.bukkititembuilder.util.ColorUtil;
import java.util.List;
import org.junit.jupiter.api.Assertions;

final class TestUtil {

  static final Runnable USUAL_TEST = () -> {
    TestUtil.testXMaterial();
    TestUtil.testColorUtilColored();
  };

  private TestUtil() {
  }

  private static void testColorUtilColored() {
    final var nonColoredString = "&aTesty";
    Assertions.assertEquals(
      "§aTesty",
      ColorUtil.colored(nonColoredString),
      "Couldn't colored the string!"
    );
    Assertions.assertEquals(
      List.of("§aTesty", "§aTesty"),
      ColorUtil.colored(nonColoredString, nonColoredString),
      "Couldn't colored the string list!"
    );
    Assertions.assertEquals(
      List.of("§aTesty", "§aTesty"),
      ColorUtil.colored(List.of(nonColoredString, nonColoredString)),
      "Couldn't colored the string list!"
    );
  }

  private static void testXMaterial() {
    Assertions.assertTrue(XMaterial.matchXMaterial("AIR").isPresent());
  }
}
