// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.golem;

// Import d'une classe nécessaire
import net.minestom.server.color.DyeColor;
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
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class ShulkerMeta extends AbstractGolemMeta {
    // Appelle une méthode
    private static final DyeColor[] DYE_VALUES = DyeColor.values();

    // Début d'une méthode/d'un bloc
    public ShulkerMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Direction getAttachFace() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Shulker.ATTACH_FACE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAttachFace(Direction value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Shulker.ATTACH_FACE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public byte getShieldHeight() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Shulker.SHIELD_HEIGHT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setShieldHeight(byte value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Shulker.SHIELD_HEIGHT, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#SHULKER_COLOR} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public @Nullable DyeColor getColor() {
        // Appelle une méthode
        byte index = metadata.get(MetadataDef.Shulker.COLOR);
        // Embranchement : vérifie une condition
        if (index < 0) {
            // Renvoie une valeur à l'appelant
            return DyeColor.WHITE;
        // Embranchement : vérifie une condition
        } else if (index < 16) {
            // Renvoie une valeur à l'appelant
            return DYE_VALUES[index];
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#SHULKER_COLOR} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setColor(@Nullable DyeColor value) {
        // Appelle une méthode
        byte index = value == null ? (byte) 16 : (byte) value.ordinal();
        // Appelle une méthode
        metadata.set(MetadataDef.Shulker.COLOR, index);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.SHULKER_COLOR)
            // Renvoie une valeur à l'appelant
            return (T) getColor();
        // Renvoie une valeur à l'appelant
        return super.get(component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.SHULKER_COLOR)
            // Appelle une méthode
            setColor((DyeColor) value);
        // Branche alternative de la condition
        else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
