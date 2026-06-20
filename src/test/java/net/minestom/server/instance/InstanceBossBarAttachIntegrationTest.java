// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.kyori.adventure.bossbar.BossBar;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.server.play.BossBarPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static net.kyori.adventure.text.Component.text;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class InstanceBossBarAttachIntegrationTest {

    // Start of a method/block
    private static BossBar sampleBossBar() {
        // Returns a value to the caller
        return BossBar.bossBar(text("Test Boss Bar"), 1.0f, BossBar.Color.RED, BossBar.Overlay.PROGRESS);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void attachReturn(Env env) {
        // Calls a method
        Instance instance = env.process().instance().createInstanceContainer();
        // Calls a method
        BossBar bossBar = sampleBossBar();

        // Calls a method
        assertEquals(0, instance.bossBars().size());
        // Calls a method
        instance.showBossBar(bossBar);
        // Calls a method
        instance.showBossBar(bossBar);
        // Calls a method
        assertEquals(1, instance.bossBars().size());
        // Calls a method
        instance.hideBossBar(bossBar);
        // Calls a method
        instance.hideBossBar(bossBar);
        // Calls a method
        assertEquals(0, instance.bossBars().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void showOnAttach(Env env) {
        // Calls a method
        Instance instance = env.process().instance().createInstanceContainer();
        // Calls a method
        BossBar bossBar = sampleBossBar();

        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        var collector = connection.trackIncoming(BossBarPacket.class);
        // Calls a method
        instance.showBossBar(bossBar);
        // Calls a method
        collector.assertSingle(bossBarPacket -> assertInstanceOf(BossBarPacket.AddAction.class, bossBarPacket.action()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void hideOnDetach(Env env) {
        // Calls a method
        Instance instance = env.process().instance().createInstanceContainer();
        // Calls a method
        BossBar bossBar = sampleBossBar();

        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        connection.connect(instance, new Pos(0, 40, 0));
        // Calls a method
        instance.showBossBar(bossBar);
        // Calls a method
        var collector = connection.trackIncoming(BossBarPacket.class);
        // Calls a method
        instance.hideBossBar(bossBar);
        // Calls a method
        collector.assertSingle(bossBarPacket -> assertInstanceOf(BossBarPacket.RemoveAction.class, bossBarPacket.action()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void showOnAdd(Env env) {
        // Calls a method
        Instance instance = env.process().instance().createInstanceContainer();
        // Calls a method
        BossBar bossBar = sampleBossBar();
        // Calls a method
        instance.showBossBar(bossBar);

        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var collector = connection.trackIncoming(BossBarPacket.class);
        // Calls a method
        connection.connect(instance, new Pos(0, 40, 0));
        // Calls a method
        collector.assertSingle(bossBarPacket -> assertInstanceOf(BossBarPacket.AddAction.class, bossBarPacket.action()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void hideOnRemove(Env env) {
        // Calls a method
        Instance instance = env.process().instance().createInstanceContainer();
        // Calls a method
        Instance instance2 = env.process().instance().createInstanceContainer();
        // Calls a method
        BossBar bossBar = sampleBossBar();

        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        Player player = connection.connect(instance, new Pos(0, 40, 0));
        // Calls a method
        instance.showBossBar(bossBar);
        // Calls a method
        var collector = connection.trackIncoming(BossBarPacket.class);
        // Calls a method
        player.setInstance(instance2).join();
        // Calls a method
        collector.assertSingle(bossBarPacket -> assertInstanceOf(BossBarPacket.RemoveAction.class, bossBarPacket.action()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void update(Env env) {
        // Calls a method
        Instance instance = env.process().instance().createInstanceContainer();
        // Calls a method
        BossBar bossBar = sampleBossBar();
        // Calls a method
        instance.showBossBar(bossBar);

        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        connection.connect(instance, new Pos(0, 40, 0));
        // Calls a method
        var collector = connection.trackIncoming(BossBarPacket.class);
        // Calls a method
        bossBar.name(text("Text update"));
        // Calls a method
        collector.assertSingle(bossBarPacket -> assertInstanceOf(BossBarPacket.UpdateTitleAction.class, bossBarPacket.action()));
    // End of a block/expression
    }
// End of a block/expression
}
