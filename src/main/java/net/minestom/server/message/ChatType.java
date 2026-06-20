// Package declaration for this file
package net.minestom.server.message;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Holder;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Type declaration (class/interface/enum/record)
public sealed interface ChatType extends Holder.Direct<ChatType>, ChatTypes permits ChatTypeImpl {

    // Assigns a value
    Codec<ChatType> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "chat", ChatTypeDecoration.CODEC, ChatType::chat,
            // Code statement
            "narration", ChatTypeDecoration.CODEC, ChatType::narration,
            // Code statement
            ChatType::create);

    // Code statement
    static ChatType create(
            // Code statement
            ChatTypeDecoration chat,
            // Code statement
            ChatTypeDecoration narration
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new ChatTypeImpl(chat, narration);
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }


    /**
     * <p>Creates a new registry for chat types, loading the vanilla chat types.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<ChatType> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("chat_type"), REGISTRY_CODEC, RegistryData.Resource.CHAT_TYPES);
    // End of a block/expression
    }

    // Calls a method
    ChatTypeDecoration chat();

    // Calls a method
    ChatTypeDecoration narration();

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Code statement
        private ChatTypeDecoration chat;
        // Code statement
        private ChatTypeDecoration narration;

        // Start of a method/block
        private Builder() {
        // End of a block/expression
        }

        // Start of a method/block
        public Builder chat(ChatTypeDecoration chat) {
            // Access to the current/parent object
            this.chat = chat;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder narration(ChatTypeDecoration narration) {
            // Access to the current/parent object
            this.narration = narration;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public ChatType build() {
            // Returns a value to the caller
            return new ChatTypeImpl(chat, narration);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
