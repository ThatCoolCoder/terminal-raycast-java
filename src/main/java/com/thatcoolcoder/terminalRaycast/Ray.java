package com.thatcoolcoder.terminalRaycast;

import com.thatcoolcoder.terminalRaycast.Vector3d;

public class Ray {
    public Vector3d start;
    public Vector3d end;

    public Ray() {
        start = new Vector3d();
        end = new Vector3d();
    }

    public Ray(Vector3d _start, Vector3d _end) {
        start = _start;
        end = _end;
    }

    /**
     * Copy constructor
     * @param ray
     */
    public Ray(Ray ray) {
        start = new Vector3d(ray.start);
        end = new Vector3d(ray.end);
    }

    /**
     * Heading of this 
     * @param radians
     * @return
     */
    public float heading(boolean radians) {
        return Vector3d.sub(end, start).heading(radians);
    }

    /**
     * Rotate this around start by a certain amount
     * @param angle
     * @param radians
     */
    public void rotate(float angle, boolean radians) {
        Vector3d delta = Vector3d.sub(end, start);
        delta.rotate(angle, radians);
        end = Vector3d.add(start, delta);
    }

    /**
     * Find intersection point of this and another ray.
     * Returns null if no intersection
     * @param ray
     * @return
     */
    public Vector3d intersection(Ray ray) {
        Vector3d intersection = null;

        float den = (start.x - end.x) * (ray.start.y - ray.end.y) - 
            (start.y - end.y) * (ray.start.x - ray.end.x);

        // if lines intersect (simple check)
        if (den != 0)
        {
            float t = (start.x - ray.start.x) * (ray.start.y - ray.end.y) -
                (start.y - ray.start.y) * (ray.start.x - ray.end.x);
            t /= den;
            float u = -((start.x - end.x) * (start.y - ray.start.y) -
                (start.y - end.y) * (start.x - ray.start.x));
            u /= den;
            // if lines intersect (cpu intensive check)
            if (t > 0 && t < 1 && u > 0)
            {
                intersection = new Vector3d(
                    start.x + t * (end.x - start.x),
                    start.y + t * (end.y - start.y),
                    0
                );
            }
        }

        return intersection;
    }
}
