package com.zayan;

public class Main {
     public static void main(String[] args) {
          String FILE_NAME = "./src/main/resources/The_Yellow-Wallpaper.epub";

          EpubReaderIO reader = new EpubReaderIO(FILE_NAME);
          String contents = reader.getEpubContents();

          System.out.println(contents);
     }
}