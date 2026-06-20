// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record ClientEditBookPacket(int slot, List<String> pages,
                                   // Annotation pour l'élément suivant
                                   @Nullable String title) implements ClientPacket {
    // Affecte une valeur
    public static final int MAX_PAGES = 100;
    // Affecte une valeur
    public static final int MAX_TITLE_LENGTH = 32;
    // Affecte une valeur
    public static final int MAX_PAGE_LENGTH = 1024;

    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientEditBookPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientEditBookPacket::slot,
            // Instruction de code
            STRING.list(MAX_PAGES), ClientEditBookPacket::pages,
            // Instruction de code
            STRING.optional(), ClientEditBookPacket::title,
            // Instruction de code
            ClientEditBookPacket::new);

    // Début d'une méthode/d'un bloc
    public ClientEditBookPacket {
        // Boucle : répète un bloc
        for (var page : pages) {
            // Embranchement : vérifie une condition
            if (page.length() > MAX_PAGE_LENGTH) {
                // Lève une exception
                throw new IllegalArgumentException("Page length cannot be greater than " + MAX_PAGE_LENGTH);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (title != null && title.length() > MAX_TITLE_LENGTH) {
            // Lève une exception
            throw new IllegalArgumentException("Title length cannot be greater than " + MAX_TITLE_LENGTH);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        pages = List.copyOf(pages);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
