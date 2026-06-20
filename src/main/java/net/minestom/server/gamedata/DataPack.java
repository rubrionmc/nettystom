// Package declaration for this file
package net.minestom.server.gamedata;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Annotation for the following element
@ApiStatus.Experimental
// Type declaration (class/interface/enum/record)
public sealed interface DataPack permits DataPackImpl {

    // Calls a method
    DataPack MINECRAFT_CORE = new DataPackImpl(Key.key("core"), true);

    // Calls a method
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
    // Calls a method
    boolean isSynced();

// End of a block/expression
}
