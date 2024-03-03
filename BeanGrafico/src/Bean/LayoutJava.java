package Bean;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class LayoutJava extends JFrame {
    private JPanel contentPane;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LayoutJava frame = new LayoutJava();
                frame.setVisible(true);
            }
        });
    }

    public LayoutJava() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400,300,850,600);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        Visor visor = new Visor();
        visor.setBounds(50,50,600,400);
        contentPane.add(visor);

        // Crear un JButton para seleccionar imagen
        JButton boton = new JButton("Select");
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ventana para elegir imagen
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(LayoutJava.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Get Imagen seleccionada
                    File selectedFile = fileChooser.getSelectedFile();
                    // Set imagen en el visor
                    visor.setImagen(selectedFile.getAbsolutePath());
                }
            }
        });
        boton.setBounds(50, 470, 150, 30);
        contentPane.add(boton);
    }
}
