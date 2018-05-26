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
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i + 1] = Math.max(dp[i], Math.max(dp[i] * nums[i], nums[i]));
        }
        return dp[nums.length];
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
        int[] temp = new int[]{2,3,-2,4};
        System.out.println(maxProduct(temp));
    }
}