// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.IntArrayBinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.IntBinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.StringBinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.kyori.adventure.text.format.Style;
// Import of a required class
import net.kyori.adventure.text.format.TextDecoration;
// Import of a required class
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentEnum;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.CustomData;
// Import of a required class
import net.minestom.server.particle.Particle;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.server.utils.Range;
// Import of a required class
import net.minestom.server.utils.location.RelativeVec;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.UUID;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class ArgumentTypeTest {

    // Start of a method/block
    static {
        // Calls a method
        MinecraftServer.init();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentEntityType() {
        // Calls a method
        var arg = ArgumentType.EntityType("entity_type");
        // Calls a method
        assertInvalidArg(arg, "minecraft:invalid_entity_type");
        // Calls a method
        assertArg(arg, EntityType.ARMOR_STAND, EntityType.ARMOR_STAND.name());
        // Calls a method
        assertArg(arg, EntityType.PLAYER, EntityType.PLAYER.name());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentParticle() {
        // Calls a method
        var arg = ArgumentType.Particle("particle");
        // Calls a method
        assertInvalidArg(arg, "minecraft:invalid_particle");
        // Calls a method
        assertArg(arg, Particle.BLOCK, Particle.BLOCK.name());
        // Calls a method
        assertArg(arg, Particle.TOTEM_OF_UNDYING, Particle.TOTEM_OF_UNDYING.name());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentBlockState() {
        // Calls a method
        var arg = ArgumentType.BlockState("block_state");
        // Calls a method
        assertInvalidArg(arg, "minecraft:invalid_block[invalid_property=invalid_key]");
        // Calls a method
        assertInvalidArg(arg, "minecraft:stone[invalid_property=invalid_key]");
        // Calls a method
        assertInvalidArg(arg, "minecraft:kelp[age=invalid_key]");

        // Calls a method
        assertArg(arg, Block.COBBLESTONE, "minecraft:cobblestone");
        // Calls a method
        assertArg(arg, Block.KELP.withProperty("age", "14"), "minecraft:kelp[age=14]");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentColor() {
        // Calls a method
        var arg = ArgumentType.Color("color");
        // Calls a method
        assertInvalidArg(arg, "invalid_color");
        // Calls a method
        assertArg(arg, Style.style(NamedTextColor.DARK_PURPLE), "dark_purple");
        // Calls a method
        assertArg(arg, Style.empty(), "reset");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentComponent() {
        // Calls a method
        var arg = ArgumentType.Component("component");
        // Calls a method
        var component1 = Component.text("Example text", NamedTextColor.DARK_AQUA);
        // Calls a method
        var component2 = Component.text("Other example text", Style.style(TextDecoration.OBFUSCATED));
        // Calls a method
        var json1 = GsonComponentSerializer.gson().serialize(component1);
        // Calls a method
        var json2 = GsonComponentSerializer.gson().serialize(component2);

        // Calls a method
        assertInvalidArg(arg, "invalid component");
        // Calls a method
        assertArg(arg, component1, json1);
        // Calls a method
        assertArg(arg, component2, json2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentEntity() {
        // Calls a method
        var arg = ArgumentType.Entity("entity");

        // Calls a method
        assertValidArg(arg, "@a");
        // Calls a method
        assertValidArg(arg, "@p");
        // Calls a method
        assertInvalidArg(arg, "@x");

        // Calls a method
        assertValidArg(arg, "@e[type=sheep]");
        // Calls a method
        assertValidArg(arg, "@e[type=!cow]");
        // Calls a method
        assertInvalidArg(arg, "@e[type=invalid_entity]");
        // Calls a method
        assertInvalidArg(arg, "@e[type=!invalid_entity_two]");

        // Calls a method
        assertValidArg(arg, "@e[gamemode=creative]");
        // Calls a method
        assertValidArg(arg, "@e[gamemode=!survival]");
        // Calls a method
        assertInvalidArg(arg, "@e[gamemode=invalid_gamemode]");
        // Calls a method
        assertInvalidArg(arg, "@e[gamemode=!invalid_gamemode_2]");

        // Calls a method
        assertValidArg(arg, "@e[limit=500]");
        // Calls a method
        assertInvalidArg(arg, "@e[limit=-500]");
        // Calls a method
        assertInvalidArg(arg, "@e[limit=invalid_integer]");
        // Calls a method
        assertInvalidArg(arg, "@e[limit=2147483648]");

        // Calls a method
        assertValidArg(arg, "@e[sort=nearest]");
        // Calls a method
        assertInvalidArg(arg, "@e[sort=invalid_sort]");

        // Calls a method
        assertValidArg(arg, "@e[level=55]");
        // Calls a method
        assertValidArg(arg, "@e[level=100..500]");
        // Calls a method
        assertInvalidArg(arg, "@e[level=20-50]");
        // Calls a method
        assertInvalidArg(arg, "@e[level=2147483648]");

        // Calls a method
        assertValidArg(arg, "@e[distance=500]");
        // Calls a method
        assertValidArg(arg, "@e[distance=50..150]");
        // Calls a method
        assertInvalidArg(arg, "@e[distance=-500-500]");
        // Calls a method
        assertInvalidArg(arg, "@e[distance=2147483648]");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentFloatRange() {
        // Calls a method
        var arg = ArgumentType.FloatRange("float_range");
        // Calls a method
        assertArg(arg, new Range.Float(0f, 50f), "0..50");
        // Calls a method
        assertArg(arg, new Range.Float(0f, 0f), "0..0");
        // Calls a method
        assertArg(arg, new Range.Float(-50f, 0f), "-50..0");
        // Calls a method
        assertArg(arg, new Range.Float(-Float.MAX_VALUE, 50f), "..50");
        // Calls a method
        assertArg(arg, new Range.Float(0f, Float.MAX_VALUE), "0..");
        // Calls a method
        assertArg(arg, new Range.Float(-Float.MAX_VALUE, Float.MAX_VALUE), "-3.4028235E38..3.4028235E38");
        // Calls a method
        assertArg(arg, new Range.Float(0.5f, 24f), "0.5..24");
        // Calls a method
        assertArg(arg, new Range.Float(12f, 45.6f), "12..45.6");
        // Calls a method
        assertInvalidArg(arg, "..");
        // Calls a method
        assertInvalidArg(arg, "0..50..");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentIntRange() {
        // Calls a method
        var arg = ArgumentType.IntRange("int_range");

        // Calls a method
        assertArg(arg, new Range.Int(0, 50), "0..50");
        // Calls a method
        assertArg(arg, new Range.Int(0, 0), "0..0");
        // Calls a method
        assertArg(arg, new Range.Int(-50, 0), "-50..0");
        // Calls a method
        assertArg(arg, new Range.Int(Integer.MIN_VALUE, 50), "..50");
        // Calls a method
        assertArg(arg, new Range.Int(0, Integer.MAX_VALUE), "0..");
        // Calls a method
        assertArg(arg, new Range.Int(Integer.MIN_VALUE, Integer.MAX_VALUE), "-2147483648..2147483647");

        // Calls a method
        assertInvalidArg(arg, "..");
        // Calls a method
        assertInvalidArg(arg, "-2147483649..2147483647");
        // Calls a method
        assertInvalidArg(arg, "-2147483648..2147483648");
        // Calls a method
        assertInvalidArg(arg, "0..50..");
        // Calls a method
        assertInvalidArg(arg, "0.5..24");
        // Calls a method
        assertInvalidArg(arg, "12..45.6");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentItemStack() {
        // Calls a method
        var arg = ArgumentType.ItemStack("item_stack");
        // Calls a method
        assertArg(arg, ItemStack.AIR, "air");
        // Calls a method
        assertArg(arg, ItemStack.of(Material.GLASS_PANE).withTag(Tag.String("tag"), "value"), "glass_pane{tag:value}");
        // Calls a method
        assertArg(arg, ItemStack.of(Material.GLASS_PANE).with(DataComponents.REPAIR_COST, 5), "glass_pane[repair_cost=5]");
        // Calls a method
        assertArg(arg, ItemStack.of(Material.GLASS_PANE).with(DataComponents.REPAIR_COST, 5).withTag(Tag.String("tag"), "value"), "glass_pane[repair_cost=5]{tag:value}");
        // Code statement
        assertArg(arg, ItemStack.of(Material.GLASS_PANE).with(DataComponents.REPAIR_COST, 5).with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putInt("hi", 232).build())).withTag(Tag.String("tag"), "value"),
                // Code statement
                "glass_pane[repair_cost=5,minecraft:custom_data={hi:232}]{tag:value}");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentNbtCompoundTag() {
        // Calls a method
        var arg = ArgumentType.NbtCompound("nbt_compound");
        // Code statement
        assertArg(arg, CompoundBinaryTag.builder().putLongArray("long_array", new long[]{12, 49, 119}).build(),
                // Code statement
                "{\"long_array\":[L;12L,49L,119L]}");
        // Code statement
        assertArg(arg, CompoundBinaryTag.builder().put("nested", CompoundBinaryTag.builder().putIntArray("complex", new int[]{124, 999, 33256}).build()).build(),
                // Code statement
                "{\"nested\": {\"complex\": [I;124,999,33256]}}");

        // Calls a method
        assertInvalidArg(arg, "string");
        // Calls a method
        assertInvalidArg(arg, "\"string\"");
        // Calls a method
        assertInvalidArg(arg, "44");
        // Calls a method
        assertInvalidArg(arg, "[I;11,49,33]");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentNbtTag() {
        // Calls a method
        var arg = ArgumentType.NBT("nbt");
        // Calls a method
        assertArg(arg, StringBinaryTag.stringBinaryTag("string"), "string");
        // Calls a method
        assertArg(arg, StringBinaryTag.stringBinaryTag("string"), "\"string\"");
        // Calls a method
        assertArg(arg, IntBinaryTag.intBinaryTag(44), "44");
        // Calls a method
        assertArg(arg, IntArrayBinaryTag.intArrayBinaryTag(11, 49, 33), "[I;11,49,33]");
        // Code statement
        assertArg(arg, CompoundBinaryTag.builder().putLongArray("long_array", new long[]{12, 49, 119}).build(),
                // Code statement
                "{\"long_array\":[L;12L,49L,119L]}");

        // Calls a method
        assertInvalidArg(arg, "\"unbalanced string");
        // Calls a method
        assertInvalidArg(arg, "dd}");
        // Calls a method
        assertInvalidArg(arg, "{unquoted: string)}");
        // Calls a method
        assertInvalidArg(arg, "{\"array\": [D;123L,5L]}");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentResource() {
        // Calls a method
        var arg = ArgumentType.Resource("resource", "minecraft:block");
        // Calls a method
        assertArg(arg, "minecraft:resource_example", "minecraft:resource_example");
        // Calls a method
        assertInvalidArg(arg, "minecraft:invalid resource");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentResourceLocation() {
        // Calls a method
        var arg = ArgumentType.ResourceLocation("resource_location");

        // Calls a method
        assertArg(arg, Key.key("foo:bar"), "foo:bar");
        // Calls a method
        assertArg(arg, Key.key("minecraft:air"), "air");
        // Calls a method
        assertArg(arg, Key.key("minecraft:foo/bar"), "foo/bar");

        // Calls a method
        assertInvalidArg(arg, "minecraft:invalid resource location");
        // Calls a method
        assertInvalidArg(arg, "!");
        // Calls a method
        assertInvalidArg(arg, "a/b:empty");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentResourceOrTag() {
        // Calls a method
        var arg = ArgumentType.ResourceOrTag("resource_or_tag", "data/minecraft/tags/blocks");
        // Calls a method
        assertArg(arg, "minecraft:resource_or_tag_example", "minecraft:resource_or_tag_example");
        // Calls a method
        assertInvalidArg(arg, "minecraft:invalid resource or tag");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentTime() {
        // Calls a method
        var arg = ArgumentType.Time("time");
        // Calls a method
        assertArg(arg, Duration.of(20, TimeUnit.SERVER_TICK), "20");
        // Calls a method
        assertArg(arg, Duration.of(40, TimeUnit.SERVER_TICK), "40t");
        // Calls a method
        assertArg(arg, Duration.of(60, TimeUnit.SECOND), "60s");
        // Calls a method
        assertArg(arg, Duration.of(80, TimeUnit.DAY), "80d");

        // Calls a method
        assertInvalidArg(arg, "100x");
        // Calls a method
        assertInvalidArg(arg, "2147483648t");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentUUID() {
        // Calls a method
        var arg = ArgumentType.UUID("uuid");
        // Calls a method
        assertInvalidArg(arg, "invalid_uuid");
        // Calls a method
        assertArg(arg, UUID.fromString("10515090-26f2-49fa-b2ba-9594d4d0451f"), "10515090-26f2-49fa-b2ba-9594d4d0451f");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentDouble() {
        // Calls a method
        var arg = ArgumentType.Double("double");
        // Calls a method
        assertArg(arg, 2564d, "2564");
        // Calls a method
        assertArg(arg, -591.981d, "-591.981");
        // Calls a method
        assertInvalidArg(arg, "-5.5.52");
        // Calls a method
        assertInvalidArg(arg, "++2.99");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentFloat() {
        // Calls a method
        var arg = ArgumentType.Float("float");
        // Calls a method
        assertArg(arg, 2564f, "2564");
        // Calls a method
        assertArg(arg, -591.981f, "-591.981");
        // Calls a method
        assertInvalidArg(arg, "-5.5.52");
        // Calls a method
        assertInvalidArg(arg, "++2.99");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentInteger() {
        // Calls a method
        var arg = ArgumentType.Integer("integer");
        // Calls a method
        assertArg(arg, 2564, "2564");
        // Calls a method
        assertInvalidArg(arg, "256.4");
        // Calls a method
        assertInvalidArg(arg, "2147483648");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentLong() {
        // Calls a method
        var arg = ArgumentType.Long("long");
        // Calls a method
        assertArg(arg, 2564L, "2564");
        // Calls a method
        assertInvalidArg(arg, "256.4");
        // Calls a method
        assertInvalidArg(arg, "9223372036854775808");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentRelativeBlockPosition() {
        // Calls a method
        var arg = ArgumentType.RelativeBlockPosition("relative_block_position");
        // Calls a method
        var vec = new Vec(-3, 14, 255);

        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.ABSOLUTE, false, false, false), "-3 14 +255");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, true, false, false), "~-3 14 +255");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, false, true, false), "-3 ~14 +255");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, false, false, true), "-3 14 ~+255");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, true, true, true), "~-3 ~14 ~+255");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.LOCAL, true, true, true), "^-3 ^14 ^+255");

        // Calls a method
        assertInvalidArg(arg, "-3.50 14 +255");
        // Calls a method
        assertInvalidArg(arg, "-3 14.25 +255");
        // Calls a method
        assertInvalidArg(arg, "-3 14 +255.75");
        // Calls a method
        assertInvalidArg(arg, "-3 14 +-255");
        // Calls a method
        assertInvalidArg(arg, "-3 text -255");
        // Calls a method
        assertInvalidArg(arg, "-3 14 ~~+255");
        // Calls a method
        assertInvalidArg(arg, "^-3 ~14 ^+255");
        // Calls a method
        assertInvalidArg(arg, "^-3 14 ^+255");
        // Calls a method
        assertInvalidArg(arg, "1 2");
        // Calls a method
        assertInvalidArg(arg, "1 2 3 4");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentRelativeVec2() {
        // Calls a method
        var arg = ArgumentType.RelativeVec2("relative_vec_2");
        // Calls a method
        var vec = new Vec(-3, 14.25);

        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.ABSOLUTE, false, false, false), "-3 14.25");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, true, false, false), "~-3 14.25");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, false, false, true), "-3 ~14.25");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, false, false, true), "-3 ~14.25");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, true, false, true), "~-3 ~14.25");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.LOCAL, true, false, true), "^-3 ^14.25");

        // Calls a method
        assertInvalidArg(arg, "-3 +-14");
        // Calls a method
        assertInvalidArg(arg, "-3 text");
        // Calls a method
        assertInvalidArg(arg, "~~-3 14");
        // Calls a method
        assertInvalidArg(arg, "^-3 ~14");
        // Calls a method
        assertInvalidArg(arg, "^-3 14");
        // Calls a method
        assertInvalidArg(arg, "1");
        // Calls a method
        assertInvalidArg(arg, "1 2 3");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentRelativeVec3() {
        // Calls a method
        var arg = ArgumentType.RelativeVec3("relative_vec_3");
        // Calls a method
        var vec = new Vec(-3, 14.25, 255);

        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.ABSOLUTE, false, false, false), "-3 14.25 +255");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, true, false, false), "~-3 14.25 +255");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, false, true, false), "-3 ~14.25 +255");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, false, false, true), "-3 14.25 ~+255");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, true, true, true), "~-3 ~14.25 ~+255");
        // Calls a method
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.LOCAL, true, true, true), "^-3 ^14.25 ^+255");

        // Calls a method
        assertInvalidArg(arg, "-3 14 +-255");
        // Calls a method
        assertInvalidArg(arg, "-3 text -255");
        // Calls a method
        assertInvalidArg(arg, "-3 14 ~~+255");
        // Calls a method
        assertInvalidArg(arg, "^-3 ~14 ^+255");
        // Calls a method
        assertInvalidArg(arg, "^-3 14 ^+255");
        // Calls a method
        assertInvalidArg(arg, "1 2");
        // Calls a method
        assertInvalidArg(arg, "1 2 3 4");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentBoolean() {
        // Calls a method
        var arg = ArgumentType.Boolean("boolean");
        // Calls a method
        assertArg(arg, true, "true");
        // Calls a method
        assertArg(arg, false, "false");
        // Calls a method
        assertInvalidArg(arg, "invalid_boolean");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentEnum() {
        // Type declaration (class/interface/enum/record)
        enum ExampleEnum {FIRST, SECOND, Third, fourth}

        // Calls a method
        var arg = ArgumentType.Enum("enum", ExampleEnum.class);

        // Calls a method
        arg.setFormat(ArgumentEnum.Format.DEFAULT);
        // Calls a method
        assertArg(arg, ExampleEnum.FIRST, "FIRST");
        // Calls a method
        assertArg(arg, ExampleEnum.SECOND, "SECOND");
        // Calls a method
        assertArg(arg, ExampleEnum.Third, "Third");
        // Calls a method
        assertArg(arg, ExampleEnum.fourth, "fourth");
        // Calls a method
        assertInvalidArg(arg, "invalid argument");

        // Calls a method
        arg.setFormat(ArgumentEnum.Format.UPPER_CASED);
        // Calls a method
        assertArg(arg, ExampleEnum.FIRST, "FIRST");
        // Calls a method
        assertArg(arg, ExampleEnum.SECOND, "SECOND");
        // Calls a method
        assertInvalidArg(arg, "Third");
        // Calls a method
        assertInvalidArg(arg, "fourth");
        // Calls a method
        assertInvalidArg(arg, "invalid argument");

        // Calls a method
        arg.setFormat(ArgumentEnum.Format.LOWER_CASED);
        // Calls a method
        assertInvalidArg(arg, "FIRST");
        // Calls a method
        assertInvalidArg(arg, "SECOND");
        // Calls a method
        assertInvalidArg(arg, "Third");
        // Calls a method
        assertArg(arg, ExampleEnum.fourth, "fourth");
        // Calls a method
        assertInvalidArg(arg, "invalid argument");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentGroup() {
        // Calls a method
        var arg = ArgumentType.Group("group", ArgumentType.Integer("integer"), ArgumentType.String("string"), ArgumentType.Double("double"));

        // Test normal input
        // Calls a method
        var context1 = arg.parse(new ServerSender(), "1234 1234 1234");
        // Calls a method
        assertEquals(1234, context1.<Integer>get("integer"));
        // Calls a method
        assertEquals("1234", context1.<String>get("string"));
        // Calls a method
        assertEquals(1234.0, context1.<Double>get("double"));

        // Test different input + trailing spaces
        // Calls a method
        var context2 = arg.parse(new ServerSender(), "1234 abcd 1234.5678   ");
        // Calls a method
        assertEquals(1234, context2.<Integer>get("integer"));
        // Calls a method
        assertEquals("abcd", context2.<String>get("string"));
        // Calls a method
        assertEquals(1234.5678, context2.<Double>get("double"));

        // Calls a method
        assertInvalidArg(arg, "");
        // Calls a method
        assertInvalidArg(arg, "");
        // Calls a method
        assertInvalidArg(arg, "");
        // Calls a method
        assertInvalidArg(arg, "1234.5678 1234 1234.5678");
        // Calls a method
        assertInvalidArg(arg, "1234 1234 abcd");
        // Calls a method
        assertInvalidArg(arg, "1234 1234 ");
        // Calls a method
        assertInvalidArg(arg, "1234");
        // Calls a method
        assertInvalidArg(arg, "1234 abcd 1234.5678 extra");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentLiteral() {
        // Calls a method
        var arg = ArgumentType.Literal("literal");
        // Calls a method
        assertArg(arg, "literal", "literal");
        // Calls a method
        assertInvalidArg(arg, "not_literal");
        // Calls a method
        assertInvalidArg(arg, "");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentLoop() {
        // Assigns a value
        var arg = ArgumentType.Loop("loop", ArgumentType.String("string"), ArgumentType.String("string2").map(s -> {
            // Throws an exception
            throw new IllegalArgumentException("This argument should never be triggered");
        // Code statement
        }));

        // Calls a method
        assertArg(arg, List.of("a", "b", "c"), "a b c");
        // Calls a method
        assertArg(arg, List.of("a", "b"), "a b");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentString() {
        // Calls a method
        var arg = ArgumentType.String("string");
        // Calls a method
        assertArg(arg, "text", "text");
        // Calls a method
        assertArg(arg, "more text", "\"more text\"");
        // Calls a method
        assertArg(arg, "more text, but with \"escaped\" quotes", "\"more text, but with \\\"escaped\\\" quotes\"");
        // Calls a method
        assertInvalidArg(arg, "\"unclosed quotes");
        // Calls a method
        assertInvalidArg(arg, "\"unescaped \" quotes\"");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentStringArray() {
        // Calls a method
        var arg = ArgumentType.StringArray("string_array");
        // Calls a method
        assertArrayArg(arg, new String[]{"example", "text"}, "example text");
        // Calls a method
        assertArrayArg(arg, new String[]{"some", "more", "placeholder", "text"}, "some more placeholder text");
        // Calls a method
        assertArrayArg(arg, new String[]{""}, "");
        // Calls a method
        assertArrayArg(arg, new String[0], " ");
        // Calls a method
        assertArrayArg(arg, new String[0], "         ");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentWord() {
        // Calls a method
        var arg = ArgumentType.Word("word").from("word1", "word2", "word3");

        // Calls a method
        assertArg(arg, "word1", "word1");
        // Calls a method
        assertArg(arg, "word2", "word2");
        // Calls a method
        assertArg(arg, "word3", "word3");

        // Calls a method
        assertInvalidArg(arg, "word");
        // Calls a method
        assertInvalidArg(arg, "word4");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentTransformWithSender() {
        // Calls a method
        var serverSender = new ServerSender();

        // Assigns a value
        var arg = ArgumentType.Word("word").from("word1", "word2", "word3")
                // Start of a method/block
                .map((sender, s) -> {
                    // Calls a method
                    assertEquals(serverSender, sender);
                    // Returns a value to the caller
                    return s;
                // End of a block/expression
                });

        // Calls a method
        assertEquals("word1", arg.parse(serverSender, "word1"));
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> void assertArg(Argument<T> arg, T expected, String input) {
        // Calls a method
        assertEquals(expected, arg.parse(new ServerSender(), input));
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> void assertArrayArg(Argument<T[]> arg, T[] expected, String input) {
        // Calls a method
        assertArrayEquals(expected, arg.parse(new ServerSender(), input));
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> void assertValidArg(Argument<T> arg, String input) {
        // Calls a method
        assertDoesNotThrow(() -> arg.parse(new ServerSender(), input));
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> void assertInvalidArg(Argument<T> arg, String input) {
        // Calls a method
        assertThrows(ArgumentSyntaxException.class, () -> arg.parse(new ServerSender(), input));
    // End of a block/expression
    }
// End of a block/expression
}
