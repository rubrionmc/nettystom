// Déclaration du paquet de ce fichier
package net.minestom.server.utils.block;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public class BlockUtils {

    // Instruction de code
    private final Block.Getter instance;
    // Instruction de code
    private final Point position;

    // Début d'une méthode/d'un bloc
    public BlockUtils(Block.Getter instance, Point position) {
        // Accès à l'objet courant/parent
        this.instance = instance;
        // Accès à l'objet courant/parent
        this.position = position;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockUtils getRelativeTo(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return new BlockUtils(instance, position.add(x, y, z));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockUtils above() {
        // Renvoie une valeur à l'appelant
        return getRelativeTo(0, 1, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockUtils below() {
        // Renvoie une valeur à l'appelant
        return getRelativeTo(0, -1, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockUtils north() {
        // Renvoie une valeur à l'appelant
        return getRelativeTo(0, 0, -1);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockUtils east() {
        // Renvoie une valeur à l'appelant
        return getRelativeTo(1, 0, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockUtils south() {
        // Renvoie une valeur à l'appelant
        return getRelativeTo(0, 0, 1);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockUtils west() {
        // Renvoie une valeur à l'appelant
        return getRelativeTo(-1, 0, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Block getBlock() {
        // Renvoie une valeur à l'appelant
        return instance.getBlock(position);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean equals(Block block) {
        // Renvoie une valeur à l'appelant
        return getBlock().compare(block);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Map<String, String> parseProperties(String query) {
        // Embranchement : vérifie une condition
        if (!query.startsWith("[") || !query.endsWith("]")) return Map.of();
        // Embranchement : vérifie une condition
        if (query.length() == 2) return Map.of();

        // Appelle une méthode
        final int entries = StringUtils.countMatches(query, ',') + 1;
        // Instruction de code
        assert entries > 0;
        // Affecte une valeur
        String[] keys = new String[entries];
        // Affecte une valeur
        String[] values = new String[entries];
        // Affecte une valeur
        int entryCount = 0;

        // Appelle une méthode
        final int length = query.length() - 1;
        // Affecte une valeur
        int start = 1;
        // Affecte une valeur
        int index = 1;
        // Boucle : répète un bloc
        while (index <= length) {
            // Embranchement : vérifie une condition
            if (query.charAt(index) == ',' || index == length) {
                // Appelle une méthode
                final int equalIndex = query.indexOf('=', start);
                // Embranchement : vérifie une condition
                if (equalIndex != -1) {
                    // Appelle une méthode
                    final String key = query.substring(start, equalIndex).trim();
                    // Appelle une méthode
                    final String value = query.substring(equalIndex + 1, index).trim();
                    // Affecte une valeur
                    keys[entryCount] = key;
                    // Affecte une valeur
                    values[entryCount++] = value;
                // Fin d'un bloc/d'une expression
                }
                // Affecte une valeur
                start = index + 1;
            // Fin d'un bloc/d'une expression
            }
            // Instruction de code
            index++;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new Object2ObjectArrayMap<>(keys, values, entryCount);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static @Nullable CompoundBinaryTag extractClientNbt(Block block) {
        // Embranchement : vérifie une condition
        if (!block.registry().isBlockEntity()) return null;
        // Append handler tags
        // Appelle une méthode
        final BlockHandler handler = block.handler();
        // Appelle une méthode
        final CompoundBinaryTag blockNbt = Objects.requireNonNullElseGet(block.nbt(), CompoundBinaryTag::empty);
        // Embranchement : vérifie une condition
        if (handler != null) {
            // Extract explicitly defined tags and keep the rest server-side
            // Appelle une méthode
            var builder = CompoundBinaryTag.builder();
            // Boucle : répète un bloc
            for (Tag<?> tag : handler.getBlockEntityTags()) {
                // Appelle une méthode
                final var value = tag.read(blockNbt);
                // Embranchement : vérifie une condition
                if (value != null) {
                    // Tag is present and valid
                    // Appelle une méthode
                    tag.writeUnsafe(builder, value);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return builder.build();
        // Fin d'un bloc/d'une expression
        }
        // Complete nbt shall be sent if the block has no handler
        // Necessary to support all vanilla blocks
        // Renvoie une valeur à l'appelant
        return blockNbt;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
