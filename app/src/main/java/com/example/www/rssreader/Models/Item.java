package com.example.www.rssreader.Models;

public class Item {
    public String Title;
    public String PubDate;
    public String Author;
    public String Image;
    public String Link;

    public Item(String title, String pubDate, String author, String image, String link) {
        Title = title;
        PubDate = pubDate;
        Author = author;
        Image = image;
        Link = link;
    }
}
