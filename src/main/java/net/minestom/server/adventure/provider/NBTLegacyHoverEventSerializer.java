// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.provider;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.api.BinaryTagHolder;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEvent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.json.LegacyHoverEventSerializer;
// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
// Import d'une classe nécessaire
import net.kyori.adventure.util.Codec;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.UUID;

// Déclaration de type (classe/interface/enum/record)
final class NBTLegacyHoverEventSerializer implements LegacyHoverEventSerializer {
    // Appelle une méthode
    static final NBTLegacyHoverEventSerializer INSTANCE = new NBTLegacyHoverEventSerializer();

    // Affecte une valeur
    private static final String ITEM_TYPE = "id", ITEM_COUNT = "Count", ITEM_TAG = "tag";
    // Affecte une valeur
    private static final String ENTITY_TYPE = "type", ENTITY_NAME = "name", ENTITY_ID = "id";

    // Début d'une méthode/d'un bloc
    private NBTLegacyHoverEventSerializer() {
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public HoverEvent.ShowItem deserializeShowItem(Component input) throws IOException {
        // Appelle une méthode
        final String raw = PlainTextComponentSerializer.plainText().serialize(input);
        // attempt the parse
        // Appelle une méthode
        final CompoundBinaryTag contents = MinestomAdventure.NBT_CODEC.decode(raw);
        // Appelle une méthode
        final CompoundBinaryTag tag = contents.getCompound(ITEM_TAG);

        // create the event
        // Renvoie une valeur à l'appelant
        return HoverEvent.ShowItem.showItem(
                // Instruction de code
                Key.key(contents.getString(ITEM_TYPE, "")),
                // Instruction de code
                contents.getByte(ITEM_COUNT, (byte) 1),
                // Instruction de code
                tag.size() == 0 ? null : BinaryTagHolder.encode(tag, MinestomAdventure.NBT_CODEC)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public HoverEvent.ShowEntity deserializeShowEntity(Component input, Codec.Decoder<Component, String, ? extends RuntimeException> componentDecoder) throws IOException {
        // Appelle une méthode
        final String raw = PlainTextComponentSerializer.plainText().serialize(input);
        // Appelle une méthode
        final CompoundBinaryTag contents = MinestomAdventure.NBT_CODEC.decode(raw);
        // Renvoie une valeur à l'appelant
        return HoverEvent.ShowEntity.showEntity(
                // Instruction de code
                Key.key(contents.getString(ENTITY_TYPE, "")),
                // Instruction de code
                UUID.fromString(Objects.requireNonNullElse(contents.getString(ENTITY_ID), "")),
                // Instruction de code
                componentDecoder.decode(Objects.requireNonNullElse(contents.getString(ENTITY_NAME), ""))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Component serializeShowItem(HoverEvent.ShowItem input) throws IOException {
        // Appelle une méthode
        CompoundBinaryTag.Builder tag = CompoundBinaryTag.builder();
        // Appelle une méthode
        tag.putString(ITEM_TYPE, input.item().asString());
        // Appelle une méthode
        tag.putByte(ITEM_COUNT, (byte) input.count());
        // Appelle une méthode
        final BinaryTagHolder nbt = input.nbt();
        // Embranchement : vérifie une condition
        if (nbt != null) tag.put(ITEM_TAG, nbt.get(MinestomAdventure.NBT_CODEC));
        // Renvoie une valeur à l'appelant
        return Component.text(MinestomAdventure.NBT_CODEC.encode(tag.build()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Component serializeShowEntity(HoverEvent.ShowEntity input, Codec.Encoder<Component, String, ? extends RuntimeException> componentEncoder) throws IOException {
        // Appelle une méthode
        CompoundBinaryTag.Builder tag = CompoundBinaryTag.builder();
        // Appelle une méthode
        tag.putString(ENTITY_ID, input.id().toString());
        // Appelle une méthode
        tag.putString(ENTITY_TYPE, input.type().asString());
        // Appelle une méthode
        final Component name = input.name();
        // Embranchement : vérifie une condition
        if (name != null) tag.putString(ENTITY_NAME, componentEncoder.encode(name));
        // Renvoie une valeur à l'appelant
        return Component.text(MinestomAdventure.NBT_CODEC.encode(tag.build()));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
