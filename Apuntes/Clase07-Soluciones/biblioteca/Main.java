import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Library library = new Library();
        library.add(new Book("978-0134685991", "Effective Java", "Joshua Bloch"));
        library.add(new Book("978-0137673629", "Core Java Volume I", "Cay Horstmann"));

        Scanner input = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("1) Add  2) Remove  3) List  4) Search  5) Lend  6) Return  0) Exit");
            System.out.print("> ");
            String option = input.nextLine().trim();
            try {
                switch (option) {
                    case "1" -> {
                        System.out.print("isbn: ");
                        String isbn = input.nextLine();
                        System.out.print("title: ");
                        String title = input.nextLine();
                        System.out.print("author: ");
                        String author = input.nextLine();
                        library.add(new Book(isbn, title, author));
                        System.out.println("added");
                    }
                    case "2" -> {
                        System.out.print("isbn: ");
                        library.remove(input.nextLine());
                        System.out.println("removed");
                    }
                    case "3" -> {
                        for (Book book : library.listAll()) {
                            System.out.println(book);
                        }
                    }
                    case "4" -> {
                        System.out.print("text: ");
                        for (Book book : library.search(input.nextLine())) {
                            System.out.println(book);
                        }
                    }
                    case "5" -> {
                        System.out.print("isbn: ");
                        library.findByIsbn(input.nextLine()).lend();
                        System.out.println("lent");
                    }
                    case "6" -> {
                        System.out.print("isbn: ");
                        library.findByIsbn(input.nextLine()).giveBack();
                        System.out.println("returned");
                    }
                    case "0" -> running = false;
                    default -> System.out.println("unknown option: " + option);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("error: " + e.getMessage());
            }
        }
    }
}
