// Déclaration du paquet de ce fichier
package net.minestom.codegen;

// Import d'une classe nécessaire
import com.google.gson.JsonElement;
// Import d'une classe nécessaire
import com.google.gson.JsonObject;
// Import d'une classe nécessaire
import com.palantir.javapoet.*;

// Import d'une classe nécessaire
import javax.lang.model.element.Modifier;
// Import d'une classe nécessaire
import java.io.InputStream;
// Import d'une classe nécessaire
import java.io.InputStreamReader;
// Import d'une classe nécessaire
import java.nio.file.Path;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public record GameRuleGenerator(InputStream gameRulesFile,
                                // Début d'une méthode/d'un bloc
                                Path outputFolder) implements MinestomCodeGenerator {
    // Affecte une valeur
    static final String PACKAGE = "net.minestom.server.instance.gamerule";
    // Appelle une méthode
    static final ClassName GAME_RULE_CN = ClassName.get(PACKAGE, "GameRule");
    // Appelle une méthode
    static final ClassName GAME_RULE_IMPL_CN = ClassName.get(PACKAGE, "GameRuleImpl");
    // Appelle une méthode
    static final ClassName GAME_RULES_CN = ClassName.get(PACKAGE, "GameRules");

    // Début d'une méthode/d'un bloc
    public GameRuleGenerator {
        // Appelle une méthode
        Objects.requireNonNull(gameRulesFile, "Gamerules file cannot be null");
        // Appelle une méthode
        Objects.requireNonNull(outputFolder, "Output folder cannot be null");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void generate() {
        // Appelle une méthode
        ensureDirectory(outputFolder);

        // Appelle une méthode
        JsonObject gameRules = GSON.fromJson(new InputStreamReader(gameRulesFile), JsonObject.class);

        // Start code gen
        // Affecte une valeur
        TypeSpec.Builder gameRulesInterface = TypeSpec.interfaceBuilder(GAME_RULES_CN)
                // Instruction de code
                .addModifiers(Modifier.SEALED)
                // Instruction de code
                .addPermittedSubclass(GAME_RULE_CN)
                // Appelle une méthode
                .addJavadoc(generateJavadoc(GAME_RULE_CN));

        // Boucle : répète un bloc
        for (Map.Entry<String, JsonElement> particleIdObjectEntry : gameRules.entrySet()) {
            // Appelle une méthode
            final String key = particleIdObjectEntry.getKey();
            // Appelle une méthode
            final JsonObject value = particleIdObjectEntry.getValue().getAsJsonObject();

            // Appelle une méthode
            final String type = value.get("type").getAsString();

            // Affecte une valeur
            final ParameterizedTypeName fieldCN = switch (type) {
                // Embranchement multiple (switch/case)
                case "boolean" -> ParameterizedTypeName.get(GAME_RULE_CN, ClassName.get(Boolean.class));
                // Embranchement multiple (switch/case)
                case "integer" -> ParameterizedTypeName.get(GAME_RULE_CN, ClassName.get(Integer.class));
                // Embranchement multiple (switch/case)
                default -> throw new IllegalArgumentException("Unknown type: " + type);
            // Fin d'un bloc/d'une expression
            };

            // Appelle une méthode
            String fieldName = toConstant(key);
            // Appelle une méthode
            String namespacedName = namespaceShort(key);

            // Instruction de code
            gameRulesInterface.addField(FieldSpec.builder(fieldCN, fieldName)
                    // Instruction de code
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    // Appelle une méthode
                    .initializer("$T.get($S)", GAME_RULE_IMPL_CN, namespacedName).build());
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        writeFiles(JavaFile.builder(PACKAGE, gameRulesInterface.build())
                // Instruction de code
                .indent("    ")
                // Instruction de code
                .skipJavaLangImports(true)
                // Appelle une méthode
                .build());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
