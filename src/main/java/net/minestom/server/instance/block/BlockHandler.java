// Package declaration for this file
package net.minestom.server.instance.block;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interface used to provide block behavior. Set with {@link Block#withHandler(BlockHandler)}.
 * <p>
 * Implementations are expected to be thread safe.
 */
// Type declaration (class/interface/enum/record)
public interface BlockHandler {

    /**
     * Called when a block has been placed.
     *
     * @param placement the placement details
     */
    // Start of a method/block
    default void onPlace(Placement placement) {
    // End of a block/expression
    }

    /**
     * Called when a block has been destroyed or replaced.
     *
     * @param destroy the destroy details
     */
    // Start of a method/block
    default void onDestroy(Destroy destroy) {
    // End of a block/expression
    }

    /**
     * Handles interactions with this block. Can also block normal item use (containers should block when opening the
     * menu, this prevents the player from placing a block when opening it for instance).
     *
     * @param interaction the interaction details
     * @return true to let the block interaction happens, false to cancel
     */
    // Start of a method/block
    default boolean onInteract(Interaction interaction) {
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    /**
     * Defines custom behaviour for entities touching this block.
     *
     * @param touch the contact details
     */
    // Start of a method/block
    default void onTouch(Touch touch) {
    // End of a block/expression
    }

    // Start of a method/block
    default void tick(Tick tick) {
    // End of a block/expression
    }

    // Start of a method/block
    default boolean isTickable() {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    /**
     * Specifies which block entity tags should be sent to the player.
     *
     * @return The list of tags from this block's block entity that should be sent to the player
     * @see <a href="https://minecraft.wiki/w/Block_entity">Block entity on the Minecraft wiki</a>
     */
    // Start of a method/block
    default Collection<Tag<?>> getBlockEntityTags() {
        // Returns a value to the caller
        return List.of();
    // End of a block/expression
    }

    // Start of a method/block
    default byte getBlockEntityAction() {
        // Returns a value to the caller
        return -1;
    // End of a block/expression
    }

    /**
     * Gets the id of this handler.
     * <p>
     * Used to write the block entity in the anvil world format.
     *
     * @return the key of this handler
     */
    // Calls a method
    Key getKey();

    /**
     * Represents an object forwarded to {@link #onPlace(Placement)}.
     */
    // Type declaration (class/interface/enum/record)
    sealed class Placement permits PlayerPlacement {
        // Code statement
        private final Block block;
        // Code statement
        private final Block previousBlock;
        // Code statement
        private final Instance instance;
        // Code statement
        private final Point blockPosition;

        // Annotation for the following element
        @ApiStatus.Internal
        // Start of a method/block
        public Placement(Block block, Block previousBlock, Instance instance, Point blockPosition) {
            // Access to the current/parent object
            this.block = block;
            // Access to the current/parent object
            this.previousBlock = previousBlock;
            // Access to the current/parent object
            this.instance = instance;
            // Access to the current/parent object
            this.blockPosition = blockPosition;
        // End of a block/expression
        }

        // Start of a method/block
        public Block getBlock() {
            // Returns a value to the caller
            return block;
        // End of a block/expression
        }

        // Start of a method/block
        public Block getPreviousBlock() {
            // Returns a value to the caller
            return previousBlock;
        // End of a block/expression
        }

        // Start of a method/block
        public Instance getInstance() {
            // Returns a value to the caller
            return instance;
        // End of a block/expression
        }

        // Start of a method/block
        public Point getBlockPosition() {
            // Returns a value to the caller
            return blockPosition;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class PlayerPlacement extends Placement {
        // Code statement
        private final Player player;
        // Code statement
        private final PlayerHand hand;
        // Code statement
        private final BlockFace blockFace;
        // Code statement
        private final float cursorX, cursorY, cursorZ;

        // Annotation for the following element
        @ApiStatus.Internal
        // Code statement
        public PlayerPlacement(Block block, Block previousBlock, Instance instance, Point blockPosition,
                               // Start of a method/block
                               Player player, PlayerHand hand, BlockFace blockFace, float cursorX, float cursorY, float cursorZ) {
            // Access to the current/parent object
            super(block, previousBlock, instance, blockPosition);
            // Access to the current/parent object
            this.player = player;
            // Access to the current/parent object
            this.hand = hand;
            // Access to the current/parent object
            this.blockFace = blockFace;
            // Access to the current/parent object
            this.cursorX = cursorX;
            // Access to the current/parent object
            this.cursorY = cursorY;
            // Access to the current/parent object
            this.cursorZ = cursorZ;
        // End of a block/expression
        }

        // Start of a method/block
        public Player getPlayer() {
            // Returns a value to the caller
            return player;
        // End of a block/expression
        }

        // Start of a method/block
        public PlayerHand getHand() {
            // Returns a value to the caller
            return hand;
        // End of a block/expression
        }

        // Start of a method/block
        public BlockFace getBlockFace() {
            // Returns a value to the caller
            return blockFace;
        // End of a block/expression
        }

        // Start of a method/block
        public float getCursorX() {
            // Returns a value to the caller
            return cursorX;
        // End of a block/expression
        }

        // Start of a method/block
        public float getCursorY() {
            // Returns a value to the caller
            return cursorY;
        // End of a block/expression
        }

        // Start of a method/block
        public float getCursorZ() {
            // Returns a value to the caller
            return cursorZ;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    sealed class Destroy permits PlayerDestroy {
        // Code statement
        private final Block block;
        // Code statement
        private final Block newBlock;
        // Code statement
        private final Instance instance;
        // Code statement
        private final Point blockPosition;

        // Annotation for the following element
        @ApiStatus.Internal
        // Start of a method/block
        public Destroy(Block block, Block newBlock, Instance instance, Point blockPosition) {
            // Access to the current/parent object
            this.block = block;
            // Access to the current/parent object
            this.newBlock = newBlock;
            // Access to the current/parent object
            this.instance = instance;
            // Access to the current/parent object
            this.blockPosition = blockPosition;
        // End of a block/expression
        }

        // Start of a method/block
        public Block getBlock() {
            // Returns a value to the caller
            return block;
        // End of a block/expression
        }

        // Start of a method/block
        public Block getNewBlock() {
            // Returns a value to the caller
            return newBlock;
        // End of a block/expression
        }

        // Start of a method/block
        public Instance getInstance() {
            // Returns a value to the caller
            return instance;
        // End of a block/expression
        }

        // Start of a method/block
        public Point getBlockPosition() {
            // Returns a value to the caller
            return blockPosition;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class PlayerDestroy extends Destroy {
        // Code statement
        private final Player player;

        // Annotation for the following element
        @ApiStatus.Internal
        // Start of a method/block
        public PlayerDestroy(Block block, Block newBlock, Instance instance, Point blockPosition, Player player) {
            // Access to the current/parent object
            super(block, newBlock, instance, blockPosition);
            // Access to the current/parent object
            this.player = player;
        // End of a block/expression
        }

        // Start of a method/block
        public Player getPlayer() {
            // Returns a value to the caller
            return player;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class Interaction {
        // Code statement
        private final Block block;
        // Code statement
        private final Instance instance;
        // Code statement
        private final BlockFace blockFace;
        // Code statement
        private final Point blockPosition;
        // Code statement
        private final Point cursorPosition;
        // Code statement
        private final Player player;
        // Code statement
        private final PlayerHand hand;

        // Annotation for the following element
        @ApiStatus.Internal
        // Start of a method/block
        public Interaction(Block block, Instance instance, BlockFace blockFace, Point blockPosition, Point cursorPosition, Player player, PlayerHand hand) {
            // Access to the current/parent object
            this.block = block;
            // Access to the current/parent object
            this.instance = instance;
            // Access to the current/parent object
            this.blockFace = blockFace;
            // Access to the current/parent object
            this.blockPosition = blockPosition;
            // Access to the current/parent object
            this.cursorPosition = cursorPosition;
            // Access to the current/parent object
            this.player = player;
            // Access to the current/parent object
            this.hand = hand;
        // End of a block/expression
        }

        // Start of a method/block
        public Block getBlock() {
            // Returns a value to the caller
            return block;
        // End of a block/expression
        }

        // Start of a method/block
        public Instance getInstance() {
            // Returns a value to the caller
            return instance;
        // End of a block/expression
        }

        // Start of a method/block
        public BlockFace getBlockFace() {
            // Returns a value to the caller
            return blockFace;
        // End of a block/expression
        }

        // Start of a method/block
        public Point getBlockPosition() {
            // Returns a value to the caller
            return blockPosition;
        // End of a block/expression
        }

        // Start of a method/block
        public Point getCursorPosition() {
            // Returns a value to the caller
            return cursorPosition;
        // End of a block/expression
        }

        // Start of a method/block
        public Player getPlayer() {
            // Returns a value to the caller
            return player;
        // End of a block/expression
        }

        // Start of a method/block
        public PlayerHand getHand() {
            // Returns a value to the caller
            return hand;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class Touch {
        // Code statement
        private final Block block;
        // Code statement
        private final Instance instance;
        // Code statement
        private final Point blockPosition;
        // Code statement
        private final Entity touching;

        // Annotation for the following element
        @ApiStatus.Internal
        // Start of a method/block
        public Touch(Block block, Instance instance, Point blockPosition, Entity touching) {
            // Access to the current/parent object
            this.block = block;
            // Access to the current/parent object
            this.instance = instance;
            // Access to the current/parent object
            this.blockPosition = blockPosition;
            // Access to the current/parent object
            this.touching = touching;
        // End of a block/expression
        }

        // Start of a method/block
        public Block getBlock() {
            // Returns a value to the caller
            return block;
        // End of a block/expression
        }

        // Start of a method/block
        public Instance getInstance() {
            // Returns a value to the caller
            return instance;
        // End of a block/expression
        }

        // Start of a method/block
        public Point getBlockPosition() {
            // Returns a value to the caller
            return blockPosition;
        // End of a block/expression
        }

        // Start of a method/block
        public Entity getTouching() {
            // Returns a value to the caller
            return touching;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class Tick {
        // Code statement
        private final Block block;
        // Code statement
        private final Instance instance;
        // Code statement
        private final Point blockPosition;

        // Annotation for the following element
        @ApiStatus.Internal
        // Start of a method/block
        public Tick(Block block, Instance instance, Point blockPosition) {
            // Access to the current/parent object
            this.block = block;
            // Access to the current/parent object
            this.instance = instance;
            // Access to the current/parent object
            this.blockPosition = blockPosition;
        // End of a block/expression
        }

        // Start of a method/block
        public Block getBlock() {
            // Returns a value to the caller
            return block;
        // End of a block/expression
        }

        // Start of a method/block
        public Instance getInstance() {
            // Returns a value to the caller
            return instance;
        // End of a block/expression
        }

        // Start of a method/block
        public Point getBlockPosition() {
            // Returns a value to the caller
            return blockPosition;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Handler used for loaded blocks with unknown namespace
     * in order to do not lose the information while saving, and for runtime debugging purpose.
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Type declaration (class/interface/enum/record)
    final class Dummy implements BlockHandler {
        // Calls a method
        private static final Map<String, BlockHandler> DUMMY_CACHE = new ConcurrentHashMap<>();

        // Start of a method/block
        public static BlockHandler get(String namespace) {
            // Returns a value to the caller
            return DUMMY_CACHE.computeIfAbsent(namespace, Dummy::new);
        // End of a block/expression
        }

        // Code statement
        private final Key key;

        // Start of a method/block
        private Dummy(String name) {
            // Calls a method
            key = Key.key(name);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Key getKey() {
            // Returns a value to the caller
            return key;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
