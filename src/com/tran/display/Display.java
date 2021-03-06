package com.tran.display;

import com.tran.IO.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import static java.awt.SystemColor.window;

//класс для создания окна (рамка и  лист) в любой момет времени существует только
//одно окно
public abstract class Display {

    private static boolean created = false;//созданно ли окно проверка
    private static JFrame window; //создание рамки
    private static Canvas content;//добавление листа в рамку

    private static BufferedImage buffer;
    private static int[] bufferData;
    private static Graphics bufferGraphics;
    private static int clearColor ;

private  static BufferStrategy bufferStrategy;

    public static  void create(int width, int height, String title, int _clearColor, int numBuffers){

        if(created)//если окно уже созданно то не создаем новое
            return;
        window = new JFrame(title); //создание рамки
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//пр нажатии на крестик окно не только закрывается но и программаа открывается
        content = new Canvas();// когда создается конвас автоматически создается
            //функция паинт и в нее передается графический обьекс с помощью канваса


        Dimension size = new Dimension(width, height);//задание размеров окна с помоощью этого класса. тк влоб нельзя
        content.setPreferredSize(size);//вставка параметров размера листа


        window.setResizable(false);
        window.getContentPane().add(content);//вставка листа в рамку чтобы информация была только в ней
        window.pack();// изменнение размиеров окна так чтобы он подходит тоочно под размер нащего контента
        window.setLocationRelativeTo(null);//поялвение окна по центру
        window.setVisible(true);

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();
        bufferGraphics = buffer.getGraphics();
        ((Graphics2D)bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clearColor = _clearColor;


        content.createBufferStrategy(numBuffers);
        bufferStrategy = content.getBufferStrategy();
        created = true;


    }


    public static void clear(){
        Arrays.fill(bufferData, clearColor);

    }


    public static void swapBuffers(){
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer, 0, 0 , null);
        bufferStrategy.show();
    }

    public static Graphics2D getGraphics(){

        return (Graphics2D) bufferGraphics;

    }

    public static void destroy() {
        if(!created)
            return;

        window.dispose();


    }

    public static void setTitle(String title){
        window.setTitle(title);

    }

    public  static void  addInputListener(Input inputListener){
        window.add(inputListener);
    }
}
