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
    private static float frameRate = 1f / 30f;

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

            var player = new Player() {{
                position = new Vector3d();
                fieldOfView = (float) (Math.PI / 2);
                angle = 0;
                viewDistance = 20;
            }};

            var world = new World() {{
                walls = _walls;
                viewpoint = player;
            }};

            var canvas = new RenderCanvas(screen, world, screen.getTerminalSize());
            screen.startScreen();
            play(canvas, screen, player);
        }
        catch (IOException e) {
            System.out.println("Your terminal sucks");
        }

        screen.close();
    }

    private static void play(RenderCanvas canvas, Screen screen, Player player) throws IOException {
        while (true) {
            var input = screen.pollInput();
            if (input != null && input.getCharacter() == 'q') break;
            player.useInput(input);
            player.move(frameRate);
            
            canvas.render();

            try
            {
                Thread.sleep((long) (frameRate * 1000));
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
    }
}
