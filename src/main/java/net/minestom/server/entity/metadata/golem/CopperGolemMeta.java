// Package declaration for this file
package net.minestom.server.entity.metadata.golem;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Type declaration (class/interface/enum/record)
public class CopperGolemMeta extends AbstractGolemMeta {

    // Start of a method/block
    public CopperGolemMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public WeatherState getWeatherState() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.CopperGolem.WEATHER_STATE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setWeatherState(WeatherState weatherState) {
        // Calls a method
        metadata.set(MetadataDef.CopperGolem.WEATHER_STATE, weatherState);
    // End of a block/expression
    }

    // Start of a method/block
    public State getState() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.CopperGolem.STATE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setState(State state) {
        // Calls a method
        metadata.set(MetadataDef.CopperGolem.STATE, state);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum WeatherState {
        // Code statement
        UNAFFECTED,
        // Code statement
        EXPOSED,
        // Code statement
        WEATHERED,
        // Code statement
        OXIDIZED;

        // Calls a method
        public static final NetworkBuffer.Type<WeatherState> NETWORK_TYPE = NetworkBuffer.Enum(WeatherState.class);
        // Calls a method
        public static final Codec<WeatherState> CODEC = Codec.Enum(WeatherState.class);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum State {
        // Code statement
        IDLE,
        // Code statement
        GETTING_ITEM,
        // Code statement
        GETTING_NO_ITEM,
        // Code statement
        DROPPING_ITEM,
        // Code statement
        DROPPING_NO_ITEM;

        // Calls a method
        public static final NetworkBuffer.Type<State> NETWORK_TYPE = NetworkBuffer.Enum(State.class);
        // Calls a method
        public static final Codec<State> CODEC = Codec.Enum(State.class);
    // End of a block/expression
    }
// End of a block/expression
}
