package baseline;

import baseline.InventoryManagementApplication;
import baseline.Item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Alexys Veloz
 */

public class ManagerController extends InventoryManagementApplication implements Initializable {
    FileChooser fileChooser = new FileChooser();
    public ObservableList<Item> list = FXCollections.observableArrayList();
    Item item = new Item("","","");






    @FXML
    private MenuItem loadTSVButton;

    @FXML
    private MenuItem loadHTMLButton;

    @FXML
    private MenuItem loadJSONButton;



    @FXML
    private MenuItem saveTSV;

    @FXML
    private MenuItem saveHTML;

    @FXML
    private MenuItem saveJSON;

    @FXML
    private MenuItem deleteAllButton;

    @FXML
    private Label nameErrorLabel;

    @FXML
    public TextField nameText;

    @FXML
    public TextField serialText;

    @FXML
    private Label serialErrorLabel;

    @FXML
    private Label priceErrorLabel;

    @FXML
    public TextField priceText;



    @FXML
    private TextField sortNameText;

    @FXML
    private TextField sortSerialText;

    @FXML
    private Button searchClear;

    @FXML
    private TableView listTable;

    @FXML
    private TableColumn<Item, String> colName;

    @FXML
    private TableColumn<Item, String> colPrice;

    @FXML
    private TableColumn<Item, String> colSerial;


    @FXML
    private Button removeItemButton;

    @FXML
    private Button addButton;

    @FXML
    void addPressed(ActionEvent event) {
        //make a function that deals with this
        addItem();
    }

    public void addItem() {
        int i = 0;
        if (!item.nameRegex(nameText.getText())) {
            nameErrorLabel.setText("Name must be within 2-256 characters");
            i++;
        }
        else {
            nameErrorLabel.setText("");
        }
        if (!uniqueSerial(serialText.getText())){
            serialErrorLabel.setText("Serial must be unique.");
            i++;
        }
        if (!item.serialRegex(serialText.getText())){
            i++;
            serialErrorLabel.setText("Serial must be in format A-XXX-XXX-XXX");
        }

        if (!item.priceRegex(priceText.getText())){
            priceErrorLabel.setText("Price must be greater than 0 and only up to two decimals.");
            i++;
        }
        else {
            priceErrorLabel.setText("");

        }
        if (i == 0){
            Item newItem = new Item("","","");
            newItem.setName(nameText.getText());
            newItem.setSerial(serialText.getText());
            newItem.setPrice(priceText.getText());
            list.add(newItem);

            nameText.clear();
            serialText.clear();
            priceText.clear();

            nameErrorLabel.setText("");
            serialErrorLabel.setText("");
            priceText.setText("");
        }


    }

    @FXML
    void removeSelectedItem(ActionEvent event) {
        //make a function that deals with this
        removeItem();
    }
    public void removeItem() {
        listTable.getItems().removeAll(listTable.getSelectionModel().getSelectedItem());
    }


    @FXML
    void clearPressed(ActionEvent event) {
        //make a function that deals with this
        clearAllItems();
    }
    public void clearAllItems(){
        list.remove(0,list.size());
    }

    @FXML
    void htmlPressed(ActionEvent event) {
        //make it save as an HTML
        saveFile("*.html");
    }

    @FXML
    void jsonPressed(ActionEvent event) {
        //Make it save as an JSON
        saveFile("*.JSON");
    }

    @FXML
    void tsvPressed(ActionEvent event) {
        //make it save as a txt
        saveFile("*.txt");
    }

    public void saveFile(String fileType){
        Window stage = nameErrorLabel.getScene().getWindow();
        //open up filechooser and set up for a txt file to be saved
        fileChooser.setTitle("Save Dialog");
        fileChooser.setInitialFileName("InveX");
        // and save it in the format that file needs
        //depending on the what file it is, open up a file to save as
        if (fileType.equals("*.txt")){
            try {
                FileChooser.ExtensionFilter tsv = new FileChooser.ExtensionFilter("Save as a TSV text file (.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(tsv);
                fileChooser.setSelectedExtensionFilter(tsv);
                File file = fileChooser.showSaveDialog(stage);
                fileChooser.setInitialDirectory(file.getParentFile());
                saveAsTSV(file);
            }
            catch (Exception ignored){
                ignored.printStackTrace();
            }
        }
        else if (fileType.equals("*.JSON")){
            try {
                FileChooser.ExtensionFilter json = new FileChooser.ExtensionFilter("Save as a .JSON file", "*.JSON");
                fileChooser.getExtensionFilters().add(json);
                fileChooser.setSelectedExtensionFilter(json);
                File file = fileChooser.showSaveDialog(stage);
                fileChooser.setInitialDirectory(file.getParentFile());
                saveAsJSON(file);
            }
            catch (Exception ignored){
                ignored.printStackTrace();
            }
        }
        else{
            try {
                FileChooser.ExtensionFilter html = new FileChooser.ExtensionFilter("Save as a html file", "*.html");
                fileChooser.getExtensionFilters().add(html);
                fileChooser.setSelectedExtensionFilter(html);
                File file = fileChooser.showSaveDialog(stage);
                fileChooser.setInitialDirectory(file.getParentFile());
                saveAsHTML(file);
            }
            catch (Exception ignored){
                ignored.printStackTrace();
            }
        }
    }
    @FXML
    void loadHTMLPressed(ActionEvent event) {
        //load the list  as an HTML
        loadFile("*.html");
    }

    @FXML
    void loadJSONPressed(ActionEvent event) {
        //load the list as a JSON
        loadFile("*.JSON");
    }

    @FXML
    void loadTSVPressed(ActionEvent event) {
        //load the list as a TSV
        loadFile("*.txt");
    }


    @FXML
    void searchClearPressed(ActionEvent event) {
        //load in original list and clear the text fields
        clearSearchBars();
    }
    public void clearSearchBars(){
        sortNameText.clear();
        sortSerialText.clear();
        listTable.setItems(list);
    }

    @FXML
    void sortNameTyped(ActionEvent event) {
        //Sort the lists according to the text of the names
        sortNames(sortNameText.getText());
    }
    public void sortNames(String text){
        ObservableList<Item> names = FXCollections.observableArrayList();
        String lowertext = text.toLowerCase(Locale.ROOT);

        for (Item items : list) {
            String lowername = items.getName().toLowerCase(Locale.ROOT);
            if (lowername.contains(lowertext)) {
                names.add(items);
            }
        }
        listTable.setItems(names);
    }
    @FXML
    void sortSerialTyped(ActionEvent event) {
        //sort the lists according to the text of the serial
        sortSerial(sortSerialText.getText());
    }
    public void sortSerial(String text){
        //compare the list to the text given, if any Serials contain that string, show up
        ObservableList<Item> serial = FXCollections.observableArrayList();
        String lowertext = text.toLowerCase(Locale.ROOT);

        for (Item items : list) {
            String lowerserial = items.getSerial().toLowerCase(Locale.ROOT);
            if (lowerserial.contains(lowertext)) {
                serial.add(items);
            }
        }
        listTable.setItems(serial);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //initialize the table in another function
        initializeTable();
    }
    public void initializeTable(){

        //Set up the three columns, all of them being editable
        listTable.setItems(list);
        listTable.setEditable(true);
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Item, String>>) event -> {
                    if (item.nameRegex(String.valueOf(event.getNewValue())) == true) {
                        ((Item) event.getTableView().getItems().get(event.getTablePosition().getRow())).setName(String.valueOf((event.getNewValue())));
                        nameErrorLabel.setText("");
                    } else {
                        nameErrorLabel.setText("Name must be within 2-256 characters");
                        listTable.refresh();
                    }
                }
        );

        colSerial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        colSerial.setCellFactory(TextFieldTableCell.forTableColumn());
        colSerial.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Item, String>>) event -> {
                    if (Boolean.FALSE.equals(item.serialRegex(String.valueOf(event.getNewValue())))|| !uniqueSerial(String.valueOf(event.getNewValue()))) {
                        serialErrorLabel.setText("Serial must in the format A-XXX-XXX-XXX where X is either letter or number");
                        listTable.refresh();
                    } else {
                        ((Item) event.getTableView().getItems().get(event.getTablePosition().getRow())).setSerial(String.valueOf((event.getNewValue())));
                        serialErrorLabel.setText("");
                    }
                }
        );
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setCellFactory(TextFieldTableCell.forTableColumn());
        colPrice.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Item,String>>) event -> {
                    if (Boolean.FALSE.equals(item.priceRegex(String.valueOf(event.getNewValue())))) {
                        priceErrorLabel.setText("Price must be greater than 0 And  numbers only (decimal allowed)");
                        listTable.refresh();
                    } else {
                        ((Item) event.getTableView().getItems().get(event.getTablePosition().getRow())).setPrice(String.valueOf((event.getNewValue())));
                        priceErrorLabel.setText("");
                    }
                }
        );


    }
    public void saveAsTSV(File file){
        try {
            //write down the entire list into a file
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            fileWriter.write("Name\tSerial Number\tPrice($)\n");
            for (int i = 0; i < list.size();i++) {
                fileWriter.write(list.get(i).getName() + "\t");
                fileWriter.write( list.get(i).getSerial() + "\t");
                fileWriter.write(list.get(i).getPrice()+"\n");

            }
            fileWriter.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void saveAsHTML(File file){
        try {
            //write down the entire list into a file
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write("<html>\n\t<head>\n\t\t<title>Invex system</title>\n\t</head>\n\t<body>\n\t\t<table>\n\t\t");
            fileWriter.write("<thread>\n\t\t<tr>\n\t\t");
            fileWriter.write("<td> <i> Name </i> </td>\n\t\t");
            fileWriter.write("<td> <i> Serial Number </i> </td>\n\t\t");
            fileWriter.write("<td> <i> Price (in $ )</i> </td>\n\t\t</tr>\n\t\t</thread>\n\t\t<tbody>\n\t\t");
            for (int i = 0; i < list.size();i++) {
                fileWriter.write("<tr>\n");
                fileWriter.write("\t\t<td> "+list.get(i).getName()+" </td>\n");
                fileWriter.write("\t\t<td> "+list.get(i).getSerial()+" </td>\n");
                fileWriter.write("\t\t<td> "+list.get(i).getPrice()+" </td>\n");
                fileWriter.write("</tr>\n\t\t");
            }
            fileWriter.write("</tbody>\n\t\t</table>\n\t");
            fileWriter.write("</body>\n</html>");
            fileWriter.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public void saveAsJSON(File file){
        //write down the entire list into a file
        try {
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            fileWriter.write("[\n{\n");
            String shutUpSonarLint = "\",\n";

            for (int i = 0; i < list.size();i++) {
                fileWriter.write("\"Name\":\"" + list.get(i).getName() +"\",\n");
                fileWriter.write("\"serial\":\"" + list.get(i).getSerial() +"\",\n");
                fileWriter.write("\"price\":\"" + list.get(i).getPrice() + "\"\n");
                if (i+1 < list.size()) {
                    fileWriter.write("},\n{\n");
                }
                else
                {
                    fileWriter.write("}\n");
                }
            }
            fileWriter.write("]");
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
    public boolean loadAsTSV(File file){
        // load up the filechooser and look for only text files
        fileChooser.setTitle("Load Dialog");
        FileChooser.ExtensionFilter tsv = new FileChooser.ExtensionFilter("choose a text file (TSV)", "*.txt");
        fileChooser.getExtensionFilters().add(tsv);
        fileChooser.setSelectedExtensionFilter(tsv);
        try (Scanner input = new Scanner(Paths.get(String.valueOf(file))).useDelimiter("\n"))
        {
            fileChooser.setInitialDirectory(file.getParentFile());
            clearAllItems();
            //make sure the list is empty then scan in all the strings, parsing them correctly
            //also use a while to make sure it continues after the delimiter
            while (input.hasNext()) {
                Item items = new Item("", "", "");
                String str = input.next();
                String[] ArrayofString = str.split("\t", 3);
                if (!ArrayofString[2].contains("$")) {
                    items.setName(ArrayofString[0]);
                    items.setSerial(ArrayofString[1]);
                    items.setPrice(ArrayofString[2]);
                    list.add(items);
                }
            }
            return true;
        }
        catch (ArrayIndexOutOfBoundsException | IOException arrayIndexOutOfBoundsException) {
            return false;
        }

    }
    public boolean loadAsHTML(File file){
        // load up the filechooser and look for only text files
        fileChooser.setTitle("Load Dialog");

        try (Scanner input = new Scanner(Paths.get(String.valueOf(file))).useDelimiter("</html>")) {
            fileChooser.setInitialDirectory(file.getParentFile());
            clearAllItems();
            //make sure the list is empty then scan in all the strings, parsing them correctly
            //also use a while to make sure it continues after the delimiter
            ArrayList<String> arrayOfString = new ArrayList<>();
            String unparsed = input.next();
            Document doc = Jsoup.parse(unparsed);
            Element table = doc.select("table").get(0);
            Elements rows = doc.select("tr");
            for (int i = 1; i < rows.size(); i++){
                Element row = rows.get(i);
                Elements cols = row.select("td");
                arrayOfString.add(String.valueOf(cols.get(0).text()));
                arrayOfString.add(String.valueOf(cols.get(1).text()));
                arrayOfString.add(String.valueOf(cols.get(2).text()));
            }
            for (int i = 0; i < arrayOfString.size(); i++) {
                Item items = new Item("", "", "");
                items.setName(arrayOfString.get(i));
                items.setSerial(arrayOfString.get(i + 1));
                items.setPrice(arrayOfString.get(i + 2));
                list.add(items);
                i = i+2;
            }
            return true;
        }
        catch (ArrayIndexOutOfBoundsException | IOException arrayIndexOutOfBoundsException) {
            return false;
        }


    }
    public boolean loadAsJSON(File file) {
        ArrayList<String> listOfString = new ArrayList<>();
        // load up the filechooser and look for only text files

        try (Scanner input = new Scanner(Paths.get(String.valueOf(file))).useDelimiter("\n")) {
            fileChooser.setInitialDirectory(file.getParentFile());
            clearAllItems();

        //make sure the list is empty then scan in all the strings, parsing them correctly
        //also use a while to make sure it continues after the delimiter
            while (input.hasNext()) {
                String str = input.next();

                if (str.contains(":")) {
                    String[] ArrayofString = str.split("[:]", 2);
                    String whatWeWant = ArrayofString[1];
                    String[] removeQuotes = whatWeWant.split("[\"]",3);
                    String info = removeQuotes[1];
                    listOfString.add(info);
                }
            }
                for (int i = 0; i < listOfString.size(); i++){
                    String name = listOfString.get(i);
                    String serial = listOfString.get(i+1);
                    String price = listOfString.get(i+2);
                    Item items = new Item(name, serial,price);
                    list.add(items);
                    i = i+2;
                }
            return true;
    }
        catch (ArrayIndexOutOfBoundsException | IOException arrayIndexOutOfBoundsException) {
            return false;
        }
    }
    public boolean uniqueSerial(String serial){
        boolean istrue  = true;
        for (Item items : list) {

            if (items.getSerial().equals(serial)) {
                istrue = false;
                break;
            }
        }
        return istrue;
    }
    public void loadFile(String fileType){
    Window stage = nameErrorLabel.getScene().getWindow();
    //open up filechooser and set up for a txt file to be loaded
        fileChooser.setTitle("Load Dialogue");
    // and load it in the file format
        if (fileType.equals("*.txt")){
        try {
            FileChooser.ExtensionFilter tsv = new FileChooser.ExtensionFilter("choose a text file (TSV)", "*.txt");
            fileChooser.getExtensionFilters().add(tsv);
            fileChooser.setSelectedExtensionFilter(tsv);
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            if (loadAsTSV(file)){
                loadAsTSV(file);
                nameErrorLabel.setText("");
            }
            else {
                nameErrorLabel.setText("Either file was corrupted, not in inveX format, or you didn't choose a file.");
            }
        }
        catch (Exception ignored){}
    }
        else if (fileType.equals("*.JSON")){
        try {
            FileChooser.ExtensionFilter json = new FileChooser.ExtensionFilter("choose a JSON file", "*.JSON");
            fileChooser.getExtensionFilters().add(json);
            fileChooser.setSelectedExtensionFilter(json);
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            if (loadAsJSON(file)){
                loadAsJSON(file);
                nameErrorLabel.setText("");
            }
            else {
                nameErrorLabel.setText("Either file was corrupted, not in inveX format, or you didn't choose a file.");
            }
        }
        catch (Exception ignored){}
    }
        else{
        try {
            FileChooser.ExtensionFilter html = new FileChooser.ExtensionFilter("choose a html file", "*.html");
            fileChooser.getExtensionFilters().add(html);
            fileChooser.setSelectedExtensionFilter(html);
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            if (loadAsHTML(file)){
                loadAsHTML(file);
                nameErrorLabel.setText("");
            }
            else {
                nameErrorLabel.setText("Either file was corrupted, not in inveX format, or you didn't choose a file.");
            }

        }
        catch (Exception ignored){}
    }
}

}
