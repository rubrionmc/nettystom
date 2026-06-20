// Package declaration for this file
package net.minestom.server.entity.metadata;

// https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Object_Data
// Type declaration (class/interface/enum/record)
public interface ObjectDataProvider {

    // Calls a method
    int getObjectData();

    // Calls a method
    boolean requiresVelocityPacketAtSpawn();

// End of a block/expression
}
