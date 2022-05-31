import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class ceshi {

    public static void main(String[] args) throws IOException {

//        File file = new File("resource/asd.txt");//文件路径1
//        FileReader fileReader = new FileReader(file);
//        LineNumberReader reader = new LineNumberReader(fileReader);
//        int number=64;
//        String txt = "";
//        int lines = 0;
//        for (int i = 0; i < 8; i++) {
//            while (txt != null) {
//                lines++;
//                txt = reader.readLine();
//                if (lines == number & number < 72) {
//                    System.out.println("第" + reader.getLineNumber() + "行:" + txt + "\n");
//                    number++;
//                }
//            }
//        }
//        reader.close();
//        fileReader.close();
        ArrayList<String> a = new ArrayList<>();
        a.add("RNBQKBNR");
        a.add("PPPPPPPP");
        a.add("_______1");
        a.add("_______2");
        a.add("_______3");
        a.add("_______5");
        a.add("pppppppp");
        a.add("rnbqkbnr");
        a.add("b");
        a.add("RNBQKBN_");
        for (int i = 0; i < 5; i++)
            a.remove(a.get(a.size() - 1));
        for (int i = 0; i < a.size(); i++)
            System.out.println(a.get(i));
    }
}