// Début d'une méthode/d'un bloc
module net.minestom.testing.test {
    // Instruction de code
    requires org.junit.jupiter.api;

    // Instruction de code
    requires net.minestom.testing;

    // Instruction de code
    opens net.minestom.testing.test to org.junit.platform.commons;
// Fin d'un bloc/d'une expression
}