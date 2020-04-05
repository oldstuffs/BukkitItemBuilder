package io.github.portlek.bukkititembuilder.base;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Multimap;
import io.github.portlek.bukkititembuilder.*;
import io.github.portlek.bukkititembuilder.util.ColorUtil;
import java.util.*;
import java.util.function.Consumer;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
                new IllegalStateException("Material from the " + material.name() + " cannot be null!")));
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
    public ItemStackBuilder localizedName(@Nullable final String name) {
        return this.change(itemMeta ->
            itemMeta.setLocalizedName(name));
    }

    @NotNull
    private ItemStackBuilder change(@NotNull final Consumer<ItemMeta> consumer) {
        this.itemMeta().ifPresent(itemMeta -> {
            consumer.accept(itemMeta);
            this.itemstack.setItemMeta(itemMeta);
        });
        return this;
    }

    @NotNull
    private Optional<ItemMeta> itemMeta() {
        return Optional.ofNullable(this.itemstack.getItemMeta());
    }

    @NotNull
    public ItemStackBuilder customModelData(@Nullable final Integer data) {
        return this.change(itemMeta ->
            itemMeta.setCustomModelData(data));
    }

    @NotNull
    public ItemStackBuilder unbreakable(final boolean unbreakable) {
        return this.change(itemMeta ->
            itemMeta.setUnbreakable(unbreakable));
    }

    @NotNull
    public ItemStackBuilder addAttributeModifier(@NotNull final Attribute attribute,
                                                 @NotNull final AttributeModifier modifier) {
        return this.change(itemMeta ->
            itemMeta.addAttributeModifier(attribute, modifier));
    }

    @NotNull
    public ItemStackBuilder addAttributeModifier(@NotNull final Multimap<Attribute, AttributeModifier> map) {
        return this.change(itemMeta ->
            itemMeta.setAttributeModifiers(map));
    }

    @NotNull
    public ItemStackBuilder removeAttributeModifier(@NotNull final Attribute attribute) {
        return this.change(itemMeta ->
            itemMeta.removeAttributeModifier(attribute));
    }

    @NotNull
    public ItemStackBuilder removeAttributeModifier(@NotNull final EquipmentSlot slot) {
        return this.change(itemMeta ->
            itemMeta.removeAttributeModifier(slot));
    }

    @NotNull
    public ItemStackBuilder removeAttributeModifier(@NotNull final Attribute attribute,
                                                    @NotNull final AttributeModifier modifier) {
        return this.change(itemMeta ->
            itemMeta.removeAttributeModifier(attribute, modifier));
    }

    @NotNull
    public ItemStackBuilder version(final int version) {
        return this.change(itemMeta ->
            itemMeta.setVersion(version));
    }

    @NotNull
    public ItemStackBuilder name(@NotNull final String name) {
        return this.name(name, true);
    }

    @NotNull
    public ItemStackBuilder name(@NotNull final String name, final boolean colored) {
        final String fnlname;
        if (colored) {
            fnlname = ColorUtil.colored(name);
        } else {
            fnlname = name;
        }
        return this.change(itemMeta ->
            itemMeta.setDisplayName(fnlname));
    }

    @NotNull
    public CrossbowItemBuilder crossbow() {
        return new CrossbowItemBuilder(this, this.validateMeta(CrossbowMeta.class));
    }

    @NotNull
    private <T extends ItemMeta> T validateMeta(@NotNull final Class<T> meta) {
        final ItemMeta itemmeta = this.itemMeta().orElseThrow(() ->
            new IllegalStateException(this.itemstack + " has not an item meta!"));
        if (!meta.isAssignableFrom(itemmeta.getClass())) {
            throw new IllegalStateException(this.itemstack + " is not a banner!");
        }
        //noinspection unchecked
        return (T) itemmeta;
    }

    @NotNull
    public MapItemBuilder map() {
        return new MapItemBuilder(this, this.validateMeta(MapMeta.class));
    }

    @NotNull
    public SkullItemBuilder skull() {
        return new SkullItemBuilder(this, this.validateMeta(SkullMeta.class));
    }

    @NotNull
    public BannerItemBuilder banner() {
        return new BannerItemBuilder(this, this.validateMeta(BannerMeta.class));
    }

    @NotNull
    public BookItemBuilder book() {
        return new BookItemBuilder(this, this.validateMeta(BookMeta.class));
    }

    @NotNull
    public ItemStackBuilder amount(final int size) {
        this.itemstack.setAmount(size);
        return this;
    }

    @NotNull
    public ItemStackBuilder flag(@NotNull final ItemFlag... flags) {
        return this.change(itemMeta ->
            itemMeta.addItemFlags(flags));
    }

    @NotNull
    public FireworkItemBuilder firework() {
        return new FireworkItemBuilder(this, this.validateMeta(FireworkMeta.class));
    }

    @NotNull
    public ItemStackBuilder data(final byte data) {
        final MaterialData materialData = this.itemstack.getData();
        materialData.setData(data);
        this.itemstack.setData(materialData);
        return this;
    }

    @NotNull
    public ItemStackBuilder damage(final short damage) {
        this.itemstack.setDurability(damage);
        return this;
    }

    @NotNull
    public ItemStackBuilder lore(@NotNull final String... lore) {
        return this.lore(Arrays.asList(lore), true);
    }

    @NotNull
    public ItemStackBuilder lore(@NotNull final List<String> lore, final boolean colored) {
        final List<String> fnllore;
        if (colored) {
            fnllore = ColorUtil.colored(lore);
        } else {
            fnllore = lore;
        }
        return this.change(itemMeta ->
            itemMeta.setLore(fnllore));
    }

    public void setItemMeta(@NotNull final ItemMeta meta) {
        this.itemstack.setItemMeta(meta);
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
                level = ItemStackBuilder.getInt(split[1]);
            }
            XEnchantment.matchXEnchantment(enchantment).ifPresent(xEnchantment ->
                this.enchantments(xEnchantment, level));
        }
        return this;
    }

    private static int getInt(@NotNull final String text) {
        try {
            return Integer.parseInt(text);
        } catch (final Exception ignored) {
            // ignored
        }
        return 0;
    }

    @NotNull
    public ItemStackBuilder enchantments(@NotNull final XEnchantment enchantment, final int level) {
        return Optional.ofNullable(enchantment.parseEnchantment()).map(value ->
            this.enchantments(value, level)).orElse(this);
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
