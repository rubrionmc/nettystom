// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.villager;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.*;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class VillagerMeta extends AbstractVillagerMeta {
    // Début d'une méthode/d'un bloc
    public VillagerMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public VillagerData getVillagerData() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Villager.VARIANT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setVillagerData(VillagerData data) {
        // Appelle une méthode
        metadata.set(MetadataDef.Villager.VARIANT, data);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isFinalized() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Villager.IS_FINALIZED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFinalized(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Villager.IS_FINALIZED, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.VILLAGER_VARIANT)
            // Renvoie une valeur à l'appelant
            return (T) getVillagerData().type();
        // Renvoie une valeur à l'appelant
        return super.get(component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.VILLAGER_VARIANT)
            // Appelle une méthode
            setVillagerData(getVillagerData().withType((VillagerType) value));
        // Branche alternative de la condition
        else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record VillagerData(
            // Instruction de code
            VillagerType type,
            // Instruction de code
            VillagerProfession profession,
            // Instruction de code
            Level level
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        public static final VillagerData DEFAULT = new VillagerData(VillagerType.DESERT, VillagerProfession.NONE, Level.NOVICE);

        // Affecte une valeur
        public static final NetworkBuffer.Type<VillagerData> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                VillagerType.NETWORK_TYPE, VillagerData::type,
                // Instruction de code
                VillagerProfession.NETWORK_TYPE, VillagerData::profession,
                // Instruction de code
                Level.NETWORK_TYPE, VillagerData::level,
                // Instruction de code
                VillagerData::new);

        // Début d'une méthode/d'un bloc
        public VillagerData withType(VillagerType type) {
            // Renvoie une valeur à l'appelant
            return new VillagerData(type, this.profession, this.level);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public VillagerData withProfession(VillagerProfession profession) {
            // Renvoie une valeur à l'appelant
            return new VillagerData(this.type, profession, this.level);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public VillagerData withLevel(Level level) {
            // Renvoie une valeur à l'appelant
            return new VillagerData(this.type, this.profession, level);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Level {
        // Instruction de code
        NOVICE,
        // Instruction de code
        APPRENTICE,
        // Instruction de code
        JOURNEYMAN,
        // Instruction de code
        EXPERT,
        // Instruction de code
        MASTER;

        // Appelle une méthode
        private static final Level[] VALUES = values();

        // Début d'une méthode/d'un bloc
        private int toProtocolId() {
            // Renvoie une valeur à l'appelant
            return this.ordinal() + 1;  // Villager levels are 1-indexed
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static Level fromProtocolId(int value) {
            // Renvoie une valeur à l'appelant
            return VALUES[value - 1];
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        public static final NetworkBuffer.Type<Level> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(Level::fromProtocolId, Level::toProtocolId);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
