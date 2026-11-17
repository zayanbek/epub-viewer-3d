package com.zayan.ui;

import javafx.scene.shape.TriangleMesh;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MeshLoader {

     private final String FILE_PATH;

     public MeshLoader(String FILE_PATH) {
          this.FILE_PATH = FILE_PATH;
     }

     public TriangleMesh getTriangleMesh() {

          /*

               getTriangleMesh()
               v
               parseFile()
               v
               parseLine()
               v
               parseVertex(), parseFace()
               v
               triangulateFace()
               v
               addFaceVertex()


           */

          TriangleMesh triangleMesh = new TriangleMesh();
          triangleMesh.getTexCoords().addAll(0, 0);

          List<Float> points = new ArrayList<>();
          List<Integer> faces = new ArrayList<>();

          try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
               parseFile(reader, points, faces);
          } catch (IOException e) {
               e.printStackTrace();
          }

          triangleMesh.getPoints().setAll(toFloatArray(points));
          triangleMesh.getFaces().setAll(toIntArray(faces));

          return triangleMesh;
     }

     private void parseFile(
             BufferedReader reader,
             List<Float> points,
             List<Integer> faces
     ) throws IOException {
          String line;

          while ((line = reader.readLine()) != null) {
               parseLine(line, points, faces);
          }
     }

     private void parseLine(
             String line,
             List<Float> points,
             List<Integer> faces
     ) {
          line = line.trim();

          if (line.isEmpty() || line.startsWith("#")) {
               return;
          }

          String[] parts = line.split("\\s+");

          switch (parts[0]) {
               case "v" -> parseVertex(parts, points);
               case "f" -> parseFace(parts, points, faces);
          }
     }

     private void parseVertex(String[] parts, List<Float> points) {
          points.add(Float.parseFloat(parts[1]));
          points.add(Float.parseFloat(parts[2]));
          points.add(Float.parseFloat(parts[3]));
     }

     private void parseFace(
             String[] parts,
             List<Float> points,
             List<Integer> faces
     ) {
          List<Integer> vertexIndices = new ArrayList<>();

          for (int i = 1; i < parts.length; i++) {
               vertexIndices.add(
                       parseVertexIndex(parts[i], points.size() / 3)
               );
          }

          triangulateFace(vertexIndices, faces);
     }

     private int parseVertexIndex(String faceVertex, int vertexCount) {
          String index = faceVertex.split("/")[0];
          int vertexIndex = Integer.parseInt(index);
          return vertexIndex > 0
                  ? vertexIndex - 1
                  : vertexCount + vertexIndex;
     }

     private void triangulateFace(
             List<Integer> vertices,
             List<Integer> faces
     ) {
          for (int i = 1; i < vertices.size() - 1; i++) {
               addFaceVertex(vertices.get(0), faces);
               addFaceVertex(vertices.get(i), faces);
               addFaceVertex(vertices.get(i + 1), faces);
          }
     }

     private void addFaceVertex(int vertexIndex, List<Integer> faces) {
          faces.add(vertexIndex);
          faces.add(0); // Default texture coordinate
     }

     private float[] toFloatArray(List<Float> list) {
          float[] array = new float[list.size()];

          for (int i = 0; i < list.size(); i++) {
               array[i] = list.get(i);
          }

          return array;
     }

     private int[] toIntArray(List<Integer> list) {
          int[] array = new int[list.size()];

          for (int i = 0; i < list.size(); i++) {
               array[i] = list.get(i);
          }

          return array;
     }
}
