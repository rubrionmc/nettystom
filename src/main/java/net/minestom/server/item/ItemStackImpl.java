// Déclaration du paquet de ce fichier
package net.minestom.server.item;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
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
import net.minestom.server.item.component.TooltipDisplay;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Déclaration de type (classe/interface/enum/record)
record ItemStackImpl(Material material, int amount, DataComponentMap components) implements ItemStack {

    // Début d'une méthode/d'un bloc
    static NetworkBuffer.Type<ItemStack> networkType(NetworkBuffer.Type<DataComponentMap> componentPatchType) {
        // Renvoie une valeur à l'appelant
        return new NetworkBuffer.Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, ItemStack value) {
                // Embranchement : vérifie une condition
                if (value.isAir()) {
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.VAR_INT, 0);
                    // Renvoie une valeur à l'appelant
                    return;
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (value.amount() <= 0) {
                    // Lève une exception
                    throw new IllegalArgumentException(String.format("ItemStack %s amount must be greater than 0 if not air", value));
                // Fin d'un bloc/d'une expression
                }

                // Appelle une méthode
                buffer.write(NetworkBuffer.VAR_INT, value.amount());
                // Appelle une méthode
                buffer.write(NetworkBuffer.VAR_INT, value.material().id());
                // Appelle une méthode
                buffer.write(componentPatchType, ((ItemStackImpl) value).components());
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public ItemStack read(NetworkBuffer buffer) {
                // Appelle une méthode
                int amount = buffer.read(NetworkBuffer.VAR_INT);
                // Embranchement : vérifie une condition
                if (amount <= 0) return ItemStack.AIR;
                // Appelle une méthode
                Material material = Material.fromId(buffer.read(NetworkBuffer.VAR_INT));
                // Appelle une méthode
                DataComponentMap components = buffer.read(componentPatchType);
                // Renvoie une valeur à l'appelant
                return ItemStackImpl.create(material, amount, components);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static ItemStack create(Material material, int amount, DataComponentMap components) {
        // Embranchement : vérifie une condition
        if (amount <= 0 || material == Material.AIR) return AIR;
        // Renvoie une valeur à l'appelant
        return new ItemStackImpl(material, amount, components);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static ItemStack create(Material material, int amount) {
        // Renvoie une valeur à l'appelant
        return create(material, amount, DataComponentMap.EMPTY);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemStackImpl {
        // Appelle une méthode
        Check.notNull(material, "Material cannot be null");

        // It is relevant to create the minimal diff of the prototype so that #isSimilar returns consistent
        // results for ItemStacks which would resolve to the same thing. For example, consider two items
        // (name indicating prototype, brackets showing the components given during construction):
        // 1: apple[max_stack_size=64, custom_name=Hello]
        // 2: apple[custom_name=Hello]
        // After resolution the first set of components would turn into the second one because apple already has a
        // max stack size of 64. If we did not do this, #isSimilar would return false for these two items because of
        // their different patches.
        // It is worth noting that the client would handle both cases perfectly fine.
        // Embranchement : vérifie une condition
        if (components != DataComponentMap.EMPTY) {
            // Appelle une méthode
            components = DataComponentMap.diff(material.prototype(), components);
        // Fin d'un bloc/d'une expression
        }

        // Having items with amount being 0 and material not being air kicks players
        // Embranchement : vérifie une condition
        if (amount == 0) material = Material.AIR;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DataComponentMap componentPatch() {
        // Renvoie une valeur à l'appelant
        return this.components;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> @Nullable T get(DataComponent<T> component) {
        // Renvoie une valeur à l'appelant
        return components.get(material.prototype(), component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean has(DataComponent<?> component) {
        // Renvoie une valeur à l'appelant
        return components.has(material.prototype(), component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack with(Consumer<ItemStack.Builder> consumer) {
        // Appelle une méthode
        ItemStack.Builder builder = builder();
        // Appelle une méthode
        consumer.accept(builder);
        // Renvoie une valeur à l'appelant
        return builder.build();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack withMaterial(Material material) {
        // Embranchement : vérifie une condition
        if (material == Material.AIR) return ItemStack.AIR;
        // Renvoie une valeur à l'appelant
        return new ItemStackImpl(material, Math.max(1, amount), components);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack withAmount(int amount) {
        // Embranchement : vérifie une condition
        if (amount <= 0) return ItemStack.AIR;
        // Renvoie une valeur à l'appelant
        return create(material, amount, components);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> ItemStack with(DataComponent<T> component, T value) {
        // Renvoie une valeur à l'appelant
        return new ItemStackImpl(material, amount, components.set(component, value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack without(DataComponent<?> component) {
        // We can be slightly smart here. If the component is not present, this will always be a noop.
        // No need to make a new patch with the removal only for it to be removed again when doing a diff.
        // Embranchement : vérifie une condition
        if (get(component) == null) return this;
        // Renvoie une valeur à l'appelant
        return new ItemStackImpl(material, amount, components.remove(component));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack consume(int amount) {
        // Renvoie une valeur à l'appelant
        return withAmount(amount() - amount);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack damage(int amount) {
        // Appelle une méthode
        final Integer damage = get(DataComponents.DAMAGE);
        // Embranchement : vérifie une condition
        if (damage == null) return this;
        // Appelle une méthode
        final Integer maxDamage = get(DataComponents.MAX_DAMAGE);
        // Embranchement : vérifie une condition
        if (maxDamage != null && damage + amount >= maxDamage) {
            // Renvoie une valeur à l'appelant
            return ItemStack.AIR;
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return with(DataComponents.DAMAGE, damage + amount);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isSimilar(ItemStack itemStack) {
        // Renvoie une valeur à l'appelant
        return material == itemStack.material() && components.equals(((ItemStackImpl) itemStack).components);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompoundBinaryTag toItemNBT() {
        // Appelle une méthode
        final Transcoder<BinaryTag> coder = new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
        // Renvoie une valeur à l'appelant
        return (CompoundBinaryTag) CODEC.encode(coder, this).orElseThrow("Invalid NBT for ItemStack");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(value = "-> new", pure = true)
    // Début d'une méthode/d'un bloc
    public ItemStack.Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder(material, amount, components.toPatchBuilder());
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class Builder implements ItemStack.Builder {
        // Instruction de code
        private Material material;
        // Instruction de code
        private int amount;
        // Instruction de code
        private DataComponentMap.PatchBuilder components;

        // Début d'une méthode/d'un bloc
        Builder(Material material, int amount, DataComponentMap.PatchBuilder components) {
            // Accès à l'objet courant/parent
            this.material = material;
            // Accès à l'objet courant/parent
            this.amount = amount;
            // Accès à l'objet courant/parent
            this.components = components;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        Builder(Material material, int amount) {
            // Accès à l'objet courant/parent
            this.material = material;
            // Accès à l'objet courant/parent
            this.amount = amount;
            // Accès à l'objet courant/parent
            this.components = DataComponentMap.patchBuilder();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ItemStack.Builder material(Material material) {
            // Accès à l'objet courant/parent
            this.material = material;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ItemStack.Builder amount(int amount) {
            // Accès à l'objet courant/parent
            this.amount = amount;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <T> ItemStack.Builder set(DataComponent<T> component, T value) {
            // Appelle une méthode
            components.set(component, value);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ItemStack.Builder remove(DataComponent<?> component) {
            // Appelle une méthode
            components.remove(component);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <T> ItemStack.Builder set(Tag<T> tag, @Nullable T value) {
            // Appelle une méthode
            components.set(DataComponents.CUSTOM_DATA, components.get(DataComponents.CUSTOM_DATA, CustomData.EMPTY).withTag(tag, value));
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ItemStack.Builder hideExtraTooltip() {
            // Renvoie une valeur à l'appelant
            return set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(false, Set.of(
                    // Instruction de code
                    DataComponents.BANNER_PATTERNS, DataComponents.BEES, DataComponents.BLOCK_ENTITY_DATA,
                    // Instruction de code
                    DataComponents.BLOCK_STATE, DataComponents.BUNDLE_CONTENTS, DataComponents.CHARGED_PROJECTILES,
                    // Instruction de code
                    DataComponents.CONTAINER, DataComponents.CONTAINER_LOOT, DataComponents.FIREWORK_EXPLOSION,
                    // Instruction de code
                    DataComponents.FIREWORKS, DataComponents.INSTRUMENT, DataComponents.MAP_ID,
                    // Instruction de code
                    DataComponents.PAINTING_VARIANT, DataComponents.POT_DECORATIONS, DataComponents.POTION_CONTENTS,
                    // Instruction de code
                    DataComponents.TROPICAL_FISH_PATTERN, DataComponents.WRITTEN_BOOK_CONTENT,
                    // Instruction de code
                    DataComponents.UNBREAKABLE, DataComponents.ATTRIBUTE_MODIFIERS
            // Instruction de code
            )));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ItemStack build() {
            // Renvoie une valeur à l'appelant
            return ItemStackImpl.create(material, amount, components.build());
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
