package database1;

import java.sql.*;
import java.util.Scanner;

class Menu {
    private Scanner scanner;
    private DBConnection db;

    public Menu(DBConnection db) {
        this.scanner = new Scanner(System.in);
        this.db = db;
    }

    public void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Insert Records into Department");
        System.out.println("2. Insert Records into Employee");
        System.out.println("3. Display Employees in a particular Department");
        System.out.println("4. Update Employee Records (Increase Commission by 20%)");
        System.out.println("5. Insert New Employee in the HR Department");
        System.out.println("6. Delete Employees with Salary Less Than a Given Amount");
        System.out.println("7. Display All Employees");
        System.out.println("8. Display All Departments");
        System.out.println("9. Exit");
    }

    public void run() {
        try {
            db.connect();
            db.createCompanyDb();
            db.createDepartmentTable();
            db.createEmployeeTable();

            boolean exit = false;

            while (!exit) {
                displayMenu();
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        insertRecordsIntoDepartment();
                        break;
                    case 2:
                        insertRecordsIntoEmployee();
                        break;
                    case 3:
                        displayEmployeesInDepartment();
                        break;
                    case 4:
                        updateEmployeeRecords();
                        break;
                    case 5:
                        insertNewEmployeeInHRDepartment();
                        break;
                    case 6:
                        deleteEmployeesWithLessSalary();
                        break;
                    case 7:
                        displayAllEmployees();
                        break;
                    case 8:
                        displayAllDepartments();
                        break;
                    case 9:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private void insertRecordsIntoDepartment() throws SQLException {
        System.out.print("Enter Department Number: ");
        int deptno = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Department Name: ");
        String dname = scanner.nextLine();
        System.out.print("Enter Department Location: ");
        String loc = scanner.nextLine();

        String query = "INSERT INTO Department (DEPTNO, DNAME, LOC) VALUES (?, ?, ?)";
        PreparedStatement pstmt = db.conn.prepareStatement(query);
        pstmt.setInt(1, deptno);
        pstmt.setString(2, dname);
        pstmt.setString(3, loc);
        pstmt.executeUpdate();

        System.out.println("Record inserted into Department table.");
    }

    private void insertRecordsIntoEmployee() throws SQLException {
        System.out.print("Enter Employee Number: ");
        int empno = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Employee Name: ");
        String ename = scanner.nextLine();
        System.out.print("Enter Job: ");
        String job = scanner.nextLine();
        System.out.print("Enter Manager: ");
        int manager = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Hire Date: ");
        String hiredate = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double sal = scanner.nextDouble();
        System.out.print("Enter Commission: ");
        double comm = scanner.nextDouble();
        System.out.print("Enter Department Number: ");
        int deptno = scanner.nextInt();

        String query = "INSERT INTO Employee (EMPNO, ENAME, JOB, Manager, HIREDATE, SAL, COMM, DEPTNO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = db.conn.prepareStatement(query);
        pstmt.setInt(1, empno);
        pstmt.setString(2, ename);
        pstmt.setString(3, job);
        pstmt.setInt(4, manager);
        pstmt.setString(5, hiredate);
        pstmt.setDouble(6, sal);
        pstmt.setDouble(7, comm);
        pstmt.setInt(8, deptno);
        pstmt.executeUpdate();

        System.out.println("Record inserted into Employee table.");
    }

    private void displayEmployeesInDepartment() throws SQLException {
        System.out.print("Enter Department Name: ");
        String dname = scanner.nextLine();

        String query = "SELECT * FROM Employee WHERE DEPTNO IN (SELECT DEPTNO FROM Department WHERE DNAME = ?)";
        PreparedStatement pstmt = db.conn.prepareStatement(query);
        pstmt.setString(1, dname);
        ResultSet rs = pstmt.executeQuery();

        System.out.println("Employees in Department '" + dname + "':");
        while (rs.next()) {
            int empno = rs.getInt("EMPNO");
            String ename = rs.getString("ENAME");
            String job = rs.getString("JOB");
            int manager = rs.getInt("Manager");
            String hiredate = rs.getString("HIREDATE");
            double sal = rs.getDouble("SAL");
            double comm = rs.getDouble("COMM");
            int deptno = rs.getInt("DEPTNO");

            System.out.println("EMPNO: " + empno + ", ENAME: " + ename + ", JOB: " + job +
                    ", Manager: " + manager + ", HIREDATE: " + hiredate + ", SAL: " + sal +
                    ", COMM: " + comm + ", DEPTNO: " + deptno);
        }
    }

    private void updateEmployeeRecords() throws SQLException {
        System.out.print("Enter the percentage increase in commission (e.g., 20 for 20%): ");
        double percentage = scanner.nextDouble();

        String query = "UPDATE Employee SET COMM = COMM + (COMM * ? / 100)";
        PreparedStatement pstmt = db.conn.prepareStatement(query);
        pstmt.setDouble(1, percentage);
        pstmt.executeUpdate();

        System.out.println("Employee records updated.");
    }

    private void insertNewEmployeeInHRDepartment() throws SQLException {
        System.out.print("Enter Employee Number: ");
        int empno = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Employee Name: ");
        String ename = scanner.nextLine();
        System.out.print("Enter Job: ");
        String job = scanner.nextLine();
        System.out.print("Enter Manager: ");
        int manager = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Hire Date: ");
        String hiredate = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double sal = scanner.nextDouble();
        System.out.print("Enter Commission: ");
        double comm = scanner.nextDouble();

        String query = "INSERT INTO Employee (EMPNO, ENAME, JOB, Manager, HIREDATE, SAL, COMM, DEPTNO) VALUES (?, ?, ?, ?, ?, ?, ?, " +
                "(SELECT DEPTNO FROM Department WHERE DNAME = 'HR'))";
        PreparedStatement pstmt = db.conn.prepareStatement(query);
        pstmt.setInt(1, empno);
        pstmt.setString(2, ename);
        pstmt.setString(3, job);
        pstmt.setInt(4, manager);
        pstmt.setString(5, hiredate);
        pstmt.setDouble(6, sal);
        pstmt.setDouble(7, comm);
        pstmt.executeUpdate();

        System.out.println("Record inserted into Employee table for the HR department.");
    }

    private void deleteEmployeesWithLessSalary() throws SQLException {
        System.out.print("Enter the minimum salary: ");
        double minSalary = scanner.nextDouble();

        String query = "DELETE FROM Employee WHERE SAL < ?";
        PreparedStatement pstmt = db.conn.prepareStatement(query);
        pstmt.setDouble(1, minSalary);
        int affectedRows = pstmt.executeUpdate();

        System.out.println(affectedRows + " employee(s) deleted.");
    }

    private void displayAllEmployees() throws SQLException {
        String query = "SELECT * FROM Employee";
        Statement stmt = db.conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("All Employees:");
        while (rs.next()) {
            int empno = rs.getInt("EMPNO");
            String ename = rs.getString("ENAME");
            String job = rs.getString("JOB");
            int manager = rs.getInt("Manager");
            String hiredate = rs.getString("HIREDATE");
            double sal = rs.getDouble("SAL");
            double comm = rs.getDouble("COMM");
            int deptno = rs.getInt("DEPTNO");

            System.out.println("EMPNO: " + empno + ", ENAME: " + ename + ", JOB: " + job +
                    ", Manager: " + manager + ", HIREDATE: " + hiredate + ", SAL: " + sal +
                    ", COMM: " + comm + ", DEPTNO: " + deptno);
        }
    }

    private void displayAllDepartments() throws SQLException {
        String query = "SELECT * FROM Department";
        Statement stmt = db.conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("All Departments:");
        while (rs.next()) {
            int deptno = rs.getInt("DEPTNO");
            String dname = rs.getString("DNAME");
            String loc = rs.getString("LOC");

            System.out.println("DEPTNO: " + deptno + ", DNAME: " + dname + ", LOC: " + loc);
        }
    }
}