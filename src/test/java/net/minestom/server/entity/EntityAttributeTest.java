// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.entity.attribute.AttributeModifier;
// Import of a required class
import net.minestom.server.entity.attribute.AttributeOperation;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.AttributeList;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNotNull;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertThrows;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityAttributeTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testEntityUpdatesAttributes(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(0, 0).join();

        // Calls a method
        LivingEntity entity = new LivingEntity(EntityTypes.CHICKEN);
        // Calls a method
        entity.setInstance(instance).join();

        // Assigns a value
        double addition = 10;

        // Calls a method
        double baseHealth = entity.getAttribute(Attribute.MAX_HEALTH).getValue();
        // Code statement
        assertEquals(0, Double.compare(baseHealth, entity.getAttributeValue(Attribute.MAX_HEALTH))); // Avoid floating-point rounding issues

        // Assigns a value
        ItemStack itemStack = ItemStack.builder(Material.DIAMOND).set(DataComponents.ATTRIBUTE_MODIFIERS,
                // Creates a new object
                new AttributeList(new AttributeList.Modifier(Attribute.MAX_HEALTH,
                        // Creates a new object
                        new AttributeModifier(Key.key("minestom:health"), addition, AttributeOperation.ADD_VALUE), EquipmentSlotGroup.HEAD))).build();

        // Calls a method
        entity.setBoots(itemStack);
        // Code statement
        assertEquals(0, Double.compare(entity.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth)); // No change since we are in the wrong slot
        // Calls a method
        entity.setHelmet(itemStack);
        // Code statement
        assertEquals(0, Double.compare(entity.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth + addition)); // Should change
        // Calls a method
        entity.setHelmet(ItemStack.AIR);
        // Code statement
        assertEquals(0, Double.compare(entity.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth)); // Reset back to base
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testPlayerUpdatesAttributes(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 1));

        // Assigns a value
        double addition = 10;

        // Calls a method
        double baseHealth = player.getAttribute(Attribute.MAX_HEALTH).getValue();
        // Code statement
        assertEquals(0, Double.compare(baseHealth, player.getAttributeValue(Attribute.MAX_HEALTH))); // Avoid floating-point rounding issues

        // Assigns a value
        ItemStack itemStack = ItemStack.builder(Material.DIAMOND).set(DataComponents.ATTRIBUTE_MODIFIERS,
                // Creates a new object
                new AttributeList(new AttributeList.Modifier(Attribute.MAX_HEALTH,
                        // Creates a new object
                        new AttributeModifier(Key.key("minestom:health"), addition, AttributeOperation.ADD_VALUE), EquipmentSlotGroup.MAIN_HAND))).build();

        // Calls a method
        player.setBoots(itemStack);
        // Code statement
        assertEquals(0, Double.compare(player.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth)); // No change since we are in the wrong slot
        // Calls a method
        player.setItemInMainHand(itemStack);
        // Code statement
        assertEquals(0, Double.compare(player.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth + addition)); // Should change
        // Calls a method
        player.refreshHeldSlot((byte) 1);
        // Code statement
        assertEquals(0, Double.compare(player.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth)); // Changes since the player switched the main hand item
        // Calls a method
        player.refreshHeldSlot((byte) 0);
        // Code statement
        assertEquals(0, Double.compare(player.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth + addition)); // Switched back
        // Calls a method
        player.setItemInMainHand(ItemStack.AIR);
        // Calls a method
        assertEquals(0, Double.compare(player.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDirectlyAddAttributes(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 1));

        // Assigns a value
        double baseHealth = 20;
        // Assigns a value
        double addition = 10;
        // Don't compare against base health first (that will initialize the attribute, and we want to make sure we don't error when we add an item with attribute modifiers)

        // Assigns a value
        ItemStack itemStack = ItemStack.builder(Material.DIAMOND).set(DataComponents.ATTRIBUTE_MODIFIERS,
                // Creates a new object
                new AttributeList(new AttributeList.Modifier(Attribute.MAX_HEALTH,
                        // Creates a new object
                        new AttributeModifier(Key.key("minestom:health"), addition, AttributeOperation.ADD_VALUE), EquipmentSlotGroup.MAIN_HAND))).build();

        // Calls a method
        player.setItemInMainHand(itemStack);
        // Calls a method
        assertEquals(0, Double.compare(player.getAttribute(Attribute.MAX_HEALTH).getValue(), baseHealth + addition));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testEntityDefaultAttributes(Env ignored) {
        // Calls a method
        var ironGolem = new EntityCreature(EntityType.IRON_GOLEM);
        // Calls a method
        var zombie = new EntityCreature(EntityType.ZOMBIE);

        // Calls a method
        var golemHealth = ironGolem.getAttribute(Attribute.MAX_HEALTH);
        // Calls a method
        assertNotNull(golemHealth);
        // Calls a method
        assertEquals(100.0, golemHealth.getBaseValue(), 0.001);
        // Calls a method
        assertEquals(golemHealth.getBaseValue(), ironGolem.getAttributeValue(Attribute.MAX_HEALTH), 0.001);

        // Calls a method
        var zombieHealth = zombie.getAttribute(Attribute.MAX_HEALTH);
        // Calls a method
        assertNotNull(zombieHealth);
        // Calls a method
        assertEquals(20.0, zombieHealth.getBaseValue(), 0.001);
        // Calls a method
        assertEquals(zombieHealth.getBaseValue(), zombie.getAttributeValue(Attribute.MAX_HEALTH), 0.001);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testEntitySpawnsWithCorrectHealth(Env ignored) {
        // Calls a method
        var ironGolem = new EntityCreature(EntityType.IRON_GOLEM);
        // Calls a method
        assertEquals(100.0f, ironGolem.getHealth(), 0.001f);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDefaultAttributesFromRegistry() {
        // Calls a method
        var golemDefaults = EntityType.IRON_GOLEM.defaultAttributes();
        // Calls a method
        assertEquals(100.0, golemDefaults.getOrDefault(Attribute.MAX_HEALTH, Double.NaN), 0.001);
        // Calls a method
        assertEquals(0.25, golemDefaults.getOrDefault(Attribute.MOVEMENT_SPEED, Double.NaN), 0.001);

        // Calls a method
        var zombieDefaults = EntityType.ZOMBIE.defaultAttributes();
        // Calls a method
        assertEquals(3.0, zombieDefaults.getOrDefault(Attribute.ATTACK_DAMAGE, Double.NaN), 0.001);
        // Calls a method
        assertEquals(20.0, zombieDefaults.getOrDefault(Attribute.MAX_HEALTH, Double.NaN), 0.001);

        // Calls a method
        assertTrue(EntityType.AREA_EFFECT_CLOUD.defaultAttributes().isEmpty());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDefaultAttributesAreImmutable() {
        // Calls a method
        var defaults = EntityType.IRON_GOLEM.defaultAttributes();
        // Calls a method
        assertThrows(UnsupportedOperationException.class, () -> defaults.put(Attribute.MAX_HEALTH, 1.0));
    // End of a block/expression
    }
// End of a block/expression
}
