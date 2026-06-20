// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.UUID;

// Annotation for the following element
@SuppressWarnings("ConstantConditions")
// Type declaration (class/interface/enum/record)
public class CommandPacketFilteringTest {
    // Calls a method
    private static final Player PLAYER = new Player(null, new GameProfile(UUID.randomUUID(), "Test"));

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandFilteredFalse() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.setCondition(((sender, commandString) -> false));
        // Calls a method
        assertFiltering(foo, "");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandFilteredTrue() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.setCondition(((sender, commandString) -> true));
        // Code statement
        assertFiltering(foo, """
                foo=%
                0->foo
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandUnfiltered() {
        // Calls a method
        final Command foo = new Command("foo");
        // Code statement
        assertFiltering(foo, """
                foo=%
                0->foo
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandFilteredTrueWithFilteredSubcommandTrueWithFilteredSyntaxFalse() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.setCondition((sender, commandString) -> true);
        // Calls a method
        final Command bar = new Command("bar");
        // Calls a method
        bar.setCondition((sender, commandString) -> true);
        // Calls a method
        foo.addSubcommand(bar);
        // Calls a method
        bar.addConditionalSyntax((sender, commandString) -> false, null, ArgumentType.Literal("baz"));
        // Code statement
        assertFiltering(foo, """
                foo bar=%
                0->foo
                foo->bar
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandFilteredTrueWithFilteredSubcommandFalse() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.setCondition((sender, commandString) -> true);
        // Calls a method
        final Command bar = new Command("bar");
        // Calls a method
        bar.setCondition((sender, commandString) -> false);
        // Calls a method
        foo.addSubcommand(bar);
        // Code statement
        assertFiltering(foo, """
                foo=%
                0->foo
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandFilteredTrueWithFilteredSubcommandTrue() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.setCondition((sender, commandString) -> true);
        // Calls a method
        final Command bar = new Command("bar");
        // Calls a method
        bar.setCondition((sender, commandString) -> true);
        // Calls a method
        foo.addSubcommand(bar);
        // Code statement
        assertFiltering(foo, """
                foo bar=%
                0->foo
                foo->bar
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandFilteredTrueWithFilteredSubcommandTrueWithFilteredSyntaxBoth() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.setCondition((sender, commandString) -> true);
        // Calls a method
        final Command bar = new Command("bar");
        // Calls a method
        bar.setCondition((sender, commandString) -> true);
        // Calls a method
        foo.addSubcommand(bar);
        // Calls a method
        bar.addConditionalSyntax((sender, commandString) -> true, null, ArgumentType.Literal("true"));
        // Calls a method
        bar.addConditionalSyntax((sender, commandString) -> false, null, ArgumentType.Literal("false"));
        // Code statement
        assertFiltering(foo, """
                foo bar true=%
                0->foo
                foo->bar
                bar->true
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandConditionalArgGroupTrue() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.addConditionalSyntax((sender, commandString) -> true, null, ArgumentType.Group("test", ArgumentType.Literal("bar")));
        // Code statement
        assertFiltering(foo, """
                foo bar=%
                0->foo
                foo->bar
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandConditionalArgGroupFalse() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.addConditionalSyntax((sender, commandString) -> false, null, ArgumentType.Group("test", ArgumentType.Literal("foo")));
        // Code statement
        assertFiltering(foo, """
                foo=%
                0->foo
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandUnconditionalArgGroup() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.addSyntax(null, ArgumentType.Group("test", ArgumentType.Literal("bar")));
        // Code statement
        assertFiltering(foo, """
                foo bar=%
                0->foo
                foo->bar
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandConditionalArgGroupTrue2() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.addConditionalSyntax((sender, commandString) -> true, null, ArgumentType.Group("test", ArgumentType.Literal("bar"), ArgumentType.Literal("baz")));
        // Code statement
        assertFiltering(foo, """
                foo bar baz=%
                0->foo
                foo->bar
                bar->baz
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandConditionalArgGroupFalse2() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.addConditionalSyntax((sender, commandString) -> false, null, ArgumentType.Group("test", ArgumentType.Literal("foo"), ArgumentType.Literal("baz")));
        // Code statement
        assertFiltering(foo, """
                foo=%
                0->foo
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandUnconditionalArgGroup2() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.addSyntax(null, ArgumentType.Group("test", ArgumentType.Literal("bar"), ArgumentType.Literal("baz")));
        // Code statement
        assertFiltering(foo, """
                foo bar baz=%
                0->foo
                foo->bar
                bar->baz
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandUnconditionalArgLoop() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.addSyntax(null, ArgumentType.Loop("test", ArgumentType.Literal("bar"), ArgumentType.Literal("baz")));
        // Code statement
        assertFiltering(foo, """
                foo bar baz=%
                0->foo
                foo->bar baz
                bar baz+>foo
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandConditionalArgLoopTrue() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.addConditionalSyntax((sender, commandString) -> true, null, ArgumentType.Loop("test", ArgumentType.Literal("bar"), ArgumentType.Literal("baz")));
        // Code statement
        assertFiltering(foo, """
                foo bar baz=%
                0->foo
                foo->bar baz
                bar baz+>foo
                """);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandConditionalArgLoopFalse() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.addConditionalSyntax((sender, commandString) -> false, null, ArgumentType.Loop("test", ArgumentType.Literal("bar"), ArgumentType.Literal("baz")));
        // Code statement
        assertFiltering(foo, """
                foo=%
                0->foo
                """);
    // End of a block/expression
    }

    // Start of a method/block
    private void assertFiltering(Command command, String expectedStructure) {
        // Calls a method
        final DeclareCommandsPacket packet = GraphConverter.createPacket(Graph.merge(Set.of(command)), PLAYER);
        // Calls a method
        CommandTestUtils.assertPacket(packet, expectedStructure);
    // End of a block/expression
    }
// End of a block/expression
}
