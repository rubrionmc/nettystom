// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import org.intellij.lang.annotations.Subst;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
final class RegistryCodecs {

    // Déclaration de type (classe/interface/enum/record)
    record RegistryKeyImpl<T>(Registries.Selector<T> selector) implements Codec<RegistryKey<T>> {
        // Début d'une méthode/d'un bloc
        RegistryKeyImpl {
            // Appelle une méthode
            Objects.requireNonNull(selector, "selector");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<RegistryKey<T>> decode(Transcoder<D> coder, D value) {
            // Embranchement : vérifie une condition
            if (!(coder instanceof RegistryTranscoder<D> context))
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Missing registries in transcoder");
            // Appelle une méthode
            final var registry = selector.select(context.registries());
            // Appelle une méthode
            final Result<String> referenceResult = coder.getString(value);
            // Embranchement : vérifie une condition
            if (!(referenceResult instanceof Result.Ok(@Subst("a")String reference)))
                // Renvoie une valeur à l'appelant
                return referenceResult.cast();
            // Appelle une méthode
            final RegistryKey<T> key = registry.getKey(Key.key(reference));
            // Embranchement : vérifie une condition
            if (key == null) return new Result.Error<>("Unknown key " + reference + " for registry " + registry.key());
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(key);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable RegistryKey<T> value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Embranchement : vérifie une condition
            if (!(coder instanceof RegistryTranscoder<D>))
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Missing registries in transcoder");
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(coder.createString(value.key().asString()));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record HolderCodec<T>(
            // Instruction de code
            Registries.Selector<T> selector,
            // Instruction de code
            Codec<T> registryCodec
    // Début d'une méthode/d'un bloc
    ) implements Codec<Holder<T>> {
        // Début d'une méthode/d'un bloc
        HolderCodec {
            // Appelle une méthode
            Objects.requireNonNull(selector, "selector");
            // Appelle une méthode
            Objects.requireNonNull(registryCodec, "registryCodec");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<Holder<T>> decode(Transcoder<D> coder, D value) {
            // Embranchement : vérifie une condition
            if (!(coder instanceof RegistryTranscoder<D> context))
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Missing registries in transcoder");
            // Appelle une méthode
            final var registry = selector.select(context.registries());
            // Appelle une méthode
            final Result<T> directResult = registryCodec.decode(coder, value);
            // Embranchement : vérifie une condition
            if (directResult instanceof Result.Ok(T direct))
                //noinspection unchecked
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>((Holder<T>) direct);
            // Appelle une méthode
            final Result<String> referenceResult = coder.getString(value);
            // Embranchement : vérifie une condition
            if (!(referenceResult instanceof Result.Ok(@Subst("a")String reference)))
                // Renvoie une valeur à l'appelant
                return referenceResult.cast();
            // Appelle une méthode
            final RegistryKey<T> key = registry.getKey(Key.key(reference));
            // Embranchement : vérifie une condition
            if (key == null) return new Result.Error<>("Unknown key " + reference + " for registry " + registry.key());
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(key);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Holder<T> value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Embranchement : vérifie une condition
            if (!(coder instanceof RegistryTranscoder<D>))
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Missing registries in transcoder");
            // Renvoie une valeur à l'appelant
            return switch (value.unwrap()) {
                // Embranchement multiple (switch/case)
                case Either.Left(RegistryKey<T> key) -> new Result.Ok<>(coder.createString(key.key().asString()));
                // Embranchement multiple (switch/case)
                case Either.Right(T direct) -> registryCodec.encode(coder, direct);
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record TagKeyImpl<T>(Registries.Selector<T> selector, boolean hash) implements Codec<TagKey<T>> {
        // Début d'une méthode/d'un bloc
        TagKeyImpl {
            // Appelle une méthode
            Objects.requireNonNull(selector, "selector");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<TagKey<T>> decode(Transcoder<D> coder, D value) {
            // Embranchement : vérifie une condition
            if (!(coder instanceof RegistryTranscoder<D> context))
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Missing registries in transcoder");
            // Appelle une méthode
            final var registry = selector.select(context.registries());
            // Appelle une méthode
            final var result = coder.getString(value);
            // Embranchement : vérifie une condition
            if (!(result instanceof Result.Ok(@Subst("a")String reference)))
                // Renvoie une valeur à l'appelant
                return result.cast();
            // Embranchement : vérifie une condition
            if (hash) {
                // Embranchement : vérifie une condition
                if (reference.length() < 2 || reference.charAt(0) != '#')
                    // Renvoie une valeur à l'appelant
                    return new Result.Error<>("Invalid tag hash: " + reference);
                // Appelle une méthode
                reference = reference.substring(1);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final TagKey<T> tagKey = new net.minestom.server.registry.TagKeyImpl<>(Key.key(reference));
            // Embranchement : vérifie une condition
            if (registry.getTag(tagKey) == null)
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Unknown tag " + reference + " for registry " + registry.key());
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(tagKey);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable TagKey<T> value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Embranchement : vérifie une condition
            if (!(coder instanceof RegistryTranscoder<D>))
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Missing registries in transcoder");
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(coder.createString(hash ? value.hashedKey() : value.key().asString()));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record RegistryTagImpl<T>(Registries.Selector<T> selector) implements Codec<RegistryTag<T>> {
        // Per vanilla, this codec supports registryless context, in which case it can only decode direct tags.
        // Début d'une méthode/d'un bloc
        RegistryTagImpl {
            // Appelle une méthode
            Objects.requireNonNull(selector, "selector");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<RegistryTag<T>> decode(Transcoder<D> coder, D value) {
            // Affecte une valeur
            final var context = coder instanceof RegistryTranscoder<D> transcoder ? transcoder : null;
            // Appelle une méthode
            final var registry = context != null ? selector.select(context.registries()) : null;
            // Appelle une méthode
            final Result<String> tagKeyResult = coder.getString(value);
            // Embranchement : vérifie une condition
            if (tagKeyResult instanceof Result.Ok(String tagKeyStr)) {
                // Embranchement : vérifie une condition
                if (registry != null && tagKeyStr.startsWith("#")) {
                    // Appelle une méthode
                    final var tagKey = TagKey.<T>ofHash(tagKeyStr);
                    // During initialization of the registry we allow creating tags that do not exist yet, otherwise we do not.
                    // Appelle une méthode
                    final var tag = context.init() ? registry.getOrCreateTag(tagKey) : registry.getTag(tagKey);
                    // Renvoie une valeur à l'appelant
                    return tag != null ? new Result.Ok<>(tag)
                            // Appelle une méthode
                            : new Result.Error<>("Unknown tag " + tagKey + " for registry " + registry.key());
                // Fin d'un bloc/d'une expression
                }
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(RegistryTag.direct(RegistryKey.unsafeOf(tagKeyStr)));
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final Result<List<D>> entriesResult = coder.getList(value);
            // Embranchement : vérifie une condition
            if (entriesResult instanceof Result.Ok(List<D> entries)) {
                // Appelle une méthode
                final Set<RegistryKey<T>> keys = new HashSet<>(entries.size());
                // Boucle : répète un bloc
                for (D entry : entries) {
                    // Appelle une méthode
                    final Result<String> keyResult = coder.getString(entry);
                    // Embranchement : vérifie une condition
                    if (!(keyResult instanceof Result.Ok(@Subst("a")String key)))
                        // Renvoie une valeur à l'appelant
                        return keyResult.mapError(e -> "Invalid tag entry: " + e).cast();
                    // Appelle une méthode
                    final RegistryKey<T> registryKey = registry != null ? registry.getKey(Key.key(key)) : RegistryKey.unsafeOf(key);
                    // Embranchement : vérifie une condition
                    if (registryKey == null)
                        // Renvoie une valeur à l'appelant
                        return new Result.Error<>("Unknown key " + key + " for registry " + registry.key());
                    // Appelle une méthode
                    keys.add(registryKey);
                // Fin d'un bloc/d'une expression
                }
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(RegistryTag.direct(keys));
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Invalid tag value: " + value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable RegistryTag<T> value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Renvoie une valeur à l'appelant
            return switch (value) {
                // Embranchement multiple (switch/case)
                case net.minestom.server.registry.RegistryTagImpl.Backed<T> backed ->
                        // Crée un nouvel objet
                        new Result.Ok<>(coder.createString(backed.key().hashedKey()));
                // Embranchement multiple (switch/case)
                case net.minestom.server.registry.RegistryTagImpl.Empty() -> new Result.Ok<>(coder.emptyList());
                // Embranchement multiple (switch/case)
                case net.minestom.server.registry.RegistryTagImpl.Direct(var entries) -> {
                    // Embranchement : vérifie une condition
                    if (entries.isEmpty()) yield new Result.Ok<>(coder.emptyList());
                    // Embranchement : vérifie une condition
                    if (entries.size() == 1)
                        // Appelle une méthode
                        yield new Result.Ok<>(coder.createString(entries.getFirst().key().asString()));
                    // Appelle une méthode
                    final Transcoder.ListBuilder<D> result = coder.createList(entries.size());
                    // Boucle : répète un bloc
                    for (final RegistryKey<T> key : entries)
                        // Appelle une méthode
                        result.add(coder.createString(key.key().asString()));
                    // Appelle une méthode
                    yield new Result.Ok<>(result.build());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record HolderSetImpl<T extends Holder<T>>(
            // Instruction de code
            Codec<RegistryTag<T>> tagCodec,
            // Instruction de code
            Codec<T> directCodec
    // Début d'une méthode/d'un bloc
    ) implements Codec<HolderSet<T>> {
        // Début d'une méthode/d'un bloc
        HolderSetImpl {
            // Appelle une méthode
            Objects.requireNonNull(tagCodec, "tagCodec");
            // Appelle une méthode
            Objects.requireNonNull(directCodec, "directCodec");
        // Fin d'un bloc/d'une expression
        }
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<HolderSet<T>> decode(Transcoder<D> coder, D value) {
            // First try to decode as a tag
            // Appelle une méthode
            final Result<RegistryTag<T>> tagResult = tagCodec.decode(coder, value);
            // Embranchement : vérifie une condition
            if (tagResult instanceof Result.Ok(RegistryTag<T> tag))
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(tag);

            // Otherwise try to decode as a direct holder set
            // Appelle une méthode
            final Result<List<D>> entriesResult = coder.getList(value);
            // Embranchement : vérifie une condition
            if (!(entriesResult instanceof Result.Ok(List<D> entries)))
                // Renvoie une valeur à l'appelant
                return entriesResult.mapError(e -> "Invalid holder set value: " + e).cast();

            // Appelle une méthode
            final List<T> directEntries = new ArrayList<>(entries.size());
            // Boucle : répète un bloc
            for (D entry : entries) {
                // Appelle une méthode
                final Result<T> directResult = directCodec.decode(coder, entry);
                // Embranchement : vérifie une condition
                if (directResult instanceof Result.Ok(T direct)) {
                    // Appelle une méthode
                    directEntries.add(direct);
                // Branche alternative de la condition
                } else {
                    // Renvoie une valeur à l'appelant
                    return directResult.mapError(e -> "Invalid holder set entry: " + e).cast();
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // This raw type is kinda gross. Its safe because direct is checked only
            // to be instantiated with Holder.Direct types, but HolderSet itself supports non-direct types.
            //noinspection rawtypes,unchecked
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>((HolderSet<T>) new HolderSet.Direct(directEntries));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable HolderSet<T> value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Renvoie une valeur à l'appelant
            return switch (value) {
                // Embranchement multiple (switch/case)
                case RegistryTag<T> tag -> tagCodec.encode(coder, tag);
                // This raw type is kinda gross. Its safe because direct is checked only
                // to be instantiated with Holder.Direct types, but HolderSet itself supports non-direct types.
                //noinspection rawtypes
                // Embranchement multiple (switch/case)
                case HolderSet.Direct d -> {
                    // Appelle une méthode
                    final Transcoder.ListBuilder<D> result = coder.createList(d.values().size());
                    // Boucle : répète un bloc
                    for (final Object rawValue : d.values()) {
                        // Appelle une méthode
                        final var directResult = directCodec.encode(coder, (T) rawValue);
                        // Embranchement : vérifie une condition
                        if (directResult instanceof Result.Ok(D direct)) {
                            // Appelle une méthode
                            result.add(direct);
                        // Branche alternative de la condition
                        } else {
                            // Appelle une méthode
                            yield directResult.mapError(e -> "Invalid holder set entry: " + e).cast();
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                    // Appelle une méthode
                    yield new Result.Ok<>(result.build());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
