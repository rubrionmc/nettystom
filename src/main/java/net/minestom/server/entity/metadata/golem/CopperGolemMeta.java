// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.golem;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Déclaration de type (classe/interface/enum/record)
public class CopperGolemMeta extends AbstractGolemMeta {

    // Début d'une méthode/d'un bloc
    public CopperGolemMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public WeatherState getWeatherState() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.CopperGolem.WEATHER_STATE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setWeatherState(WeatherState weatherState) {
        // Appelle une méthode
        metadata.set(MetadataDef.CopperGolem.WEATHER_STATE, weatherState);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public State getState() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.CopperGolem.STATE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setState(State state) {
        // Appelle une méthode
        metadata.set(MetadataDef.CopperGolem.STATE, state);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum WeatherState {
        // Instruction de code
        UNAFFECTED,
        // Instruction de code
        EXPOSED,
        // Instruction de code
        WEATHERED,
        // Instruction de code
        OXIDIZED;

        // Appelle une méthode
        public static final NetworkBuffer.Type<WeatherState> NETWORK_TYPE = NetworkBuffer.Enum(WeatherState.class);
        // Appelle une méthode
        public static final Codec<WeatherState> CODEC = Codec.Enum(WeatherState.class);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum State {
        // Instruction de code
        IDLE,
        // Instruction de code
        GETTING_ITEM,
        // Instruction de code
        GETTING_NO_ITEM,
        // Instruction de code
        DROPPING_ITEM,
        // Instruction de code
        DROPPING_NO_ITEM;

        // Appelle une méthode
        public static final NetworkBuffer.Type<State> NETWORK_TYPE = NetworkBuffer.Enum(State.class);
        // Appelle une méthode
        public static final Codec<State> CODEC = Codec.Enum(State.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
