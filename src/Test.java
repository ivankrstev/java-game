//import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class Test {
    static Random randNum = new Random();
    public static int m = 5; //rows
    public static int n = 5; //columns
    static JButton[][] array_button = new JButton[m][n];
    static JPanel panel1 = new JPanel();

    public static void rebuild(int m, int n) {
        panel1.removeAll();
        array_button = new JButton[m][n];
        for (int i = 0; i < m; i++) {
            for (int y = 0; y < n; y++) {
                JButton button = new JButton("" + "abcdefghijklmnopqrstuvwxyz".toCharArray()[randNum.nextInt("abcdefghijklmnopqrstuvwxyz".toCharArray().length)]);
                button.setPreferredSize(new Dimension(42, 42));
//                button.setLayout(new MigLayout("debug, novisualpadding"));
                array_button[i][y] = button;
//                panel1.add(button);
            }
        }
//        panel1.setLayout(new GridLayout(m, n));
        panel1.updateUI();
    }

    Test() {
        JFrame window = new JFrame("Word Guess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setMinimumSize(new Dimension(700, 0));
        JPanel panel2 = new JPanel();
        JPanel nav_bar = new JPanel();
        JButton btn_new = new JButton("New Game");
        JButton btn_load = new JButton("Load Game");
        JButton btn_exit = new JButton("Exit Game");
        JTextField setM = new JTextField(5);
        JTextField setN = new JTextField(5);

        JButton change_field = new JButton("Update Field");
        change_field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m = Integer.parseInt(setM.getText());
                n = Integer.parseInt(setN.getText());
                rebuild(m, n);
            }
        });

        JLabel labelM = new JLabel(" Set width: ");
        JLabel labelN = new JLabel(" Set height: ");

        nav_bar.setLayout(new GridBagLayout());
        nav_bar.setPreferredSize(new Dimension(0, 75));
        nav_bar.add(btn_new, new GridBagConstraints());
        nav_bar.add(btn_load, new GridBagConstraints());
        nav_bar.add(btn_exit, new GridBagConstraints());
        nav_bar.add(labelM);
        nav_bar.add(setM);
        nav_bar.add(labelN);
        nav_bar.add(setN);
        nav_bar.add(change_field);

        if (m != 0 || n != 0) {
            panel1.setLayout(new GridLayout(m, n));
        }
        for (int i = 0; i < m; i++) {
            for (int y = 0; y < n; y++) {
//                String rand_char = ;
                JButton button = new JButton("" + "abcdefghijklmnopqrstuvwxyz".toCharArray()[randNum.nextInt("abcdefghijklmnopqrstuvwxyz".toCharArray().length)]);
                button.setPreferredSize(new Dimension(42, 42));
//                button.setBorder(new EmptyBorder(0, 0, 0, 0));
//                button.
                array_button[i][y] = button;
                panel1.add(button, "wmax pref");
            }
        }
        panel2.setPreferredSize(new Dimension(200, 400));
        window.add(nav_bar, BorderLayout.NORTH);
        window.add(panel1, BorderLayout.CENTER);
        window.add(panel2, BorderLayout.EAST);
        window.pack();
        window.setVisible(true);
    }
}
