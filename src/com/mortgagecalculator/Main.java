package com.mortgagecalculator;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main {

    static final int MONTHS_IN_YEAR = 12;
    static final int PERCENT = 100;
    private static CsvWriter csvWriter;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        try {
            FileWriter writer  = new FileWriter(FileProvider.getFile());
            csvWriter = new CsvWriter(writer);
            csvWriter.writeHeader();
        }catch (IOException e) {
            System.out.println("Some error occured when initializing the CsvWriter: " + e.getMessage());
            return;
        }


        System.out.println("Please enter the amount");


        int amount;
        int period;
        double interestRate;

        try {
            amount =Integer.parseInt(scanner.nextLine());

        }catch (NumberFormatException e) {
            System.out.println("The amount is mandatory to be numeric!");
            return;
        }

        System.out.println("Please enter the loan period in years:");

        try {
            period = Integer.parseInt(scanner.nextLine());

        }catch (NumberFormatException e) {
            System.out.println("The period is mandatory to be numeric!");
            return;
        }

        System.out.println("Please enter the annual interest rate");

        try {
            interestRate = Double.parseDouble(scanner.nextLine());

        }catch (NumberFormatException e) {
            System.out.println("The period is mandatory to be numeric!");
            return;

        }
        double balance = amount;
        for(int month=1; month<= period + MONTHS_IN_YEAR; month++) {
            double lastMonthBalance = balance;
            double monthlyMortgage = calculatorMortgage(amount, period, interestRate);
            double monthlyInterest = calculateInterest(lastMonthBalance, interestRate);
            double paidAmount = monthlyMortgage - monthlyInterest;

            balance = (lastMonthBalance - paidAmount) < 0 ? 0 : (lastMonthBalance - paidAmount);

            try {
                csvWriter.writeRecord(month, monthlyMortgage, balance, monthlyInterest, paidAmount);
            }catch (IOException e) {
                System.out.println("Error while writting the csv file: " + e.getMessage());
            }


        }
        try {
            csvWriter.closeFile();

        }catch (IOException e){
            System.out.println("Something went wrong when trying to close the csv.file: " + e.getMessage());

        }

    }

    private static double calculatorMortgage(int amount, int period, double interestRate) {
        double monthlyRate = interestRate / PERCENT / MONTHS_IN_YEAR;
        return (monthlyRate * amount) / (1 - Math.pow(1 + monthlyRate,(-period * MONTHS_IN_YEAR)));

    }

    private static double calculateInterest(double balance, double interestRate){
        double interestPerYear = balance * interestRate / PERCENT;
        return interestPerYear / MONTHS_IN_YEAR;

    }
}