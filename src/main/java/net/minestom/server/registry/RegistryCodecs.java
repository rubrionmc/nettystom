// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import org.intellij.lang.annotations.Subst;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
final class RegistryCodecs {

    // Type declaration (class/interface/enum/record)
    record RegistryKeyImpl<T>(Registries.Selector<T> selector) implements Codec<RegistryKey<T>> {
        // Start of a method/block
        RegistryKeyImpl {
            // Calls a method
            Objects.requireNonNull(selector, "selector");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<RegistryKey<T>> decode(Transcoder<D> coder, D value) {
            // Branch: checks a condition
            if (!(coder instanceof RegistryTranscoder<D> context))
                // Returns a value to the caller
                return new Result.Error<>("Missing registries in transcoder");
            // Calls a method
            final var registry = selector.select(context.registries());
            // Calls a method
            final Result<String> referenceResult = coder.getString(value);
            // Branch: checks a condition
            if (!(referenceResult instanceof Result.Ok(@Subst("a")String reference)))
                // Returns a value to the caller
                return referenceResult.cast();
            // Calls a method
            final RegistryKey<T> key = registry.getKey(Key.key(reference));
            // Branch: checks a condition
            if (key == null) return new Result.Error<>("Unknown key " + reference + " for registry " + registry.key());
            // Returns a value to the caller
            return new Result.Ok<>(key);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable RegistryKey<T> value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Branch: checks a condition
            if (!(coder instanceof RegistryTranscoder<D>))
                // Returns a value to the caller
                return new Result.Error<>("Missing registries in transcoder");
            // Returns a value to the caller
            return new Result.Ok<>(coder.createString(value.key().asString()));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record HolderCodec<T>(
            // Code statement
            Registries.Selector<T> selector,
            // Code statement
            Codec<T> registryCodec
    // Start of a method/block
    ) implements Codec<Holder<T>> {
        // Start of a method/block
        HolderCodec {
            // Calls a method
            Objects.requireNonNull(selector, "selector");
            // Calls a method
            Objects.requireNonNull(registryCodec, "registryCodec");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<Holder<T>> decode(Transcoder<D> coder, D value) {
            // Branch: checks a condition
            if (!(coder instanceof RegistryTranscoder<D> context))
                // Returns a value to the caller
                return new Result.Error<>("Missing registries in transcoder");
            // Calls a method
            final var registry = selector.select(context.registries());
            // Calls a method
            final Result<T> directResult = registryCodec.decode(coder, value);
            // Branch: checks a condition
            if (directResult instanceof Result.Ok(T direct))
                //noinspection unchecked
                // Returns a value to the caller
                return new Result.Ok<>((Holder<T>) direct);
            // Calls a method
            final Result<String> referenceResult = coder.getString(value);
            // Branch: checks a condition
            if (!(referenceResult instanceof Result.Ok(@Subst("a")String reference)))
                // Returns a value to the caller
                return referenceResult.cast();
            // Calls a method
            final RegistryKey<T> key = registry.getKey(Key.key(reference));
            // Branch: checks a condition
            if (key == null) return new Result.Error<>("Unknown key " + reference + " for registry " + registry.key());
            // Returns a value to the caller
            return new Result.Ok<>(key);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Holder<T> value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Branch: checks a condition
            if (!(coder instanceof RegistryTranscoder<D>))
                // Returns a value to the caller
                return new Result.Error<>("Missing registries in transcoder");
            // Returns a value to the caller
            return switch (value.unwrap()) {
                // Multiple branching (switch/case)
                case Either.Left(RegistryKey<T> key) -> new Result.Ok<>(coder.createString(key.key().asString()));
                // Multiple branching (switch/case)
                case Either.Right(T direct) -> registryCodec.encode(coder, direct);
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TagKeyImpl<T>(Registries.Selector<T> selector, boolean hash) implements Codec<TagKey<T>> {
        // Start of a method/block
        TagKeyImpl {
            // Calls a method
            Objects.requireNonNull(selector, "selector");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<TagKey<T>> decode(Transcoder<D> coder, D value) {
            // Branch: checks a condition
            if (!(coder instanceof RegistryTranscoder<D> context))
                // Returns a value to the caller
                return new Result.Error<>("Missing registries in transcoder");
            // Calls a method
            final var registry = selector.select(context.registries());
            // Calls a method
            final var result = coder.getString(value);
            // Branch: checks a condition
            if (!(result instanceof Result.Ok(@Subst("a")String reference)))
                // Returns a value to the caller
                return result.cast();
            // Branch: checks a condition
            if (hash) {
                // Branch: checks a condition
                if (reference.length() < 2 || reference.charAt(0) != '#')
                    // Returns a value to the caller
                    return new Result.Error<>("Invalid tag hash: " + reference);
                // Calls a method
                reference = reference.substring(1);
            // End of a block/expression
            }
            // Calls a method
            final TagKey<T> tagKey = new net.minestom.server.registry.TagKeyImpl<>(Key.key(reference));
            // Branch: checks a condition
            if (registry.getTag(tagKey) == null)
                // Returns a value to the caller
                return new Result.Error<>("Unknown tag " + reference + " for registry " + registry.key());
            // Returns a value to the caller
            return new Result.Ok<>(tagKey);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable TagKey<T> value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Branch: checks a condition
            if (!(coder instanceof RegistryTranscoder<D>))
                // Returns a value to the caller
                return new Result.Error<>("Missing registries in transcoder");
            // Returns a value to the caller
            return new Result.Ok<>(coder.createString(hash ? value.hashedKey() : value.key().asString()));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record RegistryTagImpl<T>(Registries.Selector<T> selector) implements Codec<RegistryTag<T>> {
        // Per vanilla, this codec supports registryless context, in which case it can only decode direct tags.
        // Start of a method/block
        RegistryTagImpl {
            // Calls a method
            Objects.requireNonNull(selector, "selector");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<RegistryTag<T>> decode(Transcoder<D> coder, D value) {
            // Assigns a value
            final var context = coder instanceof RegistryTranscoder<D> transcoder ? transcoder : null;
            // Calls a method
            final var registry = context != null ? selector.select(context.registries()) : null;
            // Calls a method
            final Result<String> tagKeyResult = coder.getString(value);
            // Branch: checks a condition
            if (tagKeyResult instanceof Result.Ok(String tagKeyStr)) {
                // Branch: checks a condition
                if (registry != null && tagKeyStr.startsWith("#")) {
                    // Calls a method
                    final var tagKey = TagKey.<T>ofHash(tagKeyStr);
                    // During initialization of the registry we allow creating tags that do not exist yet, otherwise we do not.
                    // Calls a method
                    final var tag = context.init() ? registry.getOrCreateTag(tagKey) : registry.getTag(tagKey);
                    // Returns a value to the caller
                    return tag != null ? new Result.Ok<>(tag)
                            // Calls a method
                            : new Result.Error<>("Unknown tag " + tagKey + " for registry " + registry.key());
                // End of a block/expression
                }
                // Returns a value to the caller
                return new Result.Ok<>(RegistryTag.direct(RegistryKey.unsafeOf(tagKeyStr)));
            // End of a block/expression
            }
            // Calls a method
            final Result<List<D>> entriesResult = coder.getList(value);
            // Branch: checks a condition
            if (entriesResult instanceof Result.Ok(List<D> entries)) {
                // Calls a method
                final Set<RegistryKey<T>> keys = new HashSet<>(entries.size());
                // Loop: repeats a block
                for (D entry : entries) {
                    // Calls a method
                    final Result<String> keyResult = coder.getString(entry);
                    // Branch: checks a condition
                    if (!(keyResult instanceof Result.Ok(@Subst("a")String key)))
                        // Returns a value to the caller
                        return keyResult.mapError(e -> "Invalid tag entry: " + e).cast();
                    // Calls a method
                    final RegistryKey<T> registryKey = registry != null ? registry.getKey(Key.key(key)) : RegistryKey.unsafeOf(key);
                    // Branch: checks a condition
                    if (registryKey == null)
                        // Returns a value to the caller
                        return new Result.Error<>("Unknown key " + key + " for registry " + registry.key());
                    // Calls a method
                    keys.add(registryKey);
                // End of a block/expression
                }
                // Returns a value to the caller
                return new Result.Ok<>(RegistryTag.direct(keys));
            // End of a block/expression
            }

            // Returns a value to the caller
            return new Result.Error<>("Invalid tag value: " + value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable RegistryTag<T> value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Returns a value to the caller
            return switch (value) {
                // Multiple branching (switch/case)
                case net.minestom.server.registry.RegistryTagImpl.Backed<T> backed ->
                        // Creates a new object
                        new Result.Ok<>(coder.createString(backed.key().hashedKey()));
                // Multiple branching (switch/case)
                case net.minestom.server.registry.RegistryTagImpl.Empty() -> new Result.Ok<>(coder.emptyList());
                // Multiple branching (switch/case)
                case net.minestom.server.registry.RegistryTagImpl.Direct(var entries) -> {
                    // Branch: checks a condition
                    if (entries.isEmpty()) yield new Result.Ok<>(coder.emptyList());
                    // Branch: checks a condition
                    if (entries.size() == 1)
                        // Calls a method
                        yield new Result.Ok<>(coder.createString(entries.getFirst().key().asString()));
                    // Calls a method
                    final Transcoder.ListBuilder<D> result = coder.createList(entries.size());
                    // Loop: repeats a block
                    for (final RegistryKey<T> key : entries)
                        // Calls a method
                        result.add(coder.createString(key.key().asString()));
                    // Calls a method
                    yield new Result.Ok<>(result.build());
                // End of a block/expression
                }
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record HolderSetImpl<T extends Holder<T>>(
            // Code statement
            Codec<RegistryTag<T>> tagCodec,
            // Code statement
            Codec<T> directCodec
    // Start of a method/block
    ) implements Codec<HolderSet<T>> {
        // Start of a method/block
        HolderSetImpl {
            // Calls a method
            Objects.requireNonNull(tagCodec, "tagCodec");
            // Calls a method
            Objects.requireNonNull(directCodec, "directCodec");
        // End of a block/expression
        }
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<HolderSet<T>> decode(Transcoder<D> coder, D value) {
            // First try to decode as a tag
            // Calls a method
            final Result<RegistryTag<T>> tagResult = tagCodec.decode(coder, value);
            // Branch: checks a condition
            if (tagResult instanceof Result.Ok(RegistryTag<T> tag))
                // Returns a value to the caller
                return new Result.Ok<>(tag);

            // Otherwise try to decode as a direct holder set
            // Calls a method
            final Result<List<D>> entriesResult = coder.getList(value);
            // Branch: checks a condition
            if (!(entriesResult instanceof Result.Ok(List<D> entries)))
                // Returns a value to the caller
                return entriesResult.mapError(e -> "Invalid holder set value: " + e).cast();

            // Calls a method
            final List<T> directEntries = new ArrayList<>(entries.size());
            // Loop: repeats a block
            for (D entry : entries) {
                // Calls a method
                final Result<T> directResult = directCodec.decode(coder, entry);
                // Branch: checks a condition
                if (directResult instanceof Result.Ok(T direct)) {
                    // Calls a method
                    directEntries.add(direct);
                // Alternative branch of the condition
                } else {
                    // Returns a value to the caller
                    return directResult.mapError(e -> "Invalid holder set entry: " + e).cast();
                // End of a block/expression
                }
            // End of a block/expression
            }
            // This raw type is kinda gross. Its safe because direct is checked only
            // to be instantiated with Holder.Direct types, but HolderSet itself supports non-direct types.
            //noinspection rawtypes,unchecked
            // Returns a value to the caller
            return new Result.Ok<>((HolderSet<T>) new HolderSet.Direct(directEntries));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable HolderSet<T> value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Returns a value to the caller
            return switch (value) {
                // Multiple branching (switch/case)
                case RegistryTag<T> tag -> tagCodec.encode(coder, tag);
                // This raw type is kinda gross. Its safe because direct is checked only
                // to be instantiated with Holder.Direct types, but HolderSet itself supports non-direct types.
                //noinspection rawtypes
                // Multiple branching (switch/case)
                case HolderSet.Direct d -> {
                    // Calls a method
                    final Transcoder.ListBuilder<D> result = coder.createList(d.values().size());
                    // Loop: repeats a block
                    for (final Object rawValue : d.values()) {
                        // Calls a method
                        final var directResult = directCodec.encode(coder, (T) rawValue);
                        // Branch: checks a condition
                        if (directResult instanceof Result.Ok(D direct)) {
                            // Calls a method
                            result.add(direct);
                        // Alternative branch of the condition
                        } else {
                            // Calls a method
                            yield directResult.mapError(e -> "Invalid holder set entry: " + e).cast();
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                    // Calls a method
                    yield new Result.Ok<>(result.build());
                // End of a block/expression
                }
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
