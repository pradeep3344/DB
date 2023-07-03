package database1;

public class Main {
    public static void main(String[] args) {
        DBConnection db = new DBConnection("Company");
        Menu menu = new Menu(db);
        menu.run();
    }
}