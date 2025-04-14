import java.util.*;
import java.io.*;

abstract class User { 
    private String username; 
    private String password; 
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getRole() {
        return this.role;
    }
    
    public abstract void showMenu(Scanner sc);
}

class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, "Admin");
    }

    @Override
    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("=======[Admin Menu]=======");
            System.out.println("1. Add User");
            System.out.println("2. Remove User");
            System.out.println("3. List Users");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addUser(sc);
                    break;
                case 2:
                    removeUser(sc);
                    break;
                case 3:
                    listUsers();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
        while (choice != 4);
    }

    private boolean isUsernameExists(String username) {
        try (Scanner fileScanner = new Scanner(new File("users.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(",");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        }
        return false;
    }

    private void addUser(Scanner sc) {
        sc.nextLine(); // Clear buffer if needed
    
        String username;
        while (true) {
            System.out.print("Enter username: ");
            username = sc.nextLine().trim();
    
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
            } else if (isUsernameExists(username)) {
                System.out.println("Username already exists. Please choose another.");
            } else {
                break;
            }
        }
    
        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = sc.nextLine().trim();
    
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty.");
            } else {
                break;
            }
        }
    
        String role;
        while (true) {
            System.out.print("Enter role (Admin/Librarian/Reader): ");
            role = sc.nextLine().trim();
    
            if (role.isEmpty()) {
                System.out.println("Role cannot be empty.");
            } else if (!role.equals("Admin") && !role.equals("Librarian") && !role.equals("Reader")) {
                System.out.println("Invalid role. Must be Admin, Librarian, or Reader.");
            } else {
                break;
            }
        }
    
        try {
            FileWriter fw = new FileWriter("users.txt", true);
            fw.write(username + "," + password + "," + role + "\n");
            fw.close();
            System.out.println("New user [" + username + "] has been added.");
        } catch (IOException e) {
            System.out.println("Failed to add user: " + e.getMessage());
        }
    }

    private void removeUser(Scanner sc) {
        sc.nextLine(); // Consume newline left-over
        System.out.print("Enter username to remove: ");
        String username = sc.nextLine().trim();
    
        File userFile = new File("users.txt");
        ArrayList<String> updatedFile = new ArrayList<>();
        boolean found = false;
    
        try {
            Scanner rk = new Scanner(userFile);
            while (rk.hasNextLine()) {
                String line = rk.nextLine();
                String[] data = line.split(",");
                if (data.length >= 1 && data[0].equals(username)) {
                    found = true; 
                    continue;
                }
                updatedFile.add(line);
            }
            rk.close();
    
            if (found) {
                FileWriter wr = new FileWriter(userFile, false);
                for (String line : updatedFile) {
                    wr.write(line + "\n");
                }
                wr.close();
                System.out.println("User [" + username + "] removed successfully.");
            } else {
                System.out.println("Cannot find user [" + username + "] in the file.");
            }
    
        } catch (IOException e) {
            System.out.println("Error occurred while processing file.");
        }
    }

    private void listUsers() {
        try {
            File userFile = new File("users.txt");
            Scanner sc = new Scanner(userFile);
            System.out.println("=======[User List]=======");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");
                System.out.println("Username: " + data[0] + ", Role: " + data[2]);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading file.");
        }
    }
}

class Librarian extends User {
    public Librarian(String username, String password) {
        super(username, password, "Librarian");
    }

    @Override
    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("=======[Librarian Menu]=======");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. List Available Books");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addBook(sc);
                    break;
                case 2:
                    removeBook(sc);
                    break;
                case 3:
                    listBooks();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
        while (choice != 4);
    }

    public void addBook(Scanner sc) {
        sc.nextLine(); // Consume leftover newline
    
        String type;
        while (true) {
            System.out.print("Enter type of book (1. PrintedBook, 2. EBook): ");
            type = sc.nextLine().trim();
            if (type.equals("1") || type.equals("2")) break;
            System.out.println("Invalid book type. Only 1 (PrintedBook) or 2 (EBook) allowed.");
        }
    
        String title, author, genre, isbn;
    
        do {
            System.out.print("Enter Title: ");
            title = sc.nextLine().trim();
        } while (title.isEmpty());
    
        do {
            System.out.print("Enter Author Name: ");
            author = sc.nextLine().trim();
        } while (author.isEmpty());
    
        do {
            System.out.print("Enter Genre: ");
            genre = sc.nextLine().trim();
        } while (genre.isEmpty());
    
        do {
            System.out.print("Enter ISBN: ");
            isbn = sc.nextLine().trim();
        } while (isbn.isEmpty());
    
        try {
            FileWriter fw = new FileWriter("books.txt", true);
    
            if (type.equals("1")) {
                String availability = "true";
                String dueDate = "";
                int numOfPage;
    
                while (true) {
                    System.out.print("Enter Number of Pages: ");
                    String pageInput = sc.nextLine().trim();
                    try {
                        numOfPage = Integer.parseInt(pageInput);
                        if (numOfPage <= 0) {
                            System.out.println("Page number must be greater than 0.");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                }
    
                fw.write("PrintedBook," + title + "," + author + "," + genre + "," + isbn + ",true," + numOfPage + "," + dueDate + "\n");
    
            } else {
                String format;
                do {
                    System.out.print("File Format (e.g. PDF, Word): ");
                    format = sc.nextLine().trim();
                } while (format.isEmpty());
    
                fw.write("EBook," + title + "," + author + "," + genre + "," + isbn + ",true," + format + "\n");
            }
    
            fw.close();
            System.out.println("New book [" + title + "] has been added! [Type: " + (type.equals("1") ? "PrintedBook" : "EBook") + "]");
    
        } catch (IOException e) {
            System.out.println("Failed to add book: " + e.getMessage());
        }
    }

    public void removeBook(Scanner sc) {
        sc.nextLine(); // Consume leftover newline
        System.out.print("Enter title of book to remove: ");
        String title = sc.nextLine().trim();
    
        File bookFile = new File("books.txt");
        ArrayList<String> updatedFile = new ArrayList<>();
        boolean found = false;
    
        try {
            Scanner rk = new Scanner(bookFile);
            while (rk.hasNextLine()) {
                String line = rk.nextLine();
                String[] data = line.split(",");
                
                if (data.length >= 2 && data[1].equalsIgnoreCase(title)) {
                    found = true;
                    continue;
                }
                updatedFile.add(line);
            }
            rk.close();
    
            if (found) {
                FileWriter wr = new FileWriter(bookFile, false);
                for (String line : updatedFile) {
                    wr.write(line + "\n");
                }
                wr.close();
                System.out.println("Book [" + title + "] has been removed.");
            } else {
                System.out.println("Cannot find book [" + title + "] in the file.");
            }
    
        } catch (IOException e) {
            System.out.println("Error occurred while processing file: " + e.getMessage());
        }
    }

    public void listBooks() {
        try {
            File bookFile = new File("books.txt");
            Scanner sc = new Scanner(bookFile);
            System.out.println("=======[Book List]=======");
    
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");
    
                if (data.length < 7) {
                    System.out.println("Invalid book entry: " + line);
                    continue;
                }
    
                String type = data[0];
    
                if (type.equals("PrintedBook")) {
                    String available = data[5];
                    if (available.equals("true")) {
                        System.out.println("Title: " + data[1] + ", Author: " + data[2] + ", Genre: " + data[3]
                            + ", ISBN: " + data[4] + ", Pages: " + data[6]);
                    }
                } else if (type.equals("EBook")) {
                    System.out.println("Title: " + data[1] + ", Author: " + data[2] + ", Genre: " + data[3]
                        + ", ISBN: " + data[4] + ", Format: " + data[6]);
                } else {
                    System.out.println("Unknown book type: " + line);
                }
            }
    
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading file.");
        }
    }
}

class Reader extends User {
    public Reader(String username, String password) {
        super(username, password, "Reader");
    }

    @Override
    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("=======[Reader Menu]=======");
            System.out.println("1. View Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    borrowBook(sc);
                    break;
                case 3:
                    returnBook(sc);
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 4);
    }

    public void viewBooks() {
        try {
            File bookFile = new File("books.txt");
            Scanner sc = new Scanner(bookFile);
            System.out.println("=======[Available Books]=======");
    
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");
    
                if (data[0].equals("PrintedBook") && data[5].equals("true")) {
                    System.out.println("Title: " + data[1] + ", Author: " + data[2] + ", Genre: " + data[3] +
                                       ", ISBN: " + data[4] + ", Pages: " + data[6] +
                                       ", Availability: Available, Type: " + data[0]);
                } else if (data[0].equals("EBook") && data[5].equals("true")) {
                    System.out.println("Title: " + data[1] + ", Author: " + data[2] + ", Genre: " + data[3] +
                                       ", ISBN: " + data[4] + ", Format: " + data[6] + ", Type: " + data[0]);
                }
            }
    
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading file.");
        }
    }
    
    public void borrowBook(Scanner sc) {
        System.out.print("Enter title of book to borrow: ");
        sc.nextLine(); // Consume leftover newline
        String title = sc.nextLine().trim();
    
        File bookFile = new File("books.txt");
        ArrayList<String> updatedFile = new ArrayList<>();
        boolean found = false;
    
        try {
            Scanner rk = new Scanner(bookFile);
            while (rk.hasNextLine()) {
                String line = rk.nextLine();
                String[] data = line.split(",");
    
                if (data[1].equalsIgnoreCase(title)) {
                    found = true;
    
                    if (data[0].equals("EBook")) {
                        System.out.println("You can't borrow EBook! Please choose another book.");
                    } 
                    else if (data[5].equals("true")) {
                        data[5] = "false"; // Mark as borrowed
                        System.out.println("You have borrowed the book [" + title + "] successfully.");
    
                        try (FileWriter tfw = new FileWriter("transactions.txt", true)) {
                            String transaction = getUsername() + " | BORROW | Title: " + data[1] +
                                                 " | ISBN: " + data[4] +
                                                 " | Date: " + java.time.LocalDate.now();
                            tfw.write(transaction + "\n");
                        } catch (IOException e) {
                            System.out.println("Error writing to transactions file.");
                        }
                    } else {
                        System.out.println("Book [" + title + "] is not available for borrowing.");
                    }
                }
    
                updatedFile.add(String.join(",", data));
            }
            rk.close();
    
            if (found) {
                FileWriter wr = new FileWriter(bookFile, false);
                for (String line : updatedFile) {
                    wr.write(line + "\n");
                }
                wr.close();
            } else {
                System.out.println("Cannot find book [" + title + "] in the file.");
            }
    
        } catch (IOException e) {
            System.out.println("Error occurred while processing file.");
        }
    }
    
    public void returnBook(Scanner sc) {
        System.out.print("Enter title of book to return: ");
        sc.nextLine(); // Consume leftover newline
        String title = sc.nextLine().trim();
    
        File bookFile = new File("books.txt");
        File transactionFile = new File("transactions.txt");
        ArrayList<String> updatedBookFile = new ArrayList<>();
        boolean found = false;
        boolean canReturn = false;
        String isbnToReturn = "";
    
        try {
            List<String> borrowLog = new ArrayList<>();
            List<String> returnLog = new ArrayList<>();
    
            if (transactionFile.exists()) {
                Scanner txReader = new Scanner(transactionFile);
                while (txReader.hasNextLine()) {
                    String line = txReader.nextLine();
                    if (line.contains("BORROW") && line.contains(title)) {
                        borrowLog.add(line);
                    } else if (line.contains("RETURN") && line.contains(title)) {
                        returnLog.add(line);
                    }
                }
                txReader.close();
            }
    
            if (borrowLog.size() > returnLog.size()) {
                canReturn = true;
            }
    
            Scanner rk = new Scanner(bookFile);
            while (rk.hasNextLine()) {
                String line = rk.nextLine();
                String[] data = line.split(",");
    
                if (data[1].equalsIgnoreCase(title)) {
                    found = true;
    
                    if (data[0].equals("EBook")) {
                        System.out.println("You can't return EBook! Please choose another book.");
                    } else if (!canReturn) {
                        System.out.println("You have not borrowed this book or already returned it.");
                    } else if (data[5].equalsIgnoreCase("false")) {
                        data[5] = "true"; // Mark as returned
                        isbnToReturn = data[4];
                        System.out.println("You have returned the book [" + title + "] successfully.");
    
                        // Ghi log RETURN vào transactions.txt
                        try (FileWriter tfw = new FileWriter(transactionFile, true)) {
                            String transaction = getUsername() + " | RETURN | Title: " + data[1] +
                                                 " | ISBN: " + isbnToReturn +
                                                 " | Date: " + java.time.LocalDate.now();
                            tfw.write(transaction + "\n");
                        } catch (IOException e) {
                            System.out.println("Error writing to transactions file.");
                        }
                    } else {
                        System.out.println("Book [" + title + "] is not currently borrowed.");
                    }
                }
    
                updatedBookFile.add(String.join(",", data));
            }
            rk.close();
    
            if (found) {
                FileWriter wr = new FileWriter(bookFile, false);
                for (String line : updatedBookFile) {
                    wr.write(line + "\n");
                }
                wr.close();
            } else {
                System.out.println("Cannot find book [" + title + "] in the file.");
            }
    
        } catch (IOException e) {
            System.out.println("Error occurred while processing file.");
        }
    }
    
}



    
    