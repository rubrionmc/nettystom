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
import java.util.Comparator;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.regex.Pattern;

// Déclaration de type (classe/interface/enum/record)
public record ParticleGenerator(InputStream particleFile,
                                // Début d'une méthode/d'un bloc
                                Path outputFolder) implements MinestomCodeGenerator {
    // Appelle une méthode
    public static final Pattern PASCAL_PATTERN = Pattern.compile("_([a-z])");

    // Début d'une méthode/d'un bloc
    public ParticleGenerator {
        // Appelle une méthode
        Objects.requireNonNull(particleFile, "Particle file cannot be null");
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

        // Important classes we use alot
        // Appelle une méthode
        ClassName particleCN = ClassName.get("net.minestom.server.particle", "Particle");
        // Appelle une méthode
        ClassName particleImplCN = ClassName.get("net.minestom.server.particle", "ParticleImpl");

        // Appelle une méthode
        JsonObject particleObject = GSON.fromJson(new InputStreamReader(particleFile), JsonObject.class);
        // Affecte une valeur
        List<Map.Entry<String, JsonElement>> orderedParticleIdObjectEntries = particleObject.entrySet().stream()
                // Appelle une méthode
                .sorted(Comparator.comparingInt(o -> o.getValue().getAsJsonObject().get("id").getAsInt())).toList();

        // Start code gen
        // Appelle une méthode
        ClassName particlesCN = ClassName.get("net.minestom.server.particle", "Particles");
        // Affecte une valeur
        TypeSpec.Builder particlesInterface = TypeSpec.interfaceBuilder(particlesCN)
                // Instruction de code
                .addModifiers(Modifier.SEALED)
                // Instruction de code
                .addPermittedSubclass(particleCN)
                // Appelle une méthode
                .addJavadoc(generateJavadoc(particleCN));

        // Boucle : répète un bloc
        for (Map.Entry<String, JsonElement> particleIdObjectEntry : orderedParticleIdObjectEntries) {
            // Appelle une méthode
            final String key = particleIdObjectEntry.getKey();
            // Appelle une méthode
            final JsonObject value = particleIdObjectEntry.getValue().getAsJsonObject();
            // Appelle une méthode
            final String namespacedName = namespaceShort(key);

            // Instruction de code
            final ClassName fieldCN;
            // Instruction de code
            final CodeBlock cast;
            // Embranchement : vérifie une condition
            if (value.get("hasData").getAsBoolean()) {
                // This particle has data, use the particle implementation class
                // Affecte une valeur
                fieldCN = ClassName.get("net.minestom.server.particle", "Particle",
                        // Appelle une méthode
                        toPascalCase(namespacedName));
                // Appelle une méthode
                cast = CodeBlock.of("($T) ", fieldCN);
            // Branche alternative de la condition
            } else {
                // Affecte une valeur
                fieldCN = particleCN;
                // Affecte une valeur
                cast = CodeBlock.builder().build(); // Empty cast for particles without data
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            String fieldName = toConstant(key);

            // Instruction de code
            particlesInterface.addField(FieldSpec.builder(fieldCN, fieldName)
                    // Instruction de code
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    // Appelle une méthode
                    .initializer("$L$T.get($S)", cast, particleImplCN, key).build());
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        writeFiles(JavaFile.builder("net.minestom.server.particle", particlesInterface.build())
                // Instruction de code
                .indent("    ")
                // Instruction de code
                .skipJavaLangImports(true)
                // Appelle une méthode
                .build());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static String toPascalCase(String input) {
        // Affecte une valeur
        String camelCase = PASCAL_PATTERN
                // Instruction de code
                .matcher(input)
                // Appelle une méthode
                .replaceAll(m -> m.group(1).toUpperCase());
        // Renvoie une valeur à l'appelant
        return camelCase.substring(0, 1).toUpperCase() + camelCase.substring(1);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
