package com.ruthuja.krushikmitr;
public class VideoItem {
    public Snippet snippet;

    public class Snippet {
        public String title;
        public Thumbnails thumbnails;
    }

    public class Thumbnails {
        public ThumbnailDetails medium;
    }

    public class ThumbnailDetails {
        public String url;
    }
}
