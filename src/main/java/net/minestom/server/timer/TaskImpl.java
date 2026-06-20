// Package declaration for this file
package net.minestom.server.timer;

// Import of a required class
import it.unimi.dsi.fastutil.HashCommon;

// Import of a required class
import java.lang.invoke.MethodHandles;
// Import of a required class
import java.lang.invoke.VarHandle;
// Import of a required class
import java.util.function.Supplier;

// Type declaration (class/interface/enum/record)
final class TaskImpl implements Task {
    // Code statement
    private static final VarHandle PARKED;

    // Start of a method/block
    static {
        // Exception handling
        try {
            // Calls a method
            PARKED = MethodHandles.lookup().findVarHandle(TaskImpl.class, "parked", boolean.class);
        // Start of a method/block
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Throws an exception
            throw new IllegalStateException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    private final int id;
    // Code statement
    private final Supplier<TaskSchedule> task;
    // Code statement
    private final ExecutionType executionType;
    // Code statement
    private final SchedulerImpl owner;

    // Code statement
    volatile boolean alive;
    // Code statement
    volatile boolean parked;

    // Code statement
    TaskImpl(int id,
             // Code statement
             Supplier<TaskSchedule> task,
             // Code statement
             ExecutionType executionType,
             // Start of a method/block
             SchedulerImpl owner) {
        // Access to the current/parent object
        this.id = id;
        // Access to the current/parent object
        this.task = task;
        // Access to the current/parent object
        this.executionType = executionType;
        // Access to the current/parent object
        this.owner = owner;
        // Access to the current/parent object
        this.alive = true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void unpark() {
        // Access to the current/parent object
        this.owner.unparkTask(this);
    // End of a block/expression
    }

    // Start of a method/block
    boolean tryUnpark() {
        // Returns a value to the caller
        return PARKED.compareAndSet(this, true, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isParked() {
        // Returns a value to the caller
        return parked;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void cancel() {
        // Access to the current/parent object
        this.alive = false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isAlive() {
        // Returns a value to the caller
        return alive;
    // End of a block/expression
    }

    // Start of a method/block
    public int id() {
        // Returns a value to the caller
        return id;
    // End of a block/expression
    }

    // Start of a method/block
    public Supplier<TaskSchedule> task() {
        // Returns a value to the caller
        return task;
    // End of a block/expression
    }

    // Start of a method/block
    public ExecutionType executionType() {
        // Returns a value to the caller
        return executionType;
    // End of a block/expression
    }

    // Start of a method/block
    public SchedulerImpl owner() {
        // Returns a value to the caller
        return owner;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object obj) {
        // Branch: checks a condition
        if (obj == this) return true;
        // Branch: checks a condition
        if (obj == null || obj.getClass() != this.getClass()) return false;
        // Calls a method
        var that = (TaskImpl) obj;
        // Returns a value to the caller
        return this.id == that.id;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Returns a value to the caller
        return HashCommon.murmurHash3(id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return "TaskImpl[" +
                // Code statement
                "id=" + id + ", " +
                // Code statement
                "task=" + task + ", " +
                // Code statement
                "executionType=" + executionType + ", " +
                // Code statement
                "owner=" + owner + ']';
    // End of a block/expression
    }

// End of a block/expression
}
