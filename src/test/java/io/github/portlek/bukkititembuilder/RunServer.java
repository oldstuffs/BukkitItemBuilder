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
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public abstract class RunServer {

  private static final OptionParser PARSER = new OptionParser() {{
    this.acceptsAll(Arrays.asList("?", "help"), "Show the help");
    this.acceptsAll(Arrays.asList("c", "config"), "Properties file to use")
      .withRequiredArg()
      .ofType(File.class)
      .defaultsTo(new File("server.properties"))
      .describedAs("Properties file");
    this.acceptsAll(Arrays.asList("P", "plugins"), "Plugin directory to use")
      .withRequiredArg()
      .ofType(File.class)
      .defaultsTo(new File("plugins"))
      .describedAs("Plugin directory");
    this.acceptsAll(Arrays.asList("h", "host", "server-ip"), "Host to listen on")
      .withRequiredArg()
      .ofType(String.class)
      .describedAs("Hostname or IP");
    this.acceptsAll(Arrays.asList("W", "world-dir", "universe", "world-container"), "World container")
      .withRequiredArg()
      .ofType(File.class)
      .defaultsTo(new File("."))
      .describedAs("Directory containing worlds");
    this.acceptsAll(Arrays.asList("w", "world", "level-name"), "World name")
      .withRequiredArg()
      .ofType(String.class)
      .describedAs("World name");
    this.acceptsAll(Arrays.asList("p", "port", "server-port"), "Port to listen on")
      .withRequiredArg()
      .ofType(Integer.class)
      .describedAs("Port");
    this.acceptsAll(Arrays.asList("o", "online-mode"), "Whether to use online authentication")
      .withRequiredArg()
      .ofType(Boolean.class)
      .describedAs("Authentication");
    this.acceptsAll(Arrays.asList("s", "size", "max-players"), "Maximum amount of players")
      .withRequiredArg()
      .ofType(Integer.class)
      .describedAs("Server size");
    this.acceptsAll(Arrays.asList("d", "date-format"), "Format of the date to display in the console (for log entries)")
      .withRequiredArg()
      .ofType(SimpleDateFormat.class)
      .describedAs("Log date format");
    this.acceptsAll(Collections.singletonList("log-pattern"), "Specfies the log filename pattern")
      .withRequiredArg()
      .ofType(String.class)
      .defaultsTo("server.log")
      .describedAs("Log filename");
    this.acceptsAll(Collections.singletonList("log-limit"), "Limits the maximum size of the log file (0 = unlimited)")
      .withRequiredArg()
      .ofType(Integer.class)
      .defaultsTo(0)
      .describedAs("Max log size");
    this.acceptsAll(Collections.singletonList("log-count"), "Specified how many log files to cycle through")
      .withRequiredArg()
      .ofType(Integer.class)
      .defaultsTo(1)
      .describedAs("Log count");
    this.acceptsAll(Collections.singletonList("log-append"), "Whether to append to the log file")
      .withRequiredArg()
      .ofType(Boolean.class)
      .defaultsTo(true)
      .describedAs("Log append");
    this.acceptsAll(Collections.singletonList("log-strip-color"), "Strips color codes from log file");
    this.acceptsAll(Arrays.asList("b", "bukkit-settings"), "File for bukkit settings")
      .withRequiredArg()
      .ofType(File.class)
      .defaultsTo(new File("bukkit.yml"))
      .describedAs("Yml file");
    this.acceptsAll(Arrays.asList("C", "commands-settings"), "File for command settings")
      .withRequiredArg()
      .ofType(File.class)
      .defaultsTo(new File("commands.yml"))
      .describedAs("Yml file");
    this.acceptsAll(Collections.singletonList("forceUpgrade"), "Whether to force a world upgrade");
    this.acceptsAll(Collections.singletonList("eraseCache"), "Whether to force cache erase during world upgrade");
    this.acceptsAll(Collections.singletonList("nogui"), "Disables the graphical console");
    this.acceptsAll(Collections.singletonList("nojline"), "Disables jline and emulates the vanilla console");
    this.acceptsAll(Collections.singletonList("noconsole"), "Disables the console");
    this.acceptsAll(Arrays.asList("v", "version"), "Show the CraftBukkit Version");
    this.acceptsAll(Collections.singletonList("demo"), "Demo mode");
    this.acceptsAll(Arrays.asList("S", "spigot-settings"), "File for spigot settings")
      .withRequiredArg()
      .ofType(File.class)
      .defaultsTo(new File("spigot.yml"))
      .describedAs("Yml file");
  }};

  static OptionSet parseOptions(final String[] args) {
    try {
      return RunServer.PARSER.parse(args);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected abstract boolean checkTpsFilled() throws Exception;

  protected abstract void main(final String[] args);

  final void runServer(@NotNull final Runnable test) {
    Thread thread = null;
    try {
      final var here = new File(System.getProperty("user.dir"));
      final var path = here.toPath();
      final var before = here.getParentFile();
      final var pathBefore = before.toPath();
      final var testClassesPath = pathBefore.resolve("test-classes");
      final var serverProperties = testClassesPath.resolve("server.properties");
      final var bukkitYml = testClassesPath.resolve("bukkit.yml");
      final var spigotYml = testClassesPath.resolve("spigot.yml");
      Files.deleteIfExists(path.resolve("world"));
      Files.deleteIfExists(path.resolve("world_nether"));
      Files.deleteIfExists(path.resolve("world_the_end"));
      thread = new Thread(() -> {
        System.setProperty("com.mojang.eula.agree", "true");
        this.main(new String[]{
          "nogui",
          "noconsole",
          "--config=" + serverProperties,
          "--bukkit-settings=" + bukkitYml,
          "--spigot-settings=" + spigotYml
        });
      });
      thread.start();
      while (!this.checkTpsFilled()) {
        Thread.sleep(5L);
      }
      Thread.sleep(1000L);
      test.run();
      Thread.sleep(1000L);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (thread != null) {
        Bukkit.shutdown();
        thread.interrupt();
      }
    }
  }
}
