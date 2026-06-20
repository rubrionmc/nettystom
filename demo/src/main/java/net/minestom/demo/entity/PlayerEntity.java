// Déclaration du paquet de ce fichier
package net.minestom.demo.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.PlayerInfoRemovePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.PlayerInfoUpdatePacket;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public class PlayerEntity extends Entity {
    // Affecte une valeur
    private final String username = "Minestom";

    // Instruction de code
    private final String skinTexture = "ewogICJ0aW1lc3RhbXAiIDogMTc1NzcyNDE5NDAzNiwKICAicHJvZmlsZUlkIiA6ICI3MGNkYjNiZjhhN2E0ODYxYWY0ZWEzY2U1MDcwY2ViOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJNaW5lc3RvbSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iMTNkOTU0ZjRjMGJiMzYyY2MxNzYxYWRhYWY5N2NjYzcxNWM1OTc5MDdkNjdlNDI5ZmQzNzM3N2FiZWYwNTkyIgogICAgfQogIH0KfQ==";
    // Affecte une valeur
    private final String skinSignature = "TaCq03pmLJthNtgsp7s2LnNgMLnOyy2B4GlGC7stxQ0O5nH42Qp7z7MBVa2hCJVPTje4bjWIoaG4Ydsw/RrE1MZ+XP9EgN5ZYipeoeHqk21uYlGWzlTqTvMxjExHmoFZFvyhWSfaqp6fM69l+Da9pf2gNeqPLg8J8GdQCBHhGbJFSP9f/Cb/fTp9/Y69eXhxP9TBLA1Eqo9bnYJzYBqIYXkbeyPZDJnZ1wOhnlZCNzo/276qsPIMMkSYRI0x4AAs0NfadbdAyszb8txW3eULQHlh2kOgColzC3Mldj3ad2/UXF4tsEJX6YcEmrbJU2lbanhVUCECKkyZxvOVGe8D6ImOyPGuGouAMBEDwWpa8gQTV96czDe0CJbGbT0l84G0mB5N8v1h1j8/7bupQ4i3S9aUHYyl0g+EtFGuUIMk8DdiAGNMJNi21zSm/oZ8W7fYO1rg8MTggHebzGvRUWY1hF9NcjHl5bBoT1VzW5EM8LFqOZAwRXWIZEZuMwaXhlhNGyDg/zV9eGUmhykiLTWvh0iA/OK7dtKqPo4XxewDIPEjpFo7Q0q1hGkHlhuA2u9+0amHPW/TyQOT2KbK+1fW63nAT8N8KfXLBSVC/5NOB+KEBzBbIY2vqTJW3p9+I4dZQ8hDCF8WCu3zD9zal4u3A8aGQpcMLzOG+BrghNPZz/A=";

    // Début d'une méthode/d'un bloc
    public PlayerEntity() {
        // Accès à l'objet courant/parent
        super(EntityType.PLAYER);

        // Appelle une méthode
        setNoGravity(true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void updateNewViewer(Player player) {
        // Affecte une valeur
        var properties = new ArrayList<PlayerInfoUpdatePacket.Property>();
        // Appelle une méthode
        properties.add(new PlayerInfoUpdatePacket.Property("textures", skinTexture, skinSignature));
        // Affecte une valeur
        var entry = new PlayerInfoUpdatePacket.Entry(
                // Instruction de code
                getUuid(), username, properties, false,
                // Instruction de code
                0, GameMode.SURVIVAL, null,
                // Instruction de code
                null, 0, true);
        // Appelle une méthode
        player.sendPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.ADD_PLAYER, entry));

        // Spawn the player entity
        // Accès à l'objet courant/parent
        super.updateNewViewer(player);

        // Enable skin layers
        // Instruction de code
        player.sendPackets(new EntityMetaDataPacket(getEntityId(), Map.of(
                // Instruction de code
                MetadataDef.Avatar.DISPLAYED_MODEL_PARTS_FLAGS.index(), Metadata.Byte((byte) 127)
        // Instruction de code
        )));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void updateOldViewer(Player player) {
        // Accès à l'objet courant/parent
        super.updateOldViewer(player);

        // Appelle une méthode
        player.sendPacket(new PlayerInfoRemovePacket(getUuid()));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
