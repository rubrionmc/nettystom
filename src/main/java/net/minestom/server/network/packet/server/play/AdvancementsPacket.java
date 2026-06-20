// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.advancements.FrameType;
// Import d'une classe nécessaire
import net.minestom.server.adventure.ComponentHolder;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Déclaration de type (classe/interface/enum/record)
public record AdvancementsPacket(
        // Instruction de code
        boolean reset,
        // Instruction de code
        List<AdvancementMapping> advancementMappings,
        // Instruction de code
        List<String> identifiersToRemove,
        // Instruction de code
        List<ProgressMapping> progressMappings,
        // Instruction de code
        boolean showAdvancements
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final int MAX_ADVANCEMENTS = Short.MAX_VALUE;

    // Affecte une valeur
    public static final NetworkBuffer.Type<AdvancementsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.BOOLEAN, AdvancementsPacket::reset,
            // Instruction de code
            AdvancementMapping.SERIALIZER.list(MAX_ADVANCEMENTS), AdvancementsPacket::advancementMappings,
            // Instruction de code
            NetworkBuffer.STRING.list(MAX_ADVANCEMENTS), AdvancementsPacket::identifiersToRemove,
            // Instruction de code
            ProgressMapping.SERIALIZER.list(MAX_ADVANCEMENTS), AdvancementsPacket::progressMappings,
            // Instruction de code
            NetworkBuffer.BOOLEAN, AdvancementsPacket::showAdvancements,
            // Instruction de code
            AdvancementsPacket::new
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public AdvancementsPacket {
        // Appelle une méthode
        advancementMappings = List.copyOf(advancementMappings);
        // Appelle une méthode
        identifiersToRemove = List.copyOf(identifiersToRemove);
        // Appelle une méthode
        progressMappings = List.copyOf(progressMappings);
    // Fin d'un bloc/d'une expression
    }

    // TODO is the display-item needed to be updated?
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Appelle une méthode
        final var displayData = this.advancementMappings.stream().map(AdvancementMapping::value).map(Advancement::displayData).filter(Objects::nonNull).toList();
        // Appelle une méthode
        final var titles = displayData.stream().map(DisplayData::title).toList();
        // Appelle une méthode
        final var descriptions = displayData.stream().map(DisplayData::description).toList();

        // Appelle une méthode
        final var list = new ArrayList<Component>();

        // Appelle une méthode
        list.addAll(titles);
        // Appelle une méthode
        list.addAll(descriptions);

        // Renvoie une valeur à l'appelant
        return List.copyOf(list);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(final UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return new AdvancementsPacket(
                // Accès à l'objet courant/parent
                this.reset,
                // Accès à l'objet courant/parent
                this.advancementMappings.stream().map(mapping -> mapping.copyWithOperator(operator)).toList(),
                // Accès à l'objet courant/parent
                this.identifiersToRemove,
                // Accès à l'objet courant/parent
                this.progressMappings,
                // Accès à l'objet courant/parent
                this.showAdvancements
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    /**
     * AdvancementMapping maps the namespaced ID to the Advancement.
     */
    // Déclaration de type (classe/interface/enum/record)
    public record AdvancementMapping(String key,
                                     // Début d'une méthode/d'un bloc
                                     Advancement value) implements ComponentHolder<AdvancementMapping> {
        // Affecte une valeur
        public static final NetworkBuffer.Type<AdvancementMapping> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.STRING, AdvancementMapping::key,
                // Instruction de code
                Advancement.SERIALIZER, AdvancementMapping::value,
                // Instruction de code
                AdvancementMapping::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Renvoie une valeur à l'appelant
            return this.value.components();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public AdvancementMapping copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return this.value.displayData == null ? this : new AdvancementMapping(this.key, this.value.copyWithOperator(operator));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Advancement(@Nullable String parentIdentifier, @Nullable DisplayData displayData,
                              // Instruction de code
                              List<Requirement> requirements,
                              // Début d'une méthode/d'un bloc
                              boolean sendTelemetryData) implements ComponentHolder<Advancement> {
        // Début d'une méthode/d'un bloc
        public Advancement {
            // Appelle une méthode
            requirements = List.copyOf(requirements);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<Advancement> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.STRING.optional(), Advancement::parentIdentifier,
                // Instruction de code
                DisplayData.SERIALIZER.optional(), Advancement::displayData,
                // Instruction de code
                Requirement.SERIALIZER.list(MAX_ADVANCEMENTS), Advancement::requirements,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Advancement::sendTelemetryData,
                // Instruction de code
                Advancement::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Renvoie une valeur à l'appelant
            return this.displayData != null ? this.displayData.components() : List.of();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Advancement copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return this.displayData == null ? this : new Advancement(this.parentIdentifier, this.displayData.copyWithOperator(operator), this.requirements, this.sendTelemetryData);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Requirement(List<String> requirements) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Requirement> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.STRING.list(MAX_ADVANCEMENTS), Requirement::requirements,
                // Instruction de code
                Requirement::new
        // Fin d'un bloc/d'une expression
        );

        // Début d'une méthode/d'un bloc
        public Requirement {
            // Appelle une méthode
            requirements = List.copyOf(requirements);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record DisplayData(Component title, Component description,
                              // Instruction de code
                              ItemStack icon, FrameType frameType,
                              // Instruction de code
                              int flags, @Nullable String backgroundTexture,
                              // Début d'une méthode/d'un bloc
                              float x, float y) implements ComponentHolder<DisplayData> {

        // Affecte une valeur
        public static final NetworkBuffer.Type<DisplayData> SERIALIZER = new NetworkBuffer.Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, DisplayData value) {
                // Appelle une méthode
                buffer.write(NetworkBuffer.COMPONENT, value.title);
                // Appelle une méthode
                buffer.write(NetworkBuffer.COMPONENT, value.description);
                // Appelle une méthode
                buffer.write(ItemStack.NETWORK_TYPE, value.icon);
                // Appelle une méthode
                buffer.write(NetworkBuffer.Enum(FrameType.class), value.frameType);
                // Appelle une méthode
                buffer.write(NetworkBuffer.INT, value.flags);
                // Embranchement : vérifie une condition
                if ((value.flags & 0x1) != 0) {
                    // Instruction de code
                    assert value.backgroundTexture != null;
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.STRING, value.backgroundTexture);
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                buffer.write(NetworkBuffer.FLOAT, value.x);
                // Appelle une méthode
                buffer.write(NetworkBuffer.FLOAT, value.y);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public DisplayData read(NetworkBuffer buffer) {
                // Appelle une méthode
                var title = buffer.read(NetworkBuffer.COMPONENT);
                // Appelle une méthode
                var description = buffer.read(NetworkBuffer.COMPONENT);
                // Appelle une méthode
                var icon = buffer.read(ItemStack.NETWORK_TYPE);
                // Appelle une méthode
                var frameType = FrameType.values()[buffer.read(NetworkBuffer.VAR_INT)];
                // Appelle une méthode
                var flags = buffer.read(NetworkBuffer.INT);
                // Appelle une méthode
                var backgroundTexture = (flags & 0x1) != 0 ? buffer.read(NetworkBuffer.STRING) : null;
                // Appelle une méthode
                var x = buffer.read(NetworkBuffer.FLOAT);
                // Appelle une méthode
                var y = buffer.read(NetworkBuffer.FLOAT);
                // Renvoie une valeur à l'appelant
                return new DisplayData(title, description,
                        // Instruction de code
                        icon, frameType,
                        // Instruction de code
                        flags, backgroundTexture,
                        // Instruction de code
                        x, y);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Renvoie une valeur à l'appelant
            return List.of(this.title, this.description);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DisplayData copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return new DisplayData(operator.apply(this.title), operator.apply(this.description), this.icon, this.frameType, this.flags, this.backgroundTexture, this.x, this.y);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record ProgressMapping(String key, AdvancementProgress progress) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<ProgressMapping> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.STRING, ProgressMapping::key,
                // Instruction de code
                AdvancementProgress.SERIALIZER, ProgressMapping::progress,
                // Instruction de code
                ProgressMapping::new
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record AdvancementProgress(List<Criteria> criteria) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<AdvancementProgress> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                Criteria.SERIALIZER.list(MAX_ADVANCEMENTS), AdvancementProgress::criteria,
                // Instruction de code
                AdvancementProgress::new
        // Fin d'un bloc/d'une expression
        );

        // Début d'une méthode/d'un bloc
        public AdvancementProgress {
            // Appelle une méthode
            criteria = List.copyOf(criteria);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Criteria(String criterionIdentifier, CriterionProgress criterionProgress) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Criteria> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.STRING, Criteria::criterionIdentifier,
                // Instruction de code
                CriterionProgress.SERIALIZER, Criteria::criterionProgress,
                // Instruction de code
                Criteria::new
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record CriterionProgress(@Nullable Long dateOfAchieving) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<CriterionProgress> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.LONG.optional(), CriterionProgress::dateOfAchieving,
                // Instruction de code
                CriterionProgress::new
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}