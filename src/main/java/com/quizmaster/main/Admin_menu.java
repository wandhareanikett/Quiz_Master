package com.quizmaster.main;

import java.util.Scanner;

import com.adminModules.AdminModule;
import com.util.Consolehelper;

public class Admin_menu {
	Scanner scanner;

	AdminModule admin = new AdminModule();

	public void showsdminnmenu() throws Exception {

		boolean exit = false;
		while (!exit) {

			Consolehelper.println("\n=== Admin Menu ===");
			System.out.println("1. Add New Question");
			System.out.println("2. Edit Question");
			System.out.println("3. Delete Question");
			System.out.println("4. View All Student Scores");
			System.out.println("5. Search Student Score by ID");
			System.out.println("6. View Top Scorer");
			System.out.println("7. Exit to Main Menu");
			System.out.print("Enter your choice: ");
			String input = scanner.next();
			int choice = -1;
			if (input.matches("\\d+")) {
				choice = Integer.parseInt(input);
			} else {
				System.out.println("Invalid input! Please enter a number.");
				continue;
			}

			switch (choice) {
			case 1:
				admin.addQuestion();
				break;
			case 2:
				admin.editQuestion();
				break;
			case 3:
				admin.deleteQuestion();
				break;
			case 4:
				admin.viewAllScores();
				break;
			case 5:
				admin.searchScoreById();
				break;
			case 6:
				admin.viewTopScorer();
				break;
			case 7:
				System.exit(0);
			default:
				System.out.println("Invalid choice.");
			}

		}

	}

	public Admin_menu(Scanner scanner) {

		this.scanner = scanner;
	}
}
