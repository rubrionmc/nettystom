// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.kyori.adventure.text.format.TextDecoration;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.condition.Conditions;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.scoreboard.Sidebar;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class SidebarCommand extends Command {
    // Calls a method
    private final Sidebar sidebar = new Sidebar(Component.text("DEMO").decorate(TextDecoration.BOLD));
    // Assigns a value
    private int currentLine = 0;

    // Start of a method/block
    public SidebarCommand() {
        // Access to the current/parent object
        super("sidebar");

        // Calls a method
        addLine("BLANK ", Sidebar.NumberFormat.blank());
        // Calls a method
        addLine("STYLE ", Sidebar.NumberFormat.styled(Component.empty().decorate(TextDecoration.STRIKETHROUGH).color(NamedTextColor.GRAY)));
        // Calls a method
        addLine("FIXED ", Sidebar.NumberFormat.fixed(Component.text("FIXED").color(NamedTextColor.GRAY)));
        // Calls a method
        addLine("NULL ", null);

        // Calls a method
        setDefaultExecutor((source, args) -> source.sendMessage(Component.text("Unknown syntax (note: title must be quoted)")));
        // Calls a method
        setCondition(Conditions::playerOnly);

        // Calls a method
        var option = ArgumentType.Word("option").from("add-line", "remove-line", "set-title", "toggle", "update-content", "update-score");
        // Calls a method
        var content = ArgumentType.String("content").setDefaultValue("");
        // Calls a method
        var targetLine = ArgumentType.Integer("target line").setDefaultValue(-1);

        // Calls a method
        addSyntax(this::handleSidebar, option);
        // Calls a method
        addSyntax(this::handleSidebar, option, content);
        // Calls a method
        addSyntax(this::handleSidebar, option, content, targetLine);
    // End of a block/expression
    }


    // Start of a method/block
    private void handleSidebar(CommandSender source, CommandContext context) {
        // Calls a method
        Player player = (Player) source;
        // Calls a method
        String option = context.get("option");
        // Calls a method
        String content = context.get("content");
        // Calls a method
        int targetLine = context.get("target line");
        // Branch: checks a condition
        if (targetLine == -1) targetLine = currentLine;
        // Multiple branching (switch/case)
        switch (option) {
            // Multiple branching (switch/case)
            case "add-line":
                // Calls a method
                addLine(content, null);
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            case "remove-line":
                // Calls a method
                removeLine();
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            case "set-title":
                // Calls a method
                setTitle(content);
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            case "toggle":
                // Calls a method
                toggleSidebar(player);
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            case "update-content":
                // Calls a method
                updateLineContent(content, String.valueOf(targetLine));
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            case "update-score":
                // Calls a method
                updateLineScore(Integer.parseInt(content), String.valueOf(targetLine));
                // Breaks out of the loop/block
                break;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void addLine(String content, @Nullable Sidebar.NumberFormat numberFormat) {
        // Branch: checks a condition
        if (currentLine < 16) {
            // Calls a method
            sidebar.createLine(new Sidebar.ScoreboardLine(String.valueOf(currentLine), Component.text(content).color(NamedTextColor.WHITE), currentLine, numberFormat));
            // Code statement
            currentLine++;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void removeLine() {
        // Branch: checks a condition
        if (currentLine > 0) {
            // Calls a method
            sidebar.removeLine(String.valueOf(currentLine));
            // Code statement
            currentLine--;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void setTitle(String title) {
        // Calls a method
        sidebar.setTitle(Component.text(title).decorate(TextDecoration.BOLD));
    // End of a block/expression
    }

    // Start of a method/block
    private void toggleSidebar(Player player) {
        // Branch: checks a condition
        if (sidebar.getViewers().contains(player)) sidebar.removeViewer(player);
        // Alternative branch of the condition
        else sidebar.addViewer(player);
    // End of a block/expression
    }

    // Start of a method/block
    private void updateLineContent(String content, String lineId) {
        // Calls a method
        sidebar.updateLineContent(lineId, Component.text(content).color(NamedTextColor.WHITE));
    // End of a block/expression
    }

    // Start of a method/block
    private void updateLineScore(int score, String lineId) {
        // Calls a method
        sidebar.updateLineScore(lineId, score);
    // End of a block/expression
    }
// End of a block/expression
}
