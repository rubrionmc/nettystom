// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.kyori.adventure.bossbar.BossBar;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.BossBarPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static net.kyori.adventure.text.Component.text;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class InstanceBossBarAttachIntegrationTest {

    // Début d'une méthode/d'un bloc
    private static BossBar sampleBossBar() {
        // Renvoie une valeur à l'appelant
        return BossBar.bossBar(text("Test Boss Bar"), 1.0f, BossBar.Color.RED, BossBar.Overlay.PROGRESS);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void attachReturn(Env env) {
        // Appelle une méthode
        Instance instance = env.process().instance().createInstanceContainer();
        // Appelle une méthode
        BossBar bossBar = sampleBossBar();

        // Appelle une méthode
        assertEquals(0, instance.bossBars().size());
        // Appelle une méthode
        instance.showBossBar(bossBar);
        // Appelle une méthode
        instance.showBossBar(bossBar);
        // Appelle une méthode
        assertEquals(1, instance.bossBars().size());
        // Appelle une méthode
        instance.hideBossBar(bossBar);
        // Appelle une méthode
        instance.hideBossBar(bossBar);
        // Appelle une méthode
        assertEquals(0, instance.bossBars().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void showOnAttach(Env env) {
        // Appelle une méthode
        Instance instance = env.process().instance().createInstanceContainer();
        // Appelle une méthode
        BossBar bossBar = sampleBossBar();

        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        var collector = connection.trackIncoming(BossBarPacket.class);
        // Appelle une méthode
        instance.showBossBar(bossBar);
        // Appelle une méthode
        collector.assertSingle(bossBarPacket -> assertInstanceOf(BossBarPacket.AddAction.class, bossBarPacket.action()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void hideOnDetach(Env env) {
        // Appelle une méthode
        Instance instance = env.process().instance().createInstanceContainer();
        // Appelle une méthode
        BossBar bossBar = sampleBossBar();

        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        instance.showBossBar(bossBar);
        // Appelle une méthode
        var collector = connection.trackIncoming(BossBarPacket.class);
        // Appelle une méthode
        instance.hideBossBar(bossBar);
        // Appelle une méthode
        collector.assertSingle(bossBarPacket -> assertInstanceOf(BossBarPacket.RemoveAction.class, bossBarPacket.action()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void showOnAdd(Env env) {
        // Appelle une méthode
        Instance instance = env.process().instance().createInstanceContainer();
        // Appelle une méthode
        BossBar bossBar = sampleBossBar();
        // Appelle une méthode
        instance.showBossBar(bossBar);

        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var collector = connection.trackIncoming(BossBarPacket.class);
        // Appelle une méthode
        connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        collector.assertSingle(bossBarPacket -> assertInstanceOf(BossBarPacket.AddAction.class, bossBarPacket.action()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void hideOnRemove(Env env) {
        // Appelle une méthode
        Instance instance = env.process().instance().createInstanceContainer();
        // Appelle une méthode
        Instance instance2 = env.process().instance().createInstanceContainer();
        // Appelle une méthode
        BossBar bossBar = sampleBossBar();

        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        Player player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        instance.showBossBar(bossBar);
        // Appelle une méthode
        var collector = connection.trackIncoming(BossBarPacket.class);
        // Appelle une méthode
        player.setInstance(instance2).join();
        // Appelle une méthode
        collector.assertSingle(bossBarPacket -> assertInstanceOf(BossBarPacket.RemoveAction.class, bossBarPacket.action()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void update(Env env) {
        // Appelle une méthode
        Instance instance = env.process().instance().createInstanceContainer();
        // Appelle une méthode
        BossBar bossBar = sampleBossBar();
        // Appelle une méthode
        instance.showBossBar(bossBar);

        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        var collector = connection.trackIncoming(BossBarPacket.class);
        // Appelle une méthode
        bossBar.name(text("Text update"));
        // Appelle une méthode
        collector.assertSingle(bossBarPacket -> assertInstanceOf(BossBarPacket.UpdateTitleAction.class, bossBarPacket.action()));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
