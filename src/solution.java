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

    public String reverseWords(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        char[] a = s.toCharArray();
        int n = a.length;
        reverse(a, 0, n - 1);
        reverseWord(a, n);
        return removespace(a, n);
    }

    private void reverse(char[] a, int start, int end) {
        while (start < end) {
            char temp = a[start];
            a[start++] = a[end];
            a[end--] = temp;
        }
    }

    private void reverseWord(char[] a, int length) {
        int pre = 0;
        int end = 0;
        while (pre < length) {
            while ((pre < end || a[pre] == ' ') && pre < length) {
                pre++;
            }
            while ((end < pre || a[end] != ' ') && end < length) {
                end++;
            }
            reverse(a, pre, end - 1);
        }
    }

    private String removespace(char[] a, int length) {
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

    public static void main(String[] args) {
        ListNode l = new ListNode(4);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(1);
        ListNode l4 = new ListNode(3);
        l.next = l2;
        l2.next = l3;
        l3.next = l4;
        sortList(l);
    }
}