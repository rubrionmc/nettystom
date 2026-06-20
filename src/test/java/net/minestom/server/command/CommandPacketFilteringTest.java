// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.UUID;

// Annotation pour l'élément suivant
@SuppressWarnings("ConstantConditions")
// Déclaration de type (classe/interface/enum/record)
public class CommandPacketFilteringTest {
    // Appelle une méthode
    private static final Player PLAYER = new Player(null, new GameProfile(UUID.randomUUID(), "Test"));

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandFilteredFalse() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.setCondition(((sender, commandString) -> false));
        // Appelle une méthode
        assertFiltering(foo, "");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandFilteredTrue() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.setCondition(((sender, commandString) -> true));
        // Instruction de code
        assertFiltering(foo, """
                foo=%
                0->foo
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandUnfiltered() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Instruction de code
        assertFiltering(foo, """
                foo=%
                0->foo
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandFilteredTrueWithFilteredSubcommandTrueWithFilteredSyntaxFalse() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.setCondition((sender, commandString) -> true);
        // Appelle une méthode
        final Command bar = new Command("bar");
        // Appelle une méthode
        bar.setCondition((sender, commandString) -> true);
        // Appelle une méthode
        foo.addSubcommand(bar);
        // Appelle une méthode
        bar.addConditionalSyntax((sender, commandString) -> false, null, ArgumentType.Literal("baz"));
        // Instruction de code
        assertFiltering(foo, """
                foo bar=%
                0->foo
                foo->bar
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandFilteredTrueWithFilteredSubcommandFalse() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.setCondition((sender, commandString) -> true);
        // Appelle une méthode
        final Command bar = new Command("bar");
        // Appelle une méthode
        bar.setCondition((sender, commandString) -> false);
        // Appelle une méthode
        foo.addSubcommand(bar);
        // Instruction de code
        assertFiltering(foo, """
                foo=%
                0->foo
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandFilteredTrueWithFilteredSubcommandTrue() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.setCondition((sender, commandString) -> true);
        // Appelle une méthode
        final Command bar = new Command("bar");
        // Appelle une méthode
        bar.setCondition((sender, commandString) -> true);
        // Appelle une méthode
        foo.addSubcommand(bar);
        // Instruction de code
        assertFiltering(foo, """
                foo bar=%
                0->foo
                foo->bar
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandFilteredTrueWithFilteredSubcommandTrueWithFilteredSyntaxBoth() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.setCondition((sender, commandString) -> true);
        // Appelle une méthode
        final Command bar = new Command("bar");
        // Appelle une méthode
        bar.setCondition((sender, commandString) -> true);
        // Appelle une méthode
        foo.addSubcommand(bar);
        // Appelle une méthode
        bar.addConditionalSyntax((sender, commandString) -> true, null, ArgumentType.Literal("true"));
        // Appelle une méthode
        bar.addConditionalSyntax((sender, commandString) -> false, null, ArgumentType.Literal("false"));
        // Instruction de code
        assertFiltering(foo, """
                foo bar true=%
                0->foo
                foo->bar
                bar->true
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandConditionalArgGroupTrue() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.addConditionalSyntax((sender, commandString) -> true, null, ArgumentType.Group("test", ArgumentType.Literal("bar")));
        // Instruction de code
        assertFiltering(foo, """
                foo bar=%
                0->foo
                foo->bar
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandConditionalArgGroupFalse() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.addConditionalSyntax((sender, commandString) -> false, null, ArgumentType.Group("test", ArgumentType.Literal("foo")));
        // Instruction de code
        assertFiltering(foo, """
                foo=%
                0->foo
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandUnconditionalArgGroup() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.addSyntax(null, ArgumentType.Group("test", ArgumentType.Literal("bar")));
        // Instruction de code
        assertFiltering(foo, """
                foo bar=%
                0->foo
                foo->bar
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandConditionalArgGroupTrue2() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.addConditionalSyntax((sender, commandString) -> true, null, ArgumentType.Group("test", ArgumentType.Literal("bar"), ArgumentType.Literal("baz")));
        // Instruction de code
        assertFiltering(foo, """
                foo bar baz=%
                0->foo
                foo->bar
                bar->baz
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandConditionalArgGroupFalse2() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.addConditionalSyntax((sender, commandString) -> false, null, ArgumentType.Group("test", ArgumentType.Literal("foo"), ArgumentType.Literal("baz")));
        // Instruction de code
        assertFiltering(foo, """
                foo=%
                0->foo
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandUnconditionalArgGroup2() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.addSyntax(null, ArgumentType.Group("test", ArgumentType.Literal("bar"), ArgumentType.Literal("baz")));
        // Instruction de code
        assertFiltering(foo, """
                foo bar baz=%
                0->foo
                foo->bar
                bar->baz
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandUnconditionalArgLoop() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.addSyntax(null, ArgumentType.Loop("test", ArgumentType.Literal("bar"), ArgumentType.Literal("baz")));
        // Instruction de code
        assertFiltering(foo, """
                foo bar baz=%
                0->foo
                foo->bar baz
                bar baz+>foo
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandConditionalArgLoopTrue() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.addConditionalSyntax((sender, commandString) -> true, null, ArgumentType.Loop("test", ArgumentType.Literal("bar"), ArgumentType.Literal("baz")));
        // Instruction de code
        assertFiltering(foo, """
                foo bar baz=%
                0->foo
                foo->bar baz
                bar baz+>foo
                """);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandConditionalArgLoopFalse() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.addConditionalSyntax((sender, commandString) -> false, null, ArgumentType.Loop("test", ArgumentType.Literal("bar"), ArgumentType.Literal("baz")));
        // Instruction de code
        assertFiltering(foo, """
                foo=%
                0->foo
                """);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void assertFiltering(Command command, String expectedStructure) {
        // Appelle une méthode
        final DeclareCommandsPacket packet = GraphConverter.createPacket(Graph.merge(Set.of(command)), PLAYER);
        // Appelle une méthode
        CommandTestUtils.assertPacket(packet, expectedStructure);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
