# Simple-Pagnetnation-With-Check

Libraries used: 
- Multidex library: to avoide any compiling and invocation issues
- Glide libaray: to load and set images
- Volley library: for connection with server
- Design library: for the rows layout

MainActivity includes:
- initialization and definition of variables, item class and adapter class
- "refreshLayout" gets triggered when user pull down the screen to reload data and get new updates
- "recycler" detects when user scroll down and add +1 to current page to load more data (activiting it when it's 8 items left from loaded items)
- method "Load_Data" (with page number) is launched at the start when the application is opened
- method "GetLastPostsionNext" gets triggered when user scroll down and it adds 10 new items each time
- when button "filer_click" is clicked it activates method "filter_button_method"
- method "filter_button_method" checks object (Checked) within Items class to see if there are any items 
checked, and if there is any it starts looping and adding them in ArrayList. Once the process is over it checks the count of the items in array list. If the count is 0 it send Toast that atleast 1 item has to be selected, if its greater than 10 it send Toast that more then 10 items cant be selected, and if its in between 1 and 10 then it saves the data in sharedpreferences while adding "id=" string to it and intent to Filtered_Activity.
- onDestroy() clears data in SharedPreferences

Filtered_Activity includes:
- onCreate() loads the ID array saved in SharedPreferences and set it in "Load_Data" method
- initialization and definition of variables, item class and adapter class
- refreshLayout gets triggered when user pull down the screen to reload data and get new updates
- method "Load_Data" (with page number) is launched at the start when the applications is opened
- onPause() clears data in SharedPreferences

Additional Packages:
- Helpers
- Interface
They contain implementation of conncection to a server, checking connection, json methods and UI design.
