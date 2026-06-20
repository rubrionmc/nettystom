// Déclaration du paquet de ce fichier
package net.minestom.server.utils.collection;

// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.ValueSource;

// Import d'une classe nécessaire
import java.util.Arrays;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class ObjectArrayTest {

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {false, true})
    // Début d'une méthode/d'un bloc
    public void objectArray(boolean concurrent) {
        // Appelle une méthode
        ObjectArray<String> array = concurrent ? ObjectArray.concurrent() : ObjectArray.singleThread();

        // Appelle une méthode
        array.set(50, "Hey");
        // Appelle une méthode
        assertEquals("Hey", array.get(50));
        // Appelle une méthode
        assertNull(array.get(49));
        // Appelle une méthode
        assertNull(array.get(51));

        // Appelle une méthode
        array.set(0, "Hey2");
        // Appelle une méthode
        assertEquals("Hey2", array.get(0));
        // Appelle une méthode
        assertEquals("Hey", array.get(50));

        // Appelle une méthode
        array.trim();
        // Appelle une méthode
        assertEquals("Hey2", array.get(0));
        // Appelle une méthode
        assertEquals("Hey", array.get(50));

        // Appelle une méthode
        array.set(250, "Hey3");
        // Appelle une méthode
        assertEquals("Hey3", array.get(250));
        // Appelle une méthode
        assertEquals("Hey2", array.get(0));
        // Appelle une méthode
        assertEquals("Hey", array.get(50));

        // Appelle une méthode
        assertNull(array.get(49));
        // Appelle une méthode
        assertNull(array.get(251));
        // Appelle une méthode
        assertNull(array.get(Integer.MAX_VALUE));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {false, true})
    // Début d'une méthode/d'un bloc
    public void arrayCopy(boolean concurrent) {
        // Appelle une méthode
        ObjectArray<String> array = concurrent ? ObjectArray.concurrent() : ObjectArray.singleThread();

        // Appelle une méthode
        array.set(1, "Hey");
        // Appelle une méthode
        String[] copyCache = array.arrayCopy(String.class);
        // Appelle une méthode
        assertArrayEquals(new String[]{null, "Hey"}, copyCache);

        // Appelle une méthode
        array.set(2, "Hey2");
        // Appelle une méthode
        assertArrayEquals(new String[]{null, "Hey", "Hey2"}, array.arrayCopy(String.class));
        // Appelle une méthode
        assertArrayEquals(new String[]{null, "Hey"}, copyCache, "The copy cache should not be modified");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {false, true})
    // Début d'une méthode/d'un bloc
    public void trim(boolean concurrent) {
        // Appelle une méthode
        ObjectArray<String> array = concurrent ? ObjectArray.concurrent() : ObjectArray.singleThread();
        // zero case
        // Appelle une méthode
        array.trim();
        // Appelle une méthode
        assertArrayEquals(new String[0], array.arrayCopy(String.class));

        // 3 elements with a space
        // Appelle une méthode
        array.set(0, "Hey");
        // Appelle une méthode
        array.set(1, "Hey2");
        // Appelle une méthode
        array.set(3, "Hey4");
        // Appelle une méthode
        array.trim();
        // Appelle une méthode
        assertArrayEquals(new String[]{"Hey", "Hey2", null, "Hey4"}, array.arrayCopy(String.class));

        // 4 elements without a space
        // Appelle une méthode
        array.set(2, "Hey3");
        // Appelle une méthode
        array.trim();
        // Appelle une méthode
        assertArrayEquals(new String[]{"Hey", "Hey2", "Hey3", "Hey4"}, array.arrayCopy(String.class));

        // set trailing 2 elements with a null
        // Appelle une méthode
        array.remove(2);
        // Appelle une méthode
        array.remove(3);
        // Appelle une méthode
        array.trim();
        // Appelle une méthode
        assertArrayEquals(new String[]{"Hey", "Hey2"}, array.arrayCopy(String.class));

        // remove first element
        // Appelle une méthode
        array.remove(0);
        // Appelle une méthode
        array.trim();
        // Appelle une méthode
        assertArrayEquals(new String[]{null, "Hey2"}, array.arrayCopy(String.class));

        // remove last element, forcing the array to shrink after trim
        // Appelle une méthode
        array.remove(1);
        // Appelle une méthode
        array.trim();
        // Appelle une méthode
        assertArrayEquals(new String[0], array.arrayCopy(String.class));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
