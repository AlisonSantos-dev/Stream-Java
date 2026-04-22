package application;

import model.entities.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path = sc.nextLine();

        System.out.print("Enter salary: ");
        Double salary = sc.nextDouble();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            List<Employee> list = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
                line = br.readLine();
            }

            List<String> emails = list.stream()
                    .filter(e -> e.getSalary() > salary)
                    .map(Employee::getEmail)
                    .sorted()
                    .collect(Collectors.toList());

            System.out.println("Email of people whose salary is more than " + salary + ":");

            emails.forEach(System.out::println);

            double sum = list.stream()
                    .filter(e -> e.getName().toUpperCase().startsWith("M"))
                    .mapToDouble(Employee::getSalary)
                    .sum();

            System.out.println("Sum of salary of people whose name starts with 'M': " + sum);


        } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            sc.close();
    }
}
