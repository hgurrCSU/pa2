import java.util.*;
import java.util.stream.Collectors;

public class UserAnalysis {
    private Map <String, Map<String, Double>> userRatings;

    private Set<String> allSongs;

    private Set<String> uncooperativeUsers;

    public UserAnalysis(){
        this.userRatings = new HashMap<>();
        this.allSongs = new HashSet<>();
        this.uncooperativeUsers = new HashSet<>();
    }

    public void addRating(String username, String song, double rating){
        allSongs.add(song);

        userRatings.putIfAbsent(username, new HashMap<>());

        userRatings.get(username).put(song, rating);

    }

    public Set<String> identifyUncooperativeUsers () {
        uncooperativeUsers.clear();

        for (Map.Entry<String, Map<String, Double>> userEntry : userRatings.entrySet()) {
            String username = userEntry.getKey();

            Map<String, Double> ratings = userEntry.getValue();

            Set<Double> distinctRatings = new HashSet<>(ratings.values());

            if(distinctRatings.size() <= 1){
                uncooperativeUsers.add(username);
            }
        }

        return uncooperativeUsers;
    }

    public void filterSongsFromUncooperativeUsers (){
        Set<String> songsToKeep = new HashSet<>();


        for (Map.Entry<String, Map<String, Double>> userEntry : userRatings.entrySet()) {
            String username = userEntry.getKey();

            if(uncooperativeUsers.contains(username)){
                continue;
            }

            songsToKeep.addAll(userEntry.getValue().keySet());


        }

        allSongs = songsToKeep;

         for (Map<String, Double> userMap : userRatings.values()){
            userMap.keySet().retainAll(songsToKeep);
         }
    }



    public List<String[]> generateUserProfiles(){
        List<String[]> records = new ArrayList<>();

        List<String> sortedUsers = userRatings.keySet().stream().filter(user -> !uncooperativeUsers.contains(user)).sorted().collect(Collectors.toList());

        List<String> sortedSongs = allSongs.stream().sorted().collect(Collectors.toList());

        for(String user : sortedUsers){
            Map<String, Double> userRatingMap = userRatings.get(user);

            for(String song : sortedSongs){
                Double rating = userRatingMap.getOrDefault(song, Double.NaN);

                records.add(new String[]  {user, song, Double.toString(rating)});


            }
        }

        return records;

    }


    public Set<String> getAllSongs(){
        return allSongs;
    }

    public Set<String> getUncooperativeUsers(){
        return uncooperativeUsers;
    }

    public Map<String, Map<String, Double>> getUserRatings(){
        return userRatings;
    }


}