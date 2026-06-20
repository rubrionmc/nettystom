// Package declaration for this file
package net.minestom.server.dialog;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Holder;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Map;

/**
 * <p>Represents an action button action in a dialog.</p>
 *
 * <p>Notably some of these actions are duplicates from click events on components.
 * Until adventure supports these properly they are duplicated.</p>
 */
// Type declaration (class/interface/enum/record)
public sealed interface DialogAction {
    // Assigns a value
    Registry<StructCodec<? extends DialogAction>> REGISTRY = DynamicRegistry.fromMap(
            // Code statement
            Key.key("dialog_action_type"),
            // Code statement
            Map.entry(Key.key("open_url"), OpenUrl.CODEC),
            // Code statement
            Map.entry(Key.key("run_command"), RunCommand.CODEC),
            // Code statement
            Map.entry(Key.key("suggest_command"), SuggestCommand.CODEC),
            // Code statement
            Map.entry(Key.key("show_dialog"), ShowDialog.CODEC),
            // Code statement
            Map.entry(Key.key("change_page"), ChangePage.CODEC),
            // Code statement
            Map.entry(Key.key("copy_to_clipboard"), CopyToClipboard.CODEC),
            // Code statement
            Map.entry(Key.key("custom"), Custom.CODEC),
            // Code statement
            Map.entry(Key.key("dynamic/run_command"), DynamicRunCommand.CODEC),
            // Calls a method
            Map.entry(Key.key("dynamic/custom"), DynamicCustom.CODEC));
    // Calls a method
    StructCodec<DialogAction> CODEC = Codec.RegistryTaggedUnion(REGISTRY, DialogAction::codec);

    // Type declaration (class/interface/enum/record)
    record OpenUrl(String url) implements DialogAction {
        // Assigns a value
        public static final StructCodec<OpenUrl> CODEC = StructCodec.struct(
                // Code statement
                "url", StructCodec.STRING, OpenUrl::url,
                // Code statement
                OpenUrl::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<OpenUrl> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record RunCommand(String command) implements DialogAction {
        // Assigns a value
        public static final StructCodec<RunCommand> CODEC = StructCodec.struct(
                // Code statement
                "command", StructCodec.STRING, RunCommand::command,
                // Code statement
                RunCommand::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<RunCommand> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record SuggestCommand(String command) implements DialogAction {
        // Assigns a value
        public static final StructCodec<SuggestCommand> CODEC = StructCodec.struct(
                // Code statement
                "command", StructCodec.STRING, SuggestCommand::command,
                // Code statement
                SuggestCommand::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<SuggestCommand> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ShowDialog(Holder<Dialog> dialog) implements DialogAction {
        // Assigns a value
        public static final StructCodec<ShowDialog> CODEC = StructCodec.struct(
                // Code statement
                "dialog", Holder.codec(Registries::dialog, Codec.ForwardRef(() -> Dialog.REGISTRY_CODEC)), ShowDialog::dialog,
                // Code statement
                ShowDialog::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<ShowDialog> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ChangePage(int page) implements DialogAction {
        // Assigns a value
        public static final StructCodec<ChangePage> CODEC = StructCodec.struct(
                // Code statement
                "page", StructCodec.INT, ChangePage::page,
                // Code statement
                ChangePage::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<ChangePage> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record CopyToClipboard(String value) implements DialogAction {
        // Assigns a value
        public static final StructCodec<CopyToClipboard> CODEC = StructCodec.struct(
                // Code statement
                "value", StructCodec.STRING, CopyToClipboard::value,
                // Code statement
                CopyToClipboard::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<CopyToClipboard> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Custom(Key key, @Nullable BinaryTag payload) implements DialogAction {
        // Assigns a value
        public static final StructCodec<Custom> CODEC = StructCodec.struct(
                // Code statement
                "id", Codec.KEY, Custom::key,
                // Code statement
                "payload", Codec.NBT.optional(), Custom::payload,
                // Code statement
                Custom::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Custom> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record DynamicRunCommand(String template) implements DialogAction {
        // Assigns a value
        public static final StructCodec<DynamicRunCommand> CODEC = StructCodec.struct(
                // Code statement
                "template", StructCodec.STRING, DynamicRunCommand::template,
                // Code statement
                DynamicRunCommand::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<DynamicRunCommand> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record DynamicCustom(Key key, @Nullable CompoundBinaryTag additions) implements DialogAction {
        // Assigns a value
        public static final StructCodec<DynamicCustom> CODEC = StructCodec.struct(
                // Code statement
                "id", Codec.KEY, DynamicCustom::key,
                // Code statement
                "additions", Codec.NBT_COMPOUND.optional(), DynamicCustom::additions,
                // Code statement
                DynamicCustom::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<DynamicCustom> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.OverrideOnly
    // Calls a method
    StructCodec<? extends DialogAction> codec();
// End of a block/expression
}
