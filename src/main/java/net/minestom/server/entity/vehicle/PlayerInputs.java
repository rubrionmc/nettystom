// Déclaration du paquet de ce fichier
package net.minestom.server.entity.vehicle;

// Déclaration de type (classe/interface/enum/record)
public class PlayerInputs {

    // Instruction de code
    private boolean forward;
    // Instruction de code
    private boolean backward;
    // Instruction de code
    private boolean left;
    // Instruction de code
    private boolean right;
    // Instruction de code
    private boolean jump;
    // Instruction de code
    private boolean shift;
    // Instruction de code
    private boolean sprint;

    // Début d'une méthode/d'un bloc
    public boolean forward() {
        // Renvoie une valeur à l'appelant
        return forward;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean backward() {
        // Renvoie une valeur à l'appelant
        return backward;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean left() {
        // Renvoie une valeur à l'appelant
        return left;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean right() {
        // Renvoie une valeur à l'appelant
        return right;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean jump() {
        // Renvoie une valeur à l'appelant
        return jump;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean shift() {
        // Renvoie une valeur à l'appelant
        return shift;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean sprint() {
        // Renvoie une valeur à l'appelant
        return sprint;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void refresh(boolean forward, boolean backward, boolean left, boolean right, boolean jump, boolean shift, boolean sprint) {
        // Accès à l'objet courant/parent
        this.forward = forward;
        // Accès à l'objet courant/parent
        this.backward = backward;
        // Accès à l'objet courant/parent
        this.left = left;
        // Accès à l'objet courant/parent
        this.right = right;
        // Accès à l'objet courant/parent
        this.jump = jump;
        // Accès à l'objet courant/parent
        this.shift = shift;
        // Accès à l'objet courant/parent
        this.sprint = sprint;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
