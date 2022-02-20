package com.thatcoolcoder.terminalRaycast;

import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Main {
    public static void main(String[] args) throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);

        try {
            var _walls = new ArrayList<Wall>();
            _walls.add(new Wall() {{
                start = new Vector3d(-1, 5, 0);
                end = new Vector3d(1, 5, 0);
                height = 0.5f;
            }});
            _walls.add(new Wall() {{
                start = new Vector3d(-1, 5, 0);
                end = new Vector3d(-1, 0, 0);
                height = 0.5f;
            }});
            _walls.add(new Wall() {{
                start = new Vector3d(1, 5, 0);
                end = new Vector3d(1, 0, 0);
                height = 0.5f;
            }});

            var world = new World() {{
                walls = _walls;
                viewpoint = new Viewpoint() {{
                    position = new Vector3d();
                    fieldOfView = (float) (Math.PI / 2);
                    angle = 0;
                    viewDistance = 20;
                }};
            }};

            var canvas = new RenderCanvas(screen, world, screen.getTerminalSize());
            screen.startScreen();
            canvas.render();
            screen.readInput();
        }
        catch (IOException e) {
            System.out.println("Hmm, failed to do things");
        }

        screen.close();
    }
}
