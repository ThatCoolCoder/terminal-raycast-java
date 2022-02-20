package com.thatcoolcoder.terminalRaycast;

// I couldn't find a single decent vector implementation so I wrote one myself.
// Would be nice to support generics but java generics are not that great apparently.
public class Vector3d {
    public float x = 0;
    public float y = 0;
    public float z = 0;

    static float radToDeg = (float) (180.0 / Math.PI);

    /**
     * Create empty vector
     */
    public Vector3d() {

    }

    /**
     * Create vector with values on axis
     * @param _x
     * @param _y
     * @param _z
     */
    public Vector3d(float _x, float _y, float _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    /**
     * Copy constructor
     * @param v
     */
    public Vector3d(Vector3d v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    //#region BasicArithmetic

    /**
     * Add v to this, modifying this.
     * @param v
     */
    public void add(Vector3d v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    /**
     * Add v1 and v2 and return the result.
     * @param v1
     * @param v2
     */
    public static Vector3d add(Vector3d v1, Vector3d v2) {
        return new Vector3d(
            v1.x + v2.x,
            v1.y + v2.y,
            v1.z + v2.z);
    }

    /**
     * Subtract v from this, modifying this.
     * @param v
     */
    public void sub(Vector3d v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    /**
     * Subtract v2 from v1 and return the result.
     * @param v1
     * @param v2
     */
    public static Vector3d sub(Vector3d v1, Vector3d v2) {
        return new Vector3d(
            v1.x - v2.x,
            v1.y - v2.y,
            v1.z - v2.z);
    }

    /**
     * Multiply by an amount, modifying this.
     * @param amount
     */
    public void mult(float amount) {
        x *= amount;
        y *= amount;
        z *= amount;
    }

    /**
     * Multiply vector by an amount and return result
     * @param v
     * @param amount
     */
    public static Vector3d mult(Vector3d v, float amount) {
        return new Vector3d(
            v.x * amount,
            v.y * amount,
            v.z * amount);
    }

    /**
     * Divide by an amount, modifying this.
     * @param amount
     */
    public void divide(float amount) {
        x /= amount;
        y /= amount;
        z /= amount;
    }

    /**
     * Divide vector by an amount and return result
     * @param v
     * @param amount
     */
    public static Vector3d divide(Vector3d v, float amount) {
        return new Vector3d(
            v.x / amount,
            v.y / amount,
            v.z / amount);
    }

    //#endregion BasicArithmetic

    /**
     * Get magnitude (length) of the vector
     * @return
     */
    public float mag() {
        return (float) Math.sqrt((double) x * x + y * y + z * z);
    }

    /**
     * Get magnitude (length) squared of the vector
     * @return
     */
    public float magSq() {
        return x * x + y * y + z * z;
    }

    /**
     * Get the distance from here to another vector
     * @param v
     * @return
     */
    public float dist(Vector3d v) {
        float displacementX = x - v.x;
        float displacementY = y - v.y;
        float displacementZ = z - v.z;
        return (float) Math.sqrt((double) (
            displacementX * displacementX +
            displacementY * displacementY +
            displacementZ * displacementZ));
    }

    /**
     * Get the distance squared from here to another vector
     * @param v
     * @return
     */
    public float distSq(Vector3d v) {
        float displacementX = x - v.x;
        float displacementY = y - v.y;
        float displacementZ = z - v.z;
        return displacementX * displacementX +
            displacementY * displacementY +
            displacementZ * displacementZ;
    }

    /**
     * Scale this vector so its magnitude is 1
     */
    public void normalize() {
        float magnitude = mag();
        this.x /= magnitude;
        this.z /= magnitude;
        this.y /= magnitude;
    }

    /**
     * Return a normalized copy of the vector
     * @param v
     * @return
     */
    public static Vector3d normalize(Vector3d v) {
        float magnitude = v.mag();
        return new Vector3d(
            v.x / magnitude,
            v.y / magnitude,
            v.z / magnitude
        );
    }

    //#region Angles

    /**
     * Rotate a vector in 2d space, around the z axis.
     * @param angle
     * @param radians
     */
    public void rotate(float angle, boolean radians) {
        if (! radians) angle /= radToDeg;

        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        float newX = x * cos - y * sin;
        float newY = x * sin + y * cos;

        x = newX;
        y = newY;
    }

    /**
     * Return a rotated copy of a vector
     * @param v
     * @param angle
     * @param radians
     * @return
     */
    public static Vector3d rotate(Vector3d v, float angle, boolean radians) {
        if (! radians) angle /= radToDeg;

        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        float newX = v.x * cos - v.y * sin;
        float newY = v.x * sin + v.y * cos;

        return new Vector3d(newX, newY, 0);
    }

    /**
     * Get the heading (angle) of the vector
     * @param radians
     * @return
     */
    public float heading(boolean radians) {
        float angle = (float) Math.atan2(y, x);
        if (! radians) angle *= radToDeg;
        return angle;
    }

    //#endregion Angles
}
