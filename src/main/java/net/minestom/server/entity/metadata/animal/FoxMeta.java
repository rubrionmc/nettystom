// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.UUID;

// Déclaration de type (classe/interface/enum/record)
public class FoxMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public FoxMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }


    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#FOX_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public FoxMeta.Variant getVariant() {
        // Renvoie une valeur à l'appelant
        return Variant.VALUES[metadata.get(MetadataDef.Fox.VARIANT)];
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#FOX_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setVariant(FoxMeta.Variant variant) {
        // Appelle une méthode
        metadata.set(MetadataDef.Fox.VARIANT, variant.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSitting() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Fox.IS_SITTING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSitting(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Fox.IS_SITTING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isFoxSneaking() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Fox.IS_CROUCHING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFoxSneaking(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Fox.IS_CROUCHING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isInterested() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Fox.IS_INTERESTED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setInterested(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Fox.IS_INTERESTED, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isPouncing() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Fox.IS_POUNCING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setPouncing(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Fox.IS_POUNCING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSleeping() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Fox.IS_SLEEPING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSleeping(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Fox.IS_SLEEPING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isFaceplanted() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Fox.IS_FACEPLANTED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFaceplanted(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Fox.IS_FACEPLANTED, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isDefending() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Fox.IS_DEFENDING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setDefending(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Fox.IS_DEFENDING, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public UUID getFirstUUID() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Fox.FIRST_UUID);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFirstUUID(@Nullable UUID value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Fox.FIRST_UUID, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public UUID getSecondUUID() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Fox.SECOND_UUID);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSecondUUID(@Nullable UUID value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Fox.SECOND_UUID, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.FOX_VARIANT)
            // Renvoie une valeur à l'appelant
            return (T) getVariant();
        // Renvoie une valeur à l'appelant
        return super.get(component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.FOX_VARIANT)
            // Appelle une méthode
            setVariant((FoxMeta.Variant) value);
        // Branche alternative de la condition
        else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Variant {
        // Instruction de code
        RED,
        // Instruction de code
        SNOW;

        // Appelle une méthode
        public static final NetworkBuffer.Type<Variant> NETWORK_TYPE = NetworkBuffer.Enum(Variant.class);
        // Appelle une méthode
        public static final Codec<Variant> CODEC = Codec.Enum(Variant.class);

        // Appelle une méthode
        private final static Variant[] VALUES = values();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
