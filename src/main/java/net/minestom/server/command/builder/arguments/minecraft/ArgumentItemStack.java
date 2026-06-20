// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponentMap;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.CustomData;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;

// Import d'une classe nécessaire
import java.io.IOException;

/**
 * Argument which can be used to retrieve an {@link ItemStack} from its material and with NBT data.
 * <p>
 * It is the same type as the one used in the /give command.
 * <p>
 * Example: diamond_sword{display:{Name:"{\"text\":\"Sword of Power\"}"}}
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentItemStack extends Argument<ItemStack> {

    // Affecte une valeur
    public static final int NO_MATERIAL = 1;
    // Affecte une valeur
    public static final int INVALID_NBT = 2;
    // Affecte une valeur
    public static final int INVALID_MATERIAL = 3;
    // Affecte une valeur
    public static final int INVALID_COMPONENT = 4;

    // Début d'une méthode/d'un bloc
    public ArgumentItemStack(String id) {
        // Accès à l'objet courant/parent
        super(id, true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Renvoie une valeur à l'appelant
        return staticParse(input);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.ITEM_STACK;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link Argument#parse(CommandSender, Argument)}
     */
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked") @Deprecated
    // Début d'une méthode/d'un bloc
    public static ItemStack staticParse(String input) throws ArgumentSyntaxException {
        // Appelle une méthode
        var reader = new StringReader(input);

        // Appelle une méthode
        final Material material = Material.fromKey(reader.readKey());
        // Embranchement : vérifie une condition
        if (material == null)
            // Lève une exception
            throw new ArgumentSyntaxException("Material is invalid", input, INVALID_MATERIAL);
        // Embranchement : vérifie une condition
        if (!reader.hasMore()) {
            // Renvoie une valeur à l'appelant
            return ItemStack.of(material); // Nothing else, we have our item
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        DataComponentMap.Builder components = DataComponentMap.builder();

        // Parse the declared components
        // Embranchement : vérifie une condition
        if (reader.peek() == '[') {
            // Appelle une méthode
            reader.consume('[');
            // Appelle une méthode
            final Transcoder<BinaryTag> coder = new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
            // Boucle : répète un bloc
            do {
                // Appelle une méthode
                final Key componentId = reader.readKey();
                // Appelle une méthode
                final DataComponent<?> component = DataComponent.fromKey(componentId);
                // Embranchement : vérifie une condition
                if (component == null)
                    // Lève une exception
                    throw new ArgumentSyntaxException("Unknown item component", input, INVALID_COMPONENT);

                // Appelle une méthode
                reader.consume('=');

                // Appelle une méthode
                final Result<Object> componentValueResult = (Result<Object>) component.decode(coder, reader.readTag());
                // Appelle une méthode
                components.set((DataComponent<Object>) component, componentValueResult.orElseThrow());

                // Embranchement : vérifie une condition
                if (reader.peek() != ']')
                    // Appelle une méthode
                    reader.consume(',');
            // Appelle une méthode
            } while (reader.peek() != ']');
            // Appelle une méthode
            reader.consume(']');
        // Fin d'un bloc/d'une expression
        }

        // Parse the NBT
        // Embranchement : vérifie une condition
        if (reader.hasMore() && reader.peek() == '{') {
            // Appelle une méthode
            final BinaryTag nbt = reader.readTag();
            // Embranchement : vérifie une condition
            if (!(nbt instanceof CompoundBinaryTag compound))
                // Lève une exception
                throw new ArgumentSyntaxException("Item NBT must be compound", input, INVALID_NBT);

            // Affecte une valeur
            final CompoundBinaryTag customData = CompoundBinaryTag.builder()
                    // Instruction de code
                    .put(components.get(DataComponents.CUSTOM_DATA, CustomData.EMPTY).nbt())
                    // Instruction de code
                    .put(compound)
                    // Appelle une méthode
                    .build();
            // Appelle une méthode
            components.set(DataComponents.CUSTOM_DATA, new CustomData(customData));
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (reader.hasMore())
            // Lève une exception
            throw new ArgumentSyntaxException("Unexpected remaining input", input, INVALID_NBT);

        // Renvoie une valeur à l'appelant
        return ItemStack.of(material, components.build());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("ItemStack<%s>", getId());
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private static class StringReader {
        // Instruction de code
        private String input;
        // Affecte une valeur
        private int index = 0;

        // Début d'une méthode/d'un bloc
        public StringReader(String input) {
            // Accès à l'objet courant/parent
            this.input = input;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean hasMore() {
            // Renvoie une valeur à l'appelant
            return index < input.length();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public char peek() {
            // Embranchement : vérifie une condition
            if (!hasMore()) {
                // Lève une exception
                throw new ArgumentSyntaxException("Unexpected end of input", input, INVALID_NBT);
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return input.charAt(index);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public void consume(char c) {
            // Appelle une méthode
            char next = peek();
            // Embranchement : vérifie une condition
            if (next != c) {
                // Lève une exception
                throw new ArgumentSyntaxException("Expected '" + c + "', got '" + next + "'", input, INVALID_NBT);
            // Fin d'un bloc/d'une expression
            }
            // Instruction de code
            index++;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Key readKey() {
            // Instruction de code
            char c;
            // Affecte une valeur
            int start = index;
            // Boucle : répète un bloc
            while (hasMore() && (c = peek()) != '{' && c != '[' && c != '=') {
                // Instruction de code
                index++;
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return Key.key(input.substring(start, index));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public BinaryTag readTag() {
            // Gestion des exceptions
            try {
                // Appelle une méthode
                StringBuilder remainder = new StringBuilder();
                // Appelle une méthode
                final BinaryTag result = MinestomAdventure.tagStringIO().asTag(input.substring(index), remainder);
                // Accès à l'objet courant/parent
                this.input = remainder.toString();
                // Accès à l'objet courant/parent
                this.index = 0;

                // Renvoie une valeur à l'appelant
                return result;
            // Début d'une méthode/d'un bloc
            } catch (IOException e) {
                // Lève une exception
                throw new ArgumentSyntaxException("Invalid NBT", input, INVALID_NBT);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
