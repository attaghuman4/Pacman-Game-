import java.util.ArrayList;
import java.util.Scanner;

public class PacmanGame {

    // Map dimensions: 19 rows (y) x 21 columns (x)
    private char[][] map;
    private int pacX = 1, pacY = 1;
    private int dx = 0, dy = 0;
    private ArrayList<int[]> ghosts;
    private boolean gameOver = false;

    public PacmanGame() {
        initMap();
        ghosts = new ArrayList<>();
        // Start ghosts in the bottom right area, but safely inside walls
        ghosts.add(new int[]{18, 17});
    }

    private void initMap() {
        // map[row][col] -> map[y][x]
        map = new char[][]{
                "#####################".toCharArray(),
                "#...................#".toCharArray(),
                "#...................#".toCharArray(),
                "#...................#".toCharArray(),
                "#...................#".toCharArray(),
                "#.....#...#...#.....#".toCharArray(),
                "#...................#".toCharArray(),
                "#...................#".toCharArray(),
                "#...................#".toCharArray(),
                "#.....#...#.......#.#".toCharArray(),
                "#...................#".toCharArray(),
                "#.........#.........#".toCharArray(),
                "#...................#".toCharArray(),
                "#...#.....#.....#...#".toCharArray(),
                "#...................#".toCharArray(),
                "#.....#...#...#.....#".toCharArray(),
                "#...................#".toCharArray(),
                "#...................#".toCharArray(),
                "#####################".toCharArray(),
        };
    }

    // Helper to keep us from crashing or walking through walls
    private boolean canMoveTo(int x, int y) {
        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            return false;
        }
        return map[y][x] != '#';
    }

    public void update() {
        if (gameOver) return;

        // 1. Move Pacman
        int nextPacX = pacX + dx;
        int nextPacY = pacY + dy;

        if (canMoveTo(nextPacX, nextPacY)) {
            pacX = nextPacX;
            pacY = nextPacY;
            // Eat the dot
            if (map[pacY][pacX] == '.') {
                map[pacY][pacX] = ' ';
            }
        }

        // 2. Move Ghosts
        for (int[] ghost : ghosts) {
            int gx = ghost[0];
            int gy = ghost[1];

            // Decide direction (Basic tracking)
            int stepX = (pacX > gx) ? 1 : (pacX < gx ? -1 : 0);
            int stepY = (pacY > gy) ? 1 : (pacY < gy ? -1 : 0);

            // Try moving horizontally first
            if (stepX != 0 && canMoveTo(gx + stepX, gy)) {
                ghost[0] += stepX;
            }
            // If blocked or no X move needed, try vertically
            else if (stepY != 0 && canMoveTo(gx, gy + stepY)) {
                ghost[1] += stepY;
            }
        }

        // 3. Collision Check
        for (int[] ghost : ghosts) {
            if (ghost[0] == pacX && ghost[1] == pacY) {
                gameOver = true;
            }
        }
    }

    public void draw() {
        // Print some empty lines to "clear" the console
        for(int i = 0; i < 10; i++) System.out.println();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                // Check if ghost is here
                boolean isGhost = false;
                for (int[] g : ghosts) {
                    if (g[0] == x && g[1] == y) isGhost = true;
                }

                if (x == pacX && y == pacY) System.out.print('P');
                else if (isGhost) System.out.print('G');
                else System.out.print(map[y][x]);
            }
            System.out.println();
        }
        System.out.println("Use W/A/S/D + Enter to move!");
    }

    public static void main(String[] args) {
        PacmanGame game = new PacmanGame();
        Scanner input = new Scanner(System.in);

        while (!game.gameOver) {
            game.draw();
            System.out.print("> ");
            String move = input.nextLine().toLowerCase();

            if (move.length() > 0) {
                char c = move.charAt(0);
                if (c == 'w') { game.dx = 0;  game.dy = -1; }
                else if (c == 's') { game.dx = 0;  game.dy = 1;  }
                else if (c == 'a') { game.dx = -1; game.dy = 0;  }
                else if (c == 'd') { game.dx = 1;  game.dy = 0;  }
            }

            game.update();
        }

        game.draw();
        System.out.println("GAME OVER - The ghost caught you!");
        input.close();
    }
}
