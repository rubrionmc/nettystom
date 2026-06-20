// Déclaration du paquet de ce fichier
package net.minestom.server.item;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.DataComponentValue;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEvent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEventSource;
// Import d'une classe nécessaire
import net.kyori.adventure.util.RGBLike;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomDataComponentValue;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponentMap;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.item.component.CustomData;
// Import d'une classe nécessaire
import net.minestom.server.item.component.CustomModelData;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagReadable;
// Import d'une classe nécessaire
import net.minestom.server.utils.Unit;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.IntUnaryOperator;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

/**
 * Represents an immutable item to be placed inside {@link net.minestom.server.inventory.PlayerInventory},
 * {@link net.minestom.server.inventory.Inventory} or even on the ground {@link net.minestom.server.entity.ItemEntity}.
 * <p>
 * An item stack cannot be null, {@link ItemStack#AIR} should be used instead.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface ItemStack extends TagReadable, DataComponent.Holder, HoverEventSource<HoverEvent.ShowItem>
        // Début d'une méthode/d'un bloc
        permits ItemStackImpl {

    // Appelle une méthode
    NetworkBuffer.Type<ItemStack> NETWORK_TYPE = ItemStackImpl.networkType(DataComponent.PATCH_NETWORK_TYPE);
    // Appelle une méthode
    NetworkBuffer.Type<ItemStack> UNTRUSTED_NETWORK_TYPE = ItemStackImpl.networkType(DataComponent.UNTRUSTED_PATCH_NETWORK_TYPE);
    // Affecte une valeur
    NetworkBuffer.Type<ItemStack> STRICT_NETWORK_TYPE = NETWORK_TYPE.transform(itemStack -> {
        // Appelle une méthode
        Check.argCondition(itemStack.amount() == 0 || itemStack.isAir(), "ItemStack cannot be empty");
        // Renvoie une valeur à l'appelant
        return itemStack;
    // Début d'une méthode/d'un bloc
    }, itemStack -> {
        // Appelle une méthode
        Check.argCondition(itemStack.amount() == 0 || itemStack.isAir(), "ItemStack cannot be empty");
        // Renvoie une valeur à l'appelant
        return itemStack;
    // Fin d'un bloc/d'une expression
    });
    // Affecte une valeur
    Codec<ItemStack> CODEC = new StructCodec<>() {
        // These exist because Mojang optionally decodes count (ie missing will default to 1),
        // but when encoding they always include the 1. We want to preserve this behavior and
        // since its currently a one off we can just do it here in a gross way.
        // Affecte une valeur
        private static final StructCodec<ItemStack> DECODER = StructCodec.struct(
                // Instruction de code
                "id", Material.CODEC, ItemStack::material,
                // Instruction de code
                "count", Codec.INT.optional(1), ItemStack::amount,
                // Instruction de code
                "components", DataComponent.PATCH_CODEC.optional(DataComponentMap.EMPTY), ItemStack::componentPatch,
                // Instruction de code
                ItemStack::of);
        // Affecte une valeur
        private static final StructCodec<ItemStack> ENCODER = StructCodec.struct(
                // Instruction de code
                "id", Material.CODEC, ItemStack::material,
                // Instruction de code
                "count", Codec.INT, ItemStack::amount,
                // Instruction de code
                "components", DataComponent.PATCH_CODEC.optional(DataComponentMap.EMPTY), ItemStack::componentPatch,
                // Instruction de code
                ItemStack::of);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<ItemStack> decodeFromMap(Transcoder<D> coder, Transcoder.MapLike<D> map) {
            // Renvoie une valeur à l'appelant
            return DECODER.decodeFromMap(coder, map);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encodeToMap(Transcoder<D> coder, ItemStack value, Transcoder.MapBuilder<D> map) {
            // Renvoie une valeur à l'appelant
            return ENCODER.encodeToMap(coder, value, map);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    /**
     * Constant AIR item. Should be used instead of 'null'.
     */
    // Appelle une méthode
    ItemStack AIR = new ItemStackImpl(Material.AIR, 0, DataComponentMap.EMPTY);

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> new", pure = true)
    // Début d'une méthode/d'un bloc
    static Builder builder(Material material) {
        // Renvoie une valeur à l'appelant
        return new ItemStackImpl.Builder(material, 1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> new", pure = true)
    // Début d'une méthode/d'un bloc
    static ItemStack of(Material material) {
        // Renvoie une valeur à l'appelant
        return of(material, 1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_ ,_ -> new", pure = true)
    // Début d'une méthode/d'un bloc
    static ItemStack of(Material material, int amount) {
        // Renvoie une valeur à l'appelant
        return ItemStackImpl.create(material, amount);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_ ,_ -> new", pure = true)
    // Début d'une méthode/d'un bloc
    static ItemStack of(Material material, DataComponentMap components) {
        // Renvoie une valeur à l'appelant
        return ItemStackImpl.create(material, 1, components);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_ ,_, _ -> new", pure = true)
    // Début d'une méthode/d'un bloc
    static ItemStack of(Material material, int amount, DataComponentMap components) {
        // Renvoie une valeur à l'appelant
        return ItemStackImpl.create(material, amount, components);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts this item to an NBT tag containing the id (material), count (amount), and components.
     *
     * @param nbtCompound The nbt representation of the item
     */
    // Début d'une méthode/d'un bloc
    static ItemStack fromItemNBT(CompoundBinaryTag nbtCompound) {
        // Appelle une méthode
        final Transcoder<BinaryTag> coder = new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
        // Renvoie une valeur à l'appelant
        return CODEC.decode(coder, nbtCompound).orElseThrow("Invalid NBT for ItemStack");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Material material();

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    int amount();

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    DataComponentMap componentPatch();

    // Annotation pour l'élément suivant
    @Contract(value = "_, -> new", pure = true)
    // Appelle une méthode
    ItemStack with(Consumer<Builder> consumer);

    /**
     * Returns a new ItemStack with the given Material set.
     *
     * @param material The material to apply
     * @return A new item stack with the new material
     *
     * <p>Note: When material is AIR, the resulting amount will always be 0. For others, the amount will be >0, e.g. 1 if 0 before</p>
     */
    // Annotation pour l'élément suivant
    @Contract(value = "_, -> new", pure = true)
    // Appelle une méthode
    ItemStack withMaterial(Material material);

    // Annotation pour l'élément suivant
    @Contract(value = "_, -> new", pure = true)
    // Appelle une méthode
    ItemStack withAmount(int amount);

    // Annotation pour l'élément suivant
    @Contract(value = "_, -> new", pure = true)
    // Début d'une méthode/d'un bloc
    default ItemStack withAmount(IntUnaryOperator intUnaryOperator) {
        // Renvoie une valeur à l'appelant
        return withAmount(intUnaryOperator.applyAsInt(amount()));
    // Fin d'un bloc/d'une expression
    }

    /**
     * <p>Returns a new ItemStack with the given component set to the given value.</p>
     *
     * <p>Note: this should not be used to remove components, see {@link #without(DataComponent)}.</p>
     */
    // Annotation pour l'élément suivant
    @Contract(value = "_, _ -> new", pure = true)
    // Appelle une méthode
    <T> ItemStack with(DataComponent<T> component, T value);

    /**
     * Returns a new ItemStack with the given {@link Unit} component applied.
     *
     * @param component The unit component to apply
     * @return A new ItemStack with the given component applied
     */
    // Annotation pour l'élément suivant
    @Contract(value = "_ -> new", pure = true)
    // Début d'une méthode/d'un bloc
    default ItemStack with(DataComponent<Unit> component) {
        // Renvoie une valeur à l'appelant
        return with(component, Unit.INSTANCE);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies a transformation to the value of a component, only if present.
     *
     * @param component The component type to modify
     * @param operator  The transformation function
     * @param <T>       The component type
     * @return A new ItemStack if the component was transformed, otherwise this.
     */
    // Début d'une méthode/d'un bloc
    default <T> ItemStack with(DataComponent<T> component, UnaryOperator<T> operator) {
        // Appelle une méthode
        T value = get(component);
        // Embranchement : vérifie une condition
        if (value == null) return this;
        // Renvoie une valeur à l'appelant
        return with(component, operator.apply(value));
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Contract(value = "_, -> new", pure = true)
    // Appelle une méthode
    ItemStack without(DataComponent<?> component);

    // Annotation pour l'élément suivant
    @Contract(value = "_, -> new", pure = true)
    // Début d'une méthode/d'un bloc
    default ItemStack withCustomName(Component customName) {
        // Renvoie une valeur à l'appelant
        return with(DataComponents.CUSTOM_NAME, customName);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_, -> new", pure = true)
    // Début d'une méthode/d'un bloc
    default ItemStack withLore(Component... lore) {
        // Renvoie une valeur à l'appelant
        return with(DataComponents.LORE, List.of(lore));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_, -> new", pure = true)
    // Début d'une méthode/d'un bloc
    default ItemStack withLore(List<Component> lore) {
        // Renvoie une valeur à l'appelant
        return with(DataComponents.LORE, lore);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_, -> new", pure = true)
    // Début d'une méthode/d'un bloc
    default ItemStack withItemModel(String model) {
        // Renvoie une valeur à l'appelant
        return with(DataComponents.ITEM_MODEL, model);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_, _, _, _ -> new", pure = true)
    // Début d'une méthode/d'un bloc
    default ItemStack withCustomModelData(List<Float> floats, List<Boolean> flags, List<String> strings, List<RGBLike> colors) {
        // Renvoie une valeur à l'appelant
        return with(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(floats, flags, strings, colors));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> new", pure = true)
    // Début d'une méthode/d'un bloc
    default ItemStack withGlowing(boolean glowing) {
        // Renvoie une valeur à l'appelant
        return with(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, glowing);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "-> new", pure = true)
    // Début d'une méthode/d'un bloc
    default ItemStack withoutExtraTooltip() {
        // Renvoie une valeur à l'appelant
        return builder().hideExtraTooltip().build();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int maxStackSize() {
        // Renvoie une valeur à l'appelant
        return get(DataComponents.MAX_STACK_SIZE, 64);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> new", pure = true)
    // Début d'une méthode/d'un bloc
    default ItemStack withMaxStackSize(int maxStackSize) {
        // Renvoie une valeur à l'appelant
        return with(DataComponents.MAX_STACK_SIZE, maxStackSize);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_, _ -> new", pure = true)
    // Début d'une méthode/d'un bloc
    default <T> ItemStack withTag(Tag<T> tag, @Nullable T value) {
        // Renvoie une valeur à l'appelant
        return with(DataComponents.CUSTOM_DATA, get(DataComponents.CUSTOM_DATA, CustomData.EMPTY).withTag(tag, value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default <T> @UnknownNullability T getTag(Tag<T> tag) {
        // Renvoie une valeur à l'appelant
        return get(DataComponents.CUSTOM_DATA, CustomData.EMPTY).getTag(tag);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_, -> new", pure = true)
    // Appelle une méthode
    ItemStack consume(int amount);

    // Annotation pour l'élément suivant
    @Contract(value = "_, -> new", pure = true)
    // Appelle une méthode
    ItemStack damage(int amount);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean isAir() {
        // Renvoie une valeur à l'appelant
        return material() == Material.AIR;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    boolean isSimilar(ItemStack itemStack);

    /**
     * Converts this itemstack back into a builder (starting from the current state).
     *
     * @return this itemstack, as a builder.
     */
    // Appelle une méthode
    ItemStack.Builder builder();

    /**
     * Converts this item to an NBT tag containing the id (material), count (amount), and components (diff)
     *
     * @return The nbt representation of the item
     */
    // Appelle une méthode
    CompoundBinaryTag toItemNBT();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default HoverEvent<HoverEvent.ShowItem> asHoverEvent(UnaryOperator<HoverEvent.ShowItem> op) {
        // Embranchement : vérifie une condition
        if (componentPatch().isEmpty())
            // Renvoie une valeur à l'appelant
            return HoverEvent.showItem(op.apply(HoverEvent.ShowItem.showItem(material(), amount())));

        // Affecte une valeur
        final Map<Key, DataComponentValue> dataComponents = new HashMap<>();
        // Boucle : répète un bloc
        for (final DataComponent.Value entry : componentPatch().entrySet())
            // Appelle une méthode
            dataComponents.put(entry.component().key(), MinestomDataComponentValue.dataComponentValue(entry.value()));
        // Renvoie une valeur à l'appelant
        return HoverEvent.showItem(op.apply(HoverEvent.ShowItem.showItem(material(), amount(), dataComponents)));
    // Fin d'un bloc/d'une expression
    }

    // These functions are mirrors of ComponentHolder, but we can't actually implement that interface
    // because it conflicts with DataComponent.Holder.

    // Début d'une méthode/d'un bloc
    static Collection<Component> textComponents(ItemStack itemStack) {
        // Appelle une méthode
        final var components = new ArrayList<>(itemStack.get(DataComponents.LORE, List.of()));
        // Appelle une méthode
        final var displayName = itemStack.get(DataComponents.CUSTOM_NAME);
        // Embranchement : vérifie une condition
        if (displayName != null) components.add(displayName);
        // Appelle une méthode
        final var itemName = itemStack.get(DataComponents.ITEM_NAME);
        // Embranchement : vérifie une condition
        if (itemName != null) components.add(itemName);
        // Renvoie une valeur à l'appelant
        return List.copyOf(components);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static ItemStack copyWithOperator(ItemStack itemStack, UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return itemStack
                // Instruction de code
                .with(DataComponents.CUSTOM_NAME, operator)
                // Instruction de code
                .with(DataComponents.ITEM_NAME, operator)
                // Début d'une méthode/d'un bloc
                .with(DataComponents.LORE, (UnaryOperator<List<Component>>) lines -> {
                    // Affecte une valeur
                    final var translatedComponents = new ArrayList<Component>();
                    // Appelle une méthode
                    lines.forEach(component -> translatedComponents.add(operator.apply(component)));
                    // Renvoie une valeur à l'appelant
                    return translatedComponents;
                // Fin d'un bloc/d'une expression
                });
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Hash permits ItemStackHashImpl.Air, ItemStackHashImpl.Item {
        // Appelle une méthode
        Hash AIR = new ItemStackHashImpl.Air();

        // Début d'une méthode/d'un bloc
        static Hash of(ItemStack itemStack) {
            // Renvoie une valeur à l'appelant
            return ItemStackHashImpl.of(new RegistryTranscoder<>(Transcoder.CRC32_HASH, MinecraftServer.process()), itemStack);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        NetworkBuffer.Type<Hash> NETWORK_TYPE = ItemStackHashImpl.NETWORK_TYPE;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Builder permits ItemStackImpl.Builder {

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Appelle une méthode
        Builder material(Material material);

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Appelle une méthode
        Builder amount(int amount);

        // Annotation pour l'élément suivant
        @Contract(value = "_, _ -> this")
        // Appelle une méthode
        <T> Builder set(DataComponent<T> component, T value);

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        default Builder set(DataComponent<Unit> component) {
            // Renvoie une valeur à l'appelant
            return set(component, Unit.INSTANCE);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Appelle une méthode
        Builder remove(DataComponent<?> component);

        // Début d'une méthode/d'un bloc
        default Builder customName(Component customName) {
            // Renvoie une valeur à l'appelant
            return set(DataComponents.CUSTOM_NAME, customName);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Builder lore(Component... lore) {
            // Renvoie une valeur à l'appelant
            return set(DataComponents.LORE, List.of(lore));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Builder lore(List<Component> lore) {
            // Renvoie une valeur à l'appelant
            return set(DataComponents.LORE, lore);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Builder itemModel(String model) {
            // Renvoie une valeur à l'appelant
            return set(DataComponents.ITEM_MODEL, model);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Builder customModelData(List<Float> floats, List<Boolean> flags, List<String> strings, List<RGBLike> colors) {
            // Renvoie une valeur à l'appelant
            return set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(floats, flags, strings, colors));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Builder glowing() {
            // Renvoie une valeur à l'appelant
            return set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Builder glowing(boolean glowing) {
            // Renvoie une valeur à l'appelant
            return set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, glowing);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Builder maxStackSize(int maxStackSize) {
            // Renvoie une valeur à l'appelant
            return set(DataComponents.MAX_STACK_SIZE, maxStackSize);
        // Fin d'un bloc/d'une expression
        }

        /**
         * <p>Hides all components which append tooltip lines using {@link DataComponents#TOOLTIP_DISPLAY}.
         * The result should be an item with only name and lore.</p>
         */
        // Appelle une méthode
        Builder hideExtraTooltip();

        // Annotation pour l'élément suivant
        @Contract(value = "_, _ -> this")
        // Appelle une méthode
        <T> Builder set(Tag<T> tag, @Nullable T value);

        // Début d'une méthode/d'un bloc
        default <T> void setTag(Tag<T> tag, @Nullable T value) {
            // Appelle une méthode
            set(tag, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "-> new", pure = true)
        // Appelle une méthode
        ItemStack build();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
