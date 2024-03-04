package com.java.test2;
	import java.util.HashMap;
	import java.util.Map;
	import java.util.Scanner;

public class ATMInterface {
	    private Map<Integer, User> users;
	    private Scanner scanner;

	    public ATMInterface() {
	        users = new HashMap<>();
	        users.put(123456, new User(123456, 1234, 1000.0)); // Sample user
	        scanner = new Scanner(System.in);
	    }

	    public void start() {
	        System.out.println("Welcome to the ATM!");
	        System.out.print("Enter your user ID: ");
	        int userID = scanner.nextInt();
	        System.out.print("Enter your PIN: ");
	        int userPIN = scanner.nextInt();

	        if (authenticateUser(userID, userPIN)) {
	            showMenu(userID);
	        } else {
	            System.out.println("Invalid user ID or PIN. Exiting...");
	        }
	    }

	    private boolean authenticateUser(int userID, int userPIN) {
	        User user = users.get(userID);
	        return user != null && user.getUserPIN() == userPIN;
	    }

	    private void showMenu(int userID) {
	        ATM atm = new ATM(users.get(userID));

	        while (true) {
	            System.out.println("\nATM Menu:");
	            System.out.println("1. Check Balance");
	            System.out.println("2. Withdraw Money");
	            System.out.println("3. Deposit Money");
	            System.out.println("4. Exit");
	            System.out.print("Enter your choice: ");
	            int choice = scanner.nextInt();

	            switch (choice) {
	                case 1:
	                    atm.checkBalance();
	                    break;
	                case 2:
	                    System.out.print("Enter amount to withdraw: ");
	                    double withdrawAmount = scanner.nextDouble();
	                    atm.withdraw(withdrawAmount);
	                    break;
	                case 3:
	                    System.out.print("Enter amount to deposit: ");
	                    double depositAmount = scanner.nextDouble();
	                    atm.deposit(depositAmount);
	                    break;
	                case 4:
	                    System.out.println("Exiting...");
	                    return;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }

	    public static void main(String[] args) {
	        ATMInterface atmInterface = new ATMInterface();
	        atmInterface.start();
	    }
	}

	class User {
	    private int userID;
	    private int userPIN;
	    private double accountBalance;

	    public User(int userID, int userPIN, double accountBalance) {
	        this.userID = userID;
	        this.userPIN = userPIN;
	        this.accountBalance = accountBalance;
	    }

	    public int getUserID() {
	        return userID;
	    }

	    public int getUserPIN() {
	        return userPIN;
	    }

	    public double getAccountBalance() {
	        return accountBalance;
	    }

	    public void setAccountBalance(double accountBalance) {
	        this.accountBalance = accountBalance;
	    }
	}

	class ATM {
	    private User user;

	    public ATM(User user) {
	        this.user = user;
	    }

	    public void checkBalance() {
	        System.out.println("Current balance: " + user.getAccountBalance());
	    }

	    public void withdraw(double amount) {
	        if (amount > 0 && amount <= user.getAccountBalance()) {
	            user.setAccountBalance(user.getAccountBalance() - amount);
	            System.out.println("Withdrawal successful. Remaining balance: " + user.getAccountBalance());
	        } else {
	            System.out.println("Invalid amount or insufficient funds.");
	        }
	    }

	    public void deposit(double amount) {
	        if (amount > 0) {
	            user.setAccountBalance(user.getAccountBalance() + amount);
	            System.out.println("Deposit successful. New balance: " + user.getAccountBalance());
	        } else {
	            System.out.println("Invalid amount.");
	        }
	    }
	}
