import java.io.*;
import java.util.*;
import java.nio.file.*;
import org.apache.commons.csv.*;

public class Cs214Project {
    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.println("ERROR: Expected 2 arguments (input.csv output.csv)");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        Map<String, SongStats> songStatsMap = new HashMap<>();

        try(Reader reader = Files.newBufferedReader(Paths.get(inputFile));

             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for(CSVRecord record : csvParser) {

                String song = record.get(0);
                int rating = Integer.parseInt(record.get(2));
                
                songStatsMap.putIfAbsent(song, new SongStats());
                songStatsMap.get(song).addRating(rating);
            }
            
        } catch(IOException e) {
            e.printStackTrace();
        }

        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile));
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                 CSVFormat.DEFAULT.builder().setHeader("song", "number of ratings", "mean", "standard deviation").build())) {

            songStatsMap.keySet().stream().sorted().forEach(song -> {
                    SongStats stats = songStatsMap.get(song);
                    try {
                        csvPrinter.printRecord(song, stats.getNumOfRatings(), stats.getMean(), stats.getStdDev());
                 } catch(IOException e) {
                        e.printStackTrace();
                    }
                });
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}