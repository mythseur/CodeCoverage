package programme;

public class DoubleRecursive {

    public int f1(int n, int m) {
        if (n <= 0) {
            return m;
        } else {
            return f2(n - 1, m);
        }
    }

    private int f2(int n, int m) {
        if (m <= 0) {
            return n;
        } else {
            return f1(n, m - 1);
        }
    }
}
