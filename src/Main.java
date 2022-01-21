import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class Checker {
    public static String[] array;

    public Checker() throws FileNotFoundException {
        File txt = new File("dictionary.txt");
        Scanner scan = new Scanner(txt);
        ArrayList<String> data = new ArrayList<String>();
        while (scan.hasNextLine()) {
            data.add(scan.nextLine());
        }
        array = data.toArray(new String[]{});
    }
}

public class Main extends JFrame implements ActionListener {
    static Random randNum = new Random();
    public static int m; //rows
    public static int n; //columns
    JButton[][] array_button = new JButton[n][m];
    JPanel panel1 = new JPanel(), panel2 = new JPanel(new FlowLayout());
    JFrame frame;
    JLabel check_word_results;
    JButton finish_word = new JButton("Finish Word");
    boolean guessing = false, first_button_clicked = false, possible_selected_word;
    int[][] clicked_buttons, secondary_buttons;
    String current_word = "";
    int times_clicked_btn = 0, score = 0;
    public static String[] words_array;

    public void reset() {
        for (int i = 0; i < n; i++) {
            for (int y = 0; y < m; y++) {
                array_button[i][y].setBackground(Color.getColor("r=238,g=238,b=238"));
                array_button[i][y].setEnabled(true);
            }
        }
        clicked_buttons = new int[n][m];
        current_word = "";
        times_clicked_btn = 0;
        panel2.remove(finish_word);
        check_word_results.setText("");
        panel2.updateUI();
    }

    public void rebuild() {
        m = (int) (Math.random() * 36) + 2; //36
        n = (int) (Math.random() * 33) + 2; //33
        clicked_buttons = new int[n][m];
        panel1.removeAll();
        array_button = new JButton[n][m];
        for (int i = 0; i < n; i++) {
            for (int y = 0; y < m; y++) {
                JButton button = new JButton("" + "abcdefghijklmnopqrstuvwxyz".toCharArray()[randNum.nextInt("abcdefghijklmnopqrstuvwxyz".toCharArray().length)]);
                array_button[i][y] = button;
                button.addActionListener(this);
                button.setEnabled(true);
                panel1.add(button);
            }
        }
        reset();
        int rand1 = (int) (Math.random() * m); //36
        int rand2 = (int) (Math.random() * n); //33
        array_button[rand2][rand1].doClick();
        panel1.updateUI();
        panel1.setLayout(new GridLayout(n, m));
        this.pack(); //Window is sized to fit the preferred size and layouts of its subcomponents
        this.setLocationRelativeTo(null); // It centers the Window in the middle of the screen
    }

    public Main() {
        //NAVBAR
        JPanel nav_bar = new JPanel();
        JButton btn_new = new JButton("New Game");
        btn_new.addActionListener(e -> {
            m = (int) (Math.random() * 36) + 2; //36
            n = (int) (Math.random() * 33) + 2; //33
            System.out.println(m + " " + n);
            if (m != 0 && n != 0)
                rebuild();
        });
        JButton btn_load = new JButton("Load Game");
        JTextField setM = new JTextField(4);
        JTextField setN = new JTextField(4);
        JButton change_field = new JButton("Update Field");
        change_field.addActionListener(e -> {
            boolean popup_opened = false;
            int temp1 = 0, temp2 = 0;
            try {
                temp1 = Integer.parseInt(setM.getText());
                if (temp1 <= 0) {
                    JOptionPane.showMessageDialog(frame, "Enter a number bigger than 0!",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                    popup_opened = true;
                }
            } catch (Throwable ee) {
                JOptionPane.showMessageDialog(frame, "Enter a Number!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                popup_opened = true;
            }
            try {
                temp2 = Integer.parseInt(setN.getText());
                if (temp2 <= 0 && !popup_opened) {
                    JOptionPane.showMessageDialog(frame, "Enter a number bigger than 0!",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Throwable ee) {
                if (!popup_opened) {
                    JOptionPane.showMessageDialog(frame, "Enter a Number!",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (temp1 > 0 && temp2 > 0) {
                m = temp1;
                n = temp2;
                rebuild();
            }
        });
        JLabel labelM = new JLabel(" Set width: ");
        JLabel labelN = new JLabel(" Set height: ");

        nav_bar.setLayout(new GridBagLayout());
        nav_bar.setPreferredSize(new Dimension(0, 50));
        nav_bar.add(btn_new);
        nav_bar.add(btn_load);
        nav_bar.add(labelM);
        nav_bar.add(setM);
        nav_bar.add(labelN);
        nav_bar.add(setN);
        nav_bar.add(change_field);

        if (m != 0 || n != 0) {
            panel1.setLayout(new GridLayout(n, m));
        }
        for (int i = 0; i < n; i++) {
            for (int y = 0; y < m; y++) {
                JButton button = new JButton("" + "abcdefghijklmnopqrstuvwxyz".toCharArray()[randNum.nextInt("abcdefghijklmnopqrstuvwxyz".toCharArray().length)]);
                array_button[i][y] = button;
                button.addActionListener(this);
                panel1.add(button);
            }
        }

        //PANEL 2
        panel2.setLayout(new FlowLayout());
        panel2.setPreferredSize(new Dimension(175, 300));
        finish_word.setPreferredSize(new Dimension(101, 25));
        finish_word.addActionListener(e -> System.out.println(current_word));
        JLabel score_label = new JLabel("Score: " + score);
        check_word_results = new JLabel("");
        JButton check_word_btn = new JButton("Check Selected Word");
        JButton reset_word = new JButton("Reset Word");
        reset_word.addActionListener(e -> reset());
        check_word_btn.addActionListener(e -> {
            for (int i = 0; i < words_array.length; i++) {
                if (words_array[i].contains(current_word)) {
                    possible_selected_word = true;
                    break;
                } else possible_selected_word = false;
            }
            if (possible_selected_word)
                check_word_results.setText("<html>You can continue the<br>already selected string.</html>");
            else
                check_word_results.setText("<html>You can't continue<br>the already selected string.</html>");
        });
        score_label.setPreferredSize(new Dimension(200, 50));
        score_label.setHorizontalAlignment(JLabel.CENTER);
//        check_word_results.setPreferredSize(new Dimension());
//        check_word_results.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 6));
        check_word_results.setHorizontalAlignment(JLabel.CENTER);
        panel2.add(reset_word);
        panel2.add(score_label);
        panel2.add(check_word_btn);
        panel2.add(check_word_results);

        //FRAME
        add(nav_bar, BorderLayout.NORTH);
        add(panel1, BorderLayout.CENTER);
        add(panel2, BorderLayout.EAST);
        setMinimumSize(new Dimension(700, 300));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setMaximumSize(new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight()));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Word Search");
//        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        clicked_buttons = new int[n][m];
        secondary_buttons = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int y = 0; y < m; y++) {
                if (e.getSource() == array_button[i][y]) {
                    clicked_buttons[i][y] = 11; // The character index which is added to the current word
                    times_clicked_btn++;
                    guessing = true;
                    array_button[i][y].setBackground(Color.BLUE);
                    array_button[i][y].setEnabled(false);
                    first_button_clicked = true;
                    current_word = current_word + array_button[i][y].getText();
                    try {
                        secondary_buttons[i + 1][y] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                        if (!array_button[i + 1][y].getModel().isEnabled())
                            array_button[i + 1][y].setBackground(Color.BLUE);
                        else
                            array_button[i + 1][y].setBackground(Color.green);
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i + 1][y + 1] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                        if (!array_button[i + 1][y + 1].getModel().isEnabled())
                            array_button[i + 1][y + 1].setBackground(Color.BLUE);
                        else
                            array_button[i + 1][y + 1].setBackground(Color.green);
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i + 1][y - 1] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                        if (!array_button[i + 1][y - 1].getModel().isEnabled())
                            array_button[i + 1][y - 1].setBackground(Color.BLUE);
                        else
                            array_button[i + 1][y - 1].setBackground(Color.green);
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i - 1][y + 1] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                        if (!array_button[i - 1][y + 1].getModel().isEnabled())
                            array_button[i - 1][y + 1].setBackground(Color.BLUE);
                        else
                            array_button[i - 1][y + 1].setBackground(Color.green);
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i - 1][y - 1] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                        if (!array_button[i - 1][y - 1].getModel().isEnabled())
                            array_button[i - 1][y - 1].setBackground(Color.BLUE);
                        else
                            array_button[i - 1][y - 1].setBackground(Color.green);
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i][y + 1] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                        if (!array_button[i][y + 1].getModel().isEnabled())
                            array_button[i][y + 1].setBackground(Color.BLUE);
                        else
                            array_button[i][y + 1].setBackground(Color.green);
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i][y - 1] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                        if (!array_button[i][y - 1].getModel().isEnabled())
                            array_button[i][y - 1].setBackground(Color.BLUE);
                        else
                            array_button[i][y - 1].setBackground(Color.green);
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i - 1][y] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                        if (!array_button[i - 1][y].getModel().isEnabled())
                            array_button[i - 1][y].setBackground(Color.BLUE);
                        else
                            array_button[i - 1][y].setBackground(Color.green);
                    } catch (Throwable ignored) {
                    }
//                        clicked_button.setBackground(Color.getColor("r=238,g=238,b=238"));
                } else if (clicked_buttons[i][y] != 11) clicked_buttons[i][y] = 0;
            }
        }

        if (first_button_clicked) {
            for (int i = 0; i < n; i++) {
                for (int y = 0; y < m; y++) {
                    if (clicked_buttons[i][y] == 0) {
                        array_button[i][y].setBackground(Color.getColor("r=238,g=238,b=238"));
                        array_button[i][y].setEnabled(false);
                    }
                    if (secondary_buttons[i][y] == 1) {
                        array_button[i][y].setBackground(Color.GREEN);
                        array_button[i][y].setEnabled(true);
                    }
                    if (clicked_buttons[i][y] == 11) {
                        array_button[i][y].setBackground(Color.BLUE);
                        array_button[i][y].setEnabled(false);
                    }
                }
            }
        }
        if (times_clicked_btn == 2) {
            panel2.add(finish_word);
            panel2.updateUI();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Checker checker = new Checker();
        words_array = Checker.array;
        System.out.println(Arrays.toString(words_array));
        new Main();
        System.out.println(words_array.length);
    }
}

