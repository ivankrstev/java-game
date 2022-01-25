import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Checker {
    public String[] array;

    public Checker() throws FileNotFoundException {
        File txt = new File("dictionary.txt");
        Scanner scan = new Scanner(txt);
        ArrayList<String> data = new ArrayList<>();
        while (scan.hasNextLine()) {
            data.add(scan.nextLine());
        }
        array = data.toArray(new String[]{});
    }
}

class fileOperate {
    public String[][] array;
    String filename = "save_data.txt";
    File file = new File(filename);
    JFrame frame;
    ArrayList<String[]> data = new ArrayList<>();
    boolean created;

    fileOperate() throws IOException {
        if (!file.exists()) {
            try {
                created = file.createNewFile();
            } catch (Throwable ee) {
                System.out.println(created);
                JOptionPane.showMessageDialog(frame, "The game was not able to create a save file!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void read() throws FileNotFoundException {
        if (file.exists()) {
            data = new ArrayList<>();
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String[] extracted = scan.nextLine().split(" ");
                data.add(extracted);
            }
            array = data.toArray(new String[][]{});
            scan.close();
        }
    }

    public void write(int m, int n) throws IOException {
        if (file.exists()) {
            read();
            FileWriter myWriter = new FileWriter(filename);
            int i;
            for (i = 0; i < data.size(); i++) {
                myWriter.write(array[i][0] + " " + array[i][1] + " " + array[i][2] + "\n");
            }
            myWriter.write(i + 1 + " " + m + " " + n);
            myWriter.close();
        }
    }
}

public class Main extends JFrame implements ActionListener {
    static Random randNum = new Random();
    public static int m; //rows
    public static int n; //columns
    JButton[][] array_button = new JButton[n][m];
    JPanel panel1 = new JPanel(), panel2 = new JPanel(new FlowLayout());
    JFrame frame;
    JLabel check_word_results, intro_text;
    JTextField setM = new JTextField(4), setN = new JTextField(4);
    JButton finish_word = new JButton("Finish Word");
    boolean guessing = false, first_button_clicked = false, possible_selected_word;
    int[][] clicked_buttons, secondary_buttons, finished_words_letters;
    static String[][] saved_data;
    static String current_word = "";
    int times_clicked_btn = 0, score = 0, final_score = 0;
    static String[] words_array;

    public void reset() {
        for (int i = 0; i < n; i++) {
            for (int y = 0; y < m; y++) {
                array_button[i][y].setBackground(Color.getColor("r=238,g=238,b=238"));
                if (clicked_buttons[i][y] != 11)
                    array_button[i][y].setEnabled(true);
                if (finished_words_letters[i][y] == 1)
                    array_button[i][y].setEnabled(false);
            }
        }
        clicked_buttons = new int[n][m];
        current_word = "";
        times_clicked_btn = 0;
        panel2.remove(finish_word);
        check_word_results.setText("");
        panel2.updateUI();
    }

    public void newGame() {
        score = 0;
        final_score = 0;
        clicked_buttons = new int[n][m];
        finished_words_letters = new int[n][m];
        panel1.removeAll();
        array_button = new JButton[n][m];
        for (int i = 0; i < n; i++) {
            for (int y = 0; y < m; y++) {
                JButton button = new JButton("" + "abcdefghijklmnopqrstuvwxyz".toCharArray()[randNum.nextInt(26)]);
                array_button[i][y] = button;
                button.addActionListener(this);
                button.setEnabled(true);
                panel1.add(button);
            }
        }
        reset();
        int rand1 = (int) (Math.random() * m);
        int rand2 = (int) (Math.random() * n);
        array_button[rand2][rand1].doClick();
        panel1.updateUI();
        panel1.setLayout(new GridLayout(n, m));
        this.pack(); //Window is sized to fit the preferred size and layouts of its subcomponents
    }

    public Main() {
        //NAVBAR
        JPanel nav_bar = new JPanel();
        JButton btn_new = new JButton("Start Game");
        btn_new.addActionListener(e -> {
            boolean popup_opened = false;
            guessing = false;
            score = 0;
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
                newGame();
                fileOperate fileRead;
                try {
                    fileRead = new fileOperate();
                    fileRead.write(m, n);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JButton btn_exit = new JButton("End Game");
        btn_exit.addActionListener(e -> {
            //Pop Up Dialog For Result
            String text = "You scored " + final_score + ".\n" + "Click Yes if you want to start a new game.\n" + "Click No to exit.";
            int a = JOptionPane.showConfirmDialog(frame, text, "Results", JOptionPane.YES_NO_OPTION);
            if (a == 1)
                System.exit(0);
            if (a == 0) {
                newGame();
            }
            if (a == -1)
                System.exit(0);
        });
        JButton btn_load = new JButton("Load Game");
        btn_load.addActionListener(e -> {
            fileOperate fileRead;
            try {
                fileRead = new fileOperate();
                fileRead.read();
                saved_data = fileRead.array;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            boolean isValid = false;
            if (saved_data != null) {
                for (int i = 0; i < saved_data.length; i++) {
                    if (saved_data[i] != null) {
                        if (saved_data[i][0] != null && saved_data[i][1] != null && saved_data[i][1] != null)
                            isValid = true;
                        else isValid = false;
                    }
                }
            }
            if (isValid) {
                String[] choices = new String[saved_data.length];
                for (int i = 0; i < saved_data.length; i++)
                    choices[i] = saved_data[i][1] + "x" + saved_data[i][2];
                String input = (String) JOptionPane.showInputDialog(null, "Choose",
                        "Select Layout", JOptionPane.QUESTION_MESSAGE, null,
                        choices,
                        "No Data");
                if (input != null) {
                    final String[] parts = input.split("x");
                    m = Integer.parseInt(parts[0]);
                    n = Integer.parseInt(parts[1]);
                    newGame();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No Saved Data.",
                        "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        });
        JLabel labelM = new JLabel(" Set width: ");
        JLabel labelN = new JLabel(" Set height: ");

        nav_bar.setLayout(new GridBagLayout());
        nav_bar.setPreferredSize(new Dimension(0, 50));
        nav_bar.add(labelM);
        nav_bar.add(setM);
        nav_bar.add(labelN);
        nav_bar.add(setN);
        nav_bar.add(btn_new);
        nav_bar.add(btn_load);
        nav_bar.add(btn_exit);

        //PANEL 2
        panel2.setLayout(new FlowLayout());
        panel2.setPreferredSize(new Dimension(175, 300));
        finish_word.setPreferredSize(new Dimension(101, 25));
        finish_word.addActionListener(e -> {
            for (int i = 0; i < words_array.length; i++) {
                if (Objects.equals(words_array[i], current_word)) {
                    score = (int) Math.pow(2, current_word.length());
                    break;
                } else score = 0;
            }
            final_score += score;
            reset();
        });
        check_word_results = new JLabel("");
        JButton check_word_btn = new JButton("Check Selected Word");
        check_word_btn.addActionListener(e -> {
            for (int i = 0; i < words_array.length; i++) {
                if (words_array[i].startsWith(current_word)) {
                    possible_selected_word = true;
                    break;
                } else possible_selected_word = false;
            }
            if (possible_selected_word)
                check_word_results.setText("<html>You can continue the<br>already selected string.</html>");
            else
                check_word_results.setText("<html>You can't continue<br>the already selected string.</html>");
        });
        check_word_results.setHorizontalAlignment(JLabel.CENTER);
        panel2.add(intro_text = new JLabel("Start guessing!"));
        panel2.add(check_word_btn);
        panel2.add(check_word_results);

        //FRAME
        this.add(nav_bar, BorderLayout.NORTH);
        this.add(panel1, BorderLayout.CENTER);
        this.add(panel2, BorderLayout.EAST);
        this.setMinimumSize(new Dimension(700, 300));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setMaximumSize(new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight()));
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); // It centers the Window in the middle of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Word Search");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        secondary_buttons = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int y = 0; y < m; y++) {
                if (e.getSource() == array_button[i][y]) {
                    clicked_buttons[i][y] = 11; // The character index which is added to the current word
                    times_clicked_btn++;
                    guessing = true;
                    array_button[i][y].setBackground(Color.BLUE);
                    array_button[i][y].setEnabled(false);
                    finished_words_letters[i][y] = 1;
                    first_button_clicked = true;
                    current_word += array_button[i][y].getText();
                    try {
                        secondary_buttons[i + 1][y] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i + 1][y + 1] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i + 1][y - 1] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i - 1][y + 1] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i - 1][y - 1] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i][y + 1] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i][y - 1] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                    } catch (Throwable ignored) {
                    }
                    try {
                        secondary_buttons[i - 1][y] = 1; // The button character index which is one of the neighbours of the button with the character that was added to the current word
                    } catch (Throwable ignored) {
                    }
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
                    if (secondary_buttons[i][y] == 1 && finished_words_letters[i][y] != 1) {
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
            panel2.remove(intro_text);
            panel2.add(finish_word);
            panel2.updateUI();
        }
    }

    public static void main(String[] args) throws IOException {
        Checker check_words = new Checker();
        words_array = check_words.array;
        new Main();
    }
}
