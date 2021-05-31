package ru.zhenyaak.bankAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.sql.*;

@SpringBootApplication
public class BankApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApiApplication.class, args);
		String URL = "jdbc:h2:file:/Users/u19215200/Documents/bankAPI/src/main/resources/data/bank";
		String user = "Eugeny";
		String password = "12345678";
		int q = 0;
		try {
			Connection connection = DriverManager.getConnection(URL, user, password);
			System.out.println("Connection successfull");
			PreparedStatement p = connection.prepareStatement("SELECT COUNT(*) FROM owners");
			ResultSet r = p.executeQuery();
			while(r.next()){
				q = r.getInt(1);
			}
			System.out.println(q);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

	}

}
