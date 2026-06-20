// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.water.fish;

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
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

// Déclaration de type (classe/interface/enum/record)
public class SalmonMeta extends AbstractFishMeta {
    // Début d'une méthode/d'un bloc
    public SalmonMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#SALMON_SIZE} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public SalmonMeta.Size getSize() {
        // Renvoie une valeur à l'appelant
        return Size.VALUES[metadata.get(MetadataDef.Salmon.SIZE)];
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#SALMON_SIZE} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setSize(SalmonMeta.Size size) {
        // Appelle une méthode
        metadata.set(MetadataDef.Salmon.SIZE, size.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.SALMON_SIZE)
            // Renvoie une valeur à l'appelant
            return (T) getSize();
        // Renvoie une valeur à l'appelant
        return super.get(component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.SALMON_SIZE)
            // Appelle une méthode
            setSize((SalmonMeta.Size) value);
        // Branche alternative de la condition
        else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Size {
        // Instruction de code
        SMALL("small"),
        // Instruction de code
        MEDIUM("medium"),
        // Appelle une méthode
        LARGE("large");

        // Appelle une méthode
        private static final Size[] VALUES = values();

        // Appelle une méthode
        public static final NetworkBuffer.Type<Size> NETWORK_TYPE = NetworkBuffer.Enum(Size.class);
        // Appelle une méthode
        public static final Codec<Size> CODEC = Codec.Enum(Size.class);

        // Affecte une valeur
        private static final Map<String, Size> BY_ID = Arrays.stream(values())
                // Appelle une méthode
                .collect(Collectors.toMap(Size::id, (size) -> size));

        // Instruction de code
        private final String id;

        // Début d'une méthode/d'un bloc
        Size(String id) {
            // Accès à l'objet courant/parent
            this.id = id;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public String id() {
            // Renvoie une valeur à l'appelant
            return id;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
