import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;

public class PayManagement {
	private ArrayList<Pay> pays;

	public PayManagement(ArrayList<Pay> Pays) {
		this.pays = Pays;
	}

	public Pay afficher(int index) {
		return this.pays.get(index);
	}

	public ArrayList<Pay> getPays() {
		return pays;
	}

	public void setPays(ArrayList<Pay> pays) {
		this.pays = pays;
	}

	public void add(Pay pay) {
		this.pays.add(pay);
	}

	public void update(int index, Pay pay) {
		this.pays.set(index, pay);
		System.out.println(pays);

	}

	public void delete(int pay) {
		this.pays.remove(pay);
		System.out.println(pays);

	}

	public void charger() {
		try {
			//FileReader fr = new FileReader("data.txt");
			//BufferedReader br = new BufferedReader(fr);
			//String line = br.readLine();

			Connection conex = connect();
			String selectQuery = "SELECT * FROM Pay";
			Statement statement = conex.createStatement();
			ResultSet resultSet = statement.executeQuery(selectQuery);
			while (resultSet.next()) {
				pays.add(new Pay(resultSet.getString("nom"), resultSet.getString("capitale"),
														resultSet.getInt("pupilation"), resultSet.getString("cantinent")));
				//line = br.readLine();
			}
			//br.close();
			System.out.println("size : " + pays.get(0).getNom());
		}
		catch (Exception ex) {
			System.out.println("error :" + ex);
		}
	}

	public void save() {
		try {
			//FileWriter fw = new FileWriter("data.txt");
			//BufferedWriter bw = new BufferedWriter(fw);
			Connection conex = connect();
			String deleteQuery = "DELETE FROM Pay";
			Statement statement = conex.createStatement();
			int rowsDeleted = statement.executeUpdate(deleteQuery);
			System.out.println("Number of rows deleted: " + rowsDeleted);
			String insertQuery = "INSERT INTO Pay (nom, capitale, pupilation, cantinent) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conex.prepareStatement(insertQuery);
			for (Pay pay : pays) {
				//String line = String.format("%s;%s;%s;%s", pay.getNom(), pay.getCapitale(), pay.getPupilation(), pay.getCantinent());
				ps.setString(1, pay.getNom());
				ps.setString(2, pay.getCapitale());
				ps.setInt(3,  pay.getPupilation());
				ps.setString(4, pay.getCantinent());
				ps.executeUpdate();
				//bw.write(line);
				//bw.newLine();
			}
			//bw.close();
			System.out.println(pays);
		} catch (Exception ex) {
			System.out.println("error :" + ex.getMessage());
		}
	}

	public Connection connect() {
		try {
			Connection connection;
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/countryDB", "yassine", "yassine");
			System.out.println("Connection successful!");
			return connection;
		} catch (ClassNotFoundException ex) {
			System.out.println("MySQL JDBC Driver not found.");
			return null;
		} catch (SQLException ex) {
			System.out.println("Connection failed!");
			return null;
		}
	}

}