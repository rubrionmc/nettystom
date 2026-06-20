// Déclaration du paquet de ce fichier
package net.minestom.codegen;

// Import d'une classe nécessaire
import java.io.InputStream;
// Import d'une classe nécessaire
import java.nio.file.Path;

// Déclaration de type (classe/interface/enum/record)
public final class RecipeTypeGenerator extends GenericEnumGenerator {
    // Début d'une méthode/d'un bloc
    public RecipeTypeGenerator(InputStream recipeTypesFile, Path outputFolder) {
        // Accès à l'objet courant/parent
        super("net.minestom.server.recipe", "RecipeType", recipeTypesFile, outputFolder);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toConstant(String namespace) {
        // Renvoie une valeur à l'appelant
        return super.toConstant(namespace).replace("CRAFTING_", "");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
