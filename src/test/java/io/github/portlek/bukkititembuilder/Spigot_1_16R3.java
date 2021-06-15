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

import java.io.File;
import java.lang.reflect.Field;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.Main;

abstract class Spigot_1_16R3 {

  private static Field recentTps;

  private static Thread thread = null;

  static void startServer() throws Exception {
    final var path = new File(System.getProperty("user.dir"))
      .getParentFile()
      .toPath()
      .resolve("test-classes");
    System.setProperty("com.mojang.eula.agree", "true");
    Spigot_1_16R3.thread = new Thread(() ->
      Main.main(new String[]{
        "nogui",
        "noconsole",
        "--config=" + path.resolve("server.properties"),
        "--bukkit-settings=" + path.resolve("bukkit.yml"),
        "--spigot-settings=" + path.resolve("spigot.yml")
      }));
    Spigot_1_16R3.thread.start();
    while (!Spigot_1_16R3.checkTpsFilled()) {
      Thread.sleep(5L);
    }
    Thread.sleep(1000L);
  }

  static void stopServer() {
    if (Spigot_1_16R3.thread != null) {
      Bukkit.shutdown();
    }
  }

  private static boolean checkTpsFilled() throws NoSuchFieldException, IllegalAccessException {
    if (Spigot_1_16R3.recentTps == null) {
      Spigot_1_16R3.recentTps = MinecraftServer.class.getDeclaredField("recentTps");
    }
    return MinecraftServer.getServer() != null &&
      ((double[]) Spigot_1_16R3.recentTps.get(MinecraftServer.getServer()))[0] != 0;
  }
}
