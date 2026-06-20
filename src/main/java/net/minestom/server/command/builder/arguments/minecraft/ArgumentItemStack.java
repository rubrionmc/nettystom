// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponentMap;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.CustomData;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;

// Import of a required class
import java.io.IOException;

/**
 * Argument which can be used to retrieve an {@link ItemStack} from its material and with NBT data.
 * <p>
 * It is the same type as the one used in the /give command.
 * <p>
 * Example: diamond_sword{display:{Name:"{\"text\":\"Sword of Power\"}"}}
 */
// Type declaration (class/interface/enum/record)
public class ArgumentItemStack extends Argument<ItemStack> {

    // Assigns a value
    public static final int NO_MATERIAL = 1;
    // Assigns a value
    public static final int INVALID_NBT = 2;
    // Assigns a value
    public static final int INVALID_MATERIAL = 3;
    // Assigns a value
    public static final int INVALID_COMPONENT = 4;

    // Start of a method/block
    public ArgumentItemStack(String id) {
        // Access to the current/parent object
        super(id, true);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Returns a value to the caller
        return staticParse(input);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.ITEM_STACK;
    // End of a block/expression
    }

    /**
     * @deprecated use {@link Argument#parse(CommandSender, Argument)}
     */
    // Annotation for the following element
    @SuppressWarnings("unchecked") @Deprecated
    // Start of a method/block
    public static ItemStack staticParse(String input) throws ArgumentSyntaxException {
        // Calls a method
        var reader = new StringReader(input);

        // Calls a method
        final Material material = Material.fromKey(reader.readKey());
        // Branch: checks a condition
        if (material == null)
            // Throws an exception
            throw new ArgumentSyntaxException("Material is invalid", input, INVALID_MATERIAL);
        // Branch: checks a condition
        if (!reader.hasMore()) {
            // Returns a value to the caller
            return ItemStack.of(material); // Nothing else, we have our item
        // End of a block/expression
        }

        // Calls a method
        DataComponentMap.Builder components = DataComponentMap.builder();

        // Parse the declared components
        // Branch: checks a condition
        if (reader.peek() == '[') {
            // Calls a method
            reader.consume('[');
            // Calls a method
            final Transcoder<BinaryTag> coder = new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
            // Loop: repeats a block
            do {
                // Calls a method
                final Key componentId = reader.readKey();
                // Calls a method
                final DataComponent<?> component = DataComponent.fromKey(componentId);
                // Branch: checks a condition
                if (component == null)
                    // Throws an exception
                    throw new ArgumentSyntaxException("Unknown item component", input, INVALID_COMPONENT);

                // Calls a method
                reader.consume('=');

                // Calls a method
                final Result<Object> componentValueResult = (Result<Object>) component.decode(coder, reader.readTag());
                // Calls a method
                components.set((DataComponent<Object>) component, componentValueResult.orElseThrow());

                // Branch: checks a condition
                if (reader.peek() != ']')
                    // Calls a method
                    reader.consume(',');
            // Calls a method
            } while (reader.peek() != ']');
            // Calls a method
            reader.consume(']');
        // End of a block/expression
        }

        // Parse the NBT
        // Branch: checks a condition
        if (reader.hasMore() && reader.peek() == '{') {
            // Calls a method
            final BinaryTag nbt = reader.readTag();
            // Branch: checks a condition
            if (!(nbt instanceof CompoundBinaryTag compound))
                // Throws an exception
                throw new ArgumentSyntaxException("Item NBT must be compound", input, INVALID_NBT);

            // Assigns a value
            final CompoundBinaryTag customData = CompoundBinaryTag.builder()
                    // Code statement
                    .put(components.get(DataComponents.CUSTOM_DATA, CustomData.EMPTY).nbt())
                    // Code statement
                    .put(compound)
                    // Calls a method
                    .build();
            // Calls a method
            components.set(DataComponents.CUSTOM_DATA, new CustomData(customData));
        // End of a block/expression
        }

        // Branch: checks a condition
        if (reader.hasMore())
            // Throws an exception
            throw new ArgumentSyntaxException("Unexpected remaining input", input, INVALID_NBT);

        // Returns a value to the caller
        return ItemStack.of(material, components.build());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("ItemStack<%s>", getId());
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private static class StringReader {
        // Code statement
        private String input;
        // Assigns a value
        private int index = 0;

        // Start of a method/block
        public StringReader(String input) {
            // Access to the current/parent object
            this.input = input;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean hasMore() {
            // Returns a value to the caller
            return index < input.length();
        // End of a block/expression
        }

        // Start of a method/block
        public char peek() {
            // Branch: checks a condition
            if (!hasMore()) {
                // Throws an exception
                throw new ArgumentSyntaxException("Unexpected end of input", input, INVALID_NBT);
            // End of a block/expression
            }

            // Returns a value to the caller
            return input.charAt(index);
        // End of a block/expression
        }

        // Start of a method/block
        public void consume(char c) {
            // Calls a method
            char next = peek();
            // Branch: checks a condition
            if (next != c) {
                // Throws an exception
                throw new ArgumentSyntaxException("Expected '" + c + "', got '" + next + "'", input, INVALID_NBT);
            // End of a block/expression
            }
            // Code statement
            index++;
        // End of a block/expression
        }

        // Start of a method/block
        public Key readKey() {
            // Code statement
            char c;
            // Assigns a value
            int start = index;
            // Loop: repeats a block
            while (hasMore() && (c = peek()) != '{' && c != '[' && c != '=') {
                // Code statement
                index++;
            // End of a block/expression
            }
            // Returns a value to the caller
            return Key.key(input.substring(start, index));
        // End of a block/expression
        }

        // Start of a method/block
        public BinaryTag readTag() {
            // Exception handling
            try {
                // Calls a method
                StringBuilder remainder = new StringBuilder();
                // Calls a method
                final BinaryTag result = MinestomAdventure.tagStringIO().asTag(input.substring(index), remainder);
                // Access to the current/parent object
                this.input = remainder.toString();
                // Access to the current/parent object
                this.index = 0;

                // Returns a value to the caller
                return result;
            // Start of a method/block
            } catch (IOException e) {
                // Throws an exception
                throw new ArgumentSyntaxException("Invalid NBT", input, INVALID_NBT);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
