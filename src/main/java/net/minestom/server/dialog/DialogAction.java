// Déclaration du paquet de ce fichier
package net.minestom.server.dialog;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Holder;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Map;

/**
 * <p>Represents an action button action in a dialog.</p>
 *
 * <p>Notably some of these actions are duplicates from click events on components.
 * Until adventure supports these properly they are duplicated.</p>
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface DialogAction {
    // Affecte une valeur
    Registry<StructCodec<? extends DialogAction>> REGISTRY = DynamicRegistry.fromMap(
            // Instruction de code
            Key.key("dialog_action_type"),
            // Instruction de code
            Map.entry(Key.key("open_url"), OpenUrl.CODEC),
            // Instruction de code
            Map.entry(Key.key("run_command"), RunCommand.CODEC),
            // Instruction de code
            Map.entry(Key.key("suggest_command"), SuggestCommand.CODEC),
            // Instruction de code
            Map.entry(Key.key("show_dialog"), ShowDialog.CODEC),
            // Instruction de code
            Map.entry(Key.key("change_page"), ChangePage.CODEC),
            // Instruction de code
            Map.entry(Key.key("copy_to_clipboard"), CopyToClipboard.CODEC),
            // Instruction de code
            Map.entry(Key.key("custom"), Custom.CODEC),
            // Instruction de code
            Map.entry(Key.key("dynamic/run_command"), DynamicRunCommand.CODEC),
            // Appelle une méthode
            Map.entry(Key.key("dynamic/custom"), DynamicCustom.CODEC));
    // Appelle une méthode
    StructCodec<DialogAction> CODEC = Codec.RegistryTaggedUnion(REGISTRY, DialogAction::codec);

    // Déclaration de type (classe/interface/enum/record)
    record OpenUrl(String url) implements DialogAction {
        // Affecte une valeur
        public static final StructCodec<OpenUrl> CODEC = StructCodec.struct(
                // Instruction de code
                "url", StructCodec.STRING, OpenUrl::url,
                // Instruction de code
                OpenUrl::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<OpenUrl> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record RunCommand(String command) implements DialogAction {
        // Affecte une valeur
        public static final StructCodec<RunCommand> CODEC = StructCodec.struct(
                // Instruction de code
                "command", StructCodec.STRING, RunCommand::command,
                // Instruction de code
                RunCommand::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<RunCommand> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record SuggestCommand(String command) implements DialogAction {
        // Affecte une valeur
        public static final StructCodec<SuggestCommand> CODEC = StructCodec.struct(
                // Instruction de code
                "command", StructCodec.STRING, SuggestCommand::command,
                // Instruction de code
                SuggestCommand::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<SuggestCommand> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ShowDialog(Holder<Dialog> dialog) implements DialogAction {
        // Affecte une valeur
        public static final StructCodec<ShowDialog> CODEC = StructCodec.struct(
                // Instruction de code
                "dialog", Holder.codec(Registries::dialog, Codec.ForwardRef(() -> Dialog.REGISTRY_CODEC)), ShowDialog::dialog,
                // Instruction de code
                ShowDialog::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<ShowDialog> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ChangePage(int page) implements DialogAction {
        // Affecte une valeur
        public static final StructCodec<ChangePage> CODEC = StructCodec.struct(
                // Instruction de code
                "page", StructCodec.INT, ChangePage::page,
                // Instruction de code
                ChangePage::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<ChangePage> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record CopyToClipboard(String value) implements DialogAction {
        // Affecte une valeur
        public static final StructCodec<CopyToClipboard> CODEC = StructCodec.struct(
                // Instruction de code
                "value", StructCodec.STRING, CopyToClipboard::value,
                // Instruction de code
                CopyToClipboard::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<CopyToClipboard> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Custom(Key key, @Nullable BinaryTag payload) implements DialogAction {
        // Affecte une valeur
        public static final StructCodec<Custom> CODEC = StructCodec.struct(
                // Instruction de code
                "id", Codec.KEY, Custom::key,
                // Instruction de code
                "payload", Codec.NBT.optional(), Custom::payload,
                // Instruction de code
                Custom::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Custom> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record DynamicRunCommand(String template) implements DialogAction {
        // Affecte une valeur
        public static final StructCodec<DynamicRunCommand> CODEC = StructCodec.struct(
                // Instruction de code
                "template", StructCodec.STRING, DynamicRunCommand::template,
                // Instruction de code
                DynamicRunCommand::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<DynamicRunCommand> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record DynamicCustom(Key key, @Nullable CompoundBinaryTag additions) implements DialogAction {
        // Affecte une valeur
        public static final StructCodec<DynamicCustom> CODEC = StructCodec.struct(
                // Instruction de code
                "id", Codec.KEY, DynamicCustom::key,
                // Instruction de code
                "additions", Codec.NBT_COMPOUND.optional(), DynamicCustom::additions,
                // Instruction de code
                DynamicCustom::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<DynamicCustom> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.OverrideOnly
    // Appelle une méthode
    StructCodec<? extends DialogAction> codec();
// Fin d'un bloc/d'une expression
}
