// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class CommandPacketTest {
    // Start of a method/block
    static {
        // Calls a method
        MinecraftServer.init();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandWithOneSyntax() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.addSyntax(CommandPacketTest::dummyExecutor, ArgumentType.Integer("bar"));

        // Calls a method
        final DeclareCommandsPacket packet = GraphConverter.createPacket(Graph.merge(Graph.fromCommand(foo)), null);
        // Calls a method
        assertEquals(3, packet.nodes().size());
        // Calls a method
        final DeclareCommandsPacket.Node root = packet.nodes().get(packet.rootIndex());
        // Calls a method
        assertNotNull(root);
        // Calls a method
        assertNodeType(DeclareCommandsPacket.NodeType.ROOT, root.flags);
        // Calls a method
        assertEquals(1, root.children.length);
        // Calls a method
        final DeclareCommandsPacket.Node cmd = packet.nodes().get(root.children[0]);
        // Calls a method
        assertNotNull(cmd);
        // Calls a method
        assertNodeType(DeclareCommandsPacket.NodeType.LITERAL, cmd.flags);
        // Calls a method
        assertEquals(1, cmd.children.length);
        // Calls a method
        assertEquals("foo", cmd.name);
        // Calls a method
        final DeclareCommandsPacket.Node arg = packet.nodes().get(cmd.children[0]);
        // Calls a method
        assertNotNull(arg);
        // Calls a method
        assertNodeType(DeclareCommandsPacket.NodeType.ARGUMENT, arg.flags);
        // Calls a method
        assertExecutable(arg.flags);
        // Calls a method
        assertEquals(0, arg.children.length);
        // Calls a method
        assertEquals("bar", arg.name);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void executeLike() {
        // Type declaration (class/interface/enum/record)
        enum Dimension {OVERWORLD, THE_NETHER, THE_END}
        // Calls a method
        final Command execute = new Command("execute");
        // Code statement
        execute.addSyntax(CommandPacketTest::dummyExecutor, ArgumentType.Loop("params",
                // Code statement
                ArgumentType.Group("facing", ArgumentType.Literal("facing"), ArgumentType.RelativeVec3("pos")),
                // Code statement
                ArgumentType.Group("at", ArgumentType.Literal("at"), ArgumentType.Entity("targets")),
                // Code statement
                ArgumentType.Group("as", ArgumentType.Literal("as"), ArgumentType.Entity("targets")),
                // Code statement
                ArgumentType.Group("in", ArgumentType.Literal("in"), ArgumentType.Enum("dimension", Dimension.class)),
                // Code statement
                ArgumentType.Group("run", ArgumentType.Command("run"))
        // Code statement
        ));
        // Calls a method
        var graph = Graph.fromCommand(execute);
        // Code statement
        assertPacketGraph("""
                execute facing at as in run=%
                overworld the_nether the_end=§
                0->execute
                atEnt asEnt=targets ENTITY 0
                execute->facing at as in run
                at->atEnt
                as->asEnt
                in->overworld the_nether the_end
                pos=! VEC3
                facing->pos
                pos atEnt asEnt overworld the_nether the_end+>execute
                run+>0
                """, graph);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandTwoEnum() {
        // Assigns a value
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Code statement
                .append(ArgumentType.Enum("bar", A.class), b -> b.append(ArgumentType.Enum("baz", B.class)))
                // Calls a method
                .build();
        // Code statement
        assertPacketGraph("""
                foo=%
                a b c d e f=§
                0->foo
                foo->a b c
                a b c->d e f
                """, graph);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandRestrictedWord() {
        // Assigns a value
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Code statement
                .append(ArgumentType.Word("bar").from("A", "B", "C"))
                // Calls a method
                .build();
        // Code statement
        assertPacketGraph("""
                foo=%
                a b c=§
                0->foo
                foo->a b c
                """, graph);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandWord() {
        // Assigns a value
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Code statement
                .append(ArgumentType.Word("bar"))
                // Calls a method
                .build();
        // Code statement
        assertPacketGraph("""
                foo=%
                bar=! STRING 0
                0->foo
                foo->bar
                """, graph);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandCommandAfterEnum() {
        // Assigns a value
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Code statement
                .append(ArgumentType.Enum("bar", A.class), b -> b.append(ArgumentType.Command("baz")))
                // Calls a method
                .build();
        // Code statement
        assertPacketGraph("""
                foo baz=%
                a b c=§
                0->foo
                foo->a b c
                a b c->baz
                baz+>0
                """, graph);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void twoCommandIntEnumInt() {
        // Assigns a value
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Code statement
                .append(ArgumentType.Integer("int1"), b -> b.append(ArgumentType.Enum("test", A.class), c -> c.append(ArgumentType.Integer("int2"))))
                // Calls a method
                .build();
        // Assigns a value
        var graph2 = Graph.builder(ArgumentType.Literal("bar"))
                // Code statement
                .append(ArgumentType.Integer("int3"), b -> b.append(ArgumentType.Enum("test", B.class), c -> c.append(ArgumentType.Integer("int4"))))
                // Calls a method
                .build();
        // Code statement
        assertPacketGraph("""
                foo bar=%
                0->foo bar
                a b c d e f=§
                int1 int2 int3 int4=! INTEGER 0
                foo->int1
                bar->int3
                int1->a b c
                int3->d e f
                a b c->int2
                d e f->int4
                """, graph, graph2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandTwoGroupOfIntInt() {
        // Assigns a value
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Code statement
                .append(ArgumentType.Group("1", ArgumentType.Integer("int1"), ArgumentType.Integer("int2")),
                        // Code statement
                        b -> b.append(ArgumentType.Group("2", ArgumentType.Integer("int3"), ArgumentType.Integer("int4"))))
                // Calls a method
                .build();
        // Code statement
        assertPacketGraph("""
                foo=%
                int1 int2 int3 int4=! INTEGER 0
                0->foo
                foo->int1
                int1->int2
                int2->int3
                int3->int4
                """, graph);
    // End of a block/expression
    }
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void twoEnumAndOneLiteralChild() {
        // Assigns a value
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Code statement
                .append(ArgumentType.Enum("a", A.class))
                // Code statement
                .append(ArgumentType.Literal("l"))
                // Code statement
                .append(ArgumentType.Enum("b", B.class))
                // Calls a method
                .build();
        // Code statement
        assertPacketGraph("""
                foo l=%
                0->foo
                a b c d e f=§
                foo->a b c d e f l
                """, graph);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void commandAliasWithoutArg() {
        // Assigns a value
        var graph = Graph.builder(ArgumentType.Word("foo").from("foo", "bar"))
                // Calls a method
                .build();
        // Code statement
        assertPacketGraph("""
                foo bar=%
                0->foo bar
                """, graph);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void commandAliasWithArg() {
        // Assigns a value
        var graph = Graph.builder(ArgumentType.Word("foo").from("foo", "bar"))
                // Code statement
                .append(ArgumentType.Literal("l"))
                // Calls a method
                .build();
        // Code statement
        assertPacketGraph("""
                foo bar l=%
                0->foo bar
                foo bar->l
                """, graph);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cmdArgShortcut() {
        // Assigns a value
        var foo = Graph.builder(ArgumentType.Literal("foo"))
                // Code statement
                .append(ArgumentType.String("msg"))
                // Calls a method
                .build();
        // Assigns a value
        var bar = Graph.builder(ArgumentType.Literal("bar"))
                // Code statement
                .append(ArgumentType.Command("cmd").setShortcut("foo"))
                // Calls a method
                .build();
        // Code statement
        assertPacketGraph("""
                foo bar cmd=%
                0->foo bar
                bar->cmd
                cmd+>foo
                foo->msg
                msg=! STRING 1
                """, foo, bar);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cmdArgShortcutWithPartialArg() {
        // Assigns a value
        var foo = Graph.builder(ArgumentType.Literal("foo"))
                // Code statement
                .append(ArgumentType.String("msg"))
                // Calls a method
                .build();
        // Assigns a value
        var bar = Graph.builder(ArgumentType.Literal("bar"))
                // Code statement
                .append(ArgumentType.Command("cmd").setShortcut("foo \"prefix "))
                // Calls a method
                .build();
        // Code statement
        assertPacketGraph("""
                foo bar cmd=%
                0->foo bar
                bar->cmd
                cmd+>foo
                foo->msg
                msg=! STRING 1
                """, foo, bar);
    // End of a block/expression
    }

    // Start of a method/block
    static void assertPacketGraph(String expected, Graph... graphs) {
        // Calls a method
        var packet = GraphConverter.createPacket(Graph.merge(graphs), null);
        // Calls a method
        CommandTestUtils.assertPacket(packet, expected);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    enum A {A, B, C}

    // Type declaration (class/interface/enum/record)
    enum B {D, E, F}

    // Type declaration (class/interface/enum/record)
    enum C {G, H, I, J, K}

    // Start of a method/block
    private static void assertNodeType(DeclareCommandsPacket.NodeType expected, byte flags) {
        // Calls a method
        assertEquals(expected, DeclareCommandsPacket.NodeType.values()[flags & 0x03]);
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertExecutable(byte flags) {
        // Calls a method
        assertTrue((flags & DeclareCommandsPacket.IS_EXECUTABLE) != 0);
    // End of a block/expression
    }

    // Start of a method/block
    private static void dummyExecutor(CommandSender sender, CommandContext context) {
    // End of a block/expression
    }
// End of a block/expression
}
