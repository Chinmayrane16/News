# News

Date: 19 April 2018
Author: Chinmay Rane

It is a simple News app. In this app the feed is collected from the website "http://rss.nytimes.com" .
There are 2 major things the apps handles.. 
First it uses HTTPUrlConnection to establish connection and the returned format is in RSS and it is converted to JSON
using GSON library... 
And Secondly it implements ItemClickListener in RecyclerView.
