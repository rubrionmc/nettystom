// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.lang.classfile.ClassFile;
// Import d'une classe nécessaire
import java.lang.classfile.CodeBuilder;
// Import d'une classe nécessaire
import java.lang.constant.ClassDesc;
// Import d'une classe nécessaire
import java.lang.constant.ConstantDescs;
// Import d'une classe nécessaire
import java.lang.constant.MethodTypeDesc;
// Import d'une classe nécessaire
import java.lang.invoke.MethodHandle;
// Import d'une classe nécessaire
import java.lang.invoke.MethodHandles;
// Import d'une classe nécessaire
import java.lang.invoke.MethodType;
// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Function;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
final class NetworkBufferTemplateImpl {
    // Affecte une valeur
    private static final String PACKAGE = "net.minestom.server.network";
    // Affecte une valeur
    private static final ClassDesc CD_OBJECT = ConstantDescs.CD_Object;
    // Affecte une valeur
    private static final ClassDesc CD_STRING = ConstantDescs.CD_String;
    // Affecte une valeur
    private static final ClassDesc CD_CLASS = ConstantDescs.CD_Class;
    // Affecte une valeur
    private static final ClassDesc CD_INT = ConstantDescs.CD_int;
    // Affecte une valeur
    private static final ClassDesc CD_VOID = ConstantDescs.CD_void;
    // Affecte une valeur
    private static final ClassDesc CD_METHOD_HANDLES = ConstantDescs.CD_MethodHandles;
    // Affecte une valeur
    private static final ClassDesc CD_METHOD_HANDLES_LOOKUP = ConstantDescs.CD_MethodHandles_Lookup;
    // Appelle une méthode
    private static final ClassDesc CD_NETWORK_BUFFER = NetworkBuffer.class.describeConstable().orElseThrow();
    // Appelle une méthode
    private static final ClassDesc CD_TYPE = NetworkBuffer.Type.class.describeConstable().orElseThrow();
    // Appelle une méthode
    private static final ClassDesc CD_TEMPLATE_TYPE = NetworkTemplate.class.describeConstable().orElseThrow();
    // Appelle une méthode
    private static final ClassDesc CD_FUNCTION = Function.class.describeConstable().orElseThrow();

    // Appelle une méthode
    private static final MethodTypeDesc MT_VOID = MethodTypeDesc.of(CD_VOID);
    // Appelle une méthode
    private static final MethodTypeDesc MT_LOOKUP = MethodTypeDesc.of(CD_METHOD_HANDLES_LOOKUP);
    // Appelle une méthode
    private static final MethodTypeDesc MT_CLASS_DATA_AT = MethodTypeDesc.of(CD_OBJECT, CD_METHOD_HANDLES_LOOKUP, CD_STRING, CD_CLASS, CD_INT);
    // Appelle une méthode
    private static final MethodTypeDesc MT_READ_OBJECT = MethodTypeDesc.of(CD_OBJECT, CD_NETWORK_BUFFER);
    // Appelle une méthode
    private static final MethodTypeDesc MT_WRITE_OBJECT = MethodTypeDesc.of(CD_VOID, CD_NETWORK_BUFFER, CD_OBJECT);
    // Appelle une méthode
    private static final MethodTypeDesc MT_FUNCTION_APPLY = MethodTypeDesc.of(CD_OBJECT, CD_OBJECT);

    // Affecte une valeur
    private static final int FIELD_FLAGS = ClassFile.ACC_PRIVATE | ClassFile.ACC_STATIC | ClassFile.ACC_FINAL | ClassFile.ACC_SYNTHETIC;
    // Affecte une valeur
    private static final int METHOD_FLAGS = ClassFile.ACC_PUBLIC | ClassFile.ACC_FINAL | ClassFile.ACC_SYNTHETIC;
    // Affecte une valeur
    private static final int CLASS_FLAGS = ClassFile.ACC_FINAL | ClassFile.ACC_SUPER | ClassFile.ACC_SYNTHETIC;

    // Affecte une valeur
    private static final String CTOR_NAME = "ctor";
    // Affecte une valeur
    private static final String TYPE_PREFIX = "t";
    // Affecte une valeur
    private static final String GETTER_PREFIX = "g";
    // Affecte une valeur
    private static final String READ = "read";
    // Affecte une valeur
    private static final String WRITE = "write";

    // Début d'une méthode/d'un bloc
    private NetworkBufferTemplateImpl() {
    // Fin d'un bloc/d'une expression
    }

    // pairs of [Type<T>, Function (getter)] for N fields, up to 20
    // always odd because ends in ctor applicable to N.
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    static <T extends @UnknownNullability Object> NetworkBuffer.Type<T> template(Object... values) {
        // Appelle une méthode
        Objects.requireNonNull(values, "values");
        // Appelle une méthode
        Check.argCondition(values.length % 2 == 0, "Expected an odd number of values, got: {0}", values.length);
        // Appelle une méthode
        Check.argCondition(values.length < 3, "Expected at least three values ([type, getter], ctor), got: {0}", values.length);
        // Affecte une valeur
        final int fieldCount = values.length / 2;
        // Appelle une méthode
        Check.argCondition(fieldCount > 20, "Templates only support up to 20 fields, got: {0}", fieldCount);
        // Boucle : répète un bloc
        for (int i = 0; i < fieldCount; i++) {
            // Appelle une méthode
            Objects.requireNonNull(values[i * 2], typeName(i));
            // Appelle une méthode
            Objects.requireNonNull(values[i * 2 + 1], getterName(i));
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Objects.requireNonNull(values[values.length - 1], CTOR_NAME);
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final ClassDesc classDesc = ClassDesc.of(PACKAGE, "NetworkTemplateImpl");
            // Affecte une valeur
            final byte[] bytes = ClassFile.of().build(classDesc, classBuilder -> {
                // Instruction de code
                classBuilder.withFlags(CLASS_FLAGS)
                        // Instruction de code
                        .withSuperclass(CD_OBJECT)
                        // Appelle une méthode
                        .withInterfaceSymbols(CD_TEMPLATE_TYPE);

                // Boucle : répète un bloc
                for (int i = 0; i < fieldCount; i++) {
                    // Appelle une méthode
                    classBuilder.withField(typeName(i), CD_TYPE, FIELD_FLAGS);
                    // Appelle une méthode
                    classBuilder.withField(getterName(i), CD_FUNCTION, FIELD_FLAGS);
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                final ClassDesc ctor = constructorInterface(fieldCount);
                // Appelle une méthode
                classBuilder.withField(CTOR_NAME, ctor, FIELD_FLAGS);

                // Instruction de code
                classBuilder.withMethodBody(ConstantDescs.CLASS_INIT_NAME, MT_VOID, ClassFile.ACC_STATIC | ClassFile.ACC_SYNTHETIC,
                        // Appelle une méthode
                        codeBuilder -> buildClassInitializer(codeBuilder, classDesc, fieldCount, ctor));
                // Instruction de code
                classBuilder.withMethodBody(ConstantDescs.INIT_NAME, MT_VOID, ClassFile.ACC_PRIVATE | ClassFile.ACC_SYNTHETIC,
                        // Appelle une méthode
                        codeBuilder -> codeBuilder.aload(0).invokespecial(CD_OBJECT, ConstantDescs.INIT_NAME, MT_VOID).return_());
                // Instruction de code
                classBuilder.withMethodBody(WRITE, MT_WRITE_OBJECT, METHOD_FLAGS,
                        // Appelle une méthode
                        codeBuilder -> buildWrite(codeBuilder, classDesc, fieldCount));
                // Instruction de code
                classBuilder.withMethodBody(READ, MT_READ_OBJECT, METHOD_FLAGS,
                        // Appelle une méthode
                        codeBuilder -> buildRead(codeBuilder, classDesc, fieldCount, ctor));
            // Fin d'un bloc/d'une expression
            });

            // Appelle une méthode
            final MethodHandles.Lookup lookup = MethodHandles.lookup().defineHiddenClassWithClassData(bytes, Arrays.asList(values), true, MethodHandles.Lookup.ClassOption.NESTMATE);
            // Appelle une méthode
            final MethodHandle constructor = lookup.findConstructor(lookup.lookupClass(), MethodType.methodType(void.class));
            // Renvoie une valeur à l'appelant
            return (NetworkBuffer.Type<T>) constructor.invoke();
        // Début d'une méthode/d'un bloc
        } catch (Throwable throwable) {
            // Lève une exception
            throw new IllegalStateException("Failed to generate network type template, if this continues to be an issue consider disabling compiled templates by setting the property `minestom.template-compiler` to `false`", throwable);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void buildClassInitializer(CodeBuilder codeBuilder, ClassDesc classDesc, int fieldCount, ClassDesc ctor) {
        // Instruction de code
        codeBuilder.invokestatic(CD_METHOD_HANDLES, "lookup", MT_LOOKUP)
                // Appelle une méthode
                .astore(0);
        // Boucle : répète un bloc
        for (int i = 0; i < fieldCount; i++) {
            // Instruction de code
            loadClassDataAt(codeBuilder, CD_TYPE, i * 2)
                    // Appelle une méthode
                    .putstatic(classDesc, typeName(i), CD_TYPE);
            // Instruction de code
            loadClassDataAt(codeBuilder, CD_FUNCTION, i * 2 + 1)
                    // Appelle une méthode
                    .putstatic(classDesc, getterName(i), CD_FUNCTION);
        // Fin d'un bloc/d'une expression
        }
        // Instruction de code
        loadClassDataAt(codeBuilder, ctor, fieldCount * 2)
                // Instruction de code
                .putstatic(classDesc, CTOR_NAME, ctor)
                // Appelle une méthode
                .return_();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void buildWrite(CodeBuilder codeBuilder, ClassDesc classDesc, int fieldCount) {
        // Boucle : répète un bloc
        for (int i = 0; i < fieldCount; i++) {
            // Instruction de code
            codeBuilder.getstatic(classDesc, typeName(i), CD_TYPE)
                    // Instruction de code
                    .aload(1)
                    // Instruction de code
                    .getstatic(classDesc, getterName(i), CD_FUNCTION)
                    // Instruction de code
                    .aload(2)
                    // Instruction de code
                    .invokeinterface(CD_FUNCTION, "apply", MT_FUNCTION_APPLY)
                    // Appelle une méthode
                    .invokeinterface(CD_TYPE, WRITE, MT_WRITE_OBJECT);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        codeBuilder.return_();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void buildRead(CodeBuilder codeBuilder, ClassDesc classDesc, int fieldCount, ClassDesc ctor) {
        // Appelle une méthode
        codeBuilder.getstatic(classDesc, CTOR_NAME, ctor);

        // Boucle : répète un bloc
        for (int i = 0; i < fieldCount; i++) {
            // Instruction de code
            codeBuilder.getstatic(classDesc, typeName(i), CD_TYPE)
                    // Instruction de code
                    .aload(1)
                    // Appelle une méthode
                    .invokeinterface(CD_TYPE, READ, MT_READ_OBJECT);
        // Fin d'un bloc/d'une expression
        }
        // Instruction de code
        codeBuilder.invokeinterface(ctor, "apply", constructorApplyType(fieldCount))
                // Appelle une méthode
                .areturn();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static ClassDesc constructorInterface(int fieldCount) {
        // Renvoie une valeur à l'appelant
        return ClassDesc.of(PACKAGE, "NetworkBufferTemplate$F" + fieldCount);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static MethodTypeDesc constructorApplyType(int fieldCount) {
        // Affecte une valeur
        ClassDesc[] parameters = new ClassDesc[fieldCount];
        // Appelle une méthode
        Arrays.fill(parameters, CD_OBJECT);
        // Renvoie une valeur à l'appelant
        return MethodTypeDesc.of(CD_OBJECT, parameters);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static CodeBuilder loadClassDataAt(CodeBuilder codeBuilder, ClassDesc type, int index) {
        // Renvoie une valeur à l'appelant
        return codeBuilder.aload(0) // assumes lookup is at slot 0
                // Instruction de code
                .ldc("_")
                // Instruction de code
                .ldc(type)
                // Instruction de code
                .loadConstant(index)
                // Instruction de code
                .invokestatic(CD_METHOD_HANDLES, "classDataAt", MT_CLASS_DATA_AT)
                // Appelle une méthode
                .checkcast(type);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static String typeName(int index) {
        // Renvoie une valeur à l'appelant
        return TYPE_PREFIX + (index + 1);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static String getterName(int index) {
        // Renvoie une valeur à l'appelant
        return GETTER_PREFIX + (index + 1);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public interface NetworkTemplate extends NetworkBuffer.Type<Object> {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
