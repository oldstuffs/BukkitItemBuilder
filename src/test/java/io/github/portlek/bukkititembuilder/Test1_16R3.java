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
import io.github.portlek.bukkititembuilder.util.ItemStackUtil;
import io.github.portlek.bukkititembuilder.util.KeyUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

final class Test1_16R3 extends Spigot_1_16R3 {

  @BeforeAll
  static void setup() throws Exception {
    Spigot_1_16R3.startServer();
  }

  @AfterAll
  static void teardown() {
    Spigot_1_16R3.stopServer();
  }

  @Test
  void colorUtil() {
    final var nonColoredString = "&aTesty";
    new Assertion<>(
      "Couldn't colored the string!",
      ColorUtil.colored(nonColoredString),
      new IsEqual<>(ChatColor.GREEN + "Testy")
    ).affirm();
    new Assertion<>(
      "Couldn't colored the string list!",
      ColorUtil.colored(nonColoredString, nonColoredString),
      new IsEqual<>(List.of(ChatColor.GREEN + "Testy", ChatColor.GREEN + "Testy"))
    ).affirm();
    new Assertion<>(
      "Couldn't colored the string list!",
      ColorUtil.colored(List.of(nonColoredString, nonColoredString)),
      new IsEqual<>(List.of(ChatColor.GREEN + "Testy", ChatColor.GREEN + "Testy"))
    ).affirm();
  }

  @Test
  void itemStackUtil() {
    final var expected = new ItemStack(Material.WOODEN_SWORD, 10);
    final var meta = expected.getItemMeta();
    if (meta != null) {
      meta.setDisplayName("Test");
      meta.setLore(List.of("Test", "Test"));
    }
    expected.setItemMeta(meta);
    final var holder = KeyUtil.Holder.map(Map.of(
      "material", "WOODEN_SWORD",
      "amount", 10,
      "name", "Test",
      "lore", List.of("Test", "Test")));
    new Assertion<>(
      "Couldn't deserialize the item stack!",
      ItemStackUtil.deserialize(holder).orElseThrow(() ->
        new IllegalStateException("Couldn't create the item stack!")).isSimilar(expected),
      new IsTrue()
    ).affirm();
    final var serialized = new HashMap<String, Object>();
    ItemStackUtil.serialize(expected, KeyUtil.Holder.map(serialized));
    new Assertion<>(
      "Couldn't serialize the item stack!",
      serialized,
      new IsEqual<>(Map.of(
        "material", "WOODEN_SWORD",
        "amount", 10,
        "name", "Test",
        "lore", List.of("Test", "Test")))
    ).affirm();
    final var serializedFirework = new HashMap<String, Object>();
    ItemStackUtil.serialize(
      ItemStackBuilder.from(XMaterial.FIREWORK_ROCKET)
        .setName("&aTest")
        .addLore("&aTestLore", "&aTestLore")
        .setAmount(10)
        .addEnchantments("DAMAGE_ALL:10")
        .asFirework()
        .setPower(10)
        .addEffect(FireworkEffect.builder()
          .flicker(true)
          .trail(true)
          .with(FireworkEffect.Type.STAR)
          .withColor(Color.fromRGB(255, 255, 255))
          .withFade(Color.fromRGB(255, 255, 255))
          .build())
        .getItemStack(),
      KeyUtil.Holder.map(serializedFirework));
    final var expectedFireworkMap = Map.of(
      "material", "FIREWORK_ROCKET",
      "power", 10,
      "amount", 10,
      "name", "&aTest",
      "lore", List.of("&aTestLore", "&aTestLore"),
      "enchants", Map.of(
        "DAMAGE_ALL", 10
      ),
      "firework", Map.of(
        0, Map.of(
          "trail", true,
          "flicker", true,
          "type", "STAR",
          "colors", Map.of(
            "fade", List.of("255, 255, 255"),
            "base", List.of("255, 255, 255")
          )
        )
      )
    );
    new Assertion<>(
      "Couldn't serialize the firework!",
      serializedFirework,
      new IsEqual<>(expectedFireworkMap)
    ).affirm();
  }
}
