package lab3.var5;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer implements TableCellRenderer {

    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private double eps = 1e-10;
    private boolean needToSearch = false;
    private FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
    // Ищем ячейки, строковое представление которых совпадает с needle
// (иголкой). Применяется аналогия поиска иголки в стоге сена, в роли
// стога сена - таблица
    private String needle = null;

    private DecimalFormat formatter =
            (DecimalFormat)NumberFormat.getInstance();

    public GornerTableCellRenderer() {
        // Показывать только 5 знаков после запятой
        formatter.setMaximumFractionDigits(5);
        // Не использовать группировку (т.е. не отделять тысячи
// ни запятыми, ни пробелами), т.е. показывать число как "1000",
// а не "1 000" или "1,000"
        formatter.setGroupingUsed(false);
        // Установить в качестве разделителя дробной части точку, а не
// запятую. По умолчанию, в региональных настройках
// Россия/Беларусь дробная часть отделяется запятой
        DecimalFormatSymbols dottedDouble =
                formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        // Разместить надпись внутри панели
        panel.add(label);
        // Установить выравнивание надписи по левому краю панели
        panel.setLayout(layout);
    }

    public void setNeedToSearch(boolean b)
    {
        needToSearch = b;
    }

    public boolean getNeedToSearch(){
        return needToSearch;
    }

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        // Преобразовать double в строку с помощью форматировщика
        String formattedDouble = formatter.format(value);
        double d = Double.parseDouble(formattedDouble);
        if(d > 0) layout.setAlignment(FlowLayout.RIGHT);
        if(d < 0) layout.setAlignment(FlowLayout.LEFT);
        if(Math.abs(d) < eps) layout.setAlignment(FlowLayout.CENTER);
        panel.setLayout(layout);
        // Установить текст надписи равным строковому представлению числа
        label.setText(formattedDouble);
        if (col==1 && needle!=null && needle.equals(formattedDouble)) {
            // Номер столбца = 1 (т.е. второй столбец) + иголка не null
// (значит что-то ищем) +
            // значение иголки совпадает со значением ячейки таблицы -
            // окрасить задний фон панели в красный цвет
            panel.setBackground(Color.RED);
        } else if (needToSearch && d > 1 && Math.abs(Math.abs(Math.round(d)) - Math.abs(d)) <= 0.1 + eps  && isPrime((int)Math.round(d))) {
            panel.setBackground(Color.CYAN);
        } else{
            // Иначе - в обычный белый
            panel.setBackground(Color.WHITE);
        }
        return panel;
    }

    public void setNeedle(String needle) {
        this.needle = needle;
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false; // 0 и 1 не являются простыми числами
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false; // число делится на i, значит, не простое
            }
        }
        return true; // число простое
    }

}