package bsu.lab.atakishiyev;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.function.DoublePredicate;

public class Main extends JFrame {

    private Double[] coefficients;
    Dimension dim = new Dimension(600,420);
    private TableModel table_model;
    private TableRender table_render = new TableRender();
    private Box result_box;
    private JMenuItem MI_save_txt, MI_save_bin, MI_save_csv;
    private JMenuItem MI_find_yx, MI_find_range;
    private JMenuItem MI_info_about;
    private JFileChooser fileChooser = null;

    Main(String name, Double[] coefficients){
        super(name);
        setSize(dim);
        setLocationRelativeTo(null);
        this.coefficients = coefficients;
        result_box = Box.createHorizontalBox();
        result_box.add(new JPanel());



        //============================= Menu =============================//
        JMenuBar menu_bar = new JMenuBar();

        /*----- Create Items -----*/
        //Save Menu
        JMenu save_menu = new JMenu("Save");
        JMenu save_as   = new JMenu("Save as");
        MI_save_txt = new JMenuItem(".txt");
        MI_save_bin = new JMenuItem(".bin");
        MI_save_csv = new JMenuItem(".csv");

        save_as.add(MI_save_txt);
        save_as.add(MI_save_bin);
        save_as.add(MI_save_csv);
        save_menu.add(save_as);

        MI_save_txt.setEnabled(false);
        MI_save_bin.setEnabled(false);
        MI_save_csv.setEnabled(false);

        //Find Menu
        JMenu find_menu = new JMenu("Find");
        MI_find_yx = new JMenuItem("Find exact y(x)");
        MI_find_range = new JMenuItem("Find by range");

        find_menu.add(MI_find_yx);
        find_menu.add(MI_find_range);

        MI_find_yx.setEnabled(false);
        MI_find_range.setEnabled(false);

        //Info Menu
        JMenu info_menu = new JMenu("Info");
        MI_info_about = new JMenuItem("About");
        info_menu.add(MI_info_about);
        /*----- Create Items -----*/

        /*----- Add Actions to Items -----*/
        //Save Menu Items
        MI_save_txt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser==null) {                                                           // Если экземпляр диалогового окна "Открытьфайл" ещѐ не создан
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));                      // инициализировать текущей директорией
                }
                if (fileChooser.showSaveDialog(Main.this) == JFileChooser.APPROVE_OPTION)   // Если результат показа диалоговое окно успешный
                    saveToTextFile(fileChooser.getSelectedFile());                                 // сохранить данные в текстовый файл
            }
        });
        MI_save_bin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser==null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(Main.this) == JFileChooser.APPROVE_OPTION)
                    saveToGraphicsFile(fileChooser.getSelectedFile());
            }
        });
        MI_save_csv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser == null){
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("F"));
                }
                if (fileChooser.showSaveDialog(Main.this) == JFileChooser.APPROVE_OPTION){
                    saveToCSVFile(fileChooser.getSelectedFile());
                }
            }
        });
        //Find Menu Items
        MI_find_yx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog(Main.this, "Введите значение для поиска",
                        /**/                                      "Поиск значения", JOptionPane.QUESTION_MESSAGE);

                table_render.set_find_item(value); // Обновить таблицу
                getContentPane().repaint();
            }
        });
        MI_find_range.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog(Main.this, "Введите диапазон через пробел",
                        /**/                                      "Поиск значений", JOptionPane.QUESTION_MESSAGE);
                String[] range = value.split("\\s+");

                table_render.set_range(range); // Обновить таблицу
                getContentPane().repaint();
            }
        });
        //Info Menu Items
        MI_info_about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon icon;
                try {
                    Image image = ImageIO.read(new File("/Users/tshahakurov/Desktop/Kurs 2 BGU/Java Lab/Lab 3/Lab 3/src/amogus.png"));
                    image = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(image);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                JOptionPane.showMessageDialog(Main.this, "Title : \tLab 3 Java\nAuthor : \tDovlet Atakishiyev\nGroup : \t 9",
                        "title", JOptionPane.DEFAULT_OPTION, icon);
            }
        });
        /*----- Add Actions to Items -----*/

        /*----- Add to MenuBar -----*/
        menu_bar.add(save_menu);
        menu_bar.add(find_menu);
        menu_bar.add(info_menu);
        /*----- Add to MenuBar -----*/
        //============================= Menu =============================//

        //============================= User Input =============================//
        JLabel text_info = new JLabel("Please provide initial data: ");
        JLabel text_X0   = new JLabel("X0 = ");
        JLabel text_Xi   = new JLabel("Xi = ");
        JLabel text_step = new JLabel("Step = ");

        JTextField tf_X0   = new JTextField("0.0", 5);
        JTextField tf_Xi   = new JTextField("1.0", 5);
        JTextField tf_step = new JTextField("0.1", 5);

        tf_X0.setMaximumSize(tf_X0.getPreferredSize());
        tf_Xi.setMaximumSize(tf_Xi.getPreferredSize());
        tf_step.setMaximumSize(tf_step.getPreferredSize());

        Box input_box = Box.createHorizontalBox();
        input_box.add(Box.createHorizontalGlue());
        input_box.add(text_info);
        input_box.add(Box.createHorizontalStrut(10));
        input_box.add(text_X0);
        input_box.add(tf_X0);
        input_box.add(text_Xi);
        input_box.add(tf_Xi);
        input_box.add(text_step);
        input_box.add(tf_step);
        input_box.add(Box.createHorizontalGlue());
        //============================= User Input =============================//

        //============================= Calc/Clear =============================//
        JButton btn_calculate = new JButton("Calculate");
        btn_calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Double from = Double.parseDouble(tf_X0.getText());
                    Double to   = Double.parseDouble(tf_Xi.getText());
                    Double step = Double.parseDouble(tf_step.getText());

                    table_model = new TableModel(Main.this.coefficients, from, to, step);
                    JTable table = new JTable(table_model);
                    table.setDefaultRenderer(Double.class, table_render);

                    MI_save_txt.setEnabled(true);
                    MI_save_bin.setEnabled(true);
                    MI_save_csv.setEnabled(true);
                    MI_find_yx.setEnabled(true);
                    MI_find_range.setEnabled(true);

                    table.setRowHeight(30);
                    result_box.removeAll();
                    result_box.add(new JScrollPane(table));
                    getContentPane().validate();


                }catch (Exception exception){
                    System.out.println("Num Exeption");
                    System.out.println(exception.getMessage());
                }

            }
        });
        JButton btn_clear = new JButton("Clear");
        btn_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tf_X0.setText("0.0");
                tf_Xi.setText("1.0");
                tf_step.setText("0.1");

                result_box.removeAll();

                result_box.add(new JPanel());

                MI_save_txt.setEnabled(false);
                MI_save_bin.setEnabled(false);
                MI_save_csv.setEnabled(false);
                MI_find_yx.setEnabled(false);
                MI_find_range.setEnabled(false);
                table_render.set_find_item(null);
                table_render.set_range(null);

                getContentPane().validate();
            }
        });

        Box buttons_box = Box.createHorizontalBox();
        buttons_box.add(Box.createHorizontalGlue());
        buttons_box.add(btn_calculate);
        buttons_box.add(btn_clear);
        buttons_box.add(Box.createHorizontalGlue());
        //============================= Calc/Clear =============================//

        //============================= Add to Frame =============================//


        setJMenuBar(menu_bar);
        add(input_box, BorderLayout.NORTH);
        add(result_box, BorderLayout.CENTER);
        add(buttons_box, BorderLayout.SOUTH);
        //============================= Add to Frame =============================//
    }

    //------------------------------ Save as .txt ------------------------------//
    protected void saveToTextFile(File selectedFile) {
        try {
            // Создать новый символьный поток вывода, направленный в указанный файл
            PrintStream out = new PrintStream(selectedFile);
            // Записать в поток вывода заголовочные сведения
            out.println("Результаты табулирования многочлена по схеме Горнера");
            out.print("Многочлен: ");
            for (int i=0; i < coefficients.length; i++) {
                out.print(coefficients[i] + "*X^" + (coefficients.length-i-1));
                if (i!=coefficients.length-1)
                    out.print(" + ");
            }

            out.println("");
            out.println("Интервал от " + table_model.get_from() + " до " + table_model.get_to() + " с шагом " + table_model.get_step());
            out.println("====================================================");

            // Записать в поток вывода значения в точках
            for (int i = 0; i < table_model.getRowCount(); i++) {
                out.println("Значение в точке " + table_model.getValueAt(i,0) + " равно " + table_model.getValueAt(i,1));
            }
            // Закрыть поток
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //------------------------------ Save as .txt ------------------------------//

    //------------------------------ Save as .bin ------------------------------//
    private void saveToGraphicsFile(File selectedFile) {
        try{
            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));
            for (int i = 0; i < table_model.getRowCount(); i++){
                for (int j = 0; j < 2; j++) {
                    out.writeDouble((Double)table_model.getValueAt(i, j));
                }
            }
            out.close();
        } catch (IOException e) {
            System.out.println("File couldn't be created");
        }
    }
    //------------------------------ Save as .bin ------------------------------//

    //------------------------------ Save as .csv ------------------------------//
    private void saveToCSVFile(File selectedFile) {
        try {
            FileWriter writer = new FileWriter(selectedFile);
            for (int i = 0; i < table_model.getRowCount(); i++){
                for (int j = 0; j < table_model.getColumnCount(); j++) {
                    writer.write(String.valueOf(table_model.getValueAt(i,j)));
                    if(j != 3)
                        writer.write(", ");
                }
                writer.write("\n");
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("File couldn't be created");
        }
    }
    //------------------------------ Save as .csv ------------------------------//

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("There are no coefficients");
            System.exit(-1);
        }
        Double[] coefficients = new Double[args.length];
        int i = 0;
        try {
            for (String arg: args) {
                coefficients[i++] = Double.parseDouble(arg);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Кароче что то не так ¯\\_(ツ)_/¯");
            System.exit(-2);
        }

        Main mainFrame = new Main("Test", coefficients);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}