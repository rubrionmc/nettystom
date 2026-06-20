// Package declaration for this file
package net.minestom.server.entity.metadata.display;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.EntityMeta;

// Type declaration (class/interface/enum/record)
public class AbstractDisplayMeta extends EntityMeta {
    // Start of a method/block
    protected AbstractDisplayMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getTransformationInterpolationStartDelta() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.INTERPOLATION_DELAY);
    // End of a block/expression
    }

    // Start of a method/block
    public void setTransformationInterpolationStartDelta(int value) {
        // Calls a method
        metadata.set(MetadataDef.Display.INTERPOLATION_DELAY, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getTransformationInterpolationDuration() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.TRANSFORMATION_INTERPOLATION_DURATION);
    // End of a block/expression
    }

    // Start of a method/block
    public void setTransformationInterpolationDuration(int value) {
        // Calls a method
        metadata.set(MetadataDef.Display.TRANSFORMATION_INTERPOLATION_DURATION, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getPosRotInterpolationDuration() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.POSITION_ROTATION_INTERPOLATION_DURATION);
    // End of a block/expression
    }

    // Start of a method/block
    public void setPosRotInterpolationDuration(int value) {
        // Calls a method
        metadata.set(MetadataDef.Display.POSITION_ROTATION_INTERPOLATION_DURATION, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Point getTranslation() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.TRANSLATION);
    // End of a block/expression
    }

    // Start of a method/block
    public void setTranslation(Point value) {
        // Calls a method
        metadata.set(MetadataDef.Display.TRANSLATION, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Vec getScale() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.SCALE).asVec();
    // End of a block/expression
    }

    // Start of a method/block
    public void setScale(Vec value) {
        // Calls a method
        metadata.set(MetadataDef.Display.SCALE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public float [] getLeftRotation() {
        //todo replace with actual quaternion type
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.ROTATION_LEFT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLeftRotation(float [] value) {
        // Calls a method
        metadata.set(MetadataDef.Display.ROTATION_LEFT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public float [] getRightRotation() {
        //todo replace with actual quaternion type
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.ROTATION_RIGHT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setRightRotation(float [] value) {
        // Calls a method
        metadata.set(MetadataDef.Display.ROTATION_RIGHT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public BillboardConstraints getBillboardRenderConstraints() {
        // Returns a value to the caller
        return BillboardConstraints.VALUES[metadata.get(MetadataDef.Display.BILLBOARD_CONSTRAINTS)];
    // End of a block/expression
    }

    // Start of a method/block
    public void setBillboardRenderConstraints(BillboardConstraints value) {
        // Calls a method
        metadata.set(MetadataDef.Display.BILLBOARD_CONSTRAINTS, (byte) value.ordinal());
    // End of a block/expression
    }

    // Start of a method/block
    public int getBrightnessOverride() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.BRIGHTNESS_OVERRIDE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setBrightnessOverride(int value) {
        // Calls a method
        metadata.set(MetadataDef.Display.BRIGHTNESS_OVERRIDE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public void setBrightness(int blockLight, int skyLight) {
        // Calls a method
        setBrightnessOverride((blockLight & 0xF) << 4 | (skyLight & 0xF) << 20);
    // End of a block/expression
    }

    // Start of a method/block
    public int getBlockLight() {
        // Returns a value to the caller
        return getLight(4);
    // End of a block/expression
    }

    // Start of a method/block
    public int getSkyLight() {
        // Returns a value to the caller
        return getLight(20);
    // End of a block/expression
    }

    // Start of a method/block
    private int getLight(int shift) {
        // Calls a method
        int brightnessOverride = getBrightnessOverride();
        // Branch: checks a condition
        if (brightnessOverride <= 0)
            // Returns a value to the caller
            return 0;
        // Alternative branch of the condition
        else
            // Returns a value to the caller
            return (brightnessOverride >> shift) & 0xF;
    // End of a block/expression
    }

    // Start of a method/block
    public float getViewRange() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.VIEW_RANGE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setViewRange(float value) {
        // Calls a method
        metadata.set(MetadataDef.Display.VIEW_RANGE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public float getShadowRadius() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.SHADOW_RADIUS);
    // End of a block/expression
    }

    // Start of a method/block
    public void setShadowRadius(float value) {
        // Calls a method
        metadata.set(MetadataDef.Display.SHADOW_RADIUS, value);
    // End of a block/expression
    }

    // Start of a method/block
    public float getShadowStrength() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.SHADOW_STRENGTH);
    // End of a block/expression
    }

    // Start of a method/block
    public void setShadowStrength(float value) {
        // Calls a method
        metadata.set(MetadataDef.Display.SHADOW_STRENGTH, value);
    // End of a block/expression
    }

    // Start of a method/block
    public float getWidth() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.WIDTH);
    // End of a block/expression
    }

    // Start of a method/block
    public void setWidth(float value) {
        // Calls a method
        metadata.set(MetadataDef.Display.WIDTH, value);
    // End of a block/expression
    }

    // Start of a method/block
    public float getHeight() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.HEIGHT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHeight(float value) {
        // Calls a method
        metadata.set(MetadataDef.Display.HEIGHT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getGlowColorOverride() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Display.GLOW_COLOR_OVERRIDE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setGlowColorOverride(int value) {
        // Calls a method
        metadata.set(MetadataDef.Display.GLOW_COLOR_OVERRIDE, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum BillboardConstraints {
        // Code statement
        FIXED,
        // Code statement
        VERTICAL,
        // Code statement
        HORIZONTAL,
        // Code statement
        CENTER;

        // Calls a method
        private final static BillboardConstraints[] VALUES = values();
    // End of a block/expression
    }

// End of a block/expression
}
