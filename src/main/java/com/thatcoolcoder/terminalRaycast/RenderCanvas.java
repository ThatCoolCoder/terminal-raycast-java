package com.thatcoolcoder.terminalRaycast;

import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class RenderCanvas {
    public TerminalSize terminalSize;
    World world;
    TextGraphics textGraphics;
    Screen screen;

    public RenderCanvas(Screen _screen, World _world, TerminalSize _terminalSize) {
        screen = _screen;
        world = _world;
        terminalSize = _terminalSize;

        textGraphics = screen.newTextGraphics();
    }
    
    public void render() throws IOException {
        var rays = createRays(terminalSize.getColumns());
        var intersections = findIntersections(rays);
        draw(intersections);
    }

    private ArrayList<Ray> createRays(int rayAmount) {
        var rays = new ArrayList<Ray>();

        float angleBetweenRays = world.viewpoint.fieldOfView / rayAmount;
        float crntAngle = world.viewpoint.angle - world.viewpoint.fieldOfView / 2;

        for (int i = 0; i < rayAmount; i ++) {
            var endPosition = new Vector3d(0, world.viewpoint.viewDistance, 0);
            endPosition.rotate(crntAngle, true);
            endPosition.add(world.viewpoint.position);
            rays.add(new Ray(new Vector3d(world.viewpoint.position), endPosition));
            crntAngle += angleBetweenRays;
        }

        return rays;
    }

    private ArrayList<Intersection> findIntersections(ArrayList<Ray> rays) {
        var intersections = new ArrayList<Intersection>();

        for (int rayNumber = 0; rayNumber < rays.size(); rayNumber ++) {
            var ray = rays.get(rayNumber);

            float closestIntersectionDist = Float.MAX_VALUE;
            Wall closestWall = null;
            for (var wall : world.walls) {
                var intersectionPos = wall.intersection(ray);
                if (intersectionPos == null) continue;
                var distance = ray.start.dist(intersectionPos);
                if (distance < closestIntersectionDist) {
                    closestIntersectionDist = distance;
                    closestWall = wall;
                }
            }
            if (closestWall != null) {
                intersections.add(new Intersection(
                    closestIntersectionDist,
                    closestWall,
                    rayNumber
                ));
            }
        }

        return intersections;
    }

    private void draw(ArrayList<Intersection> intersections) throws IOException {
        screen.clear();
        float verticalFieldOfView = (float) terminalSize.getRows() / (float) terminalSize.getColumns() *
            world.viewpoint.fieldOfView;
        for (var intersection : intersections) {
            float angle = (float) Math.atan(intersection.wall.height / intersection.distance);
            int columnHeight = (int) (angle / verticalFieldOfView * terminalSize.getRows());
                
            textGraphics.drawRectangle(
                new TerminalPosition(intersection.rayNumber,
                    terminalSize.getRows() / 2 - columnHeight / 2),
                new TerminalSize(1, columnHeight), '#');
        }
        screen.refresh();
    }
}

class Intersection {
    public float distance;
    public Wall wall;
    public int rayNumber;

    public Intersection(float _distance, Wall _wall, int _rayNumber) {
        distance = _distance;
        wall = _wall;
        rayNumber = _rayNumber;
    }
}