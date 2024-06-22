package utils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CLI {
    
   public DAOAction readChoice(List<String> choices) {
        /*
         * gets string from choices list and maps it to enum value
         * 
         */
        Scanner scanner = new Scanner(System.in);
        int choiceIndex = -1;

        while (true) {
            IntStream.range(0, choices.size())
                .mapToObj(i -> Integer.toString(i) + ": " + choices.get(i))
                .forEach(s -> System.out.println(s));

            System.out.print("Enter your choice (0-" + (choices.size() - 1) + "): ");
            String input = scanner.nextLine();

            try {
                choiceIndex = Integer.parseInt(input);
                if (choiceIndex >= 0 && choiceIndex < choices.size()) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 0 and " + (choices.size() - 1) + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        scanner.close();
        return DAOAction.values()[choiceIndex];
    }

}
