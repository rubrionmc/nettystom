// Package declaration for this file
package net.minestom.codegen;

// Import of a required class
import com.google.gson.Gson;
// Import of a required class
import com.google.gson.GsonBuilder;
// Import of a required class
import com.google.gson.JsonArray;
// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import com.palantir.javapoet.AnnotationSpec;
// Import of a required class
import com.palantir.javapoet.ClassName;
// Import of a required class
import com.palantir.javapoet.CodeBlock;
// Import of a required class
import com.palantir.javapoet.JavaFile;
// Import of a required class
import com.palantir.javapoet.TypeSpec;
// Import of a required class
import net.minestom.data.MinestomData;

// Import of a required class
import javax.lang.model.SourceVersion;
// Import of a required class
import java.io.IOException;
// Import of a required class
import java.io.InputStream;
// Import of a required class
import java.io.InputStreamReader;
// Import of a required class
import java.io.Reader;
// Import of a required class
import java.nio.file.Files;
// Import of a required class
import java.nio.file.Path;
// Import of a required class
import java.util.Comparator;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Locale;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.stream.StreamSupport;

// Type declaration (class/interface/enum/record)
final class Codegen {
    // Calls a method
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    // Code statement
    private final Path outputFolder;

    // Start of a method/block
    Codegen(Path outputFolder) {
        // Access to the current/parent object
        this.outputFolder = Objects.requireNonNull(outputFolder, "Output folder cannot be null");
    // End of a block/expression
    }

    // Start of a method/block
    Path outputFolder() {
        // Returns a value to the caller
        return outputFolder;
    // End of a block/expression
    }

    // Start of a method/block
    InputStream resource(String name) {
        // Returns a value to the caller
        return Objects.requireNonNull(MinestomData.resource(name), "Cannot find resource: %s".formatted(name));
    // End of a block/expression
    }

    // Start of a method/block
    JsonObject objectResource(String name) {
        // Returns a value to the caller
        return parse(resource(name), JsonObject.class);
    // End of a block/expression
    }

    // Start of a method/block
    JsonArray arrayResource(String name) {
        // Returns a value to the caller
        return parse(resource(name), JsonArray.class);
    // End of a block/expression
    }

    // Start of a method/block
    List<JsonObject> orderedObjects(String resourceName) {
        // Returns a value to the caller
        return StreamSupport.stream(arrayResource(resourceName).spliterator(), true)
                // Code statement
                .map(JsonElement::getAsJsonObject)
                // Code statement
                .sorted(Comparator.comparingInt(object -> object.get("id").getAsInt()))
                // Calls a method
                .toList();
    // End of a block/expression
    }

    // Start of a method/block
    List<Map.Entry<String, JsonElement>> orderedEntries(String resourceName) {
        // Returns a value to the caller
        return objectResource(resourceName).entrySet().stream()
                // Code statement
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getAsJsonObject().get("id").getAsInt()))
                // Calls a method
                .toList();
    // End of a block/expression
    }

    // Start of a method/block
    String constantName(String namespace) {
        // Assigns a value
        String constant = namespaceShort(namespace)
                // Code statement
                .replaceFirst("brigadier:", "")
                // Code statement
                .replace(".", "_")
                // Calls a method
                .toUpperCase(Locale.ROOT);
        // Branch: checks a condition
        if (!SourceVersion.isName(constant)) {
            // Assigns a value
            constant = "_" + constant;
        // End of a block/expression
        }
        // Returns a value to the caller
        return constant;
    // End of a block/expression
    }

    // Start of a method/block
    String namespaceShort(String namespace) {
        // Returns a value to the caller
        return namespace.replaceFirst("minecraft:", "");
    // End of a block/expression
    }

    // Start of a method/block
    CodeBlock constantsJavadoc(ClassName forImpl) {
        // Returns a value to the caller
        return CodeBlock.builder()
                // Code statement
                .add("This class contains all the generated constants for {@link $T}\n<br>\n", forImpl)
                // Code statement
                .add("Code autogenerated, do not edit!")
                // Calls a method
                .build();
    // End of a block/expression
    }

    // Start of a method/block
    AnnotationSpec suppressUnused() {
        // Returns a value to the caller
        return AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "unused").build();
    // End of a block/expression
    }

    // Start of a method/block
    JavaFile javaFile(String packageName, TypeSpec type) {
        // Returns a value to the caller
        return JavaFile.builder(packageName, type)
                // Code statement
                .indent("    ")
                // Code statement
                .skipJavaLangImports(true)
                // Calls a method
                .build();
    // End of a block/expression
    }

    // Start of a method/block
    void write(JavaFile... files) {
        // Calls a method
        ensureOutputFolder();
        // Loop: repeats a block
        for (JavaFile javaFile : files) {
            // Exception handling
            try {
                // Calls a method
                javaFile.writeTo(outputFolder);
            // Start of a method/block
            } catch (IOException e) {
                // Throws an exception
                throw new IllegalStateException("Failed to write all the output!", e);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void ensureOutputFolder() {
        // Branch: checks a condition
        if (Files.isDirectory(outputFolder)) return;
        // Exception handling
        try {
            // Calls a method
            Files.createDirectories(outputFolder);
        // Start of a method/block
        } catch (IOException e) {
            // Throws an exception
            throw new IllegalStateException("Failed to create folder for %s".formatted(outputFolder), e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> T parse(InputStream inputStream, Class<T> type) {
        // Exception handling
        try (inputStream; Reader reader = new InputStreamReader(inputStream)) {
            // Returns a value to the caller
            return GSON.fromJson(reader, type);
        // Start of a method/block
        } catch (IOException e) {
            // Throws an exception
            throw new IllegalStateException("Failed to read generator input", e);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
