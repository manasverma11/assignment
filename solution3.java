import java.util.*;

class ChessGame {
    private static final int BOARD_SIZE = 10;
    private static final char EMPTY = '.';
    private static final char SOLDIER = 'S';
    private static final char CASTLE = 'C';

    private char[][] board;
    private List<Position> soldiers;
    private Position castle;
    private Set<String> uniquePaths;

    public ChessGame() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        soldiers = new ArrayList<>();
        uniquePaths = new HashSet<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            Arrays.fill(board[i], EMPTY);
        }
    }

    public void placeSoldiers(int count) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < count; i++) {
            System.out.print("Enter coordinates for soldier " + (i + 1) + ": ");
            String input = scanner.nextLine();
            String[] parts = input.split(",");
            int x = Integer.parseInt(parts[0].trim()) - 1;
            int y = Integer.parseInt(parts[1].trim()) - 1;
            if (isValidPosition(x, y) && board[x][y] == EMPTY) {
                board[x][y] = SOLDIER;
                soldiers.add(new Position(x, y));
            } else {
                System.out.println("Invalid position or cell already occupied. Try again.");
                i--;
            }
        }
    }

    public void placeCastle() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the coordinates for your 'special' castle: ");
        String input = scanner.nextLine();
        String[] parts = input.split(",");
        int x = Integer.parseInt(parts[0].trim()) - 1;
        int y = Integer.parseInt(parts[1].trim()) - 1;
        if (isValidPosition(x, y) && board[x][y] == EMPTY) {
            board[x][y] = CASTLE;
            castle = new Position(x, y);
        } else {
            System.out.println("Invalid position or cell already occupied.");
        }
    }

    public void findPaths() {
        List<List<String>> paths = new ArrayList<>();
        explorePaths(castle.x, castle.y, new ArrayList<>(), paths);
        System.out.println("Thanks. There are " + paths.size() + " unique paths for your 'special_castle'");
        for (int i = 0; i < paths.size(); i++) {
            System.out.println("Path " + (i + 1));
            for (String step : paths.get(i)) {
                System.out.println(step);
            }
            System.out.println("Arrive (" + (castle.x + 1) + "," + (castle.y + 1) + ")");
        }
    }

    private void explorePaths(int x, int y, List<String> currentPath, List<List<String>> paths) {
        if (currentPath.isEmpty()) {
            currentPath.add("Start (" + (x + 1) + "," + (y + 1) + ")");
        }

        boolean moved = false;

        for (int i = x + 1; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == SOLDIER) {
                    currentPath.add("Kill (" + (i + 1) + "," + (j + 1) + "). Turn Left");
                    board[i][j] = EMPTY; // Remove soldier
                    explorePaths(i, j, currentPath, paths);
                    board[i][j] = SOLDIER; // Restore soldier
                    currentPath.remove(currentPath.size() - 1);
                    moved = true;
                }
            }
            if (!moved && board[i][y] == EMPTY) {
                currentPath.add("Jump (" + (i + 1) + "," + (y + 1) + ")");
                explorePaths(i, y, currentPath, paths);
                currentPath.remove(currentPath.size() - 1);
                moved = true;
            }
        }

        if (!moved) {
            String pathString = String.join(" -> ", currentPath);
            if (!uniquePaths.contains(pathString)) {
                uniquePaths.add(pathString);
                paths.add(new ArrayList<>(currentPath));
            }
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        Scanner scanner = new Scanner(System.in);
        System.out.print(" find_my_home_castle -soldiers ");
        int soldierCount = scanner.nextInt();
        scanner.nextLine();
        game.placeSoldiers(soldierCount);
        game.placeCastle();
        game.findPaths();
    }

    private static class Position {
        int x, y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
