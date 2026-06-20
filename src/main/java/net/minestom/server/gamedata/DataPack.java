// Déclaration du paquet de ce fichier
package net.minestom.server.gamedata;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Annotation pour l'élément suivant
@ApiStatus.Experimental
// Déclaration de type (classe/interface/enum/record)
public sealed interface DataPack permits DataPackImpl {

    // Appelle une méthode
    DataPack MINECRAFT_CORE = new DataPackImpl(Key.key("core"), true);

    // Appelle une méthode
    DataPack MINESTOM_UNNAMED = new DataPackImpl(Key.key("minestom", "unnamed"), false);

    /**
     * <p>Returns true if this data pack is synced with the client. The null data pack is never synced.</p>
     *
     * <p>In practice, this currently only makes sense for vanilla and modded content.</p>
     *
     * <p>TODO: in the future this should be based on what the client responds with known packs, I suppose.</p>
     *
     * @return true if this data pack is synced with the client, false otherwise.
     */
    // Appelle une méthode
    boolean isSynced();

// Fin d'un bloc/d'une expression
}
