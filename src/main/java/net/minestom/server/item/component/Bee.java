// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Déclaration de type (classe/interface/enum/record)
public record Bee(CustomData entityData, int ticksInHive, int minTicksInHive) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<Bee> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            CustomData.NETWORK_TYPE, Bee::entityData,
            // Instruction de code
            NetworkBuffer.VAR_INT, Bee::ticksInHive,
            // Instruction de code
            NetworkBuffer.VAR_INT, Bee::minTicksInHive,
            // Instruction de code
            Bee::new);
    // Affecte une valeur
    public static final Codec<Bee> CODEC = StructCodec.struct(
            // Instruction de code
            "entity_data", CustomData.CODEC, Bee::entityData,
            // Instruction de code
            "ticks_in_hive", Codec.INT, Bee::ticksInHive,
            // Instruction de code
            "min_ticks_in_hive", Codec.INT, Bee::minTicksInHive,
            // Instruction de code
            Bee::new);

    // Début d'une méthode/d'un bloc
    public Bee withEntityData(CustomData entityData) {
        // Renvoie une valeur à l'appelant
        return new Bee(entityData, ticksInHive, minTicksInHive);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Bee withTicksInHive(int ticksInHive) {
        // Renvoie une valeur à l'appelant
        return new Bee(entityData, ticksInHive, minTicksInHive);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Bee withMinTicksInHive(int minTicksInHive) {
        // Renvoie une valeur à l'appelant
        return new Bee(entityData, ticksInHive, minTicksInHive);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
