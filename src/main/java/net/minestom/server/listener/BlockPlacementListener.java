// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.collision.CollisionUtils;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerBlockInteractEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockManager;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.BlockPredicates;
// Import d'une classe nécessaire
import net.minestom.server.item.component.ItemBlockState;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPlayerBlockPlacementPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.AcknowledgeBlockChangePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.BlockChangePacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;

// Déclaration de type (classe/interface/enum/record)
public class BlockPlacementListener {
    // Appelle une méthode
    private static final BlockManager BLOCK_MANAGER = MinecraftServer.getBlockManager();

    // Début d'une méthode/d'un bloc
    public static void listener(ClientPlayerBlockPlacementPacket packet, Player player) {
        // Appelle une méthode
        final PlayerHand hand = packet.hand();
        // Appelle une méthode
        final BlockFace blockFace = packet.blockFace();
        // Appelle une méthode
        Point blockPosition = packet.blockPosition();

        // Appelle une méthode
        final Instance instance = player.getInstance();
        // Embranchement : vérifie une condition
        if (instance == null)
            // Renvoie une valeur à l'appelant
            return;

        // Prevent outdated/modified client data
        // Appelle une méthode
        final Chunk interactedChunk = instance.getChunkAt(blockPosition);
        // Embranchement : vérifie une condition
        if (!ChunkUtils.isLoaded(interactedChunk)) {
            // Client tried to place a block in an unloaded chunk, ignore the request
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final ItemStack usedItem = player.getItemInHand(hand);
        // Appelle une méthode
        final Block interactedBlock = instance.getBlock(blockPosition);

        // Appelle une méthode
        final Point cursorPosition = new Vec(packet.cursorPositionX(), packet.cursorPositionY(), packet.cursorPositionZ());

        // Interact at block
        // FIXME: onUseOnBlock
        // Appelle une méthode
        PlayerBlockInteractEvent playerBlockInteractEvent = new PlayerBlockInteractEvent(player, hand, interactedBlock, new BlockVec(blockPosition), cursorPosition, blockFace);
        // Appelle une méthode
        EventDispatcher.call(playerBlockInteractEvent);
        // Appelle une méthode
        boolean blockUse = playerBlockInteractEvent.isBlockingItemUse();
        // Embranchement : vérifie une condition
        if (!playerBlockInteractEvent.isCancelled()) {
            // Appelle une méthode
            final var handler = interactedBlock.handler();
            // Embranchement : vérifie une condition
            if (handler != null) {
                // Appelle une méthode
                blockUse |= !handler.onInteract(new BlockHandler.Interaction(interactedBlock, instance, blockFace, blockPosition, cursorPosition, player, hand));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (blockUse) {
            // If the usage was blocked then the world is already up-to-date (from the prior handlers),
            // So ack the change with the current world state.
            // Appelle une méthode
            player.sendPacket(new AcknowledgeBlockChangePacket(packet.sequence()));
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final Material useMaterial = usedItem.material();
        // Embranchement : vérifie une condition
        if (!useMaterial.isBlock()) {
            // Player didn't try to place a block but interacted with one
            // Appelle une méthode
            PlayerUseItemOnBlockEvent event = new PlayerUseItemOnBlockEvent(player, hand, usedItem, blockPosition, cursorPosition, blockFace);
            // Appelle une méthode
            EventDispatcher.call(event);
            // Ack the block change. This is required to reset the client prediction to the server state.
            // Appelle une méthode
            player.sendPacket(new AcknowledgeBlockChangePacket(packet.sequence()));
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Verify if the player can place the block
        // Affecte une valeur
        boolean canPlaceBlock = true;
        // Check if the player is allowed to place blocks based on their game mode
        // Embranchement : vérifie une condition
        if (player.getGameMode() == GameMode.SPECTATOR) {
            // Affecte une valeur
            canPlaceBlock = false; // Spectators can't place blocks
        // Embranchement : vérifie une condition
        } else if (player.getGameMode() == GameMode.ADVENTURE) {
            //Check if the block can be placed on the block
            // Appelle une méthode
            BlockPredicates placePredicate = usedItem.get(DataComponents.CAN_PLACE_ON, BlockPredicates.NEVER);
            // Appelle une méthode
            canPlaceBlock = placePredicate.test(interactedBlock);
        // Fin d'un bloc/d'une expression
        }


        // Get the newly placed block position
        //todo it feels like it should be possible to have better replacement rules than this, feels pretty scuffed.
        // Affecte une valeur
        Point placementPosition = blockPosition;
        // Appelle une méthode
        var interactedPlacementRule = BLOCK_MANAGER.getBlockPlacementRule(interactedBlock);
        // Embranchement : vérifie une condition
        if (!interactedBlock.isAir() && (interactedPlacementRule == null || !interactedPlacementRule.isSelfReplaceable(
                // Crée un nouvel objet
                new BlockPlacementRule.Replacement(interactedBlock, blockFace, cursorPosition, false, useMaterial)))) {
            // If the block is not replaceable, try to place next to it.
            // Appelle une méthode
            placementPosition = blockPosition.relative(blockFace);

            // Appelle une méthode
            var placementBlock = instance.getBlock(placementPosition);
            // Appelle une méthode
            var placementRule = BLOCK_MANAGER.getBlockPlacementRule(placementBlock);
            // Embranchement : vérifie une condition
            if (!placementBlock.registry().isReplaceable() && !(placementRule != null && placementRule.isSelfReplaceable(
                    // Crée un nouvel objet
                    new BlockPlacementRule.Replacement(placementBlock, blockFace, cursorPosition, true, useMaterial)))) {
                // If the block is still not replaceable, cancel the placement
                // Affecte une valeur
                canPlaceBlock = false;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final DimensionType instanceDim = instance.getCachedDimensionType();
        // Embranchement : vérifie une condition
        if (placementPosition.y() >= instanceDim.maxY() || placementPosition.y() < instanceDim.minY()) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Ensure that the final placement position is inside the world border.
        // Embranchement : vérifie une condition
        if (!instance.getWorldBorder().inBounds(placementPosition)) {
            // Affecte une valeur
            canPlaceBlock = false;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (!canPlaceBlock) {
            // Send a block change with the real block in the instance to keep the client in sync,
            // using refreshChunk results in the client not being in sync
            // after rapid invalid block placements
            // Appelle une méthode
            final Block block = instance.getBlock(placementPosition);
            // Appelle une méthode
            player.sendPacket(new BlockChangePacket(placementPosition, block));
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final Chunk chunk = instance.getChunkAt(placementPosition);
        // Instruction de code
        Check.stateCondition(!ChunkUtils.isLoaded(chunk),
                // Instruction de code
                "A player tried to place a block in the border of a loaded chunk {0}", placementPosition);
        // Embranchement : vérifie une condition
        if (chunk.isReadOnly()) {
            // Appelle une méthode
            refresh(player, chunk);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final ItemBlockState blockState = usedItem.get(DataComponents.BLOCK_STATE, ItemBlockState.EMPTY);
        // Appelle une méthode
        final Block placedBlock = blockState.apply(useMaterial.block());

        // Appelle une méthode
        Entity collisionEntity = CollisionUtils.canPlaceBlockAt(instance, placementPosition, placedBlock);
        // Embranchement : vérifie une condition
        if (collisionEntity != null) {
            // If a player is trying to place a block on themselves, the client will send a block change but will not set the block on the client
            // For this reason, the block doesn't need to be updated for the client

            // Client also doesn't predict placement of blocks on entities, but we need to refresh for cases where bounding boxes on the server don't match the client
            // Embranchement : vérifie une condition
            if (collisionEntity != player)
                // Appelle une méthode
                refresh(player, chunk);

            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // BlockPlaceEvent check
        // Appelle une méthode
        PlayerBlockPlaceEvent playerBlockPlaceEvent = new PlayerBlockPlaceEvent(player, placedBlock, blockFace, new BlockVec(placementPosition), cursorPosition, packet.hand());
        // Appelle une méthode
        playerBlockPlaceEvent.consumeBlock(player.getGameMode() != GameMode.CREATIVE);
        // Appelle une méthode
        playerBlockPlaceEvent.setDoBlockUpdates(blockState.equals(useMaterial.prototype().get(DataComponents.BLOCK_STATE, ItemBlockState.EMPTY)));
        // Appelle une méthode
        EventDispatcher.call(playerBlockPlaceEvent);
        // Embranchement : vérifie une condition
        if (playerBlockPlaceEvent.isCancelled()) {
            // Appelle une méthode
            refresh(player, chunk);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Place the block
        // Appelle une méthode
        Block resultBlock = playerBlockPlaceEvent.getBlock();
        // Instruction de code
        instance.placeBlock(new BlockHandler.PlayerPlacement(resultBlock, instance.getBlock(placementPosition), instance, placementPosition, player, hand, blockFace,
                // Appelle une méthode
                packet.cursorPositionX(), packet.cursorPositionY(), packet.cursorPositionZ()), playerBlockPlaceEvent.shouldDoBlockUpdates());
        // Appelle une méthode
        player.sendPacket(new AcknowledgeBlockChangePacket(packet.sequence()));
        // Block consuming
        // Embranchement : vérifie une condition
        if (playerBlockPlaceEvent.doesConsumeBlock()) {
            // Consume the block in the player's hand
            // Appelle une méthode
            final ItemStack newUsedItem = usedItem.consume(1);
            // Appelle une méthode
            player.setItemInHand(hand, newUsedItem);
        // Branche alternative de la condition
        } else {
            // Prevent invisible item on client
            // Appelle une méthode
            player.getInventory().update();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void refresh(Player player, Chunk chunk) {
        // Appelle une méthode
        player.getInventory().update();
        // Appelle une méthode
        chunk.sendChunk(player);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
