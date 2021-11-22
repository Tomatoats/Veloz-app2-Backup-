# InveX

An Inventory manager able to handle Items, their serial numbers, and prices!

## Acknowledgements
I first want to say thank you for Professor Hollander. This was by far my favorite thing to do yet.
This class was amazing and I'd take it again in a heartbeat. 
Thank you for anyone in the  UCF CS, ECE, & IT discord who helped
## Authors
Alexys Veloz
## Features

- Can Add items
- Can edit items once they're in
- Can save inventory in a tsv format, html, or JSON.
- Can load existing inventories from files
- Can sort by name (alphanumeric), Serial number (Alphanumeric), and Price (highest / lowest).
- Can search for items by name or by serial number.


## How to Use

- Add an Item:
Write a product name, Serial number, and Price into the labeled text field. All of these values can't be empty, and must be in proper formats.

Name: Must be within 2 and 256 characters, inclusive
Serial Number: It'll be in the format A-XXX-XXX-XXX, where A is a letter (lower or capital)
and X is a letter or a number (lower or capital). Serial numbers must be unique, so if it is the same as another item's serial number, it will give an error.
i.e: h-J8e-kr4-KOE is a valid serial number, but 9-34f-34e1-gH is not.
Price: Price is in U.S Dollars!!! Let that be known. Also, it must be greater than 0. Additionally, it can't have more than 2 decimals. 
i.e: 3.43 is a valid number, and so is 5000, but 4.189 or 0 is not a valid number. 

Once you've written these in the appropiate text field, pres the 'add item' button!



- Remove an Item: 
You can remove an item from the table by clicking that item, then clicking the
"Remove selected Item" button. You won't be able to get it back if you didn't save, so be careful.


- Clear the List:
To clear the list, go to the menu bar and hover over 'Edit'. Underneath, you'll see a button that says "Delete all Items". Click it. Warning: This will delete every item off the list.
You won't be able to get them back if you didn't save before hand, so be careful.


- Edit an existing item's Name:
Click on the item you want to edit, in the Name column. Then write / edit the new name,
and then hit enter. If it still follows the name rule of 2-256 characters, it'll change it to the new edited Name.
otherwise, it will give you an error on the bottom and won't save the Name to what you set it to.


- Edit an existing item's Serial number:
Click on the item you want to edit, in the Serial Number column. Then write / edit the new serial number,
and then hit enter. If it still follows the Serial Number format of A-XXX-XXX-XXX where A is a letter and X is a letter or number, it'll change it to the new serial number, assuming it is a unique serial number. Otherwise, it will give you an error on the bottom and won't save the Serial number to what you set it to.


- Edit an existing item's Price:
Click on the item you want to edit, in the Price column. Then write / edit the new price,
and then hit enter. If it still follows the price rule of over 0 and no more than 2 decimal places, it'll change it to the new edited price.
otherwise, it will give you an error on the bottom and won't save the price to what you set it to.


- Search items by Name:
in the left hand side you'll see two textboxes with a label on top that says Search. The one on top searches by name. Write something in there and press enter, and if any item contains that string, it'll bring it up to the tableview. i.e if you have an item with name "stuff" and search for all items with the name "uff" in it, that item will show up.


- Search items by Serial Number:
in the left hand side you'll see two textboxes with a label on top that says Search. The one on bottom searches by serial number. Write something in there and press enter, and if any item contains what you typed as a serial number, it'll bring it up to the tableview. This is not case sensitive, so if you type "ug" in the search field, it'll bring up any serial number that has "ug", "Ug", "uG", or "UG" as part of the serial number.


- Clear Search to bring it back to normal View:
On the left hand side you'll see a button that says 'Clear Search'. Press the button and it'll show the whole list of items again. 


- Sort By Name:
Click on the "Name" column at the very top, and items will be sorted by name. Clicking it once  will sort by  alphanumerics (0-9 and A first, Z last, case insensitive). Click on it again it will sort again by reverse Alphanumerics (Z first, 0-9 and A last). Clicking on it again will not do anything, and then the clicks cycle. Essentially every (%3 = 1) clicks will sort by alphanumerics (%3 = 2) clicks will sort by reverse alphanumerics, and every (%3 = 0) clicks will do nothing.


- Sort By Serial Number:
Click on the "Serial Number" column at the very top, and items will be sorted by Serial Number. Clicking it once will sort by Alphabetical order (A first, Z last, case insensitive). Clicking on it again will sort it by reverse Alphabetical order (Z first, A last, case insensitive). Clicking on it again will do nothing, and then the clicks cycle. Essentially every (%3 = 1) clicks will sort by alphabetical order, every (%3 = 2) clicks will sort by reverse alphabetical order, and every (%3 = 0) clicks will do nothing.

- Sort By Price:
Click on the "Price (U.S Dollars)" column at the very top, and items will be sorted by Price. Clicking it once will sort by ascending order (smallest value first, biggest value last). Clicking on it again will sort it by descending order (biggest value first, smallest value last). Clicking on it again will do nothing, and then the clicks cycle. Essentially every (%3 = 1) clicks will sort by ascending order, every (%3 = 2) clicks will sort by descending order, and every (%3 = 0) clicks will do nothing.


- How to Save a list with all its items:
To Save the list, go to the menu bar and hover over 'File'. Underneath, you'll see buttons to save as either a TSV format (.txt file), .html file, or .JSON file. Click which ever you'd like to save as,  which will prompt a file explorer for whichever Operating System you are on. Choose through your directory where you'd like to save it to and what the file name will be.

- How to load an existing / previously saved list:
| Warning: Before loading, if you have a list with items in it at the moment I highly recommend saving, as it will delete the items to account for the loaded in list. | To load in a list, go to the menu bar and hover over 'File'. Underneath, you'll see buttons to load a TSV (.txt) file, .html, or .JSON file. Click which ever file format you'd like to load, and a file explorer for whichever Opertating System you are on should appear. Choose through your directory the file you'd like to load! Once loaded, all the items from said list will show up in your tableview, ready for you!