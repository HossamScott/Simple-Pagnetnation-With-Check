# READ-ME Simple-Pagnetnation-With-Check

Libraries used: 
- Multidex library: to avoide any compiling and invocation issues
- Glide libaray: to load and set images
- Volley library: for connection with server
- Design library: for the rows layout

MainActivity includes:
- initialization and definition of DrawerLayout, toolbar and setting-up homepage fragment

HomeFragment includes:
- initialization and definition of ProgressBar, adapter and ViewModel
- method "getMovieArticles" getting data from API using VM and setting data in adapter

ArticleDetails Activity includes:
- initialization and definition of Textview's and Imageview
- Getting data from intent and setting up the UI 


Additional custom Classes:
- RoundRectCornerImageView
Contain implementation of conncection to a server, checking connection, json methods and Imageview cirle design.
