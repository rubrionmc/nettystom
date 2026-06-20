// Package declaration for this file
package net.minestom.server.item;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.event.DataComponentValue;
// Import of a required class
import net.kyori.adventure.text.event.HoverEvent;
// Import of a required class
import net.kyori.adventure.text.event.HoverEventSource;
// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.adventure.MinestomDataComponentValue;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponentMap;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.item.component.CustomData;
// Import of a required class
import net.minestom.server.item.component.CustomModelData;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.server.tag.TagReadable;
// Import of a required class
import net.minestom.server.utils.Unit;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.IntUnaryOperator;
// Import of a required class
import java.util.function.UnaryOperator;

/**
 * Represents an immutable item to be placed inside {@link net.minestom.server.inventory.PlayerInventory},
 * {@link net.minestom.server.inventory.Inventory} or even on the ground {@link net.minestom.server.entity.ItemEntity}.
 * <p>
 * An item stack cannot be null, {@link ItemStack#AIR} should be used instead.
 */
// Type declaration (class/interface/enum/record)
public sealed interface ItemStack extends TagReadable, DataComponent.Holder, HoverEventSource<HoverEvent.ShowItem>
        // Start of a method/block
        permits ItemStackImpl {

    // Calls a method
    NetworkBuffer.Type<ItemStack> NETWORK_TYPE = ItemStackImpl.networkType(DataComponent.PATCH_NETWORK_TYPE);
    // Calls a method
    NetworkBuffer.Type<ItemStack> UNTRUSTED_NETWORK_TYPE = ItemStackImpl.networkType(DataComponent.UNTRUSTED_PATCH_NETWORK_TYPE);
    // Assigns a value
    NetworkBuffer.Type<ItemStack> STRICT_NETWORK_TYPE = NETWORK_TYPE.transform(itemStack -> {
        // Calls a method
        Check.argCondition(itemStack.amount() == 0 || itemStack.isAir(), "ItemStack cannot be empty");
        // Returns a value to the caller
        return itemStack;
    // Start of a method/block
    }, itemStack -> {
        // Calls a method
        Check.argCondition(itemStack.amount() == 0 || itemStack.isAir(), "ItemStack cannot be empty");
        // Returns a value to the caller
        return itemStack;
    // End of a block/expression
    });
    // Assigns a value
    Codec<ItemStack> CODEC = new StructCodec<>() {
        // These exist because Mojang optionally decodes count (ie missing will default to 1),
        // but when encoding they always include the 1. We want to preserve this behavior and
        // since it's currently a one off we can just do it here in a gross way.
        // Assigns a value
        private static final StructCodec<ItemStack> DECODER = StructCodec.struct(
                // Code statement
                "id", Material.CODEC, ItemStack::material,
                // Code statement
                "count", Codec.INT.optional(1), ItemStack::amount,
                // Code statement
                "components", DataComponent.PATCH_CODEC.optional(DataComponentMap.EMPTY), ItemStack::componentPatch,
                // Code statement
                ItemStack::of);
        // Assigns a value
        private static final StructCodec<ItemStack> ENCODER = StructCodec.struct(
                // Code statement
                "id", Material.CODEC, ItemStack::material,
                // Code statement
                "count", Codec.INT, ItemStack::amount,
                // Code statement
                "components", DataComponent.PATCH_CODEC.optional(DataComponentMap.EMPTY), ItemStack::componentPatch,
                // Code statement
                ItemStack::of);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<ItemStack> decodeFromMap(Transcoder<D> coder, Transcoder.MapLike<D> map) {
            // Returns a value to the caller
            return DECODER.decodeFromMap(coder, map);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encodeToMap(Transcoder<D> coder, ItemStack value, Transcoder.MapBuilder<D> map) {
            // Returns a value to the caller
            return ENCODER.encodeToMap(coder, value, map);
        // End of a block/expression
        }
    // End of a block/expression
    };

    /**
     * Constant AIR item. Should be used instead of 'null'.
     */
    // Calls a method
    ItemStack AIR = new ItemStackImpl(Material.AIR, 0, DataComponentMap.EMPTY);

    // Annotation for the following element
    @Contract(value = "_ -> new", pure = true)
    // Start of a method/block
    static Builder builder(Material material) {
        // Returns a value to the caller
        return new ItemStackImpl.Builder(material, 1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_ -> new", pure = true)
    // Start of a method/block
    static ItemStack of(Material material) {
        // Returns a value to the caller
        return of(material, 1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_ ,_ -> new", pure = true)
    // Start of a method/block
    static ItemStack of(Material material, int amount) {
        // Returns a value to the caller
        return ItemStackImpl.create(material, amount);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_ ,_ -> new", pure = true)
    // Start of a method/block
    static ItemStack of(Material material, DataComponentMap components) {
        // Returns a value to the caller
        return ItemStackImpl.create(material, 1, components);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_ ,_, _ -> new", pure = true)
    // Start of a method/block
    static ItemStack of(Material material, int amount, DataComponentMap components) {
        // Returns a value to the caller
        return ItemStackImpl.create(material, amount, components);
    // End of a block/expression
    }

    /**
     * Converts this item to an NBT tag containing the id (material), count (amount), and components.
     *
     * @param nbtCompound The nbt representation of the item
     */
    // Start of a method/block
    static ItemStack fromItemNBT(CompoundBinaryTag nbtCompound) {
        // Calls a method
        final Transcoder<BinaryTag> coder = new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
        // Returns a value to the caller
        return CODEC.decode(coder, nbtCompound).orElseThrow("Invalid NBT for ItemStack");
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    Material material();

    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    int amount();

    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    DataComponentMap componentPatch();

    // Annotation for the following element
    @Contract(value = "_, -> new", pure = true)
    // Calls a method
    ItemStack with(Consumer<Builder> consumer);

    /**
     * Returns a new ItemStack with the given Material set.
     *
     * @param material The material to apply
     * @return A new item stack with the new material
     *
     * <p>Note: When material is AIR, the resulting amount will always be 0. For others, the amount will be >0, e.g. 1 if 0 before</p>
     */
    // Annotation for the following element
    @Contract(value = "_, -> new", pure = true)
    // Calls a method
    ItemStack withMaterial(Material material);

    // Annotation for the following element
    @Contract(value = "_, -> new", pure = true)
    // Calls a method
    ItemStack withAmount(int amount);

    // Annotation for the following element
    @Contract(value = "_, -> new", pure = true)
    // Start of a method/block
    default ItemStack withAmount(IntUnaryOperator intUnaryOperator) {
        // Returns a value to the caller
        return withAmount(intUnaryOperator.applyAsInt(amount()));
    // End of a block/expression
    }

    /**
     * <p>Returns a new ItemStack with the given component set to the given value.</p>
     *
     * <p>Note: this should not be used to remove components, see {@link #without(DataComponent)}.</p>
     */
    // Annotation for the following element
    @Contract(value = "_, _ -> new", pure = true)
    // Calls a method
    <T> ItemStack with(DataComponent<T> component, T value);

    /**
     * Returns a new ItemStack with the given {@link Unit} component applied.
     *
     * @param component The unit component to apply
     * @return A new ItemStack with the given component applied
     */
    // Annotation for the following element
    @Contract(value = "_ -> new", pure = true)
    // Start of a method/block
    default ItemStack with(DataComponent<Unit> component) {
        // Returns a value to the caller
        return with(component, Unit.INSTANCE);
    // End of a block/expression
    }

    /**
     * Applies a transformation to the value of a component, only if present.
     *
     * @param component The component type to modify
     * @param operator  The transformation function
     * @param <T>       The component type
     * @return A new ItemStack if the component was transformed, otherwise this.
     */
    // Start of a method/block
    default <T> ItemStack with(DataComponent<T> component, UnaryOperator<T> operator) {
        // Calls a method
        T value = get(component);
        // Branch: checks a condition
        if (value == null) return this;
        // Returns a value to the caller
        return with(component, operator.apply(value));
    // End of a block/expression
    }

    /**
     * <p>Removes the given component from this item. This will explicitly remove the component from the item, as opposed
     * to reverting back to the default.</p>
     *
     * <p>For example, if {@link DataComponents#FOOD} is applied to an apple, and then this method is called,
     * the resulting itemstack will not be a food item at all, as opposed to returning to the default apple
     * food type. Likewise, if this method is called on a default apple, it will no longer be a food item.</p>
     *
     * @param component The component to remove
     * @return A new ItemStack without the given component
     */
    // Annotation for the following element
    @Contract(value = "_, -> new", pure = true)
    // Calls a method
    ItemStack without(DataComponent<?> component);

    // Annotation for the following element
    @Contract(value = "_, -> new", pure = true)
    // Start of a method/block
    default ItemStack withCustomName(Component customName) {
        // Returns a value to the caller
        return with(DataComponents.CUSTOM_NAME, customName);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_, -> new", pure = true)
    // Start of a method/block
    default ItemStack withLore(Component... lore) {
        // Returns a value to the caller
        return with(DataComponents.LORE, List.of(lore));
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_, -> new", pure = true)
    // Start of a method/block
    default ItemStack withLore(List<Component> lore) {
        // Returns a value to the caller
        return with(DataComponents.LORE, lore);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_, -> new", pure = true)
    // Start of a method/block
    default ItemStack withItemModel(String model) {
        // Returns a value to the caller
        return with(DataComponents.ITEM_MODEL, model);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_, _, _, _ -> new", pure = true)
    // Start of a method/block
    default ItemStack withCustomModelData(List<Float> floats, List<Boolean> flags, List<String> strings, List<RGBLike> colors) {
        // Returns a value to the caller
        return with(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(floats, flags, strings, colors));
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_ -> new", pure = true)
    // Start of a method/block
    default ItemStack withGlowing(boolean glowing) {
        // Returns a value to the caller
        return with(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, glowing);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "-> new", pure = true)
    // Start of a method/block
    default ItemStack withoutExtraTooltip() {
        // Returns a value to the caller
        return builder().hideExtraTooltip().build();
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default int maxStackSize() {
        // Returns a value to the caller
        return get(DataComponents.MAX_STACK_SIZE, 64);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_ -> new", pure = true)
    // Start of a method/block
    default ItemStack withMaxStackSize(int maxStackSize) {
        // Returns a value to the caller
        return with(DataComponents.MAX_STACK_SIZE, maxStackSize);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_, _ -> new", pure = true)
    // Start of a method/block
    default <T> ItemStack withTag(Tag<T> tag, @Nullable T value) {
        // Returns a value to the caller
        return with(DataComponents.CUSTOM_DATA, get(DataComponents.CUSTOM_DATA, CustomData.EMPTY).withTag(tag, value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default <T> @UnknownNullability T getTag(Tag<T> tag) {
        // Returns a value to the caller
        return get(DataComponents.CUSTOM_DATA, CustomData.EMPTY).getTag(tag);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_, -> new", pure = true)
    // Calls a method
    ItemStack consume(int amount);

    // Annotation for the following element
    @Contract(value = "_, -> new", pure = true)
    // Calls a method
    ItemStack damage(int amount);

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default boolean isAir() {
        // Returns a value to the caller
        return material() == Material.AIR;
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    boolean isSimilar(ItemStack itemStack);

    /**
     * Converts this itemstack back into a builder (starting from the current state).
     *
     * @return this itemstack, as a builder.
     */
    // Calls a method
    ItemStack.Builder builder();

    /**
     * Converts this item to an NBT tag containing the id (material), count (amount), and components (diff)
     *
     * @return The nbt representation of the item
     */
    // Calls a method
    CompoundBinaryTag toItemNBT();

    // Annotation for the following element
    @Override
    // Start of a method/block
    default HoverEvent<HoverEvent.ShowItem> asHoverEvent(UnaryOperator<HoverEvent.ShowItem> op) {
        // Branch: checks a condition
        if (componentPatch().isEmpty())
            // Returns a value to the caller
            return HoverEvent.showItem(op.apply(HoverEvent.ShowItem.showItem(material(), amount())));

        // Calls a method
        final Map<Key, DataComponentValue> dataComponents = new HashMap<>();
        // Loop: repeats a block
        for (final DataComponent.Value entry : componentPatch().entrySet())
            // Calls a method
            dataComponents.put(entry.component().key(), MinestomDataComponentValue.dataComponentValue(entry.value()));
        // Returns a value to the caller
        return HoverEvent.showItem(op.apply(HoverEvent.ShowItem.showItem(material(), amount(), dataComponents)));
    // End of a block/expression
    }

    // These functions are mirrors of ComponentHolder, but we can't actually implement that interface
    // because it conflicts with DataComponent.Holder.

    // Start of a method/block
    static Collection<Component> textComponents(ItemStack itemStack) {
        // Calls a method
        final var components = new ArrayList<>(itemStack.get(DataComponents.LORE, List.of()));
        // Calls a method
        final var displayName = itemStack.get(DataComponents.CUSTOM_NAME);
        // Branch: checks a condition
        if (displayName != null) components.add(displayName);
        // Calls a method
        final var itemName = itemStack.get(DataComponents.ITEM_NAME);
        // Branch: checks a condition
        if (itemName != null) components.add(itemName);
        // Returns a value to the caller
        return List.copyOf(components);
    // End of a block/expression
    }

    // Start of a method/block
    static ItemStack copyWithOperator(ItemStack itemStack, UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return itemStack
                // Code statement
                .with(DataComponents.CUSTOM_NAME, operator)
                // Code statement
                .with(DataComponents.ITEM_NAME, operator)
                // Start of a method/block
                .with(DataComponents.LORE, (UnaryOperator<List<Component>>) lines -> {
                    // Calls a method
                    final var translatedComponents = new ArrayList<Component>();
                    // Calls a method
                    lines.forEach(component -> translatedComponents.add(operator.apply(component)));
                    // Returns a value to the caller
                    return translatedComponents;
                // End of a block/expression
                });
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    sealed interface Hash permits ItemStackHashImpl.Air, ItemStackHashImpl.Item {
        // Calls a method
        Hash AIR = new ItemStackHashImpl.Air();

        // Start of a method/block
        static Hash of(ItemStack itemStack) {
            // Returns a value to the caller
            return ItemStackHashImpl.of(new RegistryTranscoder<>(Transcoder.CRC32_HASH, MinecraftServer.process()), itemStack);
        // End of a block/expression
        }

        // Assigns a value
        NetworkBuffer.Type<Hash> NETWORK_TYPE = ItemStackHashImpl.NETWORK_TYPE;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    sealed interface Builder permits ItemStackImpl.Builder {

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Calls a method
        Builder material(Material material);

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Calls a method
        Builder amount(int amount);

        // Annotation for the following element
        @Contract(value = "_, _ -> this")
        // Calls a method
        <T> Builder set(DataComponent<T> component, T value);

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        default Builder set(DataComponent<Unit> component) {
            // Returns a value to the caller
            return set(component, Unit.INSTANCE);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Calls a method
        Builder remove(DataComponent<?> component);

        // Start of a method/block
        default Builder customName(Component customName) {
            // Returns a value to the caller
            return set(DataComponents.CUSTOM_NAME, customName);
        // End of a block/expression
        }

        // Start of a method/block
        default Builder lore(Component... lore) {
            // Returns a value to the caller
            return set(DataComponents.LORE, List.of(lore));
        // End of a block/expression
        }

        // Start of a method/block
        default Builder lore(List<Component> lore) {
            // Returns a value to the caller
            return set(DataComponents.LORE, lore);
        // End of a block/expression
        }

        // Start of a method/block
        default Builder itemModel(String model) {
            // Returns a value to the caller
            return set(DataComponents.ITEM_MODEL, model);
        // End of a block/expression
        }

        // Start of a method/block
        default Builder customModelData(List<Float> floats, List<Boolean> flags, List<String> strings, List<RGBLike> colors) {
            // Returns a value to the caller
            return set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(floats, flags, strings, colors));
        // End of a block/expression
        }

        // Start of a method/block
        default Builder glowing() {
            // Returns a value to the caller
            return set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        // End of a block/expression
        }

        // Start of a method/block
        default Builder glowing(boolean glowing) {
            // Returns a value to the caller
            return set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, glowing);
        // End of a block/expression
        }

        // Start of a method/block
        default Builder maxStackSize(int maxStackSize) {
            // Returns a value to the caller
            return set(DataComponents.MAX_STACK_SIZE, maxStackSize);
        // End of a block/expression
        }

        /**
         * <p>Hides all components which append tooltip lines using {@link DataComponents#TOOLTIP_DISPLAY}.
         * The result should be an item with only name and lore.</p>
         */
        // Calls a method
        Builder hideExtraTooltip();

        // Annotation for the following element
        @Contract(value = "_, _ -> this")
        // Calls a method
        <T> Builder set(Tag<T> tag, @Nullable T value);

        // Start of a method/block
        default <T> void setTag(Tag<T> tag, @Nullable T value) {
            // Calls a method
            set(tag, value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "-> new", pure = true)
        // Calls a method
        ItemStack build();
    // End of a block/expression
    }
// End of a block/expression
}
