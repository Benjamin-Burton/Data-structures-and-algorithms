import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        double count = 1;
        while (true) {
            String nextLine = StdIn.readLine();
            if (nextLine.equals("")) {
                StdOut.println(champion);
                break;
            } else {
                if (StdRandom.bernoulli(1/count)) {
                    champion = nextLine;
                }   
            }
                count++;
        }
    }
}
