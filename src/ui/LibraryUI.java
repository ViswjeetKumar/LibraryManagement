package ui;

import service.LibraryService;
import model.Book;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LibraryUI {
    private final LibraryService libraryService;
    private JFrame frame;
    private JTextField isbnField, titleField, authorField, yearField, removeIsbnField;
    private JTextArea resultArea;

    public LibraryUI() {
        libraryService = new LibraryService();
        initializeUI();
    }

    private void initializeUI() {
        // Frame setup
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(new Color(102, 255, 230)); // background color similar to screenshot
        frame.setLayout(new BorderLayout());

        // Title at top
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        JLabel mainTitle = new JLabel("Books Management System", SwingConstants.CENTER);
        mainTitle.setFont(new Font("Arial", Font.BOLD, 50));
        JLabel subTitle = new JLabel("(Your Library)", SwingConstants.CENTER);
        subTitle.setFont(new Font("Arial", Font.PLAIN, 36));
        titlePanel.add(mainTitle);
        titlePanel.add(subTitle);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Center form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Common font for labels & fields
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        Dimension fieldSize = new Dimension(250, 35);

        // ISBN
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel isbnLabel = new JLabel("ISBN : ");
        isbnLabel.setFont(labelFont);
        formPanel.add(isbnLabel, gbc);

        gbc.gridx = 1;
        isbnField = new JTextField();
        isbnField.setFont(fieldFont);
        isbnField.setPreferredSize(fieldSize);
        formPanel.add(isbnField, gbc);

        // Title
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel titleLabel = new JLabel("Title : ");
        titleLabel.setFont(labelFont);
        formPanel.add(titleLabel, gbc);

        gbc.gridx = 1;
        titleField = new JTextField();
        titleField.setFont(fieldFont);
        titleField.setPreferredSize(fieldSize);
        formPanel.add(titleField, gbc);

        // Author
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel authorLabel = new JLabel("Author : ");
        authorLabel.setFont(labelFont);
        formPanel.add(authorLabel, gbc);

        gbc.gridx = 1;
        authorField = new JTextField();
        authorField.setFont(fieldFont);
        authorField.setPreferredSize(fieldSize);
        formPanel.add(authorField, gbc);

        // Year
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel yearLabel = new JLabel("Year : ");
        yearLabel.setFont(labelFont);
        formPanel.add(yearLabel, gbc);

        gbc.gridx = 1;
        yearField = new JTextField();
        yearField.setFont(fieldFont);
        yearField.setPreferredSize(fieldSize);
        formPanel.add(yearField, gbc);

        // Remove ISBN
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel removeLabel = new JLabel("Remove ISBN : ");
        removeLabel.setFont(labelFont);
        formPanel.add(removeLabel, gbc);

        gbc.gridx = 1;
        removeIsbnField = new JTextField();
        removeIsbnField.setFont(fieldFont);
        removeIsbnField.setPreferredSize(fieldSize);
        formPanel.add(removeIsbnField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton addButton = new JButton("Add Book");
        JButton viewButton = new JButton("View Books");
        JButton removeButton = new JButton("Remove");

        Font btnFont = new Font("Arial", Font.BOLD, 14);
        Dimension btnSize = new Dimension(120, 35);

        addButton.setFont(btnFont);
        addButton.setPreferredSize(btnSize);
        viewButton.setFont(btnFont);
        viewButton.setPreferredSize(btnSize);
        removeButton.setFont(btnFont);
        removeButton.setPreferredSize(btnSize);

        addButton.addActionListener(e -> addBook());
        viewButton.addActionListener(e -> viewBooks());
        removeButton.addActionListener(e -> removeBook());

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(removeButton);

        // Add form + buttons together in center
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(centerPanel, BorderLayout.CENTER);

        // Books List area
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setOpaque(false);
        JLabel listLabel = new JLabel("Books’ List :");
        listLabel.setFont(new Font("Arial", Font.BOLD, 34));
        resultArea = new JTextArea(10, 50);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        listPanel.add(listLabel, BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(listPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        viewBooks(); // load existing books on start
    }

    private void addBook() {
        try {
            String isbn = isbnField.getText().trim();
            if (isbn.isEmpty() || libraryService.findBookByIsbn(isbn) != null) {
                JOptionPane.showMessageDialog(frame, "ISBN cannot be empty or already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            if (year < 1000 || year > 9999) {
                JOptionPane.showMessageDialog(frame, "Year must be between 1000 and 9999.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            libraryService.addBook(isbn, title, author, year, true);
            JOptionPane.showMessageDialog(frame, "Book added successfully!");
            clearFields();
            viewBooks();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid year.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewBooks() {
        resultArea.setText("");
        List<Book> books = libraryService.getAllBooks();
        if (books.isEmpty()) {
            resultArea.append("No books available.\n");
        } else {
            for (Book book : books) {
                resultArea.append(book.toString() + "\n");
            }
        }
    }

    private void removeBook() {
        String isbn = removeIsbnField.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter an ISBN to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Book book = libraryService.findBookByIsbn(isbn);
        if (book == null) {
            JOptionPane.showMessageDialog(frame, "Book with ISBN " + isbn + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            libraryService.removeBook(isbn);
            JOptionPane.showMessageDialog(frame, "Book removed successfully!");
            removeIsbnField.setText("");
            viewBooks();
        }
    }

    private void clearFields() {
        isbnField.setText("");
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryUI::new);
    }
}
