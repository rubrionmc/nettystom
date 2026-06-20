// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.instance.InstanceRegisterEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.instance.InstanceUnregisterEvent;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Used to register {@link Instance}.
 */
// Déclaration de type (classe/interface/enum/record)
public final class InstanceManager {

    // Instruction de code
    private final Registries registries;
    // Appelle une méthode
    private final Set<Instance> instances = new CopyOnWriteArraySet<>();

    // Début d'une méthode/d'un bloc
    public InstanceManager(Registries registries) {
        // Accès à l'objet courant/parent
        this.registries = registries;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Registers an {@link Instance} internally.
     * <p>
     * Note: not necessary if you created your instance using {@link #createInstanceContainer()} or {@link #createSharedInstance(InstanceContainer)}
     * but only if you instantiated your instance object manually
     *
     * @param instance the {@link Instance} to register
     */
    // Début d'une méthode/d'un bloc
    public void registerInstance(Instance instance) {
        // Instruction de code
        Check.stateCondition(instance instanceof SharedInstance,
                // Instruction de code
                "Please use InstanceManager#registerSharedInstance to register a shared instance");
        // Appelle une méthode
        UNSAFE_registerInstance(instance);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates and register an {@link InstanceContainer} with the specified {@link DimensionType}.
     *
     * @param dimensionType the {@link DimensionType} of the instance
     * @param loader        the chunk loader
     * @return the created {@link InstanceContainer}
     */
    // Début d'une méthode/d'un bloc
    public InstanceContainer createInstanceContainer(RegistryKey<DimensionType> dimensionType, @Nullable ChunkLoader loader) {
        // Appelle une méthode
        final InstanceContainer instanceContainer = new InstanceContainer(registries, UUID.randomUUID(), dimensionType, loader, dimensionType.key());
        // Appelle une méthode
        registerInstance(instanceContainer);
        // Renvoie une valeur à l'appelant
        return instanceContainer;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public InstanceContainer createInstanceContainer(RegistryKey<DimensionType> dimensionType) {
        // Renvoie une valeur à l'appelant
        return createInstanceContainer(dimensionType, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public InstanceContainer createInstanceContainer(@Nullable ChunkLoader loader) {
        // Renvoie une valeur à l'appelant
        return createInstanceContainer(DimensionType.OVERWORLD, loader);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates and register an {@link InstanceContainer}.
     *
     * @return the created {@link InstanceContainer}
     */
    // Début d'une méthode/d'un bloc
    public InstanceContainer createInstanceContainer() {
        // Renvoie une valeur à l'appelant
        return createInstanceContainer(DimensionType.OVERWORLD, null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Registers a {@link SharedInstance}.
     * <p>
     * WARNING: the {@link SharedInstance} needs to have an {@link InstanceContainer} assigned to it.
     *
     * @param sharedInstance the {@link SharedInstance} to register
     * @return the registered {@link SharedInstance}
     * @throws NullPointerException if {@code sharedInstance} doesn't have an {@link InstanceContainer} assigned to it
     */
    // Début d'une méthode/d'un bloc
    public SharedInstance registerSharedInstance(SharedInstance sharedInstance) {
        // Appelle une méthode
        final InstanceContainer instanceContainer = sharedInstance.getInstanceContainer();
        // Appelle une méthode
        Objects.requireNonNull(instanceContainer, "SharedInstance needs to have an InstanceContainer to be created!");

        // Appelle une méthode
        instanceContainer.addSharedInstance(sharedInstance);
        // Appelle une méthode
        UNSAFE_registerInstance(sharedInstance);
        // Renvoie une valeur à l'appelant
        return sharedInstance;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates and register a {@link SharedInstance}.
     *
     * @param instanceContainer the container assigned to the shared instance
     * @return the created {@link SharedInstance}
     * @throws IllegalStateException if {@code instanceContainer} is not registered
     */
    // Début d'une méthode/d'un bloc
    public SharedInstance createSharedInstance(InstanceContainer instanceContainer) {
        // Appelle une méthode
        Objects.requireNonNull(instanceContainer, "Instance container cannot be null when creating a SharedInstance!");
        // Appelle une méthode
        Check.stateCondition(!instanceContainer.isRegistered(), "The container needs to be register in the InstanceManager");

        // Appelle une méthode
        final SharedInstance sharedInstance = new SharedInstance(UUID.randomUUID(), instanceContainer);
        // Renvoie une valeur à l'appelant
        return registerSharedInstance(sharedInstance);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Unregisters the {@link Instance} internally.
     * <p>
     * If {@code instance} is an {@link InstanceContainer} all chunks are unloaded.
     *
     * @param instance the {@link Instance} to unregister
     */
    // Début d'une méthode/d'un bloc
    public void unregisterInstance(Instance instance) {
        // Appelle une méthode
        long onlinePlayers = instance.getPlayers().stream().filter(Player::isOnline).count();
        // Appelle une méthode
        Check.stateCondition(onlinePlayers > 0, "You cannot unregister an instance with players inside.");
        // Début d'une méthode/d'un bloc
        synchronized (instance) {
            // Appelle une méthode
            InstanceUnregisterEvent event = new InstanceUnregisterEvent(instance);
            // Appelle une méthode
            EventDispatcher.call(event);

            // Unload all chunks
            // Embranchement : vérifie une condition
            if (instance instanceof InstanceContainer) {
                // Appelle une méthode
                instance.getChunks().forEach(instance::unloadChunk);
                // Appelle une méthode
                var dispatcher = MinecraftServer.process().dispatcher();
                // Appelle une méthode
                instance.getChunks().forEach(dispatcher::deletePartition);
            // Fin d'un bloc/d'une expression
            }
            // Unregister
            // Appelle une méthode
            instance.setRegistered(false);
            // Accès à l'objet courant/parent
            this.instances.remove(instance);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all the registered instances.
     *
     * @return an unmodifiable {@link Set} containing all the registered instances
     */
    // Début d'une méthode/d'un bloc
    public Set<Instance> getInstances() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableSet(instances);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets an instance by the given UUID.
     *
     * @param uuid UUID of the instance
     * @return the instance with the given UUID, null if not found
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Instance getInstance(UUID uuid) {
        // Affecte une valeur
        Optional<Instance> instance = getInstances()
                // Instruction de code
                .stream()
                // Instruction de code
                .filter(someInstance -> someInstance.getUuid().equals(uuid))
                // Appelle une méthode
                .findFirst();
        // Renvoie une valeur à l'appelant
        return instance.orElse(null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Registers an {@link Instance} internally.
     * <p>
     * Unsafe because it does not check if {@code instance} is a {@link SharedInstance} to verify its container.
     *
     * @param instance the {@link Instance} to register
     */
    // Début d'une méthode/d'un bloc
    private void UNSAFE_registerInstance(Instance instance) {
        // Appelle une méthode
        instance.setRegistered(true);
        // Accès à l'objet courant/parent
        this.instances.add(instance);
        // Appelle une méthode
        var dispatcher = MinecraftServer.process().dispatcher();
        // Appelle une méthode
        instance.getChunks().forEach(dispatcher::createPartition);
        // Appelle une méthode
        InstanceRegisterEvent event = new InstanceRegisterEvent(instance);
        // Appelle une méthode
        EventDispatcher.call(event);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
