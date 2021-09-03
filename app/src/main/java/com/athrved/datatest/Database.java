package com.athrved.datatest;

import android.os.AsyncTask;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {

    private Connection connection;

    private final String host = "ec2-54-158-232-223.compute-1.amazonaws.com";
    private final String database = "ddgaguv61p4m63";
    private final int port = 5432;
    private final String user = "jfeitasqnyuanh";
    private final String pass = "d60b43b4e9ea924c91deb754cf18a51d5948b7a7e58b4e4d0045487767174ad8";
    private String url = "jdbc:postgresql://ec2-54-158-232-223.compute-1.amazonaws.com:5432/ddgaguv61p4m63?sslmode=require&user=jfeitasqnyuanh&password=d60b43b4e9ea924c91deb754cf18a51d5948b7a7e58b4e4d0045487767174ad8";
    private boolean status;

    public Database()

    {

        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
        //this.disconnect();
        System.out.println("connection status:" + status);
    }


    private void connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    getExtraConnection();
                    System.out.println("connected:" + status);
                } catch (Exception e) {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    public Connection getExtraConnection(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO USERS (EMAIL,USERNAME) "
                    + "VALUES ('nvnn@gmail.com','Neer')";
            stmt.executeUpdate(sql);



            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Records created successfully");
        return c;

    }
}