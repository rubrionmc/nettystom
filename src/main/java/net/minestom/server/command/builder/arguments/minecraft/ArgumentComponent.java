// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Import d'une classe nécessaire
import com.google.gson.JsonElement;
// Import d'une classe nécessaire
import com.google.gson.JsonParseException;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;
// Import d'une classe nécessaire
import net.minestom.server.utils.json.JsonUtil;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentComponent extends Argument<Component> {
    // Affecte une valeur
    public static final int INVALID_JSON_ERROR = 1;

    // Début d'une méthode/d'un bloc
    public ArgumentComponent(String id) {
        // Accès à l'objet courant/parent
        super(id, true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Component parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final Transcoder<JsonElement> coder = new RegistryTranscoder<>(Transcoder.JSON, MinecraftServer.process());
            // Appelle une méthode
            final Result<Component> result = Codec.COMPONENT.decode(coder, JsonUtil.fromJson(input));
            // Renvoie une valeur à l'appelant
            return switch (result) {
                // Embranchement multiple (switch/case)
                case Result.Ok(var component) -> component;
                // Embranchement multiple (switch/case)
                case Result.Error(var message) ->
                        // Lève une exception
                        throw new ArgumentSyntaxException("Failed to parse component: " + message, input, INVALID_JSON_ERROR);
            // Fin d'un bloc/d'une expression
            };
        // Début d'une méthode/d'un bloc
        } catch (JsonParseException e) {
            // Lève une exception
            throw new ArgumentSyntaxException("Invalid JSON", input, INVALID_JSON_ERROR);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.COMPONENT;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("Component<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
