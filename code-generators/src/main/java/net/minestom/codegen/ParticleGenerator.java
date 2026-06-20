// Package declaration for this file
package net.minestom.codegen;

// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import com.palantir.javapoet.ClassName;
// Import of a required class
import com.palantir.javapoet.FieldSpec;
// Import of a required class
import com.palantir.javapoet.TypeSpec;

// Import of a required class
import javax.lang.model.element.Modifier;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.regex.Pattern;

// Type declaration (class/interface/enum/record)
record ParticleGenerator(Codegen codegen) {
    // Calls a method
    private static final Pattern PASCAL_PATTERN = Pattern.compile("_([a-z])");

    // Start of a method/block
    void generate() {
        // Calls a method
        ClassName particleCN = ClassName.get("net.minestom.server.particle", "Particle");
        // Calls a method
        ClassName particleImplCN = ClassName.get("net.minestom.server.particle", "ParticleImpl");
        // Calls a method
        ClassName particlesCN = ClassName.get("net.minestom.server.particle", "Particles");

        // Assigns a value
        TypeSpec.Builder particlesInterface = TypeSpec.interfaceBuilder(particlesCN)
                // Code statement
                .addModifiers(Modifier.SEALED)
                // Code statement
                .addPermittedSubclass(particleCN)
                // Calls a method
                .addJavadoc(codegen.constantsJavadoc(particleCN));

        // Loop: repeats a block
        for (Map.Entry<String, JsonElement> particleIdObjectEntry : codegen.orderedEntries("particle.json")) {
            // Calls a method
            final String key = particleIdObjectEntry.getKey();
            // Calls a method
            final JsonObject value = particleIdObjectEntry.getValue().getAsJsonObject();
            // Calls a method
            final String namespacedName = codegen.namespaceShort(key);

            // Assigns a value
            final ClassName fieldCN = value.get("hasData").getAsBoolean()
                    // Code statement
                    ? ClassName.get("net.minestom.server.particle", "Particle", toPascalCase(namespacedName))
                    // Code statement
                    : particleCN;

            // Code statement
            particlesInterface.addField(FieldSpec.builder(fieldCN, codegen.constantName(key))
                    // Code statement
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    // Code statement
                    .initializer("$T.get($S)", particleImplCN, key)
                    // Calls a method
                    .build());
        // End of a block/expression
        }

        // Calls a method
        codegen.write(codegen.javaFile("net.minestom.server.particle", particlesInterface.build()));
    // End of a block/expression
    }

    // Start of a method/block
    private static String toPascalCase(String input) {
        // Assigns a value
        String camelCase = PASCAL_PATTERN
                // Code statement
                .matcher(input)
                // Calls a method
                .replaceAll(match -> match.group(1).toUpperCase());
        // Returns a value to the caller
        return camelCase.substring(0, 1).toUpperCase() + camelCase.substring(1);
    // End of a block/expression
    }
// End of a block/expression
}
