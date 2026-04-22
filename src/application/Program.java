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
            // Abre o arquivo no caminho informado usando BufferedReader
            // O try-with-resources garante que o arquivo será fechado automaticamente

            List<Employee> list = new ArrayList<>();
            // Cria uma lista para armazenar os funcionários lidos do arquivo

            String line = br.readLine();
            // Lê a primeira linha do arquivo

            while (line != null) {
                // Enquanto ainda existirem linhas no arquivo

                String[] fields = line.split(",");
                // Divide a linha em partes usando vírgula como separador
                // fields[0] = nome, fields[1] = email, fields[2] = salário

                list.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
                // Cria um objeto Employee com os dados da linha
                // Converte o salário de String para double
                // Adiciona o funcionário na lista

                line = br.readLine();
                // Lê a próxima linha do arquivo
            }

            List<String> emails = list.stream()
                    .filter(e -> e.getSalary() > salary)
                    // Filtra funcionários com salário maior que o valor informado

                    .map(Employee::getEmail)
                    // Pega apenas o email de cada funcionário filtrado

                    .sorted()
                    // Ordena os emails em ordem alfabética

                    .collect(Collectors.toList());
            // Converte o resultado do Stream para uma lista

            System.out.println("Email of people whose salary is more than " + salary + ":");
            // Exibe mensagem informando o filtro aplicado

            emails.forEach(System.out::println);
            // Percorre a lista de emails e imprime cada um no console

            double sum = list.stream()
                    .filter(e -> e.getName().toUpperCase().startsWith("M"))
                    // Filtra funcionários cujo nome começa com "M" (ignorando maiúsculo/minúsculo)

                    .mapToDouble(Employee::getSalary)
                    // Converte os funcionários filtrados para seus salários

                    .sum();
            // Soma todos os salários

            System.out.println("Sum of salary of people whose name starts with 'M': " + sum);
            // Exibe o resultado da soma

        } catch (IOException e) {
            // Captura possíveis erros de leitura do arquivo

            System.out.println("Error: " + e.getMessage());
            // Exibe mensagem de erro no console
        }

            sc.close();
    }
}
