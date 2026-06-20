// Déclaration du paquet de ce fichier
package net.minestom.server.color;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.StringBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class AlphaColorTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void alphaColorTest() {
        // Appelle une méthode
        AlphaColor color = new AlphaColor(0x11, 0x22, 0x33, 0x44);
        // Appelle une méthode
        assertEquals(0x11223344, color.asARGB());
        // Appelle une méthode
        assertEquals(0x22334411, color.asRGBA());

        // Affecte une valeur
        String hexString = "#AABBCCDD";
        // Instruction de code
        assertEquals(
                // Crée un nouvel objet
                new AlphaColor(0xDDAABBCC),
                // Instruction de code
                AlphaColor.fromRGBAHexString(hexString)
        // Fin d'un bloc/d'une expression
        );
        // Instruction de code
        assertEquals(
                // Crée un nouvel objet
                new AlphaColor(0xAABBCCDD),
                // Instruction de code
                AlphaColor.fromARGBHexString(hexString)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void codecTest() {
        // Appelle une méthode
        AlphaColor testColor = new AlphaColor(0x01, 0x23, 0x45, 0x67);
        // Appelle une méthode
        BinaryTag elementARGB = AlphaColor.ARGB_STRING_CODEC.encode(Transcoder.NBT, testColor).orElseThrow();
        // Appelle une méthode
        BinaryTag elementRGBA = AlphaColor.RGBA_STRING_CODEC.encode(Transcoder.NBT, testColor).orElseThrow();
        // Appelle une méthode
        assertEquals(StringBinaryTag.stringBinaryTag("#01234567"), elementARGB);
        // Appelle une méthode
        assertEquals(StringBinaryTag.stringBinaryTag("#23456701"), elementRGBA);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
