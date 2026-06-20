// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata;

// https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Object_Data
// Déclaration de type (classe/interface/enum/record)
public interface ObjectDataProvider {

    // Appelle une méthode
    int getObjectData();

    // Appelle une méthode
    boolean requiresVelocityPacketAtSpawn();

// Fin d'un bloc/d'une expression
}
