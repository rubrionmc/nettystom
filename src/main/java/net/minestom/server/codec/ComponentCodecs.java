// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.text.*;
// Import of a required class
import net.kyori.adventure.text.event.ClickEvent;
// Import of a required class
import net.kyori.adventure.text.event.HoverEvent;
// Import of a required class
import net.kyori.adventure.text.format.*;
// Import of a required class
import net.kyori.adventure.text.object.ObjectContents;
// Import of a required class
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
// Import of a required class
import net.kyori.adventure.text.object.SpriteObjectContents;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.codec.Transcoder.MapBuilder;
// Import of a required class
import net.minestom.server.codec.Transcoder.MapLike;
// Import of a required class
import net.minestom.server.dialog.Dialog;
// Import of a required class
import net.minestom.server.network.player.ResolvableProfile;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

/**
 * Used internally to hold component codecs
 */
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class ComponentCodecs {
    // Code statement
    private ComponentCodecs() {}
    // Very gross :|
    // Calls a method
    private static final Codec<Component> COMPONENT_FORWARD = Codec.ForwardRef(() -> Codec.COMPONENT);

    // Assigns a value
    public static final Codec<TextColor> TEXT_COLOR = new Codec<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<TextColor> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<String> colorResult = coder.getString(value);
            // Branch: checks a condition
            if (!(colorResult instanceof Result.Ok(String colorString)))
                // Returns a value to the caller
                return colorResult.cast();
            // Branch: checks a condition
            if (colorString.startsWith("#")) {
                // Calls a method
                final TextColor color = TextColor.fromHexString(colorString);
                // Branch: checks a condition
                if (color == null) return new Result.Error<>("Unknown color: " + colorString);
                // Returns a value to the caller
                return new Result.Ok<>(color);
            // End of a block/expression
            }
            // Calls a method
            final NamedTextColor namedColor = NamedTextColor.NAMES.value(colorString);
            // Branch: checks a condition
            if (namedColor == null) return new Result.Error<>("Unknown color: " + colorString);
            // Returns a value to the caller
            return new Result.Ok<>(namedColor);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable TextColor value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Branch: checks a condition
            if (value instanceof NamedTextColor namedColor)
                // Returns a value to the caller
                return new Result.Ok<>(coder.createString(namedColor.name()));
            // Returns a value to the caller
            return new Result.Ok<>(coder.createString(value.asHexString()));
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Calls a method
    public static final Codec<ShadowColor> SHADOW_COLOR = Codec.INT.transform(ShadowColor::shadowColor, ShadowColor::value);

    // Start of a method/block
    private static @Nullable Boolean stateToBool(TextDecoration.State state) {
        // Returns a value to the caller
        return switch (state) {
            // Multiple branching (switch/case)
            case NOT_SET -> null;
            // Multiple branching (switch/case)
            case FALSE -> false;
            // Multiple branching (switch/case)
            case TRUE -> true;
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static List<Component> extractTranslatableComponents(final TranslatableComponent component) {
        // Calls a method
        final List<TranslationArgument> arguments = component.arguments();
        // Branch: checks a condition
        if (arguments.isEmpty()) return List.of();
        // Calls a method
        Component[] components = new Component[arguments.size()];
        // Loop: repeats a block
        for (int i = 0; i < components.length; i++) {
            // Calls a method
            components[i] = arguments.get(i).asComponent();
        // End of a block/expression
        }
        // Returns a value to the caller
        return List.of(components);
    // End of a block/expression
    }

    // Assigns a value
    public static final StructCodec<ClickEvent<?>> CLICK_EVENT = new StructCodec<>() {
        // Calls a method
        private static final Codec<ClickEvent.Action<?>> ACTION_CODEC = Codec.STRING.transform(ClickEvent.Action.NAMES::value, ClickEvent.Action::name);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<ClickEvent<?>> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
            // Calls a method
            final Result<ClickEvent.Action<?>> actionResult = map.getValue("action").map(value -> ACTION_CODEC.decode(coder, value));
            // Branch: checks a condition
            if (!(actionResult instanceof Result.Ok(var action)))
                // Returns a value to the caller
                return actionResult.cast();

            // Returns a value to the caller
            return switch (action) {
                // Multiple branching (switch/case)
                case ClickEvent.Action.OpenUrl _ -> map.getValue("url").map(value -> Codec.STRING.decode(coder, value))
                        // Calls a method
                        .mapResult(ClickEvent::openUrl);
                // Multiple branching (switch/case)
                case ClickEvent.Action.OpenFile _ -> map.getValue("path").map(value -> Codec.STRING.decode(coder, value))
                        // Calls a method
                        .mapResult(ClickEvent::openFile);
                // Multiple branching (switch/case)
                case ClickEvent.Action.RunCommand _ -> map.getValue("command").map(value -> Codec.STRING.decode(coder, value))
                        // Calls a method
                        .mapResult(ClickEvent::runCommand);
                // Multiple branching (switch/case)
                case ClickEvent.Action.SuggestCommand _ -> map.getValue("command").map(value -> Codec.STRING.decode(coder, value))
                        // Calls a method
                        .mapResult(ClickEvent::suggestCommand);
                // Multiple branching (switch/case)
                case ClickEvent.Action.ChangePage _ -> map.getValue("page").map(value -> Codec.INT.decode(coder, value))
                        // Calls a method
                        .mapResult(ClickEvent::changePage);
                // Multiple branching (switch/case)
                case ClickEvent.Action.CopyToClipboard _ -> map.getValue("value").map(value -> Codec.STRING.decode(coder, value))
                        // Calls a method
                        .mapResult(ClickEvent::copyToClipboard);
                // Multiple branching (switch/case)
                case ClickEvent.Action.ShowDialog _ -> map.getValue("dialog").map(value -> Dialog.CODEC.decode(coder, value))
                        // Calls a method
                        .mapResult(dialog -> ClickEvent.showDialog(Dialog.wrap(dialog)));
                // Multiple branching (switch/case)
                case ClickEvent.Action.Custom _ -> {
                    // Calls a method
                    final Result<Key> idResult = map.getValue("id").map(value -> Codec.KEY.decode(coder, value));
                    // Branch: checks a condition
                    if (!(idResult instanceof Result.Ok(Key id)))
                        // Calls a method
                        yield idResult.cast();

                    // Assigns a value
                    BinaryTag payload = CompoundBinaryTag.empty(); // Default to empty. It is optional technically, but adventure does not support that.
                    // Branch: checks a condition
                    if (map.hasValue("payload")) {
                        // Assigns a value
                        final Result<BinaryTag> payloadResult = map.getValue("payload")
                                // Code statement
                                .map(value -> Codec.RAW_VALUE.decode(coder, value))
                                // Calls a method
                                .map(value -> value.convertTo(Transcoder.NBT));
                        // Branch: checks a condition
                        if (!(payloadResult instanceof Result.Ok(BinaryTag rawValue)))
                            // Calls a method
                            yield payloadResult.cast();
                        // Assigns a value
                        payload = rawValue;
                    // End of a block/expression
                    }

                    // Calls a method
                    yield new Result.Ok<>(ClickEvent.custom(id, MinestomAdventure.wrapNbt(payload)));
                // End of a block/expression
                }
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encodeToMap(Transcoder<D> coder, ClickEvent<?> value, MapBuilder<D> map) {
            // Calls a method
            final Result<D> actionResult = ACTION_CODEC.encode(coder, value.action());
            // Branch: checks a condition
            if (!(actionResult instanceof Result.Ok(D actionValue)))
                // Returns a value to the caller
                return actionResult.cast();
            // Calls a method
            map.put("action", actionValue);

            // Returns a value to the caller
            return encodePayload(coder, switch (value.action()) {
                // Multiple branching (switch/case)
                case ClickEvent.Action.OpenUrl _ -> "url";
                // Multiple branching (switch/case)
                case ClickEvent.Action.OpenFile _ -> "path";
                // Multiple branching (switch/case)
                case ClickEvent.Action.RunCommand _, ClickEvent.Action.SuggestCommand _ -> "command";
                // Multiple branching (switch/case)
                case ClickEvent.Action.ChangePage _  -> "page";
                // Multiple branching (switch/case)
                case ClickEvent.Action.CopyToClipboard _ -> "value";
                // Multiple branching (switch/case)
                case ClickEvent.Action.ShowDialog _ -> "dialog";
                // Multiple branching (switch/case)
                case ClickEvent.Action.Custom _ -> "__IGNORED__"; // Custom payload keys are written inside its writer
            // Calls a method
            }, value.payload(), map);
        // End of a block/expression
        }

        // Start of a method/block
        private static <D> Result<D> encodePayload(Transcoder<D> coder, String name, ClickEvent.Payload payload, MapBuilder<D> map) {
            // Returns a value to the caller
            return switch (payload) {
                // Multiple branching (switch/case)
                case ClickEvent.Payload.Text string -> {
                    // Calls a method
                    map.put(name, coder.createString(string.value()));
                    // Calls a method
                    yield new Result.Ok<>(map.build());
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case ClickEvent.Payload.Int integer -> {
                    // Calls a method
                    map.put(name, coder.createInt(integer.integer()));
                    // Calls a method
                    yield new Result.Ok<>(map.build());
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case ClickEvent.Payload.Dialog dialog -> {
                    // Calls a method
                    final Result<D> dialogResult = Dialog.CODEC.encode(coder, Dialog.unwrap(dialog.dialog()));
                    // Branch: checks a condition
                    if (!(dialogResult instanceof Result.Ok(D dialogValue)))
                        // Calls a method
                        yield dialogResult.cast();
                    // Calls a method
                    map.put(name, dialogValue);
                    // Calls a method
                    yield new Result.Ok<>(map.build());
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case ClickEvent.Payload.Custom custom -> {
                    // Calls a method
                    map.put("id", coder.createString(custom.key().asString()));
                    // Calls a method
                    final RawValue payloadRawValue = RawValue.of(Transcoder.NBT, MinestomAdventure.unwrapNbt(custom.nbt()));
                    // Calls a method
                    final Result<D> payloadResult = Codec.RAW_VALUE.encode(coder, payloadRawValue);
                    // Branch: checks a condition
                    if (!(payloadResult instanceof Result.Ok(D customPayload)))
                        // Calls a method
                        yield payloadResult.cast();
                    // Calls a method
                    map.put("payload", customPayload);
                    // Calls a method
                    yield new Result.Ok<>(map.build());
                // End of a block/expression
                }
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Calls a method
    private static final Codec<HoverEvent.Action<?>> HOVER_EVENT_ACTION = Codec.STRING.transform(HoverEvent.Action.NAMES::value, HoverEvent.Action::toString);
    // Calls a method
    private static final Codec<HoverEvent<?>> HOVER_EVENT = HOVER_EVENT_ACTION.unionType("action", ComponentCodecs::hoverEventCodec, HoverEvent::action);

    // Assigns a value
    private static final StructCodec<HoverEvent<Component>> SHOW_TEXT = StructCodec.struct(
            // Code statement
            "value", COMPONENT_FORWARD, HoverEvent::value,
            // Code statement
            HoverEvent::showText);
    // Assigns a value
    private static final StructCodec<HoverEvent<HoverEvent.ShowItem>> SHOW_ITEM = StructCodec.struct(
            // Code statement
            "id", Codec.KEY, hoverEvent -> hoverEvent.value().item(),
            // Code statement
            "count", Codec.INT.optional(1), hoverEvent -> hoverEvent.value().count(),
            // Code statement
            HoverEvent::showItem); // TODO(1.21.5): components
    // Assigns a value
    private static final StructCodec<HoverEvent<HoverEvent.ShowEntity>> SHOW_ENTITY = StructCodec.struct(
            // Code statement
            "id", Codec.KEY, hoverEvent -> hoverEvent.value().type(),
            // Code statement
            "uuid", Codec.UUID_COERCED, hoverEvent -> hoverEvent.value().id(),
            // Code statement
            "name", COMPONENT_FORWARD.optional(), hoverEvent -> hoverEvent.value().name(),
            // Code statement
            HoverEvent::showEntity);

    // Start of a method/block
    private static StructCodec<? extends HoverEvent<?>> hoverEventCodec(HoverEvent.Action<?> action) {
        // Branch: checks a condition
        if (action == HoverEvent.Action.SHOW_TEXT) return SHOW_TEXT;
        // Branch: checks a condition
        if (action == HoverEvent.Action.SHOW_ITEM) return SHOW_ITEM;
        // Branch: checks a condition
        if (action == HoverEvent.Action.SHOW_ENTITY) return SHOW_ENTITY;
        // Throws an exception
        throw new IllegalStateException("Unknown hover event action: " + action);
    // End of a block/expression
    }

    // Assigns a value
    public static final StructCodec<Style> STYLE = StructCodec.struct(
            // Code statement
            "color", TEXT_COLOR.optional(), Style::color,
            // Code statement
            "shadow_color", SHADOW_COLOR.optional(), Style::shadowColor,
            // Code statement
            "bold", Codec.BOOLEAN.optional(), s -> stateToBool(s.decoration(TextDecoration.BOLD)),
            // Code statement
            "italic", Codec.BOOLEAN.optional(), s -> stateToBool(s.decoration(TextDecoration.ITALIC)),
            // Code statement
            "underlined", Codec.BOOLEAN.optional(), s -> stateToBool(s.decoration(TextDecoration.UNDERLINED)),
            // Code statement
            "strikethrough", Codec.BOOLEAN.optional(), s -> stateToBool(s.decoration(TextDecoration.STRIKETHROUGH)),
            // Code statement
            "obfuscated", Codec.BOOLEAN.optional(), s -> stateToBool(s.decoration(TextDecoration.OBFUSCATED)),
            // Code statement
            "click_event", CLICK_EVENT.optional(), Style::clickEvent,
            // Code statement
            "hover_event", HOVER_EVENT.optional(), Style::hoverEvent,
            // Code statement
            "insertion", Codec.STRING.optional(), Style::insertion,
            // Code statement
            "font", Codec.KEY.optional(), Style::font,
            // Code statement
            (color, shadowColor, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, font) -> Style.style()
                    // Code statement
                    .color(color)
                    // Code statement
                    .shadowColor(shadowColor)
                    // Code statement
                    .decoration(TextDecoration.BOLD, TextDecoration.State.byBoolean(bold))
                    // Code statement
                    .decoration(TextDecoration.ITALIC, TextDecoration.State.byBoolean(italic))
                    // Code statement
                    .decoration(TextDecoration.UNDERLINED, TextDecoration.State.byBoolean(underlined))
                    // Code statement
                    .decoration(TextDecoration.STRIKETHROUGH, TextDecoration.State.byBoolean(strikethrough))
                    // Code statement
                    .decoration(TextDecoration.OBFUSCATED, TextDecoration.State.byBoolean(obfuscated))
                    // Code statement
                    .clickEvent(clickEvent)
                    // Code statement
                    .hoverEvent(hoverEvent)
                    // Code statement
                    .insertion(insertion)
                    // Code statement
                    .font(font)
                    // Code statement
                    .build()
    // End of a block/expression
    );

    // Assigns a value
    private static final StructCodec<TextComponent> TEXT_CONTENT = StructCodec.struct(
            // Code statement
            "text", Codec.STRING, TextComponent::content,
            // Code statement
            Component::text);
    // Assigns a value
    private static final StructCodec<TranslatableComponent> TRANSLATABLE_CONTENT = StructCodec.struct(
            // Code statement
            "translate", Codec.STRING, TranslatableComponent::key,
            // Code statement
            "fallback", Codec.STRING.optional(), TranslatableComponent::fallback,
            // Code statement
            "with", COMPONENT_FORWARD.list().optional(List.of()), ComponentCodecs::extractTranslatableComponents,
            // Code statement
            Component::translatable);
    // Assigns a value
    private static final StructCodec<ScoreComponent> SCORE_INNER_CONTENT = StructCodec.struct(
            // Code statement
            "name", Codec.STRING, ScoreComponent::name,
            // Code statement
            "objective", Codec.STRING, ScoreComponent::objective,
            // Code statement
            Component::score);
    // Assigns a value
    private static final StructCodec<ScoreComponent> SCORE_CONTENT = StructCodec.struct(
            // Code statement
            "score", SCORE_INNER_CONTENT, component -> component,
            // Code statement
            component -> component);
    // Assigns a value
    private static final StructCodec<SelectorComponent> SELECTOR_CONTENT = StructCodec.struct(
            // Code statement
            "selector", Codec.STRING, SelectorComponent::pattern,
            // Code statement
            "separator", COMPONENT_FORWARD.optional(), SelectorComponent::separator,
            // Code statement
            Component::selector);
    // Assigns a value
    private static final StructCodec<KeybindComponent> KEYBIND_CONTENT = StructCodec.struct(
            // Code statement
            "keybind", Codec.STRING, component -> component.keybind(),
            // Code statement
            Component::keybind);
    // Assigns a value
    private static final StructCodec<ObjectComponent> OBJECT_CONTENT = new StructCodec<>() {
        // Assigns a value
        private static final StructCodec<SpriteObjectContents> SPRITE_CONTENT = StructCodec.struct(
                // Code statement
                "atlas", Codec.KEY.optional(SpriteObjectContents.DEFAULT_ATLAS), SpriteObjectContents::atlas,
                // Code statement
                "sprite", Codec.KEY, SpriteObjectContents::sprite,
                // Code statement
                ObjectContents::sprite);
        // Assigns a value
        private static final StructCodec<PlayerHeadObjectContents> PLAYER_HEAD_CONTENTS = StructCodec.struct(
                // Code statement
                "player", ResolvableProfile.CODEC, ResolvableProfile::fromPlayerHeadContents,
                // Code statement
                "hat", Codec.BOOLEAN.optional(true), PlayerHeadObjectContents::hat,
                // Start of a method/block
                (player, hat) -> {
                    // Calls a method
                    final PlayerHeadObjectContents.Builder builder = ObjectContents.playerHead();
                    // Calls a method
                    player.applySkinToPlayerHeadContents(builder);
                    // Returns a value to the caller
                    return builder.hat(hat).build();
                // End of a block/expression
                });

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<ObjectComponent> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
            // Assigns a value
            final Result<? extends ObjectContents> contents = map.hasValue("player")
                    // Code statement
                    ? PLAYER_HEAD_CONTENTS.decodeFromMap(coder, map)
                    // Calls a method
                    : SPRITE_CONTENT.decodeFromMap(coder, map);
            // fallback is inlined into the map.
            // Branch: checks a condition
            if (!map.hasValue("fallback")) {
                // Returns a value to the caller
                return contents.mapResult(Component::object);
            // End of a block/expression
            }

            // Assigns a value
            final Result<Component> fallback = map.getValue("fallback")
                    // Code statement
                    .map(value -> ComponentCodecs.COMPONENT.decode(coder, value))
                    // Calls a method
                    .mapError(error -> "fallback: " + error);
            // Returns a value to the caller
            return contents.map(objectContents -> fallback.mapResult(fallbackComponent ->
                    // Calls a method
                    Component.object().contents(objectContents).fallback(fallbackComponent).build()));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encodeToMap(Transcoder<D> coder, ObjectComponent value, MapBuilder<D> map) {
            // Calls a method
            var fallback = value.fallback();
            // Branch: checks a condition
            if (fallback != null) {
                // Multiple branching (switch/case)
                switch (ComponentCodecs.COMPONENT.encode(coder, fallback)) {
                    // Multiple branching (switch/case)
                    case Result.Ok<D>(D component) -> map.put("fallback", component);
                    // Multiple branching (switch/case)
                    case Result.Error<D>(String error) -> {
                        // Returns a value to the caller
                        return new Result.Error<>("fallback: " + error);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return switch (value.contents()) {
                // Multiple branching (switch/case)
                case SpriteObjectContents sprite -> SPRITE_CONTENT.encodeToMap(coder, sprite, map);
                // Multiple branching (switch/case)
                case PlayerHeadObjectContents playerHead -> PLAYER_HEAD_CONTENTS.encodeToMap(coder, playerHead, map);
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    };
    // Assigns a value
    private static final StructCodec<NBTComponent<?>> NBT_CONTENT = new StructCodec<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<NBTComponent<?>> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
            // Returns a value to the caller
            return new Result.Error<>("NBTComponent not yet supported");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encodeToMap(Transcoder<D> coder, NBTComponent<?> value, MapBuilder<D> map) {
            // Returns a value to the caller
            return new Result.Error<>("NBTComponent not yet supported");
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Assigns a value
    public static final Codec<Component> COMPONENT = Codec.Recursive((componentCodec) -> {
        // Calls a method
        final Codec<List<Component>> componentListCodec = componentCodec.list();
        // Assigns a value
        final StructCodec<List<Component>> childrenCodec = StructCodec.struct(
                // Code statement
                "extra", componentListCodec.optional(List.of()), children -> children,
                // Code statement
                children -> children);
        // Returns a value to the caller
        return new Codec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<Component> decode(Transcoder<D> coder, D value) {
                // A single string is a valid serialized form of a text component, try it.
                // Calls a method
                final Result<String> stringResult = coder.getString(value);
                // Branch: checks a condition
                if (stringResult instanceof Result.Ok(String string))
                    // Returns a value to the caller
                    return new Result.Ok<>(Component.text(string));
                // A list of components is a valid serialized form of a component, try it.
                // Calls a method
                final Result<List<Component>> listResult = componentListCodec.decode(coder, value);
                // Branch: checks a condition
                if (listResult instanceof Result.Ok(List<Component> list))
                    // Returns a value to the caller
                    return new Result.Ok<>(Component.empty().children(list));

                // Otherwise it must be an object and we need to infer the type
                // Calls a method
                final Result<MapLike<D>> mapResult = coder.getMap(value);
                // Branch: checks a condition
                if (!(mapResult instanceof Result.Ok(MapLike<D> map)))
                    // Returns a value to the caller
                    return mapResult.cast();

                // Calls a method
                final String maybeType = map.getValue("type").map(coder::getString).orElse(null);
                // Assigns a value
                final Result<? extends Component> baseResult = switch (maybeType) {
                    // Multiple branching (switch/case)
                    case "text" -> TEXT_CONTENT.decodeFromMap(coder, map);
                    // Multiple branching (switch/case)
                    case "translatable" -> TRANSLATABLE_CONTENT.decodeFromMap(coder, map);
                    // Multiple branching (switch/case)
                    case "score" -> SCORE_CONTENT.decodeFromMap(coder, map);
                    // Multiple branching (switch/case)
                    case "selector" -> SELECTOR_CONTENT.decodeFromMap(coder, map);
                    // Multiple branching (switch/case)
                    case "keybind" -> KEYBIND_CONTENT.decodeFromMap(coder, map);
                    // Multiple branching (switch/case)
                    case "nbt" -> NBT_CONTENT.decodeFromMap(coder, map);
                    // Multiple branching (switch/case)
                    case "object" -> OBJECT_CONTENT.decodeFromMap(coder, map);
                    // Multiple branching (switch/case)
                    case null, default -> {
                        // Type was not included, try to guess based on the content.
                        // Calls a method
                        final Result<? extends Component> textResult = TEXT_CONTENT.decodeFromMap(coder, map);
                        // Branch: checks a condition
                        if (textResult instanceof Result.Ok<? extends Component>)
                            // Code statement
                            yield textResult;
                        // Calls a method
                        final Result<? extends Component> translatableResult = TRANSLATABLE_CONTENT.decodeFromMap(coder, map);
                        // Branch: checks a condition
                        if (translatableResult instanceof Result.Ok<? extends Component>)
                            // Code statement
                            yield translatableResult;
                        // Calls a method
                        final Result<? extends Component> scoreResult = SCORE_CONTENT.decodeFromMap(coder, map);
                        // Branch: checks a condition
                        if (scoreResult instanceof Result.Ok<? extends Component>)
                            // Code statement
                            yield scoreResult;
                        // Calls a method
                        final Result<? extends Component> selectorResult = SELECTOR_CONTENT.decodeFromMap(coder, map);
                        // Branch: checks a condition
                        if (selectorResult instanceof Result.Ok<? extends Component>)
                            // Code statement
                            yield selectorResult;
                        // Calls a method
                        final Result<? extends Component> keybindResult = KEYBIND_CONTENT.decodeFromMap(coder, map);
                        // Branch: checks a condition
                        if (keybindResult instanceof Result.Ok<? extends Component>)
                            // Code statement
                            yield keybindResult;
                        // Calls a method
                        final Result<? extends Component> nbtResult = NBT_CONTENT.decodeFromMap(coder, map);
                        // Branch: checks a condition
                        if (nbtResult instanceof Result.Ok<? extends Component>)
                            // Code statement
                            yield nbtResult;
                        // Calls a method
                        final Result<? extends Component> objectResult = OBJECT_CONTENT.decodeFromMap(coder, map);
                        // Branch: checks a condition
                        if (objectResult instanceof Result.Ok<? extends Component>)
                            // Code statement
                            yield objectResult;
                        // Calls a method
                        yield new Result.Error<>("Unable to determine component type");
                    // End of a block/expression
                    }
                // End of a block/expression
                };

                // Returns a value to the caller
                return baseResult
                        // Code statement
                        .map(base -> childrenCodec.decodeFromMap(coder, map).mapResult(base::children))
                        // Calls a method
                        .map(style -> STYLE.decodeFromMap(coder, map).mapResult(style::style));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encode(Transcoder<D> coder, @Nullable Component value) {
                // Branch: checks a condition
                if (value == null) return new Result.Error<>("null");

                // As a special case we want to encode text components with no children or styling as strings directly.
                // Branch: checks a condition
                if (value instanceof TextComponent text && value.children().isEmpty() && value.style().isEmpty())
                    // Returns a value to the caller
                    return new Result.Ok<>(coder.createString(text.content()));

                // Otherwise an object. Never encode as a list even through it is a supported decode format.
                // Calls a method
                final MapBuilder<D> map = coder.createMap();
                // Assigns a value
                final Result<D> baseResult = switch (value) {
                    // Multiple branching (switch/case)
                    case TextComponent textComponent -> TEXT_CONTENT.encodeToMap(coder, textComponent, map);
                    // Multiple branching (switch/case)
                    case TranslatableComponent translatableComponent ->
                            // Calls a method
                            TRANSLATABLE_CONTENT.encodeToMap(coder, translatableComponent, map);
                    // Multiple branching (switch/case)
                    case ScoreComponent scoreComponent -> SCORE_CONTENT.encodeToMap(coder, scoreComponent, map);
                    // Multiple branching (switch/case)
                    case SelectorComponent selectorComponent ->
                            // Calls a method
                            SELECTOR_CONTENT.encodeToMap(coder, selectorComponent, map);
                    // Multiple branching (switch/case)
                    case KeybindComponent keybindComponent -> KEYBIND_CONTENT.encodeToMap(coder, keybindComponent, map);
                    // Multiple branching (switch/case)
                    case NBTComponent<?> nbtComponent -> NBT_CONTENT.encodeToMap(coder, nbtComponent, map);
                    // Multiple branching (switch/case)
                    case ObjectComponent objectComponent -> OBJECT_CONTENT.encodeToMap(coder, objectComponent, map);
                // End of a block/expression
                };

                // Returns a value to the caller
                return baseResult
                        // Code statement
                        .map(ignored -> childrenCodec.encodeToMap(coder, value.children(), map))
                        // Code statement
                        .map(ignored -> STYLE.encodeToMap(coder, value.style(), map))
                        // Calls a method
                        .mapResult(ignored -> map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    });
// End of a block/expression
}
