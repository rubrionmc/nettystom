// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE;

/**
 * Represents the game mode of a player.
 * <p>
 * Can be set with {@link Player#setGameMode(GameMode)}.
 */
// Déclaration de type (classe/interface/enum/record)
public enum GameMode {
    // Instruction de code
    SURVIVAL(false, false, false),
    // Instruction de code
    CREATIVE(true, true, true),
    // Instruction de code
    ADVENTURE(false, false, false),
    // Appelle une méthode
    SPECTATOR(true, true, false);

    // Instruction de code
    private final boolean allowFlying;
    // Instruction de code
    private final boolean invulnerable;
    // Instruction de code
    private final boolean instantBreak;

    // Début d'une méthode/d'un bloc
    GameMode(boolean allowFlying, boolean invulnerable, boolean instantBreak) {
        // Accès à l'objet courant/parent
        this.allowFlying = allowFlying;
        // Accès à l'objet courant/parent
        this.invulnerable = invulnerable;
        // Accès à l'objet courant/parent
        this.instantBreak = instantBreak;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean allowFlying() {
        // Renvoie une valeur à l'appelant
        return allowFlying;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean invulnerable() {
        // Renvoie une valeur à l'appelant
        return invulnerable;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean instantBreak() {
        // Renvoie une valeur à l'appelant
        return instantBreak;
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    private static final GameMode[] VALUES = values();

    // Affecte une valeur
    public static final NetworkBuffer.Type<GameMode> NETWORK_TYPE = BYTE.transform(
            // Instruction de code
            id -> VALUES[id],
            // Instruction de code
            gameMode -> (byte) gameMode.ordinal()
    // Fin d'un bloc/d'une expression
    );

    // Affecte une valeur
    public static final NetworkBuffer.Type<GameMode> OPT_NETWORK_TYPE = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, GameMode value) {
            // Appelle une méthode
            buffer.write(BYTE, value != null ? (byte) value.ordinal() : -1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public GameMode read(NetworkBuffer buffer) {
            // Appelle une méthode
            final byte id = buffer.read(BYTE);
            // Renvoie une valeur à l'appelant
            return id != -1 ? VALUES[id] : null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
// Fin d'un bloc/d'une expression
}
