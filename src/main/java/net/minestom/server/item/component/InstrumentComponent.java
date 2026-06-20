// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.item.instrument.Instrument;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Holder;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record InstrumentComponent(Either<Holder<Instrument>, RegistryKey<Instrument>> instrument) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<InstrumentComponent> NETWORK_TYPE = NetworkBuffer
            // Instruction de code
            .Either(Instrument.NETWORK_TYPE, RegistryKey.<Instrument>uncheckedNetworkType())
            // Appelle une méthode
            .transform(InstrumentComponent::new, InstrumentComponent::instrument);
    // Affecte une valeur
    public static final Codec<InstrumentComponent> CODEC = Codec
            // Instruction de code
            .Either(Instrument.CODEC, RegistryKey.<Instrument>uncheckedCodec())
            // Appelle une méthode
            .transform(InstrumentComponent::new, InstrumentComponent::instrument);

    // Début d'une méthode/d'un bloc
    public @Nullable Instrument resolve(DynamicRegistry<Instrument> registry) {
        // Renvoie une valeur à l'appelant
        return switch (this.instrument) {
            // Embranchement multiple (switch/case)
            case Either.Left(Holder<Instrument> holder) -> holder.resolve(registry);
            // Embranchement multiple (switch/case)
            case Either.Right(RegistryKey<Instrument> reference) -> registry.get(reference);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}