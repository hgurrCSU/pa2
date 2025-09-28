public class SongStats {
        private int numOfRatings = 0;
        private double mean = 0.0;
        private double sumOfSquares = 0.0;

        public void addRating(int rating) {
            numOfRatings++;

            double origMeanDiff = rating - mean;
            mean += origMeanDiff / numOfRatings;

            double newMeanDiff = rating - mean;
            sumOfSquares += newMeanDiff * origMeanDiff;
        }

        public int getNumOfRatings() {
            return numOfRatings;
        }

        public double getMean() {
            return mean;
        }

        public double getStdDev() {
            return numOfRatings > 0 ? Math.sqrt(sumOfSquares / numOfRatings) : 0.0;
        }
}