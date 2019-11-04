
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Paper {
    public String title = "";
    public int index;
    public ArrayList<Integer> ciations = new ArrayList<Integer>();
    public ArrayList<Paper> citedBy = new ArrayList<Paper>();
}

public class Main {

    static void f(Paper x, int tier) {
        if (tier < 0)
            return;

        x.citedBy.stream().forEach((i) -> {
            System.out.println("TIER " + tier + ": " + i.title);
        });
        x.citedBy.stream().forEach((i) -> {
            f(i, tier - 1);
        });
    }

    public static void main(String args[]) throws Exception {

        Scanner tmpS = new Scanner(System.in);
        System.out.println("filename:");
        String fileName = tmpS.nextLine();
        System.out.println("keyword:");
        String keyword = tmpS.nextLine();
        System.out.println("tier:");
        int tier = tmpS.nextInt();
        tmpS.nextLine();

        Scanner sc = new Scanner(new File(fileName));

        System.out.println("Reading data");
        ArrayList<Paper> papers = new ArrayList<Paper>();
        int np = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < np; i++) {
            String line;

            Paper x = new Paper();
            while ((line = sc.nextLine()).length() != 0) {
                if (line.startsWith("#*"))
                    x.title = line.substring(2);
                else if (line.startsWith("#index"))
                    x.index = Integer.parseInt(line.substring(6));
                else if (line.startsWith("#%"))
                    x.ciations.add(Integer.parseInt(line.substring(2)));

                if (!sc.hasNextLine())
                    break;
            }

            papers.add(x);
        }
        System.out.println("Read");

        System.out.println("Building networks of all papers...");
        papers.forEach((i)->{
            i.ciations.forEach((j)->{
                papers.get(j).citedBy.add(i);
            });
        });
        System.out.println("Built\n");

        System.out.println("Looking for papers with keyword " + keyword);
        final String tmp = keyword;
        final int tmp2 = tier;
        papers.stream().filter(i -> i.title.contains(tmp)).forEach((i) -> {
            System.out.println("FOUND: " + i.title);
            f(i, tmp2);

            System.out.println();
        });
    }

}
