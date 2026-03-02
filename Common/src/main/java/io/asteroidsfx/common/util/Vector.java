package io.asteroidsfx.common.util;

/**
 * A classic 2D vector with x and y components.
 */
public class Vector {
    /**
     * The x-component of the vector.
     */
    public double x;
    /**
     * The y-component of the vector.
     */
    public double y;

    /**
     * A constructor that creates a Vector with values x=0, y=0.
     */
    public Vector(){
        this(0, 0);
    }

    /**
     * A constructor that creates a Vector.
     * @param x the x component of the Vector.
     * @param y the y component of the Vector.
     */
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Create a unit Vector from an angle.
     * @param angleInRadians the angle given in radians.
     * @return the unit Vector facing the specified angle.
     */
    public static Vector fromAngle(double angleInRadians){
        return new Vector(Math.cos(angleInRadians), Math.sin(angleInRadians));
    }

    /**
     * Set the components of the Vector.
     * @param x the new x-component value.
     * @param y the new y-component value.
     * @return the modified Vector.
     */
    public Vector set(double x, double y){
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Create a copy of a Vector.
     * <br>
     * Useful to prevent modifying the original vector.
     * @return the copy of the Vector.
     */
    public Vector copy(){
        return new Vector(this.x, this.y);
    }

    /**
     * Get the magnitude of the Vector.
     * <br>
     * Consider using {@link Vector#magSq()} to prevent expensive sqrt calculation.
     * @return the magnitude of the Vector.
     */
    public double mag(){
        return Math.sqrt( (this.x*this.x) + (this.y*this.y) );
    }

    /**
     * Get the squared magnitude of the Vector.
     * @return the square magnitude of the Vector.
     */
    public double magSq(){
        return (this.x*this.x) + (this.y*this.y);
    }

    /**
     * Adds another Vector to the Vector.
     * @param other the Vector to add.
     * @return the Vector after the addition.
     */
    public Vector add(Vector other){
        this.add(other.x, other.y);
        return this;
    }

    /**
     * Adds components to the Vector.
     * @param x the x-component to add.
     * @param y the y-component to add.
     * @return the Vector after the addition.
     */
    public Vector add(double x, double y){
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Adds two Vectors
     * @param v1 the first Vector to add.
     * @param v2 the second Vector to add.
     * @return a new Vector equalling sum of the given Vectors.
     */
    public static Vector add(Vector v1, Vector v2){
        return new Vector(v1.x + v2.x, v1.y + v2.y);
    }

    /**
     * Subtracts another Vector from the Vector.
     * @param other the Vector to subtract.
     * @return the Vector after the subtraction.
     */
    public Vector sub(Vector other){
        this.sub(other.x, other.y);
        return this;
    }

    /**
     * Subtracts the components from the Vector.
     * @param x the x-component to subtract.
     * @param y the x-component to subtract.
     * @return the Vector after the subtraction.
     */
    public Vector sub(double x, double y){
        this.x -= x;
        this.y -= y;
        return this;
    }

    /**
     * Subtracts two Vectors.
     * @param v1 the minuend Vector, e.g. the one being subtracted from.
     * @param v2 the subtrahend Vector, e.g. the one being subtracted.
     * @return a new Vector equalling the result of the subtraction.
     */
    public static Vector sub(Vector v1, Vector v2){
        return new Vector(v1.x - v2.x, v1.y - v2.y);
    }

    /**
     * Multiplies both components of a Vector by the multiplier value.
     * @param n the multiplier value.
     * @return the Vector after the multiplication.
     */
    public Vector mult(double n){
        this.x *= n;
        this.y *= n;
        return this;
    }

    /**
     * Multiplies both components of the Vector by the multiplier value.
     * @param v the Vector being multiplied.
     * @param n the multiplier value.
     * @return the Vector after the multiplication.
     */
    public static Vector mult(Vector v, double n){
        v.mult(n);
        return v;
    }

    /**
     * Divides both components of a Vector by the divisor value.
     * @param n the divisor value.
     * @return the Vector after the division.
     */
    public Vector div(double n){
        this.x /= n;
        this.y /= n;
        return this;
    }


    /**
     * Divides both components of the Vector by the divisor value.
     * @param v the Vector being divided.
     * @param n the divisor value.
     * @return the Vector after the division.
     */
    public static Vector div(Vector v, double n){
        v.div(n);
        return v;
    }

    /**
     * Calculates the distance from a Vector to the Vector.
     * @param v the other Vector.
     * @return the distance between the Vectors.
     */
    public double dist(Vector v){
        double dx = x - v.x;
        double dy = y - v.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * Calculates the distance from a Vector to another Vector.
     * @param v1 the first Vector.
     * @param v2 the second Vector.
     * @return the distance between the Vectors.
     */
    public static double dist(Vector v1, Vector v2) {
        double dx = v1.x - v2.x;
        double dy = v1.y - v2.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * Calculates the dot-product between a Vector and the Vector.
     * @param v the other Vector.
     * @return the dot-product.
     */
    public double dot(Vector v) {
        return x*v.x + y*v.y;
    }

    /**
     * Calculates the dot-product between a Vector and the components.
     * @param x the x-component.
     * @param y the y-component.
     * @return the dot-product.
     */
    public double dot(double x, double y) {
        return this.x*x + this.y*y;
    }

    /**
     * Calculates the dot-product between a Vector and another Vector.
     * @param v1 the first Vector.
     * @param v2 the second Vector.
     * @return the dot-product.
     */
    public static double dot(Vector v1, Vector v2) {
        return v1.x*v2.x + v1.y*v2.y;
    }

    /**
     * Normalizes the vector, meaning the magnitude is now equal to 1.
     * @return the Vector after the normalization.
     */
    public Vector normalize(){
        double m = mag();
        if (m != 0 && m != 1) {
            div(m);
        }
        return this;
    }

    /**
     * Limits the magnitude of the Vector.
     * @param max the upper-limit of the magnitude.
     * @return the Vector after the limit is applied.
     */
    public Vector limit(double max) {
        if (magSq() > max*max) {
            normalize();
            mult(max);
        }
        return this;
    }

    /**
     * Sets the magnitude to a given size.
     * @param size the new magnitude value.
     * @return the Vector after setting the new magnitude.
     */
    public Vector setMag(double size) {
        normalize();
        mult(size);
        return this;
    }

    /**
     * Calculates the heading (in radians) of a Vector.
     * @return the heading (in radians).
     */
    public double heading() {
        return Math.atan2(y, x);
    }

    /**
     * Sets the heading (in radians) of a Vector.
     * @param angle the new heading angle (in radians).
     * @return the Vector after the new heading is applied.
     */
    public Vector setHeading(double angle) {
        double m = mag();
        x = m * Math.cos(angle);
        y = m * Math.sin(angle);
        return this;
    }

    /**
     * Rotates the heading of the Vector.
     * @param theta the angle (in radians) to rotate the heading.
     * @return the Vector after the rotation.
     */
    public Vector rotate(double theta) {
        double temp = x;
        x = x*Math.cos(theta) - y*Math.sin(theta);
        y = temp*Math.sin(theta) + y*Math.cos(theta);
        return this;
    }

    /**
     * Stringifies the Vector.
     * @return a string in the format "[x, y]".
     */
    @Override
    public String toString() {
        return "[ " + x + ", " + y + " ]";
    }

    /**
     * Tests equality based on x and y components.
     * @param obj the reference object with which to compare.
     * @return true if the other Vector has equal components, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector v)) {
            return false;
        }
        return x == v.x && y == v.y;
    }

}
