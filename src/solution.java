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

    public static int numDecodings(String s) {
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

    public static void main(String[] args) {
//        ListNode l = new ListNode(4);
//        ListNode l2 = new ListNode(2);
//        ListNode l3 = new ListNode(1);
//        ListNode l4 = new ListNode(3);
//        l.next = l2;
//        l2.next = l3;
//        l3.next = l4;
//        sortList(l);
//        reverseWords("the sky is blue");
        int[][] temp = new int[][]{{7,0},{4,4},{7,1},{5,0},{6,1},{5,2}};
        System.out.println(reconstructQueue(temp));
    }
}