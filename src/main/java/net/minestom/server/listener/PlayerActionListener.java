// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.LivingEntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.item.PlayerCancelItemUseEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.*;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.component.BlockPredicates;
// Import d'une classe nécessaire
import net.minestom.server.item.component.Tool;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPlayerActionPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.AcknowledgeBlockChangePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.BlockEntityDataPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockBreakCalculation;
// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockUtils;

// Déclaration de type (classe/interface/enum/record)
public final class PlayerActionListener {

    // Début d'une méthode/d'un bloc
    public static void playerActionListener(ClientPlayerActionPacket packet, Player player) {
        // Appelle une méthode
        final ClientPlayerActionPacket.Status status = packet.status();
        // Appelle une méthode
        final Point blockPosition = packet.blockPosition();
        // Appelle une méthode
        final Instance instance = player.getInstance();
        // Embranchement : vérifie une condition
        if (instance == null) return;

        // Affecte une valeur
        DiggingResult diggingResult = null;
        // Embranchement : vérifie une condition
        if (status == ClientPlayerActionPacket.Status.STARTED_DIGGING) {
            // Embranchement : vérifie une condition
            if (!instance.isChunkLoaded(blockPosition)) return;
            // Appelle une méthode
            diggingResult = startDigging(player, instance, blockPosition, packet.blockFace());
        // Embranchement : vérifie une condition
        } else if (status == ClientPlayerActionPacket.Status.CANCELLED_DIGGING) {
            // Embranchement : vérifie une condition
            if (!instance.isChunkLoaded(blockPosition)) return;
            // Appelle une méthode
            diggingResult = cancelDigging(player, instance, blockPosition);
        // Embranchement : vérifie une condition
        } else if (status == ClientPlayerActionPacket.Status.FINISHED_DIGGING) {
            // Embranchement : vérifie une condition
            if (!instance.isChunkLoaded(blockPosition)) return;
            // Appelle une méthode
            diggingResult = finishDigging(player, instance, blockPosition, packet.blockFace());
        // Embranchement : vérifie une condition
        } else if (status == ClientPlayerActionPacket.Status.DROP_ITEM_STACK) {
            // Appelle une méthode
            dropStack(player);
        // Embranchement : vérifie une condition
        } else if (status == ClientPlayerActionPacket.Status.DROP_ITEM) {
            // Appelle une méthode
            dropSingle(player);
        // Embranchement : vérifie une condition
        } else if (status == ClientPlayerActionPacket.Status.UPDATE_ITEM_STATE) {
            // Appelle une méthode
            updateItemState(player);
        // Embranchement : vérifie une condition
        } else if (status == ClientPlayerActionPacket.Status.SWAP_ITEM_HAND) {
            // Appelle une méthode
            swapItemHand(player);
        // Embranchement : vérifie une condition
        } else if (status == ClientPlayerActionPacket.Status.STAB) {
            // Appelle une méthode
            stab(player);
        // Fin d'un bloc/d'une expression
        }
        // Acknowledge start/cancel/finish digging status
        // Embranchement : vérifie une condition
        if (diggingResult != null) {
            // Appelle une méthode
            player.sendPacket(new AcknowledgeBlockChangePacket(packet.sequence()));
            // Embranchement : vérifie une condition
            if (!diggingResult.success()) {
                // Refresh block on player screen in case it had special data (like a sign)
                // Appelle une méthode
                var blockEntityType = diggingResult.block().registry().blockEntityType();
                // Embranchement : vérifie une condition
                if (blockEntityType != null) {
                    // Appelle une méthode
                    final CompoundBinaryTag data = BlockUtils.extractClientNbt(diggingResult.block());
                    // Appelle une méthode
                    player.sendPacketToViewersAndSelf(new BlockEntityDataPacket(blockPosition, blockEntityType, data));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static DiggingResult startDigging(Player player, Instance instance, Point blockPosition, BlockFace blockFace) {
        // Appelle une méthode
        final Block block = instance.getBlock(blockPosition);

        // Prevent spectators and check players in adventure mode
        // Embranchement : vérifie une condition
        if (shouldPreventBreaking(player, block)) {
            // Renvoie une valeur à l'appelant
            return new DiggingResult(block, false);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final int breakTicks = BlockBreakCalculation.breakTicks(block, player);
        // Affecte une valeur
        final boolean instantBreak = breakTicks == 0;
        // Embranchement : vérifie une condition
        if (!instantBreak) {
            // Appelle une méthode
            PlayerStartDiggingEvent playerStartDiggingEvent = new PlayerStartDiggingEvent(player, instance, block, blockPosition.asBlockVec(), blockFace);
            // Appelle une méthode
            EventDispatcher.call(playerStartDiggingEvent);
            // Renvoie une valeur à l'appelant
            return new DiggingResult(block, !playerStartDiggingEvent.isCancelled());
        // Fin d'un bloc/d'une expression
        }
        // Client only sends a single STARTED_DIGGING when insta-break is enabled
        // Renvoie une valeur à l'appelant
        return breakBlock(instance, player, blockPosition, block, blockFace);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static DiggingResult cancelDigging(Player player, Instance instance, Point blockPosition) {
        // Appelle une méthode
        final Block block = instance.getBlock(blockPosition);

        // Appelle une méthode
        PlayerCancelDiggingEvent playerCancelDiggingEvent = new PlayerCancelDiggingEvent(player, instance, block, blockPosition.asBlockVec());
        // Appelle une méthode
        EventDispatcher.call(playerCancelDiggingEvent);
        // Renvoie une valeur à l'appelant
        return new DiggingResult(block, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static DiggingResult finishDigging(Player player, Instance instance, Point blockPosition, BlockFace blockFace) {
        // Appelle une méthode
        final Block block = instance.getBlock(blockPosition);

        // Embranchement : vérifie une condition
        if (shouldPreventBreaking(player, block)) {
            // Renvoie une valeur à l'appelant
            return new DiggingResult(block, false);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final int breakTicks = BlockBreakCalculation.breakTicks(block, player);
        // Realistically shouldn't happen, but a hacked client can send any packet, also illegal ones
        // If the block is unbreakable, prevent a hacked client from breaking it!
        // Embranchement : vérifie une condition
        if (breakTicks == BlockBreakCalculation.UNBREAKABLE) {
            // Appelle une méthode
            PlayerCancelDiggingEvent playerCancelDiggingEvent = new PlayerCancelDiggingEvent(player, instance, block, blockPosition.asBlockVec());
            // Appelle une méthode
            EventDispatcher.call(playerCancelDiggingEvent);
            // Renvoie une valeur à l'appelant
            return new DiggingResult(block, false);
        // Fin d'un bloc/d'une expression
        }
        // TODO maybe add a check if the player has spent enough time mining the block.
        //   a hacked client could send START_DIGGING and FINISH_DIGGING to instamine any block

        // Appelle une méthode
        PlayerFinishDiggingEvent playerFinishDiggingEvent = new PlayerFinishDiggingEvent(player, instance, block, blockPosition.asBlockVec());
        // Appelle une méthode
        EventDispatcher.call(playerFinishDiggingEvent);

        // Renvoie une valeur à l'appelant
        return breakBlock(instance, player, blockPosition, playerFinishDiggingEvent.getBlock(), blockFace);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean shouldPreventBreaking(Player player, Block block) {
        // Appelle une méthode
        final ItemStack itemInMainHand = player.getItemInMainHand();

        // Renvoie une valeur à l'appelant
        return switch (player.getGameMode()) {
            // Spectators can't break blocks
            // Embranchement multiple (switch/case)
            case SPECTATOR -> true;
            // Check if the currently held item can break the block
            // Embranchement multiple (switch/case)
            case ADVENTURE -> !itemInMainHand
                    // Instruction de code
                    .get(DataComponents.CAN_BREAK, BlockPredicates.NEVER)
                    // Appelle une méthode
                    .test(block);
            // Certain tools (swords, tridents, maces) can't break blocks in creative
            // Embranchement multiple (switch/case)
            case CREATIVE -> {
                // Appelle une méthode
                final Tool tool = itemInMainHand.get(DataComponents.TOOL);
                // Appelle une méthode
                yield tool != null && !tool.canDestroyBlocksInCreative();
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            default -> false;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void dropStack(Player player) {
        // Appelle une méthode
        final ItemStack droppedItemStack = player.getItemInMainHand();
        // Appelle une méthode
        dropItem(player, droppedItemStack, ItemStack.AIR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void dropSingle(Player player) {
        // Appelle une méthode
        final ItemStack handItem = player.getItemInMainHand();
        // Appelle une méthode
        final int handAmount = handItem.amount();
        // Embranchement : vérifie une condition
        if (handAmount <= 1) {
            // Drop the whole item without copy
            // Appelle une méthode
            dropItem(player, handItem, ItemStack.AIR);
        // Branche alternative de la condition
        } else {
            // Drop a single item
            // Instruction de code
            dropItem(player,
                    // Instruction de code
                    handItem.withAmount(1), // Single dropped item
                    // Instruction de code
                    handItem.withAmount(handAmount - 1)); // Updated hand
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void updateItemState(Player player) {
        // Appelle une méthode
        LivingEntityMeta meta = player.getLivingEntityMeta();
        // Embranchement : vérifie une condition
        if (meta == null || !meta.isHandActive()) return;
        // Appelle une méthode
        final PlayerHand hand = meta.getActiveHand();

        // Appelle une méthode
        PlayerCancelItemUseEvent cancelUseEvent = new PlayerCancelItemUseEvent(player, hand, player.getItemInHand(hand), player.getCurrentItemUseTime());
        // Appelle une méthode
        EventDispatcher.call(cancelUseEvent);

        // Reset server state
        // Affecte une valeur
        final boolean isOffHand = hand == PlayerHand.OFF;
        // Appelle une méthode
        player.refreshActiveHand(false, isOffHand, cancelUseEvent.isRiptideSpinAttack());
        // Appelle une méthode
        player.clearItemUse();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void swapItemHand(Player player) {
        // Appelle une méthode
        final ItemStack mainHand = player.getItemInMainHand();
        // Appelle une méthode
        final ItemStack offHand = player.getItemInOffHand();
        // Appelle une méthode
        PlayerSwapItemEvent swapItemEvent = new PlayerSwapItemEvent(player, offHand, mainHand);
        // Début d'une méthode/d'un bloc
        EventDispatcher.callCancellable(swapItemEvent, () -> {
            // Appelle une méthode
            player.setItemInMainHand(swapItemEvent.getMainHandItem());
            // Appelle une méthode
            player.setItemInOffHand(swapItemEvent.getOffHandItem());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static DiggingResult breakBlock(Instance instance,
                                            // Instruction de code
                                            Player player,
                                            // Début d'une méthode/d'un bloc
                                            Point blockPosition, Block previousBlock, BlockFace blockFace) {
        // Unverified block break, client is fully responsible
        // Appelle une méthode
        final boolean success = instance.breakBlock(player, blockPosition, blockFace);
        // Appelle une méthode
        final Block updatedBlock = instance.getBlock(blockPosition);
        // Embranchement : vérifie une condition
        if (!success) {
            // Embranchement : vérifie une condition
            if (previousBlock.isSolid()) {
                // Appelle une méthode
                final Pos playerPosition = player.getPosition();
                // Teleport the player back if he broke a solid block just below him
                // Embranchement : vérifie une condition
                if (playerPosition.sub(0, 1, 0).samePoint(blockPosition)) {
                    // Appelle une méthode
                    player.teleport(playerPosition);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new DiggingResult(updatedBlock, success);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static void dropItem(Player player,
                                 // Début d'une méthode/d'un bloc
                                 ItemStack droppedItem, ItemStack handItem) {
        // Embranchement : vérifie une condition
        if (player.dropItem(droppedItem)) {
            // Appelle une méthode
            player.setItemInMainHand(handItem);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            player.getInventory().update();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void stab(Player player) {
        // Appelle une méthode
        final ItemStack itemInMainHand = player.getItemInMainHand();
        // Embranchement : vérifie une condition
        if (!itemInMainHand.has(DataComponents.PIERCING_WEAPON))
            // Renvoie une valeur à l'appelant
            return;
        // Appelle une méthode
        EventDispatcher.call(new PlayerStabEvent(player));
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record DiggingResult(Block block, boolean success) {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
