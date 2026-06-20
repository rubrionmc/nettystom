// Déclaration du paquet de ce fichier
package net.minestom.server.utils.nbt;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTagType;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTagTypes;

// Import d'une classe nécessaire
import java.io.DataOutput;
// Import d'une classe nécessaire
import java.io.IOException;

// Based on net.kyori.adventure.nbt.BinaryTagWriterImpl licensed under the MIT license.
// https://github.com/KyoriPowered/adventure/blob/main/4/nbt/src/main/java/net/kyori/adventure/nbt/BinaryTagWriterImpl.java
// Déclaration de type (classe/interface/enum/record)
public class BinaryTagWriter {

    // Début d'une méthode/d'un bloc
    static {
        // Instruction de code
        BinaryTagTypes.COMPOUND.id(); // Force initialization
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private final DataOutput output;

    // Début d'une méthode/d'un bloc
    public BinaryTagWriter(DataOutput output) {
        // Accès à l'objet courant/parent
        this.output = output;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void writeNameless(BinaryTag tag) throws IOException {
        //noinspection unchecked
        // Appelle une méthode
        BinaryTagType<BinaryTag> type = (BinaryTagType<BinaryTag>) tag.type();
        // Appelle une méthode
        output.writeByte(type.id());
        // Appelle une méthode
        type.write(tag, output);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void writeNamed(String name, BinaryTag tag) throws IOException {
        //noinspection unchecked
        // Appelle une méthode
        BinaryTagType<BinaryTag> type = (BinaryTagType<BinaryTag>) tag.type();
        // Appelle une méthode
        output.writeByte(type.id());
        // Appelle une méthode
        output.writeUTF(name);
        // Appelle une méthode
        type.write(tag, output);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
