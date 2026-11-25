import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Library library;
    private ListView<String> shortListView;
    private TextArea detailsArea;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        library = new Library("SumDU Central Library", "2 Rymskogo-Korsakova St");
        FileHandler.loadLibrary(library);

        // --- Section 1: Add Book Form ---
        Label addHeader = new Label("Add New Book");
        addHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        ComboBox<String> typeBox = new ComboBox<String>();
        typeBox.getItems().addAll("EBook", "PaperBook", "AudioBook", "InteractiveEBook");
        typeBox.setValue("EBook");

        TextField titleField = new TextField();
        titleField.setPromptText("Book title");
        TextField authorField = new TextField();
        authorField.setPromptText("Author name");
        TextField yearField = new TextField();
        yearField.setPromptText("e.g. 2024");
        TextField priceField = new TextField();
        priceField.setPromptText("e.g. 250.0");

        ComboBox<Genre> genreBox = new ComboBox<Genre>();
        genreBox.getItems().addAll(Genre.values());
        genreBox.setValue(Genre.SCIENCE);

        TextField quantityField = new TextField("1");
        Label extra1Label = new Label("File Size (MB):");
        TextField extra1Field = new TextField("5.0");
        Label extra2Label = new Label("Format:");
        TextField extra2Field = new TextField("PDF");

        typeBox.setOnAction(e -> {
            String selected = typeBox.getValue();
            if ("EBook".equals(selected) || "InteractiveEBook".equals(selected)) {
                extra1Label.setText("File Size (MB):");
                extra1Field.setText("5.0");
                extra2Label.setText("Format:");
                extra2Field.setText("PDF");
            } else if ("PaperBook".equals(selected)) {
                extra1Label.setText("Page Count:");
                extra1Field.setText("300");
                extra2Label.setText("Weight (kg):");
                extra2Field.setText("0.5");
            } else if ("AudioBook".equals(selected)) {
                extra1Label.setText("Narrator:");
                extra1Field.setText("John Doe");
                extra2Label.setText("Duration (min):");
                extra2Field.setText("180");
            }
        });

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(6);
        formGrid.addRow(0, new Label("Type:"), typeBox, new Label("Genre:"), genreBox);
        formGrid.addRow(1, new Label("Title:"), titleField, new Label("Author:"), authorField);
        formGrid.addRow(2, new Label("Year:"), yearField, new Label("Price:"), priceField);
        formGrid.addRow(3, extra1Label, extra1Field, extra2Label, extra2Field);
        formGrid.addRow(4, new Label("Quantity:"), quantityField);

        Button addButton = new Button("Add Book");
        statusLabel = new Label();

        addButton.setOnAction(e -> {
            try {
                String type = typeBox.getValue();
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                int year = Integer.parseInt(yearField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());
                Genre genre = genreBox.getValue();
                int quantity = Integer.parseInt(quantityField.getText().trim());

                Book newBook;
                if ("EBook".equals(type)) {
                    double size = Double.parseDouble(extra1Field.getText().trim());
                    String format = extra2Field.getText().trim();
                    newBook = new EBook(title, author, year, price, genre, size, format);
                } else if ("PaperBook".equals(type)) {
                    int pages = Integer.parseInt(extra1Field.getText().trim());
                    double weight = Double.parseDouble(extra2Field.getText().trim());
                    newBook = new PaperBook(title, author, year, price, genre, pages, weight);
                } else if ("AudioBook".equals(type)) {
                    String narrator = extra1Field.getText().trim();
                    int duration = Integer.parseInt(extra2Field.getText().trim());
                    newBook = new AudioBook(title, author, year, price, genre, narrator, duration);
                } else {
                    double size = Double.parseDouble(extra1Field.getText().trim());
                    String format = extra2Field.getText().trim();
                    newBook = new InteractiveEBook(title, author, year, price, genre, size, format, true, true);
                }

                library.addNewBook(newBook, quantity);
                FileHandler.saveLibrary(library);
                refreshShortList();
                statusLabel.setText("Added successfully! UUID: " + newBook.getUuid());
                titleField.clear();
                authorField.clear();
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
            }
        });

        // --- Section 2: Short Collection View ---
        Label listHeader = new Label("Library Collection (Short View: Type, Title & UUID)");
        listHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        shortListView = new ListView<String>();
        shortListView.setPrefHeight(160);
        refreshShortList();

        // --- Section 3: Search by UUID ---
        Label searchHeader = new Label("Search Book by UUID");
        searchHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        TextField uuidSearchField = new TextField();
        uuidSearchField.setPromptText("Paste or enter UUID here...");
        uuidSearchField.setPrefWidth(360);

        shortListView.setOnMouseClicked(e -> {
            String selected = shortListView.getSelectionModel().getSelectedItem();
            if (selected != null && selected.contains("UUID: ")) {
                String extractedUuid = selected.substring(selected.indexOf("UUID: ") + 6).trim();
                uuidSearchField.setText(extractedUuid);
            }
        });

        Button searchButton = new Button("Find");
        detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setWrapText(true);
        detailsArea.setPrefHeight(110);

        searchButton.setOnAction(e -> {
            String query = uuidSearchField.getText().trim();
            try {
                LibraryItem found = library.searchByUuid(query);
                if (found == null) {
                    detailsArea.setText("Book with UUID '" + query + "' was not found.");
                } else {
                    detailsArea.setText("Full Object Information:\n" + found.toString());
                }
            } catch (IllegalArgumentException ex) {
                detailsArea.setText("Invalid UUID format! Please enter a valid UUID (e.g. 550e8400-e29b-41d4-a716-446655440000).");
            }
        });

        HBox searchRow = new HBox(10, uuidSearchField, searchButton);

        VBox root = new VBox(10,
                addHeader, formGrid, new HBox(10, addButton, statusLabel),
                new Separator(),
                listHeader, shortListView,
                new Separator(),
                searchHeader, searchRow, detailsArea
        );
        root.setPadding(new Insets(14));

        Scene scene = new Scene(root, 680, 620);
        primaryStage.setTitle("Library Manager - UUID & JavaFX GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void refreshShortList() {
        shortListView.getItems().clear();
        for (LibraryItem item : library.getItems()) {
            shortListView.getItems().add(item.getBook().toShortString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}