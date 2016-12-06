package by.vma.lab1;

public class Main {
    private static class Matrix {
        public double[][] matrix;
        private int lines;
        private int columns;

        public Matrix() {
            this.lines = 0;
            this.columns = 0;
            this.matrix = null;
        }

        public Matrix(int lines, int columns) throws Exception {
            if (lines < 1 || columns < 1) {
                throw new Exception("Incorrect size.");
            }
            this.lines = lines;
            this.columns = columns;
            this.matrix = new double[lines][columns];
        }

        public int getLines() {
            return lines;
        }

        public int getColumns() {
            return columns;
        }

        public void setColumn(int index, Vector column) throws Exception {
            if (column.getLength() != lines) {
                throw new Exception("Incorrect vector.");
            }
            for (int i = 0; i < lines; i++) {
                matrix[i][index] = column.vector[i];
            }
        }

        public void print(boolean exponent) {
            for (double[] i : matrix) {
                for (double j : i) {
                    if (exponent) {
                        System.out.printf("%e  ", j);
                    } else {
                        System.out.printf("%.5f  ", j);
                    }
                }
                System.out.println();
            }
        }

        public void swap(int fi, int fj, int si, int sj) {
            double tmp = matrix[fi][fj];
            matrix[fi][fj] = matrix[si][sj];
            matrix[si][sj] = tmp;
        }

        public void swapColumns(int fcolumn, int scolumn) {
            for (int i = 0; i < lines; i++) {
                swap(i, fcolumn, i, scolumn);
            }
        }

        public void fillDefault() {
            double[][] a = {{0.6444, 0.0000, -0.1683, 0.1184, 0.1973},
                    {-0.0395, 0.4208, 0.0000, -0.0802, 0.0263},
                    {0.0132, -0.1184, 0.7627, 0.0145, 0.0460},
                    {0.0395, 0.0000, -0.0960, 0.7627, 0.0000},
                    {0.0263, -0.0395, 0.1907, -0.0158, 0.5523}};
            this.lines = 5;
            this.columns = 5;
            this.matrix = a;
        }

        public void fillE(int n) {
            lines = n;
            columns = n;
            matrix = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        matrix[i][j] = 1;
                    } else {
                        matrix[i][j] = 0;
                    }
                }
            }
        }

        public double normI() {
            double maxSum = -1;
            double sum;
            for (int i = 0; i < lines; i++) {
                sum = 0;
                for (int j = 0; j < columns; j++) {
                    sum += Math.abs(matrix[i][j]);
                }
                if (sum > maxSum) {
                    maxSum = sum;
                }
            }
            return maxSum;
        }

        public Vector mul(Vector vector) throws Exception {
            if (columns != vector.getLength()) {
                throw new Exception("Incorrect matrix or vector.");
            }
            Vector result = new Vector(vector.getLength());
            for (int i = 0; i < lines; i++) {
                result.vector[i] = 0;
                for (int j = 0; j < columns; j++) {
                    result.vector[i] += matrix[i][j] * vector.vector[j];
                }
            }
            return result;
        }

        public Matrix mul(Matrix mtr) throws Exception {
            if (columns != mtr.getLines()) {
                throw new Exception("Incorrect matrix.");
            }
            Matrix result = new Matrix(lines, mtr.getColumns());
            for (int i = 0; i < result.getLines(); i++) {
                for (int j = 0; j < result.getColumns(); j++) {
                    result.matrix[i][j] = 0;
                    for (int k = 0; k < columns; k++) {
                        result.matrix[i][j] += this.matrix[i][k] * mtr.matrix[k][j];
                    }
                }
            }
            return result;
        }

        public Matrix subtract(Matrix mtr) throws Exception {
            if (lines != mtr.getLines() || columns != mtr.getColumns()) {
                throw new Exception("Incorrect matrix.");
            }
            Matrix result = new Matrix(lines, columns);
            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < columns; j++) {
                    result.matrix[i][j] = this.matrix[i][j] - mtr.matrix[i][j];
                }
            }
            return result;
        }
    }

    private static class Vector {
        public double[] vector;
        private int length;

        public Vector() {
            this.length = 0;
            this.vector = null;
        }

        public Vector(int length) throws Exception {
            if (length < 1) {
                throw new Exception("Incorrect size.");
            }
            this.length = length;
            vector = new double[length];
        }

        public int getLength() {
            return length;
        }

        public void print(boolean exponent) {
            for (double item : vector) {
                if(exponent){
                    System.out.printf("%e\n", item);
                } else {
                    System.out.printf("%.5f\n", item);
                }
            }
        }

        public void swap(int i, int j) {
            double tmp = vector[i];
            vector[i] = vector[j];
            vector[j] = tmp;
        }

        public void fillDefault() {
            double[] b = {1.2677, 1.6819, -2.3657, -6.5369, 2.8351};
            this.length = 5;
            this.vector = b;
        }

        public Vector subtract(Vector sub) throws Exception {
            if (length != sub.getLength()) {
                throw new Exception("Incorrect vector.");
            }
            Vector result = new Vector(length);
            for (int i = 0; i < length; i++) {
                result.vector[i] = this.vector[i] - sub.vector[i];
            }
            return result;
        }

        public double normI() {
            double max = Math.abs(vector[0]);
            for (int i = 1; i < length; i++) {
                if (Math.abs(vector[i]) > max) {
                    max = Math.abs(vector[i]);
                }
            }
            return max;
        }
    }

    private static Matrix a;
    private static final int n = 5;

    public static void main(String[] args) {
        Vector x;
        Vector b = new Vector();
        Vector r;
        Matrix inverseMtr;
        Matrix identityMtr = new Matrix();
        Matrix rMtr;
        b.fillDefault();
        try {
            x = gauss(b, true);
            System.out.println("Решение: ");
            x.print(false);
            a.fillDefault();
            b.fillDefault();
            r = a.mul(x).subtract(b);
            System.out.println("Вектор невязки r:");
            r.print(true);
            System.out.print("Норма вектора невязки r.normI = ");
            System.out.printf("%e", r.normI());
            System.out.println();
            inverseMtr = inverseMatrix();
            System.out.println("Обратная матрица: ");
            inverseMtr.print(false);
            identityMtr.fillE(n);
            a.fillDefault();
            rMtr = a.mul(inverseMtr).subtract(identityMtr);
            System.out.println("Матрица невязки R = A*A^(-1) - E: ");
            rMtr.print(true);
            System.out.print("Норма матрицы невязки R.normI = ");
            System.out.printf("%e", rMtr.normI());
            System.out.println();
            System.out.print("Число обусловленности ||A|| * ||A^(-1)|| = ");
            System.out.printf("%.5f", a.normI() * inverseMtr.normI());
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Vector gauss(Vector b, boolean determinant) throws Exception {
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
                if (Math.abs(max) < Math.abs(a.matrix[k][i])) {
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
            System.out.print("Определитель матрицы A: ");
            System.out.printf("%.5f", det);
            System.out.println();
        }
        return x;
    }

    private static Matrix inverseMatrix() throws Exception {
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
            a.fillDefault();
        }
        return inverse;
    }
}
