package by.vma.lab1;

public class Matrix {
    public double[][] matrix;
    private int lines;
    private int columns;

    public Matrix() {
        this.lines = 0;
        this.columns = 0;
        this.matrix = null;
    }

    public Matrix(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        this.matrix = new double[lines][columns];
    }

    public Matrix(Matrix init) {
        this(init.getLines(), init.getColumns());
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                this.matrix[i][j] = init.matrix[i][j];
            }
        }
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }

    public void print() {
        for (double[] i : matrix) {
            for (double j : i) {
                System.out.printf("%.5f", j);
            }
            System.out.println();
        }
    }

    public void swap(int fi, int fj, int si, int sj) {
        double tmp = matrix[fi][fj];
        matrix[fi][fj] = matrix[si][sj];
        matrix[si][sj] = tmp;
    }

    public void swapLines(int fline, int sline) {
        for (int i = 0; i < columns; i++) {
            swap(fline, i, sline, i);
        }
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

    public Vector mul(Vector vector) throws Exception {
        if(columns != vector.getLength()) {
            throw new Exception("Incorrect matrix or vector.");
        }
        Vector result = new Vector(vector.getLength());
        for(int i = 0; i < lines; i++) {
            result.vector[i] = 0;
            for(int j = 0; j < columns; j++) {
                result.vector[i] += matrix[i][j] * vector.vector[j];
            }
        }
        return result;
    }
}
