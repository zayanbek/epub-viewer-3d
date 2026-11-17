package com.zayan.ui;

import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class MainApp extends Application {

     @Override
     public void start(Stage stage) {
          TriangleMesh triangleMesh = buildTriangleMeshBox();
          MeshView meshView = buildMeshView(triangleMesh);

          meshView.getTransforms().addAll(
                  new Rotate(10, Rotate.X_AXIS),
                  new Rotate(-10, Rotate.Y_AXIS),
                  new Rotate(0, Rotate.Z_AXIS)
          );

          // Set up the scene
          StackPane root = new StackPane();

          root.getChildren().addAll(meshView);

          Scene scene = new Scene(root, 800, 600);
          scene.setFill(Color.WHITE);
          scene.setCamera(buildCamera(0, 0, -200));

          // Set up the scene for viewing
          stage.setTitle("3D Epub Viewer 3000");
          stage.setScene(scene);
          stage.show();
     }

     private TriangleMesh buildTriangleMeshBox() {
          MeshLoader loader = new MeshLoader("src/main/resources/models/car/car_obj.obj");
          return loader.getTriangleMesh();
     }

     private MeshView buildMeshView(TriangleMesh triangleMesh) {
          MeshView meshView = new MeshView(triangleMesh);
          meshView.setDrawMode(DrawMode.LINE);

          PhongMaterial material = new PhongMaterial();
          material.setDiffuseColor(Color.BLACK);
          meshView.setMaterial(material);

          // scale the image up
          meshView.setScaleX(100);
          meshView.setScaleY(100);
          meshView.setScaleZ(100);

          meshView.setRotationAxis(Rotate.X_AXIS);
          meshView.setRotate(180);

          return meshView;
     }

     private Camera buildCamera(int x, int y, int z) {
          Camera camera = new PerspectiveCamera(false);
          camera.setTranslateX(x);
          camera.setTranslateY(y);
          camera.setTranslateZ(z);

          return camera;
     }
}

