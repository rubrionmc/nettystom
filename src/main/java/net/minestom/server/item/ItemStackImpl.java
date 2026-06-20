// Package declaration for this file
package net.minestom.server.item;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.MinecraftServer;
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
import net.minestom.server.item.component.TooltipDisplay;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.function.Consumer;

// Type declaration (class/interface/enum/record)
record ItemStackImpl(Material material, int amount, DataComponentMap components) implements ItemStack {

    // Start of a method/block
    static NetworkBuffer.Type<ItemStack> networkType(NetworkBuffer.Type<DataComponentMap> componentPatchType) {
        // Returns a value to the caller
        return new NetworkBuffer.Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, ItemStack value) {
                // Branch: checks a condition
                if (value.isAir()) {
                    // Calls a method
                    buffer.write(NetworkBuffer.VAR_INT, 0);
                    // Returns a value to the caller
                    return;
                // End of a block/expression
                }

                // Branch: checks a condition
                if (value.amount() <= 0) {
                    // Throws an exception
                    throw new IllegalArgumentException(String.format("ItemStack %s amount must be greater than 0 if not air", value));
                // End of a block/expression
                }

                // Calls a method
                buffer.write(NetworkBuffer.VAR_INT, value.amount());
                // Calls a method
                buffer.write(NetworkBuffer.VAR_INT, value.material().id());
                // Calls a method
                buffer.write(componentPatchType, ((ItemStackImpl) value).components());
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public ItemStack read(NetworkBuffer buffer) {
                // Calls a method
                final int amount = buffer.read(NetworkBuffer.VAR_INT);
                // Branch: checks a condition
                if (amount <= 0) return ItemStack.AIR;
                // Calls a method
                final Material material = buffer.read(Material.NETWORK_TYPE);
                // Calls a method
                final DataComponentMap components = buffer.read(componentPatchType);
                // Returns a value to the caller
                return ItemStackImpl.create(material, amount, components);
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    static ItemStack create(Material material, int amount, DataComponentMap components) {
        // Branch: checks a condition
        if (amount <= 0 || material == Material.AIR) return AIR;
        // Returns a value to the caller
        return new ItemStackImpl(material, amount, components);
    // End of a block/expression
    }

    // Start of a method/block
    static ItemStack create(Material material, int amount) {
        // Returns a value to the caller
        return create(material, amount, DataComponentMap.EMPTY);
    // End of a block/expression
    }

    // Start of a method/block
    public ItemStackImpl {
        // Calls a method
        Objects.requireNonNull(material, "Material cannot be null");

        // It is relevant to create the minimal diff of the prototype so that #isSimilar returns consistent
        // results for ItemStacks which would resolve to the same thing. For example, consider two items
        // (name indicating prototype, brackets showing the components given during construction):
        // 1: apple[max_stack_size=64, custom_name=Hello]
        // 2: apple[custom_name=Hello]
        // After resolution the first set of components would turn into the second one because apple already has a
        // max stack size of 64. If we did not do this, #isSimilar would return false for these two items because of
        // their different patches.
        // It is worth noting that the client would handle both cases perfectly fine.
        // Branch: checks a condition
        if (components != DataComponentMap.EMPTY) {
            // Calls a method
            components = DataComponentMap.diff(material.prototype(), components);
        // End of a block/expression
        }

        // Having items with amount being 0 and material not being air kicks players
        // Branch: checks a condition
        if (amount == 0) material = Material.AIR;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DataComponentMap componentPatch() {
        // Returns a value to the caller
        return this.components;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> @Nullable T get(DataComponent<T> component) {
        // Returns a value to the caller
        return components.get(material.prototype(), component);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean has(DataComponent<?> component) {
        // Returns a value to the caller
        return components.has(material.prototype(), component);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack with(Consumer<ItemStack.Builder> consumer) {
        // Calls a method
        ItemStack.Builder builder = builder();
        // Calls a method
        consumer.accept(builder);
        // Returns a value to the caller
        return builder.build();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack withMaterial(Material material) {
        // Returns a value to the caller
        return create(material, Math.max(1, amount), components);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack withAmount(int amount) {
        // Branch: checks a condition
        if (amount <= 0) return ItemStack.AIR;
        // Returns a value to the caller
        return create(material, amount, components);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> ItemStack with(DataComponent<T> component, T value) {
        // Returns a value to the caller
        return create(material, amount, components.set(component, value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack without(DataComponent<?> component) {
        // We can be slightly smart here. If the component is not present, this will always be a noop.
        // No need to make a new patch with the removal only for it to be removed again when doing a diff.
        // Branch: checks a condition
        if (get(component) == null) return this;
        // Returns a value to the caller
        return create(material, amount, components.remove(component));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack consume(int amount) {
        // Returns a value to the caller
        return withAmount(amount() - amount);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack damage(int amount) {
        // Calls a method
        final Integer damage = get(DataComponents.DAMAGE);
        // Branch: checks a condition
        if (damage == null) return this;
        // Calls a method
        final Integer maxDamage = get(DataComponents.MAX_DAMAGE);
        // Branch: checks a condition
        if (maxDamage != null && damage + amount >= maxDamage) {
            // Returns a value to the caller
            return ItemStack.AIR;
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return with(DataComponents.DAMAGE, damage + amount);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isSimilar(ItemStack itemStack) {
        // Returns a value to the caller
        return material == itemStack.material() && components.equals(((ItemStackImpl) itemStack).components);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompoundBinaryTag toItemNBT() {
        // Calls a method
        final Transcoder<BinaryTag> coder = new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
        // Returns a value to the caller
        return (CompoundBinaryTag) CODEC.encode(coder, this).orElseThrow("Invalid NBT for ItemStack");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(value = "-> new", pure = true)
    // Start of a method/block
    public ItemStack.Builder builder() {
        // Returns a value to the caller
        return new Builder(material, amount, components.toPatchBuilder());
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class Builder implements ItemStack.Builder {
        // Assigns a value
        private static final TooltipDisplay EXTRA_TOOLTIP_HIDE = new TooltipDisplay(false, Set.of(
                // Code statement
                DataComponents.BANNER_PATTERNS, DataComponents.BEES, DataComponents.BLOCK_ENTITY_DATA,
                // Code statement
                DataComponents.BLOCK_STATE, DataComponents.BUNDLE_CONTENTS, DataComponents.CHARGED_PROJECTILES,
                // Code statement
                DataComponents.CONTAINER, DataComponents.CONTAINER_LOOT, DataComponents.FIREWORK_EXPLOSION,
                // Code statement
                DataComponents.FIREWORKS, DataComponents.INSTRUMENT, DataComponents.MAP_ID,
                // Code statement
                DataComponents.PAINTING_VARIANT, DataComponents.POT_DECORATIONS, DataComponents.POTION_CONTENTS,
                // Code statement
                DataComponents.TROPICAL_FISH_PATTERN, DataComponents.WRITTEN_BOOK_CONTENT,
                // Code statement
                DataComponents.UNBREAKABLE, DataComponents.ATTRIBUTE_MODIFIERS
        // Code statement
        ));

        // Code statement
        private Material material;
        // Code statement
        private int amount;
        // Code statement
        private final DataComponentMap.PatchBuilder components;

        // Start of a method/block
        Builder(Material material, int amount, DataComponentMap.PatchBuilder components) {
            // Access to the current/parent object
            this.material = material;
            // Access to the current/parent object
            this.amount = amount;
            // Access to the current/parent object
            this.components = components;
        // End of a block/expression
        }

        // Start of a method/block
        Builder(Material material, int amount) {
            // Access to the current/parent object
            this.material = material;
            // Access to the current/parent object
            this.amount = amount;
            // Access to the current/parent object
            this.components = DataComponentMap.patchBuilder();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ItemStack.Builder material(Material material) {
            // Access to the current/parent object
            this.material = material;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ItemStack.Builder amount(int amount) {
            // Access to the current/parent object
            this.amount = amount;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <T> ItemStack.Builder set(DataComponent<T> component, T value) {
            // Calls a method
            components.set(component, value);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ItemStack.Builder remove(DataComponent<?> component) {
            // Calls a method
            components.remove(component);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <T> ItemStack.Builder set(Tag<T> tag, @Nullable T value) {
            // Calls a method
            components.set(DataComponents.CUSTOM_DATA, components.get(DataComponents.CUSTOM_DATA, CustomData.EMPTY).withTag(tag, value));
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ItemStack.Builder hideExtraTooltip() {
            // Returns a value to the caller
            return set(DataComponents.TOOLTIP_DISPLAY, EXTRA_TOOLTIP_HIDE);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ItemStack build() {
            // Returns a value to the caller
            return ItemStackImpl.create(material, amount, components.build());
        // End of a block/expression
        }

    // End of a block/expression
    }
// End of a block/expression
}
