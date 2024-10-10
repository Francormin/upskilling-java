import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Gestión de Lámparas (con List)
        /*

        List<Lamp> lamps = new ArrayList<>();

        lamps.add(new Lamp("Kitchen"));
        lamps.add(new Lamp("Living Room"));
        lamps.add(new Lamp("Bedroom"));
        lamps.add(new Lamp("Bathroom"));
        lamps.add(new Lamp("Garden"));

        lamps.get(0).turnOn();
        lamps.get(1).turnOn();
        lamps.get(4).turnOn();

        lamps.get(0).turnOff();

        List<String> lampNames = new ArrayList<>();
        System.out.println("LAMPS:");
        for (Lamp lamp : lamps) {
            String name = lamp.getName();
            lampNames.add(name);

            System.out.println(lamp);
        }

        boolean containsCertainName = lampNames.contains("Dining Room");
        System.out.println("\nIs a lamp with a certain name?: " + containsCertainName);

        Collections.sort(lampNames);
        System.out.println("\nLamps sorted by name:");
        for (String lampName : lampNames) {
            System.out.println(lampName);
        }

        */

        // Gestión de Productos (con Map)
        /*

        Map<String, Double> products = new HashMap<>();

        Product product1 = new Product("T-shirt", 13.99);
        Product product2 = new Product("Shirt", 16.99);
        Product product3 = new Product("Trousers", 15.99);
        Product product4 = new Product("Shoes", 14.99);
        Product product5 = new Product("Jacket", 17.99);

        products.put(product1.getName(), product1.getPrice());
        products.put(product2.getName(), product2.getPrice());
        products.put(product3.getName(), product3.getPrice());
        products.put(product4.getName(), product4.getPrice());
        products.put(product5.getName(), product5.getPrice());

        System.out.println("\nPRODUCTS:");
        for (Map.Entry<String, Double> productsEntry : products.entrySet()) {
            String productName = productsEntry.getKey();
            Double productPrice = productsEntry.getValue();

            System.out.println(productName + ": $" + productPrice);
        }

        // Extraer las entradas del Map a una List
        List<Map.Entry<String, Double>> productList = new ArrayList<>(products.entrySet());

        // Ordenar por nombre de producto (clave)
        for (int i = 0; i < productList.size() - 1; i++) {
            for (int j = i + 1; j < productList.size(); j++) {
                if (productList.get(i).getKey().compareTo(productList.get(j).getKey()) > 0) {
                    // Intercambio de elementos
                    Map.Entry<String, Double> temp = productList.get(i);
                    productList.set(i, productList.get(j));
                    productList.set(j, temp);
                }
            }
        }

        System.out.println("\nProducts sorted by name:");
        for (Map.Entry<String, Double> product : productList) {
            System.out.println(product.getKey() + ": $" + product.getValue());
        }

        // Ordenar por precio (valor)
        for (int i = 0; i < productList.size() - 1; i++) {
            for (int j = i + 1; j < productList.size(); j++) {
                if (productList.get(i).getValue() > productList.get(j).getValue()) {
                    // Intercambio de elementos
                    Map.Entry<String, Double> temp = productList.get(i);
                    productList.set(i, productList.get(j));
                    productList.set(j, temp);
                }
            }
        }

        System.out.println("\nProducts sorted by price:");
        for (Map.Entry<String, Double> product : productList) {
            System.out.println(product.getKey() + ": $" + product.getValue());
        }

        */

        // Ejercicio de gestión de tareas
        /*

        TaskManager taskManager = new TaskManager();

        taskManager.addTask("Wash the dishes");
        taskManager.addTask("Take out the trash");
        taskManager.addTask("Make the bed");
        taskManager.addTask("Take a walk");
        taskManager.addTask("Read a book");

        try {
            taskManager.removeTask(4);
            taskManager.markTaskAsCompleted(3);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("There is not task found in that index. Error message: " + e.getMessage());
        }

        System.out.println("Tasks List:");
        taskManager.printTaskList();

        List<String> tasks = taskManager.getTasks();

        System.out.println();
        boolean isTaskInsideList = tasks.contains("Wash the dishes");
        System.out.println("Is the task in the list?: " + isTaskInsideList);
        boolean isTheListEmpty = tasks.isEmpty();
        System.out.println("Is the list empty?: " + isTheListEmpty);

        Collections.sort(tasks);
        System.out.println();
        System.out.println("Tasks ordered alphabetically:");
        taskManager.printTaskList();

        Object[] tasksArray = tasks.toArray();
        System.out.println();
        System.out.println("Array of tasks: " + Arrays.toString(tasksArray));

        */

        // Ejercicio de gestión de invitados a una fiesta
        /*

        PartyGuestList partyGuestList = new PartyGuestList();

        partyGuestList.addGuest("Steve Jobs");
        partyGuestList.addGuest("Bill Gates");
        partyGuestList.addGuest("Mark Zuckerberg");
        partyGuestList.addGuest("Jeff Bezos");
        partyGuestList.addGuest("Elon Musk");
        // Do not add duplicate elements
        // partyGuestList.addGuest("Elon Musk");

        Set<String> guestsList = partyGuestList.getGuests();

        System.out.println("List of guests: " + guestsList);
        System.out.println("Is the guest in the list?: " + partyGuestList.isGuestInList("Jeff Besos"));

        boolean didRemove = partyGuestList.removeGuest("Steve Jobs");
        if (didRemove) {
            System.out.println("Guest removed successfully");
            System.out.println("List of guests: " + guestsList);
        } else
            System.out.println("There is no current guest with that name in the list");

        System.out.println("Total number of guests: " + partyGuestList.getTotalGuests());
        System.out.println("Is the guests list empty?: " + guestsList.isEmpty());

        */

        // Ejercicio de registro de alumnos y calificaciones
        /*

        StudentRecord studentRecord = new StudentRecord();

        studentRecord.addStudent("John Doe", 8);
        studentRecord.addStudent("Jack Doe", 6);
        studentRecord.addStudent("James Doe", 9);
        studentRecord.addStudent("Jerome Doe", 7);
        studentRecord.addStudent("Jamal Doe", 5);
        // Overwrites the value if the key is already in the HashMap
        // studentRecord.addStudent("Jamal Doe", 10);

        Map<String, Integer> studentRecordList = studentRecord.getStudentRecord();

        System.out.println("Student Record: " + studentRecordList);
        System.out.println("Is student in record?: " + studentRecord.isStudentInRecord("Jacob Doe"));
        System.out.println("Score of student Jerome Doe: " + studentRecord.getGrade("Jerome Doe"));

        try {
            int studentScore = studentRecord.removeStudent("Jack Doe");
            System.out.println("Student removed successfully");
        } catch (NullPointerException e) {
            System.out.println("There is no current student with that name in the list");
            // System.out.println("Removing student error: " + e.getMessage());
        }

        System.out.println("Student Record: " + studentRecordList);
        System.out.println("Does any student have a score of 10?: " + studentRecordList.containsValue(10));
        System.out.println("Student names: " + studentRecordList.keySet());

        */
    }
}