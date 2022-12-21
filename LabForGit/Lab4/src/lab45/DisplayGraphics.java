package lab45;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;

public class DisplayGraphics extends JPanel {
    private Double[][] graphicsData;            // Coordinates X and Y

    private boolean showAxis = true;            //  show OXY
    private boolean showMarkers = true;         //  show points
    private boolean showRotate = false;
    private boolean showCalculate = false;

    private double[] minScale = new double[2];      // min X Y
    private double[] maxScale = new double[2];      // max X Y

    private double scale;                   // масштаб изображения

    double xIncrement;
    double yIncrement;



    private BasicStroke graphicsStroke;     // draw Line
    private BasicStroke axisStroke;         //      Axis
    private BasicStroke markerStroke;       //      Marker
    private BasicStroke squareStroke;       //      Square
    private Font axisFont, divisionsFont;      //  Used Font

    public DisplayGraphics() {
        setBackground(Color.GRAY);     // bg color
        graphicsStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, new float[]{20,5,5,5,5,5,10,5,10,5}, 0.0f);    // Line
        axisStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);        // Axis
        markerStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);      // Point
        squareStroke = new BasicStroke(4.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);      // Square
        axisFont = new Font("Serif", Font.BOLD, 36);    // Font
        divisionsFont = new Font("Serif", Font.PLAIN, 16);

    }

    public void showGraphics(Double[][] graphicsData) {
        this.graphicsData = graphicsData;                   // Adding Xn,Yn
//        func();
        repaint();
    }

    public void setShowAxis(boolean showAxis) {
        this.showAxis = showAxis;                       //  displaying axis
        repaint();
    }

    public void setShowMarkers(boolean showMarkers) {
        this.showMarkers = showMarkers;                 // Displaying markers
        repaint();
    }
    public void setShowRotate(boolean antiClockRotate) {
        this.showRotate = antiClockRotate;
        repaint();
    }
    public void setCalcSquare(boolean showCalculate) {
        this.showCalculate = showCalculate;
        repaint();
    }


    protected void rotate(Graphics2D canvas){
        AffineTransform transform = AffineTransform.getRotateInstance(
                -Math.PI / 2,
                getSize().getWidth() / 2 + 500,
                getSize().getHeight() - 500);
//        transform.concatenate(new AffineTransform(
//                getSize().getHeight() / getSize().getWidth(),0.0, 0.0,
//                getSize().getWidth() / getSize().getHeight(),
//                (getSize().getWidth() - getSize().getHeight()) / 2,
//                (getSize().getHeight() - getSize().getWidth()) / 2));
        canvas.setTransform(transform);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (graphicsData==null || graphicsData.length==0)
            return;

        //------ Finding Min / Max = X-Y
        minScale[0] = graphicsData[0][0];
        maxScale[0] = graphicsData[graphicsData.length-1][0];
        minScale[1] = graphicsData[0][1];
        maxScale[1] = minScale[1];

        for (int i = 1; i < graphicsData.length; i++) {
            if (graphicsData[i][1] < minScale[1])
                minScale[1] = graphicsData[i][1];
            if (graphicsData[i][1] > maxScale[1])
                maxScale[1] = graphicsData[i][1];
        }

        double scaleX = getSize().getWidth() / (maxScale[0] - minScale[0]);
        double scaleY = getSize().getHeight() / (maxScale[1] - minScale[1]);

        scale = Math.min(scaleX, scaleY);

        if (scale == scaleX) {
            yIncrement = (getSize().getHeight()/scale - (maxScale[1] - minScale[1])) / 2;
            maxScale[1] += yIncrement;
            minScale[1] -= yIncrement;
        }
        if (scale == scaleY) {
            xIncrement = (getSize().getWidth()/scale - (maxScale[0] - minScale[0])) / 2;
            maxScale[0] += xIncrement;
            minScale[0] -= xIncrement;
        }

        Graphics2D canvas = (Graphics2D) g;
        Stroke oldStroke = canvas.getStroke();
        Color oldColor = canvas.getColor();
        Paint oldPaint = canvas.getPaint();
        Font oldFont = canvas.getFont();

        if(showRotate)
            rotate(canvas);
        if(showCalculate)
            paintSquare(canvas);
        if (showAxis) {
            paintAxis(canvas);
            paintDivisions(canvas);
        }
        paintGraphics(canvas);

        if (showMarkers) 
            paintMarkers(canvas);


        canvas.setFont(oldFont);
        canvas.setPaint(oldPaint);
        canvas.setColor(oldColor);
        canvas.setStroke(oldStroke);
    }

    private void paintSquare(Graphics2D canvas){
        canvas.setStroke(squareStroke);
        canvas.setColor(Color.MAGENTA);

        GeneralPath square = new GeneralPath();
        Point2D.Double point;

        for (int i = 0; i < graphicsData.length; i++) {
            point = xyToPoint(graphicsData[i][0], graphicsData[i][1]);
            if (i > 0)
                square.lineTo(point.getX(), point.getY());
            else
                square.moveTo(point.getX(), point.getY());
        }

        //------------------------------------------------------------------------------//
        point = xyToPoint(graphicsData[0][0], graphicsData[0][1]);
        Point2D.Double temp = xyToPoint(point.getX(), 0);
        square.moveTo(point.getX(), point.getY());
        if (point.getY() != temp.getY()){
            square.lineTo(point.getX(), temp.getY());
        }
        point = xyToPoint(graphicsData[graphicsData.length-1][0], graphicsData[graphicsData.length-1][1]);
        temp = xyToPoint(point.getX(), 0);
        if (point.getY() != temp.getY()){
            square.lineTo(point.getX(), temp.getY());
            square.lineTo(point.getX(), point.getY());
        }
        else
            square.lineTo(point.getX(),point.getY());
        //------------------------------------------------------------------------------//


        //------------------------------------------------------------------------------//
//        for (int i = 0; i < graphicsData.length-1; i++) {
//            point = xyToPoint(graphicsData[i][0],graphicsData[i][1]);
//            temp = xyToPoint(graphicsData[i][0] + 0.1,0);
//            square.moveTo(point.getX(),point.getY());
//            square.lineTo(temp.getX(),temp.getY());
//
//        }
//        for (int i = graphicsData.length-1; i > 1 ; i-- ) {
//            point = xyToPoint(graphicsData[i][0],graphicsData[i][1]);
//            temp = xyToPoint(graphicsData[i][0] - 0.1,0);
//            square.moveTo(point.getX(),point.getY());
//            square.lineTo(temp.getX(),temp.getY());
//        }
        //------------------------------------------------------------------------------//


        Point2D.Double new_point = xyToPoint(0,0);

        int screen_width = (int)getSize().getWidth();
        int screen_heigth = (int)getSize().getHeight();
        double step = scale;
        double min_X = graphicsData[0][0];
        double max_X = graphicsData[graphicsData.length-1][0];
        double shift =  max_X - min_X;
        shift *= step;

        int i_shift = (int)(screen_width - shift)/2;
        System.out.println(step + " " + shift + " " + i_shift);


        for (int i = 0; i < graphicsData.length; i++) {
            int x = i_shift + (int) (step * 0.1 * i);
            double y = graphicsData[i][1];
            canvas.drawLine(x,
                            screen_heigth - (int)(y*step),
                            x,
                            screen_heigth);
        }

        int[][] x = new int[graphicsData.length][4];
        int[][] y = new int[graphicsData.length][4];
        double y_temp;

        for (int i = 0; i < graphicsData.length-1; i++) {
            x[i][0] = i_shift + (int) (step * i * 0.1);
            x[i][1] = i_shift + (int) (step * i * 0.1);
            x[i][2] = i_shift + (int) (step * (i + 1) * 0.1);
            x[i][3] = i_shift + (int) (step * (i + 1) * 0.1);

            y_temp = 0;
            y[i][0] =  screen_heigth - (int)(y_temp * step);
            y[i][3] =  screen_heigth - (int)(y_temp * step);
            y_temp = graphicsData[i][1];
            y[i][1] =  screen_heigth - (int)(y_temp * step);
            y_temp = graphicsData[i+1][1];
            y[i][2] =  screen_heigth - (int)(y_temp * step);
        }
        for (int i = 0; i < graphicsData.length-1; i++) {
            canvas.fillPolygon(x[i], y[i], 4);
        }

        //------------------------------------------------------------------------------//

        canvas.draw(square);

        //------------------------------------------------------------------------------//

        canvas.setColor(Color.black);
        canvas.setFont(axisFont);
        String text_to_show = "S = " + calculateSquare() + " кв. ед.";

        canvas.drawString(text_to_show, 100,100);


    }

//    @Override
//    public void paint(Graphics g) {
//        Graphics2D g2D = (Graphics2D) g;
//
//        g2D.drawLine(0,0,100, 100);
//
//        super.paint(g2D);
//    }

    private void paintGraphics(Graphics2D canvas) {


        canvas.setStroke(graphicsStroke);
        canvas.setColor(Color.blue);

        GeneralPath graphics = new GeneralPath();
        for (int i = 0; i < graphicsData.length; i++) {
            Point2D.Double point = xyToPoint(graphicsData[i][0], graphicsData[i][1]);
            if (i > 0)
                graphics.lineTo(point.getX(), point.getY());
            else
                graphics.moveTo(point.getX(), point.getY());
        }
        canvas.draw(graphics);
    }



    private void paintMarkers(Graphics2D canvas) {

        canvas.setStroke(markerStroke);
        Double temp = null;
        int counter = 0;

        for (Double[] point : graphicsData) {


            Double pointY = point[1];


            if (temp != null && pointY > temp || counter == 0){
                canvas.setColor(Color.RED);
            }else {
                canvas.setColor(Color.green);
            }

            Point2D.Double center = xyToPoint(point[0], point[1]);

            canvas.draw(new Line2D.Double(shiftPoint(center, 10, 0),
                    shiftPoint(center, -10, 0)));
            canvas.draw(new Line2D.Double(shiftPoint(center, 0, 10),
                    shiftPoint(center, 0, -10)));

            canvas.draw(new Line2D.Double(shiftPoint(center, -10, -10),
                    shiftPoint(center, 10, 10)));
            canvas.draw(new Line2D.Double(shiftPoint(center, -10, 10),
                    shiftPoint(center, 10, -10)));

            temp = point[1];
            counter++;
        }

    }

    private void paintAxis(Graphics2D canvas) {
        canvas.setStroke(axisStroke);
        canvas.setColor(Color.BLACK);
        canvas.setPaint(Color.BLACK);
        canvas.setFont(axisFont);

        FontRenderContext context = canvas.getFontRenderContext();

        if (minScale[0] <= 0.0 && maxScale[0] >= 0.0) {
            canvas.draw(new Line2D.Double(xyToPoint(0., maxScale[1]), xyToPoint(0., minScale[1])));
            GeneralPath arrow = new GeneralPath();
            Point2D.Double lineEnd = xyToPoint(0., maxScale[1]);
            assert lineEnd != null;
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());

            arrow.lineTo(arrow.getCurrentPoint().getX()+5, arrow.getCurrentPoint().getY()+20);
            arrow.lineTo(arrow.getCurrentPoint().getX()-10, arrow.getCurrentPoint().getY());

            arrow.closePath();
            canvas.draw(arrow);
            canvas.fill(arrow);

            Rectangle2D bounds = axisFont.getStringBounds("y", context);
            Point2D.Double labelPos = xyToPoint(0., maxScale[1]);

            assert labelPos != null;
            canvas.drawString("y", (float)labelPos.getX() + 10, (float)(labelPos.getY() - bounds.getY()));
        }
        if (minScale[1] <= 0.0 && maxScale[1] >= 0.0) {
            canvas.draw(new Line2D.Double(xyToPoint(minScale[0], 0.), xyToPoint(maxScale[0], 0.)));
            GeneralPath arrow = new GeneralPath();
            Point2D.Double lineEnd = xyToPoint(maxScale[0], 0.);
            assert lineEnd != null;
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());

            arrow.lineTo(arrow.getCurrentPoint().getX()-20, arrow.getCurrentPoint().getY()-5);
            arrow.lineTo(arrow.getCurrentPoint().getX(), arrow.getCurrentPoint().getY()+10);
            arrow.closePath();
            canvas.draw(arrow);
            canvas.fill(arrow);

            Rectangle2D bounds = axisFont.getStringBounds("x", context);
            Point2D.Double labelPos = xyToPoint(maxScale[0], 0.);

            assert labelPos != null;
            canvas.drawString("x", (float)(labelPos.getX() - bounds.getWidth() - 10), (float)(labelPos.getY() + bounds.getY()));

        }
    }

    protected void paintDivisions(Graphics2D canvas){
        canvas.setFont(divisionsFont);

        double x, y;

        if (minScale[0] <= 0.0 && maxScale[0] >= 0.0)
            x = 0;
        else
            x = minScale[0];

        if (minScale[1] <= 0.0 && maxScale[1] >= 0.0)
            y = 0;
        else
            y = minScale[1];

        double from = Math.ceil(minScale[0]);
        double step = Math.ceil((maxScale[0] - minScale[0]) / 100);

        while (from < Math.ceil(maxScale[0])){
            Point2D.Double point = xyToPoint(from, y);
            String num = String.valueOf(from);
            canvas.drawString(num,
                    (float)(point.getX() + 10),
                    (float) (point.getY() - 10));
            canvas.drawString("|",
                    (float)(point.getX()),
                    (float) (point.getY()));

            from += step;
        }

        from = Math.ceil(minScale[1]);
        step = Math.ceil((maxScale[1] - minScale[1]) / 100);
        while (from < Math.ceil(maxScale[1])){
            Point2D.Double point = xyToPoint(x, from);
            String num = String.valueOf(Math.ceil(from));
            canvas.drawString(num,
                    (float)(point.getX() + 10),
                    (float) (point.getY() - 10));
            canvas.drawString("---",
                    (float)(point.getX()),
                    (float) (point.getY()));

            from += step;
        }
    }

    protected Point2D.Double xyToPoint(double x, double y) {
        double deltaX = x - minScale[0];
        double deltaY = maxScale[1] - y;
        return new Point2D.Double(deltaX*scale, deltaY*scale);
    }

    protected int[] func(double x, double y){
        double[] point_double = {x * scale, x * scale};
        int X = (int) point_double[0];
        int Y = (int) point_double[1];

        int[] XY = {X,Y};
        return XY;
    }

    protected Point2D.Double shiftPoint(Point2D.Double src, double deltaX, double deltaY) {
        Point2D.Double dest = new Point2D.Double();
        dest.setLocation(src.getX() + deltaX, src.getY() + deltaY);
        return dest;
    }





    int function = 0;

    // ------------------ for line 1
    private double f1(double x){
        return Math.pow(x,3) + 2*x*x;
    }
    // ------------------ for line 2
    private double f2(double x){
        return 0.25*Math.pow(x,3) + 0.5*x*x + 0.5*x + 1;
    }

    private double calculateSquare(){
//        double  a = -2,
//                b = 0;
//        double n = 20;
        double  a = -2,
                b = 1;
        double n = 30;

        double result, temp = 0, t = a;
        double h = (b - a)/ n;

        for (int i = 1; i < n-1; i++){
            t += h;
            if(function == 1)
                temp += f1(t);
            if (function == 2)
                temp += f2(t);
        }

        if(function == 1)
            result = f1(a) + 2*temp + f1(b);
        else
            result = f2(a) + 2*temp + f2(b);

        result = h/2 * result;

        int dadadadad = (int)(result * 1000);
        result = dadadadad/1000.;

        return result;
    }

    public int current_file(int function){
        this.function = function;
        return function;
    }

}
