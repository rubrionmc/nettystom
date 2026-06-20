// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.item.component.FireworkExplosion;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.codec.CodecAssertions.assertOk;

// Déclaration de type (classe/interface/enum/record)
public class TagSerializerTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fromCompound(){
        // Affecte une valeur
        var serializer = TagSerializer.fromCompound(
                // Instruction de code
                c -> assertOk(FireworkExplosion.CODEC.decode(Transcoder.NBT, c)),
                // Appelle une méthode
                explosion -> (CompoundBinaryTag) assertOk(FireworkExplosion.CODEC.encode(Transcoder.NBT, explosion)));
        // Appelle une méthode
        var effect = new FireworkExplosion(FireworkExplosion.Shape.BURST, List.of(), List.of(), false, false);
        // Appelle une méthode
        TagHandler handler = TagHandler.newHandler();
        // Appelle une méthode
        serializer.write(handler, effect);
        // Appelle une méthode
        Assertions.assertEquals(effect, serializer.read(handler));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
