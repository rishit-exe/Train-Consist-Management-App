import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Bogie{
    String name;
    int capacity;

    Bogie(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }
}

public class Train {

    public static List<Bogie> filterBogiesByCapacity(List<Bogie> bogies, int threshold) {
        return bogies.stream()
                .filter(b -> b.capacity > threshold)
                .toList();
    }

    public static Map<String, List<Bogie>> groupBogiesByType(List<Bogie> bogies){
        return bogies.stream()
                .collect(Collectors.groupingBy(b->b.name));
    }

    public static int aggregateCapacity(List<Bogie> bogies){
        return bogies.stream()
                .map(b->b.capacity).reduce(0, Integer::sum);
    }

    private static final Pattern TRAIN_PATTERN = Pattern.compile("TRN-\\d{4}");
    private static final Pattern CARGO_PATTERN = Pattern.compile("PET-[A-Z]{2}");

    public static boolean isValidTrainId(String trainId) {
        return trainId != null && TRAIN_PATTERN.matcher(trainId).matches();
    }

    public static boolean isValidCargoCode(String cargoCode) {
        return cargoCode != null && CARGO_PATTERN.matcher(cargoCode).matches();
    }

    static class GoodsBogie {
        String type;
        String cargo;


        GoodsBogie(String type, String cargo){
            this.type = type;
            this.cargo = cargo;
        }
    }

    static boolean allMatch(List<GoodsBogie> goodsBogies) {
        return goodsBogies.stream().allMatch(bogie ->
                bogie != null
                        && bogie.type != null
                        && bogie.cargo != null
                        && (!"Cylindrical".equalsIgnoreCase(bogie.type)
                        || "Petroleum".equalsIgnoreCase(bogie.cargo))
        );
    }

    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println("       === Train Consist Management App === ");
        System.out.println("========================================================\n");

        List<String> trainConsist = new LinkedList<>();

        System.out.println("Train initialized successfully...");
        System.out.println("Inital Bogie Count: " + trainConsist.size());
        System.out.println("Current Train Consist: " + trainConsist);

        System.out.println("\nSystem ready for operations...");

        System.out.println("===========================");
        System.out.println("Add Passenger Bogies to Train (UC2)");
        System.out.println("===========================");

        List<String> passengerBogies = new ArrayList<>();

        passengerBogies.add("Sleeper");
        passengerBogies.add("AC Chair");
        passengerBogies.add("First Class");

        System.out.println("\nAfter adding Bogies: ");
        System.out.println("Passenger Bogies: " + passengerBogies);

        passengerBogies.remove("AC Chair");
        System.out.println("\nAfter removing 'AC Chair': ");
        System.out.println("Passenger Bogies: " + passengerBogies);

        System.out.println("\nChecking if 'Sleeper' exists: ");
        System.out.println("Contains Sleeper? : " + passengerBogies.contains("Sleeper"));

        System.out.println("\nFinal Train Passenger Consist: ");
        System.out.println(passengerBogies);

        System.out.println("\nCRUD operations(UC2) completed successfully...");


        System.out.println("\n\n===========================");
        System.out.println("Track Unique Bogie IDs (UC3)");
        System.out.println("===========================\n");

        Set<String> bogieIds = new HashSet<>();
        bogieIds.add("BG101");
        bogieIds.add("BG102");
        bogieIds.add("BG103");
        bogieIds.add("BG104");
        //duplicating the entries
        bogieIds.add("BG101");
        bogieIds.add("BG102");


        System.out.println("Bogie IDs After Insertion: ");
        System.out.println(bogieIds);

        System.out.println("\nNote:\nDuplicates are automatically ignored by the HashSet.\n");

        System.out.println("Uniqueness validation completed... (UC3)");

        System.out.println("\n\n=======================================");
        System.out.println("  Maintain Ordered Bogie Consist (UC4)");
        System.out.println("========================================\n");

        trainConsist.add("Engine");
        trainConsist.add("Sleeper");
        trainConsist.add("AC");
        trainConsist.add("Cargo");
        trainConsist.add("Guard");

        System.out.println("Initial Train Consist: ");
        System.out.println(trainConsist);

        trainConsist.add(2, "Pantry Car");
        System.out.println("\nAfter Inserting 'Pantry Car' at position 2: ");
        System.out.println(trainConsist);

       trainConsist.removeFirst();
       trainConsist.removeLast();
       
        System.out.println("\nAfter removing First and Last Bogie: ");
        System.out.println(trainConsist);

        System.out.println("\nOrdered consist operations completed... (UC4)");

        System.out.println("\n\n========================================");
        System.out.println("  Preserve Insertion Order of Bogies (UC5)");
        System.out.println("=========================================\n");

        Set<String> formation = new LinkedHashSet<>();
        formation.add("Engine");
        formation.add("Sleeper");
        formation.add("Cargo");
        formation.add("Guard");
        //Duplicating entries for test
        formation.add("Guard");
        formation.add("Cargo");

        System.out.println("Final Train Formation:");
        System.out.println(formation);

        System.out.println("\nNote:\nLinkedHashSet preserves the insertion order and removes duplicates automatically.\n");
        System.out.println("Formation setup completed... (UC5)");

        /* -------------------UC6_Deprecated------------------
        System.out.println("\n\n========================================");
        System.out.println("  Map Bogie to Capacity (HashMap) (UC6)");
        System.out.println("========================================\n");

        Map<String, Integer> capacityMap = new HashMap<>();
        capacityMap.put("First Class", 24);
        capacityMap.put("Cargo", 120);
        capacityMap.put("Sleeper", 72);
        capacityMap.put("AC Chair", 56);

        System.out.println("Bogie Capacity Details:");
        for (Map.Entry<String, Integer> entry : capacityMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println("\nBogie-capacity mapping completed... (UC6)");*/

        System.out.println("\n\n========================================");
        System.out.println("  Sort Bogies by Capacity (Comparator) (UC7)");
        System.out.println("========================================\n");

        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("First Class", 24));
        bogies.add(new Bogie("Cargo", 120));
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 56));
        bogies.add(new Bogie("AC Chair", 60));
        bogies.add(new Bogie("Sleeper", 70));

        System.out.println("Before sorting");
        for (Bogie b : bogies) {
            System.out.printf("%s  ->  %d\n", b.name, b.capacity);
        }

        bogies.sort(Comparator.comparingInt(b->b.capacity));
        System.out.println("\nAfter sorting");
        for (Bogie b : bogies) {
            System.out.printf("%s  ->  %d\n", b.name, b.capacity);
        }

        System.out.println("Sorting completed... (UC7)");

        System.out.println("\n\n========================================");
        System.out.println("  Filter Passenger Bogies Using Streams (UC8)");
        System.out.println("========================================\n");

        Stream<Bogie> stream = bogies.stream();
        List<Bogie> filteredList = filterBogiesByCapacity(bogies, 60);

        System.out.println("Filtered Bogies (Capacity > 60)");

        filteredList.forEach(b ->
                        System.out.println(b.name + " - " + b.capacity));

        System.out.println("\nFiltering completed... (UC8)");



        System.out.println("\n\n========================================");
        System.out.println("  Group Bogies by Type (Collectors.groupingBy) (UC9)");
        System.out.println("========================================\n");

        Map<String, List<Bogie>> groupedBogies = groupBogiesByType(bogies);

        System.out.println("Grouped Bogies: \n");
        for(Map.Entry<String, List<Bogie>> entry : groupedBogies.entrySet()){
            System.out.println("Bogie Type: " + entry.getKey());
            for (Bogie b : entry.getValue()) {
                System.out.println("   Capacity -> " + b.capacity);
            }
        }

        System.out.println("\nGrouping completed... (UC9)");


        System.out.println("\n\n========================================");
        System.out.println("  Count Total Seats in Train (reduce) (UC10)");
        System.out.println("========================================\n");

        System.out.println("Total Seating Capacity of Train: " + aggregateCapacity(bogies));

        System.out.println("\nAggregation completed... (UC10)");



        System.out.println("\n\n========================================");
        System.out.println("  Validate Train ID & Cargo Codes (Regex) (UC11)");
        System.out.println("========================================\n");

        Scanner obj = new Scanner(System.in);

        System.out.print("Enter Train ID (Format: TRN-1234): ");
        String trainId = obj.nextLine();

        System.out.print("Enter Cargo Code (Format: PET-AB): ");
        String cargoCode = obj.nextLine();

        System.out.println("Train ID Valid: " + isValidTrainId(trainId));
        System.out.println("Cargo Code Valid: " + isValidCargoCode(cargoCode));

        obj.close();

        System.out.println("\nValidation completed... (UC11)");



        System.out.println("\n\n========================================");
        System.out.println("  Safety Compliance Check for Goods Bogies (UC12)");
        System.out.println("========================================\n");

        List<GoodsBogie> goodsBogies = new ArrayList<>();

        goodsBogies.add(new GoodsBogie("Cylindrical", "Petroleum"));
        goodsBogies.add(new GoodsBogie("Open", "Coal"));
        goodsBogies.add(new GoodsBogie("Box", "Grain"));
        goodsBogies.add(new GoodsBogie("Cylindrical", "Coal"));

        System.out.println("Goods Bogies in Train: ");
        for(GoodsBogie bogieX : goodsBogies){
            System.out.println(bogieX.cargo + " -> " + bogieX.type);
        }

        boolean safetyCompliant = allMatch(goodsBogies);
        System.out.println("\nSafety Compliance Status: " + safetyCompliant);
        System.out.println(safetyCompliant
            ? "Train formation is SAFE."
            : "Train formation is NOT SAFE.");

        System.out.println("\nSafety Validation completed... (UC12)");

    }
}