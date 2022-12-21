package lab45;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JFileChooser fileChooser = null;

    private JCheckBoxMenuItem showAxisMenuItem;
    private JCheckBoxMenuItem showMarkersMenuItem;
    private JCheckBoxMenuItem showRotateMenuItem;
    private JCheckBoxMenuItem showCalculateMenuItem;

    private DisplayGraphics display = new DisplayGraphics();

    private boolean fileLoaded = false;

    Main(){
        super("Построение графиков функций на основе заранее подготовленных файлов");

        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2, (kit.getScreenSize().height - HEIGHT)/2);
        setExtendedState(MAXIMIZED_BOTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        Action openGraphicsAction = new AbstractAction("Открыть файл с графиком") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser==null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showOpenDialog(Main.this) == JFileChooser.APPROVE_OPTION){
                    openGraphics(fileChooser.getSelectedFile());
                }
            }
        };
        fileMenu.add(openGraphicsAction);
        JMenu graphicsMenu = new JMenu("График");
        menuBar.add(graphicsMenu);

        Action showAxisAction = new AbstractAction("Показывать оси координат") {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setShowAxis(showAxisMenuItem.isSelected());
            }
        };

        showAxisMenuItem = new JCheckBoxMenuItem(showAxisAction);
        graphicsMenu.add(showAxisMenuItem);
        showAxisMenuItem.setSelected(true);

        Action showMarkersAction = new AbstractAction("Показывать маркеры точек"){
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setShowMarkers(showMarkersMenuItem.isSelected());
            }
        };

        showMarkersMenuItem = new JCheckBoxMenuItem(showMarkersAction);
        graphicsMenu.add(showMarkersMenuItem);
        showMarkersMenuItem.setSelected(true);

        Action showRotateAction = new AbstractAction("Повернуть на 90 градусов") {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setShowRotate(showRotateMenuItem.isSelected());
            }
        };

        showRotateMenuItem = new JCheckBoxMenuItem(showRotateAction);
        graphicsMenu.add(showRotateMenuItem);
        showRotateMenuItem.setSelected(false);

        Action calcSquareAction = new AbstractAction("Посчитать площадь") {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setCalcSquare(showCalculateMenuItem.isSelected());
            }
        };

        showCalculateMenuItem = new JCheckBoxMenuItem(calcSquareAction);
        graphicsMenu.add(showCalculateMenuItem);
        showCalculateMenuItem.setSelected(false);




        graphicsMenu.addMenuListener(new GraphicsMenuListener());
        getContentPane().add(display, BorderLayout.CENTER);
    }

    private void openGraphics(File selectedFile) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(selectedFile));
            Double[][] graphicsData = new Double[in.available()/(Double.SIZE / 8) / 2][];

            int i = 0;
            while (in.available() > 0) {
                double x = in.readDouble();
                double y = in.readDouble();
                graphicsData[i++] = new Double[] {x, y};
            }
            if (graphicsData!=null && graphicsData.length>0) {
                fileLoaded = true;

                String name = selectedFile.getName();
                if (name.equals("Line1.bin")){
                    display.current_file(1);
                }else{
                    display.current_file(2);
                }
                display.showGraphics(graphicsData);
            }
            in.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        Main frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

    private class GraphicsMenuListener implements MenuListener {
        @Override
        public void menuSelected(MenuEvent e) {
            showAxisMenuItem.setEnabled(fileLoaded);
            showMarkersMenuItem.setEnabled(fileLoaded);
            showRotateMenuItem.setEnabled(fileLoaded);
            showCalculateMenuItem.setEnabled(fileLoaded);
        }

        @Override
        public void menuDeselected(MenuEvent e) {

        }

        @Override
        public void menuCanceled(MenuEvent e) {

        }
    }
}

