// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.lang.classfile.ClassFile;
// Import of a required class
import java.lang.classfile.CodeBuilder;
// Import of a required class
import java.lang.constant.ClassDesc;
// Import of a required class
import java.lang.constant.ConstantDescs;
// Import of a required class
import java.lang.constant.MethodTypeDesc;
// Import of a required class
import java.lang.invoke.MethodHandle;
// Import of a required class
import java.lang.invoke.MethodHandles;
// Import of a required class
import java.lang.invoke.MethodType;
// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.Function;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
final class NetworkBufferTemplateImpl {
    // Assigns a value
    private static final String PACKAGE = "net.minestom.server.network";
    // Assigns a value
    private static final ClassDesc CD_OBJECT = ConstantDescs.CD_Object;
    // Assigns a value
    private static final ClassDesc CD_STRING = ConstantDescs.CD_String;
    // Assigns a value
    private static final ClassDesc CD_CLASS = ConstantDescs.CD_Class;
    // Assigns a value
    private static final ClassDesc CD_INT = ConstantDescs.CD_int;
    // Assigns a value
    private static final ClassDesc CD_VOID = ConstantDescs.CD_void;
    // Assigns a value
    private static final ClassDesc CD_METHOD_HANDLES = ConstantDescs.CD_MethodHandles;
    // Assigns a value
    private static final ClassDesc CD_METHOD_HANDLES_LOOKUP = ConstantDescs.CD_MethodHandles_Lookup;
    // Calls a method
    private static final ClassDesc CD_NETWORK_BUFFER = NetworkBuffer.class.describeConstable().orElseThrow();
    // Calls a method
    private static final ClassDesc CD_TYPE = NetworkBuffer.Type.class.describeConstable().orElseThrow();
    // Calls a method
    private static final ClassDesc CD_TEMPLATE_TYPE = NetworkTemplate.class.describeConstable().orElseThrow();
    // Calls a method
    private static final ClassDesc CD_FUNCTION = Function.class.describeConstable().orElseThrow();

    // Calls a method
    private static final MethodTypeDesc MT_VOID = MethodTypeDesc.of(CD_VOID);
    // Calls a method
    private static final MethodTypeDesc MT_LOOKUP = MethodTypeDesc.of(CD_METHOD_HANDLES_LOOKUP);
    // Calls a method
    private static final MethodTypeDesc MT_CLASS_DATA_AT = MethodTypeDesc.of(CD_OBJECT, CD_METHOD_HANDLES_LOOKUP, CD_STRING, CD_CLASS, CD_INT);
    // Calls a method
    private static final MethodTypeDesc MT_READ_OBJECT = MethodTypeDesc.of(CD_OBJECT, CD_NETWORK_BUFFER);
    // Calls a method
    private static final MethodTypeDesc MT_WRITE_OBJECT = MethodTypeDesc.of(CD_VOID, CD_NETWORK_BUFFER, CD_OBJECT);
    // Calls a method
    private static final MethodTypeDesc MT_FUNCTION_APPLY = MethodTypeDesc.of(CD_OBJECT, CD_OBJECT);

    // Assigns a value
    private static final int FIELD_FLAGS = ClassFile.ACC_PRIVATE | ClassFile.ACC_STATIC | ClassFile.ACC_FINAL | ClassFile.ACC_SYNTHETIC;
    // Assigns a value
    private static final int METHOD_FLAGS = ClassFile.ACC_PUBLIC | ClassFile.ACC_FINAL | ClassFile.ACC_SYNTHETIC;
    // Assigns a value
    private static final int CLASS_FLAGS = ClassFile.ACC_FINAL | ClassFile.ACC_SUPER | ClassFile.ACC_SYNTHETIC;

    // Assigns a value
    private static final String CTOR_NAME = "ctor";
    // Assigns a value
    private static final String TYPE_PREFIX = "t";
    // Assigns a value
    private static final String GETTER_PREFIX = "g";
    // Assigns a value
    private static final String READ = "read";
    // Assigns a value
    private static final String WRITE = "write";

    // Start of a method/block
    private NetworkBufferTemplateImpl() {
    // End of a block/expression
    }

    // pairs of [Type<T>, Function (getter)] for N fields, up to 20
    // always odd because ends in ctor applicable to N.
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    static <T extends @UnknownNullability Object> NetworkBuffer.Type<T> template(Object... values) {
        // Calls a method
        Objects.requireNonNull(values, "values");
        // Calls a method
        Check.argCondition(values.length % 2 == 0, "Expected an odd number of values, got: {0}", values.length);
        // Calls a method
        Check.argCondition(values.length < 3, "Expected at least three values ([type, getter], ctor), got: {0}", values.length);
        // Assigns a value
        final int fieldCount = values.length / 2;
        // Calls a method
        Check.argCondition(fieldCount > 20, "Templates only support up to 20 fields, got: {0}", fieldCount);
        // Loop: repeats a block
        for (int i = 0; i < fieldCount; i++) {
            // Calls a method
            Objects.requireNonNull(values[i * 2], typeName(i));
            // Calls a method
            Objects.requireNonNull(values[i * 2 + 1], getterName(i));
        // End of a block/expression
        }
        // Calls a method
        Objects.requireNonNull(values[values.length - 1], CTOR_NAME);
        // Exception handling
        try {
            // Calls a method
            final ClassDesc classDesc = ClassDesc.of(PACKAGE, "NetworkTemplateImpl");
            // Assigns a value
            final byte[] bytes = ClassFile.of().build(classDesc, classBuilder -> {
                // Code statement
                classBuilder.withFlags(CLASS_FLAGS)
                        // Code statement
                        .withSuperclass(CD_OBJECT)
                        // Calls a method
                        .withInterfaceSymbols(CD_TEMPLATE_TYPE);

                // Loop: repeats a block
                for (int i = 0; i < fieldCount; i++) {
                    // Calls a method
                    classBuilder.withField(typeName(i), CD_TYPE, FIELD_FLAGS);
                    // Calls a method
                    classBuilder.withField(getterName(i), CD_FUNCTION, FIELD_FLAGS);
                // End of a block/expression
                }
                // Calls a method
                final ClassDesc ctor = constructorInterface(fieldCount);
                // Calls a method
                classBuilder.withField(CTOR_NAME, ctor, FIELD_FLAGS);

                // Code statement
                classBuilder.withMethodBody(ConstantDescs.CLASS_INIT_NAME, MT_VOID, ClassFile.ACC_STATIC | ClassFile.ACC_SYNTHETIC,
                        // Calls a method
                        codeBuilder -> buildClassInitializer(codeBuilder, classDesc, fieldCount, ctor));
                // Code statement
                classBuilder.withMethodBody(ConstantDescs.INIT_NAME, MT_VOID, ClassFile.ACC_PRIVATE | ClassFile.ACC_SYNTHETIC,
                        // Calls a method
                        codeBuilder -> codeBuilder.aload(0).invokespecial(CD_OBJECT, ConstantDescs.INIT_NAME, MT_VOID).return_());
                // Code statement
                classBuilder.withMethodBody(WRITE, MT_WRITE_OBJECT, METHOD_FLAGS,
                        // Calls a method
                        codeBuilder -> buildWrite(codeBuilder, classDesc, fieldCount));
                // Code statement
                classBuilder.withMethodBody(READ, MT_READ_OBJECT, METHOD_FLAGS,
                        // Calls a method
                        codeBuilder -> buildRead(codeBuilder, classDesc, fieldCount, ctor));
            // End of a block/expression
            });

            // Calls a method
            final MethodHandles.Lookup lookup = MethodHandles.lookup().defineHiddenClassWithClassData(bytes, Arrays.asList(values), true, MethodHandles.Lookup.ClassOption.NESTMATE);
            // Calls a method
            final MethodHandle constructor = lookup.findConstructor(lookup.lookupClass(), MethodType.methodType(void.class));
            // Returns a value to the caller
            return (NetworkBuffer.Type<T>) constructor.invoke();
        // Start of a method/block
        } catch (Throwable throwable) {
            // Throws an exception
            throw new IllegalStateException("Failed to generate network type template, if this continues to be an issue consider disabling compiled templates by setting the property `minestom.template-compiler` to `false`", throwable);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void buildClassInitializer(CodeBuilder codeBuilder, ClassDesc classDesc, int fieldCount, ClassDesc ctor) {
        // Code statement
        codeBuilder.invokestatic(CD_METHOD_HANDLES, "lookup", MT_LOOKUP)
                // Calls a method
                .astore(0);
        // Loop: repeats a block
        for (int i = 0; i < fieldCount; i++) {
            // Code statement
            loadClassDataAt(codeBuilder, CD_TYPE, i * 2)
                    // Calls a method
                    .putstatic(classDesc, typeName(i), CD_TYPE);
            // Code statement
            loadClassDataAt(codeBuilder, CD_FUNCTION, i * 2 + 1)
                    // Calls a method
                    .putstatic(classDesc, getterName(i), CD_FUNCTION);
        // End of a block/expression
        }
        // Code statement
        loadClassDataAt(codeBuilder, ctor, fieldCount * 2)
                // Code statement
                .putstatic(classDesc, CTOR_NAME, ctor)
                // Calls a method
                .return_();
    // End of a block/expression
    }

    // Start of a method/block
    private static void buildWrite(CodeBuilder codeBuilder, ClassDesc classDesc, int fieldCount) {
        // Loop: repeats a block
        for (int i = 0; i < fieldCount; i++) {
            // Code statement
            codeBuilder.getstatic(classDesc, typeName(i), CD_TYPE)
                    // Code statement
                    .aload(1)
                    // Code statement
                    .getstatic(classDesc, getterName(i), CD_FUNCTION)
                    // Code statement
                    .aload(2)
                    // Code statement
                    .invokeinterface(CD_FUNCTION, "apply", MT_FUNCTION_APPLY)
                    // Calls a method
                    .invokeinterface(CD_TYPE, WRITE, MT_WRITE_OBJECT);
        // End of a block/expression
        }
        // Calls a method
        codeBuilder.return_();
    // End of a block/expression
    }

    // Start of a method/block
    private static void buildRead(CodeBuilder codeBuilder, ClassDesc classDesc, int fieldCount, ClassDesc ctor) {
        // Calls a method
        codeBuilder.getstatic(classDesc, CTOR_NAME, ctor);

        // Loop: repeats a block
        for (int i = 0; i < fieldCount; i++) {
            // Code statement
            codeBuilder.getstatic(classDesc, typeName(i), CD_TYPE)
                    // Code statement
                    .aload(1)
                    // Calls a method
                    .invokeinterface(CD_TYPE, READ, MT_READ_OBJECT);
        // End of a block/expression
        }
        // Code statement
        codeBuilder.invokeinterface(ctor, "apply", constructorApplyType(fieldCount))
                // Calls a method
                .areturn();
    // End of a block/expression
    }

    // Start of a method/block
    private static ClassDesc constructorInterface(int fieldCount) {
        // Returns a value to the caller
        return ClassDesc.of(PACKAGE, "NetworkBufferTemplate$F" + fieldCount);
    // End of a block/expression
    }

    // Start of a method/block
    private static MethodTypeDesc constructorApplyType(int fieldCount) {
        // Assigns a value
        ClassDesc[] parameters = new ClassDesc[fieldCount];
        // Calls a method
        Arrays.fill(parameters, CD_OBJECT);
        // Returns a value to the caller
        return MethodTypeDesc.of(CD_OBJECT, parameters);
    // End of a block/expression
    }

    // Start of a method/block
    private static CodeBuilder loadClassDataAt(CodeBuilder codeBuilder, ClassDesc type, int index) {
        // Returns a value to the caller
        return codeBuilder.aload(0) // assumes lookup is at slot 0
                // Code statement
                .ldc("_")
                // Code statement
                .ldc(type)
                // Code statement
                .loadConstant(index)
                // Code statement
                .invokestatic(CD_METHOD_HANDLES, "classDataAt", MT_CLASS_DATA_AT)
                // Calls a method
                .checkcast(type);
    // End of a block/expression
    }

    // Start of a method/block
    private static String typeName(int index) {
        // Returns a value to the caller
        return TYPE_PREFIX + (index + 1);
    // End of a block/expression
    }

    // Start of a method/block
    private static String getterName(int index) {
        // Returns a value to the caller
        return GETTER_PREFIX + (index + 1);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public interface NetworkTemplate extends NetworkBuffer.Type<Object> {
    // End of a block/expression
    }
// End of a block/expression
}
