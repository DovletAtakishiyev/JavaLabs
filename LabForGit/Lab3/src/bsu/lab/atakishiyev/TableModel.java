package bsu.lab.atakishiyev;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from, to, step;

    private static Double y, y_;

    public TableModel(Double[] coefficients, Double from, Double to, Double step){
        this.coefficients = coefficients;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    public Double get_from(){
        return from;
    }
    public Double get_to(){
        return to;
    }
    public Double get_step(){
        return step;
    }

    @Override
    public int getRowCount() {
        return (int) Math.ceil((to - from) / step + 1) ;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Double result = 0.0d;
        Double x = from + rowIndex * step;

        if (columnIndex == 0){
            return x;
        } else if (columnIndex == 1) {
            for (int i = 0; i < coefficients.length; i++) {
                result += coefficients[i] * Math.pow(x, coefficients.length - 1 - i);
            }
            y = result;
            return result;
        } else if (columnIndex == 2) {
            for (int i = coefficients.length - 1; i >= 0; i--) {
                result += coefficients[i] * Math.pow(x, i);
            }
            y_ = result;
            return result;
        } else {
            result = y_ - y;
            return result;
        }
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "X";
            case 1 -> "Y(X)";
            case 2 -> "Y(X)'";
            default -> "Y(X)' - Y(X)";
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Double.class;
    }
}
