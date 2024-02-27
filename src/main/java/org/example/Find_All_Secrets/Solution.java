package src.main.java.org.example.Find_All_Secrets;

import java.util.*;

class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        int[] component = new int[n];
        int[] visit_time = new int[n];

        int component_num = 0;

        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        Arrays.fill(visit_time, 100001);
        visit_time[firstPerson] = 0;
        visit_time[0] = 0;

        for (int i = 0; i < meetings.length; i++) {
            int[] curr_vertex = new int[2];
            curr_vertex[0] = meetings[i][1];
            curr_vertex[1] = meetings[i][2];
            graph[meetings[i][0]].add(curr_vertex);

            int[] curr_vertex_rev = new int[2];
            curr_vertex_rev[0] = meetings[i][0];
            curr_vertex_rev[1] = meetings[i][2];
            graph[meetings[i][1]].add(curr_vertex_rev);
        }

        List<Integer> currentComponent = new ArrayList<>();

//        dfs(0, 666, graph, component, visit_time);
//        for (int i = 0; i < n; i++) {
//            if (component[i] == 666 && !currentComponent.contains(i)) {
//                currentComponent.add(i);
//            }
//        }
//
//        dfs(firstPerson, 777, graph, component, visit_time);
//        for (int i = 0; i < n; i++) {
//            if (component[i] == 777 && !currentComponent.contains(i)) {
//                currentComponent.add(i);
//            }
//        }



        for (int v = 0; v < meetings.length; v++) {
            if (component[v] == 0) {
                dfs(v, ++component_num, graph, component, visit_time);
                for (int i = 0; i < n; i++) {
                    if ((component[i] == 1 || component[i] == 2) && !currentComponent.contains(i))  {
                        currentComponent.add(i);
                    }
                }
            }
        }

        Collections.sort(currentComponent);
        return currentComponent;
    }

    private static void dfs(int v, int num, List<int[]>[] adj_arr, int[] component, int[] visit_time) {
        component[v] = num;
        int curr_time = visit_time[v];
        for (int u = 0; u < adj_arr[v].size(); ++u) {
            int next_vertex = adj_arr[v].get(u)[0];
            int next_time = adj_arr[v].get(u)[1];

            if (next_time >= curr_time && component[next_vertex] == 0) {
                visit_time[next_vertex] = next_time;
                dfs(next_vertex, num, adj_arr, component, visit_time);
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int n = 6;
        int[][] meetings = {{0, 2, 1}, {1, 3, 1}, {4, 5, 1}};
        int firstPerson = 1;

        List<Integer> result = solution.findAllPeople(n, meetings, firstPerson);

        System.out.println(result);
    }
}