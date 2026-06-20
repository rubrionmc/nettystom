// Package declaration for this file
package net.minestom.server.adventure.provider;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.api.BinaryTagHolder;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.event.HoverEvent;
// Import of a required class
import net.kyori.adventure.text.serializer.json.LegacyHoverEventSerializer;
// Import of a required class
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
// Import of a required class
import net.kyori.adventure.util.Codec;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.UUID;

// Type declaration (class/interface/enum/record)
final class NBTLegacyHoverEventSerializer implements LegacyHoverEventSerializer {
    // Calls a method
    static final NBTLegacyHoverEventSerializer INSTANCE = new NBTLegacyHoverEventSerializer();

    // Assigns a value
    private static final String ITEM_TYPE = "id", ITEM_COUNT = "Count", ITEM_TAG = "tag";
    // Assigns a value
    private static final String ENTITY_TYPE = "type", ENTITY_NAME = "name", ENTITY_ID = "id";

    // Start of a method/block
    private NBTLegacyHoverEventSerializer() {
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public HoverEvent.ShowItem deserializeShowItem(Component input) throws IOException {
        // Calls a method
        final String raw = PlainTextComponentSerializer.plainText().serialize(input);
        // attempt the parse
        // Calls a method
        final CompoundBinaryTag contents = MinestomAdventure.NBT_CODEC.decode(raw);
        // Calls a method
        final CompoundBinaryTag tag = contents.getCompound(ITEM_TAG);

        // create the event
        // Returns a value to the caller
        return HoverEvent.ShowItem.showItem(
                // Code statement
                Key.key(contents.getString(ITEM_TYPE, "")),
                // Code statement
                contents.getByte(ITEM_COUNT, (byte) 1),
                // Code statement
                tag.isEmpty() ? null : BinaryTagHolder.encode(tag, MinestomAdventure.NBT_CODEC)
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public HoverEvent.ShowEntity deserializeShowEntity(Component input, Codec.Decoder<Component, String, ? extends RuntimeException> componentDecoder) throws IOException {
        // Calls a method
        final String raw = PlainTextComponentSerializer.plainText().serialize(input);
        // Calls a method
        final CompoundBinaryTag contents = MinestomAdventure.NBT_CODEC.decode(raw);
        // Returns a value to the caller
        return HoverEvent.ShowEntity.showEntity(
                // Code statement
                Key.key(contents.getString(ENTITY_TYPE, "")),
                // Code statement
                UUID.fromString(Objects.requireNonNullElse(contents.getString(ENTITY_ID), "")),
                // Code statement
                componentDecoder.decode(Objects.requireNonNullElse(contents.getString(ENTITY_NAME), ""))
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Component serializeShowItem(HoverEvent.ShowItem input) throws IOException {
        // Calls a method
        CompoundBinaryTag.Builder tag = CompoundBinaryTag.builder();
        // Calls a method
        tag.putString(ITEM_TYPE, input.item().asString());
        // Calls a method
        tag.putByte(ITEM_COUNT, (byte) input.count());
        // Calls a method
        final BinaryTagHolder nbt = input.nbt();
        // Branch: checks a condition
        if (nbt != null) tag.put(ITEM_TAG, nbt.get(MinestomAdventure.NBT_CODEC));
        // Returns a value to the caller
        return Component.text(MinestomAdventure.NBT_CODEC.encode(tag.build()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Component serializeShowEntity(HoverEvent.ShowEntity input, Codec.Encoder<Component, String, ? extends RuntimeException> componentEncoder) throws IOException {
        // Calls a method
        CompoundBinaryTag.Builder tag = CompoundBinaryTag.builder();
        // Calls a method
        tag.putString(ENTITY_ID, input.id().toString());
        // Calls a method
        tag.putString(ENTITY_TYPE, input.type().asString());
        // Calls a method
        final Component name = input.name();
        // Branch: checks a condition
        if (name != null) tag.putString(ENTITY_NAME, componentEncoder.encode(name));
        // Returns a value to the caller
        return Component.text(MinestomAdventure.NBT_CODEC.encode(tag.build()));
    // End of a block/expression
    }
// End of a block/expression
}
