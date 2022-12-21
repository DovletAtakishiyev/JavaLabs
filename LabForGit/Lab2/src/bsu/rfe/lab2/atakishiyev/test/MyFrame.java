package bsu.rfe.lab2.atakishiyev.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class MyFrame extends JFrame {

    private static final Dimension dim = new Dimension(700,500);
    private JTextField textField_X, textField_Y, textField_Z;
    private JTextField textField_result;
    private JTextField textField_mem1, textField_mem2, textField_mem3;
    private int formula_ID = 1;
    private int memory_ID = 1;
    private Double x, y, z, result, mem1, mem2, mem3;
    private Box formula_choice_box = Box.createHorizontalBox();
    private Box memory_choice_box = Box.createHorizontalBox();
    private ButtonGroup formula_buttons = new ButtonGroup();
    private ButtonGroup memory_buttons = new ButtonGroup();
    JLabel label_formula_image = new JLabel();

    ImageIcon image;

//    ImageIcon image_1 = new ImageIcon("/Users/tshahakurov/Desktop/Kurs 2 BGU/Java Lab/Lab 2/LAB2/src/bsu/rfe/lab2/atakishiyev/test/formula1.png");
//    ImageIcon image_2 = new ImageIcon("/Users/tshahakurov/Desktop/Kurs 2 BGU/Java Lab/Lab 2/LAB2/src/bsu/rfe/lab2/atakishiyev/test/formula2.png");


    MyFrame(){
        setTitle("Calculator");
        setSize(dim);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try{

        }catch(Exception exception){
            System.out.println("Image not found");
        }

        //*1*//
        //--------------------------RadioButtons Box--------------------------//
        add_FormulaButton("F1(x,y,z)",1);
        add_FormulaButton("F2(x,y,z)",2);
        formula_buttons.setSelected(formula_buttons.getElements().nextElement().getModel(), true);
        //--------------------------RadioButtons Box--------------------------//

        //*2*//
        //--------------------------XYZ Box--------------------------//
        JLabel labelX = new JLabel("X = ");
        textField_X = new JTextField("0",10);
        textField_X.setMaximumSize(textField_X.getPreferredSize());

        JLabel labelY = new JLabel("Y = ");
        textField_Y = new JTextField("0",10);
        textField_Y.setMaximumSize(textField_Y.getPreferredSize());

        JLabel labelZ = new JLabel("Z = ");
        textField_Z = new JTextField("0",10);
        textField_Z.setMaximumSize(textField_Z.getPreferredSize());

        Box XYZ_box = Box.createHorizontalBox();
//        XYZ_box.add(Box.createHorizontalGlue());
        XYZ_box.add(labelX);
        XYZ_box.add(textField_X);
        XYZ_box.add(Box.createHorizontalGlue());
        XYZ_box.add(labelY);
        XYZ_box.add(textField_Y);
        XYZ_box.add(Box.createHorizontalGlue());
        XYZ_box.add(labelZ);
        XYZ_box.add(textField_Z);
//        XYZ_box.add(Box.createHorizontalGlue());
        //--------------------------XYZ Box--------------------------//

        //*3*//
        //--------------------------Calculation Result Box--------------------------//
        JLabel label_result = new JLabel("Result = ");
        textField_result = new JTextField("0", 20);
        textField_result.setMaximumSize(textField_result.getPreferredSize());

        Box result_box = Box.createHorizontalBox();
        result_box.add(label_result);
        result_box.add(textField_result);
        //--------------------------Calculation Result Box--------------------------//


        //*4*//
        //--------------------------Calculate/Clear Buttons Box--------------------------//
        JButton calculate_btn = new JButton("Calculate");
        calculate_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    x = Double.parseDouble(textField_X.getText());
                    y = Double.parseDouble(textField_Y.getText());
                    z = Double.parseDouble(textField_Z.getText());

                    if (formula_ID == 1){
                        result = formula_1(x,y,z);
                    }else{
                        result = formula_2(x,y,z);
                    }

                    textField_result.setText(result.toString());
                }catch (Exception exception){
                    System.out.println("Нормально введи число!");
                    System.out.println(exception.getMessage());
                }
            }
        });

        JButton clear_btn = new JButton("Clear");
        clear_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField_X.setText("0");
                textField_Y.setText("0");
                textField_Z.setText("0");
                textField_result.setText("0");
            }
        });

        Box calculate_clear_box = Box.createHorizontalBox();
        calculate_clear_box.add(calculate_btn);
        calculate_clear_box.add(clear_btn);
        //--------------------------Calculate/Clear Buttons Box--------------------------//

        //*5*//
        //--------------------------Memory Choice Box--------------------------//
        add_MemoryButton("Memory 1",1);
        add_MemoryButton("Memory 2",2);
        add_MemoryButton("Memory 3",3);
        memory_buttons.setSelected(memory_buttons.getElements().nextElement().getModel(), true);
        //--------------------------Memory Choice Box--------------------------//

        //*6*//
        //--------------------------Memory Show Box--------------------------//
        JLabel label_mem1 = new JLabel("Memory 1 = ");
        textField_mem1 = new JTextField("0", 20);
        textField_mem1.setMaximumSize(textField_mem1.getPreferredSize());
        Box mem1_box = Box.createHorizontalBox();
        mem1_box.add(label_mem1);
        mem1_box.add(textField_mem1);

        JLabel label_mem2 = new JLabel("Memory 2 = ");
        textField_mem2 = new JTextField("0", 20);
        textField_mem2.setMaximumSize(textField_mem2.getPreferredSize());
        Box mem2_box = Box.createHorizontalBox();
        mem2_box.add(label_mem2);
        mem2_box.add(textField_mem2);

        JLabel label_mem3 = new JLabel("Memory 3 = ");
        textField_mem3 = new JTextField("0", 20);
        textField_mem3.setMaximumSize(textField_mem3.getPreferredSize());
        Box mem3_box = Box.createHorizontalBox();
        mem3_box.add(label_mem3);
        mem3_box.add(textField_mem3);

        Box memory_show_box = Box.createVerticalBox();
        memory_show_box.add(mem1_box);
        memory_show_box.add(mem2_box);
        memory_show_box.add(mem3_box);
        //--------------------------Memory Show Box--------------------------//

        //*7*//
        //--------------------------Add Result to Memory Box--------------------------//
        JButton MP_btn = new JButton("M+");
        MP_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    result = Double.parseDouble(textField_result.getText());
                    mem1   = Double.parseDouble(textField_mem1.getText());
                    mem2   = Double.parseDouble(textField_mem2.getText());
                    mem3   = Double.parseDouble(textField_mem3.getText());

                    if (memory_ID == 1){
                        mem1 += result;
                        textField_mem1.setText(mem1.toString());
                    }else if (memory_ID == 2) {
                        mem2 += result;
                        textField_mem2.setText(mem2.toString());
                    }else{
                        mem3 += result;
                        textField_mem3.setText(mem3.toString());
                    }

                }catch (Exception exception){
                    System.out.println("Нормально введи число!");
                    System.out.println(exception.getMessage());
                }
            }
        });

        JButton MC_btn = new JButton("MC");
        MC_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (memory_ID == 1){
                    textField_mem1.setText("0");
                    mem1 = 0d;
                } else if (memory_ID == 2) {
                    textField_mem2.setText("0");
                    mem2 = 0d;
                }else{
                    textField_mem3.setText("0");
                    mem3 = 0d;
                }
            }
        });

        Box MP_MC_box = Box.createHorizontalBox();
        MP_MC_box.add(MC_btn);
        MP_MC_box.add(MP_btn);
        //--------------------------Add Result to Memory Box--------------------------//

        //*8*//
        //--------------------------Add Pics Box--------------------------//
        Box formula_image_box = Box.createHorizontalBox();
        try{
            label_formula_image.setIcon(image);
            formula_image_box.add(label_formula_image);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        //--------------------------Add Pics Box--------------------------//


        //--------------------------Main Box with all Components--------------------------//
        Box main_box = Box.createVerticalBox();

        main_box.add(Box.createVerticalGlue());
        main_box.add(formula_image_box);
        main_box.add(Box.createVerticalGlue());
        main_box.add(formula_choice_box);
        main_box.add(Box.createVerticalGlue());
        main_box.add(XYZ_box);
        main_box.add(Box.createVerticalGlue());
        main_box.add(result_box);
        main_box.add(Box.createVerticalGlue());
        main_box.add(calculate_clear_box);
        main_box.add(Box.createVerticalGlue());
        main_box.add(memory_choice_box);
        main_box.add(Box.createVerticalGlue());
        main_box.add(memory_show_box);
        main_box.add(Box.createVerticalGlue());
        main_box.add(MP_MC_box);
        main_box.add(Box.createVerticalGlue());

        //--------------------------Main Box with all Components--------------------------//

        add(main_box);
    }

    //--------------------------Add Formula Buttons--------------------------//
    private void add_FormulaButton(String name, int formula_ID){
        JRadioButton btn = new JRadioButton(name);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFrame.this.formula_ID = formula_ID;
                try{
                    if (formula_ID == 1){
                        image = new ImageIcon("/Users/tshahakurov/Desktop/Kurs 2 BGU/Java Lab/Lab 2/LAB2/src/bsu/rfe/lab2/atakishiyev/test/formula1.png");
                        image = new ImageIcon(image.getImage().getScaledInstance(350,50, Image.SCALE_SMOOTH));
                    }else{
                        image = new ImageIcon("/Users/tshahakurov/Desktop/Kurs 2 BGU/Java Lab/Lab 2/LAB2/src/bsu/rfe/lab2/atakishiyev/test/formula2.png");
                        image = new ImageIcon(image.getImage().getScaledInstance(420,50, Image.SCALE_SMOOTH));
                    }

                    label_formula_image.setIcon(image);
                }catch (Exception exception){
                    System.out.println("Кароче фоток нет.");
                    System.out.println(exception.getMessage());
                }
            }
        });
        formula_buttons.add(btn);
        formula_choice_box.add(btn);
    }
    //--------------------------Add Formula Buttons--------------------------//

    //--------------------------Add Memory Buttons--------------------------//
    private void add_MemoryButton(String name, int memory_ID){
        JRadioButton btn = new JRadioButton(name);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFrame.this.memory_ID = memory_ID;
            }
        });
        memory_buttons.add(btn);
        memory_choice_box.add(btn);
    }
    //--------------------------Add Memory Buttons--------------------------//



    //--------------------------Formula #1/#2--------------------------//
    public Double formula_1(Double x, Double y, Double z){
        return Math.sin(Math.log(y) + Math.sin(Math.PI * y * y)) * Math.pow(x*x + Math.sin(z) + Math.exp(Math.cos(y*y)), 1/4d);
    }
    public Double formula_2(Double x, Double y, Double z){
        return Math.pow( (Math.cos(Math.exp(x))) + (Math.pow(Math.log(1+y),2)) + (Math.sqrt(Math.exp(Math.cos(x)) + Math.pow(Math.sin(Math.PI*z),2))) + (Math.sqrt(1d/x)) + (Math.cos(y*y)), Math.sin(z));
    }
    //--------------------------Formula #1/#2--------------------------//

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        frame.setVisible(true);
    }
}
