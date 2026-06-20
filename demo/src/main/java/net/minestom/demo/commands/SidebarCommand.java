// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.TextDecoration;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.Conditions;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.scoreboard.Sidebar;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class SidebarCommand extends Command {
    // Appelle une méthode
    private final Sidebar sidebar = new Sidebar(Component.text("DEMO").decorate(TextDecoration.BOLD));
    // Affecte une valeur
    private int currentLine = 0;

    // Début d'une méthode/d'un bloc
    public SidebarCommand() {
        // Accès à l'objet courant/parent
        super("sidebar");

        // Appelle une méthode
        addLine("BLANK ", Sidebar.NumberFormat.blank());
        // Appelle une méthode
        addLine("STYLE ", Sidebar.NumberFormat.styled(Component.empty().decorate(TextDecoration.STRIKETHROUGH).color(NamedTextColor.GRAY)));
        // Appelle une méthode
        addLine("FIXED ", Sidebar.NumberFormat.fixed(Component.text("FIXED").color(NamedTextColor.GRAY)));
        // Appelle une méthode
        addLine("NULL ", null);

        // Appelle une méthode
        setDefaultExecutor((source, args) -> source.sendMessage(Component.text("Unknown syntax (note: title must be quoted)")));
        // Appelle une méthode
        setCondition(Conditions::playerOnly);

        // Appelle une méthode
        var option = ArgumentType.Word("option").from("add-line", "remove-line", "set-title", "toggle", "update-content", "update-score");
        // Appelle une méthode
        var content = ArgumentType.String("content").setDefaultValue("");
        // Appelle une méthode
        var targetLine = ArgumentType.Integer("target line").setDefaultValue(-1);

        // Appelle une méthode
        addSyntax(this::handleSidebar, option);
        // Appelle une méthode
        addSyntax(this::handleSidebar, option, content);
        // Appelle une méthode
        addSyntax(this::handleSidebar, option, content, targetLine);
    // Fin d'un bloc/d'une expression
    }


    // Début d'une méthode/d'un bloc
    private void handleSidebar(CommandSender source, CommandContext context) {
        // Affecte une valeur
        Player player = (Player) source;
        // Appelle une méthode
        String option = context.get("option");
        // Appelle une méthode
        String content = context.get("content");
        // Appelle une méthode
        int targetLine = context.get("target line");
        // Embranchement : vérifie une condition
        if (targetLine == -1) targetLine = currentLine;
        // Embranchement multiple (switch/case)
        switch (option) {
            // Embranchement multiple (switch/case)
            case "add-line":
                // Appelle une méthode
                addLine(content, null);
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            case "remove-line":
                // Appelle une méthode
                removeLine();
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            case "set-title":
                // Appelle une méthode
                setTitle(content);
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            case "toggle":
                // Appelle une méthode
                toggleSidebar(player);
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            case "update-content":
                // Appelle une méthode
                updateLineContent(content, String.valueOf(targetLine));
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            case "update-score":
                // Appelle une méthode
                updateLineScore(Integer.parseInt(content), String.valueOf(targetLine));
                // Interrompt la boucle/le bloc
                break;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void addLine(String content, @Nullable Sidebar.NumberFormat numberFormat) {
        // Embranchement : vérifie une condition
        if (currentLine < 16) {
            // Appelle une méthode
            sidebar.createLine(new Sidebar.ScoreboardLine(String.valueOf(currentLine), Component.text(content).color(NamedTextColor.WHITE), currentLine, numberFormat));
            // Instruction de code
            currentLine++;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void removeLine() {
        // Embranchement : vérifie une condition
        if (currentLine > 0) {
            // Appelle une méthode
            sidebar.removeLine(String.valueOf(currentLine));
            // Instruction de code
            currentLine--;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void setTitle(String title) {
        // Appelle une méthode
        sidebar.setTitle(Component.text(title).decorate(TextDecoration.BOLD));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void toggleSidebar(Player player) {
        // Embranchement : vérifie une condition
        if (sidebar.getViewers().contains(player)) sidebar.removeViewer(player);
        // Branche alternative de la condition
        else sidebar.addViewer(player);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void updateLineContent(String content, String lineId) {
        // Appelle une méthode
        sidebar.updateLineContent(lineId, Component.text(content).color(NamedTextColor.WHITE));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void updateLineScore(int score, String lineId) {
        // Appelle une méthode
        sidebar.updateLineScore(lineId, score);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
