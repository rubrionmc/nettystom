// Package declaration for this file
package net.minestom.testing;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import org.junit.jupiter.api.extension.*;

// Type declaration (class/interface/enum/record)
final class EnvTestExt implements
        // Code statement
        BeforeEachCallback,
        // Code statement
        AfterEachCallback,
        // Start of a method/block
        ParameterResolver {

    // Assigns a value
    private static final String ENV_KEY = "minestom.env";

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void beforeEach(ExtensionContext context) {
        // Calls a method
        System.setProperty("minestom.viewable-packet", "false");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Code statement
    public Object resolveParameter(ParameterContext parameterContext,
                                   // Start of a method/block
                                   ExtensionContext extensionContext) {
        // Returns a value to the caller
        return extensionContext.getStore(ExtensionContext.Namespace.create(getClass()))
                // Code statement
                .getOrComputeIfAbsent(ENV_KEY,
                        // Code statement
                        key -> new EnvImpl(MinecraftServer.updateProcess()),
                        // Code statement
                        EnvImpl.class);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void afterEach(ExtensionContext context) {
        // Calls a method
        ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create(getClass()));
        // Calls a method
        EnvImpl env = store.remove(ENV_KEY, EnvImpl.class);
        // Branch: checks a condition
        if (env != null) env.cleanup();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Code statement
    public boolean supportsParameter(ParameterContext parameterContext,
                                     // Start of a method/block
                                     ExtensionContext extensionContext) {
        // Returns a value to the caller
        return parameterContext.getParameter().getType() == Env.class;
    // End of a block/expression
    }
// End of a block/expression
}
