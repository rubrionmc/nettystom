// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.instance.light.Light;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;

// Déclaration de type (classe/interface/enum/record)
public record Section(Palette blockPalette, Palette biomePalette, Light skyLight, Light blockLight) {
    // Début d'une méthode/d'un bloc
    public Section(Palette blockPalette, Palette biomePalette) {
        // Appelle une méthode
        this(blockPalette, biomePalette, Light.sky(), Light.block());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Section() {
        // Appelle une méthode
        this(Palette.blocks(), Palette.biomes());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void clear() {
        // Accès à l'objet courant/parent
        this.blockPalette.fill(0);
        // Accès à l'objet courant/parent
        this.biomePalette.fill(0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void invalidate() {
        // Accès à l'objet courant/parent
        this.skyLight.invalidate();
        // Accès à l'objet courant/parent
        this.blockLight.invalidate();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Section clone() {
        // Appelle une méthode
        final Light skyLight = Light.sky();
        // Appelle une méthode
        final Light blockLight = Light.block();

        // Appelle une méthode
        skyLight.set(this.skyLight.array());
        // Appelle une méthode
        blockLight.set(this.blockLight.array());

        // Renvoie une valeur à l'appelant
        return new Section(this.blockPalette.clone(), this.biomePalette.clone(), skyLight, blockLight);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSkyLight(byte[] copyArray) {
        // Accès à l'objet courant/parent
        this.skyLight.set(copyArray);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBlockLight(byte[] copyArray) {
        // Accès à l'objet courant/parent
        this.blockLight.set(copyArray);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
