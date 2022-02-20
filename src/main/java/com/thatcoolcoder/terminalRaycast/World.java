package com.thatcoolcoder.terminalRaycast;

import java.util.ArrayList;

public class World {
    public ArrayList<Wall> walls;
    public Viewpoint viewpoint;

    public World() {
        
    }

    public World(ArrayList<Wall> _walls, Viewpoint _viewpoint) {
        walls = _walls;
        viewpoint = _viewpoint;
    }
}
