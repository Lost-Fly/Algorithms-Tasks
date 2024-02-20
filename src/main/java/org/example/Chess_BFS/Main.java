import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


public class Main {

    private static int N = 12;

    private static int[] convertChessNotationToCoordinates(String chessNotation) {
        return new int[]{chessNotation.charAt(0) - 'a' + 2, Character.getNumericValue(chessNotation.charAt(1)) + 1};
    }

    private static String convertCoordinatesToChessNotation(int[] coordinates) {
        return String.valueOf((char) ('a' + coordinates[0] - 2)) + (char) (coordinates[1] + '1' - 2);
    }

    public static void main(String[] args) throws IOException {


        int[][] chessBoard = new int[N][N];
        Cell[][] prev = new Cell[N][N];

        Scanner scanner = new Scanner(new File("in.txt"));

        String horse_pos = scanner.nextLine();
        String pawn_pos = scanner.nextLine();

        int[] horse_position = convertChessNotationToCoordinates(horse_pos);
        int[] pawn_position = convertChessNotationToCoordinates(pawn_pos);

        int[][] forbidden_pos = {
                {pawn_position[0] - 1, pawn_position[1] + 1},
                {pawn_position[0] + 1, pawn_position[1] + 1},
                {pawn_position[0] - 1, pawn_position[1] - 1},
                {pawn_position[0] + 1, pawn_position[1] - 1}
        };

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int[] currentPos = {i, j};
                prev[i][j] = null;
                if (2 <= i && i <= 9 && 2 <= j && j <= 9 &&
                        !Arrays.equals(currentPos, forbidden_pos[0]) &&
                        !Arrays.equals(currentPos, forbidden_pos[1]) &&
                        !Arrays.equals(currentPos, forbidden_pos[2]) &&
                        !Arrays.equals(currentPos, forbidden_pos[3])) {
                    chessBoard[i][j] = -1;
                } else {
                    chessBoard[i][j] = 0;
                }
            }
        }

        Cell start = new Cell(horse_position[0], horse_position[1]);
        Cell end = new Cell(pawn_position[0], pawn_position[1]);

        bfs(start, end, chessBoard, prev);

        printPath(start, end, prev);

    }

    static class Cell {
        int x, y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static void bfs(Cell s, Cell t, int[][] d, Cell[][] prev) {
        Queue<Cell> q = new LinkedList<>();
        q.add(s);

        d[s.x][s.y] = 0;

        while (!q.isEmpty()) {
            Cell currentCell = q.poll();
            int x = currentCell.x;
            int y = currentCell.y;

            int[] dx = {-1, 1, 2, 2, 1, -1, -2, -2};
            int[] dy = {2, 2, 1, -1, -2, -2, -1, 1};
            for (int k = 0; k < 8; k++) {
                int _x = x + dx[k];
                int _y = y + dy[k];

                if (_x >= 0 && _x < N && _y >= 0 && _y < N && d[_x][_y] == -1) {
                    d[_x][_y] = d[x][y] + 1;
                    prev[_x][_y] = currentCell;
                    q.add(new Cell(_x, _y));
                }
            }
        }
    }

    static void printPath(Cell s, Cell t, Cell[][] prev) {

        LinkedList<Cell> path = new LinkedList<>();
        for (Cell at = t; at != null; at = prev[at.x][at.y]) {
            path.addFirst(at);
        }

        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < path.size(); i++) {
            Cell cell = path.get(i);
            int[] crds = new int[]{cell.x, cell.y};
            ans.append(convertCoordinatesToChessNotation(crds));

            if (i < path.size() - 1) {
                ans.append("\n");
            }
        }

        File output = new File("out.txt");
        try (FileWriter myWriter = new FileWriter("out.txt")) {
            myWriter.write(ans.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
