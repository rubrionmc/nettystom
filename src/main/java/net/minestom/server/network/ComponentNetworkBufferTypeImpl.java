// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.text.*;
// Import of a required class
import net.kyori.adventure.text.event.ClickEvent;
// Import of a required class
import net.kyori.adventure.text.event.HoverEvent;
// Import of a required class
import net.kyori.adventure.text.format.*;
// Import of a required class
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
// Import of a required class
import net.kyori.adventure.text.object.SpriteObjectContents;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.adventure.serializer.nbt.NbtDataComponentValue;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.dialog.Dialog;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;
// Import of a required class
import net.minestom.server.utils.nbt.BinaryTagWriter;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.util.*;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;
// Static import of a member
import static net.minestom.server.network.NetworkBufferImpl.impl;

// Type declaration (class/interface/enum/record)
record ComponentNetworkBufferTypeImpl() implements NetworkBufferTypeImpl<Component> {

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void write(NetworkBuffer buffer, Component value) {
        // Calls a method
        Objects.requireNonNull(value, "Component cannot be null");

        // Calls a method
        buffer.write(BYTE, TAG_COMPOUND);
        // Calls a method
        writeInnerComponent(buffer, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Component read(NetworkBuffer buffer) {
        // Assigns a value
        final Transcoder<BinaryTag> coder = buffer.registries() != null
                // Code statement
                ? new RegistryTranscoder<>(Transcoder.NBT, buffer.registries())
                // Code statement
                : Transcoder.NBT;
        // Returns a value to the caller
        return Codec.COMPONENT.decode(coder, buffer.read(NBT)).orElseThrow();
    // End of a block/expression
    }

    // WRITING IMPL, pretty gross. Would not recommend reading.

    // Assigns a value
    private static final byte TAG_END = 0;
    // Assigns a value
    private static final byte TAG_BYTE = 1;
    // Assigns a value
    private static final byte TAG_INT = 3;
    // Assigns a value
    private static final byte TAG_STRING = 8;
    // Assigns a value
    private static final byte TAG_LIST = 9;
    // Assigns a value
    private static final byte TAG_COMPOUND = 10;
    // Assigns a value
    private static final byte TAG_INT_ARRAY = 11;

    // Start of a method/block
    private void writeInnerComponent(NetworkBuffer buffer, Component component) {
        // Code statement
        buffer.write(BYTE, TAG_STRING); // Start first tag (always the type)
        // Calls a method
        buffer.write(STRING_IO_UTF8, "type");
        // Multiple branching (switch/case)
        switch (component) {
            // Multiple branching (switch/case)
            case TextComponent text -> {
                // Calls a method
                buffer.write(STRING_IO_UTF8, "text");

                // Code statement
                buffer.write(BYTE, TAG_STRING); // Start "text" tag
                // Calls a method
                buffer.write(STRING_IO_UTF8, "text");
                // Calls a method
                buffer.write(STRING_IO_UTF8, text.content());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case TranslatableComponent translatable -> {
                // Calls a method
                buffer.write(STRING_IO_UTF8, "translatable");

                // Code statement
                buffer.write(BYTE, TAG_STRING); // Start "translate" tag
                // Calls a method
                buffer.write(STRING_IO_UTF8, "translate");
                // Calls a method
                buffer.write(STRING_IO_UTF8, translatable.key());

                // Calls a method
                final String fallback = translatable.fallback();
                // Branch: checks a condition
                if (fallback != null) {
                    // Calls a method
                    buffer.write(BYTE, TAG_STRING);
                    // Calls a method
                    buffer.write(STRING_IO_UTF8, "fallback");
                    // Calls a method
                    buffer.write(STRING_IO_UTF8, fallback);
                // End of a block/expression
                }

                // Calls a method
                final List<TranslationArgument> args = translatable.arguments();
                // Branch: checks a condition
                if (!args.isEmpty()) {
                    // Calls a method
                    buffer.write(BYTE, TAG_LIST);
                    // Calls a method
                    buffer.write(STRING_IO_UTF8, "with");
                    // Code statement
                    buffer.write(BYTE, TAG_COMPOUND); // List type
                    // Calls a method
                    buffer.write(INT, args.size());
                    // Loop: repeats a block
                    for (final TranslationArgument arg : args)
                        // Calls a method
                        writeInnerComponent(buffer, arg.asComponent());
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case ScoreComponent score -> {
                // Calls a method
                buffer.write(STRING_IO_UTF8, "score");

                // Code statement
                buffer.write(BYTE, TAG_COMPOUND); // Start "score" tag
                // Calls a method
                buffer.write(STRING_IO_UTF8, "score");
                // Start of a block
                {
                    // Calls a method
                    buffer.write(BYTE, TAG_STRING);
                    // Calls a method
                    buffer.write(STRING_IO_UTF8, "name");
                    // Calls a method
                    buffer.write(STRING_IO_UTF8, score.name());

                    // Calls a method
                    buffer.write(BYTE, TAG_STRING);
                    // Calls a method
                    buffer.write(STRING_IO_UTF8, "objective");
                    // Calls a method
                    buffer.write(STRING_IO_UTF8, score.objective());
                // End of a block/expression
                }
                // Code statement
                buffer.write(BYTE, TAG_END); // End "score" tag

            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case SelectorComponent selector -> {
                // Calls a method
                buffer.write(STRING_IO_UTF8, "selector");

                // Calls a method
                buffer.write(BYTE, TAG_STRING);
                // Calls a method
                buffer.write(STRING_IO_UTF8, "selector");
                // Calls a method
                buffer.write(STRING_IO_UTF8, selector.pattern());

                // Calls a method
                final Component separator = selector.separator();
                // Branch: checks a condition
                if (separator != null) {
                    // Calls a method
                    buffer.write(BYTE, TAG_COMPOUND);
                    // Calls a method
                    buffer.write(STRING_IO_UTF8, "separator");
                    // Calls a method
                    writeInnerComponent(buffer, separator);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case KeybindComponent keybind -> {
                // Calls a method
                buffer.write(STRING_IO_UTF8, "keybind");

                // Calls a method
                buffer.write(BYTE, TAG_STRING);
                // Calls a method
                buffer.write(STRING_IO_UTF8, "keybind");
                // Calls a method
                buffer.write(STRING_IO_UTF8, keybind.keybind());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case NBTComponent<?> _ -> {
                //todo
                // Throws an exception
                throw new UnsupportedOperationException("NBTComponent is not implemented yet");
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case ObjectComponent object -> {
                // Calls a method
                buffer.write(STRING_IO_UTF8, "object");

                // Multiple branching (switch/case)
                switch (object.contents()) {
                    // Multiple branching (switch/case)
                    case SpriteObjectContents sprite -> {
                        // Branch: checks a condition
                        if (!sprite.atlas().equals(SpriteObjectContents.DEFAULT_ATLAS)) {
                            // Calls a method
                            buffer.write(BYTE, TAG_STRING);
                            // Calls a method
                            buffer.write(STRING_IO_UTF8, "atlas");
                            // Calls a method
                            buffer.write(STRING_IO_UTF8, sprite.atlas().asMinimalString());
                        // End of a block/expression
                        }

                        // Calls a method
                        buffer.write(BYTE, TAG_STRING);
                        // Calls a method
                        buffer.write(STRING_IO_UTF8, "sprite");
                        // Calls a method
                        buffer.write(STRING_IO_UTF8, sprite.sprite().asMinimalString());
                    // End of a block/expression
                    }
                    // Multiple branching (switch/case)
                    case PlayerHeadObjectContents player -> {
                        // Code statement
                        buffer.write(BYTE, TAG_COMPOUND); // Start "player" tag
                        // Calls a method
                        buffer.write(STRING_IO_UTF8, "player");
                        // Start of a block
                        {
                            // Calls a method
                            final String name = player.name();
                            // Branch: checks a condition
                            if (name != null) {
                                // Calls a method
                                buffer.write(BYTE, TAG_STRING);
                                // Calls a method
                                buffer.write(STRING_IO_UTF8, "name");
                                // Calls a method
                                buffer.write(STRING_IO_UTF8, name);
                            // End of a block/expression
                            }

                            // Calls a method
                            final UUID id = player.id();
                            // Branch: checks a condition
                            if (id != null) {
                                // Calls a method
                                buffer.write(BYTE, TAG_INT_ARRAY);
                                // Calls a method
                                buffer.write(STRING_IO_UTF8, "id");
                                // Calls a method
                                buffer.write(INT, 4);

                                // Calls a method
                                final long uuidMost = id.getMostSignificantBits();
                                // Calls a method
                                final long uuidLeast = id.getLeastSignificantBits();
                                // Calls a method
                                buffer.write(INT, (int) (uuidMost >> 32));
                                // Calls a method
                                buffer.write(INT, (int) uuidMost);
                                // Calls a method
                                buffer.write(INT, (int) (uuidLeast >> 32));
                                // Calls a method
                                buffer.write(INT, (int) uuidLeast);
                            // End of a block/expression
                            }

                            // Calls a method
                            int propertyCount = player.profileProperties().size();
                            // Branch: checks a condition
                            if (propertyCount > 0) {
                                // Calls a method
                                buffer.write(BYTE, TAG_LIST);
                                // Calls a method
                                buffer.write(STRING_IO_UTF8, "properties");
                                // Code statement
                                buffer.write(BYTE, TAG_COMPOUND); // List type
                                // Calls a method
                                buffer.write(INT, propertyCount);

                                // Loop: repeats a block
                                for (PlayerHeadObjectContents.ProfileProperty property : player.profileProperties()) {
                                    // Calls a method
                                    buffer.write(BYTE, TAG_STRING);
                                    // Calls a method
                                    buffer.write(STRING_IO_UTF8, "name");
                                    // Calls a method
                                    buffer.write(STRING_IO_UTF8, property.name());

                                    // Calls a method
                                    buffer.write(BYTE, TAG_STRING);
                                    // Calls a method
                                    buffer.write(STRING_IO_UTF8, "value");
                                    // Calls a method
                                    buffer.write(STRING_IO_UTF8, property.value());

                                    // Calls a method
                                    final String signature = property.signature();
                                    // Branch: checks a condition
                                    if (signature != null) {
                                        // Calls a method
                                        buffer.write(BYTE, TAG_STRING);
                                        // Calls a method
                                        buffer.write(STRING_IO_UTF8, "signature");
                                        // Calls a method
                                        buffer.write(STRING_IO_UTF8, signature);
                                    // End of a block/expression
                                    }

                                    // Code statement
                                    buffer.write(BYTE, TAG_END); // End property object
                                // End of a block/expression
                                }
                            // End of a block/expression
                            }

                            // Calls a method
                            final Key texture = player.texture();
                            // Branch: checks a condition
                            if (texture != null) {
                                // Calls a method
                                buffer.write(BYTE, TAG_STRING);
                                // Calls a method
                                buffer.write(STRING_IO_UTF8, "body");
                                // Calls a method
                                buffer.write(STRING_IO_UTF8, texture.asMinimalString());
                            // End of a block/expression
                            }
                        // End of a block/expression
                        }
                        // Code statement
                        buffer.write(BYTE, TAG_END); // End "player" tag

                        // Branch: checks a condition
                        if (!player.hat()) {
                            // Calls a method
                            buffer.write(BYTE, TAG_BYTE);
                            // Calls a method
                            buffer.write(STRING_IO_UTF8, "hat");
                            // Calls a method
                            buffer.write(BYTE, (byte) 0);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                    // Multiple branching (switch/case)
                    default -> throw new UnsupportedOperationException("Unknown object contents: " + object.contents());
                // End of a block/expression
                }
                // Calls a method
                var fallback = object.fallback();
                // Branch: checks a condition
                if (fallback != null) {
                    // Calls a method
                    buffer.write(BYTE, TAG_STRING);
                    // Calls a method
                    buffer.write(STRING_IO_UTF8, "fallback");
                    // Calls a method
                    writeInnerComponent(buffer, fallback);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            default -> throw new UnsupportedOperationException("Unsupported component type: " + component.getClass());
        // End of a block/expression
        }

        // Children
        // Branch: checks a condition
        if (!component.children().isEmpty()) {
            // Calls a method
            buffer.write(BYTE, TAG_LIST);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "extra");
            // Code statement
            buffer.write(BYTE, TAG_COMPOUND); // List type

            // Calls a method
            buffer.write(INT, component.children().size());
            // Loop: repeats a block
            for (final Component child : component.children())
                // Calls a method
                writeInnerComponent(buffer, child);
        // End of a block/expression
        }

        // Formatting/Interactivity
        // Calls a method
        writeComponentStyle(buffer, component.style());

        // Calls a method
        buffer.write(BYTE, TAG_END);
    // End of a block/expression
    }

    // Start of a method/block
    private void writeComponentStyle(NetworkBuffer buffer, Style style) {
        // Calls a method
        final TextColor color = style.color();
        // Branch: checks a condition
        if (color != null) {
            // Calls a method
            buffer.write(BYTE, TAG_STRING);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "color");
            // Branch: checks a condition
            if (color instanceof NamedTextColor namedColor)
                // Calls a method
                buffer.write(STRING_IO_UTF8, namedColor.name());
            // Alternative branch of the condition
            else buffer.write(STRING_IO_UTF8, color.asHexString());
        // End of a block/expression
        }

        // Calls a method
        final ShadowColor shadowColor = style.shadowColor();
        // Branch: checks a condition
        if (shadowColor != null) {
            // Calls a method
            buffer.write(BYTE, TAG_INT);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "shadow_color");
            // Calls a method
            buffer.write(INT, shadowColor.value());
        // End of a block/expression
        }

        // Calls a method
        final Key font = style.font();
        // Branch: checks a condition
        if (font != null) {
            // Calls a method
            buffer.write(BYTE, TAG_STRING);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "font");
            // Calls a method
            buffer.write(STRING_IO_UTF8, font.asString());
        // End of a block/expression
        }

        // Calls a method
        final TextDecoration.State bold = style.decoration(TextDecoration.BOLD);
        // Branch: checks a condition
        if (bold != TextDecoration.State.NOT_SET) {
            // Calls a method
            buffer.write(BYTE, TAG_BYTE);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "bold");
            // Calls a method
            buffer.write(BYTE, bold == TextDecoration.State.TRUE ? (byte) 1 : (byte) 0);
        // End of a block/expression
        }

        // Calls a method
        final TextDecoration.State italic = style.decoration(TextDecoration.ITALIC);
        // Branch: checks a condition
        if (italic != TextDecoration.State.NOT_SET) {
            // Calls a method
            buffer.write(BYTE, TAG_BYTE);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "italic");
            // Calls a method
            buffer.write(BYTE, italic == TextDecoration.State.TRUE ? (byte) 1 : (byte) 0);
        // End of a block/expression
        }

        // Calls a method
        final TextDecoration.State underlined = style.decoration(TextDecoration.UNDERLINED);
        // Branch: checks a condition
        if (underlined != TextDecoration.State.NOT_SET) {
            // Calls a method
            buffer.write(BYTE, TAG_BYTE);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "underlined");
            // Calls a method
            buffer.write(BYTE, underlined == TextDecoration.State.TRUE ? (byte) 1 : (byte) 0);
        // End of a block/expression
        }

        // Calls a method
        final TextDecoration.State strikethrough = style.decoration(TextDecoration.STRIKETHROUGH);
        // Branch: checks a condition
        if (strikethrough != TextDecoration.State.NOT_SET) {
            // Calls a method
            buffer.write(BYTE, TAG_BYTE);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "strikethrough");
            // Calls a method
            buffer.write(BYTE, strikethrough == TextDecoration.State.TRUE ? (byte) 1 : (byte) 0);
        // End of a block/expression
        }

        // Calls a method
        final TextDecoration.State obfuscated = style.decoration(TextDecoration.OBFUSCATED);
        // Branch: checks a condition
        if (obfuscated != TextDecoration.State.NOT_SET) {
            // Calls a method
            buffer.write(BYTE, TAG_BYTE);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "obfuscated");
            // Calls a method
            buffer.write(BYTE, obfuscated == TextDecoration.State.TRUE ? (byte) 1 : (byte) 0);
        // End of a block/expression
        }

        // Calls a method
        final String insertion = style.insertion();
        // Branch: checks a condition
        if (insertion != null) {
            // Calls a method
            buffer.write(BYTE, TAG_STRING);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "insertion");
            // Calls a method
            buffer.write(STRING_IO_UTF8, insertion);
        // End of a block/expression
        }

        // Calls a method
        final ClickEvent<?> clickEvent = style.clickEvent();
        // Branch: checks a condition
        if (clickEvent != null) writeClickEvent(buffer, clickEvent);

        // Calls a method
        final HoverEvent<?> hoverEvent = style.hoverEvent();
        // Branch: checks a condition
        if (hoverEvent != null) writeHoverEvent(buffer, hoverEvent);
    // End of a block/expression
    }

    // Start of a method/block
    private void writeClickEvent(NetworkBuffer buffer, ClickEvent<?> clickEvent) {
        // Calls a method
        buffer.write(BYTE, TAG_COMPOUND);
        // Calls a method
        buffer.write(STRING_IO_UTF8, "click_event");

        // Calls a method
        buffer.write(BYTE, TAG_STRING);
        // Calls a method
        buffer.write(STRING_IO_UTF8, "action");
        // Calls a method
        assert clickEvent.action().name().toLowerCase(Locale.ROOT).equals(clickEvent.action().name()) : "action is not lowercase";
        // Calls a method
        buffer.write(STRING_IO_UTF8, clickEvent.action().name());

        // Multiple branching (switch/case)
        switch (clickEvent.action()) {
            // Multiple branching (switch/case)
            case ClickEvent.Action.OpenUrl _ -> {
                // Calls a method
                final ClickEvent.Payload.Text payload = checkPayload(clickEvent, ClickEvent.Payload.Text.class);
                // Calls a method
                buffer.write(BYTE, TAG_STRING);
                // Calls a method
                buffer.write(STRING_IO_UTF8, "url");
                // Calls a method
                buffer.write(STRING_IO_UTF8, payload.value());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case ClickEvent.Action.OpenFile _ -> {
                // Calls a method
                final ClickEvent.Payload.Text payload = checkPayload(clickEvent, ClickEvent.Payload.Text.class);
                // Calls a method
                buffer.write(BYTE, TAG_STRING);
                // Calls a method
                buffer.write(STRING_IO_UTF8, "path");
                // Calls a method
                buffer.write(STRING_IO_UTF8, payload.value());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case ClickEvent.Action.RunCommand _, ClickEvent.Action.SuggestCommand _ -> {
                // Calls a method
                final ClickEvent.Payload.Text payload = checkPayload(clickEvent, ClickEvent.Payload.Text.class);
                // Calls a method
                buffer.write(BYTE, TAG_STRING);
                // Calls a method
                buffer.write(STRING_IO_UTF8, "command");
                // Calls a method
                buffer.write(STRING_IO_UTF8, payload.value());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case ClickEvent.Action.ChangePage _ -> {
                // Calls a method
                final ClickEvent.Payload.Int payload = checkPayload(clickEvent, ClickEvent.Payload.Int.class);
                // Calls a method
                buffer.write(BYTE, TAG_INT);
                // Calls a method
                buffer.write(STRING_IO_UTF8, "page");
                // Calls a method
                buffer.write(INT, payload.integer());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case ClickEvent.Action.CopyToClipboard _ -> {
                // Calls a method
                final ClickEvent.Payload.Text payload = checkPayload(clickEvent, ClickEvent.Payload.Text.class);
                // Calls a method
                buffer.write(BYTE, TAG_STRING);
                // Calls a method
                buffer.write(STRING_IO_UTF8, "value");
                // Calls a method
                buffer.write(STRING_IO_UTF8, payload.value());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case ClickEvent.Action.ShowDialog _ -> {
                // Calls a method
                final ClickEvent.Payload.Dialog payload = checkPayload(clickEvent, ClickEvent.Payload.Dialog.class);

                // Exception handling
                try {
                    // Assigns a value
                    final Transcoder<BinaryTag> coder = buffer.registries() != null
                            // Code statement
                            ? new RegistryTranscoder<>(Transcoder.NBT, buffer.registries())
                            // Code statement
                            : Transcoder.NBT;
                    // Calls a method
                    final BinaryTag dialog = Dialog.CODEC.encode(coder, Dialog.unwrap(payload.dialog())).orElseThrow();

                    // Calls a method
                    final BinaryTagWriter nbtWriter = impl(buffer).nbtWriter();
                    // Calls a method
                    nbtWriter.writeNamed("dialog", dialog);
                // Start of a method/block
                } catch (IOException e) {
                    // Throws an exception
                    throw new RuntimeException("Failed to write dialog click event payload", e);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case ClickEvent.Action.Custom _ -> {
                // Calls a method
                final ClickEvent.Payload.Custom payload = checkPayload(clickEvent, ClickEvent.Payload.Custom.class);
                // Calls a method
                buffer.write(BYTE, TAG_STRING);
                // Calls a method
                buffer.write(STRING_IO_UTF8, "id");
                // Calls a method
                buffer.write(STRING_IO_UTF8, payload.key().asString());

                // Exception handling
                try {
                    // Calls a method
                    final BinaryTagWriter nbtWriter = impl(buffer).nbtWriter();
                    // Calls a method
                    nbtWriter.writeNamed("payload", MinestomAdventure.unwrapNbt(payload.nbt()));
                // Start of a method/block
                } catch (IOException e) {
                    // Throws an exception
                    throw new RuntimeException("Failed to write custom click event payload", e);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            default -> throw new UnsupportedOperationException("Unknown click event action: " + clickEvent.action());
        // End of a block/expression
        }

        // Calls a method
        buffer.write(BYTE, TAG_END);
    // End of a block/expression
    }

    // Start of a method/block
    private <T extends ClickEvent.Payload> T checkPayload(ClickEvent<?> clickEvent, Class<T> expected) {
        // Calls a method
        final ClickEvent.Payload payload = clickEvent.payload();
        // Branch: checks a condition
        if (!expected.isInstance(payload))
            // Throws an exception
            throw new IllegalArgumentException(
                    // Calls a method
                    "Expected " + expected.getSimpleName() + " for " + clickEvent.action() + ", got: " + payload.getClass());
        // Returns a value to the caller
        return expected.cast(payload);
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    private void writeHoverEvent(NetworkBuffer buffer, HoverEvent<?> hoverEvent) {
        // Calls a method
        buffer.write(BYTE, TAG_COMPOUND);
        // Calls a method
        buffer.write(STRING_IO_UTF8, "hover_event");

        // Calls a method
        buffer.write(BYTE, TAG_STRING);
        // Calls a method
        buffer.write(STRING_IO_UTF8, "action");
        // Calls a method
        buffer.write(STRING_IO_UTF8, hoverEvent.action().toString().toLowerCase(Locale.ROOT));

        // Branch: checks a condition
        if (hoverEvent.action() == HoverEvent.Action.SHOW_TEXT) {
            // Calls a method
            buffer.write(BYTE, TAG_COMPOUND);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "value");
            // Calls a method
            writeInnerComponent(buffer, (Component) hoverEvent.value());
        // Branch: checks a condition
        } else if (hoverEvent.action() == HoverEvent.Action.SHOW_ITEM) {
            // Calls a method
            var value = ((HoverEvent<HoverEvent.ShowItem>) hoverEvent).value();

            // Calls a method
            buffer.write(BYTE, TAG_STRING);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "id");
            // Calls a method
            buffer.write(STRING_IO_UTF8, value.item().asString());

            // Calls a method
            buffer.write(BYTE, TAG_INT);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "count");
            // Calls a method
            buffer.write(INT, value.count());

            // Calls a method
            buffer.write(BYTE, TAG_COMPOUND);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "components");
            // Calls a method
            final Map<Key, NbtDataComponentValue> dataComponents = value.dataComponentsAs(NbtDataComponentValue.class);
            // Branch: checks a condition
            if (!dataComponents.isEmpty()) {
                // Calls a method
                final BinaryTagWriter nbtWriter = impl(buffer).nbtWriter();
                // Exception handling
                try {
                    // Loop: repeats a block
                    for (final Map.Entry<Key, NbtDataComponentValue> entry : dataComponents.entrySet()) {
                        // Calls a method
                        final BinaryTag dataComponentValue = entry.getValue().value();
                        // Branch: checks a condition
                        if (dataComponentValue == null) {
                            // Calls a method
                            buffer.write(BYTE, TAG_COMPOUND);
                            // Calls a method
                            buffer.write(STRING_IO_UTF8, "!" + entry.getKey().asString());
                            // Calls a method
                            buffer.write(BYTE, TAG_END);
                        // Alternative branch of the condition
                        } else {
                            // Calls a method
                            nbtWriter.writeNamed(entry.getKey().asString(), dataComponentValue);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // Start of a method/block
                } catch (IOException e) {
                    // Throws an exception
                    throw new RuntimeException(e);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Calls a method
            buffer.write(BYTE, TAG_END);
        // Branch: checks a condition
        } else if (hoverEvent.action() == HoverEvent.Action.SHOW_ENTITY) {
            // Calls a method
            var value = ((HoverEvent<HoverEvent.ShowEntity>) hoverEvent).value();

            // Calls a method
            final Component name = value.name();
            // Branch: checks a condition
            if (name != null) {
                // Calls a method
                buffer.write(BYTE, TAG_COMPOUND);
                // Calls a method
                buffer.write(STRING_IO_UTF8, "name");
                // Calls a method
                writeInnerComponent(buffer, name);
            // End of a block/expression
            }

            // Calls a method
            buffer.write(BYTE, TAG_STRING);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "id");
            // Calls a method
            buffer.write(STRING_IO_UTF8, value.type().asString());

            // Calls a method
            buffer.write(BYTE, TAG_STRING);
            // Calls a method
            buffer.write(STRING_IO_UTF8, "uuid");
            // Calls a method
            buffer.write(STRING_IO_UTF8, value.id().toString());
        // Alternative branch of the condition
        } else {
            // Throws an exception
            throw new UnsupportedOperationException("Unknown hover event action: " + hoverEvent.action());
        // End of a block/expression
        }

        // Calls a method
        buffer.write(BYTE, TAG_END);
    // End of a block/expression
    }
// End of a block/expression
}
