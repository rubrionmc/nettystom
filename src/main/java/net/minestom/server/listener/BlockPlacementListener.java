// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.collision.CollisionUtils;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerBlockInteractEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.instance.block.BlockManager;
// Import of a required class
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.BlockPredicates;
// Import of a required class
import net.minestom.server.item.component.ItemBlockState;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPlayerBlockPlacementPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.AcknowledgeBlockChangePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.BlockChangePacket;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkUtils;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import net.minestom.server.world.DimensionType;

// Type declaration (class/interface/enum/record)
public class BlockPlacementListener {
    // Calls a method
    private static final BlockManager BLOCK_MANAGER = MinecraftServer.getBlockManager();

    // Start of a method/block
    public static void listener(ClientPlayerBlockPlacementPacket packet, Player player) {
        // Calls a method
        final PlayerHand hand = packet.hand();
        // Calls a method
        final BlockFace blockFace = packet.blockFace();
        // Calls a method
        Point blockPosition = packet.blockPosition();

        // Calls a method
        final Instance instance = player.getInstance();
        // Branch: checks a condition
        if (instance == null)
            // Returns a value to the caller
            return;

        // Prevent outdated/modified client data
        // Calls a method
        final Chunk interactedChunk = instance.getChunkAt(blockPosition);
        // Branch: checks a condition
        if (!ChunkUtils.isLoaded(interactedChunk)) {
            // Client tried to place a block in an unloaded chunk, ignore the request
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final ItemStack usedItem = player.getItemInHand(hand);
        // Calls a method
        final Block interactedBlock = instance.getBlock(blockPosition);

        // Calls a method
        final Point cursorPosition = new Vec(packet.cursorPositionX(), packet.cursorPositionY(), packet.cursorPositionZ());

        // Interact at block
        // FIXME: onUseOnBlock
        // Calls a method
        PlayerBlockInteractEvent playerBlockInteractEvent = new PlayerBlockInteractEvent(player, hand, instance, interactedBlock, blockPosition.asBlockVec(), cursorPosition, blockFace);
        // Calls a method
        EventDispatcher.call(playerBlockInteractEvent);
        // Calls a method
        boolean blockUse = playerBlockInteractEvent.isBlockingItemUse();
        // Branch: checks a condition
        if (!playerBlockInteractEvent.isCancelled()) {
            // Calls a method
            final var handler = interactedBlock.handler();
            // Branch: checks a condition
            if (handler != null) {
                // Calls a method
                blockUse |= !handler.onInteract(new BlockHandler.Interaction(interactedBlock, instance, blockFace, blockPosition, cursorPosition, player, hand));
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Branch: checks a condition
        if (blockUse) {
            // If the usage was blocked then the world is already up-to-date (from the prior handlers),
            // So ack the change with the current world state.
            // Calls a method
            player.sendPacket(new AcknowledgeBlockChangePacket(packet.sequence()));
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final Material useMaterial = usedItem.material();
        // Branch: checks a condition
        if (!useMaterial.isBlock()) {
            // Player didn't try to place a block but interacted with one
            // Calls a method
            PlayerUseItemOnBlockEvent event = new PlayerUseItemOnBlockEvent(player, hand, usedItem, blockPosition, cursorPosition, blockFace);
            // Calls a method
            EventDispatcher.call(event);
            // Ack the block change. This is required to reset the client prediction to the server state.
            // Calls a method
            player.sendPacket(new AcknowledgeBlockChangePacket(packet.sequence()));
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Verify if the player can place the block
        // Assigns a value
        boolean canPlaceBlock = true;
        // Check if the player is allowed to place blocks based on their game mode
        // Branch: checks a condition
        if (player.getGameMode() == GameMode.SPECTATOR) {
            // Assigns a value
            canPlaceBlock = false; // Spectators can't place blocks
        // Branch: checks a condition
        } else if (player.getGameMode() == GameMode.ADVENTURE) {
            //Check if the block can be placed on the block
            // Calls a method
            BlockPredicates placePredicate = usedItem.get(DataComponents.CAN_PLACE_ON, BlockPredicates.NEVER);
            // Calls a method
            canPlaceBlock = placePredicate.test(interactedBlock);
        // End of a block/expression
        }


        // Get the newly placed block position
        //todo it feels like it should be possible to have better replacement rules than this, feels pretty scuffed.
        // Assigns a value
        Point placementPosition = blockPosition;
        // Calls a method
        var interactedPlacementRule = BLOCK_MANAGER.getBlockPlacementRule(interactedBlock);
        // Branch: checks a condition
        if (!interactedBlock.isAir() && (interactedPlacementRule == null || !interactedPlacementRule.isSelfReplaceable(
                // Creates a new object
                new BlockPlacementRule.Replacement(interactedBlock, blockFace, cursorPosition, false, useMaterial)))) {
            // If the block is not replaceable, try to place next to it.
            // Calls a method
            placementPosition = blockPosition.relative(blockFace);

            // Calls a method
            var placementBlock = instance.getBlock(placementPosition);
            // Calls a method
            var placementRule = BLOCK_MANAGER.getBlockPlacementRule(placementBlock);
            // Branch: checks a condition
            if (!placementBlock.registry().isReplaceable() && !(placementRule != null && placementRule.isSelfReplaceable(
                    // Creates a new object
                    new BlockPlacementRule.Replacement(placementBlock, blockFace, cursorPosition, true, useMaterial)))) {
                // If the block is still not replaceable, cancel the placement
                // Assigns a value
                canPlaceBlock = false;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        final DimensionType instanceDim = instance.getCachedDimensionType();
        // Branch: checks a condition
        if (placementPosition.y() >= instanceDim.maxY() || placementPosition.y() < instanceDim.minY()) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Ensure that the final placement position is inside the world border.
        // Branch: checks a condition
        if (!instance.getWorldBorder().inBounds(placementPosition)) {
            // Assigns a value
            canPlaceBlock = false;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (!canPlaceBlock) {
            // Send a block change with the real block in the instance to keep the client in sync,
            // using refreshChunk results in the client not being in sync
            // after rapid invalid block placements
            // Calls a method
            final Block block = instance.getBlock(placementPosition);
            // Calls a method
            player.sendPacket(new BlockChangePacket(placementPosition, block));
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final Chunk chunk = instance.getChunkAt(placementPosition);
        // Code statement
        Check.stateCondition(!ChunkUtils.isLoaded(chunk),
                // Code statement
                "A player tried to place a block in the border of a loaded chunk {0}", placementPosition);
        // Branch: checks a condition
        if (chunk.isReadOnly()) {
            // Calls a method
            refresh(player, chunk);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final ItemBlockState blockState = usedItem.get(DataComponents.BLOCK_STATE, ItemBlockState.EMPTY);
        // Calls a method
        final Block placedBlock = blockState.apply(useMaterial.block());

        // Calls a method
        Entity collisionEntity = CollisionUtils.canPlaceBlockAt(instance, placementPosition, placedBlock);
        // Branch: checks a condition
        if (collisionEntity != null) {
            // If a player is trying to place a block on themselves, the client will send a block change but will not set the block on the client
            // For this reason, the block doesn't need to be updated for the client

            // Client also doesn't predict placement of blocks on entities, but we need to refresh for cases where bounding boxes on the server don't match the client
            // Branch: checks a condition
            if (collisionEntity != player)
                // Calls a method
                refresh(player, chunk);

            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // BlockPlaceEvent check
        // Calls a method
        PlayerBlockPlaceEvent playerBlockPlaceEvent = new PlayerBlockPlaceEvent(player, instance, placedBlock, blockFace, placementPosition.asBlockVec(), cursorPosition, packet.hand());
        // Calls a method
        playerBlockPlaceEvent.consumeBlock(player.getGameMode() != GameMode.CREATIVE);
        // Calls a method
        playerBlockPlaceEvent.setDoBlockUpdates(blockState.equals(useMaterial.prototype().get(DataComponents.BLOCK_STATE, ItemBlockState.EMPTY)));
        // Calls a method
        EventDispatcher.call(playerBlockPlaceEvent);
        // Branch: checks a condition
        if (playerBlockPlaceEvent.isCancelled()) {
            // Calls a method
            refresh(player, chunk);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Place the block
        // Calls a method
        Block resultBlock = playerBlockPlaceEvent.getBlock();
        // Code statement
        instance.placeBlock(new BlockHandler.PlayerPlacement(resultBlock, instance.getBlock(placementPosition), instance, placementPosition, player, hand, blockFace,
                // Calls a method
                packet.cursorPositionX(), packet.cursorPositionY(), packet.cursorPositionZ()), playerBlockPlaceEvent.shouldDoBlockUpdates());
        // Calls a method
        player.sendPacket(new AcknowledgeBlockChangePacket(packet.sequence()));
        // Block consuming
        // Branch: checks a condition
        if (playerBlockPlaceEvent.doesConsumeBlock()) {
            // Consume the block in the player's hand
            // Calls a method
            final ItemStack newUsedItem = usedItem.consume(1);
            // Calls a method
            player.setItemInHand(hand, newUsedItem);
        // Alternative branch of the condition
        } else {
            // Prevent invisible item on client
            // Calls a method
            player.getInventory().update();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void refresh(Player player, Chunk chunk) {
        // Calls a method
        player.getInventory().update();
        // Calls a method
        chunk.sendChunk(player);
    // End of a block/expression
    }
// End of a block/expression
}
