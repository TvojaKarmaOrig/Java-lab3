package lab3.var5;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {

    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step,
                            Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
        // В данной модели два столбца
        return 4;
    }

    public int getRowCount() {
        // Вычислить количество точек между началом и концом отрезка
        // исходя из шага табулирования
        return new Double(Math.ceil((to-from)/step)).intValue()+1;
    }

    public Object getValueAt(int row, int col) {
        // Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step*row;
        Double result = coefficients[0];
        Double result2 = 0.0;
        for(int i = 1; i < coefficients.length; ++i)
        {
            result = result * x + coefficients[i];
        }
        for(int i = 0; i < coefficients.length; ++i)
        {
            result2 += Math.pow(x, coefficients.length - i - 1) * coefficients[i];
        }
        if(col == 1){
           return result;
        } else if(col == 2)
        {
            return result2;
        } else if(col == 3)
            return Math.abs(Math.abs(result2) - Math.abs(result));
        return x;
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
                // Название 1-го столбца
                return "Значение X";
            case 1:
                // Название 2-го столбца
                return "Значение по Горнеру";
            case 2:
                return "Значение используя pow()";
            default:
                return "Разница";
        }
    }

    public Class<?> getColumnClass(int col) {
        // И в 1-ом и во 2-ом столбце находятся значения типа Double
        return Double.class;
    }

}