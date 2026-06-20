// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.IntArrayBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.IntBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.StringBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.Style;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.TextDecoration;
// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentEnum;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.CustomData;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.utils.Range;
// Import d'une classe nécessaire
import net.minestom.server.utils.location.RelativeVec;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentTypeTest {

    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        MinecraftServer.init();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentEntityType() {
        // Appelle une méthode
        var arg = ArgumentType.EntityType("entity_type");
        // Appelle une méthode
        assertInvalidArg(arg, "minecraft:invalid_entity_type");
        // Appelle une méthode
        assertArg(arg, EntityType.ARMOR_STAND, EntityType.ARMOR_STAND.name());
        // Appelle une méthode
        assertArg(arg, EntityType.PLAYER, EntityType.PLAYER.name());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentParticle() {
        // Appelle une méthode
        var arg = ArgumentType.Particle("particle");
        // Appelle une méthode
        assertInvalidArg(arg, "minecraft:invalid_particle");
        // Appelle une méthode
        assertArg(arg, Particle.BLOCK, Particle.BLOCK.name());
        // Appelle une méthode
        assertArg(arg, Particle.TOTEM_OF_UNDYING, Particle.TOTEM_OF_UNDYING.name());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentBlockState() {
        // Appelle une méthode
        var arg = ArgumentType.BlockState("block_state");
        // Appelle une méthode
        assertInvalidArg(arg, "minecraft:invalid_block[invalid_property=invalid_key]");
        // Appelle une méthode
        assertInvalidArg(arg, "minecraft:stone[invalid_property=invalid_key]");
        // Appelle une méthode
        assertInvalidArg(arg, "minecraft:kelp[age=invalid_key]");

        // Appelle une méthode
        assertArg(arg, Block.COBBLESTONE, "minecraft:cobblestone");
        // Appelle une méthode
        assertArg(arg, Block.KELP.withProperty("age", "14"), "minecraft:kelp[age=14]");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentColor() {
        // Appelle une méthode
        var arg = ArgumentType.Color("color");
        // Appelle une méthode
        assertInvalidArg(arg, "invalid_color");
        // Appelle une méthode
        assertArg(arg, Style.style(NamedTextColor.DARK_PURPLE), "dark_purple");
        // Appelle une méthode
        assertArg(arg, Style.empty(), "reset");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentComponent() {
        // Appelle une méthode
        var arg = ArgumentType.Component("component");
        // Appelle une méthode
        var component1 = Component.text("Example text", NamedTextColor.DARK_AQUA);
        // Appelle une méthode
        var component2 = Component.text("Other example text", Style.style(TextDecoration.OBFUSCATED));
        // Appelle une méthode
        var json1 = GsonComponentSerializer.gson().serialize(component1);
        // Appelle une méthode
        var json2 = GsonComponentSerializer.gson().serialize(component2);

        // Appelle une méthode
        assertInvalidArg(arg, "invalid component");
        // Appelle une méthode
        assertArg(arg, component1, json1);
        // Appelle une méthode
        assertArg(arg, component2, json2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentEntity() {
        // Appelle une méthode
        var arg = ArgumentType.Entity("entity");

        // Appelle une méthode
        assertValidArg(arg, "@a");
        // Appelle une méthode
        assertValidArg(arg, "@p");
        // Appelle une méthode
        assertInvalidArg(arg, "@x");

        // Appelle une méthode
        assertValidArg(arg, "@e[type=sheep]");
        // Appelle une méthode
        assertValidArg(arg, "@e[type=!cow]");
        // Appelle une méthode
        assertInvalidArg(arg, "@e[type=invalid_entity]");
        // Appelle une méthode
        assertInvalidArg(arg, "@e[type=!invalid_entity_two]");

        // Appelle une méthode
        assertValidArg(arg, "@e[gamemode=creative]");
        // Appelle une méthode
        assertValidArg(arg, "@e[gamemode=!survival]");
        // Appelle une méthode
        assertInvalidArg(arg, "@e[gamemode=invalid_gamemode]");
        // Appelle une méthode
        assertInvalidArg(arg, "@e[gamemode=!invalid_gamemode_2]");

        // Appelle une méthode
        assertValidArg(arg, "@e[limit=500]");
        // Appelle une méthode
        assertInvalidArg(arg, "@e[limit=-500]");
        // Appelle une méthode
        assertInvalidArg(arg, "@e[limit=invalid_integer]");
        // Appelle une méthode
        assertInvalidArg(arg, "@e[limit=2147483648]");

        // Appelle une méthode
        assertValidArg(arg, "@e[sort=nearest]");
        // Appelle une méthode
        assertInvalidArg(arg, "@e[sort=invalid_sort]");

        // Appelle une méthode
        assertValidArg(arg, "@e[level=55]");
        // Appelle une méthode
        assertValidArg(arg, "@e[level=100..500]");
        // Appelle une méthode
        assertInvalidArg(arg, "@e[level=20-50]");
        // Appelle une méthode
        assertInvalidArg(arg, "@e[level=2147483648]");

        // Appelle une méthode
        assertValidArg(arg, "@e[distance=500]");
        // Appelle une méthode
        assertValidArg(arg, "@e[distance=50..150]");
        // Appelle une méthode
        assertInvalidArg(arg, "@e[distance=-500-500]");
        // Appelle une méthode
        assertInvalidArg(arg, "@e[distance=2147483648]");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentFloatRange() {
        // Appelle une méthode
        var arg = ArgumentType.FloatRange("float_range");
        // Appelle une méthode
        assertArg(arg, new Range.Float(0f, 50f), "0..50");
        // Appelle une méthode
        assertArg(arg, new Range.Float(0f, 0f), "0..0");
        // Appelle une méthode
        assertArg(arg, new Range.Float(-50f, 0f), "-50..0");
        // Appelle une méthode
        assertArg(arg, new Range.Float(-Float.MAX_VALUE, 50f), "..50");
        // Appelle une méthode
        assertArg(arg, new Range.Float(0f, Float.MAX_VALUE), "0..");
        // Appelle une méthode
        assertArg(arg, new Range.Float(-Float.MAX_VALUE, Float.MAX_VALUE), "-3.4028235E38..3.4028235E38");
        // Appelle une méthode
        assertArg(arg, new Range.Float(0.5f, 24f), "0.5..24");
        // Appelle une méthode
        assertArg(arg, new Range.Float(12f, 45.6f), "12..45.6");
        // Appelle une méthode
        assertInvalidArg(arg, "..");
        // Appelle une méthode
        assertInvalidArg(arg, "0..50..");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentIntRange() {
        // Appelle une méthode
        var arg = ArgumentType.IntRange("int_range");

        // Appelle une méthode
        assertArg(arg, new Range.Int(0, 50), "0..50");
        // Appelle une méthode
        assertArg(arg, new Range.Int(0, 0), "0..0");
        // Appelle une méthode
        assertArg(arg, new Range.Int(-50, 0), "-50..0");
        // Appelle une méthode
        assertArg(arg, new Range.Int(Integer.MIN_VALUE, 50), "..50");
        // Appelle une méthode
        assertArg(arg, new Range.Int(0, Integer.MAX_VALUE), "0..");
        // Appelle une méthode
        assertArg(arg, new Range.Int(Integer.MIN_VALUE, Integer.MAX_VALUE), "-2147483648..2147483647");

        // Appelle une méthode
        assertInvalidArg(arg, "..");
        // Appelle une méthode
        assertInvalidArg(arg, "-2147483649..2147483647");
        // Appelle une méthode
        assertInvalidArg(arg, "-2147483648..2147483648");
        // Appelle une méthode
        assertInvalidArg(arg, "0..50..");
        // Appelle une méthode
        assertInvalidArg(arg, "0.5..24");
        // Appelle une méthode
        assertInvalidArg(arg, "12..45.6");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentItemStack() {
        // Appelle une méthode
        var arg = ArgumentType.ItemStack("item_stack");
        // Appelle une méthode
        assertArg(arg, ItemStack.AIR, "air");
        // Appelle une méthode
        assertArg(arg, ItemStack.of(Material.GLASS_PANE).withTag(Tag.String("tag"), "value"), "glass_pane{tag:value}");
        // Appelle une méthode
        assertArg(arg, ItemStack.of(Material.GLASS_PANE).with(DataComponents.REPAIR_COST, 5), "glass_pane[repair_cost=5]");
        // Appelle une méthode
        assertArg(arg, ItemStack.of(Material.GLASS_PANE).with(DataComponents.REPAIR_COST, 5).withTag(Tag.String("tag"), "value"), "glass_pane[repair_cost=5]{tag:value}");
        // Instruction de code
        assertArg(arg, ItemStack.of(Material.GLASS_PANE).with(DataComponents.REPAIR_COST, 5).with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putInt("hi", 232).build())).withTag(Tag.String("tag"), "value"),
                // Affecte une valeur
                "glass_pane[repair_cost=5,minecraft:custom_data={hi:232}]{tag:value}");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentNbtCompoundTag() {
        // Appelle une méthode
        var arg = ArgumentType.NbtCompound("nbt_compound");
        // Instruction de code
        assertArg(arg, CompoundBinaryTag.builder().putLongArray("long_array", new long[]{12, 49, 119}).build(),
                // Instruction de code
                "{\"long_array\":[L;12L,49L,119L]}");
        // Instruction de code
        assertArg(arg, CompoundBinaryTag.builder().put("nested", CompoundBinaryTag.builder().putIntArray("complex", new int[]{124, 999, 33256}).build()).build(),
                // Instruction de code
                "{\"nested\": {\"complex\": [I;124,999,33256]}}");

        // Appelle une méthode
        assertInvalidArg(arg, "string");
        // Appelle une méthode
        assertInvalidArg(arg, "\"string\"");
        // Appelle une méthode
        assertInvalidArg(arg, "44");
        // Appelle une méthode
        assertInvalidArg(arg, "[I;11,49,33]");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentNbtTag() {
        // Appelle une méthode
        var arg = ArgumentType.NBT("nbt");
        // Appelle une méthode
        assertArg(arg, StringBinaryTag.stringBinaryTag("string"), "string");
        // Appelle une méthode
        assertArg(arg, StringBinaryTag.stringBinaryTag("string"), "\"string\"");
        // Appelle une méthode
        assertArg(arg, IntBinaryTag.intBinaryTag(44), "44");
        // Appelle une méthode
        assertArg(arg, IntArrayBinaryTag.intArrayBinaryTag(11, 49, 33), "[I;11,49,33]");
        // Instruction de code
        assertArg(arg, CompoundBinaryTag.builder().putLongArray("long_array", new long[]{12, 49, 119}).build(),
                // Instruction de code
                "{\"long_array\":[L;12L,49L,119L]}");

        // Appelle une méthode
        assertInvalidArg(arg, "\"unbalanced string");
        // Appelle une méthode
        assertInvalidArg(arg, "dd}");
        // Appelle une méthode
        assertInvalidArg(arg, "{unquoted: string)}");
        // Appelle une méthode
        assertInvalidArg(arg, "{\"array\": [D;123L,5L]}");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentResource() {
        // Appelle une méthode
        var arg = ArgumentType.Resource("resource", "minecraft:block");
        // Appelle une méthode
        assertArg(arg, "minecraft:resource_example", "minecraft:resource_example");
        // Appelle une méthode
        assertInvalidArg(arg, "minecraft:invalid resource");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentResourceLocation() {
        // Appelle une méthode
        var arg = ArgumentType.ResourceLocation("resource_location");

        // Appelle une méthode
        assertArg(arg, Key.key("foo:bar"), "foo:bar");
        // Appelle une méthode
        assertArg(arg, Key.key("minecraft:air"), "air");
        // Appelle une méthode
        assertArg(arg, Key.key("minecraft:foo/bar"), "foo/bar");

        // Appelle une méthode
        assertInvalidArg(arg, "minecraft:invalid resource location");
        // Appelle une méthode
        assertInvalidArg(arg, "!");
        // Appelle une méthode
        assertInvalidArg(arg, "a/b:empty");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentResourceOrTag() {
        // Appelle une méthode
        var arg = ArgumentType.ResourceOrTag("resource_or_tag", "data/minecraft/tags/blocks");
        // Appelle une méthode
        assertArg(arg, "minecraft:resource_or_tag_example", "minecraft:resource_or_tag_example");
        // Appelle une méthode
        assertInvalidArg(arg, "minecraft:invalid resource or tag");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentTime() {
        // Appelle une méthode
        var arg = ArgumentType.Time("time");
        // Appelle une méthode
        assertArg(arg, Duration.of(20, TimeUnit.SERVER_TICK), "20");
        // Appelle une méthode
        assertArg(arg, Duration.of(40, TimeUnit.SERVER_TICK), "40t");
        // Appelle une méthode
        assertArg(arg, Duration.of(60, TimeUnit.SECOND), "60s");
        // Appelle une méthode
        assertArg(arg, Duration.of(80, TimeUnit.DAY), "80d");

        // Appelle une méthode
        assertInvalidArg(arg, "100x");
        // Appelle une méthode
        assertInvalidArg(arg, "2147483648t");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentUUID() {
        // Appelle une méthode
        var arg = ArgumentType.UUID("uuid");
        // Appelle une méthode
        assertInvalidArg(arg, "invalid_uuid");
        // Appelle une méthode
        assertArg(arg, UUID.fromString("10515090-26f2-49fa-b2ba-9594d4d0451f"), "10515090-26f2-49fa-b2ba-9594d4d0451f");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentDouble() {
        // Appelle une méthode
        var arg = ArgumentType.Double("double");
        // Appelle une méthode
        assertArg(arg, 2564d, "2564");
        // Appelle une méthode
        assertArg(arg, -591.981d, "-591.981");
        // Appelle une méthode
        assertInvalidArg(arg, "-5.5.52");
        // Appelle une méthode
        assertInvalidArg(arg, "++2.99");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentFloat() {
        // Appelle une méthode
        var arg = ArgumentType.Float("float");
        // Appelle une méthode
        assertArg(arg, 2564f, "2564");
        // Appelle une méthode
        assertArg(arg, -591.981f, "-591.981");
        // Appelle une méthode
        assertInvalidArg(arg, "-5.5.52");
        // Appelle une méthode
        assertInvalidArg(arg, "++2.99");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentInteger() {
        // Appelle une méthode
        var arg = ArgumentType.Integer("integer");
        // Appelle une méthode
        assertArg(arg, 2564, "2564");
        // Appelle une méthode
        assertInvalidArg(arg, "256.4");
        // Appelle une méthode
        assertInvalidArg(arg, "2147483648");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentLong() {
        // Appelle une méthode
        var arg = ArgumentType.Long("long");
        // Appelle une méthode
        assertArg(arg, 2564l, "2564");
        // Appelle une méthode
        assertInvalidArg(arg, "256.4");
        // Appelle une méthode
        assertInvalidArg(arg, "9223372036854775808");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentRelativeBlockPosition() {
        // Appelle une méthode
        var arg = ArgumentType.RelativeBlockPosition("relative_block_position");
        // Appelle une méthode
        var vec = new Vec(-3, 14, 255);

        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.ABSOLUTE, false, false, false), "-3 14 +255");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, true, false, false), "~-3 14 +255");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, false, true, false), "-3 ~14 +255");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, false, false, true), "-3 14 ~+255");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, true, true, true), "~-3 ~14 ~+255");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.LOCAL, true, true, true), "^-3 ^14 ^+255");

        // Appelle une méthode
        assertInvalidArg(arg, "-3.50 14 +255");
        // Appelle une méthode
        assertInvalidArg(arg, "-3 14.25 +255");
        // Appelle une méthode
        assertInvalidArg(arg, "-3 14 +255.75");
        // Appelle une méthode
        assertInvalidArg(arg, "-3 14 +-255");
        // Appelle une méthode
        assertInvalidArg(arg, "-3 text -255");
        // Appelle une méthode
        assertInvalidArg(arg, "-3 14 ~~+255");
        // Appelle une méthode
        assertInvalidArg(arg, "^-3 ~14 ^+255");
        // Appelle une méthode
        assertInvalidArg(arg, "^-3 14 ^+255");
        // Appelle une méthode
        assertInvalidArg(arg, "1 2");
        // Appelle une méthode
        assertInvalidArg(arg, "1 2 3 4");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentRelativeVec2() {
        // Appelle une méthode
        var arg = ArgumentType.RelativeVec2("relative_vec_2");
        // Appelle une méthode
        var vec = new Vec(-3, 14.25);

        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.ABSOLUTE, false, false, false), "-3 14.25");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, true, false, false), "~-3 14.25");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, false, false, true), "-3 ~14.25");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, false, false, true), "-3 ~14.25");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, true, false, true), "~-3 ~14.25");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.LOCAL, true, false, true), "^-3 ^14.25");

        // Appelle une méthode
        assertInvalidArg(arg, "-3 +-14");
        // Appelle une méthode
        assertInvalidArg(arg, "-3 text");
        // Appelle une méthode
        assertInvalidArg(arg, "~~-3 14");
        // Appelle une méthode
        assertInvalidArg(arg, "^-3 ~14");
        // Appelle une méthode
        assertInvalidArg(arg, "^-3 14");
        // Appelle une méthode
        assertInvalidArg(arg, "1");
        // Appelle une méthode
        assertInvalidArg(arg, "1 2 3");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentRelativeVec3() {
        // Appelle une méthode
        var arg = ArgumentType.RelativeVec3("relative_vec_3");
        // Appelle une méthode
        var vec = new Vec(-3, 14.25, 255);

        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.ABSOLUTE, false, false, false), "-3 14.25 +255");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, true, false, false), "~-3 14.25 +255");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, false, true, false), "-3 ~14.25 +255");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, false, false, true), "-3 14.25 ~+255");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.RELATIVE, true, true, true), "~-3 ~14.25 ~+255");
        // Appelle une méthode
        assertArg(arg, new RelativeVec(vec, RelativeVec.CoordinateType.LOCAL, true, true, true), "^-3 ^14.25 ^+255");

        // Appelle une méthode
        assertInvalidArg(arg, "-3 14 +-255");
        // Appelle une méthode
        assertInvalidArg(arg, "-3 text -255");
        // Appelle une méthode
        assertInvalidArg(arg, "-3 14 ~~+255");
        // Appelle une méthode
        assertInvalidArg(arg, "^-3 ~14 ^+255");
        // Appelle une méthode
        assertInvalidArg(arg, "^-3 14 ^+255");
        // Appelle une méthode
        assertInvalidArg(arg, "1 2");
        // Appelle une méthode
        assertInvalidArg(arg, "1 2 3 4");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentBoolean() {
        // Appelle une méthode
        var arg = ArgumentType.Boolean("boolean");
        // Appelle une méthode
        assertArg(arg, true, "true");
        // Appelle une méthode
        assertArg(arg, false, "false");
        // Appelle une méthode
        assertInvalidArg(arg, "invalid_boolean");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentEnum() {
        // Déclaration de type (classe/interface/enum/record)
        enum ExampleEnum {FIRST, SECOND, Third, fourth}

        // Appelle une méthode
        var arg = ArgumentType.Enum("enum", ExampleEnum.class);

        // Appelle une méthode
        arg.setFormat(ArgumentEnum.Format.DEFAULT);
        // Appelle une méthode
        assertArg(arg, ExampleEnum.FIRST, "FIRST");
        // Appelle une méthode
        assertArg(arg, ExampleEnum.SECOND, "SECOND");
        // Appelle une méthode
        assertArg(arg, ExampleEnum.Third, "Third");
        // Appelle une méthode
        assertArg(arg, ExampleEnum.fourth, "fourth");
        // Appelle une méthode
        assertInvalidArg(arg, "invalid argument");

        // Appelle une méthode
        arg.setFormat(ArgumentEnum.Format.UPPER_CASED);
        // Appelle une méthode
        assertArg(arg, ExampleEnum.FIRST, "FIRST");
        // Appelle une méthode
        assertArg(arg, ExampleEnum.SECOND, "SECOND");
        // Appelle une méthode
        assertInvalidArg(arg, "Third");
        // Appelle une méthode
        assertInvalidArg(arg, "fourth");
        // Appelle une méthode
        assertInvalidArg(arg, "invalid argument");

        // Appelle une méthode
        arg.setFormat(ArgumentEnum.Format.LOWER_CASED);
        // Appelle une méthode
        assertInvalidArg(arg, "FIRST");
        // Appelle une méthode
        assertInvalidArg(arg, "SECOND");
        // Appelle une méthode
        assertInvalidArg(arg, "Third");
        // Appelle une méthode
        assertArg(arg, ExampleEnum.fourth, "fourth");
        // Appelle une méthode
        assertInvalidArg(arg, "invalid argument");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentGroup() {
        // Appelle une méthode
        var arg = ArgumentType.Group("group", ArgumentType.Integer("integer"), ArgumentType.String("string"), ArgumentType.Double("double"));

        // Test normal input
        // Appelle une méthode
        var context1 = arg.parse(new ServerSender(), "1234 1234 1234");
        // Appelle une méthode
        assertEquals(1234, context1.<Integer>get("integer"));
        // Appelle une méthode
        assertEquals("1234", context1.<String>get("string"));
        // Appelle une méthode
        assertEquals(1234.0, context1.<Double>get("double"));

        // Test different input + trailing spaces
        // Appelle une méthode
        var context2 = arg.parse(new ServerSender(), "1234 abcd 1234.5678   ");
        // Appelle une méthode
        assertEquals(1234, context2.<Integer>get("integer"));
        // Appelle une méthode
        assertEquals("abcd", context2.<String>get("string"));
        // Appelle une méthode
        assertEquals(1234.5678, context2.<Double>get("double"));

        // Appelle une méthode
        assertInvalidArg(arg, "");
        // Appelle une méthode
        assertInvalidArg(arg, "");
        // Appelle une méthode
        assertInvalidArg(arg, "");
        // Appelle une méthode
        assertInvalidArg(arg, "1234.5678 1234 1234.5678");
        // Appelle une méthode
        assertInvalidArg(arg, "1234 1234 abcd");
        // Appelle une méthode
        assertInvalidArg(arg, "1234 1234 ");
        // Appelle une méthode
        assertInvalidArg(arg, "1234");
        // Appelle une méthode
        assertInvalidArg(arg, "1234 abcd 1234.5678 extra");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentLiteral() {
        // Appelle une méthode
        var arg = ArgumentType.Literal("literal");
        // Appelle une méthode
        assertArg(arg, "literal", "literal");
        // Appelle une méthode
        assertInvalidArg(arg, "not_literal");
        // Appelle une méthode
        assertInvalidArg(arg, "");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentLoop() {
        // Affecte une valeur
        var arg = ArgumentType.Loop("loop", ArgumentType.String("string"), ArgumentType.String("string2").map(s -> {
            // Lève une exception
            throw new IllegalArgumentException("This argument should never be triggered");
        // Instruction de code
        }));

        // Appelle une méthode
        assertArg(arg, List.of("a", "b", "c"), "a b c");
        // Appelle une méthode
        assertArg(arg, List.of("a", "b"), "a b");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentString() {
        // Appelle une méthode
        var arg = ArgumentType.String("string");
        // Appelle une méthode
        assertArg(arg, "text", "text");
        // Appelle une méthode
        assertArg(arg, "more text", "\"more text\"");
        // Appelle une méthode
        assertArg(arg, "more text, but with \"escaped\" quotes", "\"more text, but with \\\"escaped\\\" quotes\"");
        // Appelle une méthode
        assertInvalidArg(arg, "\"unclosed quotes");
        // Appelle une méthode
        assertInvalidArg(arg, "\"unescaped \" quotes\"");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentStringArray() {
        // Appelle une méthode
        var arg = ArgumentType.StringArray("string_array");
        // Appelle une méthode
        assertArrayArg(arg, new String[]{"example", "text"}, "example text");
        // Appelle une méthode
        assertArrayArg(arg, new String[]{"some", "more", "placeholder", "text"}, "some more placeholder text");
        // Appelle une méthode
        assertArrayArg(arg, new String[]{""}, "");
        // Appelle une méthode
        assertArrayArg(arg, new String[0], " ");
        // Appelle une méthode
        assertArrayArg(arg, new String[0], "         ");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentWord() {
        // Appelle une méthode
        var arg = ArgumentType.Word("word").from("word1", "word2", "word3");

        // Appelle une méthode
        assertArg(arg, "word1", "word1");
        // Appelle une méthode
        assertArg(arg, "word2", "word2");
        // Appelle une méthode
        assertArg(arg, "word3", "word3");

        // Appelle une méthode
        assertInvalidArg(arg, "word");
        // Appelle une méthode
        assertInvalidArg(arg, "word4");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentTransformWithSender() {
        // Appelle une méthode
        var serverSender = new ServerSender();

        // Affecte une valeur
        var arg = ArgumentType.Word("word").from("word1", "word2", "word3")
                // Début d'une méthode/d'un bloc
                .map((sender, s) -> {
                    // Appelle une méthode
                    assertEquals(serverSender, sender);
                    // Renvoie une valeur à l'appelant
                    return s;
                // Fin d'un bloc/d'une expression
                });

        // Appelle une méthode
        assertEquals("word1", arg.parse(serverSender, "word1"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> void assertArg(Argument<T> arg, T expected, String input) {
        // Appelle une méthode
        assertEquals(expected, arg.parse(new ServerSender(), input));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> void assertArrayArg(Argument<T[]> arg, T[] expected, String input) {
        // Appelle une méthode
        assertArrayEquals(expected, arg.parse(new ServerSender(), input));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> void assertValidArg(Argument<T> arg, String input) {
        // Appelle une méthode
        assertDoesNotThrow(() -> arg.parse(new ServerSender(), input));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> void assertInvalidArg(Argument<T> arg, String input) {
        // Appelle une méthode
        assertThrows(ArgumentSyntaxException.class, () -> arg.parse(new ServerSender(), input));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
