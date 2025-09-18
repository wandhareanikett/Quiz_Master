package com.adminModules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AdminModule {
	
	private static final String URL = "jdbc:mysql://localhost:3306/quizmaster";
    private static final String USER = "root"; // change to your DB user
    private static final String PASSWORD = "Aniket"; // change to your DB password

	 private Connection connect() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }


	public void addQuestion() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Question Text: ");
		String question = sc.nextLine();

		System.out.print("Enter Option 1: ");
		String opt1 = sc.nextLine();
		System.out.print("Enter Option 2: ");
		String opt2 = sc.nextLine();
		System.out.print("Enter Option 3: ");
		String opt3 = sc.nextLine();
		System.out.print("Enter Option 4: ");
		String opt4 = sc.nextLine();

		System.out.print("Enter Correct Option Number (1-4): ");
		int correctOption = sc.nextInt();

		String sql = "INSERT INTO question (question_text, option1, option2, option3, option4, correct_option) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, question);
			ps.setString(2, opt1);
			ps.setString(3, opt2);
			ps.setString(4, opt3);
			ps.setString(5, opt4);
			ps.setInt(6, correctOption);
			ps.executeUpdate();
			System.out.println("✅ Question added successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void editQuestion() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Question ID to Edit: ");
		int id = sc.nextInt();
		sc.nextLine(); // consume newline

		System.out.print("Enter New Question Text: ");
		String question = sc.nextLine();

		System.out.print("Enter New Option 1: ");
		String opt1 = sc.nextLine();
		System.out.print("Enter New Option 2: ");
		String opt2 = sc.nextLine();
		System.out.print("Enter New Option 3: ");
		String opt3 = sc.nextLine();
		System.out.print("Enter New Option 4: ");
		String opt4 = sc.nextLine();

		System.out.print("Enter New Correct Option Number (1-4): ");
		int correctOption = sc.nextInt();

		String sql = "UPDATE question SET question_text=?, option1=?, option2=?, option3=?, option4=?, correct_option=? WHERE id=?";
		try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, question);
			ps.setString(2, opt1);
			ps.setString(3, opt2);
			ps.setString(4, opt3);
			ps.setString(5, opt4);
			ps.setInt(6, correctOption);
			ps.setInt(7, id);
			int rows = ps.executeUpdate();
			if (rows > 0) {
				System.out.println("✅ Question updated successfully!");
			} else {
				System.out.println("⚠️ No question found with ID: " + id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 3. Delete Question
	public void deleteQuestion() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Question ID to Delete: ");
		int id = sc.nextInt();

		String sql = "DELETE FROM question WHERE id=?";
		try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			int rows = ps.executeUpdate();
			if (rows > 0) {
				System.out.println("✅ Question deleted successfully!");
			} else {
				System.out.println("⚠️ No question found with ID: " + id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 4. View All Student Scores
	public void viewAllScores() {
		String sql = "SELECT s.id, s.first_name, sc.total_score, sc.grade "
				+ "FROM student s JOIN score sc ON s.id = sc.student_id ORDER BY sc.total_score ASC";
		try (Connection con = connect(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			System.out.println("=== All Student Scores ===");
			System.out.println("ID | Name | Score | Grade");
			while (rs.next()) {
				System.out.println(rs.getInt("id") + " | " + rs.getString("first_name") + " | "
						+ rs.getInt("total_score") + " | " + rs.getString("grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 5. Search Score by Student ID
	public void searchScoreById() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Student ID: ");
		int id = sc.nextInt();

		String sql = "SELECT total_score, grade FROM score WHERE student_id=?";
		try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("Score: " + rs.getInt("total_score"));
				System.out.println("Grade: " + rs.getString("grade"));
			} else {
				System.out.println("⚠️ No score found for student ID " + id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 6. View Top Scorer
	public void viewTopScorer() {
		String sql = "SELECT s.first_name, sc.total_score, sc.grade "
				+ "FROM student s JOIN score sc ON s.id = sc.student_id "
				+ "WHERE sc.total_score = (SELECT MAX(total_score) FROM score)";
		try (Connection con = connect(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			System.out.println("=== Top Scorer ===");
			while (rs.next()) {
				System.out.println("Name: " + rs.getString("first_name"));
				System.out.println("Score: " + rs.getInt("total_score"));
				System.out.println("Grade: " + rs.getString("grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
