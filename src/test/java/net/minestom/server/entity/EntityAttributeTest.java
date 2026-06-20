// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.AttributeModifier;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.AttributeOperation;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.AttributeList;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNotNull;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertThrows;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityAttributeTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testEntityUpdatesAttributes(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(0, 0).join();

        // Appelle une méthode
        LivingEntity entity = new LivingEntity(EntityTypes.CHICKEN);
        // Appelle une méthode
        entity.setInstance(instance).join();

        // Affecte une valeur
        double addition = 10;

        // Appelle une méthode
        double baseHealth = entity.getAttribute(Attribute.MAX_HEALTH).getValue();
        // Instruction de code
        assertEquals(0, Double.compare(baseHealth, entity.getAttributeValue(Attribute.MAX_HEALTH))); // Avoid floating-point rounding issues

        // Affecte une valeur
        ItemStack itemStack = ItemStack.builder(Material.DIAMOND).set(DataComponents.ATTRIBUTE_MODIFIERS,
                // Crée un nouvel objet
                new AttributeList(new AttributeList.Modifier(Attribute.MAX_HEALTH,
                        // Crée un nouvel objet
                        new AttributeModifier(Key.key("minestom:health"), addition, AttributeOperation.ADD_VALUE), EquipmentSlotGroup.HEAD))).build();

        // Appelle une méthode
        entity.setBoots(itemStack);
        // Instruction de code
        assertEquals(0, Double.compare(entity.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth)); // No change since we are in the wrong slot
        // Appelle une méthode
        entity.setHelmet(itemStack);
        // Instruction de code
        assertEquals(0, Double.compare(entity.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth + addition)); // Should change
        // Appelle une méthode
        entity.setHelmet(ItemStack.AIR);
        // Instruction de code
        assertEquals(0, Double.compare(entity.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth)); // Reset back to base
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testPlayerUpdatesAttributes(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 1));

        // Affecte une valeur
        double addition = 10;

        // Appelle une méthode
        double baseHealth = player.getAttribute(Attribute.MAX_HEALTH).getValue();
        // Instruction de code
        assertEquals(0, Double.compare(baseHealth, player.getAttributeValue(Attribute.MAX_HEALTH))); // Avoid floating-point rounding issues

        // Affecte une valeur
        ItemStack itemStack = ItemStack.builder(Material.DIAMOND).set(DataComponents.ATTRIBUTE_MODIFIERS,
                // Crée un nouvel objet
                new AttributeList(new AttributeList.Modifier(Attribute.MAX_HEALTH,
                        // Crée un nouvel objet
                        new AttributeModifier(Key.key("minestom:health"), addition, AttributeOperation.ADD_VALUE), EquipmentSlotGroup.MAIN_HAND))).build();

        // Appelle une méthode
        player.setBoots(itemStack);
        // Instruction de code
        assertEquals(0, Double.compare(player.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth)); // No change since we are in the wrong slot
        // Appelle une méthode
        player.setItemInMainHand(itemStack);
        // Instruction de code
        assertEquals(0, Double.compare(player.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth + addition)); // Should change
        // Appelle une méthode
        player.refreshHeldSlot((byte) 1);
        // Instruction de code
        assertEquals(0, Double.compare(player.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth)); // Changes since the player switched the main hand item
        // Appelle une méthode
        player.refreshHeldSlot((byte) 0);
        // Instruction de code
        assertEquals(0, Double.compare(player.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth + addition)); // Switched back
        // Appelle une méthode
        player.setItemInMainHand(ItemStack.AIR);
        // Appelle une méthode
        assertEquals(0, Double.compare(player.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDirectlyAddAttributes(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 1));

        // Affecte une valeur
        double baseHealth = 20;
        // Affecte une valeur
        double addition = 10;
        // Don't compare against base health first (that will initialize the attribute, and we want to make sure we don't error when we add an item with attribute modifiers)

        // Affecte une valeur
        ItemStack itemStack = ItemStack.builder(Material.DIAMOND).set(DataComponents.ATTRIBUTE_MODIFIERS,
                // Crée un nouvel objet
                new AttributeList(new AttributeList.Modifier(Attribute.MAX_HEALTH,
                        // Crée un nouvel objet
                        new AttributeModifier(Key.key("minestom:health"), addition, AttributeOperation.ADD_VALUE), EquipmentSlotGroup.MAIN_HAND))).build();

        // Appelle une méthode
        player.setItemInMainHand(itemStack);
        // Appelle une méthode
        assertEquals(0, Double.compare(player.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth + addition));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testEntityDefaultAttributes(Env ignored) {
        // Appelle une méthode
        var ironGolem = new EntityCreature(EntityType.IRON_GOLEM);
        // Appelle une méthode
        var zombie = new EntityCreature(EntityType.ZOMBIE);

        // Appelle une méthode
        var golemHealth = ironGolem.getAttribute(Attribute.MAX_HEALTH);
        // Appelle une méthode
        assertNotNull(golemHealth);
        // Appelle une méthode
        assertEquals(100.0, golemHealth.getBaseValue(), 0.001);
        // Appelle une méthode
        assertEquals(golemHealth.getBaseValue(), ironGolem.getAttributeValue(Attribute.MAX_HEALTH), 0.001);

        // Appelle une méthode
        var zombieHealth = zombie.getAttribute(Attribute.MAX_HEALTH);
        // Appelle une méthode
        assertNotNull(zombieHealth);
        // Appelle une méthode
        assertEquals(20.0, zombieHealth.getBaseValue(), 0.001);
        // Appelle une méthode
        assertEquals(zombieHealth.getBaseValue(), zombie.getAttributeValue(Attribute.MAX_HEALTH), 0.001);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testEntitySpawnsWithCorrectHealth(Env ignored) {
        // Appelle une méthode
        var ironGolem = new EntityCreature(EntityType.IRON_GOLEM);
        // Appelle une méthode
        assertEquals(100.0f, ironGolem.getHealth(), 0.001f);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDefaultAttributesFromRegistry() {
        // Appelle une méthode
        var golemDefaults = EntityType.IRON_GOLEM.defaultAttributes();
        // Appelle une méthode
        assertEquals(100.0, golemDefaults.getOrDefault(Attribute.MAX_HEALTH, Double.NaN), 0.001);
        // Appelle une méthode
        assertEquals(0.25, golemDefaults.getOrDefault(Attribute.MOVEMENT_SPEED, Double.NaN), 0.001);

        // Appelle une méthode
        var zombieDefaults = EntityType.ZOMBIE.defaultAttributes();
        // Appelle une méthode
        assertEquals(3.0, zombieDefaults.getOrDefault(Attribute.ATTACK_DAMAGE, Double.NaN), 0.001);
        // Appelle une méthode
        assertEquals(20.0, zombieDefaults.getOrDefault(Attribute.MAX_HEALTH, Double.NaN), 0.001);

        // Appelle une méthode
        assertTrue(EntityType.AREA_EFFECT_CLOUD.defaultAttributes().isEmpty());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDefaultAttributesAreImmutable() {
        // Appelle une méthode
        var defaults = EntityType.IRON_GOLEM.defaultAttributes();
        // Appelle une méthode
        assertThrows(UnsupportedOperationException.class, () -> defaults.put(Attribute.MAX_HEALTH, 1.0));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
