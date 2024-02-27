package src.main.java.org.example.Graph_Connectivity_Components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static final int max_n = (int) 1e5;

    public static int component_num = 0;

    public static int[] component = new int[max_n];

    public static List<List<Integer>> components = new ArrayList<>();

    public static void dfs(int v, int num, int[][] adj_matrix) {
        component[v] = num;



        for (int u = 0; u < adj_matrix.length; ++u) {
            if (adj_matrix[v][u] == 1 && component[u] == 0) {
                dfs(u, num, adj_matrix);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(new File("in.txt"));

        int matrix_rang = Integer.parseInt(scanner.nextLine());
        int[][] adjacent_matrix = new int[matrix_rang][matrix_rang];

        for (int i = 0; i < matrix_rang; i++) {
            String[] row = scanner.nextLine().split(" ");
            for (int j = 0; j < matrix_rang; j++) {
                adjacent_matrix[i][j] = Integer.parseInt(row[j]);
            }
        }

        for (int v = 0; v < matrix_rang; v++) {
            if (component[v] == 0) {
                List<Integer> currentComponent = new ArrayList<>();
                dfs(v, ++component_num, adjacent_matrix);
                for (int i = 0; i < matrix_rang; i++) {
                    if (component[i] == component_num) {
                        currentComponent.add(i);
                    }
                }
                components.add(currentComponent);
            }
        }


        StringBuilder ans = new StringBuilder();

        ans.append(component_num).append("\n");


        for (List<Integer> component : components) {
            for (int vertex : component) {
                ans.append(vertex + 1).append(" ");
            }
            ans.append("\n");
        }


        File output = new File("out.txt");
        try (FileWriter myWriter = new FileWriter(output)) {
            myWriter.write(ans.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
