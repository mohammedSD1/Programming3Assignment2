/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mohammed
 */
public class Accounts {

    private int id;
    private int user_id;
    private int account_number;
    private String username;
    private String currency;
    private double balance;
    private String creation_date;

    public Accounts(int user_id, int account_number, String username, String currency, double balance, String creation_date) {
        this.user_id = user_id;
        this.account_number = account_number;
        this.username = username;
        this.currency = currency;
        this.balance = balance;
        this.creation_date = creation_date;
    }

    public Accounts(int account_number, String username, String currency, double balance, String creation_date) {
        this.account_number = account_number;
        this.username = username;
        this.currency = currency;
        this.balance = balance;
        this.creation_date = creation_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAccount_number() {
        return account_number;
    }

    public void setAccount_number(int account_number) {
        this.account_number = account_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public int save() throws SQLException, ClassNotFoundException {
        Connection c = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        int recordCounter = 0;
        String sql = "INSERT INTO accounts (id ,user_id,account_number ,username,currency,balance,creation_date) VALUES (?, ?, ?, ?,?,?,?)";
        ps = c.prepareStatement(sql);
        ps.setInt(1, this.getId());
        ps.setInt(2, this.getUser_id());
        ps.setInt(3, this.getAccount_number());
        ps.setString(4, this.getUsername());
        ps.setString(5, this.getCurrency());
        ps.setDouble(6, this.getBalance());
        ps.setString(7, this.getCreation_date());
        recordCounter = ps.executeUpdate();
        if (recordCounter > 0) {
            System.out.println(this.getUsername()
                    + " Account was added successfully!");
        }
        if (ps != null) {
            ps.close();
        }
        c.close();
        return recordCounter;
    }

    public static ArrayList<Accounts> getAllAccounts() throws SQLException, ClassNotFoundException {
        Connection c = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Accounts> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts ";
        ps = c.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Accounts account = new Accounts(rs.getInt(3), rs.getString(4), rs.getString(5), rs.getDouble(6), rs.getString(7));
            account.setId(rs.getInt(1));
            accounts.add(account);
        }
        if (ps != null) {
            ps.close();
        }
        c.close();
        return accounts;
    }

    public int update() throws SQLException, ClassNotFoundException {
        Connection c = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        int recordCounter = 0;
        String sql = "UPDATE accounts SET account_number=?, username=? , currency=?,balance=? ,creation_date=? WHERE id=?";
        ps = c.prepareStatement(sql);
        ps.setInt(1, this.getAccount_number());
        ps.setString(2, this.getUsername());
        ps.setString(3, this.getCurrency());
        ps.setDouble(4, this.getBalance());
        ps.setString(5, this.getCreation_date());
        ps.setInt(6, this.getId());
        recordCounter = ps.executeUpdate();
        if (recordCounter > 0) {
            System.out.println("account with id : " + this.getId() + " was updated successfully!");
        }
        if (ps != null) {
            ps.close();
        }
        c.close();
        return recordCounter;
    }

    public int delete() throws SQLException, ClassNotFoundException {
        Connection c = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        int recordCounter = 0;
        String sql = "DELETE FROM accounts WHERE id=? ";
        ps = c.prepareStatement(sql);
        ps.setInt(1, this.getId());
        recordCounter = ps.executeUpdate();
        if (recordCounter > 0) {
            System.out.println("The account with id : " + this.getId() + " was deleted successfully!");
        }
        if (ps != null) {
            ps.close();
        }
        c.close();
        return recordCounter;
    }

}
