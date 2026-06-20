// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.instance.InstanceRegisterEvent;
// Import of a required class
import net.minestom.server.event.instance.InstanceUnregisterEvent;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Used to register {@link Instance}.
 */
// Type declaration (class/interface/enum/record)
public final class InstanceManager {

    // Code statement
    private final Registries registries;
    // Calls a method
    private final Set<Instance> instances = new CopyOnWriteArraySet<>();

    // Start of a method/block
    public InstanceManager(Registries registries) {
        // Access to the current/parent object
        this.registries = registries;
    // End of a block/expression
    }

    /**
     * Registers an {@link Instance} internally.
     * <p>
     * Note: not necessary if you created your instance using {@link #createInstanceContainer()} or {@link #createSharedInstance(InstanceContainer)}
     * but only if you instantiated your instance object manually
     *
     * @param instance the {@link Instance} to register
     */
    // Start of a method/block
    public void registerInstance(Instance instance) {
        // Code statement
        Check.stateCondition(instance instanceof SharedInstance,
                // Code statement
                "Please use InstanceManager#registerSharedInstance to register a shared instance");
        // Calls a method
        UNSAFE_registerInstance(instance);
    // End of a block/expression
    }

    /**
     * Creates and register an {@link InstanceContainer} with the specified {@link DimensionType}.
     *
     * @param dimensionType the {@link DimensionType} of the instance
     * @param loader        the chunk loader
     * @return the created {@link InstanceContainer}
     */
    // Start of a method/block
    public InstanceContainer createInstanceContainer(RegistryKey<DimensionType> dimensionType, @Nullable ChunkLoader loader) {
        // Calls a method
        final InstanceContainer instanceContainer = new InstanceContainer(registries, UUID.randomUUID(), dimensionType, loader, dimensionType.key());
        // Calls a method
        registerInstance(instanceContainer);
        // Returns a value to the caller
        return instanceContainer;
    // End of a block/expression
    }

    // Start of a method/block
    public InstanceContainer createInstanceContainer(RegistryKey<DimensionType> dimensionType) {
        // Returns a value to the caller
        return createInstanceContainer(dimensionType, null);
    // End of a block/expression
    }

    // Start of a method/block
    public InstanceContainer createInstanceContainer(@Nullable ChunkLoader loader) {
        // Returns a value to the caller
        return createInstanceContainer(DimensionType.OVERWORLD, loader);
    // End of a block/expression
    }

    /**
     * Creates and register an {@link InstanceContainer}.
     *
     * @return the created {@link InstanceContainer}
     */
    // Start of a method/block
    public InstanceContainer createInstanceContainer() {
        // Returns a value to the caller
        return createInstanceContainer(DimensionType.OVERWORLD, null);
    // End of a block/expression
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
    // Start of a method/block
    public SharedInstance registerSharedInstance(SharedInstance sharedInstance) {
        // Calls a method
        final InstanceContainer instanceContainer = sharedInstance.getInstanceContainer();
        // Calls a method
        Objects.requireNonNull(instanceContainer, "SharedInstance needs to have an InstanceContainer to be created!");

        // Calls a method
        instanceContainer.addSharedInstance(sharedInstance);
        // Calls a method
        UNSAFE_registerInstance(sharedInstance);
        // Returns a value to the caller
        return sharedInstance;
    // End of a block/expression
    }

    /**
     * Creates and register a {@link SharedInstance}.
     *
     * @param instanceContainer the container assigned to the shared instance
     * @return the created {@link SharedInstance}
     * @throws IllegalStateException if {@code instanceContainer} is not registered
     */
    // Start of a method/block
    public SharedInstance createSharedInstance(InstanceContainer instanceContainer) {
        // Calls a method
        Objects.requireNonNull(instanceContainer, "Instance container cannot be null when creating a SharedInstance!");
        // Calls a method
        Check.stateCondition(!instanceContainer.isRegistered(), "The container needs to be register in the InstanceManager");

        // Calls a method
        final SharedInstance sharedInstance = new SharedInstance(UUID.randomUUID(), instanceContainer);
        // Returns a value to the caller
        return registerSharedInstance(sharedInstance);
    // End of a block/expression
    }

    /**
     * Unregisters the {@link Instance} internally.
     * <p>
     * If {@code instance} is an {@link InstanceContainer} all chunks are unloaded.
     *
     * @param instance the {@link Instance} to unregister
     */
    // Start of a method/block
    public void unregisterInstance(Instance instance) {
        // Calls a method
        long onlinePlayers = instance.getPlayers().stream().filter(Player::isOnline).count();
        // Calls a method
        Check.stateCondition(onlinePlayers > 0, "You cannot unregister an instance with players inside.");
        // Start of a method/block
        synchronized (instance) {
            // Calls a method
            InstanceUnregisterEvent event = new InstanceUnregisterEvent(instance);
            // Calls a method
            EventDispatcher.call(event);

            // Unload all chunks
            // Branch: checks a condition
            if (instance instanceof InstanceContainer) {
                // Calls a method
                instance.getChunks().forEach(instance::unloadChunk);
                // Calls a method
                var dispatcher = MinecraftServer.process().dispatcher();
                // Calls a method
                instance.getChunks().forEach(dispatcher::deletePartition);
            // End of a block/expression
            }
            // Unregister
            // Calls a method
            instance.setRegistered(false);
            // Access to the current/parent object
            this.instances.remove(instance);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets all the registered instances.
     *
     * @return an unmodifiable {@link Set} containing all the registered instances
     */
    // Start of a method/block
    public Set<Instance> getInstances() {
        // Returns a value to the caller
        return Collections.unmodifiableSet(instances);
    // End of a block/expression
    }

    /**
     * Gets an instance by the given UUID.
     *
     * @param uuid UUID of the instance
     * @return the instance with the given UUID, null if not found
     */
    // Start of a method/block
    public @Nullable Instance getInstance(UUID uuid) {
        // Assigns a value
        Optional<Instance> instance = getInstances()
                // Code statement
                .stream()
                // Code statement
                .filter(someInstance -> someInstance.getUuid().equals(uuid))
                // Calls a method
                .findFirst();
        // Returns a value to the caller
        return instance.orElse(null);
    // End of a block/expression
    }

    /**
     * Registers an {@link Instance} internally.
     * <p>
     * Unsafe because it does not check if {@code instance} is a {@link SharedInstance} to verify its container.
     *
     * @param instance the {@link Instance} to register
     */
    // Start of a method/block
    private void UNSAFE_registerInstance(Instance instance) {
        // Calls a method
        instance.setRegistered(true);
        // Access to the current/parent object
        this.instances.add(instance);
        // Calls a method
        var dispatcher = MinecraftServer.process().dispatcher();
        // Calls a method
        instance.getChunks().forEach(dispatcher::createPartition);
        // Calls a method
        InstanceRegisterEvent event = new InstanceRegisterEvent(instance);
        // Calls a method
        EventDispatcher.call(event);
    // End of a block/expression
    }
// End of a block/expression
}
