// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTagType;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTagTypes;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.lang.invoke.VarHandle;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.UnaryOperator;

// Type declaration (class/interface/enum/record)
final class TagHandlerImpl implements TagHandler {
    // Calls a method
    static final Serializers.Entry<Node, CompoundBinaryTag> NODE_SERIALIZER = new Serializers.Entry<>(BinaryTagTypes.COMPOUND, entries -> fromCompound(entries).root, Node::compound, true);

    // Code statement
    private final Node root;
    // Code statement
    private volatile Node copy;

    // Start of a method/block
    TagHandlerImpl(Node root) {
        // Access to the current/parent object
        this.root = root;
    // End of a block/expression
    }

    // Start of a method/block
    TagHandlerImpl() {
        // Access to the current/parent object
        this.root = new Node();
    // End of a block/expression
    }

    // Start of a method/block
    static TagHandlerImpl fromCompound(CompoundBinaryTag compound) {
        // Calls a method
        TagHandlerImpl handler = new TagHandlerImpl();
        // Calls a method
        TagNbtSeparator.separate(compound, entry -> handler.setTag(entry.tag(), entry.value()));
        // Assigns a value
        handler.root.compound = compound;
        // Returns a value to the caller
        return handler;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> @UnknownNullability T getTag(Tag<T> tag) {
        // Calls a method
        VarHandle.fullFence();
        // Returns a value to the caller
        return root.getTag(tag);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> void setTag(Tag<T> tag, @Nullable T value) {
        // Calls a method
        TagImpl<T> tagImpl = (TagImpl<T>) tag;
        // Handle view tags
        // Branch: checks a condition
        if (tag.isView()) {
            // Start of a method/block
            synchronized (this) {
                // Calls a method
                Node syncNode = traversePathWrite(root, tag, value != null);
                // Branch: checks a condition
                if (syncNode != null) {
                    // Calls a method
                    syncNode.updateContent(value != null ? (CompoundBinaryTag) tagImpl.entry().write(value) : CompoundBinaryTag.empty());
                    // Calls a method
                    syncNode.invalidate();
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Normal tag
        // Calls a method
        final int tagIndex = tagImpl.index();
        // Calls a method
        VarHandle.fullFence();
        // Calls a method
        Node node = traversePathWrite(root, tag, value != null);
        // Branch: checks a condition
        if (node == null)
            // Returns a value to the caller
            return; // Tried to remove an absent tag. Do nothing
        // Assigns a value
        StaticIntMap<Entry<?>> entries = node.entries;
        // Branch: checks a condition
        if (value != null) {
            // Calls a method
            Entry previous = entries.get(tagIndex);
            // Branch: checks a condition
            if (previous != null && previous.tag.shareValue(tag)) {
                // Calls a method
                previous.updateValue(tag.copyValue(value));
            // Alternative branch of the condition
            } else {
                // Start of a method/block
                synchronized (this) {
                    // Calls a method
                    node = traversePathWrite(root, tag, true);
                    // Calls a method
                    node.entries.put(tagIndex, valueToEntry(node, tag, value));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Start of a method/block
            synchronized (this) {
                // Calls a method
                node = traversePathWrite(root, tag, false);
                // Branch: checks a condition
                if (node == null) return;
                // Calls a method
                node.entries.remove(tagIndex);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        node.invalidate();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> @Nullable T getAndSetTag(Tag<T> tag, @Nullable T value) {
        // Returns a value to the caller
        return updateTag0(tag, _ -> value, true);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> void updateTag(Tag<T> tag, UnaryOperator<@UnknownNullability T> value) {
        // Calls a method
        updateTag0(tag, value, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> @UnknownNullability T updateAndGetTag(Tag<T> tag, UnaryOperator<@UnknownNullability T> value) {
        // Returns a value to the caller
        return updateTag0(tag, value, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> @UnknownNullability T getAndUpdateTag(Tag<T> tag, UnaryOperator<@UnknownNullability T> value) {
        // Returns a value to the caller
        return updateTag0(tag, value, true);
    // End of a block/expression
    }

    // Start of a method/block
    private synchronized <T> @UnknownNullability T updateTag0(Tag<T> tag, UnaryOperator<T> value, boolean returnPrevious) {
        // Calls a method
        TagImpl<T> tagImpl = (TagImpl<T>) tag;
        // Calls a method
        final Node node = traversePathWrite(root, tag, true);
        // Branch: checks a condition
        if (tag.isView()) {
            // Calls a method
            final T previousValue = tag.read(node.compound());
            // Calls a method
            final T newValue = value.apply(previousValue);
            // Calls a method
            node.updateContent((CompoundBinaryTag) tagImpl.entry().write(newValue));
            // Calls a method
            node.invalidate();
            // Returns a value to the caller
            return returnPrevious ? previousValue : newValue;
        // End of a block/expression
        }

        // Calls a method
        final int tagIndex = tagImpl.index();
        // Assigns a value
        StaticIntMap<Entry<?>> entries = node.entries;

        // Calls a method
        final Entry previousEntry = entries.get(tagIndex);
        // Code statement
        final T previousValue;
        // Branch: checks a condition
        if (previousEntry != null) {
            // Assigns a value
            final Object previousTmp = previousEntry.value;
            // Branch: checks a condition
            if (previousTmp instanceof Node n) {
                // Calls a method
                final CompoundBinaryTag compound = CompoundBinaryTag.from(Map.of(tag.key(), n.compound()));
                // Calls a method
                previousValue = tag.read(compound);
            // Alternative branch of the condition
            } else {
                // Calls a method
                previousValue = (T) previousTmp;
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Calls a method
            previousValue = tag.createDefault();
        // End of a block/expression
        }
        // Calls a method
        final T newValue = value.apply(previousValue);
        // Branch: checks a condition
        if (newValue != null) entries.put(tagIndex, valueToEntry(node, tag, newValue));
        // Alternative branch of the condition
        else entries.remove(tagIndex);

        // Calls a method
        node.invalidate();
        // Returns a value to the caller
        return returnPrevious ? previousValue : newValue;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public TagReadable readableCopy() {
        // Assigns a value
        Node copy = this.copy;
        // Branch: checks a condition
        if (copy == null) {
            // Start of a method/block
            synchronized (this) {
                // Access to the current/parent object
                this.copy = copy = root.copy(null);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return copy;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public synchronized TagHandler copy() {
        // Returns a value to the caller
        return new TagHandlerImpl(root.copy(null));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public synchronized void updateContent(CompoundBinaryTag compound) {
        // Access to the current/parent object
        this.root.updateContent(compound);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompoundBinaryTag asCompound() {
        // Calls a method
        VarHandle.fullFence();
        // Returns a value to the caller
        return root.compound();
    // End of a block/expression
    }

    // Start of a method/block
    private static Node traversePathRead(Node node, Tag<?> tag) {
        // Calls a method
        final TagImpl.PathEntry[] paths = ((TagImpl<?>) tag).path();
        // Branch: checks a condition
        if (paths == null) return node;
        // Loop: repeats a block
        for (var path : paths) {
            // Calls a method
            final Entry<?> entry = node.entries.get(path.index());
            // Branch: checks a condition
            if (entry == null || (node = entry.toNode()) == null)
                // Returns a value to the caller
                return null;
        // End of a block/expression
        }
        // Returns a value to the caller
        return node;
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("_, _, true -> !null")
    // Code statement
    private Node traversePathWrite(Node root, Tag<?> tag,
                                   // Start of a method/block
                                   boolean present) {
        // Calls a method
        TagImpl<?> tagImpl = (TagImpl<?>) tag;
        // Calls a method
        final TagImpl.PathEntry[] paths = tagImpl.path();
        // Branch: checks a condition
        if (paths == null) return root;
        // Assigns a value
        Node local = root;
        // Loop: repeats a block
        for (TagImpl.PathEntry path : paths) {
            // Calls a method
            final int pathIndex = path.index();
            // Calls a method
            final Entry<?> entry = local.entries.get(pathIndex);
            // Branch: checks a condition
            if (entry != null && entry.tag.entry().isPath()) {
                // Existing path, continue navigating
                // Calls a method
                final Node tmp = (Node) entry.value;
                // Code statement
                assert tmp.parent == local : "Path parent is invalid: " + tmp.parent + " != " + local;
                // Assigns a value
                local = tmp;
            // Alternative branch of the condition
            } else {
                // Branch: checks a condition
                if (!present) return null;
                // Start of a method/block
                synchronized (this) {
                    // Calls a method
                    var synEntry = local.entries.get(pathIndex);
                    // Branch: checks a condition
                    if (synEntry != null && synEntry.tag.entry().isPath()) {
                        // Existing path, continue navigating
                        // Calls a method
                        final Node tmp = (Node) synEntry.value;
                        // Code statement
                        assert tmp.parent == local : "Path parent is invalid: " + tmp.parent + " != " + local;
                        // Assigns a value
                        local = tmp;
                        // Continues to the next loop iteration
                        continue;
                    // End of a block/expression
                    }

                    // Empty path, create a new handler.
                    // Slow path is taken if the entry comes from a Structure tag, requiring conversion from NBT
                    // Assigns a value
                    Node tmp = local;
                    // Calls a method
                    local = new Node(tmp);
                    // Branch: checks a condition
                    if (synEntry != null && synEntry.updatedNbt() instanceof CompoundBinaryTag compound) {
                        // Calls a method
                        local.updateContent(compound);
                    // End of a block/expression
                    }
                    // Calls a method
                    tmp.entries.put(pathIndex, Entry.makePathEntry(path.name(), local));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return local;
    // End of a block/expression
    }

    // Start of a method/block
    private <T> Entry<?> valueToEntry(Node parent, Tag<T> tag, T value) {
        // Branch: checks a condition
        if (value instanceof BinaryTag nbt) {
            // Branch: checks a condition
            if (nbt instanceof CompoundBinaryTag compound) {
                // Calls a method
                final TagHandlerImpl handler = fromCompound(compound);
                // Returns a value to the caller
                return Entry.makePathEntry(tag, new Node(parent, handler.root.entries));
            // Alternative branch of the condition
            } else {
                // Calls a method
                final var nbtEntry = TagNbtSeparator.separateSingle(tag.key(), nbt);
                // Returns a value to the caller
                return new Entry<>(nbtEntry.tag(), nbtEntry.value());
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return new Entry<>((TagImpl<? super T>) tag, tag.copyValue(value));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class Node implements TagReadable {
        // Code statement
        final @Nullable Node parent;
        // Code statement
        final StaticIntMap<Entry<?>> entries;
        // Annotation for the following element
        @Nullable CompoundBinaryTag compound;

        // Start of a method/block
        public Node(@Nullable Node parent, StaticIntMap<Entry<?>> entries) {
            // Access to the current/parent object
            this.parent = parent;
            // Access to the current/parent object
            this.entries = entries;
        // End of a block/expression
        }

        // Start of a method/block
        Node(@Nullable Node parent) {
            // Calls a method
            this(parent, new StaticIntMap.Array<>());
        // End of a block/expression
        }

        // Start of a method/block
        Node() {
            // Calls a method
            this(null);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <T> @UnknownNullability T getTag(Tag<T> tag) {
            // Calls a method
            final Node node = traversePathRead(this, tag);
            // Branch: checks a condition
            if (node == null)
                // Returns a value to the caller
                return tag.createDefault(); // Must be a path-able entry, but not present
            // Branch: checks a condition
            if (tag.isView()) return tag.read(node.compound());

            // Calls a method
            final TagImpl<T> tagImpl = (TagImpl<T>) tag;
            // Assigns a value
            final StaticIntMap<Entry<?>> entries = node.entries;
            // Calls a method
            final Entry<?> entry = entries.get(tagImpl.index());
            // Branch: checks a condition
            if (entry == null)
                // Returns a value to the caller
                return tag.createDefault(); // Not present
            // Branch: checks a condition
            if (entry.tag.shareValue(tag)) {
                // The tag used to write the entry is compatible with the one used to get
                // return the value directly
                //noinspection unchecked
                // Returns a value to the caller
                return (T) entry.value;
            // End of a block/expression
            }
            // Value must be parsed from nbt if the tag is different
            // Calls a method
            final BinaryTag nbt = entry.updatedNbt();
            // Calls a method
            final Serializers.Entry<T, BinaryTag> serializerEntry = tagImpl.entry();
            // Calls a method
            final BinaryTagType<BinaryTag> type = serializerEntry.nbtType();
            // Returns a value to the caller
            return type == null || type.equals(nbt.type()) ? serializerEntry.read(nbt) : tag.createDefault();
        // End of a block/expression
        }

        // Start of a method/block
        void updateContent(CompoundBinaryTag compound) {
            // Calls a method
            final TagHandlerImpl converted = fromCompound(compound);
            // Access to the current/parent object
            this.entries.updateContent(converted.root.entries);
            // Access to the current/parent object
            this.compound = compound;
        // End of a block/expression
        }

        // Start of a method/block
        CompoundBinaryTag compound() {
            // Code statement
            CompoundBinaryTag compound;
            // Branch: checks a condition
            if (!ServerFlag.TAG_HANDLER_CACHE_ENABLED || (compound = this.compound) == null) {
                // Calls a method
                CompoundBinaryTag.Builder tmp = CompoundBinaryTag.builder();
                // Access to the current/parent object
                this.entries.forValues(entry -> {
                    // Assigns a value
                    final TagImpl<?> tag = entry.tag;
                    // Calls a method
                    final BinaryTag nbt = entry.updatedNbt();
                    // Branch: checks a condition
                    if (nbt != null && (!tag.entry().isPath() || ServerFlag.SERIALIZE_EMPTY_COMPOUND || !((CompoundBinaryTag) nbt).isEmpty())) {
                        // Calls a method
                        tmp.put(tag.getKey(), nbt);
                    // End of a block/expression
                    }
                // End of a block/expression
                });
                // Access to the current/parent object
                this.compound = compound = tmp.build();
            // End of a block/expression
            }
            // Returns a value to the caller
            return compound;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract("null -> !null")
        // Annotation for the following element
        @Nullable Node copy(@Nullable Node parent) {
            // Calls a method
            CompoundBinaryTag.Builder tmp = CompoundBinaryTag.builder();
            // Calls a method
            Node result = new Node(parent, new StaticIntMap.Array<>());
            // Assigns a value
            StaticIntMap<Entry<?>> entries = result.entries;
            // Access to the current/parent object
            this.entries.forValues(entry -> {
                // Assigns a value
                TagImpl<?> tag = entry.tag;
                // Assigns a value
                Object value = entry.value;
                // Code statement
                BinaryTag nbt;
                // Branch: checks a condition
                if (value instanceof Node node) {
                    // Calls a method
                    Node copy = node.copy(result);
                    // Branch: checks a condition
                    if (copy == null)
                        // Returns a value to the caller
                        return; // Empty node
                    // Assigns a value
                    value = copy;
                    // Assigns a value
                    nbt = copy.compound;
                    // Code statement
                    assert nbt != null : "Node copy should also compute the compound";
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    nbt = entry.updatedNbt();
                // End of a block/expression
                }

                // Branch: checks a condition
                if (nbt != null)
                    // Calls a method
                    tmp.put(tag.getKey(), nbt);
                // Calls a method
                entries.put(tag.index(), valueToEntry(result, (Tag<Object>) tag, value));
            // End of a block/expression
            });
            // Calls a method
            var compound = tmp.build();
            // Branch: checks a condition
            if ((!ServerFlag.SERIALIZE_EMPTY_COMPOUND) && compound.isEmpty() && parent != null)
                // Returns a value to the caller
                return null; // Empty child node
            // Assigns a value
            result.compound = compound;
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }

        // Start of a method/block
        void invalidate() {
            // Assigns a value
            Node tmp = this;
            // Loop: repeats a block
            do tmp.compound = null;
            // Loop: repeats a block
            while ((tmp = tmp.parent) != null);
            // Assigns a value
            TagHandlerImpl.this.copy = null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static final class Entry<T> {
        // Code statement
        private final TagImpl<T> tag;
        // Code statement
        T value;
        // Annotation for the following element
        @Nullable BinaryTag nbt;

        // Start of a method/block
        Entry(TagImpl<T> tag, T value) {
            // Access to the current/parent object
            this.tag = tag;
            // Access to the current/parent object
            this.value = value;
        // End of a block/expression
        }

        // Start of a method/block
        static Entry<?> makePathEntry(String path, Node node) {
            // Returns a value to the caller
            return new Entry<>(TagImpl.tag(path, NODE_SERIALIZER), node);
        // End of a block/expression
        }

        // Start of a method/block
        static Entry<?> makePathEntry(Tag<?> tag, Node node) {
            // Returns a value to the caller
            return makePathEntry(tag.getKey(), node);
        // End of a block/expression
        }

        // Annotation for the following element
        @Nullable BinaryTag updatedNbt() {
            // Branch: checks a condition
            if (tag.entry().isPath()) return ((Node) value).compound();
            // Assigns a value
            BinaryTag nbt = this.nbt;
            // Branch: checks a condition
            if (nbt == null) this.nbt = nbt = tag.entry().write(value);
            // Returns a value to the caller
            return nbt;
        // End of a block/expression
        }

        // Start of a method/block
        void updateValue(T value) {
            // Calls a method
            assert !tag.entry().isPath();
            // Access to the current/parent object
            this.value = value;
            // Access to the current/parent object
            this.nbt = null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Nullable Node toNode() {
            // Branch: checks a condition
            if (tag.entry().isPath()) return (Node) value;
            // Branch: checks a condition
            if (updatedNbt() instanceof CompoundBinaryTag compound) {
                // Slow path forcing a conversion of the structure to NBTCompound
                // TODO should the handler be cached inside the entry?
                // Returns a value to the caller
                return fromCompound(compound).root;
            // End of a block/expression
            }
            // Entry is not path-able
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
