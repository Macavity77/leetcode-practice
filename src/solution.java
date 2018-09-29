import javafx.util.Pair;
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
        Stack <Integer> stack = new Stack < > ();
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

    public static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        int count = 0;
        int[] s1map = new int[26];
        int[] s2map = new int[26];

        for (int i = 0; i < s1.length(); i++) {
            s1map[s1.charAt(i) - 'a']++;
            s2map[s2.charAt(i) - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if (s1map[i] == s2map[i]) {
                count++;
            }
        }
        int right = 0, left = 0;
        for (int i = 0; i < s2.length() - s1.length(); i++) {
            right = s2.charAt(i + s1.length()) - 'a';
            left = s2.charAt(i) - 'a';
            if (count == 26) {
                return true;
            }
            s2map[right]++;
            if (s2map[right] == s1map[right]) {
                count++;
            } else if (s2map[right] == s1map[right] + 1) {//通过这个方式监控从match到非match来减少count
                count--;
            }
            s2map[left]--;
            if (s2map[left] == s1map[left]) {
                count++;
            } else if (s2map[left] == s1map[left] - 1) {
                count--;
            }
        }
        return count == 26;//final round match here
    }

    public static int maxSubArrayLen(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        int result = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == k) {
                result = i + 1;
            } else if (map.containsKey(sum - k)) {
                result = Math.max(result, i - map.get(sum - k));
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return result;
    }

    public static int[] findDiagonalOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[]{};
        }
        int row = matrix.length;
        int column = matrix[0].length;
        int[] result = new int[row * column];
        int r = 0, c = 0;
        for (int i = 0; i < result.length; i++) {
            result[i] = matrix[r][c];
            if ((r + c) % 2 == 0) {
                if (c == column - 1) {
                    r++;
                } else if (r == 0) {
                    c++;
                } else {
                    r--;
                    c++;
                }
            } else {
                if (r == row - 1) {
                    c++;
                } else if (c == 0) {
                    r++;
                } else {
                    r++;
                    c--;
                }
            }
        }
        return result;
    }

    static int[][] grid;
    static boolean[][] seen;
    static List<Integer> shape;
    public static int numDistinctIslands(int[][] grd) {
        if (grd == null || grd.length == 0) {
            return 0;
        }
        grid = grd;
        seen = new boolean[grid.length][grid[0].length];
        Set<List<Integer>> shapes = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    shape = new ArrayList<>();
                    explore(i, j, 0);
                    if (shape.size() != 0) {
                        shapes.add(shape);
                    }
                }
            }
        }
        return shapes.size();
    }

    private static void explore(int x, int y, int direction) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length
                || seen[x][y] == true || grid[x][y] == 0) {
            return;
        }
        seen[x][y] = true;
        shape.add(direction);
        explore(x - 1, y, 1);
        explore(x + 1, y, 2);
        explore(x, y - 1, 3);
        explore(x, y + 1, 4);
        shape.add(0);
    }

    public static TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return helper(nums, 0, nums.length - 1);
    }

    private static TreeNode helper(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        if (start == end) {
            return new TreeNode(nums[start]);
        }
        int maximum = Integer.MIN_VALUE, index = 0;
        for (int i = start; i <= end; i++) {
            if (nums[i] > maximum) {
                maximum = nums[i];
                index = i;
            }
        }
        TreeNode root = new TreeNode(maximum);
        root.left = helper(nums, start, index - 1);
        root.right = helper(nums, index + 1, end);
        return root;
    }

    public static TreeNode constructMaximumBinaryTree1(int[] nums) {
        Deque<TreeNode> stack = new LinkedList<>();
        for(int i = 0; i < nums.length; i++) {
            TreeNode curr = new TreeNode(nums[i]);
            while(!stack.isEmpty() && stack.peek().val < nums[i]) {
                curr.left = stack.pop();
            }
            if(!stack.isEmpty()) {
                stack.peek().right = curr;
            }
            stack.push(curr);
        }

        return stack.isEmpty() ? null : stack.removeLast();
    }

    static int[][] localSum;
    public static void NumMatrix(int[][] matrix) {
        localSum = new int[matrix.length + 1][matrix[0].length + 1];
        for (int i = 0; i < matrix.length; i++) {
            localSum[i + 1][1] = localSum[i][1] + matrix[i][0];
        }
        for (int j = 0; j < matrix[0].length; j++) {
            localSum[1][j + 1] = localSum[1][j] + matrix[0][j];
        }
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                localSum[i + 1][j + 1] = localSum[i + 1][j] + localSum[i][j + 1] - localSum[i][j] + matrix[i][j];
            }
        }
    }

    public static int sumRegion(int row1, int col1, int row2, int col2) {
        return localSum[row2 + 1][col2 + 1] - localSum[row2 + 1][col1] - localSum[row1][col2 + 1] + localSum[row1][col1];
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverseList(l1);
        l2 = reverseList(l2);
        int carry = 0;
        ListNode dummy = new ListNode(0);
        ListNode result = dummy;
        int temp;
        while (l1 != null && l2 != null) {
            temp = l1.val + l2.val + carry;
            l1.val = temp % 10;
            carry = temp / 10;
            result.next = l1;
            result = result.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            l1.val += carry;
            carry = 0;
            result.next = l1;
            result = result.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            l2.val += carry;
            carry = 0;
            result.next = l2;
            result = result.next;
            l2 = l2.next;
        }
        return reverseList(dummy.next);
    }

    private static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = null;
        ListNode temp;
        while (head != null) {
            temp = head.next;
            head.next = pre;
            pre = head;
            head = temp;
        }
        return pre;
    }

    public static boolean backspaceCompare(String S, String T) {
        Stack<Character> stackS = new Stack<>();
        Stack<Character> stackT = new Stack<>();

        for (char c : S.toCharArray()) {
            if (c == '#' && !stackS.isEmpty()) {
                stackS.pop();
            } else if (c != '#'){
                stackS.push(c);
            }
        }
        for (char c : T.toCharArray()) {
            if (c == '#' && !stackT.isEmpty()) {
                stackT.pop();
            } else if (c != '#') {
                stackT.push(c);
            }
        }
        while (!stackS.isEmpty() && !stackT.isEmpty()) {
            if (stackS.pop() != stackT.pop()) {
                return false;
            }
        }
        return stackS.isEmpty() && stackT.isEmpty();
    }

    static class Node {
        int val;
        Node next;
        Node pre;
        Node(int x) {
            val = x;
            next = null;
            pre = null;
        }
    }

    public static Node deleteFromDoubleLinkedList(Node head, Node node) {
        if (head == null || node == null) {
            return head;
        }
        Node dummy = new Node(0);
        dummy.next = head;
        if (head == node) {
            dummy.next = node.next;
        }
        if (node.pre != null) {
            node.pre.next = node.next;
        }
        if (node.next != null) {
            node.next.pre = node.pre;
        }
        return dummy.next;
    }

    public static List<String> generatePalindromes(String s) {
        int[] count = new int[128];
        for (char c : s.toCharArray()) {
            count[c]++;
        }
        int oddPart = 0;
        String odd = "";
        char[] bank = new char[s.length() / 2];
        int indexBank = 0;
        for (int i = 0; i < 128; i++) {
            if (count[i] % 2 != 0) {
                oddPart++;
                odd += (char) i;
            }
            for (int j = 0; j < count[i] / 2; j++) {
                bank[indexBank++] = (char) i;
            }
        }
        if (oddPart > 1) {
            return new ArrayList<String>();
        }
        Set<String> result = new HashSet<>();
        int[] visited = new int[bank.length];
        helper(bank, "", result, odd, visited);
        return new ArrayList<>(result);
    }

    private static void helper(char[] bank, String temp, Set<String> result, String odd, int[] visited) {
        if (temp.length() == bank.length) {
            StringBuilder sb = new StringBuilder(temp);
            result.add(temp + odd + sb.reverse().toString());
            return;
        }
        for (int i = 0; i < bank.length; i++) {
            if (visited[i] == 1 || (i != 0 && bank[i] == bank[i - 1] && visited[i - 1] == 0)) {
                continue;
            }
            temp += bank[i];
            visited[i] = 1;
            helper(bank, temp, result, odd, visited);
            visited[i] = 0;
            temp = temp.substring(0, temp.length() - 1);
        }
        return;
    }

    public enum fills {people, bicycle, empty};

    private static int distance(int a, int b, int N) {
        return Math.abs(a / N - b / N) + Math.abs(a % N - b % N);
    }

    public static class pair {
        int peopleID;
        int bicycleID;
        int distance;
        pair (int x, int y, int z) {
            peopleID = x;
            bicycleID = y;
            distance = z;
        }
    }

    public static List<Pair<Integer, Integer>> findMatch(fills[][] grid) {
        Set<Integer> people = new HashSet<>();
        Set<Integer> bicycles = new HashSet<>();
        PriorityQueue<pair> queue = new PriorityQueue<>(new Comparator<pair>(){
            @Override
            public int compare(pair o1, pair o2) {
                return o1.distance - o2.distance;
            }
        });
        int row = grid.length;
        int column = grid[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (grid[i][j] == fills.people) {
                    people.add(i * column + j);
                } else if (grid[i][j] == fills.bicycle) {
                    bicycles.add(i * column + j);
                }
            }
        }
        for (int eachPeople : people) {
            for (int bicycle : bicycles) {
                queue.offer(new pair(eachPeople, bicycle, distance(eachPeople, bicycle, column)));
            }
        }
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        while (!queue.isEmpty() && !people.isEmpty() && !bicycles.isEmpty()) {
            pair curr = queue.poll();
            if (people.contains(curr.peopleID) && bicycles.contains(curr.bicycleID)) {
                result.add(new Pair<>(curr.peopleID, curr.bicycleID));
                people.remove(curr.peopleID);
                bicycles.remove(curr.bicycleID);
            }
        }
        return result;
    }

    private static int com(int a, int b) {
        int countA = Integer.bitCount(a);
        int countB = Integer.bitCount(b);
        if (countA > countB) {
            return 1;
        } else if (countA < countB) {
            return -1;
        } else {
            return a > b ? 1 : -1;
        }
    }

    public static List<Integer> rearrange(List<Integer> elements) {
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(elements.size(), new Comparator<Integer>(){
            public int compare(Integer a, Integer b) {
                return com(a, b);
            }
        });
        for (int i : elements) {
            queue.add(i);
        }
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.poll());
        }
        return result;
    }
    class bitCompare implements Comparator<Integer> {
        public int compare (Integer a, Integer b) {
            return -1;
        }
    }

    public static List<List<Integer>> permute(int n) {
        List<Integer> bank = new ArrayList<>();
        int i = 1;
        while (i <= n) {
            bank.add(i++);
        }
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        helper1(bank, temp, result,  n);
        return result;
    }

    private static void helper1(List<Integer> bank, List<Integer> temp
            , List<List<Integer>> result, int size)
    {
        if (temp.size() == size) {
            result.add(new ArrayList<>(temp));
            return;
        }

            for (int i = 0; i < bank.size(); i++) {
                if (temp.contains(bank.get(i))) {
                    continue;
                }
                if (!temp.isEmpty() && ((temp.get(temp.size() - 1) % 2 != 0) ^ (bank.get(i) % 2 == 0))) {
                    continue;
                }
                temp.add(bank.get(i));
                helper1(bank, temp, result, size);
                temp.remove(temp.size() - 1);
            }
    }

    public static void main(String[] args) {
//        ListNode l = new ListNode(7);
//        ListNode l2 = new ListNode(2);
//        l.next = l2;
//        ListNode l3 = new ListNode(4);
//        ListNode l4 = new ListNode(3);
//        l2.next = l3;
//        l3.next = l4;
//        ListNode l5 = new ListNode(5);
//        ListNode l6 = new ListNode(6);
//        ListNode l7 = new ListNode(4);
//        l5.next = l6;
//        l6.next = l7;
//        addTwoNumbers(l, l5);
//        sortList(l);
////        reverseWords("the sky is blue");
//        int[][] temp = new int[][]{{0,1,0},{0,0,1},{1,1,1},{0,0,0}};
//        gameOfLife(temp);
//        int[][] temp = new int[][]{{1,1,0},{0,1,1},{0,0,0},{1,1,1},{0,1,0}};
//        System.out.print(numDistinctIslands(temp));
//        int[] temp = new int[]{3,2,1,6,0,5};
//        constructMaximumBinaryTree1(temp);
//        System.out.println('z' - 'A');
//        char[] test = new char[6];
//        for (char c : test) {
//            System.out.println(c);
//        }
//        int[][] temp = new int[][]{{3,0,1,4,2},{5,6,3,2,1},{1,2,0,1,5},{4,1,0,1,7},{1,0,3,0,5}};
//        NumMatrix(temp);
//        System.out.println(sumRegion(2,1,4,3));
//        System.out.println(backspaceCompare("y#fo##f", "y#f#o##f"));
        //System.out.println(repeatedStringMatch("abcd", "cdabcdab"));
//        System.out.println(generatePalindromes("aaa"));
//        fills[][] input = new fills[][]{{fills.bicycle, fills.empty, fills.empty, fills.people, fills.empty}
//                                        , {fills.empty, fills.empty, fills.bicycle, fills.empty, fills.empty}
//                                        , {fills.empty, fills.empty, fills.empty, fills.empty, fills.empty}
//                                        , {fills.empty, fills.empty, fills.empty, fills.empty, fills.people}
//                                        , {fills.empty, fills.empty, fills.people, fills.empty, fills.bicycle}
//                                        , {fills.empty, fills.empty, fills.empty, fills.empty, fills.empty}};
//        List<Pair<Integer, Integer>> result = findMatch(input);
//        for (Pair<Integer, Integer> p : result) {
//            System.out.print(p.getKey());
//            System.out.println("->" + p.getValue());
//        }
//        List<Integer> list = new ArrayList<>();
//        list.add(3);
//        list.add(2);
//        list.add(1);
//        System.out.println(rearrange(list).toString());
        for (List<Integer> list : permute(4)) {
            System.out.println(list.toString());
        }
    }
}