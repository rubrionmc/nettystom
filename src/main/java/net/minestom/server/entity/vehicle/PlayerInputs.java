// Package declaration for this file
package net.minestom.server.entity.vehicle;

// Type declaration (class/interface/enum/record)
public class PlayerInputs {

    // Code statement
    private boolean forward;
    // Code statement
    private boolean backward;
    // Code statement
    private boolean left;
    // Code statement
    private boolean right;
    // Code statement
    private boolean jump;
    // Code statement
    private boolean shift;
    // Code statement
    private boolean sprint;

    // Start of a method/block
    public boolean forward() {
        // Returns a value to the caller
        return forward;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean backward() {
        // Returns a value to the caller
        return backward;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean left() {
        // Returns a value to the caller
        return left;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean right() {
        // Returns a value to the caller
        return right;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean jump() {
        // Returns a value to the caller
        return jump;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean shift() {
        // Returns a value to the caller
        return shift;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean sprint() {
        // Returns a value to the caller
        return sprint;
    // End of a block/expression
    }

    // Start of a method/block
    public void refresh(boolean forward, boolean backward, boolean left, boolean right, boolean jump, boolean shift, boolean sprint) {
        // Access to the current/parent object
        this.forward = forward;
        // Access to the current/parent object
        this.backward = backward;
        // Access to the current/parent object
        this.left = left;
        // Access to the current/parent object
        this.right = right;
        // Access to the current/parent object
        this.jump = jump;
        // Access to the current/parent object
        this.shift = shift;
        // Access to the current/parent object
        this.sprint = sprint;
    // End of a block/expression
    }
// End of a block/expression
}
