package io.asteroidsjaylib.common.util;

import com.raylib.Raylib;

public class Vector3D {

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

    public Vector3D(Raylib.Vector3 vector3){
        this(vector3.x(), vector3.y(), vector3.z());
    }

    public Raylib.Vector3 toVector3(){
        return new Raylib.Vector3().x(x).y(y).z(z);
    }

    public Vector3D copy(){
        return new Vector3D(x, y, z);
    }

    public float mag(){
        return (float) Math.sqrt( (x*x) + (y*y) + (z*z) );
    }

    public float magSq(){
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

    public Vector3D sub(Vector3D other){
        return sub(other.x, other.y, other.z);
    }

    public Vector3D sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3D mult(float n){
        this.x *= n;
        this.y *= n;
        this.z *= n;
        return this;
    }

    public Vector3D div(float n){
        return mult(1/n);
    }

    public float dist(Vector3D other){
        float dx = x - other.x;
        float dy = y - other.y;
        float dz = z - other.z;
        return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    public static float dist(Vector3D v1, Vector3D v2) {
        float dx = v1.x - v2.x;
        float dy = v1.y - v2.y;
        float dz = v1.z - v2.z;
        return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    public static float distSq(Vector3D v1, Vector3D v2) {
        float dx = v1.x - v2.x;
        float dy = v1.y - v2.y;
        float dz = v1.z - v2.z;
        return dx*dx + dy*dy + dz*dz;
    }

    public float dot(Vector3D other) {
        return x*other.x + y*other.y + z*other.z;
    }

    public Vector3D normalize(){
        float m = mag();
        if (m != 0 && m != 1) {
            div(m);
        }
        return this;
    }

    public Vector3D limit(float max) {
        if (magSq() > max*max) {
            normalize().mult(max);
        }
        return this;
    }

    public Vector3D setMag(float size) {
        return normalize().mult(size);
    }

    public Vector3D cross(Vector3D other){
        float cx = this.y * other.z - this.z * other.y;
        float cy = this.z * other.x - this.x * other.z;
        float cz = this.x * other.y - this.y * other.x;
        return new Vector3D(cx, cy, cz);
    }

    @Override
    public String toString(){
        return "[ " + x + ", " + y + ", " + z + " ]";
    }

}
