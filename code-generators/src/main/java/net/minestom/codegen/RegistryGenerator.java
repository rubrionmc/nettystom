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
public record RegistryGenerator(Path outputFolder) implements MinestomCodeGenerator {
    // Début d'une méthode/d'un bloc
    public RegistryGenerator {
        // Appelle une méthode
        Objects.requireNonNull(outputFolder, "Output folder cannot be null");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void generate(InputStream resourceFile, String packageName, String typeName, String loaderName, String generatedName) {
        // Appelle une méthode
        ensureDirectory(outputFolder);

        // Appelle une méthode
        ClassName typeClass = ClassName.get(packageName, typeName);
        // Appelle une méthode
        ClassName loaderClass = ClassName.get(packageName, loaderName);
        // Appelle une méthode
        JsonObject json = GSON.fromJson(new InputStreamReader(resourceFile), JsonObject.class);
        // Appelle une méthode
        ClassName generatedCN = ClassName.get(packageName, generatedName);
        // BlockConstants class
        // Affecte une valeur
        TypeSpec.Builder blockConstantsClass = TypeSpec.interfaceBuilder(generatedCN)
                // Add @SuppressWarnings("unused")
                // Instruction de code
                .addModifiers(Modifier.SEALED)
                // Instruction de code
                .addPermittedSubclass(typeClass)
                // Instruction de code
                .addAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "unused").build())
                // Appelle une méthode
                .addJavadoc(generateJavadoc(typeClass));

        // Use data
        // Début d'une méthode/d'un bloc
        json.keySet().forEach(namespace -> {
            // Appelle une méthode
            final String constantName = toConstant(namespace);
            // Appelle une méthode
            final String namespaceString = namespaceShort(namespace);
            // Instruction de code
            blockConstantsClass.addField(
                    // Instruction de code
                    FieldSpec.builder(typeClass, constantName)
                            // Instruction de code
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            // Instruction de code
                            .initializer(
                                    // TypeClass.CONSTANT_NAME = LoaderClass.get(namespaceString)
                                    // Instruction de code
                                    "$T.get($S)",
                                    // Instruction de code
                                    loaderClass,
                                    // Instruction de code
                                    namespaceString
                            // Fin d'un bloc/d'une expression
                            )
                            // Instruction de code
                            .build()
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        });
        // Instruction de code
        writeFiles(JavaFile.builder(packageName, blockConstantsClass.build())
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
    public void generateKeys(InputStream resourceFile, String packageName, String typeName) {
        // Appelle une méthode
        ensureDirectory(outputFolder);

        // Affecte une valeur
        ClassName typeClass = ClassName.bestGuess(packageName + "." + typeName); // Use bestGuess to handle nested class
        // Appelle une méthode
        ClassName registryKeyClass = ClassName.get("net.minestom.server.registry", "RegistryKey");
        // Appelle une méthode
        ParameterizedTypeName typedRegistryKeyClass = ParameterizedTypeName.get(registryKeyClass, typeClass);

        // Appelle une méthode
        JsonObject json = GSON.fromJson(new InputStreamReader(resourceFile), JsonObject.class);
        // Appelle une méthode
        ClassName generatedCN = ClassName.get(packageName, typeName + "s");
        // BlockConstants class
        // Affecte une valeur
        TypeSpec.Builder blockConstantsClass = TypeSpec.interfaceBuilder(generatedCN)
                // Add @SuppressWarnings("unused")
                // Instruction de code
                .addModifiers(Modifier.SEALED)
                // Instruction de code
                .addPermittedSubclass(typeClass)
                // Instruction de code
                .addAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "unused").build())
                // Appelle une méthode
                .addJavadoc(generateJavadoc(typeClass));

        // Use data
        // Début d'une méthode/d'un bloc
        json.keySet().forEach(namespace -> {
            // Appelle une méthode
            final String constantName = toConstant(namespace);
            // Appelle une méthode
            final String namespaceString = namespaceShort(namespace);
            // Instruction de code
            blockConstantsClass.addField(
                    // Instruction de code
                    FieldSpec.builder(typedRegistryKeyClass, constantName)
                            // Instruction de code
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            // Instruction de code
                            .initializer(
                                    // RegistryKey<Biome> CONSTANT_NAME = RegistryKey.unsafeOf(nameSpaceString)
                                    // Instruction de code
                                    "$T.unsafeOf($S)",
                                    // Instruction de code
                                    registryKeyClass,
                                    // Instruction de code
                                    namespaceString
                            // Fin d'un bloc/d'une expression
                            )
                            // Instruction de code
                            .build()
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        });

        // Write files
        // Instruction de code
        writeFiles(JavaFile.builder(packageName, blockConstantsClass.build())
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

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void generate() {
        // Lève une exception
        throw new UnsupportedOperationException("Use generate(InputStream, String, String, String, String) instead");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
