import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class SnakeGame {
    int foodIndex;
    Deque<int[]> snack;
    Set<int[]> died;
    int width = 0;
    int height = 0;
    int[][] food;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        foodIndex = 0;
        snack = new ArrayDeque<>();
        snack.add(new int[]{0, 0});
        died = new HashSet<>();
        died.add(snack.getFirst());
    }
    /** Moves the snake.
     @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     @return The game's score after the move. Return -1 if game over.
     Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        int[] current = snack.getFirst();
        int[] next;
        switch (direction) {
            case "U" : next = new int[]{current[0] - 1, current[1]};
                break;
            case "L" : next = new int[]{current[0], current[1] - 1};
                break;
            case "R" : next = new int[]{current[0], current[1] + 1};
                break;
            default : next = new int[]{current[0] + 1, current[1]};
                break;
        }
        if (next[0] < 0 || next[1] < 0 || next[0] >= height || next[1] >= width) {
            return -1;
        }
        int[] tail = snack.removeLast();
        died.remove(tail);
        if (died.contains(next)) {
            return -1;
        }
        died.add(next);
        snack.addFirst(next);
        //see whether there is food
        if (next[0] == food[foodIndex][0] && next[1] == food[foodIndex][1]) {
            foodIndex++;
            died.add(tail);
            snack.addLast(tail);
        }
        return foodIndex;
    }

    static int sum = 0;
    public static int cal(int n) {
        int temp = 0;
        helper(n, temp);
        return sum;
    }
    private static void helper(int n, int temp) {
        if (temp >= n) {
            return;
        }
        if (temp % 3 == 0 || temp % 5 == 0) {
            sum += temp;
        }
        helper(n, temp + 1);
    }

    public static String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            sb.insert(0, (char) ('A' + (n % 27) - 1));
            n = n / 27;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SnakeGame s = new SnakeGame(3,3,new int[][]{{2,0},{0,0},{0,2},{0,1},{2,2},{0,1}});
        System.out.println(s.move("D"));
        System.out.println(s.move("D"));
        System.out.println(s.move("R"));
        System.out.println(s.move("U"));
        System.out.println(s.move("U"));
        System.out.println(s.move("L"));
        System.out.println(s.move("D"));
        System.out.println(s.move("R"));
        System.out.println(s.move("R"));
        System.out.println(s.move("U"));
        System.out.println(s.move("L"));
        System.out.println(s.move("L"));
        System.out.println(s.move("D"));
        System.out.println(s.move("R"));
        System.out.println(s.move("U"));

        System.out.println(cal(11));
        char c = 'A' + 1 - 2;
        System.out.println(c);

        System.out.println(convertToTitle(26));
    }
}
