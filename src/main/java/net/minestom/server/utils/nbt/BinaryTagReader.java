// Déclaration du paquet de ce fichier
package net.minestom.server.utils.nbt;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTagType;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTagTypes;

// Import d'une classe nécessaire
import java.io.DataInput;
// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.util.Map;

// Based on net.kyori.adventure.nbt.BinaryTagReaderImpl licensed under the MIT license.
// https://github.com/KyoriPowered/adventure/blob/main/4/nbt/src/main/java/net/kyori/adventure/nbt/BinaryTagReaderImpl.java
// Déclaration de type (classe/interface/enum/record)
public class BinaryTagReader {

    // Début d'une méthode/d'un bloc
    static {
        // Instruction de code
        BinaryTagTypes.COMPOUND.id(); // Force initialization
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private final DataInput input;

    // Début d'une méthode/d'un bloc
    public BinaryTagReader(DataInput input) {
        // Accès à l'objet courant/parent
        this.input = input;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BinaryTag readNameless() throws IOException {
        // Appelle une méthode
        BinaryTagType<? extends BinaryTag> type = BinaryTagUtil.nbtTypeFromId(input.readByte());
        // Renvoie une valeur à l'appelant
        return type.read(input);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Map.Entry<String, BinaryTag> readNamed() throws IOException {
        // Appelle une méthode
        BinaryTagType<? extends BinaryTag> type = BinaryTagUtil.nbtTypeFromId(input.readByte());
        // Appelle une méthode
        String name = input.readUTF();
        // Renvoie une valeur à l'appelant
        return Map.entry(name, type.read(input));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
