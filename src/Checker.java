//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class Checker {
//    public static String[] array;
//
//    public Checker() throws FileNotFoundException {
//        File txt = new File("dictionary.txt");
//        Scanner scan = new Scanner(txt);
//        ArrayList<String> data = new ArrayList<String>();
//        while (scan.hasNextLine()) {
//            data.add(scan.nextLine());
//        }
//        array = data.toArray(new String[]{});
//    }
//
//    public static void main(String[] args) throws FileNotFoundException {
//        new Checker();
//    }
//}
