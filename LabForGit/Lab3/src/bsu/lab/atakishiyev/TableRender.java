package bsu.lab.atakishiyev;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TableRender implements TableCellRenderer {
    private String find_item = null;
    private String[] range = null;
    private DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();

    public TableRender(){
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);

        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String formated_Double = formatter.format(value);
        label.setText(formated_Double);

        panel.setBackground(Color.white);



        if ((column%2 == 0 && row%2 == 0) || (column%2 == 1 && row%2 == 1)){
            panel.setBackground(Color.black);
            label.setForeground(Color.white);
        }else{
            label.setForeground(Color.black);
        }
        if (column == 1 && find_item != null && find_item.equals(formated_Double)){
            panel.setBackground(Color.GREEN);
        }
        if (column == 2 && find_item != null && find_item.equals(formated_Double)){
            panel.setBackground(Color.GREEN);
        }
        if (column == 3 && find_item != null && find_item.equals(formated_Double)){
            panel.setBackground(Color.GREEN);
        }
        if (column == 1 && range != null && (Double) value >= Double.parseDouble(range[0]) && (Double) value <= Double.parseDouble(range[1])){
            panel.setBackground(Color.GREEN);
        }



        return panel;
    }

    public void set_find_item(String find_item) {
        this.find_item = find_item;
    }
    public void set_range(String[] range){
        this.range = range;
    }
}
