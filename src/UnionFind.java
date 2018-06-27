public class UnionFind {
    private int[] father = null;
    private int count;//group number
    //build the initial group
    public UnionFind(int n) {
        father = new int[n];
        for (int i = 0; i < n; ++i) {
            father[i] = i;
        }
    }
    //find out which group it belongs to
    private int find(int x) {
        if (father[x] == x) {
            return x;
        }
        //有个分组并且set parent的作用
        father[x] = find(father[x]);
        return father[x];
    }

    public void connect(int a, int b) {
        int group_a = find(a);
        int group_b = find(b);
        if (group_a != group_b) {
            father[group_a] = group_b;
            count--;
        }
    }

    public int query() {
        return count;//final different numbers
    }

    public void set_count(int total) {
        count = total;
    }
}
