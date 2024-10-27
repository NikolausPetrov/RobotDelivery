import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        int numberOfRoutes = 1000;
        Thread[] threads = new Thread[numberOfRoutes];

        for (int i = 0; i < numberOfRoutes; i++) {
            threads[i] = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int rCount = countR(route);
                updateFrequencyMap(rCount);
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        printResults();
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countR(String route) {
        int count = 0;
        for (char c : route.toCharArray()) {
            if (c == 'R') {
                count++;
            }
        }
        return count;
    }

    public static synchronized void updateFrequencyMap(int rCount) {
        sizeToFreq.put(rCount, sizeToFreq.getOrDefault(rCount, 0) + 1);
    }

    public static void printResults() {
        int maxFrequency = 0;
        int mostFrequentCount = 0;

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentCount = entry.getKey();
            }
        }

        System.out.println("Самое частое количество повторений " + mostFrequentCount + " (встретилось " + maxFrequency + " раз)");
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() != mostFrequentCount) {
                System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
            }
        }
    }
}