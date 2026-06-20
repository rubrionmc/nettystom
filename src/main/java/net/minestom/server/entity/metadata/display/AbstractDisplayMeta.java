// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.display;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.EntityMeta;

// Déclaration de type (classe/interface/enum/record)
public class AbstractDisplayMeta extends EntityMeta {
    // Début d'une méthode/d'un bloc
    protected AbstractDisplayMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getTransformationInterpolationStartDelta() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.INTERPOLATION_DELAY);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setTransformationInterpolationStartDelta(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.INTERPOLATION_DELAY, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getTransformationInterpolationDuration() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.TRANSFORMATION_INTERPOLATION_DURATION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setTransformationInterpolationDuration(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.TRANSFORMATION_INTERPOLATION_DURATION, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getPosRotInterpolationDuration() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.POSITION_ROTATION_INTERPOLATION_DURATION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setPosRotInterpolationDuration(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.POSITION_ROTATION_INTERPOLATION_DURATION, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Point getTranslation() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.TRANSLATION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setTranslation(Point value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.TRANSLATION, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Vec getScale() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.SCALE).asVec();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setScale(Vec value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.SCALE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float [] getLeftRotation() {
        //todo replace with actual quaternion type
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.ROTATION_LEFT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLeftRotation(float [] value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.ROTATION_LEFT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float [] getRightRotation() {
        //todo replace with actual quaternion type
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.ROTATION_RIGHT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRightRotation(float [] value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.ROTATION_RIGHT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BillboardConstraints getBillboardRenderConstraints() {
        // Renvoie une valeur à l'appelant
        return BillboardConstraints.VALUES[metadata.get(MetadataDef.Display.BILLBOARD_CONSTRAINTS)];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBillboardRenderConstraints(BillboardConstraints value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.BILLBOARD_CONSTRAINTS, (byte) value.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getBrightnessOverride() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.BRIGHTNESS_OVERRIDE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBrightnessOverride(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.BRIGHTNESS_OVERRIDE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBrightness(int blockLight, int skyLight) {
        // Appelle une méthode
        setBrightnessOverride((blockLight & 0xF) << 4 | (skyLight & 0xF) << 20);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getBlockLight() {
        // Renvoie une valeur à l'appelant
        return getLight(4);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getSkyLight() {
        // Renvoie une valeur à l'appelant
        return getLight(20);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private int getLight(int shift) {
        // Appelle une méthode
        int brightnessOverride = getBrightnessOverride();
        // Embranchement : vérifie une condition
        if (brightnessOverride <= 0)
            // Renvoie une valeur à l'appelant
            return 0;
        // Branche alternative de la condition
        else
            // Renvoie une valeur à l'appelant
            return (brightnessOverride >> shift) & 0xF;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getViewRange() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.VIEW_RANGE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setViewRange(float value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.VIEW_RANGE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getShadowRadius() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.SHADOW_RADIUS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setShadowRadius(float value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.SHADOW_RADIUS, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getShadowStrength() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.SHADOW_STRENGTH);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setShadowStrength(float value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.SHADOW_STRENGTH, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getWidth() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.WIDTH);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setWidth(float value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.WIDTH, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getHeight() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.HEIGHT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHeight(float value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.HEIGHT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getGlowColorOverride() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Display.GLOW_COLOR_OVERRIDE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setGlowColorOverride(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Display.GLOW_COLOR_OVERRIDE, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum BillboardConstraints {
        // Instruction de code
        FIXED,
        // Instruction de code
        VERTICAL,
        // Instruction de code
        HORIZONTAL,
        // Instruction de code
        CENTER;

        // Appelle une méthode
        private final static BillboardConstraints[] VALUES = values();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
