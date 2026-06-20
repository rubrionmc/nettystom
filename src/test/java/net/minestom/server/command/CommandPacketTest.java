// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class CommandPacketTest {
    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        MinecraftServer.init();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandWithOneSyntax() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.addSyntax(CommandPacketTest::dummyExecutor, ArgumentType.Integer("bar"));

        // Appelle une méthode
        final DeclareCommandsPacket packet = GraphConverter.createPacket(Graph.merge(Graph.fromCommand(foo)), null);
        // Appelle une méthode
        assertEquals(3, packet.nodes().size());
        // Appelle une méthode
        final DeclareCommandsPacket.Node root = packet.nodes().get(packet.rootIndex());
        // Appelle une méthode
        assertNotNull(root);
        // Appelle une méthode
        assertNodeType(DeclareCommandsPacket.NodeType.ROOT, root.flags);
        // Appelle une méthode
        assertEquals(1, root.children.length);
        // Appelle une méthode
        final DeclareCommandsPacket.Node cmd = packet.nodes().get(root.children[0]);
        // Appelle une méthode
        assertNotNull(cmd);
        // Appelle une méthode
        assertNodeType(DeclareCommandsPacket.NodeType.LITERAL, cmd.flags);
        // Appelle une méthode
        assertEquals(1, cmd.children.length);
        // Appelle une méthode
        assertEquals("foo", cmd.name);
        // Appelle une méthode
        final DeclareCommandsPacket.Node arg = packet.nodes().get(cmd.children[0]);
        // Appelle une méthode
        assertNotNull(arg);
        // Appelle une méthode
        assertNodeType(DeclareCommandsPacket.NodeType.ARGUMENT, arg.flags);
        // Appelle une méthode
        assertExecutable(arg.flags);
        // Appelle une méthode
        assertEquals(0, arg.children.length);
        // Appelle une méthode
        assertEquals("bar", arg.name);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void executeLike() {
        // Déclaration de type (classe/interface/enum/record)
        enum Dimension {OVERWORLD, THE_NETHER, THE_END}
        // Appelle une méthode
        final Command execute = new Command("execute");
        // Instruction de code
        execute.addSyntax(CommandPacketTest::dummyExecutor, ArgumentType.Loop("params",
                // Instruction de code
                ArgumentType.Group("facing", ArgumentType.Literal("facing"), ArgumentType.RelativeVec3("pos")),
                // Instruction de code
                ArgumentType.Group("at", ArgumentType.Literal("at"), ArgumentType.Entity("targets")),
                // Instruction de code
                ArgumentType.Group("as", ArgumentType.Literal("as"), ArgumentType.Entity("targets")),
                // Instruction de code
                ArgumentType.Group("in", ArgumentType.Literal("in"), ArgumentType.Enum("dimension", Dimension.class)),
                // Instruction de code
                ArgumentType.Group("run", ArgumentType.Command("run"))
        // Instruction de code
        ));
        // Appelle une méthode
        var graph = Graph.fromCommand(execute);
        // Instruction de code
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
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandTwoEnum() {
        // Affecte une valeur
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Instruction de code
                .append(ArgumentType.Enum("bar", A.class), b -> b.append(ArgumentType.Enum("baz", B.class)))
                // Appelle une méthode
                .build();
        // Instruction de code
        assertPacketGraph("""
                foo=%
                a b c d e f=§
                0->foo
                foo->a b c
                a b c->d e f
                """, graph);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandRestrictedWord() {
        // Affecte une valeur
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Instruction de code
                .append(ArgumentType.Word("bar").from("A", "B", "C"))
                // Appelle une méthode
                .build();
        // Instruction de code
        assertPacketGraph("""
                foo=%
                a b c=§
                0->foo
                foo->a b c
                """, graph);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandWord() {
        // Affecte une valeur
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Instruction de code
                .append(ArgumentType.Word("bar"))
                // Appelle une méthode
                .build();
        // Instruction de code
        assertPacketGraph("""
                foo=%
                bar=! STRING 0
                0->foo
                foo->bar
                """, graph);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandCommandAfterEnum() {
        // Affecte une valeur
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Instruction de code
                .append(ArgumentType.Enum("bar", A.class), b -> b.append(ArgumentType.Command("baz")))
                // Appelle une méthode
                .build();
        // Instruction de code
        assertPacketGraph("""
                foo baz=%
                a b c=§
                0->foo
                foo->a b c
                a b c->baz
                baz+>0
                """, graph);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void twoCommandIntEnumInt() {
        // Affecte une valeur
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Instruction de code
                .append(ArgumentType.Integer("int1"), b -> b.append(ArgumentType.Enum("test", A.class), c -> c.append(ArgumentType.Integer("int2"))))
                // Appelle une méthode
                .build();
        // Affecte une valeur
        var graph2 = Graph.builder(ArgumentType.Literal("bar"))
                // Instruction de code
                .append(ArgumentType.Integer("int3"), b -> b.append(ArgumentType.Enum("test", B.class), c -> c.append(ArgumentType.Integer("int4"))))
                // Appelle une méthode
                .build();
        // Instruction de code
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
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandTwoGroupOfIntInt() {
        // Affecte une valeur
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Instruction de code
                .append(ArgumentType.Group("1", ArgumentType.Integer("int1"), ArgumentType.Integer("int2")),
                        // Instruction de code
                        b -> b.append(ArgumentType.Group("2", ArgumentType.Integer("int3"), ArgumentType.Integer("int4"))))
                // Appelle une méthode
                .build();
        // Instruction de code
        assertPacketGraph("""
                foo=%
                int1 int2 int3 int4=! INTEGER 0
                0->foo
                foo->int1
                int1->int2
                int2->int3
                int3->int4
                """, graph);
    // Fin d'un bloc/d'une expression
    }
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void twoEnumAndOneLiteralChild() {
        // Affecte une valeur
        var graph = Graph.builder(ArgumentType.Literal("foo"))
                // Instruction de code
                .append(ArgumentType.Enum("a", A.class))
                // Instruction de code
                .append(ArgumentType.Literal("l"))
                // Instruction de code
                .append(ArgumentType.Enum("b", B.class))
                // Appelle une méthode
                .build();
        // Instruction de code
        assertPacketGraph("""
                foo l=%
                0->foo
                a b c d e f=§
                foo->a b c d e f l
                """, graph);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void commandAliasWithoutArg() {
        // Affecte une valeur
        var graph = Graph.builder(ArgumentType.Word("foo").from("foo", "bar"))
                // Appelle une méthode
                .build();
        // Instruction de code
        assertPacketGraph("""
                foo bar=%
                0->foo bar
                """, graph);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void commandAliasWithArg() {
        // Affecte une valeur
        var graph = Graph.builder(ArgumentType.Word("foo").from("foo", "bar"))
                // Instruction de code
                .append(ArgumentType.Literal("l"))
                // Appelle une méthode
                .build();
        // Instruction de code
        assertPacketGraph("""
                foo bar l=%
                0->foo bar
                foo bar->l
                """, graph);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cmdArgShortcut() {
        // Affecte une valeur
        var foo = Graph.builder(ArgumentType.Literal("foo"))
                // Instruction de code
                .append(ArgumentType.String("msg"))
                // Appelle une méthode
                .build();
        // Affecte une valeur
        var bar = Graph.builder(ArgumentType.Literal("bar"))
                // Instruction de code
                .append(ArgumentType.Command("cmd").setShortcut("foo"))
                // Appelle une méthode
                .build();
        // Instruction de code
        assertPacketGraph("""
                foo bar cmd=%
                0->foo bar
                bar->cmd
                cmd+>foo
                foo->msg
                msg=! STRING 1
                """, foo, bar);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cmdArgShortcutWithPartialArg() {
        // Affecte une valeur
        var foo = Graph.builder(ArgumentType.Literal("foo"))
                // Instruction de code
                .append(ArgumentType.String("msg"))
                // Appelle une méthode
                .build();
        // Affecte une valeur
        var bar = Graph.builder(ArgumentType.Literal("bar"))
                // Instruction de code
                .append(ArgumentType.Command("cmd").setShortcut("foo \"prefix "))
                // Appelle une méthode
                .build();
        // Instruction de code
        assertPacketGraph("""
                foo bar cmd=%
                0->foo bar
                bar->cmd
                cmd+>foo
                foo->msg
                msg=! STRING 1
                """, foo, bar);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static void assertPacketGraph(String expected, Graph... graphs) {
        // Appelle une méthode
        var packet = GraphConverter.createPacket(Graph.merge(graphs), null);
        // Appelle une méthode
        CommandTestUtils.assertPacket(packet, expected);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    enum A {A, B, C}

    // Déclaration de type (classe/interface/enum/record)
    enum B {D, E, F}

    // Déclaration de type (classe/interface/enum/record)
    enum C {G, H, I, J, K}

    // Début d'une méthode/d'un bloc
    private static void assertNodeType(DeclareCommandsPacket.NodeType expected, byte flags) {
        // Appelle une méthode
        assertEquals(expected, DeclareCommandsPacket.NodeType.values()[flags & 0x03]);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertExecutable(byte flags) {
        // Appelle une méthode
        assertTrue((flags & DeclareCommandsPacket.IS_EXECUTABLE) != 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void dummyExecutor(CommandSender sender, CommandContext context) {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
