// Déclaration du paquet de ce fichier
package net.minestom.server.message;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Holder;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Déclaration de type (classe/interface/enum/record)
public sealed interface ChatType extends Holder.Direct<ChatType>, ChatTypes permits ChatTypeImpl {

    // Affecte une valeur
    Codec<ChatType> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "chat", ChatTypeDecoration.CODEC, ChatType::chat,
            // Instruction de code
            "narration", ChatTypeDecoration.CODEC, ChatType::narration,
            // Instruction de code
            ChatType::create);

    // Instruction de code
    static ChatType create(
            // Instruction de code
            ChatTypeDecoration chat,
            // Instruction de code
            ChatTypeDecoration narration
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new ChatTypeImpl(chat, narration);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }


    /**
     * <p>Creates a new registry for chat types, loading the vanilla chat types.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<ChatType> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("chat_type"), REGISTRY_CODEC, RegistryData.Resource.CHAT_TYPES);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    ChatTypeDecoration chat();

    // Appelle une méthode
    ChatTypeDecoration narration();

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private ChatTypeDecoration chat;
        // Instruction de code
        private ChatTypeDecoration narration;

        // Début d'une méthode/d'un bloc
        private Builder() {
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder chat(ChatTypeDecoration chat) {
            // Accès à l'objet courant/parent
            this.chat = chat;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder narration(ChatTypeDecoration narration) {
            // Accès à l'objet courant/parent
            this.narration = narration;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public ChatType build() {
            // Renvoie une valeur à l'appelant
            return new ChatTypeImpl(chat, narration);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
