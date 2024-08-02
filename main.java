import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

 class AppleDistribution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> apples = new ArrayList<>();
        while (true) {
            System.out.print("Enter apple weight in gram (-1 to stop): ");
            int weight = scanner.nextInt();
            if (weight == -1) break;
            apples.add(weight);
        }

        int totalPaid = 100,ramPaid = 50,shamPaid = 30,rahimPaid = 20;
        int totalWeight = apples.stream().mapToInt(Integer::intValue).sum();
        int ramTarget = ramPaid * totalWeight / totalPaid;
        int shamTarget = shamPaid * totalWeight / totalPaid;
        int rahimTarget = rahimPaid * totalWeight / totalPaid;

        Collections.sort(apples, Collections.reverseOrder());

        List<Integer> ramApples = new ArrayList<>();
        List<Integer> shamApples = new ArrayList<>();
        List<Integer> rahimApples = new ArrayList<>();

        distributeApples(apples, ramApples, shamApples, rahimApples, ramTarget, shamTarget, rahimTarget);

        System.out.println("Distribution Result:");
        System.out.println("Ram : " + ramApples);
        System.out.println("Sham: " + shamApples);
        System.out.println("Rahim: " + rahimApples);
        scanner.close();
    }

    private static void distributeApples(
            List<Integer> apples, List<Integer> ramApples,
            List<Integer> shamApples, List<Integer> rahimApples,
            int ramTarget, int shamTarget, int rahimTarget) {
        int ramTotal = 0;
        int shamTotal = 0;
        int rahimTotal = 0;

        for (int weight : apples) {
            if (ramTotal + weight <= ramTarget) {
                ramApples.add(weight);
                ramTotal += weight;
            } else if (shamTotal + weight <= shamTarget) {
                shamApples.add(weight);
                shamTotal += weight;
            } else if (rahimTotal + weight <= rahimTarget) {
                rahimApples.add(weight);
                rahimTotal += weight;
            } else {
                rahimApples.add(weight);
                rahimTotal += weight;
            }
        }
    }
}




