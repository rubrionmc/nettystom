// Déclaration du paquet de ce fichier
package net.minestom.codegen;

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
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public record ConstantsGenerator(InputStream constantsFile,
                                 // Début d'une méthode/d'un bloc
                                 Path outputFolder) implements MinestomCodeGenerator {
    // Début d'une méthode/d'un bloc
    public ConstantsGenerator {
        // Appelle une méthode
        Objects.requireNonNull(constantsFile, "Constants file cannot be null");
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
        final ClassName implCN = ClassName.get("net.minestom.server", "MinecraftServer");

        // Important classes we use alot
        // Appelle une méthode
        JsonObject constants = GSON.fromJson(new InputStreamReader(constantsFile), JsonObject.class);
        // Appelle une méthode
        ClassName minecraftConstantsCN = ClassName.get("net.minestom.server", "MinecraftConstants");
        // Affecte une valeur
        TypeSpec.Builder constantsInterface = TypeSpec.interfaceBuilder(minecraftConstantsCN)
                // Instruction de code
                .addModifiers(Modifier.SEALED)
                // Instruction de code
                .addPermittedSubclass(implCN)
                // Appelle une méthode
                .addJavadoc(generateJavadoc(implCN));

        // Instruction de code
        constantsInterface.addField(FieldSpec.builder(String.class, "VERSION_NAME")
                // Instruction de code
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                // Instruction de code
                .initializer("$S", constants.get("name").getAsString())
                // Instruction de code
                .build()
        // Fin d'un bloc/d'une expression
        );
        // Instruction de code
        constantsInterface.addField(FieldSpec.builder(TypeName.INT, "PROTOCOL_VERSION")
                // Instruction de code
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                // Instruction de code
                .initializer("$L", constants.get("protocol").getAsInt())
                // Instruction de code
                .build()
        // Fin d'un bloc/d'une expression
        );
        // Instruction de code
        constantsInterface.addField(FieldSpec.builder(TypeName.INT, "DATA_VERSION")
                // Instruction de code
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                // Instruction de code
                .initializer("$L", constants.get("world").getAsInt())
                // Instruction de code
                .build()
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addMajorMinorField(constantsInterface, "RESOURCE_PACK_VERSION", constants.get("resourcepack").getAsString());
        // Appelle une méthode
        addMajorMinorField(constantsInterface, "DATA_PACK_VERSION", constants.get("datapack").getAsString());

        // Write files to outputFolder
        // Instruction de code
        writeFiles(JavaFile.builder("net.minestom.server", constantsInterface.build())
                // Instruction de code
                .indent("    ")
                // Instruction de code
                .skipJavaLangImports(true)
                // Instruction de code
                .build()
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void addMajorMinorField(TypeSpec.Builder typeSpec, String name, String value) {
        // Appelle une méthode
        String[] parts = value.split("\\.");
        // Embranchement : vérifie une condition
        if (parts.length != 2) throw new IllegalArgumentException("Invalid version format for " + name + ": " + value);

        // Appelle une méthode
        var majorMinorClass = ClassName.get("net.minestom.server.utils", "MajorMinorVersion");
        // Instruction de code
        typeSpec.addField(FieldSpec.builder(majorMinorClass, name)
                                            // Instruction de code
                                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                            // Instruction de code
                                            .initializer("new $T($L, $L)", majorMinorClass, parts[0], parts[1])
                                            // Instruction de code
                                            .build()
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
