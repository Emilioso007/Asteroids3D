package io.asteroidsjaylib.common.util;

import com.raylib.Vector3;

import java.util.Random;

public class Vector3D {

    private static final Random random = new Random();
    public float x, y, z;

    public Vector3D(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3D(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Vector3 vector3){
        this(vector3.x(), vector3.y(), vector3.z());
    }

    public static Vector3D random() {
        float theta = random.nextFloat(0, (float) (2 * Math.PI));
        float z = random.nextFloat(-1, 1);

        float r = (float) Math.sqrt(1 - z * z);

        float x = r * (float) Math.cos(theta);
        float y = r * (float) Math.sin(theta);

        return new Vector3D(x, y, z);
    }

    public Vector3D set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3 toVector3(Vector3 outNativeVector){
        return outNativeVector.x(x).y(y).z(z);
    }

    public Vector3D copy(){
        return new Vector3D(x, y, z);
    }

    public float magnitude(){
        return (float) Math.sqrt( (x*x) + (y*y) + (z*z) );
    }

    public float magnitudeSquared(){
        return (x*x) + (y*y) + (z*z);
    }

    public Vector3D add(Vector3D other){
        return add(other.x, other.y, other.z);
    }

    public Vector3D add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3D subtract(Vector3D other){
        return subtract(other.x, other.y, other.z);
    }

    public Vector3D subtract(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3D multiply(float n){
        this.x *= n;
        this.y *= n;
        this.z *= n;
        return this;
    }

    public Vector3D divide(float n){
        return multiply(1/n);
    }

    public float distance(Vector3D other){
        float dx = x - other.x;
        float dy = y - other.y;
        float dz = z - other.z;
        return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    public static float distance(Vector3D v1, Vector3D v2) {
        float dx = v1.x - v2.x;
        float dy = v1.y - v2.y;
        float dz = v1.z - v2.z;
        return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    public static float distanceSquared(Vector3D v1, Vector3D v2) {
        float dx = v1.x - v2.x;
        float dy = v1.y - v2.y;
        float dz = v1.z - v2.z;
        return dx*dx + dy*dy + dz*dz;
    }

    public float dot(Vector3D other) {
        return x*other.x + y*other.y + z*other.z;
    }

    public Vector3D normalize(){
        float m = magnitude();
        if (m != 0 && m != 1) {
            divide(m);
        }
        return this;
    }

    public Vector3D limit(float max) {
        if (magnitudeSquared() > max*max) {
            normalize().multiply(max);
        }
        return this;
    }

    public Vector3D magnitude(float size) {
        return normalize().multiply(size);
    }

    public Vector3D cross(Vector3D other){
        float cx = this.y * other.z - this.z * other.y;
        float cy = this.z * other.x - this.x * other.z;
        float cz = this.x * other.y - this.y * other.x;
        return new Vector3D(cx, cy, cz);
    }

    public Vector3D addScaled(Vector3D other, float scale) {
        this.x += other.x * scale;
        this.y += other.y * scale;
        this.z += other.z * scale;
        return this;
    }

    @Override
    public String toString(){
        return "[ " + x + ", " + y + ", " + z + " ]";
    }

}
