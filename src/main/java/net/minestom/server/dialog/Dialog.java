// Package declaration for this file
package net.minestom.server.dialog;

// Import of a required class
import net.kyori.adventure.dialog.DialogLike;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.*;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public sealed interface Dialog extends Holder.Direct<Dialog>, DialogLike {
    // Assigns a value
    Registry<StructCodec<? extends Dialog>> REGISTRY = DynamicRegistry.fromMap(
            // Code statement
            Key.key("dialog_type"),
            // Code statement
            Map.entry(Key.key("notice"), Notice.CODEC),
            // Code statement
            Map.entry(Key.key("server_links"), ServerLinks.CODEC),
            // Code statement
            Map.entry(Key.key("dialog_list"), DialogList.CODEC),
            // Code statement
            Map.entry(Key.key("multi_action"), MultiAction.CODEC),
            // Calls a method
            Map.entry(Key.key("confirmation"), Confirmation.CODEC));
    // Calls a method
    StructCodec<Dialog> REGISTRY_CODEC = Codec.RegistryTaggedUnion(REGISTRY, Dialog::codec);
    // Calls a method
    NetworkBuffer.Type<Dialog> REGISTRY_NETWORK_TYPE = NetworkBuffer.TypedNBT(REGISTRY_CODEC);

    // Calls a method
    NetworkBuffer.Type<Holder<Dialog>> NETWORK_TYPE = Holder.networkType(Registries::dialog, REGISTRY_NETWORK_TYPE);
    // Calls a method
    Codec<Holder<Dialog>> CODEC = Holder.codec(Registries::dialog, REGISTRY_CODEC);

    /**
     * <p>Creates a new adventure {@link DialogLike} for the dialog at the given key.</p>
     *
     * <p>Useful for sending a dialog which has been pre-sent to the client in the Dialog registry.</p>
     *
     * @param key the key of the dialog (must be registered)
     * @return a new {@link DialogLike} for the dialog at the given key
     */
    // Start of a method/block
    static DialogLike forKey(RegistryKey<Dialog> key) {
        // Returns a value to the caller
        return new RegistryKeyDialog(key);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DialogLike wrap(Holder<Dialog> dialog) {
        // Returns a value to the caller
        return switch (dialog) {
            // Multiple branching (switch/case)
            case Dialog direct -> direct;
            // Multiple branching (switch/case)
            case RegistryKey<Dialog> reference -> new RegistryKeyDialog(reference);
            // Multiple branching (switch/case)
            default -> throw new IllegalArgumentException("Unsupported dialog type: " + dialog.getClass().getName());
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static Holder<Dialog> unwrap(DialogLike dialog) {
        // Returns a value to the caller
        return switch (dialog) {
            // Multiple branching (switch/case)
            case Dialog direct -> direct;
            // Multiple branching (switch/case)
            case RegistryKeyDialog reference -> reference.key();
            // Multiple branching (switch/case)
            default -> throw new IllegalArgumentException("Unsupported dialog type: " + dialog.getClass().getName());
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * <p>Creates a new registry for dialogs, loading the vanilla dialogs.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<Dialog> createDefaultRegistry(Registries registries) {
        // Returns a value to the caller
        return DynamicRegistry.createForDialogWithSelfReferentialLoadingNightmare(
                // Code statement
                Key.key("dialog"), REGISTRY_CODEC, RegistryData.Resource.DIALOGS, registries
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Notice(DialogMetadata metadata, DialogActionButton action) implements Dialog {
        // Calls a method
        public static final DialogActionButton DEFAULT_ACTION = new DialogActionButton(Component.translatable("gui.ok"), null, 150, null);
        // Assigns a value
        public static final StructCodec<Notice> CODEC = StructCodec.struct(
                // Code statement
                StructCodec.INLINE, DialogMetadata.CODEC, Notice::metadata,
                // Code statement
                "action", DialogActionButton.CODEC.optional(DEFAULT_ACTION), Notice::action,
                // Code statement
                Notice::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Notice> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ServerLinks(
            // Code statement
            DialogMetadata metadata,
            // Annotation for the following element
            @Nullable DialogActionButton exitAction,
            // Code statement
            int columns, int buttonWidth
    // Start of a method/block
    ) implements Dialog {
        // Assigns a value
        public static final StructCodec<ServerLinks> CODEC = StructCodec.struct(
                // Code statement
                StructCodec.INLINE, DialogMetadata.CODEC, ServerLinks::metadata,
                // Code statement
                "exit_action", DialogActionButton.CODEC.optional(), ServerLinks::exitAction,
                // Code statement
                "columns", Codec.INT.optional(2), ServerLinks::columns,
                // Code statement
                "button_width", Codec.INT.optional(150), ServerLinks::buttonWidth,
                // Code statement
                ServerLinks::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<ServerLinks> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record DialogList(
            // Code statement
            DialogMetadata metadata,
            // Code statement
            HolderSet<Dialog> dialogs,
            // Annotation for the following element
            @Nullable DialogActionButton exitAction,
            // Code statement
            int columns, int buttonWidth
    // Start of a method/block
    ) implements Dialog {
        // Assigns a value
        public static final StructCodec<DialogList> CODEC = StructCodec.struct(
                // Code statement
                StructCodec.INLINE, DialogMetadata.CODEC, DialogList::metadata,
                // Code statement
                "dialogs", HolderSet.codec(Registries::dialog, Codec.ForwardRef(() -> Dialog.REGISTRY_CODEC)), DialogList::dialogs,
                // Code statement
                "exit_action", DialogActionButton.CODEC.optional(), DialogList::exitAction,
                // Code statement
                "columns", Codec.INT.optional(2), DialogList::columns,
                // Code statement
                "button_width", Codec.INT.optional(150), DialogList::buttonWidth,
                // Code statement
                DialogList::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<DialogList> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record MultiAction(
            // Code statement
            DialogMetadata metadata,
            // Code statement
            List<DialogActionButton> actions,
            // Annotation for the following element
            @Nullable DialogActionButton exitAction,
            // Code statement
            int columns
    // Start of a method/block
    ) implements Dialog {
        // Assigns a value
        public static final StructCodec<MultiAction> CODEC = StructCodec.struct(
                // Code statement
                StructCodec.INLINE, DialogMetadata.CODEC, MultiAction::metadata,
                // Code statement
                "actions", DialogActionButton.CODEC.list(), MultiAction::actions,
                // Code statement
                "exit_action", DialogActionButton.CODEC.optional(), MultiAction::exitAction,
                // Code statement
                "columns", Codec.INT.optional(2), MultiAction::columns,
                // Code statement
                MultiAction::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<MultiAction> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Confirmation(
            // Code statement
            DialogMetadata metadata,
            // Code statement
            DialogActionButton yesButton,
            // Code statement
            DialogActionButton noButton
    // Start of a method/block
    ) implements Dialog {
        // Assigns a value
        public static final StructCodec<Confirmation> CODEC = StructCodec.struct(
                // Code statement
                StructCodec.INLINE, DialogMetadata.CODEC, Confirmation::metadata,
                // Code statement
                "yes", DialogActionButton.CODEC, Confirmation::yesButton,
                // Code statement
                "no", DialogActionButton.CODEC, Confirmation::noButton,
                // Code statement
                Confirmation::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Confirmation> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Calls a method
    DialogMetadata metadata();

    // Annotation for the following element
    @ApiStatus.OverrideOnly
    // Calls a method
    StructCodec<? extends Dialog> codec();

// End of a block/expression
}
