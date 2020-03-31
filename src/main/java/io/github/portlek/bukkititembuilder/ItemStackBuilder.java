package io.github.portlek.bukkititembuilder;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import com.sun.istack.internal.NotNull;
import java.util.*;
import java.util.function.Consumer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public final class ItemStackBuilder {

    @NotNull
    private final ItemStack itemstack;

    private ItemStackBuilder(@NotNull final ItemStack item) {
        this.itemstack = item;
    }

    @NotNull
    public static ItemStackBuilder from(@NotNull final XMaterial material) {
        return ItemStackBuilder.from(
            Optional.ofNullable(material.parseMaterial()).orElseThrow(() ->
                new IllegalStateException("Material from the " + material.name() + " cannot be null!")
            )
        );
    }

    @NotNull
    public static ItemStackBuilder from(@NotNull final Material material) {
        return ItemStackBuilder.from(new ItemStack(material));
    }

    @NotNull
    public static ItemStackBuilder from(@NotNull final ItemStack from) {
        return new ItemStackBuilder(from);
    }

    @NotNull
    public ItemStackBuilder name(@NotNull final String name) {
        return this.name(name, true);
    }

    @NotNull
    public ItemStackBuilder name(@NotNull final String name, final boolean colored) {
        this.itemMeta().ifPresent(itemMeta -> {
            if (colored) {
                itemMeta.setDisplayName(ColorUtil.colored(name));
            } else {
                itemMeta.setDisplayName(name);
            }
            this.setItemMeta(itemMeta);
        });
        return this;
    }

    @NotNull
    private Optional<ItemMeta> itemMeta() {
        return Optional.ofNullable(this.itemstack.getItemMeta());
    }

    public void setItemMeta(@NotNull final ItemMeta itemMeta) {
        this.itemstack.setItemMeta(itemMeta);
    }

    @NotNull
    public BannerItemBuilder banner() {
        final Optional<ItemMeta> optional = this.itemMeta();
        if (!optional.isPresent()) {
            throw new IllegalStateException(this.itemstack + " has not an item meta!");
        }
        if (!(optional.get() instanceof BannerMeta)) {
            throw new IllegalStateException(this.itemstack + " is not a banner!");
        }
        return new BannerItemBuilder(this, (BannerMeta) optional.get());
    }

    @NotNull
    public ItemStackBuilder amount(final int size) {
        this.itemstack.setAmount(size);
        return this;
    }

    @NotNull
    public ItemStackBuilder flag(@NotNull final ItemFlag... flags) {
        this.acceptItemMeta(itemMeta -> {
            itemMeta.addItemFlags(flags);
            this.setItemMeta(itemMeta);
        });
        return this;
    }

    private void acceptItemMeta(@NotNull final Consumer<ItemMeta> consumer) {
        this.itemMeta().ifPresent(consumer);
    }

    @NotNull
    public ItemStackBuilder data(final int data) {
        return this.data((byte) data);
    }

    @NotNull
    public ItemStackBuilder data(final byte data) {
        final MaterialData materialData = this.itemstack.getData();
        materialData.setData(data);
        this.itemstack.setData(materialData);
        return this;
    }

    @NotNull
    public ItemStackBuilder lore(@NotNull final String... lore) {
        return this.lore(Arrays.asList(lore), true);
    }

    @NotNull
    public ItemStackBuilder lore(@NotNull final List<String> lore, final boolean colored) {
        this.acceptItemMeta(itemMeta -> {
            if (colored) {
                itemMeta.setLore(
                    ColorUtil.colored(lore)
                );
            } else {
                itemMeta.setLore(
                    lore
                );
            }
            this.setItemMeta(itemMeta);
        });
        return this;
    }

    @NotNull
    public ItemStackBuilder enchantments(@NotNull final String... enchantments) {
        for (final String enchstring : enchantments) {
            final String[] split = enchstring.split(":");
            final String enchantment;
            final int level;
            if (split.length == 1) {
                enchantment = split[0];
                level = 1;
            } else {
                enchantment = split[0];
                level = this.getInt(split[1]);
            }
            XEnchantment.matchXEnchantment(enchantment).ifPresent(xEnchantment -> this.enchantments(xEnchantment, level));
        }
        return this;
    }

    private int getInt(@NotNull final String string) {
        try {
            return Integer.parseInt(string);
        } catch (final Exception ignored) {
            // ignored
        }
        return 0;
    }

    @NotNull
    public ItemStackBuilder enchantments(@NotNull final XEnchantment enchantment, final int level) {
        return Optional.ofNullable(enchantment.parseEnchantment()).map(value ->
            this.enchantments(value, level)
        ).orElse(this);
    }

    @NotNull
    public ItemStackBuilder enchantments(@NotNull final Enchantment enchantment, final int level) {
        final Map<Enchantment, Integer> map = new HashMap<>();
        map.put(enchantment, level);
        return this.enchantments(map);
    }

    @NotNull
    public ItemStackBuilder enchantments(@NotNull final Map<Enchantment, Integer> enchantments) {
        this.itemstack.addUnsafeEnchantments(enchantments);
        return this;
    }

    @NotNull
    public ItemStack build() {
        return this.itemstack;
    }

}
