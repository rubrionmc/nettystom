// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.entity.metadata.LivingEntityMeta;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.item.PlayerCancelItemUseEvent;
// Import of a required class
import net.minestom.server.event.player.*;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.component.BlockPredicates;
// Import of a required class
import net.minestom.server.item.component.Tool;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPlayerActionPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.AcknowledgeBlockChangePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.BlockEntityDataPacket;
// Import of a required class
import net.minestom.server.utils.block.BlockBreakCalculation;
// Import of a required class
import net.minestom.server.utils.block.BlockUtils;

// Type declaration (class/interface/enum/record)
public final class PlayerActionListener {

    // Start of a method/block
    public static void playerActionListener(ClientPlayerActionPacket packet, Player player) {
        // Calls a method
        final ClientPlayerActionPacket.Status status = packet.status();
        // Calls a method
        final Point blockPosition = packet.blockPosition();
        // Calls a method
        final Instance instance = player.getInstance();
        // Branch: checks a condition
        if (instance == null) return;

        // Assigns a value
        DiggingResult diggingResult = null;
        // Branch: checks a condition
        if (status == ClientPlayerActionPacket.Status.STARTED_DIGGING) {
            // Branch: checks a condition
            if (!instance.isChunkLoaded(blockPosition)) return;
            // Calls a method
            diggingResult = startDigging(player, instance, blockPosition, packet.blockFace());
        // Branch: checks a condition
        } else if (status == ClientPlayerActionPacket.Status.CANCELLED_DIGGING) {
            // Branch: checks a condition
            if (!instance.isChunkLoaded(blockPosition)) return;
            // Calls a method
            diggingResult = cancelDigging(player, instance, blockPosition);
        // Branch: checks a condition
        } else if (status == ClientPlayerActionPacket.Status.FINISHED_DIGGING) {
            // Branch: checks a condition
            if (!instance.isChunkLoaded(blockPosition)) return;
            // Calls a method
            diggingResult = finishDigging(player, instance, blockPosition, packet.blockFace());
        // Branch: checks a condition
        } else if (status == ClientPlayerActionPacket.Status.DROP_ITEM_STACK) {
            // Calls a method
            dropStack(player);
        // Branch: checks a condition
        } else if (status == ClientPlayerActionPacket.Status.DROP_ITEM) {
            // Calls a method
            dropSingle(player);
        // Branch: checks a condition
        } else if (status == ClientPlayerActionPacket.Status.UPDATE_ITEM_STATE) {
            // Calls a method
            updateItemState(player);
        // Branch: checks a condition
        } else if (status == ClientPlayerActionPacket.Status.SWAP_ITEM_HAND) {
            // Calls a method
            swapItemHand(player);
        // Branch: checks a condition
        } else if (status == ClientPlayerActionPacket.Status.STAB) {
            // Calls a method
            stab(player);
        // End of a block/expression
        }
        // Acknowledge start/cancel/finish digging status
        // Branch: checks a condition
        if (diggingResult != null) {
            // Calls a method
            player.sendPacket(new AcknowledgeBlockChangePacket(packet.sequence()));
            // Branch: checks a condition
            if (!diggingResult.success()) {
                // Refresh block on player screen in case it had special data (like a sign)
                // Calls a method
                var blockEntityType = diggingResult.block().registry().blockEntityType();
                // Branch: checks a condition
                if (blockEntityType != null) {
                    // Calls a method
                    final CompoundBinaryTag data = BlockUtils.extractClientNbt(diggingResult.block());
                    // Calls a method
                    player.sendPacketToViewersAndSelf(new BlockEntityDataPacket(blockPosition, blockEntityType, data));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static DiggingResult startDigging(Player player, Instance instance, Point blockPosition, BlockFace blockFace) {
        // Calls a method
        final Block block = instance.getBlock(blockPosition);

        // Prevent spectators and check players in adventure mode
        // Branch: checks a condition
        if (shouldPreventBreaking(player, block)) {
            // Returns a value to the caller
            return new DiggingResult(block, false);
        // End of a block/expression
        }

        // Calls a method
        final int breakTicks = BlockBreakCalculation.breakTicks(block, player);
        // Assigns a value
        final boolean instantBreak = breakTicks == 0;
        // Branch: checks a condition
        if (!instantBreak) {
            // Calls a method
            PlayerStartDiggingEvent playerStartDiggingEvent = new PlayerStartDiggingEvent(player, instance, block, blockPosition.asBlockVec(), blockFace);
            // Calls a method
            EventDispatcher.call(playerStartDiggingEvent);
            // Returns a value to the caller
            return new DiggingResult(block, !playerStartDiggingEvent.isCancelled());
        // End of a block/expression
        }
        // Client only sends a single STARTED_DIGGING when insta-break is enabled
        // Returns a value to the caller
        return breakBlock(instance, player, blockPosition, block, blockFace);
    // End of a block/expression
    }

    // Start of a method/block
    private static DiggingResult cancelDigging(Player player, Instance instance, Point blockPosition) {
        // Calls a method
        final Block block = instance.getBlock(blockPosition);

        // Calls a method
        PlayerCancelDiggingEvent playerCancelDiggingEvent = new PlayerCancelDiggingEvent(player, instance, block, blockPosition.asBlockVec());
        // Calls a method
        EventDispatcher.call(playerCancelDiggingEvent);
        // Returns a value to the caller
        return new DiggingResult(block, true);
    // End of a block/expression
    }

    // Start of a method/block
    private static DiggingResult finishDigging(Player player, Instance instance, Point blockPosition, BlockFace blockFace) {
        // Calls a method
        final Block block = instance.getBlock(blockPosition);

        // Branch: checks a condition
        if (shouldPreventBreaking(player, block)) {
            // Returns a value to the caller
            return new DiggingResult(block, false);
        // End of a block/expression
        }

        // Calls a method
        final int breakTicks = BlockBreakCalculation.breakTicks(block, player);
        // Realistically shouldn't happen, but a hacked client can send any packet, also illegal ones
        // If the block is unbreakable, prevent a hacked client from breaking it!
        // Branch: checks a condition
        if (breakTicks == BlockBreakCalculation.UNBREAKABLE) {
            // Calls a method
            PlayerCancelDiggingEvent playerCancelDiggingEvent = new PlayerCancelDiggingEvent(player, instance, block, blockPosition.asBlockVec());
            // Calls a method
            EventDispatcher.call(playerCancelDiggingEvent);
            // Returns a value to the caller
            return new DiggingResult(block, false);
        // End of a block/expression
        }
        // TODO maybe add a check if the player has spent enough time mining the block.
        //   a hacked client could send START_DIGGING and FINISH_DIGGING to instamine any block

        // Calls a method
        PlayerFinishDiggingEvent playerFinishDiggingEvent = new PlayerFinishDiggingEvent(player, instance, block, blockPosition.asBlockVec());
        // Calls a method
        EventDispatcher.call(playerFinishDiggingEvent);

        // Returns a value to the caller
        return breakBlock(instance, player, blockPosition, playerFinishDiggingEvent.getBlock(), blockFace);
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean shouldPreventBreaking(Player player, Block block) {
        // Calls a method
        final ItemStack itemInMainHand = player.getItemInMainHand();

        // Returns a value to the caller
        return switch (player.getGameMode()) {
            // Spectators can't break blocks
            // Multiple branching (switch/case)
            case SPECTATOR -> true;
            // Check if the currently held item can break the block
            // Multiple branching (switch/case)
            case ADVENTURE -> !itemInMainHand
                    // Code statement
                    .get(DataComponents.CAN_BREAK, BlockPredicates.NEVER)
                    // Calls a method
                    .test(block);
            // Certain tools (swords, tridents, maces) can't break blocks in creative
            // Multiple branching (switch/case)
            case CREATIVE -> {
                // Calls a method
                final Tool tool = itemInMainHand.get(DataComponents.TOOL);
                // Calls a method
                yield tool != null && !tool.canDestroyBlocksInCreative();
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            default -> false;
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static void dropStack(Player player) {
        // Calls a method
        final ItemStack droppedItemStack = player.getItemInMainHand();
        // Calls a method
        dropItem(player, droppedItemStack, ItemStack.AIR);
    // End of a block/expression
    }

    // Start of a method/block
    private static void dropSingle(Player player) {
        // Calls a method
        final ItemStack handItem = player.getItemInMainHand();
        // Calls a method
        final int handAmount = handItem.amount();
        // Branch: checks a condition
        if (handAmount <= 1) {
            // Drop the whole item without copy
            // Calls a method
            dropItem(player, handItem, ItemStack.AIR);
        // Alternative branch of the condition
        } else {
            // Drop a single item
            // Code statement
            dropItem(player,
                    // Code statement
                    handItem.withAmount(1), // Single dropped item
                    // Code statement
                    handItem.withAmount(handAmount - 1)); // Updated hand
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void updateItemState(Player player) {
        // Calls a method
        LivingEntityMeta meta = player.getLivingEntityMeta();
        // Branch: checks a condition
        if (meta == null || !meta.isHandActive()) return;
        // Calls a method
        final PlayerHand hand = meta.getActiveHand();

        // Calls a method
        PlayerCancelItemUseEvent cancelUseEvent = new PlayerCancelItemUseEvent(player, hand, player.getItemInHand(hand), player.getCurrentItemUseTime());
        // Calls a method
        EventDispatcher.call(cancelUseEvent);

        // Reset server state
        // Assigns a value
        final boolean isOffHand = hand == PlayerHand.OFF;
        // Calls a method
        player.refreshActiveHand(false, isOffHand, cancelUseEvent.isRiptideSpinAttack());
        // Calls a method
        player.clearItemUse();
    // End of a block/expression
    }

    // Start of a method/block
    private static void swapItemHand(Player player) {
        // Calls a method
        final ItemStack mainHand = player.getItemInMainHand();
        // Calls a method
        final ItemStack offHand = player.getItemInOffHand();
        // Calls a method
        PlayerSwapItemEvent swapItemEvent = new PlayerSwapItemEvent(player, offHand, mainHand);
        // Start of a method/block
        EventDispatcher.callCancellable(swapItemEvent, () -> {
            // Calls a method
            player.setItemInMainHand(swapItemEvent.getMainHandItem());
            // Calls a method
            player.setItemInOffHand(swapItemEvent.getOffHandItem());
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Code statement
    private static DiggingResult breakBlock(Instance instance,
                                            // Code statement
                                            Player player,
                                            // Start of a method/block
                                            Point blockPosition, Block previousBlock, BlockFace blockFace) {
        // Unverified block break, client is fully responsible
        // Calls a method
        final boolean success = instance.breakBlock(player, blockPosition, blockFace);
        // Calls a method
        final Block updatedBlock = instance.getBlock(blockPosition);
        // Branch: checks a condition
        if (!success) {
            // Branch: checks a condition
            if (previousBlock.isSolid()) {
                // Calls a method
                final Pos playerPosition = player.getPosition();
                // Teleport the player back if he broke a solid block just below him
                // Branch: checks a condition
                if (playerPosition.sub(0, 1, 0).samePoint(blockPosition)) {
                    // Calls a method
                    player.teleport(playerPosition);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return new DiggingResult(updatedBlock, success);
    // End of a block/expression
    }

    // Code statement
    private static void dropItem(Player player,
                                 // Start of a method/block
                                 ItemStack droppedItem, ItemStack handItem) {
        // Branch: checks a condition
        if (player.dropItem(droppedItem)) {
            // Calls a method
            player.setItemInMainHand(handItem);
        // Alternative branch of the condition
        } else {
            // Calls a method
            player.getInventory().update();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void stab(Player player) {
        // Calls a method
        final ItemStack itemInMainHand = player.getItemInMainHand();
        // Branch: checks a condition
        if (!itemInMainHand.has(DataComponents.PIERCING_WEAPON))
            // Returns a value to the caller
            return;
        // Calls a method
        EventDispatcher.call(new PlayerStabEvent(player));
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record DiggingResult(Block block, boolean success) {
    // End of a block/expression
    }
// End of a block/expression
}
