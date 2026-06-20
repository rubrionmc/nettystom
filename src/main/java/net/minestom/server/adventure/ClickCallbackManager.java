// Package declaration for this file
package net.minestom.server.adventure;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.UUID;

// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.IntArrayBinaryTag;
// Import of a required class
import net.kyori.adventure.text.event.ClickCallback;
// Import of a required class
import net.kyori.adventure.text.event.ClickEvent;
// Import of a required class
import net.minestom.server.Tickable;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientCustomClickActionPacket;
// Import of a required class
import net.minestom.server.utils.UUIDUtils;

/**
 * Manager for Adventure click callbacks.
 */
// Type declaration (class/interface/enum/record)
public final class ClickCallbackManager implements Tickable {
    // Calls a method
    private static final Key KEY = Key.key("minestom", "click_callback");

    // Calls a method
    private final Map<UUID, ClickCallback<Audience>> permanent = new ConcurrentHashMap<>(0);
    // Calls a method
    private final Map<UUID, CallbackData> temporary = new ConcurrentHashMap<>(0);

    // Type declaration (class/interface/enum/record)
    private record CallbackData(ClickCallback<Audience> callback, long expiry, AtomicInteger uses) {
        // Start of a method/block
        private CallbackData {
            // Calls a method
            Objects.requireNonNull(callback, "callback");
            // Calls a method
            Objects.requireNonNull(uses, "uses");
        // End of a block/expression
        }

        // Start of a method/block
        boolean isExpired(final long time) {
            // Returns a value to the caller
            return this.uses.get() <= 0 || time >= this.expiry;
        // End of a block/expression
        }

        // Start of a method/block
        void consume(final Audience audience) {
            // Calls a method
            final int remaining = this.uses.getAndUpdate(current -> current > 0 ? current - 1 : current);
            // Branch: checks a condition
            if (remaining > 0 && this.expiry > System.nanoTime()) {
                // Access to the current/parent object
                this.callback.accept(audience);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(final long time) {
        // Branch: checks a condition
        if (this.temporary.isEmpty()) return;
        // Access to the current/parent object
        this.temporary.values().removeIf(data -> data.isExpired(time));
    // End of a block/expression
    }

    /**
     * Consumes a custom click event.
     *
     * @param player the player who performed the click
     * @param packet the packet
     */
    // Start of a method/block
    public void consumeCustomClick(final Player player, final ClientCustomClickActionPacket packet) {
        // Calls a method
        Objects.requireNonNull(player, "player");
        // Calls a method
        Objects.requireNonNull(packet, "packet");
        // Branch: checks a condition
        if (!packet.key().equals(KEY)) return;

        // Branch: checks a condition
        if (packet.payload() instanceof final IntArrayBinaryTag tag) {
            // Code statement
            final UUID uuid;
            // Exception handling
            try {
                // Calls a method
                uuid = UUIDUtils.fromNbt(tag);
            // Start of a method/block
            } catch (final IndexOutOfBoundsException _) {
                // Returns a value to the caller
                return;
            // End of a block/expression
            }

            // Calls a method
            final ClickCallback<Audience> data = this.permanent.get(uuid);
            // Branch: checks a condition
            if (data != null) {
                // Calls a method
                data.accept(player);
            // Alternative branch of the condition
            } else {
                // Calls a method
                final CallbackData temp = this.temporary.get(uuid);
                // Branch: checks a condition
                if (temp != null) {
                    // Calls a method
                    temp.consume(player);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Creates a click event from the given callback and options.
     *
     * @param callback the callback
     * @param options the options
     * @return the click event
     */
    // Start of a method/block
    public ClickEvent<ClickEvent.Payload.Custom> createClickEvent(final ClickCallback<Audience> callback, final ClickCallback.Options options) {
        // Calls a method
        Objects.requireNonNull(callback, "callback");
        // Calls a method
        Objects.requireNonNull(options, "options");
        // Calls a method
        final UUID uuid = UUID.randomUUID();
        // Calls a method
        final int uses = options.uses();

        // Code statement
        long expiry;
        // Exception handling
        try {
            // Calls a method
            expiry = System.nanoTime() + options.lifetime().toNanos();
        // Start of a method/block
        } catch (final ArithmeticException _) {
            // Assigns a value
            expiry = Long.MAX_VALUE;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (expiry == Long.MAX_VALUE && uses == ClickCallback.UNLIMITED_USES) {
            // Access to the current/parent object
            this.permanent.put(uuid, callback);
        // Branch: checks a condition
        } else if (uses > 0 && expiry > System.nanoTime()) {
            // Access to the current/parent object
            this.temporary.put(uuid, new CallbackData(callback, expiry, new AtomicInteger(uses)));
        // End of a block/expression
        }

        // Returns a value to the caller
        return ClickEvent.custom(KEY, new BinaryTagHolderImpl(UUIDUtils.toNbt(uuid)));
    // End of a block/expression
    }
// End of a block/expression
}
