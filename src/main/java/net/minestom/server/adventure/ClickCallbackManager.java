// Déclaration du paquet de ce fichier
package net.minestom.server.adventure;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.UUID;

// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicLong;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.IntArrayBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.ClickCallback;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.ClickEvent;
// Import d'une classe nécessaire
import net.minestom.server.Tickable;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientCustomClickActionPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.UUIDUtils;

/**
 * Manager for Adventure click callbacks.
 */
// Déclaration de type (classe/interface/enum/record)
public final class ClickCallbackManager implements Tickable {
    // Appelle une méthode
    private static final Key KEY = Key.key("minestom", "click_callback");

    // Affecte une valeur
    private final Map<UUID, ClickCallback<Audience>> permanent = new ConcurrentHashMap<>(0);
    // Affecte une valeur
    private final Map<UUID, CallbackData> temporary = new ConcurrentHashMap<>(0);

    // Déclaration de type (classe/interface/enum/record)
    private record CallbackData(ClickCallback<Audience> callback, long expiry, AtomicInteger uses) {
        // Début d'une méthode/d'un bloc
        private CallbackData {
            // Appelle une méthode
            Objects.requireNonNull(callback, "callback");
            // Appelle une méthode
            Objects.requireNonNull(uses, "uses");
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        boolean isExpired(final long time) {
            // Renvoie une valeur à l'appelant
            return this.uses.get() <= 0 || time >= this.expiry;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void consume(final Audience audience) {
            // Appelle une méthode
            final int remaining = this.uses.getAndUpdate(current -> current > 0 ? current - 1 : current);
            // Embranchement : vérifie une condition
            if (remaining > 0 && this.expiry > System.nanoTime()) {
                // Accès à l'objet courant/parent
                this.callback.accept(audience);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void tick(final long time) {
        // Embranchement : vérifie une condition
        if (this.temporary.isEmpty()) return;
        // Accès à l'objet courant/parent
        this.temporary.values().removeIf(data -> data.isExpired(time));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Consumes a custom click event.
     *
     * @param player the player who performed the click
     * @param packet the packet
     */
    // Début d'une méthode/d'un bloc
    public void consumeCustomClick(final Player player, final ClientCustomClickActionPacket packet) {
        // Appelle une méthode
        Objects.requireNonNull(player, "player");
        // Appelle une méthode
        Objects.requireNonNull(packet, "packet");
        // Embranchement : vérifie une condition
        if (!packet.key().equals(KEY)) return;

        // Embranchement : vérifie une condition
        if (packet.payload() instanceof final IntArrayBinaryTag tag) {
            // Instruction de code
            final UUID uuid;
            // Gestion des exceptions
            try {
                // Appelle une méthode
                uuid = UUIDUtils.fromNbt(tag);
            // Début d'une méthode/d'un bloc
            } catch (final IndexOutOfBoundsException _) {
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final ClickCallback<Audience> data = this.permanent.get(uuid);
            // Embranchement : vérifie une condition
            if (data != null) {
                // Appelle une méthode
                data.accept(player);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                final CallbackData temp = this.temporary.get(uuid);
                // Embranchement : vérifie une condition
                if (temp != null) {
                    // Appelle une méthode
                    temp.consume(player);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a click event from the given callback and options.
     *
     * @param callback the callback
     * @param options the options
     * @return the click event
     */
    // Début d'une méthode/d'un bloc
    public ClickEvent createClickEvent(final ClickCallback<Audience> callback, final ClickCallback.Options options) {
        // Appelle une méthode
        Objects.requireNonNull(callback, "callback");
        // Appelle une méthode
        Objects.requireNonNull(options, "options");
        // Appelle une méthode
        final UUID uuid = UUID.randomUUID();
        // Appelle une méthode
        final int uses = options.uses();

        // Instruction de code
        long expiry;
        // Gestion des exceptions
        try {
            // Appelle une méthode
            expiry = System.nanoTime() + options.lifetime().toNanos();
        // Début d'une méthode/d'un bloc
        } catch (final ArithmeticException _) {
            // Affecte une valeur
            expiry = Long.MAX_VALUE;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (expiry == Long.MAX_VALUE && uses == ClickCallback.UNLIMITED_USES) {
            // Accès à l'objet courant/parent
            this.permanent.put(uuid, callback);
        // Embranchement : vérifie une condition
        } else if (uses > 0 && expiry > System.nanoTime()) {
            // Accès à l'objet courant/parent
            this.temporary.put(uuid, new CallbackData(callback, expiry, new AtomicInteger(uses)));
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return ClickEvent.custom(KEY, new BinaryTagHolderImpl(UUIDUtils.toNbt(uuid)));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
