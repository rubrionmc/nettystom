// Déclaration du paquet de ce fichier
package net.minestom.server.dialog;

// Import d'une classe nécessaire
import net.kyori.adventure.dialog.DialogLike;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.*;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Dialog extends Holder.Direct<Dialog>, DialogLike {
    // Affecte une valeur
    Registry<StructCodec<? extends Dialog>> REGISTRY = DynamicRegistry.fromMap(
            // Instruction de code
            Key.key("dialog_type"),
            // Instruction de code
            Map.entry(Key.key("notice"), Notice.CODEC),
            // Instruction de code
            Map.entry(Key.key("server_links"), ServerLinks.CODEC),
            // Instruction de code
            Map.entry(Key.key("dialog_list"), DialogList.CODEC),
            // Instruction de code
            Map.entry(Key.key("multi_action"), MultiAction.CODEC),
            // Appelle une méthode
            Map.entry(Key.key("confirmation"), Confirmation.CODEC));
    // Appelle une méthode
    StructCodec<Dialog> REGISTRY_CODEC = Codec.RegistryTaggedUnion(REGISTRY, Dialog::codec);
    // Appelle une méthode
    NetworkBuffer.Type<Dialog> REGISTRY_NETWORK_TYPE = NetworkBuffer.TypedNBT(REGISTRY_CODEC);

    // Appelle une méthode
    NetworkBuffer.Type<Holder<Dialog>> NETWORK_TYPE = Holder.networkType(Registries::dialog, REGISTRY_NETWORK_TYPE);
    // Appelle une méthode
    Codec<Holder<Dialog>> CODEC = Holder.codec(Registries::dialog, REGISTRY_CODEC);

    /**
     * <p>Creates a new adventure {@link DialogLike} for the dialog at the given key.</p>
     *
     * <p>Useful for sending a dialog which has been pre-sent to the client in the Dialog registry.</p>
     *
     * @param key the key of the dialog (must be registered)
     * @return a new {@link DialogLike} for the dialog at the given key
     */
    // Début d'une méthode/d'un bloc
    static DialogLike forKey(RegistryKey<Dialog> key) {
        // Renvoie une valeur à l'appelant
        return new RegistryKeyDialog(key);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DialogLike wrap(Holder<Dialog> dialog) {
        // Renvoie une valeur à l'appelant
        return switch (dialog) {
            // Embranchement multiple (switch/case)
            case Dialog direct -> direct;
            // Embranchement multiple (switch/case)
            case RegistryKey<Dialog> reference -> new RegistryKeyDialog(reference);
            // Embranchement multiple (switch/case)
            default -> throw new IllegalArgumentException("Unsupported dialog type: " + dialog.getClass().getName());
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static Holder<Dialog> unwrap(DialogLike dialog) {
        // Renvoie une valeur à l'appelant
        return switch (dialog) {
            // Embranchement multiple (switch/case)
            case Dialog direct -> direct;
            // Embranchement multiple (switch/case)
            case RegistryKeyDialog reference -> reference.key();
            // Embranchement multiple (switch/case)
            default -> throw new IllegalArgumentException("Unsupported dialog type: " + dialog.getClass().getName());
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * <p>Creates a new registry for dialogs, loading the vanilla dialogs.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<Dialog> createDefaultRegistry(Registries registries) {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.createForDialogWithSelfReferentialLoadingNightmare(
                // Instruction de code
                Key.key("dialog"), REGISTRY_CODEC, RegistryData.Resource.DIALOGS, registries
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Notice(DialogMetadata metadata, DialogActionButton action) implements Dialog {
        // Appelle une méthode
        public static final DialogActionButton DEFAULT_ACTION = new DialogActionButton(Component.translatable("gui.ok"), null, 150, null);
        // Affecte une valeur
        public static final StructCodec<Notice> CODEC = StructCodec.struct(
                // Instruction de code
                StructCodec.INLINE, DialogMetadata.CODEC, Notice::metadata,
                // Instruction de code
                "action", DialogActionButton.CODEC.optional(DEFAULT_ACTION), Notice::action,
                // Instruction de code
                Notice::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Notice> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ServerLinks(
            // Instruction de code
            DialogMetadata metadata,
            // Annotation pour l'élément suivant
            @Nullable DialogActionButton exitAction,
            // Instruction de code
            int columns, int buttonWidth
    // Début d'une méthode/d'un bloc
    ) implements Dialog {
        // Affecte une valeur
        public static final StructCodec<ServerLinks> CODEC = StructCodec.struct(
                // Instruction de code
                StructCodec.INLINE, DialogMetadata.CODEC, ServerLinks::metadata,
                // Instruction de code
                "exit_action", DialogActionButton.CODEC.optional(), ServerLinks::exitAction,
                // Instruction de code
                "columns", Codec.INT.optional(2), ServerLinks::columns,
                // Instruction de code
                "button_width", Codec.INT.optional(150), ServerLinks::buttonWidth,
                // Instruction de code
                ServerLinks::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<ServerLinks> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record DialogList(
            // Instruction de code
            DialogMetadata metadata,
            // Instruction de code
            HolderSet<Dialog> dialogs,
            // Annotation pour l'élément suivant
            @Nullable DialogActionButton exitAction,
            // Instruction de code
            int columns, int buttonWidth
    // Début d'une méthode/d'un bloc
    ) implements Dialog {
        // Affecte une valeur
        public static final StructCodec<DialogList> CODEC = StructCodec.struct(
                // Instruction de code
                StructCodec.INLINE, DialogMetadata.CODEC, DialogList::metadata,
                // Instruction de code
                "dialogs", HolderSet.codec(Registries::dialog, Codec.ForwardRef(() -> Dialog.REGISTRY_CODEC)), DialogList::dialogs,
                // Instruction de code
                "exit_action", DialogActionButton.CODEC.optional(), DialogList::exitAction,
                // Instruction de code
                "columns", Codec.INT.optional(2), DialogList::columns,
                // Instruction de code
                "button_width", Codec.INT.optional(150), DialogList::buttonWidth,
                // Instruction de code
                DialogList::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<DialogList> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record MultiAction(
            // Instruction de code
            DialogMetadata metadata,
            // Instruction de code
            List<DialogActionButton> actions,
            // Annotation pour l'élément suivant
            @Nullable DialogActionButton exitAction,
            // Instruction de code
            int columns
    // Début d'une méthode/d'un bloc
    ) implements Dialog {
        // Affecte une valeur
        public static final StructCodec<MultiAction> CODEC = StructCodec.struct(
                // Instruction de code
                StructCodec.INLINE, DialogMetadata.CODEC, MultiAction::metadata,
                // Instruction de code
                "actions", DialogActionButton.CODEC.list(), MultiAction::actions,
                // Instruction de code
                "exit_action", DialogActionButton.CODEC.optional(), MultiAction::exitAction,
                // Instruction de code
                "columns", Codec.INT.optional(2), MultiAction::columns,
                // Instruction de code
                MultiAction::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<MultiAction> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Confirmation(
            // Instruction de code
            DialogMetadata metadata,
            // Instruction de code
            DialogActionButton yesButton,
            // Instruction de code
            DialogActionButton noButton
    // Début d'une méthode/d'un bloc
    ) implements Dialog {
        // Affecte une valeur
        public static final StructCodec<Confirmation> CODEC = StructCodec.struct(
                // Instruction de code
                StructCodec.INLINE, DialogMetadata.CODEC, Confirmation::metadata,
                // Instruction de code
                "yes", DialogActionButton.CODEC, Confirmation::yesButton,
                // Instruction de code
                "no", DialogActionButton.CODEC, Confirmation::noButton,
                // Instruction de code
                Confirmation::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Confirmation> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    DialogMetadata metadata();

    // Annotation pour l'élément suivant
    @ApiStatus.OverrideOnly
    // Appelle une méthode
    StructCodec<? extends Dialog> codec();

// Fin d'un bloc/d'une expression
}
