package com.thatcoolcoder.terminalRaycast;

import com.googlecode.lanterna.input.KeyStroke;

public class Player extends Viewpoint {
    private float maxSpeed = 2;
    private Vector3d velocity = new Vector3d(0, 0, 0);
    private float maxRotationSpeed = (float) (Math.PI / 3);
    private float rotationSpeed = 0;

    public Player() {

    }

    public void useInput(KeyStroke input) {
        if (input != null) {
            var character = input.getCharacter();
            // Movement
            if (character == 'w') velocity.y = maxSpeed;
            if (character == 'x') velocity.y = -maxSpeed;
            if (character == 's') {
                velocity.x = 0;
                velocity.y = 0;
            }
            if (character == 'a') velocity.x = maxSpeed;
            if (character == 'd') velocity.x = -maxSpeed;

            // Rotation
            if (character == 'j') rotationSpeed = -maxRotationSpeed;
            if (character == 'k') rotationSpeed = 0;
            if (character == 'l') rotationSpeed = maxRotationSpeed;
        }
    }

    public void move(float deltaTime) {
        position.add(Vector3d.mult(Vector3d.rotate(velocity, angle, true), deltaTime));
        angle += rotationSpeed * deltaTime;
    }
}