// Début d'une méthode/d'un bloc
module net.minestom.testing {
    // Instruction de code
    requires transitive net.minestom.server;
    // Instruction de code
    requires org.junit.jupiter.api; // Users can bring their own version.

    // Instruction de code
    exports net.minestom.testing;
    // Instruction de code
    exports net.minestom.testing.util;
// Fin d'un bloc/d'une expression
}