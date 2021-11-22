import baseline.ManagerController;
import baseline.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Alexys Veloz
 */

import static org.junit.jupiter.api.Assertions.*;

    public class InveXTest extends ManagerController {
        ObservableList<Item> basicList = FXCollections.observableArrayList();
        ManagerController mc = new ManagerController();
        Item items = new Item("","","");

        @Test
        void CanitHandle1024() {
            //make a for loop to add a ton of items. make sure the list can handle it
            Item item = new Item("yo yo", "s-4re-e3d-e2s", "500");
            for (int i = 0; i < 1200; i++) {
                basicList.add(item);
            }
            boolean overOneThousand;
            if (basicList.size() > 1100) {
                overOneThousand = true;
            } else {
                overOneThousand = false;
            }
            assertEquals(true, overOneThousand);
        }
        @Test
        void over0(){
            //make some prices and make sure that if they're under 0, my function gives it a false
            boolean flag = true;
            String worth = "-5";
            flag = (items.priceRegex(worth));
            assertEquals(false, flag);
            worth = "100.23";
            flag = (items.priceRegex(worth));
            assertEquals(true,flag);
            worth = "0";
            flag = (items.priceRegex(worth));
            assertEquals(false,flag);

        }
        @Test
        void correctSerialFormat(){
            //make strings of different serials, and make sure the function realizes whether they're correct format or not
            String tester = "J-Q3r-5fQ-gU8";
            boolean flag = false;
            flag = (items.serialRegex(tester));
            assertEquals(true,flag);
            tester = "7-ewi-i39-oer4";
            flag = (items.serialRegex(tester));
            assertEquals(false, flag);
        }
        @Test
        void UniqueSerials(){
            //make an item and add to list, and make sure when a serial gets passed it realizes it isn't unique
            boolean flag = true;
            Item items = new Item("string","c-raz-ybi-tch","500");
            list.add(items);
            String serial = "c-raz-ybi-tch";
            flag = uniqueSerial(serial);
            assertEquals(false,flag);
        }
        @Test
        void AddItems(){
            //add an item, and make sure it can get the info
            String name = ("Hello dawg");
            String serial = ("a-bec-dec-fec");
            String price = ("43.56");
            Item items = new Item(name, serial, price);
            list.add(items);
            assertEquals(true,list.get(0).getSerial().equals("a-bec-dec-fec"));
        }
        @Test
        void removeItems(){
            //make items, then remove one and show size difference
            String name = ("Hello dawg");
            String serial = ("a-bec-dec-fec");
            String price = ("43.56");
            Item items = new Item(name, serial, price);
            int i = 0;
            for (i = 0; i < 10; i++){
                list.add(items);
            }
            assertEquals(i, list.size());
            list.remove(5);
            assertEquals(i-1,list.size());
        }
        @Test
        void clearAllItemsTest(){
            //make items, then clear them
            String name = ("Hello dawg");
            String serial = ("a-bec-dec-fec");
            String price = ("43.56");
            Item items = new Item(name, serial, price);
            int i = 0;
            for (i = 0; i < 10; i++){
                list.add(items);
            }
            assertEquals(i, list.size());
            list.remove(0,list.size());
            assertEquals(0,list.size());
        }
        @Test
        void SearchByName() {
            //make a seperate list for name that get added if the string contains some of the name
            ObservableList<Item> names = FXCollections.observableArrayList();
            Item items = new Item("string", "c-raz-ybi-tch", "500");
            list.add(items);
            Item item2 = new Item ("bing","a-123-456-789","433.22");
            list.add(item2);
            Item item3 = new Item ("Hell", "b-4hj-ybi-yok", "25");
            list.add(item3);
            String toSearch = "ing";
            for (Item item : list){
                String lowername = item.getName().toLowerCase(Locale.ROOT);
                if (lowername.contains(toSearch)) {
                    names.add(item);
                }
            }
            assertEquals(2,names.size());
        }
        @Test
        void SearchBySerial(){
            //make a seperate list for serials that get added if the string contains some of the serial
            ObservableList<Item> serials = FXCollections.observableArrayList();
            Item items = new Item("string", "c-raz-ybi-tch", "500");
            list.add(items);
            Item item2 = new Item ("bing","a-123-456-789","433.22");
            list.add(item2);
            Item item3 = new Item ("Hell", "b-4hj-ybi-yok", "25");
            list.add(item3);
            String toSearch = "ybi";
            for (Item item : list){
                String lowername = item.getSerial().toLowerCase(Locale.ROOT);
                if (lowername.contains(toSearch)) {
                    serials.add(item);
                }
            }
            assertEquals(2,serials.size());
        }
        @Test
        void SaveAndLoad() throws IOException {
            //make some items
            Item items = new Item("string", "c-raz-ybi-tch", "500");
            list.add(items);
            Item item2 = new Item ("bing","a-123-456-789","433.22");
            list.add(item2);
            Item item3 = new Item ("Hell", "b-4hj-ybi-yok", "25");
            list.add(item3);
            //save these lists to these files
            File file = new File("data/testJSON.JSON");
            saveAsJSON(file);
            file = new File("data/testHTML.html");
            saveAsHTML(file);
            file = new File("data/testTSV.txt");
            saveAsTSV(file);

            mc.clearAllItems();
            //clear the list so it's a new list

            //load and assert to prove no matter which way, it still loads in stuff
            loadAsTSV(file);
            assertEquals(true,list.get(1).getName().equals("bing"));
            mc.clearAllItems();
            file = new File("data/testHTML.html");
            loadAsHTML(file);
            assertEquals(true,list.get(2).getPrice().equals("25"));
            mc.clearAllItems();
            file = new File("data/testJSON.JSON");
            loadAsJSON(file);
            assertEquals(true,list.get(0).getSerial().equals("c-raz-ybi-tch"));
            mc.clearAllItems();
            //to set them back to empty
            file = new File("data/testJSON.JSON");
            FileWriter fw = new FileWriter(file, false);
            file = new File("data/testHTML.html");
            fw = new FileWriter(file, false);
            file = new File("data/testTSV.txt");
            fw = new FileWriter(file, false);

        }


    }
