// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTagType;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTagTypes;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.lang.invoke.VarHandle;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Déclaration de type (classe/interface/enum/record)
final class TagHandlerImpl implements TagHandler {
    // Appelle une méthode
    static final Serializers.Entry<Node, CompoundBinaryTag> NODE_SERIALIZER = new Serializers.Entry<>(BinaryTagTypes.COMPOUND, entries -> fromCompound(entries).root, Node::compound, true);

    // Instruction de code
    private final Node root;
    // Instruction de code
    private volatile Node copy;

    // Début d'une méthode/d'un bloc
    TagHandlerImpl(Node root) {
        // Accès à l'objet courant/parent
        this.root = root;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    TagHandlerImpl() {
        // Accès à l'objet courant/parent
        this.root = new Node();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TagHandlerImpl fromCompound(CompoundBinaryTag compound) {
        // Appelle une méthode
        TagHandlerImpl handler = new TagHandlerImpl();
        // Appelle une méthode
        TagNbtSeparator.separate(compound, entry -> handler.setTag(entry.tag(), entry.value()));
        // Affecte une valeur
        handler.root.compound = compound;
        // Renvoie une valeur à l'appelant
        return handler;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> @UnknownNullability T getTag(Tag<T> tag) {
        // Appelle une méthode
        VarHandle.fullFence();
        // Renvoie une valeur à l'appelant
        return root.getTag(tag);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> void setTag(Tag<T> tag, @Nullable T value) {
        // Appelle une méthode
        TagImpl<T> tagImpl = (TagImpl<T>) tag;
        // Handle view tags
        // Embranchement : vérifie une condition
        if (tag.isView()) {
            // Début d'une méthode/d'un bloc
            synchronized (this) {
                // Appelle une méthode
                Node syncNode = traversePathWrite(root, tag, value != null);
                // Embranchement : vérifie une condition
                if (syncNode != null) {
                    // Appelle une méthode
                    syncNode.updateContent(value != null ? (CompoundBinaryTag) tagImpl.entry().write(value) : CompoundBinaryTag.empty());
                    // Appelle une méthode
                    syncNode.invalidate();
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Normal tag
        // Appelle une méthode
        final int tagIndex = tagImpl.index();
        // Appelle une méthode
        VarHandle.fullFence();
        // Appelle une méthode
        Node node = traversePathWrite(root, tag, value != null);
        // Embranchement : vérifie une condition
        if (node == null)
            // Renvoie une valeur à l'appelant
            return; // Tried to remove an absent tag. Do nothing
        // Affecte une valeur
        StaticIntMap<Entry<?>> entries = node.entries;
        // Embranchement : vérifie une condition
        if (value != null) {
            // Appelle une méthode
            Entry previous = entries.get(tagIndex);
            // Embranchement : vérifie une condition
            if (previous != null && previous.tag.shareValue(tag)) {
                // Appelle une méthode
                previous.updateValue(tag.copyValue(value));
            // Branche alternative de la condition
            } else {
                // Début d'une méthode/d'un bloc
                synchronized (this) {
                    // Appelle une méthode
                    node = traversePathWrite(root, tag, true);
                    // Appelle une méthode
                    node.entries.put(tagIndex, valueToEntry(node, tag, value));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Début d'une méthode/d'un bloc
            synchronized (this) {
                // Appelle une méthode
                node = traversePathWrite(root, tag, false);
                // Embranchement : vérifie une condition
                if (node == null) return;
                // Appelle une méthode
                node.entries.remove(tagIndex);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        node.invalidate();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> @Nullable T getAndSetTag(Tag<T> tag, @Nullable T value) {
        // Renvoie une valeur à l'appelant
        return updateTag0(tag, _ -> value, true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> void updateTag(Tag<T> tag, UnaryOperator<@UnknownNullability T> value) {
        // Appelle une méthode
        updateTag0(tag, value, false);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> @UnknownNullability T updateAndGetTag(Tag<T> tag, UnaryOperator<@UnknownNullability T> value) {
        // Renvoie une valeur à l'appelant
        return updateTag0(tag, value, false);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> @UnknownNullability T getAndUpdateTag(Tag<T> tag, UnaryOperator<@UnknownNullability T> value) {
        // Renvoie une valeur à l'appelant
        return updateTag0(tag, value, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private synchronized <T> @UnknownNullability T updateTag0(Tag<T> tag, UnaryOperator<T> value, boolean returnPrevious) {
        // Appelle une méthode
        TagImpl<T> tagImpl = (TagImpl<T>) tag;
        // Appelle une méthode
        final Node node = traversePathWrite(root, tag, true);
        // Embranchement : vérifie une condition
        if (tag.isView()) {
            // Appelle une méthode
            final T previousValue = tag.read(node.compound());
            // Appelle une méthode
            final T newValue = value.apply(previousValue);
            // Appelle une méthode
            node.updateContent((CompoundBinaryTag) tagImpl.entry().write(newValue));
            // Appelle une méthode
            node.invalidate();
            // Renvoie une valeur à l'appelant
            return returnPrevious ? previousValue : newValue;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final int tagIndex = tagImpl.index();
        // Affecte une valeur
        StaticIntMap<Entry<?>> entries = node.entries;

        // Appelle une méthode
        final Entry previousEntry = entries.get(tagIndex);
        // Instruction de code
        final T previousValue;
        // Embranchement : vérifie une condition
        if (previousEntry != null) {
            // Affecte une valeur
            final Object previousTmp = previousEntry.value;
            // Embranchement : vérifie une condition
            if (previousTmp instanceof Node n) {
                // Appelle une méthode
                final CompoundBinaryTag compound = CompoundBinaryTag.from(Map.of(tag.key(), n.compound()));
                // Appelle une méthode
                previousValue = tag.read(compound);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                previousValue = (T) previousTmp;
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            previousValue = tag.createDefault();
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final T newValue = value.apply(previousValue);
        // Embranchement : vérifie une condition
        if (newValue != null) entries.put(tagIndex, valueToEntry(node, tag, newValue));
        // Branche alternative de la condition
        else entries.remove(tagIndex);

        // Appelle une méthode
        node.invalidate();
        // Renvoie une valeur à l'appelant
        return returnPrevious ? previousValue : newValue;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public TagReadable readableCopy() {
        // Affecte une valeur
        Node copy = this.copy;
        // Embranchement : vérifie une condition
        if (copy == null) {
            // Début d'une méthode/d'un bloc
            synchronized (this) {
                // Accès à l'objet courant/parent
                this.copy = copy = root.copy(null);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return copy;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public synchronized TagHandler copy() {
        // Renvoie une valeur à l'appelant
        return new TagHandlerImpl(root.copy(null));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public synchronized void updateContent(CompoundBinaryTag compound) {
        // Accès à l'objet courant/parent
        this.root.updateContent(compound);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompoundBinaryTag asCompound() {
        // Appelle une méthode
        VarHandle.fullFence();
        // Renvoie une valeur à l'appelant
        return root.compound();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Node traversePathRead(Node node, Tag<?> tag) {
        // Appelle une méthode
        final TagImpl.PathEntry[] paths = ((TagImpl<?>) tag).path();
        // Embranchement : vérifie une condition
        if (paths == null) return node;
        // Boucle : répète un bloc
        for (var path : paths) {
            // Appelle une méthode
            final Entry<?> entry = node.entries.get(path.index());
            // Embranchement : vérifie une condition
            if (entry == null || (node = entry.toNode()) == null)
                // Renvoie une valeur à l'appelant
                return null;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return node;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("_, _, true -> !null")
    // Instruction de code
    private Node traversePathWrite(Node root, Tag<?> tag,
                                   // Début d'une méthode/d'un bloc
                                   boolean present) {
        // Appelle une méthode
        TagImpl<?> tagImpl = (TagImpl<?>) tag;
        // Appelle une méthode
        final TagImpl.PathEntry[] paths = tagImpl.path();
        // Embranchement : vérifie une condition
        if (paths == null) return root;
        // Affecte une valeur
        Node local = root;
        // Boucle : répète un bloc
        for (TagImpl.PathEntry path : paths) {
            // Appelle une méthode
            final int pathIndex = path.index();
            // Appelle une méthode
            final Entry<?> entry = local.entries.get(pathIndex);
            // Embranchement : vérifie une condition
            if (entry != null && entry.tag.entry().isPath()) {
                // Existing path, continue navigating
                // Appelle une méthode
                final Node tmp = (Node) entry.value;
                // Instruction de code
                assert tmp.parent == local : "Path parent is invalid: " + tmp.parent + " != " + local;
                // Affecte une valeur
                local = tmp;
            // Branche alternative de la condition
            } else {
                // Embranchement : vérifie une condition
                if (!present) return null;
                // Début d'une méthode/d'un bloc
                synchronized (this) {
                    // Appelle une méthode
                    var synEntry = local.entries.get(pathIndex);
                    // Embranchement : vérifie une condition
                    if (synEntry != null && synEntry.tag.entry().isPath()) {
                        // Existing path, continue navigating
                        // Appelle une méthode
                        final Node tmp = (Node) synEntry.value;
                        // Instruction de code
                        assert tmp.parent == local : "Path parent is invalid: " + tmp.parent + " != " + local;
                        // Affecte une valeur
                        local = tmp;
                        // Passe à l'itération suivante de la boucle
                        continue;
                    // Fin d'un bloc/d'une expression
                    }

                    // Empty path, create a new handler.
                    // Slow path is taken if the entry comes from a Structure tag, requiring conversion from NBT
                    // Affecte une valeur
                    Node tmp = local;
                    // Appelle une méthode
                    local = new Node(tmp);
                    // Embranchement : vérifie une condition
                    if (synEntry != null && synEntry.updatedNbt() instanceof CompoundBinaryTag compound) {
                        // Appelle une méthode
                        local.updateContent(compound);
                    // Fin d'un bloc/d'une expression
                    }
                    // Appelle une méthode
                    tmp.entries.put(pathIndex, Entry.makePathEntry(path.name(), local));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return local;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private <T> Entry<?> valueToEntry(Node parent, Tag<T> tag, T value) {
        // Embranchement : vérifie une condition
        if (value instanceof BinaryTag nbt) {
            // Embranchement : vérifie une condition
            if (nbt instanceof CompoundBinaryTag compound) {
                // Appelle une méthode
                final TagHandlerImpl handler = fromCompound(compound);
                // Renvoie une valeur à l'appelant
                return Entry.makePathEntry(tag, new Node(parent, handler.root.entries));
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                final var nbtEntry = TagNbtSeparator.separateSingle(tag.key(), nbt);
                // Renvoie une valeur à l'appelant
                return new Entry<>(nbtEntry.tag(), nbtEntry.value());
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return new Entry<>((TagImpl<? super T>) tag, tag.copyValue(value));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class Node implements TagReadable {
        // Instruction de code
        final @Nullable Node parent;
        // Instruction de code
        final StaticIntMap<Entry<?>> entries;
        // Annotation pour l'élément suivant
        @Nullable CompoundBinaryTag compound;

        // Début d'une méthode/d'un bloc
        public Node(@Nullable Node parent, StaticIntMap<Entry<?>> entries) {
            // Accès à l'objet courant/parent
            this.parent = parent;
            // Accès à l'objet courant/parent
            this.entries = entries;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        Node(@Nullable Node parent) {
            // Appelle une méthode
            this(parent, new StaticIntMap.Array<>());
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        Node() {
            // Appelle une méthode
            this(null);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <T> @UnknownNullability T getTag(Tag<T> tag) {
            // Appelle une méthode
            final Node node = traversePathRead(this, tag);
            // Embranchement : vérifie une condition
            if (node == null)
                // Renvoie une valeur à l'appelant
                return tag.createDefault(); // Must be a path-able entry, but not present
            // Embranchement : vérifie une condition
            if (tag.isView()) return tag.read(node.compound());

            // Appelle une méthode
            final TagImpl<T> tagImpl = (TagImpl<T>) tag;
            // Affecte une valeur
            final StaticIntMap<Entry<?>> entries = node.entries;
            // Appelle une méthode
            final Entry<?> entry = entries.get(tagImpl.index());
            // Embranchement : vérifie une condition
            if (entry == null)
                // Renvoie une valeur à l'appelant
                return tag.createDefault(); // Not present
            // Embranchement : vérifie une condition
            if (entry.tag.shareValue(tag)) {
                // The tag used to write the entry is compatible with the one used to get
                // return the value directly
                //noinspection unchecked
                // Renvoie une valeur à l'appelant
                return (T) entry.value;
            // Fin d'un bloc/d'une expression
            }
            // Value must be parsed from nbt if the tag is different
            // Appelle une méthode
            final BinaryTag nbt = entry.updatedNbt();
            // Appelle une méthode
            final Serializers.Entry<T, BinaryTag> serializerEntry = tagImpl.entry();
            // Appelle une méthode
            final BinaryTagType<BinaryTag> type = serializerEntry.nbtType();
            // Renvoie une valeur à l'appelant
            return type == null || type.equals(nbt.type()) ? serializerEntry.read(nbt) : tag.createDefault();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void updateContent(CompoundBinaryTag compound) {
            // Appelle une méthode
            final TagHandlerImpl converted = fromCompound(compound);
            // Accès à l'objet courant/parent
            this.entries.updateContent(converted.root.entries);
            // Accès à l'objet courant/parent
            this.compound = compound;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        CompoundBinaryTag compound() {
            // Instruction de code
            CompoundBinaryTag compound;
            // Embranchement : vérifie une condition
            if (!ServerFlag.TAG_HANDLER_CACHE_ENABLED || (compound = this.compound) == null) {
                // Appelle une méthode
                CompoundBinaryTag.Builder tmp = CompoundBinaryTag.builder();
                // Accès à l'objet courant/parent
                this.entries.forValues(entry -> {
                    // Affecte une valeur
                    final TagImpl<?> tag = entry.tag;
                    // Appelle une méthode
                    final BinaryTag nbt = entry.updatedNbt();
                    // Embranchement : vérifie une condition
                    if (nbt != null && (!tag.entry().isPath() || ServerFlag.SERIALIZE_EMPTY_COMPOUND || !((CompoundBinaryTag) nbt).isEmpty())) {
                        // Appelle une méthode
                        tmp.put(tag.getKey(), nbt);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                });
                // Accès à l'objet courant/parent
                this.compound = compound = tmp.build();
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return compound;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract("null -> !null")
        // Annotation pour l'élément suivant
        @Nullable Node copy(@Nullable Node parent) {
            // Appelle une méthode
            CompoundBinaryTag.Builder tmp = CompoundBinaryTag.builder();
            // Appelle une méthode
            Node result = new Node(parent, new StaticIntMap.Array<>());
            // Affecte une valeur
            StaticIntMap<Entry<?>> entries = result.entries;
            // Accès à l'objet courant/parent
            this.entries.forValues(entry -> {
                // Affecte une valeur
                TagImpl<?> tag = entry.tag;
                // Affecte une valeur
                Object value = entry.value;
                // Instruction de code
                BinaryTag nbt;
                // Embranchement : vérifie une condition
                if (value instanceof Node node) {
                    // Appelle une méthode
                    Node copy = node.copy(result);
                    // Embranchement : vérifie une condition
                    if (copy == null)
                        // Renvoie une valeur à l'appelant
                        return; // Empty node
                    // Affecte une valeur
                    value = copy;
                    // Affecte une valeur
                    nbt = copy.compound;
                    // Instruction de code
                    assert nbt != null : "Node copy should also compute the compound";
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    nbt = entry.updatedNbt();
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (nbt != null)
                    // Appelle une méthode
                    tmp.put(tag.getKey(), nbt);
                // Appelle une méthode
                entries.put(tag.index(), valueToEntry(result, (Tag<Object>) tag, value));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            var compound = tmp.build();
            // Embranchement : vérifie une condition
            if ((!ServerFlag.SERIALIZE_EMPTY_COMPOUND) && compound.isEmpty() && parent != null)
                // Renvoie une valeur à l'appelant
                return null; // Empty child node
            // Affecte une valeur
            result.compound = compound;
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void invalidate() {
            // Affecte une valeur
            Node tmp = this;
            // Boucle : répète un bloc
            do tmp.compound = null;
            // Boucle : répète un bloc
            while ((tmp = tmp.parent) != null);
            // Affecte une valeur
            TagHandlerImpl.this.copy = null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static final class Entry<T> {
        // Instruction de code
        private final TagImpl<T> tag;
        // Instruction de code
        T value;
        // Annotation pour l'élément suivant
        @Nullable BinaryTag nbt;

        // Début d'une méthode/d'un bloc
        Entry(TagImpl<T> tag, T value) {
            // Accès à l'objet courant/parent
            this.tag = tag;
            // Accès à l'objet courant/parent
            this.value = value;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static Entry<?> makePathEntry(String path, Node node) {
            // Renvoie une valeur à l'appelant
            return new Entry<>(TagImpl.tag(path, NODE_SERIALIZER), node);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static Entry<?> makePathEntry(Tag<?> tag, Node node) {
            // Renvoie une valeur à l'appelant
            return makePathEntry(tag.getKey(), node);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Nullable BinaryTag updatedNbt() {
            // Embranchement : vérifie une condition
            if (tag.entry().isPath()) return ((Node) value).compound();
            // Affecte une valeur
            BinaryTag nbt = this.nbt;
            // Embranchement : vérifie une condition
            if (nbt == null) this.nbt = nbt = tag.entry().write(value);
            // Renvoie une valeur à l'appelant
            return nbt;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void updateValue(T value) {
            // Appelle une méthode
            assert !tag.entry().isPath();
            // Accès à l'objet courant/parent
            this.value = value;
            // Accès à l'objet courant/parent
            this.nbt = null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Nullable Node toNode() {
            // Embranchement : vérifie une condition
            if (tag.entry().isPath()) return (Node) value;
            // Embranchement : vérifie une condition
            if (updatedNbt() instanceof CompoundBinaryTag compound) {
                // Slow path forcing a conversion of the structure to NBTCompound
                // TODO should the handler be cached inside the entry?
                // Renvoie une valeur à l'appelant
                return fromCompound(compound).root;
            // Fin d'un bloc/d'une expression
            }
            // Entry is not path-able
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
