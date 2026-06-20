// Package declaration for this file
package net.minestom.codegen;

// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import com.palantir.javapoet.ClassName;
// Import of a required class
import com.palantir.javapoet.FieldSpec;
// Import of a required class
import com.palantir.javapoet.TypeName;
// Import of a required class
import com.palantir.javapoet.TypeSpec;

// Import of a required class
import javax.lang.model.element.Modifier;

// Type declaration (class/interface/enum/record)
record ConstantsGenerator(Codegen codegen) {

    // Start of a method/block
    void generate() {
        // Calls a method
        ClassName implCN = ClassName.get("net.minestom.server", "MinecraftServer");
        // Calls a method
        ClassName minecraftConstantsCN = ClassName.get("net.minestom.server", "MinecraftConstants");
        // Calls a method
        JsonObject constants = codegen.objectResource("constants.json");

        // Assigns a value
        TypeSpec.Builder constantsInterface = TypeSpec.interfaceBuilder(minecraftConstantsCN)
                // Code statement
                .addModifiers(Modifier.SEALED)
                // Code statement
                .addPermittedSubclass(implCN)
                // Calls a method
                .addJavadoc(codegen.constantsJavadoc(implCN));

        // Code statement
        constantsInterface.addField(FieldSpec.builder(String.class, "VERSION_NAME")
                // Code statement
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                // Code statement
                .initializer("$S", constants.get("name").getAsString())
                // Code statement
                .build()
        // End of a block/expression
        );
        // Code statement
        constantsInterface.addField(FieldSpec.builder(TypeName.INT, "PROTOCOL_VERSION")
                // Code statement
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                // Code statement
                .initializer("$L", constants.get("protocol").getAsInt())
                // Code statement
                .build()
        // End of a block/expression
        );
        // Code statement
        constantsInterface.addField(FieldSpec.builder(TypeName.INT, "DATA_VERSION")
                // Code statement
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                // Code statement
                .initializer("$L", constants.get("world").getAsInt())
                // Code statement
                .build()
        // End of a block/expression
        );
        // Calls a method
        addMajorMinorField(constantsInterface, "RESOURCE_PACK_VERSION", constants.get("resourcepack").getAsString());
        // Calls a method
        addMajorMinorField(constantsInterface, "DATA_PACK_VERSION", constants.get("datapack").getAsString());

        // Calls a method
        codegen.write(codegen.javaFile("net.minestom.server", constantsInterface.build()));
    // End of a block/expression
    }

    // Start of a method/block
    private static void addMajorMinorField(TypeSpec.Builder typeSpec, String name, String value) {
        // Calls a method
        String[] parts = value.split("\\.");
        // Branch: checks a condition
        if (parts.length != 2) throw new IllegalArgumentException("Invalid version format for " + name + ": " + value);

        // Calls a method
        ClassName majorMinorClass = ClassName.get("net.minestom.server.utils", "MajorMinorVersion");
        // Code statement
        typeSpec.addField(FieldSpec.builder(majorMinorClass, name)
                // Code statement
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                // Code statement
                .initializer("new $T($L, $L)", majorMinorClass, parts[0], parts[1])
                // Code statement
                .build()
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
