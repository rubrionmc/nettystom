// Package declaration for this file
package net.minestom.codegen;

// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import com.palantir.javapoet.*;

// Import of a required class
import javax.lang.model.element.Modifier;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record GameRuleGenerator(Codegen codegen) {
    // Assigns a value
    static final String PACKAGE = "net.minestom.server.instance.gamerule";
    // Calls a method
    static final ClassName GAME_RULE_CN = ClassName.get(PACKAGE, "GameRule");
    // Calls a method
    static final ClassName GAME_RULE_IMPL_CN = ClassName.get(PACKAGE, "GameRuleImpl");
    // Calls a method
    static final ClassName GAME_RULES_CN = ClassName.get(PACKAGE, "GameRules");

    // Start of a method/block
    public GameRuleGenerator {
        // Calls a method
        Objects.requireNonNull(codegen, "codegen cannot be null");
    // End of a block/expression
    }

    // Start of a method/block
    void generate() {
        // Calls a method
        JsonObject gameRules = codegen.objectResource("game_rule.json");

        // Start code gen
        // Assigns a value
        TypeSpec.Builder gameRulesInterface = TypeSpec.interfaceBuilder(GAME_RULES_CN)
                // Code statement
                .addModifiers(Modifier.SEALED)
                // Code statement
                .addPermittedSubclass(GAME_RULE_CN)
                // Calls a method
                .addJavadoc(codegen.constantsJavadoc(GAME_RULE_CN));

        // Loop: repeats a block
        for (Map.Entry<String, JsonElement> particleIdObjectEntry : gameRules.entrySet()) {
            // Calls a method
            final String key = particleIdObjectEntry.getKey();
            // Calls a method
            final JsonObject value = particleIdObjectEntry.getValue().getAsJsonObject();

            // Calls a method
            final String type = value.get("type").getAsString();

            // Assigns a value
            final ParameterizedTypeName fieldCN = switch (type) {
                // Multiple branching (switch/case)
                case "boolean" -> ParameterizedTypeName.get(GAME_RULE_CN, ClassName.get(Boolean.class));
                // Multiple branching (switch/case)
                case "integer" -> ParameterizedTypeName.get(GAME_RULE_CN, ClassName.get(Integer.class));
                // Multiple branching (switch/case)
                default -> throw new IllegalArgumentException("Unknown type: " + type);
            // End of a block/expression
            };

            // Calls a method
            String fieldName = codegen.constantName(key);
            // Calls a method
            String namespacedName = codegen.namespaceShort(key);

            // Code statement
            gameRulesInterface.addField(FieldSpec.builder(fieldCN, fieldName)
                    // Code statement
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    // Calls a method
                    .initializer("$T.get($S)", GAME_RULE_IMPL_CN, namespacedName).build());
        // End of a block/expression
        }

        // Calls a method
        codegen.write(codegen.javaFile(PACKAGE, gameRulesInterface.build()));
    // End of a block/expression
    }
// End of a block/expression
}
