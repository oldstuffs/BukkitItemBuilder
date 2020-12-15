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
import org.bukkit.inventory.meta.CrossbowMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CrossbowItemBuilder extends Builder<CrossbowItemBuilder, CrossbowMeta> {

  public CrossbowItemBuilder(@NotNull final ItemStack item, @NotNull final CrossbowMeta meta) {
    super(item, meta);
  }

  @NotNull
  public CrossbowItemBuilder chargedProjectile(@NotNull final ItemStack projectile) {
    return this.update(meta ->
      meta.addChargedProjectile(projectile));
  }

  @NotNull
  public CrossbowItemBuilder chargedProjectiles(@Nullable final List<ItemStack> projectiles) {
    return this.update(meta ->
      meta.setChargedProjectiles(projectiles));
  }

  @NotNull
  public CrossbowItemBuilder chargedProjectiles(@Nullable final ItemStack... projectiles) {
    return this.chargedProjectiles(Arrays.asList(projectiles));
  }

  @NotNull
  @Override
  public CrossbowItemBuilder get() {
    return this;
  }
}
