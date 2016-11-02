package by.vma.lab1;

public class Gauss {
    private static Matrix a;
    private static Vector b;
    private static final int n = 5;

    public static void vma() {
        Vector x;
        Matrix inverseMtr;
        Matrix identityMtr = new Matrix();
        Matrix rMtr;
        double r;
        a = new Matrix();
        b = new Vector();
        a.fillDefault();
        b.fillDefault();
        try {
            x = gauss(b, true);
            System.out.println("Solution: ");
            x.print(false);
            a.fillDefault();
            b.fillDefault();
            r = a.mul(x).subtract(b).normI();
            System.out.print("r = ");
            System.out.printf("%e", r);
            System.out.println();
            inverseMtr = inverseMatrix();
            System.out.println("Inverse matrix: ");
            inverseMtr.print(false);
            identityMtr.fillE(n);
            a.fillDefault();
            rMtr = a.mul(inverseMtr).subtract(identityMtr);
            System.out.println("R: ");
            rMtr.print(true);
            System.out.print("R.normI = ");
            System.out.printf("%e", rMtr.normI());
            System.out.println();
            System.out.print("A.normI * A[-1].normI = ");
            System.out.printf("%.5f", a.normI() * inverseMtr.normI());
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector gauss(Vector b, boolean determinant) throws Exception {
        a = new Matrix();
        Vector x = new Vector(n);
        Vector xIndexes = new Vector(n);
        double det = 1;
        boolean isDetNegative = false;
        double max;
        int maxk;
        a.fillDefault();
        for(int i = 0; i < n; i++) {
            xIndexes.vector[i] = i;
        }
        for (int k = 0; k < n; k++) {
            if(determinant) {
                det *= a.matrix[k][k];
            }
            max = a.matrix[k][k];
            maxk = k;
            for (int i = k + 1; i < n; i++) {
                if (max < Math.abs(a.matrix[k][i])) {
                    max = a.matrix[k][i];
                    maxk = i;
                }
            }
            if (maxk != k) {
                a.swapColumns(k, maxk);
                xIndexes.swap(k, maxk);
                if(determinant) {
                    isDetNegative = !isDetNegative;
                }
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
            x.vector[(int)xIndexes.vector[i]] = b.vector[i];
            for (int j = i + 1; j < n; j++) {
                x.vector[(int)xIndexes.vector[i]] -= a.matrix[i][j] * x.vector[j];
            }
        }
        if(determinant) {
            if (isDetNegative) {
                det *= -1;
            }
            System.out.print("Determinant: ");
            System.out.printf("%.5f", det);
            System.out.println();
        }
        return x;
    }

    public static Matrix inverseMatrix() throws Exception {
        Matrix inverse = new Matrix(n, n);
        Vector delta = new Vector(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == i) {
                    delta.vector[j] = 1;
                } else {
                    delta.vector[j] = 0;
                }
            }
            inverse.setColumn(i, gauss(delta, false));
        }
        return inverse;
    }
}
