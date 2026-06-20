// Package declaration for this file
package net.minestom.codegen;

// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import com.palantir.javapoet.*;

// Import of a required class
import javax.lang.model.element.Modifier;
// Import of a required class
import java.util.function.Function;

// Type declaration (class/interface/enum/record)
record RegistryGenerator(Codegen codegen) {

    // Start of a method/block
    void generate(Generators.StaticRegistrySpec spec) {
        // Calls a method
        ClassName typeClass = ClassName.get(spec.packageName(), spec.typeName());
        // Calls a method
        ClassName loaderClass = ClassName.get(spec.packageName(), spec.loaderName());
        // Code statement
        generateConstants(
                // Code statement
                spec.resource(),
                // Code statement
                typeClass,
                // Code statement
                ClassName.get(spec.packageName(), spec.generatedName()),
                // Code statement
                typeClass,
                // Code statement
                namespace -> CodeBlock.of("$T.get($S)", loaderClass, codegen.namespaceShort(namespace))
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Start of a method/block
    void generate(Generators.DynamicRegistrySpec spec) {
        // Calls a method
        ClassName typeClass = ClassName.bestGuess(spec.packageName() + "." + spec.typeName());
        // Calls a method
        ClassName registryKeyClass = ClassName.get("net.minestom.server.registry", "RegistryKey");
        // Code statement
        generateConstants(
                // Code statement
                spec.resource(),
                // Code statement
                typeClass,
                // Code statement
                ClassName.get(spec.packageName(), spec.generatedName()),
                // Code statement
                ParameterizedTypeName.get(registryKeyClass, typeClass),
                // Code statement
                namespace -> CodeBlock.of("$T.unsafeOf($S)", registryKeyClass, codegen.namespaceShort(namespace))
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Code statement
    private void generateConstants(String resource, ClassName permittedType, ClassName generatedClass,
                                   // Start of a method/block
                                   TypeName fieldType, Function<String, CodeBlock> initializer) {
        // Calls a method
        JsonObject json = codegen.objectResource(resource);
        // Assigns a value
        TypeSpec.Builder constants = TypeSpec.interfaceBuilder(generatedClass)
                // Code statement
                .addModifiers(Modifier.SEALED)
                // Code statement
                .addPermittedSubclass(permittedType)
                // Code statement
                .addAnnotation(codegen.suppressUnused())
                // Calls a method
                .addJavadoc(codegen.constantsJavadoc(permittedType));

        // Code statement
        json.keySet().forEach(namespace -> constants.addField(
                // Code statement
                FieldSpec.builder(fieldType, codegen.constantName(namespace))
                        // Code statement
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        // Code statement
                        .initializer(initializer.apply(namespace))
                        // Code statement
                        .build()
        // Code statement
        ));

        // Calls a method
        codegen.write(codegen.javaFile(generatedClass.packageName(), constants.build()));
    // End of a block/expression
    }
// End of a block/expression
}
