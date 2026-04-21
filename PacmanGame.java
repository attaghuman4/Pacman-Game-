import java.util.ArrayList;
import java.util.Random;

public class PacmanGame {

    // Map
    private char[][] map;

    // Pacman
    private int pacX = 1, pacY = 1;
    private int dx = 1, dy = 0;

    // Ghosts
    private ArrayList<int[]> ghosts;

    private boolean gameOver = false;

    public PacmanGame() {
        loadMap();

        ghosts = new ArrayList<>();
        ghosts.add(new int[]{10, 10});
    }

    private void loadMap() {
        map = new char[][]{
                "#####################".toCharArray(),
                "#........#.........#".toCharArray(),
                "#.###.###.#.###.###.#".toCharArray(),
                "#...................#".toCharArray(),
                "#.###.#.#####.#.###.#".toCharArray(),
                "#.....#...#...#.....#".toCharArray(),
                "#####.###.#.###.#####".toCharArray(),
                "#.................#".toCharArray(),
                "#.###.#####.###.#.#".toCharArray(),
                "#.....#...#.....#.#".toCharArray(),
                "#####.#.#.#.#####.#".toCharArray(),
                "#.........#.......#".toCharArray(),
                "#.###.###.#.###.###".toCharArray(),
                "#...#.....#.....#.#".toCharArray(),
                "###.#.#########.#.#".toCharArray(),
                "#.....#...#...#...#".toCharArray(),
                "#.#####.#.#.#.#####".toCharArray(),
                "#.................#".toCharArray(),
                "#####################".toCharArray(),
        };
    }

    // Game update
    public void update() {
        if (!gameOver) {
            movePacman();
            moveGhosts();
            checkCollision();

        }
    }

    private boolean dotsRemaining() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '.') {
                    return true;
                }
            }
        }
        return false;
    }

    private void movePacman() {
        int nextX = pacX + dx;
        int nextY = pacY + dy;

        if (nextY >= 0 && nextY < map.length &&
                nextX >= 0 && nextX < map[0].length &&
                map[nextY][nextX] != '#') {

            pacX = nextX;
            pacY = nextY;

            if (map[pacY][pacX] == '.') {
                map[pacY][pacX] = ' ';
            }
        }
    }

    private void moveGhosts() {
        for (int[] ghost : ghosts) {
            if (ghost[0] < pacX) ghost[0]++;
            else if (ghost[0] > pacX) ghost[0]--;

            if (ghost[1] < pacY) ghost[1]++;
            else if (ghost[1] > pacY) ghost[1]--;
        }
    }

    private void checkCollision() {
        for (int[] ghost : ghosts) {
            if (ghost[0] == pacX && ghost[1] == pacY) {
                gameOver = true;
            }
        }

    }

    // Print game to console
    public void printGame() {
        char[][] display = new char[map.length][map[0].length];

        // Copy map
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                display[i][j] = map[i][j];
            }
        }

        // Place Pacman
        display[pacY][pacX] = 'P';

        // Place Ghosts
        for (int[] ghost : ghosts) {
            display[ghost[1]][ghost[0]] = 'G';
        }

        // Print
        for (int i = 0; i < display.length; i++) {
            for (int j = 0; j < display[i].length; j++) {
                System.out.print(display[i][j]);
            }
            System.out.println();
        }

        System.out.println("----------------------");
    }

    public boolean isGameOver() {
        return gameOver;
    }

    // MAIN METHOD
    public static void main(String[] args) throws InterruptedException {

        PacmanGame game = new PacmanGame();

        int steps = 0;

        while (!game.isGameOver() && steps < 50) {

            game.update();
            game.printGame();

            Thread.sleep(300);
            steps++;
        }

        System.out.println("Game Ended!");
    }
}