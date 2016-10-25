package by.vma.lab1;

public class Gauss {
    private static Matrix a;
    private static Vector b;
    public static Vector gauss(Vector b) {
        a = new Matrix();
        Vector x = new Vector(5);
        double det = 1;
        boolean isDetNegative = false;
        int n = 5;
        double max;
        int maxk;
        a.fillDefault();
        for (int k = 0; k < n; k++) {
            det *= a.matrix[k][k];
            max = a.matrix[k][k];
            maxk = k;
            for (int i = k + 1; i < n; i++) {
                if (max < Math.abs(a.matrix[i][k])) {
                    max = a.matrix[i][k];
                    maxk = i;
                }
            }
            if (maxk != k) {
                a.swapLines(k, maxk);
                b.swap(k, maxk);
                isDetNegative = !isDetNegative;
            }
            for (int j = k; j < n; j++) {
                a.matrix[k][j] /= max;
            }
            b.vector[k] /= max;
            for (int i = k + 1; i < n; i++) {
                for (int j = k + 1; j < n; j++) {
                    a.matrix[i][j] -= a.matrix[i][k] * a.matrix[k][j];
                }
                b.vector[i] -= a.matrix[i][k] * b.vector[k];
                a.matrix[i][k] = 0;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            x.vector[i] = b.vector[i];
            for (int j = i + 1; j < n; j++) {
                x.vector[i] -= a.matrix[i][j] * x.vector[j];
            }
        }
        if(isDetNegative){
            det *= -1;
        }
        System.out.print("Determinant: ");
        System.out.printf("%.5f", det);
        System.out.println();
        return x;
    }

    public static void vma() {
        Vector x;
        double r;
        a = new Matrix();
        b = new Vector();
        a.fillDefault();
        b.fillDefault();
        x = gauss(b);
        System.out.println("Solution: ");
        x.print();
        try {
            r = a.mul(x).subtract(b).findMax();
            System.out.print("r = ");
            System.out.printf("%e", r);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
