
// Puzzle8_Medicion.java
import java.util.*;

public class Puzzle8 {

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

    public static void solve(String startState, String goalState) {
        long startTime = System.currentTimeMillis();

        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new Node(startState, null, 0));
        visited.add(startState);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.state.equals(goalState)) {
                long endTime = System.currentTimeMillis();
                System.out.println("Movimientos: " + current.depth);
                System.out.println("Nodos en memoria: " + visited.size());
                System.out.println("Tiempo: " + (endTime - startTime) + " ms");
                return;
            }

            for (String neighbor : getNeighbors(current.state)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(new Node(neighbor, current, current.depth + 1));
                }
            }
        }

        System.out.println("No se encontró solución.");
    }

    public static void main(String[] args) {
        String start = "413725086"; // Escenario 1
        String goal = "123456780";
        solve(start, goal);
    }
}
