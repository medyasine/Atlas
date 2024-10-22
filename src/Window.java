import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;

public class Window extends Frame{
    Label lbln1, lbln2, lbln3, lbln4;
    TextField txtn1, txtn2, txtn3, txtn4;
    Button cancel, add, update, validate, delete, topleft, topright, navleft, navright;
    Panel pn1, pn2, pn3, pn4;
    int currentIndex = 0;
    boolean isAdding = false;
    boolean isUpdating = false;
    Connection cnx = connect();
    ResultSet resultSet;

    public Window() {
        super("Yassine");
        this.setSize(600, 500);
        charger();

        add = new Button("Add");
        add.setBackground(Color.BLUE);
        add.setForeground(Color.black);
        update = new Button("Update");
        update.setBackground(Color.MAGENTA);
        update.setForeground(Color.black);
        validate = new Button("Validate");
        validate.setBackground(Color.MAGENTA);
        validate.setForeground(Color.black);
        delete = new Button("Delete");
        delete.setBackground(Color.RED);
        delete.setForeground(Color.black);
        cancel = new Button("Cancel");
        cancel.setBackground(Color.RED);
        cancel.setForeground(Color.black);
        cancel.setVisible(false);  // Initially hide the Cancel button

        pn1 = new Panel();
        pn1.add(add);
        pn1.add(update);
        pn1.add(validate);
        pn1.add(delete);
        pn1.add(cancel);  // Add Cancel button to the panel
        validate.setEnabled(false);

        pn1.setLayout(new GridLayout(1, 5));

        lbln1 = new Label("Name:");
        lbln2 = new Label("Capital:");
        lbln3 = new Label("Population:");
        lbln4 = new Label("Continent:");

        pn2 = new Panel();
        pn2.add(lbln1);
        pn2.add(lbln2);
        pn2.add(lbln3);
        pn2.add(lbln4);
        pn2.setLayout(new GridLayout(4, 1));

        try {
            txtn1 = new TextField(resultSet.getString(2), 10);
            txtn2 = new TextField(resultSet.getString(3), 10);
            txtn3 = new TextField(resultSet.getString(4), 20);
            txtn4 = new TextField(resultSet.getString(5), 10);
        } catch (SQLException ex) {
            System.out.println("Error :" + ex.getMessage());
        }

        txtn1.setEditable(false);
        txtn2.setEditable(false);
        txtn3.setEditable(false);
        txtn4.setEditable(false);

        pn3 = new Panel();
        pn3.add(txtn1);
        pn3.add(txtn2);
        pn3.add(txtn3);
        pn3.add(txtn4);
        pn3.setLayout(new GridLayout(4, 1));

        topleft = new Button("|<");
        topleft.setBackground(Color.BLUE);
        topleft.setForeground(Color.black);
        navleft = new Button("<<");
        navleft.setBackground(Color.MAGENTA);
        navleft.setForeground(Color.black);
        navright = new Button(">>");
        navright.setBackground(Color.MAGENTA);
        navright.setForeground(Color.black);
        topright = new Button(">|");
        topright.setBackground(Color.RED);
        topright.setForeground(Color.black);

        pn4 = new Panel();
        pn4.add(topleft);
        pn4.add(navleft);
        pn4.add(navright);
        pn4.add(topright);
        pn4.setLayout(new GridLayout(1, 4));

        this.setLayout(new BorderLayout());
        this.add(pn1, BorderLayout.NORTH);
        this.add(pn2, BorderLayout.WEST);
        this.add(pn3, BorderLayout.CENTER);
        this.add(pn4, BorderLayout.SOUTH);

        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                int rep = JOptionPane.showConfirmDialog(Window.this, "Are you sure you want to exit Atlas?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (rep == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearTextFields();
                isAdding = true;
                isUpdating = false;
                validate.setEnabled(true);
                add.setEnabled(false);
                update.setEnabled(false);
                delete.setEnabled(false);
                topleft.setEnabled(false);
                navleft.setEnabled(false);
                navright.setEnabled(false);
                topright.setEnabled(false);
                txtn1.setEditable(true);
                txtn2.setEditable(true);
                txtn3.setEditable(true);
                txtn4.setEditable(true);
                cancel.setVisible(true);  // Show Cancel button
            }
        });

        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isUpdating = true;
                isAdding = false;
                validate.setEnabled(true);
                add.setEnabled(false);
                update.setEnabled(false);
                delete.setEnabled(false);
                topleft.setEnabled(false);
                navleft.setEnabled(false);
                navright.setEnabled(false);
                topright.setEnabled(false);
                txtn1.setEditable(true);
                txtn2.setEditable(true);
                txtn3.setEditable(true);
                txtn4.setEditable(true);
                cancel.setVisible(true);  // Show Cancel button
            }
        });

        validate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isAdding) {
                    addPay();
                } else if (isUpdating) {
                    updatePay();
                }
                add.setEnabled(true);
                update.setEnabled(true);
                delete.setEnabled(true);
                topleft.setEnabled(true);
                navleft.setEnabled(true);
                navright.setEnabled(true);
                topright.setEnabled(true);
                cancel.setVisible(false);  // Hide Cancel button
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearTextFields();
                txtn1.setEditable(false);
                txtn2.setEditable(false);
                txtn3.setEditable(false);
                txtn4.setEditable(false);
                add.setEnabled(true);
                update.setEnabled(true);
                delete.setEnabled(true);
                topleft.setEnabled(true);
                navleft.setEnabled(true);
                navright.setEnabled(true);
                topright.setEnabled(true);
                validate.setEnabled(false);
                isAdding = false;
                isUpdating = false;
                cancel.setVisible(false);  // Hide Cancel button
            }
        });
    }

    private void addPay() {
        try {
            String insertQuery = "INSERT INTO Pay (nom, capitale, pupilation, cantinent) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(insertQuery);
            ps.setString(1, txtn1.getText());
            ps.setString(2, txtn2.getText());
            ps.setInt(3, Integer.parseInt(txtn3.getText()));
            ps.setString(4, txtn4.getText());
            ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error while adding: " + ex.getMessage());
        }
    }

    private void updatePay() {
        if (currentIndex >= 0) {
            try {
                String insertQuery = "UPDATE Pay SET nom = ?, capitale = ?, pupilation = ?, cantinent = ? WHERE id = ?";
                PreparedStatement ps = cnx.prepareStatement(insertQuery);
                ps.setString(1, txtn1.getText());
                ps.setString(2, txtn2.getText());
                ps.setInt(3, Integer.parseInt(txtn3.getText()));
                ps.setString(4, txtn4.getText());
                ps.setString(5, resultSet.getString(1));
                ps.executeUpdate();
            } catch (Exception ex) {
                System.out.println("Error while updating: " + ex.getMessage());
            }
        }
    }

    private void clearTextFields() {
        txtn1.setText("");
        txtn2.setText("");
        txtn3.setText("");
        txtn4.setText("");
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

    public void charger() {
        try {
            String selectQuery = "SELECT * FROM Pay";
            Statement statement = cnx.createStatement(
                    resultSet.TYPE_SCROLL_SENSITIVE,
                    resultSet.CONCUR_UPDATABLE
            );
            resultSet = statement.executeQuery(selectQuery);
            resultSet.next();
        } catch (Exception ex) {
            System.out.println("Error while loading: " + ex.getMessage());
        }
    }
	public static void main(String[] args) {
		new Window();
	}
}
