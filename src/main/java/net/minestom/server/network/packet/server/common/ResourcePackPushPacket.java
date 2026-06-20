// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.kyori.adventure.resource.ResourcePackInfo;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.COMPONENT;

// Déclaration de type (classe/interface/enum/record)
public record ResourcePackPushPacket(
        // Instruction de code
        UUID id,
        // Instruction de code
        String url,
        // Instruction de code
        String hash,
        // Instruction de code
        boolean forced,
        // Annotation pour l'élément suivant
        @Nullable Component prompt
// Début d'une méthode/d'un bloc
) implements ServerPacket.Configuration, ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ResourcePackPushPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.UUID, ResourcePackPushPacket::id,
            // Instruction de code
            NetworkBuffer.STRING, ResourcePackPushPacket::url,
            // Instruction de code
            NetworkBuffer.STRING, ResourcePackPushPacket::hash,
            // Instruction de code
            NetworkBuffer.BOOLEAN, ResourcePackPushPacket::forced,
            // Instruction de code
            COMPONENT.optional(), ResourcePackPushPacket::prompt,
            // Instruction de code
            ResourcePackPushPacket::new);

    // Début d'une méthode/d'un bloc
    public ResourcePackPushPacket(ResourcePackInfo resourcePackInfo, boolean required, @Nullable Component prompt) {
        // Appelle une méthode
        this(resourcePackInfo.id(), resourcePackInfo.uri().toString(), resourcePackInfo.hash(), required, prompt);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Renvoie une valeur à l'appelant
        return List.of(this.prompt);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return new ResourcePackPushPacket(this.id, this.url, this.hash, this.forced, operator.apply(this.prompt));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
