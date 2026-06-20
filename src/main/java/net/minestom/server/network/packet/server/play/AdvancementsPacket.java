// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.advancements.FrameType;
// Import of a required class
import net.minestom.server.adventure.ComponentHolder;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.UnaryOperator;

// Type declaration (class/interface/enum/record)
public record AdvancementsPacket(
        // Code statement
        boolean reset,
        // Code statement
        List<AdvancementMapping> advancementMappings,
        // Code statement
        List<String> identifiersToRemove,
        // Code statement
        List<ProgressMapping> progressMappings,
        // Code statement
        boolean showAdvancements
// Start of a method/block
) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final int MAX_ADVANCEMENTS = Short.MAX_VALUE;

    // Assigns a value
    public static final NetworkBuffer.Type<AdvancementsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.BOOLEAN, AdvancementsPacket::reset,
            // Code statement
            AdvancementMapping.SERIALIZER.list(MAX_ADVANCEMENTS), AdvancementsPacket::advancementMappings,
            // Code statement
            NetworkBuffer.STRING.list(MAX_ADVANCEMENTS), AdvancementsPacket::identifiersToRemove,
            // Code statement
            ProgressMapping.SERIALIZER.list(MAX_ADVANCEMENTS), AdvancementsPacket::progressMappings,
            // Code statement
            NetworkBuffer.BOOLEAN, AdvancementsPacket::showAdvancements,
            // Code statement
            AdvancementsPacket::new
    // End of a block/expression
    );

    // Start of a method/block
    public AdvancementsPacket {
        // Calls a method
        advancementMappings = List.copyOf(advancementMappings);
        // Calls a method
        identifiersToRemove = List.copyOf(identifiersToRemove);
        // Calls a method
        progressMappings = List.copyOf(progressMappings);
    // End of a block/expression
    }

    // TODO is the display-item needed to be updated?
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Calls a method
        final var displayData = this.advancementMappings.stream().map(AdvancementMapping::value).map(Advancement::displayData).filter(Objects::nonNull).toList();
        // Calls a method
        final var titles = displayData.stream().map(DisplayData::title).toList();
        // Calls a method
        final var descriptions = displayData.stream().map(DisplayData::description).toList();

        // Calls a method
        final var list = new ArrayList<Component>();

        // Calls a method
        list.addAll(titles);
        // Calls a method
        list.addAll(descriptions);

        // Returns a value to the caller
        return List.copyOf(list);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(final UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return new AdvancementsPacket(
                // Access to the current/parent object
                this.reset,
                // Access to the current/parent object
                this.advancementMappings.stream().map(mapping -> mapping.copyWithOperator(operator)).toList(),
                // Access to the current/parent object
                this.identifiersToRemove,
                // Access to the current/parent object
                this.progressMappings,
                // Access to the current/parent object
                this.showAdvancements
        // End of a block/expression
        );
    // End of a block/expression
    }

    /**
     * AdvancementMapping maps the namespaced ID to the Advancement.
     */
    // Type declaration (class/interface/enum/record)
    public record AdvancementMapping(String key,
                                     // Start of a method/block
                                     Advancement value) implements ComponentHolder<AdvancementMapping> {
        // Assigns a value
        public static final NetworkBuffer.Type<AdvancementMapping> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.STRING, AdvancementMapping::key,
                // Code statement
                Advancement.SERIALIZER, AdvancementMapping::value,
                // Code statement
                AdvancementMapping::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Returns a value to the caller
            return this.value.components();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public AdvancementMapping copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return this.value.displayData == null ? this : new AdvancementMapping(this.key, this.value.copyWithOperator(operator));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Advancement(@Nullable String parentIdentifier, @Nullable DisplayData displayData,
                              // Code statement
                              List<Requirement> requirements,
                              // Start of a method/block
                              boolean sendTelemetryData) implements ComponentHolder<Advancement> {
        // Start of a method/block
        public Advancement {
            // Calls a method
            requirements = List.copyOf(requirements);
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<Advancement> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.STRING.optional(), Advancement::parentIdentifier,
                // Code statement
                DisplayData.SERIALIZER.optional(), Advancement::displayData,
                // Code statement
                Requirement.SERIALIZER.list(MAX_ADVANCEMENTS), Advancement::requirements,
                // Code statement
                NetworkBuffer.BOOLEAN, Advancement::sendTelemetryData,
                // Code statement
                Advancement::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Returns a value to the caller
            return this.displayData != null ? this.displayData.components() : List.of();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Advancement copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return this.displayData == null ? this : new Advancement(this.parentIdentifier, this.displayData.copyWithOperator(operator), this.requirements, this.sendTelemetryData);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Requirement(List<String> requirements) {
        // Assigns a value
        public static final NetworkBuffer.Type<Requirement> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.STRING.list(MAX_ADVANCEMENTS), Requirement::requirements,
                // Code statement
                Requirement::new
        // End of a block/expression
        );

        // Start of a method/block
        public Requirement {
            // Calls a method
            requirements = List.copyOf(requirements);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record DisplayData(Component title, Component description,
                              // Code statement
                              ItemStack icon, FrameType frameType,
                              // Code statement
                              int flags, @Nullable String backgroundTexture,
                              // Start of a method/block
                              float x, float y) implements ComponentHolder<DisplayData> {

        // Assigns a value
        public static final NetworkBuffer.Type<DisplayData> SERIALIZER = new NetworkBuffer.Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, DisplayData value) {
                // Calls a method
                buffer.write(NetworkBuffer.COMPONENT, value.title);
                // Calls a method
                buffer.write(NetworkBuffer.COMPONENT, value.description);
                // Calls a method
                buffer.write(ItemStack.NETWORK_TYPE, value.icon);
                // Calls a method
                buffer.write(NetworkBuffer.Enum(FrameType.class), value.frameType);
                // Calls a method
                buffer.write(NetworkBuffer.INT, value.flags);
                // Branch: checks a condition
                if ((value.flags & 0x1) != 0) {
                    // Code statement
                    assert value.backgroundTexture != null;
                    // Calls a method
                    buffer.write(NetworkBuffer.STRING, value.backgroundTexture);
                // End of a block/expression
                }
                // Calls a method
                buffer.write(NetworkBuffer.FLOAT, value.x);
                // Calls a method
                buffer.write(NetworkBuffer.FLOAT, value.y);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public DisplayData read(NetworkBuffer buffer) {
                // Calls a method
                var title = buffer.read(NetworkBuffer.COMPONENT);
                // Calls a method
                var description = buffer.read(NetworkBuffer.COMPONENT);
                // Calls a method
                var icon = buffer.read(ItemStack.NETWORK_TYPE);
                // Calls a method
                var frameType = FrameType.values()[buffer.read(NetworkBuffer.VAR_INT)];
                // Calls a method
                var flags = buffer.read(NetworkBuffer.INT);
                // Calls a method
                var backgroundTexture = (flags & 0x1) != 0 ? buffer.read(NetworkBuffer.STRING) : null;
                // Calls a method
                var x = buffer.read(NetworkBuffer.FLOAT);
                // Calls a method
                var y = buffer.read(NetworkBuffer.FLOAT);
                // Returns a value to the caller
                return new DisplayData(title, description,
                        // Code statement
                        icon, frameType,
                        // Code statement
                        flags, backgroundTexture,
                        // Code statement
                        x, y);
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Returns a value to the caller
            return List.of(this.title, this.description);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public DisplayData copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return new DisplayData(operator.apply(this.title), operator.apply(this.description), this.icon, this.frameType, this.flags, this.backgroundTexture, this.x, this.y);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record ProgressMapping(String key, AdvancementProgress progress) {
        // Assigns a value
        public static final NetworkBuffer.Type<ProgressMapping> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.STRING, ProgressMapping::key,
                // Code statement
                AdvancementProgress.SERIALIZER, ProgressMapping::progress,
                // Code statement
                ProgressMapping::new
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record AdvancementProgress(List<Criteria> criteria) {
        // Assigns a value
        public static final NetworkBuffer.Type<AdvancementProgress> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                Criteria.SERIALIZER.list(MAX_ADVANCEMENTS), AdvancementProgress::criteria,
                // Code statement
                AdvancementProgress::new
        // End of a block/expression
        );

        // Start of a method/block
        public AdvancementProgress {
            // Calls a method
            criteria = List.copyOf(criteria);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Criteria(String criterionIdentifier, CriterionProgress criterionProgress) {
        // Assigns a value
        public static final NetworkBuffer.Type<Criteria> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.STRING, Criteria::criterionIdentifier,
                // Code statement
                CriterionProgress.SERIALIZER, Criteria::criterionProgress,
                // Code statement
                Criteria::new
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record CriterionProgress(@Nullable Long dateOfAchieving) {
        // Assigns a value
        public static final NetworkBuffer.Type<CriterionProgress> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.LONG.optional(), CriterionProgress::dateOfAchieving,
                // Code statement
                CriterionProgress::new
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}