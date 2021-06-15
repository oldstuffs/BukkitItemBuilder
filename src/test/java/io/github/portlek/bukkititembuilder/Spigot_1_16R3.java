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

import java.lang.reflect.Field;
import net.minecraft.server.v1_16_R3.Main;
import net.minecraft.server.v1_16_R3.MinecraftServer;

public abstract class Spigot_1_16R3 extends RunServer {

  private Field recentTps;

  @Override
  protected boolean checkTpsFilled() throws Exception {
    if (this.recentTps == null) {
      this.recentTps = MinecraftServer.class.getDeclaredField("recentTps");
    }
    return MinecraftServer.getServer() != null &&
      ((double[]) this.recentTps.get(MinecraftServer.getServer()))[0] != 0;
  }

  @Override
  protected void main(final String[] args) {
    Main.main(this.parseOptions(args));
  }
}
