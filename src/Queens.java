import java.util.*;

public class Queens {
    int[] row;
    int[] column;
    int[] diagonal;
    int[] disdiagonal;
    int[] xc = new int[]{-2, -2, -1, -1, 1, 1, 2, 2};
    int[] yc = new int[]{-1, 1, -2, 2, -2, 2, 1, -1};
    //1 means occupied, 2 means not occupied
    public List<List<String>> solveNQueens(int n) {
        row = new int[n];
        column = new int[n];
        diagonal = new int[n * 2 - 1];
        disdiagonal = new int[n * 2 - 1];
        int[][] temp = new int[n][n];
        int x = 1;
        List<List<String>> result = new ArrayList<>();
        Set<List<String>> tres = new HashSet<>();
        for (int j = 0; j < n; j++) {
            temp[0][j] = 1;
            row[0] = 1;
            column[j] = 1;
            diagonal[0 + j] = 1;
            disdiagonal[j - 0 + n - 1] = 1;
            helper(tres, temp, 0, j, 1, n);
            temp[0][j] = 0;
            row[0] = 0;
            column[j] = 0;
            diagonal[0 + j] = 0;
            disdiagonal[j - 0 + n - 1] = 0;
        }
        return new ArrayList<>(tres);
    }

    private void helper(Set<List<String>> result, int[][] temp, int x, int y, int queens, int size) {
        if (queens == size) {
            result.add(new ArrayList<>(transfer(temp, size)));
            return;
        }
        for (int i = 0; i < 8; i++) {
            if (x + xc[i] < 0 || y + yc[i] < 0 || x + xc[i] >= size || y + yc[i] >= size) {
                continue;
            }
            if (row[x + xc[i]] == 1 || column[y + yc[i]] == 1 || diagonal[x + xc[i] + y + yc[i]] == 1 || disdiagonal[y + yc[i] - x - xc[i] + size - 1] == 1) {
                continue;
            }
            temp[x + xc[i]][y + yc[i]] = 1;
            row[x + xc[i]] = 1;
            column[y + yc[i]] = 1;
            diagonal[x + xc[i] + y + yc[i]] = 1;
            disdiagonal[y + yc[i] - x - xc[i] + size - 1] = 1;
            helper(result, temp, x + xc[i], y + yc[i], queens + 1, size);
            temp[x + xc[i]][y + yc[i]] = 0;
            row[x + xc[i]] = 0;
            column[y + yc[i]] = 0;
            diagonal[x + xc[i] + y + yc[i]] = 0;
            disdiagonal[y + yc[i] - x - xc[i] + size - 1] = 0;
        }
    }

    public static int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    private static int mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        int result = mergeSort(nums, left, mid) + mergeSort(nums, mid + 1, right);
        int i = left, j = mid + 1;
        int pointer = mid + 1;
        int index = 0;
        int[] mergeArray = new int[right - left + 1];
        while (i <= mid) {
            while (pointer <= right && nums[i] > 2L * nums[pointer]) {
                pointer++;
            }
            result += pointer - mid -1;
            while (j <= right && nums[i] >= nums[j]) {
                mergeArray[index++] = nums[j++];
            }
            mergeArray[index++] = nums[i++];
        }
        while (j <= right) {
            mergeArray[index++] = nums[j++];
        }
        System.arraycopy(mergeArray, 0, nums, left, right - left + 1);
        return result;
    }

    private List<String> transfer(int[][] temp, int size) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j : temp[i]) {
                if (j == 1) {
                    sb.append('Q');
                } else {
                    sb.append('.');
                }
            }
            result.add(sb.toString());
        }
        return result;
    }

    public static int numberOfHorse(String input) {
        if (input == null || input.length() < 5) {
            return 0;
        }
        int count = 0;
        Map<Character, Integer> map = new HashMap<>();
        map.put('n', 0);
        map.put('e', 0);
        map.put('i', 0);
        map.put('g', 0);
        map.put('h', 0);
        for (char c : input.toCharArray()) {
            if (!map.containsKey(c)) {//not n, e, i, g, h
                throw new IllegalArgumentException("Wrong input");
            }
            map.put(c, map.get(c) + 1);
            if (!checkFirst(map)) {
                throw new IllegalArgumentException("Wrong input");
            }
            count = Math.max(updateRes(map), count);
        }
        if (!check(map)) {
            throw new IllegalArgumentException("Wrong input");
        }
        return count;
    }

    private static boolean checkFirst(Map<Character, Integer> map) {
        int[] array = new int[5];
        array[0] = map.get('n');
        array[1] = map.get('e');
        array[2] = map.get('i');
        array[3] = map.get('g');
        array[4] = map.get('h');
        for (int i = 1; i < 5; i++) {
            if (array[i] > array[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private static int updateRes(Map<Character, Integer> map) {
        int minV = Integer.MAX_VALUE;
        int maxV = Integer.MIN_VALUE;
        for (char c : map.keySet()) {
            if (map.get(c) < minV) {
                minV = map.get(c);
            } else if (map.get(c) > maxV) {
                maxV = map.get(c);
            }
        }
        return maxV - minV;
    }

    private static boolean check(Map<Character, Integer> map) {
        int temp = map.get('n');
        for (char c : map.keySet()) {
            if (map.get(c) != temp) {
                return false;
            }
        }
        return true;
    }

    public static boolean validUtf8(int[] data) {
        if (data == null || data.length == 0) {
            return true;
        }
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            count = 0;
            for (char c : convert(data[i]).toCharArray()) {
                if (c == '0') {
                    break;
                }
                count++;
            }
            if (count == 0) {
                continue;
            } else if (count == 1) {
                return false;
            } else {
                int j = i + 1;
                while (j - i < count && j < data.length) {
                    String temp = convert(data[j++]);
                    if (!temp.substring(0, 2).equals("10")) {
                        return false;
                    }
                }
                i = j - 1;
            }
        }
        return true;
    }

    private static String convert(int data) {
        String result = Integer.toBinaryString(data);
        if (result.length() <= 8) {
            result = "00000000" + result;
        }
        return result.substring(result.length() - 8, result.length());
    }

    public static void main(String[] args) {
//        Queens it = new Queens();
//        List<List<String>> result = it.solveNQueens(5);
//        for (List<String> list : result) {
//            System.out.println(list.toString());
//        }
        System.out.println(validUtf8(new int[]{255}));
        System.out.println(Integer.toBinaryString(145));
    }
}
