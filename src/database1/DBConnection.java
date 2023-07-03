package database1;

import java.sql.*;
class DBConnection {
    private String db_name;
    Connection conn;
    private Statement stmt;

    public DBConnection(String db_name) {
        this.db_name = db_name;
        this.conn = null;
        this.stmt = null;
    }

    public void connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/company";
        String username = "root";
        String password = "root";
        conn = DriverManager.getConnection(url, username, password);
        stmt = conn.createStatement();
    }

    public void createCompanyDb() throws SQLException {
        stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS Company");
        System.out.println("Company database created.");
    }

    public void createDepartmentTable() throws SQLException {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Department (DEPTNO INT PRIMARY KEY, DNAME TEXT, LOC TEXT)");
        System.out.println("Department table created.");
    }

    public void createEmployeeTable() throws SQLException {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Employee (EMPNO INT PRIMARY KEY, ENAME TEXT, JOB TEXT, Manager INT, HIREDATE TEXT, SAL FLOAT, COMM FLOAT, DEPTNO INT)");
        System.out.println("Employee table created.");
    }
}
