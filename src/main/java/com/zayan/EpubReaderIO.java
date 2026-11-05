package com.zayan;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class EpubReaderIO {

     private final String FILE_PATH;

     public EpubReaderIO(String FILE_PATH) {
          this.FILE_PATH = FILE_PATH;
     }

     public String getEpubContents() {
          StringBuilder result = new StringBuilder();

          try (
                  ZipInputStream zipStream = new ZipInputStream(
                          new FileInputStream(this.FILE_PATH)
                  )
          ) {
               ZipEntry entry;

               // Iterate through every compressed file inside the EPUB package
               while ((entry = zipStream.getNextEntry()) != null) {
                    String entryName = entry.getName();

                    // Locate and read only the book's XHTML text content files
                    if (entryName.endsWith(".xhtml") || entryName.endsWith(".html")) {
                         System.out.println("\n--- Reading File: " + entryName + " --- \n");

                         // Wrap the zip stream to read text line-by-line
                         BufferedReader reader = new BufferedReader(
                                 new InputStreamReader(zipStream, StandardCharsets.UTF_8)
                         );

                         String line;
                         while ((line = reader.readLine()) != null) {
                              result.append(line + "\n");
                         }

                         zipStream.closeEntry();
                    }
               }
          } catch (IOException e) {
               System.err.println("Error reading the EPUB file: " + e.getMessage());
          }
          return result.toString();
     }
}
