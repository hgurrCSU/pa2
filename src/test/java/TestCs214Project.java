import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import org.junit.jupiter.api.Test;

public class TestCs214Project {
    // Example of a JUnit5 test (this test can be removed)
    //@Test
    //public void exampleTest() {
        //assertEquals(2 + 2, 4);

    @Test
    public void testSongStatsCalculations() {
        SongStats stats = new SongStats();
        stats.addRating(1);
        stats.addRating(5);
        stats.addRating(5);
        stats.addRating(5);

        assertEquals(4, stats.getNumOfRatings());
        assertEquals(4.0, stats.getMean(), 1e-6);
        assertEquals(1.7320508075688772, stats.getStdDev(), 1e-6);
    }

    @Test
    public void testCsvProcessing1() throws Exception {
        String inputFile = "testInput1.csv";
        String outputFile = "testOutput1.csv";

        Files.write(Paths.get(inputFile), Arrays.asList(
            "song1,Sam,1",
            "song1,Ana,5",
            "song1,Leo,5",
            "song1,Eli,5",
            "song2,Mia,2",
            "song2,Amy,2",
            "song2,Tom,2",
            "song2,Max,2"
        ));

        Cs214Project.main(new String[]{inputFile, outputFile});

        List<String> lines = Files.readAllLines(Paths.get(outputFile));

        assertEquals("song,number of ratings,mean,standard deviation", lines.get(0));

        String[] song1 = lines.get(1).split(",");
        assertEquals("song1", song1[0]);
        assertEquals(4, Integer.parseInt(song1[1]));
        assertEquals(4.0, Double.parseDouble(song1[2]), 1e-6);
        assertEquals(1.7320508075688772, Double.parseDouble(song1[3]), 1e-6);

        String[] song2 = lines.get(2).split(",");
        assertEquals("song2", song2[0]);
        assertEquals(4, Integer.parseInt(song2[1]));
        assertEquals(2.0, Double.parseDouble(song2[2]), 1e-6);
        assertEquals(0.0, Double.parseDouble(song2[3]), 1e-6);
    }

    @Test
    public void testCsvProcessing2() throws Exception {
        String inputFile = "testInput2.csv";
        String outputFile = "testOutput2.csv";

        Files.write(Paths.get(inputFile), Arrays.asList(
            "Bohemian Rhapsody,Sam,4",
            "Sweet Home Alabama,Ana,4",
            "All Star,Leo,4",
            "All Star,Eli,2",
            "Sweet Home Alabama,Mia,2",
            "Sweet Home Alabama,Tom,3"
        ));

        Cs214Project.main(new String[]{inputFile, outputFile});

        List<String> lines = Files.readAllLines(Paths.get(outputFile));

        assertEquals("song,number of ratings,mean,standard deviation", lines.get(0));

        assertEquals("All Star", lines.get(1).split(",")[0]);
        assertEquals("Bohemian Rhapsody", lines.get(2).split(",")[0]);
        assertEquals("Sweet Home Alabama", lines.get(3).split(",")[0]);

        String[] allStar = lines.get(1).split(",");
        assertEquals(2, Integer.parseInt(allStar[1]));
        assertEquals(3.0, Double.parseDouble(allStar[2]), 1e-6);
        assertEquals(1.0, Double.parseDouble(allStar[3]), 1e-6);

        String[] bohemian = lines.get(2).split(",");
        assertEquals(1, Integer.parseInt(bohemian[1]));
        assertEquals(4.0, Double.parseDouble(bohemian[2]), 1e-6);
        assertEquals(0.0, Double.parseDouble(bohemian[3]), 1e-6);

        String[] sweetHome = lines.get(3).split(",");
        assertEquals(3, Integer.parseInt(sweetHome[1]));
        assertEquals(3.0, Double.parseDouble(sweetHome[2]), 1e-6);
        assertEquals(0.816496580927726, Double.parseDouble(sweetHome[3]), 1e-6);
    }
}