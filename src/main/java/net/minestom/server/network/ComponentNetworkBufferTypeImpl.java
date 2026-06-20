// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.*;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.ClickEvent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEvent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.*;
// Import d'une classe nécessaire
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
// Import d'une classe nécessaire
import net.kyori.adventure.text.object.SpriteObjectContents;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.adventure.serializer.nbt.NbtDataComponentValue;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.dialog.Dialog;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;
// Import d'une classe nécessaire
import net.minestom.server.utils.nbt.BinaryTagWriter;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.util.*;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBufferImpl.impl;

// Déclaration de type (classe/interface/enum/record)
record ComponentNetworkBufferTypeImpl() implements NetworkBufferTypeImpl<Component> {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void write(NetworkBuffer buffer, Component value) {
        // Appelle une méthode
        Objects.requireNonNull(value, "Component cannot be null");

        // Appelle une méthode
        buffer.write(BYTE, TAG_COMPOUND);
        // Appelle une méthode
        writeInnerComponent(buffer, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Component read(NetworkBuffer buffer) {
        // Affecte une valeur
        final Transcoder<BinaryTag> coder = buffer.registries() != null
                // Instruction de code
                ? new RegistryTranscoder<>(Transcoder.NBT, buffer.registries())
                // Instruction de code
                : Transcoder.NBT;
        // Renvoie une valeur à l'appelant
        return Codec.COMPONENT.decode(coder, buffer.read(NBT)).orElseThrow();
    // Fin d'un bloc/d'une expression
    }

    // WRITING IMPL, pretty gross. Would not recommend reading.

    // Affecte une valeur
    private static final byte TAG_END = 0;
    // Affecte une valeur
    private static final byte TAG_BYTE = 1;
    // Affecte une valeur
    private static final byte TAG_INT = 3;
    // Affecte une valeur
    private static final byte TAG_STRING = 8;
    // Affecte une valeur
    private static final byte TAG_LIST = 9;
    // Affecte une valeur
    private static final byte TAG_COMPOUND = 10;
    // Affecte une valeur
    private static final byte TAG_INT_ARRAY = 11;

    // Début d'une méthode/d'un bloc
    private void writeInnerComponent(NetworkBuffer buffer, Component component) {
        // Instruction de code
        buffer.write(BYTE, TAG_STRING); // Start first tag (always the type)
        // Appelle une méthode
        buffer.write(STRING_IO_UTF8, "type");
        // Embranchement multiple (switch/case)
        switch (component) {
            // Embranchement multiple (switch/case)
            case TextComponent text -> {
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "text");

                // Instruction de code
                buffer.write(BYTE, TAG_STRING); // Start "text" tag
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "text");
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, text.content());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case TranslatableComponent translatable -> {
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "translatable");

                // Instruction de code
                buffer.write(BYTE, TAG_STRING); // Start "translate" tag
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "translate");
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, translatable.key());

                // Appelle une méthode
                final String fallback = translatable.fallback();
                // Embranchement : vérifie une condition
                if (fallback != null) {
                    // Appelle une méthode
                    buffer.write(BYTE, TAG_STRING);
                    // Appelle une méthode
                    buffer.write(STRING_IO_UTF8, "fallback");
                    // Appelle une méthode
                    buffer.write(STRING_IO_UTF8, fallback);
                // Fin d'un bloc/d'une expression
                }

                // Appelle une méthode
                final List<TranslationArgument> args = translatable.arguments();
                // Embranchement : vérifie une condition
                if (!args.isEmpty()) {
                    // Appelle une méthode
                    buffer.write(BYTE, TAG_LIST);
                    // Appelle une méthode
                    buffer.write(STRING_IO_UTF8, "with");
                    // Instruction de code
                    buffer.write(BYTE, TAG_COMPOUND); // List type
                    // Appelle une méthode
                    buffer.write(INT, args.size());
                    // Boucle : répète un bloc
                    for (final TranslationArgument arg : args)
                        // Appelle une méthode
                        writeInnerComponent(buffer, arg.asComponent());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case ScoreComponent score -> {
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "score");

                // Instruction de code
                buffer.write(BYTE, TAG_COMPOUND); // Start "score" tag
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "score");
                // Début d'un bloc
                {
                    // Appelle une méthode
                    buffer.write(BYTE, TAG_STRING);
                    // Appelle une méthode
                    buffer.write(STRING_IO_UTF8, "name");
                    // Appelle une méthode
                    buffer.write(STRING_IO_UTF8, score.name());

                    // Appelle une méthode
                    buffer.write(BYTE, TAG_STRING);
                    // Appelle une méthode
                    buffer.write(STRING_IO_UTF8, "objective");
                    // Appelle une méthode
                    buffer.write(STRING_IO_UTF8, score.objective());
                // Fin d'un bloc/d'une expression
                }
                // Instruction de code
                buffer.write(BYTE, TAG_END); // End "score" tag

            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case SelectorComponent selector -> {
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "selector");

                // Appelle une méthode
                buffer.write(BYTE, TAG_STRING);
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "selector");
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, selector.pattern());

                // Appelle une méthode
                final Component separator = selector.separator();
                // Embranchement : vérifie une condition
                if (separator != null) {
                    // Appelle une méthode
                    buffer.write(BYTE, TAG_COMPOUND);
                    // Appelle une méthode
                    buffer.write(STRING_IO_UTF8, "separator");
                    // Appelle une méthode
                    writeInnerComponent(buffer, separator);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case KeybindComponent keybind -> {
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "keybind");

                // Appelle une méthode
                buffer.write(BYTE, TAG_STRING);
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "keybind");
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, keybind.keybind());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case NBTComponent<?> _ -> {
                //todo
                // Lève une exception
                throw new UnsupportedOperationException("NBTComponent is not implemented yet");
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case ObjectComponent object -> {
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "object");

                // Embranchement multiple (switch/case)
                switch (object.contents()) {
                    // Embranchement multiple (switch/case)
                    case SpriteObjectContents sprite -> {
                        // Embranchement : vérifie une condition
                        if (!sprite.atlas().equals(SpriteObjectContents.DEFAULT_ATLAS)) {
                            // Appelle une méthode
                            buffer.write(BYTE, TAG_STRING);
                            // Appelle une méthode
                            buffer.write(STRING_IO_UTF8, "atlas");
                            // Appelle une méthode
                            buffer.write(STRING_IO_UTF8, sprite.atlas().asMinimalString());
                        // Fin d'un bloc/d'une expression
                        }

                        // Appelle une méthode
                        buffer.write(BYTE, TAG_STRING);
                        // Appelle une méthode
                        buffer.write(STRING_IO_UTF8, "sprite");
                        // Appelle une méthode
                        buffer.write(STRING_IO_UTF8, sprite.sprite().asMinimalString());
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement multiple (switch/case)
                    case PlayerHeadObjectContents player -> {
                        // Instruction de code
                        buffer.write(BYTE, TAG_COMPOUND); // Start "player" tag
                        // Appelle une méthode
                        buffer.write(STRING_IO_UTF8, "player");
                        // Début d'un bloc
                        {
                            // Appelle une méthode
                            final String name = player.name();
                            // Embranchement : vérifie une condition
                            if (name != null) {
                                // Appelle une méthode
                                buffer.write(BYTE, TAG_STRING);
                                // Appelle une méthode
                                buffer.write(STRING_IO_UTF8, "name");
                                // Appelle une méthode
                                buffer.write(STRING_IO_UTF8, name);
                            // Fin d'un bloc/d'une expression
                            }

                            // Appelle une méthode
                            final UUID id = player.id();
                            // Embranchement : vérifie une condition
                            if (id != null) {
                                // Appelle une méthode
                                buffer.write(BYTE, TAG_INT_ARRAY);
                                // Appelle une méthode
                                buffer.write(STRING_IO_UTF8, "id");
                                // Appelle une méthode
                                buffer.write(INT, 4);

                                // Appelle une méthode
                                final long uuidMost = id.getMostSignificantBits();
                                // Appelle une méthode
                                final long uuidLeast = id.getLeastSignificantBits();
                                // Appelle une méthode
                                buffer.write(INT, (int) (uuidMost >> 32));
                                // Appelle une méthode
                                buffer.write(INT, (int) uuidMost);
                                // Appelle une méthode
                                buffer.write(INT, (int) (uuidLeast >> 32));
                                // Appelle une méthode
                                buffer.write(INT, (int) uuidLeast);
                            // Fin d'un bloc/d'une expression
                            }

                            // Appelle une méthode
                            int propertyCount = player.profileProperties().size();
                            // Embranchement : vérifie une condition
                            if (propertyCount > 0) {
                                // Appelle une méthode
                                buffer.write(BYTE, TAG_LIST);
                                // Appelle une méthode
                                buffer.write(STRING_IO_UTF8, "properties");
                                // Instruction de code
                                buffer.write(BYTE, TAG_COMPOUND); // List type
                                // Appelle une méthode
                                buffer.write(INT, propertyCount);

                                // Boucle : répète un bloc
                                for (PlayerHeadObjectContents.ProfileProperty property : player.profileProperties()) {
                                    // Appelle une méthode
                                    buffer.write(BYTE, TAG_STRING);
                                    // Appelle une méthode
                                    buffer.write(STRING_IO_UTF8, "name");
                                    // Appelle une méthode
                                    buffer.write(STRING_IO_UTF8, property.name());

                                    // Appelle une méthode
                                    buffer.write(BYTE, TAG_STRING);
                                    // Appelle une méthode
                                    buffer.write(STRING_IO_UTF8, "value");
                                    // Appelle une méthode
                                    buffer.write(STRING_IO_UTF8, property.value());

                                    // Appelle une méthode
                                    final String signature = property.signature();
                                    // Embranchement : vérifie une condition
                                    if (signature != null) {
                                        // Appelle une méthode
                                        buffer.write(BYTE, TAG_STRING);
                                        // Appelle une méthode
                                        buffer.write(STRING_IO_UTF8, "signature");
                                        // Appelle une méthode
                                        buffer.write(STRING_IO_UTF8, signature);
                                    // Fin d'un bloc/d'une expression
                                    }

                                    // Instruction de code
                                    buffer.write(BYTE, TAG_END); // End property object
                                // Fin d'un bloc/d'une expression
                                }
                            // Fin d'un bloc/d'une expression
                            }

                            // Appelle une méthode
                            final Key texture = player.texture();
                            // Embranchement : vérifie une condition
                            if (texture != null) {
                                // Appelle une méthode
                                buffer.write(BYTE, TAG_STRING);
                                // Appelle une méthode
                                buffer.write(STRING_IO_UTF8, "body");
                                // Appelle une méthode
                                buffer.write(STRING_IO_UTF8, texture.asMinimalString());
                            // Fin d'un bloc/d'une expression
                            }
                        // Fin d'un bloc/d'une expression
                        }
                        // Instruction de code
                        buffer.write(BYTE, TAG_END); // End "player" tag

                        // Embranchement : vérifie une condition
                        if (!player.hat()) {
                            // Appelle une méthode
                            buffer.write(BYTE, TAG_BYTE);
                            // Appelle une méthode
                            buffer.write(STRING_IO_UTF8, "hat");
                            // Appelle une méthode
                            buffer.write(BYTE, (byte) 0);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement multiple (switch/case)
                    default -> throw new UnsupportedOperationException("Unknown object contents: " + object.contents());
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                var fallback = object.fallback();
                // Embranchement : vérifie une condition
                if (fallback != null) {
                    // Appelle une méthode
                    buffer.write(BYTE, TAG_STRING);
                    // Appelle une méthode
                    buffer.write(STRING_IO_UTF8, "fallback");
                    // Appelle une méthode
                    writeInnerComponent(buffer, fallback);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            default -> throw new UnsupportedOperationException("Unsupported component type: " + component.getClass());
        // Fin d'un bloc/d'une expression
        }

        // Children
        // Embranchement : vérifie une condition
        if (!component.children().isEmpty()) {
            // Appelle une méthode
            buffer.write(BYTE, TAG_LIST);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "extra");
            // Instruction de code
            buffer.write(BYTE, TAG_COMPOUND); // List type

            // Appelle une méthode
            buffer.write(INT, component.children().size());
            // Boucle : répète un bloc
            for (final Component child : component.children())
                // Appelle une méthode
                writeInnerComponent(buffer, child);
        // Fin d'un bloc/d'une expression
        }

        // Formatting/Interactivity
        // Appelle une méthode
        writeComponentStyle(buffer, component.style());

        // Appelle une méthode
        buffer.write(BYTE, TAG_END);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void writeComponentStyle(NetworkBuffer buffer, Style style) {
        // Appelle une méthode
        final TextColor color = style.color();
        // Embranchement : vérifie une condition
        if (color != null) {
            // Appelle une méthode
            buffer.write(BYTE, TAG_STRING);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "color");
            // Embranchement : vérifie une condition
            if (color instanceof NamedTextColor namedColor)
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, namedColor.name());
            // Branche alternative de la condition
            else buffer.write(STRING_IO_UTF8, color.asHexString());
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final ShadowColor shadowColor = style.shadowColor();
        // Embranchement : vérifie une condition
        if (shadowColor != null) {
            // Appelle une méthode
            buffer.write(BYTE, TAG_INT);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "shadow_color");
            // Appelle une méthode
            buffer.write(INT, shadowColor.value());
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final Key font = style.font();
        // Embranchement : vérifie une condition
        if (font != null) {
            // Appelle une méthode
            buffer.write(BYTE, TAG_STRING);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "font");
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, font.asString());
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final TextDecoration.State bold = style.decoration(TextDecoration.BOLD);
        // Embranchement : vérifie une condition
        if (bold != TextDecoration.State.NOT_SET) {
            // Appelle une méthode
            buffer.write(BYTE, TAG_BYTE);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "bold");
            // Appelle une méthode
            buffer.write(BYTE, bold == TextDecoration.State.TRUE ? (byte) 1 : (byte) 0);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final TextDecoration.State italic = style.decoration(TextDecoration.ITALIC);
        // Embranchement : vérifie une condition
        if (italic != TextDecoration.State.NOT_SET) {
            // Appelle une méthode
            buffer.write(BYTE, TAG_BYTE);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "italic");
            // Appelle une méthode
            buffer.write(BYTE, italic == TextDecoration.State.TRUE ? (byte) 1 : (byte) 0);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final TextDecoration.State underlined = style.decoration(TextDecoration.UNDERLINED);
        // Embranchement : vérifie une condition
        if (underlined != TextDecoration.State.NOT_SET) {
            // Appelle une méthode
            buffer.write(BYTE, TAG_BYTE);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "underlined");
            // Appelle une méthode
            buffer.write(BYTE, underlined == TextDecoration.State.TRUE ? (byte) 1 : (byte) 0);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final TextDecoration.State strikethrough = style.decoration(TextDecoration.STRIKETHROUGH);
        // Embranchement : vérifie une condition
        if (strikethrough != TextDecoration.State.NOT_SET) {
            // Appelle une méthode
            buffer.write(BYTE, TAG_BYTE);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "strikethrough");
            // Appelle une méthode
            buffer.write(BYTE, strikethrough == TextDecoration.State.TRUE ? (byte) 1 : (byte) 0);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final TextDecoration.State obfuscated = style.decoration(TextDecoration.OBFUSCATED);
        // Embranchement : vérifie une condition
        if (obfuscated != TextDecoration.State.NOT_SET) {
            // Appelle une méthode
            buffer.write(BYTE, TAG_BYTE);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "obfuscated");
            // Appelle une méthode
            buffer.write(BYTE, obfuscated == TextDecoration.State.TRUE ? (byte) 1 : (byte) 0);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final String insertion = style.insertion();
        // Embranchement : vérifie une condition
        if (insertion != null) {
            // Appelle une méthode
            buffer.write(BYTE, TAG_STRING);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "insertion");
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, insertion);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final ClickEvent<?> clickEvent = style.clickEvent();
        // Embranchement : vérifie une condition
        if (clickEvent != null) writeClickEvent(buffer, clickEvent);

        // Appelle une méthode
        final HoverEvent<?> hoverEvent = style.hoverEvent();
        // Embranchement : vérifie une condition
        if (hoverEvent != null) writeHoverEvent(buffer, hoverEvent);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void writeClickEvent(NetworkBuffer buffer, ClickEvent<?> clickEvent) {
        // Appelle une méthode
        buffer.write(BYTE, TAG_COMPOUND);
        // Appelle une méthode
        buffer.write(STRING_IO_UTF8, "click_event");

        // Appelle une méthode
        buffer.write(BYTE, TAG_STRING);
        // Appelle une méthode
        buffer.write(STRING_IO_UTF8, "action");
        // Appelle une méthode
        assert clickEvent.action().name().toLowerCase(Locale.ROOT).equals(clickEvent.action().name()) : "action is not lowercase";
        // Appelle une méthode
        buffer.write(STRING_IO_UTF8, clickEvent.action().name());

        // Embranchement multiple (switch/case)
        switch (clickEvent.action()) {
            // Embranchement multiple (switch/case)
            case ClickEvent.Action.OpenUrl _ -> {
                // Appelle une méthode
                final ClickEvent.Payload.Text payload = checkPayload(clickEvent, ClickEvent.Payload.Text.class);
                // Appelle une méthode
                buffer.write(BYTE, TAG_STRING);
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "url");
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, payload.value());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case ClickEvent.Action.OpenFile _ -> {
                // Appelle une méthode
                final ClickEvent.Payload.Text payload = checkPayload(clickEvent, ClickEvent.Payload.Text.class);
                // Appelle une méthode
                buffer.write(BYTE, TAG_STRING);
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "path");
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, payload.value());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case ClickEvent.Action.RunCommand _, ClickEvent.Action.SuggestCommand _ -> {
                // Appelle une méthode
                final ClickEvent.Payload.Text payload = checkPayload(clickEvent, ClickEvent.Payload.Text.class);
                // Appelle une méthode
                buffer.write(BYTE, TAG_STRING);
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "command");
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, payload.value());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case ClickEvent.Action.ChangePage _ -> {
                // Appelle une méthode
                final ClickEvent.Payload.Int payload = checkPayload(clickEvent, ClickEvent.Payload.Int.class);
                // Appelle une méthode
                buffer.write(BYTE, TAG_INT);
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "page");
                // Appelle une méthode
                buffer.write(INT, payload.integer());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case ClickEvent.Action.CopyToClipboard _ -> {
                // Appelle une méthode
                final ClickEvent.Payload.Text payload = checkPayload(clickEvent, ClickEvent.Payload.Text.class);
                // Appelle une méthode
                buffer.write(BYTE, TAG_STRING);
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "value");
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, payload.value());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case ClickEvent.Action.ShowDialog _ -> {
                // Appelle une méthode
                final ClickEvent.Payload.Dialog payload = checkPayload(clickEvent, ClickEvent.Payload.Dialog.class);

                // Gestion des exceptions
                try {
                    // Affecte une valeur
                    final Transcoder<BinaryTag> coder = buffer.registries() != null
                            // Instruction de code
                            ? new RegistryTranscoder<>(Transcoder.NBT, buffer.registries())
                            // Instruction de code
                            : Transcoder.NBT;
                    // Appelle une méthode
                    final BinaryTag dialog = Dialog.CODEC.encode(coder, Dialog.unwrap(payload.dialog())).orElseThrow();

                    // Appelle une méthode
                    final BinaryTagWriter nbtWriter = impl(buffer).nbtWriter();
                    // Appelle une méthode
                    nbtWriter.writeNamed("dialog", dialog);
                // Début d'une méthode/d'un bloc
                } catch (IOException e) {
                    // Lève une exception
                    throw new RuntimeException("Failed to write dialog click event payload", e);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case ClickEvent.Action.Custom _ -> {
                // Appelle une méthode
                final ClickEvent.Payload.Custom payload = checkPayload(clickEvent, ClickEvent.Payload.Custom.class);
                // Appelle une méthode
                buffer.write(BYTE, TAG_STRING);
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "id");
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, payload.key().asString());

                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    final BinaryTagWriter nbtWriter = impl(buffer).nbtWriter();
                    // Appelle une méthode
                    nbtWriter.writeNamed("payload", MinestomAdventure.unwrapNbt(payload.nbt()));
                // Début d'une méthode/d'un bloc
                } catch (IOException e) {
                    // Lève une exception
                    throw new RuntimeException("Failed to write custom click event payload", e);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            default -> throw new UnsupportedOperationException("Unknown click event action: " + clickEvent.action());
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        buffer.write(BYTE, TAG_END);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private <T extends ClickEvent.Payload> T checkPayload(ClickEvent<?> clickEvent, Class<T> expected) {
        // Appelle une méthode
        final ClickEvent.Payload payload = clickEvent.payload();
        // Embranchement : vérifie une condition
        if (!expected.isInstance(payload))
            // Lève une exception
            throw new IllegalArgumentException(
                    // Appelle une méthode
                    "Expected " + expected.getSimpleName() + " for " + clickEvent.action() + ", got: " + payload.getClass());
        // Renvoie une valeur à l'appelant
        return expected.cast(payload);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    private void writeHoverEvent(NetworkBuffer buffer, HoverEvent<?> hoverEvent) {
        // Appelle une méthode
        buffer.write(BYTE, TAG_COMPOUND);
        // Appelle une méthode
        buffer.write(STRING_IO_UTF8, "hover_event");

        // Appelle une méthode
        buffer.write(BYTE, TAG_STRING);
        // Appelle une méthode
        buffer.write(STRING_IO_UTF8, "action");
        // Appelle une méthode
        buffer.write(STRING_IO_UTF8, hoverEvent.action().toString().toLowerCase(Locale.ROOT));

        // Embranchement : vérifie une condition
        if (hoverEvent.action() == HoverEvent.Action.SHOW_TEXT) {
            // Appelle une méthode
            buffer.write(BYTE, TAG_COMPOUND);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "value");
            // Appelle une méthode
            writeInnerComponent(buffer, (Component) hoverEvent.value());
        // Embranchement : vérifie une condition
        } else if (hoverEvent.action() == HoverEvent.Action.SHOW_ITEM) {
            // Appelle une méthode
            var value = ((HoverEvent<HoverEvent.ShowItem>) hoverEvent).value();

            // Appelle une méthode
            buffer.write(BYTE, TAG_STRING);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "id");
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, value.item().asString());

            // Appelle une méthode
            buffer.write(BYTE, TAG_INT);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "count");
            // Appelle une méthode
            buffer.write(INT, value.count());

            // Appelle une méthode
            buffer.write(BYTE, TAG_COMPOUND);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "components");
            // Appelle une méthode
            final Map<Key, NbtDataComponentValue> dataComponents = value.dataComponentsAs(NbtDataComponentValue.class);
            // Embranchement : vérifie une condition
            if (!dataComponents.isEmpty()) {
                // Appelle une méthode
                final BinaryTagWriter nbtWriter = impl(buffer).nbtWriter();
                // Gestion des exceptions
                try {
                    // Boucle : répète un bloc
                    for (final Map.Entry<Key, NbtDataComponentValue> entry : dataComponents.entrySet()) {
                        // Appelle une méthode
                        final BinaryTag dataComponentValue = entry.getValue().value();
                        // Embranchement : vérifie une condition
                        if (dataComponentValue == null) {
                            // Appelle une méthode
                            buffer.write(BYTE, TAG_COMPOUND);
                            // Appelle une méthode
                            buffer.write(STRING_IO_UTF8, "!" + entry.getKey().asString());
                            // Appelle une méthode
                            buffer.write(BYTE, TAG_END);
                        // Branche alternative de la condition
                        } else {
                            // Appelle une méthode
                            nbtWriter.writeNamed(entry.getKey().asString(), dataComponentValue);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Début d'une méthode/d'un bloc
                } catch (IOException e) {
                    // Lève une exception
                    throw new RuntimeException(e);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            buffer.write(BYTE, TAG_END);
        // Embranchement : vérifie une condition
        } else if (hoverEvent.action() == HoverEvent.Action.SHOW_ENTITY) {
            // Appelle une méthode
            var value = ((HoverEvent<HoverEvent.ShowEntity>) hoverEvent).value();

            // Appelle une méthode
            final Component name = value.name();
            // Embranchement : vérifie une condition
            if (name != null) {
                // Appelle une méthode
                buffer.write(BYTE, TAG_COMPOUND);
                // Appelle une méthode
                buffer.write(STRING_IO_UTF8, "name");
                // Appelle une méthode
                writeInnerComponent(buffer, name);
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            buffer.write(BYTE, TAG_STRING);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "id");
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, value.type().asString());

            // Appelle une méthode
            buffer.write(BYTE, TAG_STRING);
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, "uuid");
            // Appelle une méthode
            buffer.write(STRING_IO_UTF8, value.id().toString());
        // Branche alternative de la condition
        } else {
            // Lève une exception
            throw new UnsupportedOperationException("Unknown hover event action: " + hoverEvent.action());
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        buffer.write(BYTE, TAG_END);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
