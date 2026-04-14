package io.asteroidsjaylib.common.util;

import com.raylib.Raylib.*;

public class Quaternion {

    public float x, y, z, w;

    public Quaternion(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 1;
    }

    public Quaternion(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Vector4 vector4){
        this(vector4.x(), vector4.y(), vector4.z(), vector4.w());
    }

    public Vector4 toVector4(Vector4 vector4){
        return vector4.x(x).y(y).z(z).w(w);
    }

    public Quaternion copy(){
        return new Quaternion(x, y, z, w);
    }

    public static Quaternion fromAxisAngle(Vector3D axis, float angle){
        Vector3D normAxis = axis.copy().normalize();
        float halfAngle = angle/2.0f;
        float sinHalf = (float) Math.sin(halfAngle);

        return new Quaternion(
            normAxis.x * sinHalf,
            normAxis.y * sinHalf,
            normAxis.z * sinHalf,
            (float) Math.cos(halfAngle)
        );
    }

    public Quaternion multiply(Quaternion other){
        float newX = this.w * other.x + this.x * other.w + this.y * other.z - this.z * other.y;
        float newY = this.w * other.y - this.x * other.z + this.y * other.w + this.z * other.x;
        float newZ = this.w * other.z + this.x * other.y - this.y * other.x + this.z * other.w;
        float newW = this.w * other.w - this.x * other.x - this.y * other.y - this.z * other.z;

        this.x = newX;
        this.y = newY;
        this.z = newZ;
        this.w = newW;
        return this;
    }

    public Vector3D rotateVector(Vector3D v){
        Vector3D qVec = new Vector3D(this.x, this.y, this.z);

        Vector3D t = qVec.cross(v).mult(2.0f);

        Vector3D part1 = v.copy();
        Vector3D part2 = t.copy().mult(this.w);
        Vector3D part3 = qVec.cross(t);

        return part1.add(part2).add(part3);
    }

    public Quaternion normalize() {
        float mag = (float) Math.sqrt(x*x + y*y + z*z + w*w);
        if (mag != 0) {
            this.x /= mag;
            this.y /= mag;
            this.z /= mag;
            this.w /= mag;
        }
        return this;
    }

    public float getAngleDegrees() {
        return (float) Math.toDegrees(2.0 * Math.acos(this.w));
    }

    public Vector3D getAxis() {
        float sinHalf = (float) Math.sqrt(1.0 - this.w * this.w);

        if(sinHalf < 0.001f){
            return new Vector3D(0, 0, 1);
        }

        return new Vector3D(this.x / sinHalf, this.y / sinHalf, this.z / Math.abs(sinHalf));

    }

}
