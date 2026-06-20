// Start of a method/block
module net.minestom.testing {
    // Code statement
    requires transitive net.minestom.server;
    // Code statement
    requires org.junit.jupiter.api; // Users can bring their own version.

    // Code statement
    exports net.minestom.testing;
    // Code statement
    exports net.minestom.testing.util;

    // Code statement
    opens net.minestom.testing to org.junit.platform.commons;
// End of a block/expression
}