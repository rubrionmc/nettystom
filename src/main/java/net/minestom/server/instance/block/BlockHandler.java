// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interface used to provide block behavior. Set with {@link Block#withHandler(BlockHandler)}.
 * <p>
 * Implementations are expected to be thread safe.
 */
// Déclaration de type (classe/interface/enum/record)
public interface BlockHandler {

    /**
     * Called when a block has been placed.
     *
     * @param placement the placement details
     */
    // Début d'une méthode/d'un bloc
    default void onPlace(Placement placement) {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called when a block has been destroyed or replaced.
     *
     * @param destroy the destroy details
     */
    // Début d'une méthode/d'un bloc
    default void onDestroy(Destroy destroy) {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Handles interactions with this block. Can also block normal item use (containers should block when opening the
     * menu, this prevents the player from placing a block when opening it for instance).
     *
     * @param interaction the interaction details
     * @return true to let the block interaction happens, false to cancel
     */
    // Début d'une méthode/d'un bloc
    default boolean onInteract(Interaction interaction) {
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Defines custom behaviour for entities touching this block.
     *
     * @param touch the contact details
     */
    // Début d'une méthode/d'un bloc
    default void onTouch(Touch touch) {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void tick(Tick tick) {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default boolean isTickable() {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Specifies which block entity tags should be sent to the player.
     *
     * @return The list of tags from this block's block entity that should be sent to the player
     * @see <a href="https://minecraft.wiki/w/Block_entity">Block entity on the Minecraft wiki</a>
     */
    // Début d'une méthode/d'un bloc
    default Collection<Tag<?>> getBlockEntityTags() {
        // Renvoie une valeur à l'appelant
        return List.of();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default byte getBlockEntityAction() {
        // Renvoie une valeur à l'appelant
        return -1;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the id of this handler.
     * <p>
     * Used to write the block entity in the anvil world format.
     *
     * @return the key of this handler
     */
    // Appelle une méthode
    Key getKey();

    /**
     * Represents an object forwarded to {@link #onPlace(Placement)}.
     */
    // Déclaration de type (classe/interface/enum/record)
    sealed class Placement permits PlayerPlacement {
        // Instruction de code
        private final Block block;
        // Instruction de code
        private final Block previousBlock;
        // Instruction de code
        private final Instance instance;
        // Instruction de code
        private final Point blockPosition;

        // Annotation pour l'élément suivant
        @ApiStatus.Internal
        // Début d'une méthode/d'un bloc
        public Placement(Block block, Block previousBlock, Instance instance, Point blockPosition) {
            // Accès à l'objet courant/parent
            this.block = block;
            // Accès à l'objet courant/parent
            this.previousBlock = previousBlock;
            // Accès à l'objet courant/parent
            this.instance = instance;
            // Accès à l'objet courant/parent
            this.blockPosition = blockPosition;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Block getBlock() {
            // Renvoie une valeur à l'appelant
            return block;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Block getPreviousBlock() {
            // Renvoie une valeur à l'appelant
            return previousBlock;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Instance getInstance() {
            // Renvoie une valeur à l'appelant
            return instance;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Point getBlockPosition() {
            // Renvoie une valeur à l'appelant
            return blockPosition;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class PlayerPlacement extends Placement {
        // Instruction de code
        private final Player player;
        // Instruction de code
        private final PlayerHand hand;
        // Instruction de code
        private final BlockFace blockFace;
        // Instruction de code
        private final float cursorX, cursorY, cursorZ;

        // Annotation pour l'élément suivant
        @ApiStatus.Internal
        // Instruction de code
        public PlayerPlacement(Block block, Block previousBlock, Instance instance, Point blockPosition,
                               // Début d'une méthode/d'un bloc
                               Player player, PlayerHand hand, BlockFace blockFace, float cursorX, float cursorY, float cursorZ) {
            // Accès à l'objet courant/parent
            super(block, previousBlock, instance, blockPosition);
            // Accès à l'objet courant/parent
            this.player = player;
            // Accès à l'objet courant/parent
            this.hand = hand;
            // Accès à l'objet courant/parent
            this.blockFace = blockFace;
            // Accès à l'objet courant/parent
            this.cursorX = cursorX;
            // Accès à l'objet courant/parent
            this.cursorY = cursorY;
            // Accès à l'objet courant/parent
            this.cursorZ = cursorZ;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Player getPlayer() {
            // Renvoie une valeur à l'appelant
            return player;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public PlayerHand getHand() {
            // Renvoie une valeur à l'appelant
            return hand;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public BlockFace getBlockFace() {
            // Renvoie une valeur à l'appelant
            return blockFace;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public float getCursorX() {
            // Renvoie une valeur à l'appelant
            return cursorX;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public float getCursorY() {
            // Renvoie une valeur à l'appelant
            return cursorY;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public float getCursorZ() {
            // Renvoie une valeur à l'appelant
            return cursorZ;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed class Destroy permits PlayerDestroy {
        // Instruction de code
        private final Block block;
        // Instruction de code
        private final Block newBlock;
        // Instruction de code
        private final Instance instance;
        // Instruction de code
        private final Point blockPosition;

        // Annotation pour l'élément suivant
        @ApiStatus.Internal
        // Début d'une méthode/d'un bloc
        public Destroy(Block block, Block newBlock, Instance instance, Point blockPosition) {
            // Accès à l'objet courant/parent
            this.block = block;
            // Accès à l'objet courant/parent
            this.newBlock = newBlock;
            // Accès à l'objet courant/parent
            this.instance = instance;
            // Accès à l'objet courant/parent
            this.blockPosition = blockPosition;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Block getBlock() {
            // Renvoie une valeur à l'appelant
            return block;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Block getNewBlock() {
            // Renvoie une valeur à l'appelant
            return newBlock;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Instance getInstance() {
            // Renvoie une valeur à l'appelant
            return instance;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Point getBlockPosition() {
            // Renvoie une valeur à l'appelant
            return blockPosition;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class PlayerDestroy extends Destroy {
        // Instruction de code
        private final Player player;

        // Annotation pour l'élément suivant
        @ApiStatus.Internal
        // Début d'une méthode/d'un bloc
        public PlayerDestroy(Block block, Block newBlock, Instance instance, Point blockPosition, Player player) {
            // Accès à l'objet courant/parent
            super(block, newBlock, instance, blockPosition);
            // Accès à l'objet courant/parent
            this.player = player;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Player getPlayer() {
            // Renvoie une valeur à l'appelant
            return player;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class Interaction {
        // Instruction de code
        private final Block block;
        // Instruction de code
        private final Instance instance;
        // Instruction de code
        private final BlockFace blockFace;
        // Instruction de code
        private final Point blockPosition;
        // Instruction de code
        private final Point cursorPosition;
        // Instruction de code
        private final Player player;
        // Instruction de code
        private final PlayerHand hand;

        // Annotation pour l'élément suivant
        @ApiStatus.Internal
        // Début d'une méthode/d'un bloc
        public Interaction(Block block, Instance instance, BlockFace blockFace, Point blockPosition, Point cursorPosition, Player player, PlayerHand hand) {
            // Accès à l'objet courant/parent
            this.block = block;
            // Accès à l'objet courant/parent
            this.instance = instance;
            // Accès à l'objet courant/parent
            this.blockFace = blockFace;
            // Accès à l'objet courant/parent
            this.blockPosition = blockPosition;
            // Accès à l'objet courant/parent
            this.cursorPosition = cursorPosition;
            // Accès à l'objet courant/parent
            this.player = player;
            // Accès à l'objet courant/parent
            this.hand = hand;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Block getBlock() {
            // Renvoie une valeur à l'appelant
            return block;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Instance getInstance() {
            // Renvoie une valeur à l'appelant
            return instance;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public BlockFace getBlockFace() {
            // Renvoie une valeur à l'appelant
            return blockFace;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Point getBlockPosition() {
            // Renvoie une valeur à l'appelant
            return blockPosition;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Point getCursorPosition() {
            // Renvoie une valeur à l'appelant
            return cursorPosition;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Player getPlayer() {
            // Renvoie une valeur à l'appelant
            return player;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public PlayerHand getHand() {
            // Renvoie une valeur à l'appelant
            return hand;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class Touch {
        // Instruction de code
        private final Block block;
        // Instruction de code
        private final Instance instance;
        // Instruction de code
        private final Point blockPosition;
        // Instruction de code
        private final Entity touching;

        // Annotation pour l'élément suivant
        @ApiStatus.Internal
        // Début d'une méthode/d'un bloc
        public Touch(Block block, Instance instance, Point blockPosition, Entity touching) {
            // Accès à l'objet courant/parent
            this.block = block;
            // Accès à l'objet courant/parent
            this.instance = instance;
            // Accès à l'objet courant/parent
            this.blockPosition = blockPosition;
            // Accès à l'objet courant/parent
            this.touching = touching;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Block getBlock() {
            // Renvoie une valeur à l'appelant
            return block;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Instance getInstance() {
            // Renvoie une valeur à l'appelant
            return instance;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Point getBlockPosition() {
            // Renvoie une valeur à l'appelant
            return blockPosition;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Entity getTouching() {
            // Renvoie une valeur à l'appelant
            return touching;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class Tick {
        // Instruction de code
        private final Block block;
        // Instruction de code
        private final Instance instance;
        // Instruction de code
        private final Point blockPosition;

        // Annotation pour l'élément suivant
        @ApiStatus.Internal
        // Début d'une méthode/d'un bloc
        public Tick(Block block, Instance instance, Point blockPosition) {
            // Accès à l'objet courant/parent
            this.block = block;
            // Accès à l'objet courant/parent
            this.instance = instance;
            // Accès à l'objet courant/parent
            this.blockPosition = blockPosition;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Block getBlock() {
            // Renvoie une valeur à l'appelant
            return block;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Instance getInstance() {
            // Renvoie une valeur à l'appelant
            return instance;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Point getBlockPosition() {
            // Renvoie une valeur à l'appelant
            return blockPosition;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Handler used for loaded blocks with unknown namespace
     * in order to do not lose the information while saving, and for runtime debugging purpose.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Déclaration de type (classe/interface/enum/record)
    final class Dummy implements BlockHandler {
        // Appelle une méthode
        private static final Map<String, BlockHandler> DUMMY_CACHE = new ConcurrentHashMap<>();

        // Début d'une méthode/d'un bloc
        public static BlockHandler get(String namespace) {
            // Renvoie une valeur à l'appelant
            return DUMMY_CACHE.computeIfAbsent(namespace, Dummy::new);
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        private final Key key;

        // Début d'une méthode/d'un bloc
        private Dummy(String name) {
            // Appelle une méthode
            key = Key.key(name);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Key getKey() {
            // Renvoie une valeur à l'appelant
            return key;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
