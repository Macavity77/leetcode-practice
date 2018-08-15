import sun.jvm.hotspot.utilities.Interval;

import java.util.*;

public class solution {
    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        //cut the list into half
        pre.next = null;
        //cut
        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);

        ListNode p = merge(l1, l2);
        return p;
    }

    private static ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode l = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                l.next = l1;
                l1 = l1.next;
            } else {
                l.next = l2;
                l2 = l2.next;
            }
            l = l.next;
        }

        if (l1 != null) {
            l.next = l1;
        } else {
            l.next = l2;
        }

        return dummy.next;
    }

    public static String reverseWords(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        char[] a = s.toCharArray();
        int n = a.length;
        reverse(a, 0, n - 1);
        reverseWord(a, n);
        return removespace(a, n);
    }

    private static void reverse(char[] a, int start, int end) {
        while (start < end) {
            char temp = a[start];
            a[start++] = a[end];
            a[end--] = temp;
        }
    }

    private static void reverseWord(char[] a, int length) {
        int pre = 0;
        int end = 0;
        while (pre < length) {
            while (pre < length && (pre < end || a[pre] == ' ')) {
                pre++;
            }
            while (end < length && (end < pre || a[end] != ' ')) {
                end++;
            }
            reverse(a, pre, end - 1);
        }
    }

    private static String removespace(char[] a, int length) {
        int pre = 0;
        int end = 0;
        while (end < length) {
            //remove space
            while (end < length && a[end] == ' ') {
                end++;
            }
            while (end < length && a[end] != ' ') {
                a[pre++] = a[end++];
            }
            //remove space
            while (end < length && a[end] == ' ') {
                end++;
            }
            if (end < length) {
                a[pre++] = ' ';
            }
        }
        return new String(a).substring(0, pre);
    }

    public static int maxProduct(int[] nums) {
        int maxhere = nums[0];
        int minhere = nums[0];
        int result = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < 0) {
                int temp = maxhere;
                maxhere = minhere;
                minhere = temp;
            }

            maxhere = Math.max(maxhere, maxhere * nums[i]);
            minhere = Math.min(minhere, minhere * nums[i]);

            result = Math.max(result, maxhere);
        }

        return result;
    }

    public static int numIslands(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int row = grid.length;
        int column = grid[0].length;

        UnionFind f = new UnionFind(row * column);
        int total = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (grid[i][j] == 1) {
                    total++;
                }
            }
        }
        f.set_count(total);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (grid[i][j] == 1) {
                    if (i > 0 && grid[i - 1][j] == 1) {
                        f.connect(i * column + j, (i - 1) * column + j);
                    }
                    if (i < row - 1 && grid[i + 1][j] == 1) {
                        f.connect(i * column + j, (i + 1) * column + j);
                    }
                    if (j > 0 && grid[i][j - 1] == 1) {
                        f.connect(i * column + j, i * column + j - 1);
                    }
                    if (j < row - 1 && grid[i][j + 1] == 1) {
                        f.connect(i * column + j, i * column + j + 1);
                    }
                }
            }
        }
        return f.query();
    }

    public static int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length == 0 || k == 0) {
            return 0;
        }
        int start = prices[0];
        int end = 0;
        int result = 0;
        Queue<Integer> pq = new PriorityQueue<>(k, Collections.reverseOrder());
        for (int i = 1; i < prices.length; i++) {
            if (i != prices.length - 1 && (prices[i] < prices[i - 1] && prices[i] < prices[i + 1])) {
                start = prices[i];
            }
            if (i != prices.length - 1 && (prices[i] > prices[i - 1] && prices[i] > prices[i + 1])) {
                end = prices[i];
                pq.offer(end - start);
                continue;
            }
            if (i == prices.length - 1 && prices[i] > prices[i - 1]) {
                end = prices[i];
                pq.offer(end - start);
            }
        }
        while (k > 0 && pq.size() > 0) {
            result += pq.poll();
        }
        return result;
    }

    public static int numDecodings2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] CharArray = s.toCharArray();
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        if (CharArray[0] - '0' != 0) {
            dp[1] = 1;
        }
        for (int i = 2; i <= s.length(); i++) {
            int single = CharArray[i - 1] - '0';
            int doubleNum = CharArray[i - 1] - '0' + (CharArray[i - 2] - '0') * 10;
            dp[i] = (single == 0 ? 0 : dp[i - 1]) + (doubleNum >= 10 && doubleNum <= 26 ? dp[i - 2] : 0);
        }
        return dp[s.length()];
    }

    public static int trailingZeroes(int n) {
        if (n < 0) {
            return -1;
        }

        if (n == 0 || n == 1) {
            return 0;
        }
        long factorial = 1;
        while (n > 1) {
            factorial = factorial * n;
            n = n - 1;
        }

        int result = 0;
        while (true) {
            if (factorial % 10 == 0) {
                result ++;
                factorial = factorial / 10;
            } else {
                break;
            }
        }
        return result;
    }

    public static List<String> findRepeatedDnaSequences(String s) {
        List<String> counter = new ArrayList<>();
        if (s == null || s.length() <= 10) {
            return counter;
        }
        Map<String, Integer> bank = new HashMap<>();
        for (int i = 0; i <= s.length() - 10; i++) {
            String temp = s.substring(i, i + 10);
            if (!bank.containsKey(temp)) {
                bank.put(temp, 1);
                continue;
            } else {
                //if contains
                if (bank.get(temp) == 1) {
                    counter.add(temp);
                    bank.put(temp, bank.get(temp) + 1);
                }
            }
        }
        return counter;

    }

    public static int[][] reconstructQueue(int[][] people) {
        if (people == null || people.length == 0 || people[0].length == 0) {
            return people;
        }

        Arrays.sort(people, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                if (a[0] == b[0]) {
                    return a[1] - b[1];
                }
                return b[0] - a[0];
            }
        });

        List<int[]> temp = new ArrayList<>();
        for (int[] p : people) {
            temp.add(p[1], new int[]{p[0], p[1]});
        }

        int[][] result = new int[people.length][2];
        int i = 0;
        for (int[] t : temp) {
            result[i][0] = t[0];
            result[i++][1] = t[1];
        }
        return result;
    }

    public static void gameOfLife(int[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }

        int row = board.length;
        int column = board[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int lives = findNeighbor(board, i, j, row, column);

                if (board[i][j] == 1 && lives >= 2 && lives <= 3) {
                    board[i][j] = 3;
                }

                if (board[i][j] == 0 && lives == 3) {
                    board[i][j] = 2;
                }
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                board[i][j] >>= 1;
            }
        }

        return;
    }

    private static int findNeighbor(int[][] board, int i, int j, int row, int column) {
        int lives = 0;
        for (int x = Math.max(0, i - 1); x <= Math.min(row - 1, i + 1); x++) {
            for (int y = Math.max(0, j - 1); y <= Math.min(column - 1, j + 1); y++) {
                lives += board[x][y] & 1;
            }
        }

        return lives - board[i][j] & 1;
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) {
            return new int[]{};
        }

        int[] result = new int[temperatures.length];
        int maxsofar = temperatures[temperatures.length - 1];
        for (int i = temperatures.length - 2; i >= 0; i--) {
            maxsofar = Math.max(maxsofar, temperatures[i]);
            if (temperatures[i] < temperatures[i + 1]) {
                result[i] = 1;
                continue;
            } else if (temperatures[i] >= maxsofar) {
                result[i] = 0;
            } else {
                for (int j = i + 1; j < temperatures.length; j++) {
                    result[i]++;
                    if (temperatures[j] > temperatures[i]) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    public static int[] dailyTemperatures1(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) {
            return new int[]{};
        }

        int[] result = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = temperatures.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && temperatures[i] >= temperatures[stack.peek()]) {
                stack.pop();
            }
            result[i] = stack.isEmpty() ? 0 : stack.peek() - i;
            stack.push(i);
        }
        return result;
    }

    public static int largestRectangleArea(int[] heights) {
        Stack < Integer > stack = new Stack < > ();
        stack.push(-1);
        int maxarea = 0;
        for (int i = 0; i < heights.length; ++i) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i])
                maxarea = Math.max(maxarea, heights[stack.pop()] * (i - stack.peek() - 1));
            stack.push(i);
        }
        while (stack.peek() != -1)
            maxarea = Math.max(maxarea, heights[stack.pop()] * (heights.length - stack.peek() -1));
        return maxarea;
    }

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        //odd number of nodes
        if (fast != null) {
            slow = slow.next;
        }

        slow = reverse(slow);
        fast = head;

        while (slow != null) {
            if (slow.val != fast.val) {
                return false;
            }
            slow = slow.next;
            fast = fast.next;
        }
        return true;
    }

    private static ListNode reverse(ListNode head) {
        ListNode pre = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = pre;
            pre = head;
            head = temp;
        }
        return pre;
    }

    public static String minWindow(String s, String t) {
        int slen = s.length();
        int tlen = t.length();
        int matcher = tlen;
        int start = 0;
        int result = Integer.MAX_VALUE;
        char[] sc = s.toCharArray();
        char[] tc = t.toCharArray();
        int[] map = new int[128];
        for (char c : tc) {
            map[c]++;
        }

        int i = 0, j = 0;
        while (j < slen) {
            if (map[sc[j++]]-- >= 1) {
                matcher--;
            }
            //all solved
            while (matcher == 0) {
                if (result > j - i) {
                    result = j - i;
                    start = i;
                }
                //try shrinking,外面有个while就可以起到不断shrink的作用
                if (map[sc[i++]]++ == 0) {
                    matcher++;
                }
            }
        }

        return result == Integer.MAX_VALUE? "" : s.substring(start, start + result);
    }

    public static int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int length = s.length();
        int pre = 1;
        int cur = s.charAt(0) == '0'? 0 : 1;
        for (int i = 1; i < length; i++) {
            int temp = cur;
            int value = Integer.valueOf(s.substring(i - 1, i + 1));
            if (1 <= value && value <= 26) {
                if (s.charAt(i) != '0') {
                    cur += pre;
                } else {
                    cur = pre;
                }
            } else {
                if (s.charAt(i) != 0) {
                    cur = cur;
                } else {
                    continue;
                }
            }
            pre = temp;
        }
        return cur;
    }

    public static void main(String[] args) {
//        ListNode l = new ListNode(1);
//        ListNode l2 = new ListNode(2);
//        l.next = l2;
//        ListNode l3 = new ListNode(1);
//        ListNode l4 = new ListNode(3);
//        l.next = l2;
//        l2.next = l3;
//        l3.next = l4;
//        sortList(l);
////        reverseWords("the sky is blue");
//        int[][] temp = new int[][]{{0,1,0},{0,0,1},{1,1,1},{0,0,0}};
//        gameOfLife(temp);
        System.out.print(numDecodings("01"));
    }
}