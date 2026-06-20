// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.display;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class TextDisplayMeta extends AbstractDisplayMeta {
    // Début d'une méthode/d'un bloc
    public TextDisplayMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Component getText() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TextDisplay.TEXT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setText(Component value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TextDisplay.TEXT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getLineWidth() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TextDisplay.LINE_WIDTH);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLineWidth(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TextDisplay.LINE_WIDTH, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getBackgroundColor() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TextDisplay.BACKGROUND_COLOR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBackgroundColor(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TextDisplay.BACKGROUND_COLOR, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public byte getTextOpacity() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TextDisplay.TEXT_OPACITY);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setTextOpacity(byte value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TextDisplay.TEXT_OPACITY, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isShadow() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TextDisplay.HAS_SHADOW);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setShadow(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TextDisplay.HAS_SHADOW, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSeeThrough() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TextDisplay.IS_SEE_THROUGH);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSeeThrough(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TextDisplay.IS_SEE_THROUGH, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isUseDefaultBackground() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TextDisplay.USE_DEFAULT_BACKGROUND_COLOR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setUseDefaultBackground(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TextDisplay.USE_DEFAULT_BACKGROUND_COLOR, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isAlignLeft() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TextDisplay.ALIGN_LEFT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAlignLeft(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TextDisplay.ALIGN_LEFT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isAlignRight() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TextDisplay.ALIGN_RIGHT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAlignRight(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TextDisplay.ALIGN_RIGHT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Alignment getAlignment() {
        // Renvoie une valeur à l'appelant
        return Alignment.fromId(metadata.get(MetadataDef.TextDisplay.ALIGNMENT));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAlignment(Alignment value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TextDisplay.ALIGNMENT, (byte) value.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Alignment {
        // Instruction de code
        CENTER,
        // Instruction de code
        LEFT,
        // Instruction de code
        RIGHT;

        // Appelle une méthode
        private final static Alignment[] VALUES = values();

        // Début d'une méthode/d'un bloc
        private static Alignment fromId(int id) {
            // Embranchement : vérifie une condition
            if (id >= 0 && id < VALUES.length) {
                // Renvoie une valeur à l'appelant
                return VALUES[id];
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return CENTER;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
