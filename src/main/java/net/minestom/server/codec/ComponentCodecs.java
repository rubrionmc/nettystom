// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.*;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.ClickEvent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEvent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.*;
// Import d'une classe nécessaire
import net.kyori.adventure.text.object.ObjectContents;
// Import d'une classe nécessaire
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
// Import d'une classe nécessaire
import net.kyori.adventure.text.object.SpriteObjectContents;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder.MapBuilder;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder.MapLike;
// Import d'une classe nécessaire
import net.minestom.server.dialog.Dialog;
// Import d'une classe nécessaire
import net.minestom.server.network.player.ResolvableProfile;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

/**
 * Used internally to hold component codecs
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class ComponentCodecs {
    // Instruction de code
    private ComponentCodecs() {}
    // Very gross :|
    // Appelle une méthode
    private static final Codec<Component> COMPONENT_FORWARD = Codec.ForwardRef(() -> Codec.COMPONENT);

    // Affecte une valeur
    public static final Codec<TextColor> TEXT_COLOR = new Codec<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<TextColor> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<String> colorResult = coder.getString(value);
            // Embranchement : vérifie une condition
            if (!(colorResult instanceof Result.Ok(String colorString)))
                // Renvoie une valeur à l'appelant
                return colorResult.cast();
            // Embranchement : vérifie une condition
            if (colorString.startsWith("#")) {
                // Appelle une méthode
                final TextColor color = TextColor.fromHexString(colorString);
                // Embranchement : vérifie une condition
                if (color == null) return new Result.Error<>("Unknown color: " + colorString);
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(color);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final NamedTextColor namedColor = NamedTextColor.NAMES.value(colorString);
            // Embranchement : vérifie une condition
            if (namedColor == null) return new Result.Error<>("Unknown color: " + colorString);
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(namedColor);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable TextColor value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Embranchement : vérifie une condition
            if (value instanceof NamedTextColor namedColor)
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(coder.createString(namedColor.name()));
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(coder.createString(value.asHexString()));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Appelle une méthode
    public static final Codec<ShadowColor> SHADOW_COLOR = Codec.INT.transform(ShadowColor::shadowColor, ShadowColor::value);

    // Début d'une méthode/d'un bloc
    private static @Nullable Boolean stateToBool(TextDecoration.State state) {
        // Renvoie une valeur à l'appelant
        return switch (state) {
            // Embranchement multiple (switch/case)
            case NOT_SET -> null;
            // Embranchement multiple (switch/case)
            case FALSE -> false;
            // Embranchement multiple (switch/case)
            case TRUE -> true;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static List<Component> extractTranslatableComponents(final TranslatableComponent component) {
        // Appelle une méthode
        final List<TranslationArgument> arguments = component.arguments();
        // Embranchement : vérifie une condition
        if (arguments.isEmpty()) return List.of();
        // Appelle une méthode
        Component[] components = new Component[arguments.size()];
        // Boucle : répète un bloc
        for (int i = 0; i < components.length; i++) {
            // Appelle une méthode
            components[i] = arguments.get(i).asComponent();
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return List.of(components);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final StructCodec<ClickEvent<?>> CLICK_EVENT = new StructCodec<>() {
        // Appelle une méthode
        private static final Codec<ClickEvent.Action<?>> ACTION_CODEC = Codec.STRING.transform(ClickEvent.Action.NAMES::value, ClickEvent.Action::name);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<ClickEvent<?>> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
            // Appelle une méthode
            final Result<ClickEvent.Action<?>> actionResult = map.getValue("action").map(value -> ACTION_CODEC.decode(coder, value));
            // Embranchement : vérifie une condition
            if (!(actionResult instanceof Result.Ok(var action)))
                // Renvoie une valeur à l'appelant
                return actionResult.cast();

            // Renvoie une valeur à l'appelant
            return switch (action) {
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.OpenUrl _ -> map.getValue("url").map(value -> Codec.STRING.decode(coder, value))
                        // Appelle une méthode
                        .mapResult(ClickEvent::openUrl);
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.OpenFile _ -> map.getValue("path").map(value -> Codec.STRING.decode(coder, value))
                        // Appelle une méthode
                        .mapResult(ClickEvent::openFile);
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.RunCommand _ -> map.getValue("command").map(value -> Codec.STRING.decode(coder, value))
                        // Appelle une méthode
                        .mapResult(ClickEvent::runCommand);
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.SuggestCommand _ -> map.getValue("command").map(value -> Codec.STRING.decode(coder, value))
                        // Appelle une méthode
                        .mapResult(ClickEvent::suggestCommand);
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.ChangePage _ -> map.getValue("page").map(value -> Codec.INT.decode(coder, value))
                        // Appelle une méthode
                        .mapResult(ClickEvent::changePage);
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.CopyToClipboard _ -> map.getValue("value").map(value -> Codec.STRING.decode(coder, value))
                        // Appelle une méthode
                        .mapResult(ClickEvent::copyToClipboard);
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.ShowDialog _ -> map.getValue("dialog").map(value -> Dialog.CODEC.decode(coder, value))
                        // Appelle une méthode
                        .mapResult(dialog -> ClickEvent.showDialog(Dialog.wrap(dialog)));
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.Custom _ -> {
                    // Appelle une méthode
                    final Result<Key> idResult = map.getValue("id").map(value -> Codec.KEY.decode(coder, value));
                    // Embranchement : vérifie une condition
                    if (!(idResult instanceof Result.Ok(Key id)))
                        // Appelle une méthode
                        yield idResult.cast();

                    // Affecte une valeur
                    BinaryTag payload = CompoundBinaryTag.empty(); // Default to empty. It is optional technically, but adventure does not support that.
                    // Embranchement : vérifie une condition
                    if (map.hasValue("payload")) {
                        // Affecte une valeur
                        final Result<BinaryTag> payloadResult = map.getValue("payload")
                                // Instruction de code
                                .map(value -> Codec.RAW_VALUE.decode(coder, value))
                                // Appelle une méthode
                                .map(value -> value.convertTo(Transcoder.NBT));
                        // Embranchement : vérifie une condition
                        if (!(payloadResult instanceof Result.Ok(BinaryTag rawValue)))
                            // Appelle une méthode
                            yield payloadResult.cast();
                        // Affecte une valeur
                        payload = rawValue;
                    // Fin d'un bloc/d'une expression
                    }

                    // Appelle une méthode
                    yield new Result.Ok<>(ClickEvent.custom(id, MinestomAdventure.wrapNbt(payload)));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encodeToMap(Transcoder<D> coder, ClickEvent<?> value, MapBuilder<D> map) {
            // Appelle une méthode
            final Result<D> actionResult = ACTION_CODEC.encode(coder, value.action());
            // Embranchement : vérifie une condition
            if (!(actionResult instanceof Result.Ok(D actionValue)))
                // Renvoie une valeur à l'appelant
                return actionResult.cast();
            // Appelle une méthode
            map.put("action", actionValue);

            // Renvoie une valeur à l'appelant
            return encodePayload(coder, switch (value.action()) {
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.OpenUrl _ -> "url";
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.OpenFile _ -> "path";
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.RunCommand _, ClickEvent.Action.SuggestCommand _ -> "command";
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.ChangePage _  -> "page";
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.CopyToClipboard _ -> "value";
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.ShowDialog _ -> "dialog";
                // Embranchement multiple (switch/case)
                case ClickEvent.Action.Custom _ -> "__IGNORED__"; // Custom payload keys are written inside its writer
            // Appelle une méthode
            }, value.payload(), map);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static <D> Result<D> encodePayload(Transcoder<D> coder, String name, ClickEvent.Payload payload, MapBuilder<D> map) {
            // Renvoie une valeur à l'appelant
            return switch (payload) {
                // Embranchement multiple (switch/case)
                case ClickEvent.Payload.Text string -> {
                    // Appelle une méthode
                    map.put(name, coder.createString(string.value()));
                    // Appelle une méthode
                    yield new Result.Ok<>(map.build());
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case ClickEvent.Payload.Int integer -> {
                    // Appelle une méthode
                    map.put(name, coder.createInt(integer.integer()));
                    // Appelle une méthode
                    yield new Result.Ok<>(map.build());
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case ClickEvent.Payload.Dialog dialog -> {
                    // Appelle une méthode
                    final Result<D> dialogResult = Dialog.CODEC.encode(coder, Dialog.unwrap(dialog.dialog()));
                    // Embranchement : vérifie une condition
                    if (!(dialogResult instanceof Result.Ok(D dialogValue)))
                        // Appelle une méthode
                        yield dialogResult.cast();
                    // Appelle une méthode
                    map.put(name, dialogValue);
                    // Appelle une méthode
                    yield new Result.Ok<>(map.build());
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case ClickEvent.Payload.Custom custom -> {
                    // Appelle une méthode
                    map.put("id", coder.createString(custom.key().asString()));
                    // Appelle une méthode
                    final RawValue payloadRawValue = RawValue.of(Transcoder.NBT, MinestomAdventure.unwrapNbt(custom.nbt()));
                    // Appelle une méthode
                    final Result<D> payloadResult = Codec.RAW_VALUE.encode(coder, payloadRawValue);
                    // Embranchement : vérifie une condition
                    if (!(payloadResult instanceof Result.Ok(D customPayload)))
                        // Appelle une méthode
                        yield payloadResult.cast();
                    // Appelle une méthode
                    map.put("payload", customPayload);
                    // Appelle une méthode
                    yield new Result.Ok<>(map.build());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Appelle une méthode
    private static final Codec<HoverEvent.Action<?>> HOVER_EVENT_ACTION = Codec.STRING.transform(HoverEvent.Action.NAMES::value, HoverEvent.Action::toString);
    // Appelle une méthode
    private static final Codec<HoverEvent<?>> HOVER_EVENT = HOVER_EVENT_ACTION.unionType("action", ComponentCodecs::hoverEventCodec, HoverEvent::action);

    // Affecte une valeur
    private static final StructCodec<HoverEvent<Component>> SHOW_TEXT = StructCodec.struct(
            // Instruction de code
            "value", COMPONENT_FORWARD, HoverEvent::value,
            // Instruction de code
            HoverEvent::showText);
    // Affecte une valeur
    private static final StructCodec<HoverEvent<HoverEvent.ShowItem>> SHOW_ITEM = StructCodec.struct(
            // Instruction de code
            "id", Codec.KEY, hoverEvent -> hoverEvent.value().item(),
            // Instruction de code
            "count", Codec.INT.optional(1), hoverEvent -> hoverEvent.value().count(),
            // Instruction de code
            HoverEvent::showItem); // TODO(1.21.5): components
    // Affecte une valeur
    private static final StructCodec<HoverEvent<HoverEvent.ShowEntity>> SHOW_ENTITY = StructCodec.struct(
            // Instruction de code
            "id", Codec.KEY, hoverEvent -> hoverEvent.value().type(),
            // Instruction de code
            "uuid", Codec.UUID_COERCED, hoverEvent -> hoverEvent.value().id(),
            // Instruction de code
            "name", COMPONENT_FORWARD.optional(), hoverEvent -> hoverEvent.value().name(),
            // Instruction de code
            HoverEvent::showEntity);

    // Début d'une méthode/d'un bloc
    private static StructCodec<? extends HoverEvent<?>> hoverEventCodec(HoverEvent.Action<?> action) {
        // Embranchement : vérifie une condition
        if (action == HoverEvent.Action.SHOW_TEXT) return SHOW_TEXT;
        // Embranchement : vérifie une condition
        if (action == HoverEvent.Action.SHOW_ITEM) return SHOW_ITEM;
        // Embranchement : vérifie une condition
        if (action == HoverEvent.Action.SHOW_ENTITY) return SHOW_ENTITY;
        // Lève une exception
        throw new IllegalStateException("Unknown hover event action: " + action);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final StructCodec<Style> STYLE = StructCodec.struct(
            // Instruction de code
            "color", TEXT_COLOR.optional(), Style::color,
            // Instruction de code
            "shadow_color", SHADOW_COLOR.optional(), Style::shadowColor,
            // Instruction de code
            "bold", Codec.BOOLEAN.optional(), s -> stateToBool(s.decoration(TextDecoration.BOLD)),
            // Instruction de code
            "italic", Codec.BOOLEAN.optional(), s -> stateToBool(s.decoration(TextDecoration.ITALIC)),
            // Instruction de code
            "underlined", Codec.BOOLEAN.optional(), s -> stateToBool(s.decoration(TextDecoration.UNDERLINED)),
            // Instruction de code
            "strikethrough", Codec.BOOLEAN.optional(), s -> stateToBool(s.decoration(TextDecoration.STRIKETHROUGH)),
            // Instruction de code
            "obfuscated", Codec.BOOLEAN.optional(), s -> stateToBool(s.decoration(TextDecoration.OBFUSCATED)),
            // Instruction de code
            "click_event", CLICK_EVENT.optional(), Style::clickEvent,
            // Instruction de code
            "hover_event", HOVER_EVENT.optional(), Style::hoverEvent,
            // Instruction de code
            "insertion", Codec.STRING.optional(), Style::insertion,
            // Instruction de code
            "font", Codec.KEY.optional(), Style::font,
            // Instruction de code
            (color, shadowColor, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, font) -> Style.style()
                    // Instruction de code
                    .color(color)
                    // Instruction de code
                    .shadowColor(shadowColor)
                    // Instruction de code
                    .decoration(TextDecoration.BOLD, TextDecoration.State.byBoolean(bold))
                    // Instruction de code
                    .decoration(TextDecoration.ITALIC, TextDecoration.State.byBoolean(italic))
                    // Instruction de code
                    .decoration(TextDecoration.UNDERLINED, TextDecoration.State.byBoolean(underlined))
                    // Instruction de code
                    .decoration(TextDecoration.STRIKETHROUGH, TextDecoration.State.byBoolean(strikethrough))
                    // Instruction de code
                    .decoration(TextDecoration.OBFUSCATED, TextDecoration.State.byBoolean(obfuscated))
                    // Instruction de code
                    .clickEvent(clickEvent)
                    // Instruction de code
                    .hoverEvent(hoverEvent)
                    // Instruction de code
                    .insertion(insertion)
                    // Instruction de code
                    .font(font)
                    // Instruction de code
                    .build()
    // Fin d'un bloc/d'une expression
    );

    // Affecte une valeur
    private static final StructCodec<TextComponent> TEXT_CONTENT = StructCodec.struct(
            // Instruction de code
            "text", Codec.STRING, TextComponent::content,
            // Instruction de code
            Component::text);
    // Affecte une valeur
    private static final StructCodec<TranslatableComponent> TRANSLATABLE_CONTENT = StructCodec.struct(
            // Instruction de code
            "translate", Codec.STRING, TranslatableComponent::key,
            // Instruction de code
            "fallback", Codec.STRING.optional(), TranslatableComponent::fallback,
            // Instruction de code
            "with", COMPONENT_FORWARD.list().optional(List.of()), ComponentCodecs::extractTranslatableComponents,
            // Instruction de code
            Component::translatable);
    // Affecte une valeur
    private static final StructCodec<ScoreComponent> SCORE_INNER_CONTENT = StructCodec.struct(
            // Instruction de code
            "name", Codec.STRING, ScoreComponent::name,
            // Instruction de code
            "objective", Codec.STRING, ScoreComponent::objective,
            // Instruction de code
            Component::score);
    // Affecte une valeur
    private static final StructCodec<ScoreComponent> SCORE_CONTENT = StructCodec.struct(
            // Instruction de code
            "score", SCORE_INNER_CONTENT, component -> component,
            // Instruction de code
            component -> component);
    // Affecte une valeur
    private static final StructCodec<SelectorComponent> SELECTOR_CONTENT = StructCodec.struct(
            // Instruction de code
            "selector", Codec.STRING, SelectorComponent::pattern,
            // Instruction de code
            "separator", COMPONENT_FORWARD.optional(), SelectorComponent::separator,
            // Instruction de code
            Component::selector);
    // Affecte une valeur
    private static final StructCodec<KeybindComponent> KEYBIND_CONTENT = StructCodec.struct(
            // Instruction de code
            "keybind", Codec.STRING, component -> component.keybind(),
            // Instruction de code
            Component::keybind);
    // Affecte une valeur
    private static final StructCodec<ObjectComponent> OBJECT_CONTENT = new StructCodec<>() {
        // Affecte une valeur
        private static final StructCodec<SpriteObjectContents> SPRITE_CONTENT = StructCodec.struct(
                // Instruction de code
                "atlas", Codec.KEY.optional(SpriteObjectContents.DEFAULT_ATLAS), SpriteObjectContents::atlas,
                // Instruction de code
                "sprite", Codec.KEY, SpriteObjectContents::sprite,
                // Instruction de code
                ObjectContents::sprite);
        // Affecte une valeur
        private static final StructCodec<PlayerHeadObjectContents> PLAYER_HEAD_CONTENTS = StructCodec.struct(
                // Instruction de code
                "player", ResolvableProfile.CODEC, ResolvableProfile::fromPlayerHeadContents,
                // Instruction de code
                "hat", Codec.BOOLEAN.optional(true), PlayerHeadObjectContents::hat,
                // Début d'une méthode/d'un bloc
                (player, hat) -> {
                    // Appelle une méthode
                    final PlayerHeadObjectContents.Builder builder = ObjectContents.playerHead();
                    // Appelle une méthode
                    player.applySkinToPlayerHeadContents(builder);
                    // Renvoie une valeur à l'appelant
                    return builder.hat(hat).build();
                // Fin d'un bloc/d'une expression
                });

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<ObjectComponent> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
            // Affecte une valeur
            final Result<? extends ObjectContents> contents = map.hasValue("player")
                    // Instruction de code
                    ? PLAYER_HEAD_CONTENTS.decodeFromMap(coder, map)
                    // Appelle une méthode
                    : SPRITE_CONTENT.decodeFromMap(coder, map);
            // fallback is inlined into the map.
            // Embranchement : vérifie une condition
            if (!map.hasValue("fallback")) {
                // Renvoie une valeur à l'appelant
                return contents.mapResult(Component::object);
            // Fin d'un bloc/d'une expression
            }

            // Affecte une valeur
            final Result<Component> fallback = map.getValue("fallback")
                    // Instruction de code
                    .map(value -> ComponentCodecs.COMPONENT.decode(coder, value))
                    // Appelle une méthode
                    .mapError(error -> "fallback: " + error);
            // Renvoie une valeur à l'appelant
            return contents.map(objectContents -> fallback.mapResult(fallbackComponent ->
                    // Appelle une méthode
                    Component.object().contents(objectContents).fallback(fallbackComponent).build()));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encodeToMap(Transcoder<D> coder, ObjectComponent value, MapBuilder<D> map) {
            // Appelle une méthode
            var fallback = value.fallback();
            // Embranchement : vérifie une condition
            if (fallback != null) {
                // Embranchement multiple (switch/case)
                switch (ComponentCodecs.COMPONENT.encode(coder, fallback)) {
                    // Embranchement multiple (switch/case)
                    case Result.Ok<D>(D component) -> map.put("fallback", component);
                    // Embranchement multiple (switch/case)
                    case Result.Error<D>(String error) -> {
                        // Renvoie une valeur à l'appelant
                        return new Result.Error<>("fallback: " + error);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return switch (value.contents()) {
                // Embranchement multiple (switch/case)
                case SpriteObjectContents sprite -> SPRITE_CONTENT.encodeToMap(coder, sprite, map);
                // Embranchement multiple (switch/case)
                case PlayerHeadObjectContents playerHead -> PLAYER_HEAD_CONTENTS.encodeToMap(coder, playerHead, map);
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
    // Affecte une valeur
    private static final StructCodec<NBTComponent<?>> NBT_CONTENT = new StructCodec<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<NBTComponent<?>> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("NBTComponent not yet supported");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encodeToMap(Transcoder<D> coder, NBTComponent<?> value, MapBuilder<D> map) {
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("NBTComponent not yet supported");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Affecte une valeur
    public static final Codec<Component> COMPONENT = Codec.Recursive((componentCodec) -> {
        // Appelle une méthode
        final Codec<List<Component>> componentListCodec = componentCodec.list();
        // Affecte une valeur
        final StructCodec<List<Component>> childrenCodec = StructCodec.struct(
                // Instruction de code
                "extra", componentListCodec.optional(List.of()), children -> children,
                // Instruction de code
                children -> children);
        // Renvoie une valeur à l'appelant
        return new Codec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<Component> decode(Transcoder<D> coder, D value) {
                // A single string is a valid serialized form of a text component, try it.
                // Appelle une méthode
                final Result<String> stringResult = coder.getString(value);
                // Embranchement : vérifie une condition
                if (stringResult instanceof Result.Ok(String string))
                    // Renvoie une valeur à l'appelant
                    return new Result.Ok<>(Component.text(string));
                // A list of components is a valid serialized form of a component, try it.
                // Appelle une méthode
                final Result<List<Component>> listResult = componentListCodec.decode(coder, value);
                // Embranchement : vérifie une condition
                if (listResult instanceof Result.Ok(List<Component> list))
                    // Renvoie une valeur à l'appelant
                    return new Result.Ok<>(Component.empty().children(list));

                // Otherwise it must be an object and we need to infer the type
                // Appelle une méthode
                final Result<MapLike<D>> mapResult = coder.getMap(value);
                // Embranchement : vérifie une condition
                if (!(mapResult instanceof Result.Ok(MapLike<D> map)))
                    // Renvoie une valeur à l'appelant
                    return mapResult.cast();

                // Appelle une méthode
                final String maybeType = map.getValue("type").map(coder::getString).orElse(null);
                // Affecte une valeur
                final Result<? extends Component> baseResult = switch (maybeType) {
                    // Embranchement multiple (switch/case)
                    case "text" -> TEXT_CONTENT.decodeFromMap(coder, map);
                    // Embranchement multiple (switch/case)
                    case "translatable" -> TRANSLATABLE_CONTENT.decodeFromMap(coder, map);
                    // Embranchement multiple (switch/case)
                    case "score" -> SCORE_CONTENT.decodeFromMap(coder, map);
                    // Embranchement multiple (switch/case)
                    case "selector" -> SELECTOR_CONTENT.decodeFromMap(coder, map);
                    // Embranchement multiple (switch/case)
                    case "keybind" -> KEYBIND_CONTENT.decodeFromMap(coder, map);
                    // Embranchement multiple (switch/case)
                    case "nbt" -> NBT_CONTENT.decodeFromMap(coder, map);
                    // Embranchement multiple (switch/case)
                    case "object" -> OBJECT_CONTENT.decodeFromMap(coder, map);
                    // Embranchement multiple (switch/case)
                    case null, default -> {
                        // Type was not included, try to guess based on the content.
                        // Appelle une méthode
                        final Result<? extends Component> textResult = TEXT_CONTENT.decodeFromMap(coder, map);
                        // Embranchement : vérifie une condition
                        if (textResult instanceof Result.Ok<? extends Component>)
                            // Instruction de code
                            yield textResult;
                        // Appelle une méthode
                        final Result<? extends Component> translatableResult = TRANSLATABLE_CONTENT.decodeFromMap(coder, map);
                        // Embranchement : vérifie une condition
                        if (translatableResult instanceof Result.Ok<? extends Component>)
                            // Instruction de code
                            yield translatableResult;
                        // Appelle une méthode
                        final Result<? extends Component> scoreResult = SCORE_CONTENT.decodeFromMap(coder, map);
                        // Embranchement : vérifie une condition
                        if (scoreResult instanceof Result.Ok<? extends Component>)
                            // Instruction de code
                            yield scoreResult;
                        // Appelle une méthode
                        final Result<? extends Component> selectorResult = SELECTOR_CONTENT.decodeFromMap(coder, map);
                        // Embranchement : vérifie une condition
                        if (selectorResult instanceof Result.Ok<? extends Component>)
                            // Instruction de code
                            yield selectorResult;
                        // Appelle une méthode
                        final Result<? extends Component> keybindResult = KEYBIND_CONTENT.decodeFromMap(coder, map);
                        // Embranchement : vérifie une condition
                        if (keybindResult instanceof Result.Ok<? extends Component>)
                            // Instruction de code
                            yield keybindResult;
                        // Appelle une méthode
                        final Result<? extends Component> nbtResult = NBT_CONTENT.decodeFromMap(coder, map);
                        // Embranchement : vérifie une condition
                        if (nbtResult instanceof Result.Ok<? extends Component>)
                            // Instruction de code
                            yield nbtResult;
                        // Appelle une méthode
                        final Result<? extends Component> objectResult = OBJECT_CONTENT.decodeFromMap(coder, map);
                        // Embranchement : vérifie une condition
                        if (objectResult instanceof Result.Ok<? extends Component>)
                            // Instruction de code
                            yield objectResult;
                        // Appelle une méthode
                        yield new Result.Error<>("Unable to determine component type");
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                };

                // Renvoie une valeur à l'appelant
                return baseResult
                        // Instruction de code
                        .map(base -> childrenCodec.decodeFromMap(coder, map).mapResult(base::children))
                        // Appelle une méthode
                        .map(style -> STYLE.decodeFromMap(coder, map).mapResult(style::style));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encode(Transcoder<D> coder, @Nullable Component value) {
                // Embranchement : vérifie une condition
                if (value == null) return new Result.Error<>("null");

                // As a special case we want to encode text components with no children or styling as strings directly.
                // Embranchement : vérifie une condition
                if (value instanceof TextComponent text && value.children().isEmpty() && value.style().isEmpty())
                    // Renvoie une valeur à l'appelant
                    return new Result.Ok<>(coder.createString(text.content()));

                // Otherwise an object. Never encode as a list even through it is a supported decode format.
                // Appelle une méthode
                final MapBuilder<D> map = coder.createMap();
                // Affecte une valeur
                final Result<D> baseResult = switch (value) {
                    // Embranchement multiple (switch/case)
                    case TextComponent textComponent -> TEXT_CONTENT.encodeToMap(coder, textComponent, map);
                    // Embranchement multiple (switch/case)
                    case TranslatableComponent translatableComponent ->
                            // Appelle une méthode
                            TRANSLATABLE_CONTENT.encodeToMap(coder, translatableComponent, map);
                    // Embranchement multiple (switch/case)
                    case ScoreComponent scoreComponent -> SCORE_CONTENT.encodeToMap(coder, scoreComponent, map);
                    // Embranchement multiple (switch/case)
                    case SelectorComponent selectorComponent ->
                            // Appelle une méthode
                            SELECTOR_CONTENT.encodeToMap(coder, selectorComponent, map);
                    // Embranchement multiple (switch/case)
                    case KeybindComponent keybindComponent -> KEYBIND_CONTENT.encodeToMap(coder, keybindComponent, map);
                    // Embranchement multiple (switch/case)
                    case NBTComponent<?> nbtComponent -> NBT_CONTENT.encodeToMap(coder, nbtComponent, map);
                    // Embranchement multiple (switch/case)
                    case ObjectComponent objectComponent -> OBJECT_CONTENT.encodeToMap(coder, objectComponent, map);
                // Fin d'un bloc/d'une expression
                };

                // Renvoie une valeur à l'appelant
                return baseResult
                        // Instruction de code
                        .map(ignored -> childrenCodec.encodeToMap(coder, value.children(), map))
                        // Instruction de code
                        .map(ignored -> STYLE.encodeToMap(coder, value.style(), map))
                        // Appelle une méthode
                        .mapResult(ignored -> map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    });
// Fin d'un bloc/d'une expression
}
