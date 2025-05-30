
// Puzzle8Runner.java
import java.util.*;

public class Puzzle8Runner {

    static class Node {
        String state;
        Node parent;
        int depth;

        Node(String state, Node parent, int depth) {
            this.state = state;
            this.parent = parent;
            this.depth = depth;
        }
    }

    static List<String> getNeighbors(String state) {
        List<String> neighbors = new ArrayList<>();
        int zeroIndex = state.indexOf('0');
        int row = zeroIndex / 3;
        int col = zeroIndex % 3;

        int[][] moves = {
            {row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}
        };

        for (int[] move : moves) {
            int newRow = move[0], newCol = move[1];
            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                int newIndex = newRow * 3 + newCol;
                char[] chars = state.toCharArray();
                chars[zeroIndex] = chars[newIndex];
                chars[newIndex] = '0';
                neighbors.add(new String(chars));
            }
        }

        return neighbors;
    }

    public static void solve(String startState, String goalState, int scenario) {
        long startTime = System.currentTimeMillis();

        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new Node(startState, null, 0));
        visited.add(startState);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.state.equals(goalState)) {
                long endTime = System.currentTimeMillis();
                System.out.printf("Escenario %d | Tiempo: %d ms | Nodos: %d | Movimientos: %d\n",
                    scenario, (endTime - startTime), visited.size(), current.depth);
                return;
            }

            for (String neighbor : getNeighbors(current.state)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(new Node(neighbor, current, current.depth + 1));
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.printf("Escenario %d | Tiempo: %d ms | Nodos: %d | Movimientos: NA\n",
            scenario, (endTime - startTime), visited.size());
    }

    public static void main(String[] args) {
        String goal = "123456780";
        String[] startStates = {
            "413725086", "431568702", "310468572", "287364501", "867254301",
            "3210", "123456870", "0234157896ABCD EF".replace(" ", "")
        };

        for (int i = 0; i < startStates.length; i++) {
            if (startStates[i].length() != 9) {
                System.out.printf("Escenario %d | No soportado en esta implementación (no 3x3)\n", i + 1);
            } else {
                solve(startStates[i], goal, i + 1);
            }
        }
    }
}
