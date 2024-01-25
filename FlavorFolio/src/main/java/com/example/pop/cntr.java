package com.example.pop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;

public class cntr {

    /*====================controlleur pour le reste des methodes de gestion des boutons de l'affichage des recettes====================*/

    @FXML
    private HBox itemC;

    //champs de texte d'affichage d'une recette specifique
    @FXML
    private TextArea title1;
    @FXML
    private TextArea introduction1;
    @FXML
    private TextArea ingredients1;
    @FXML
    private TextArea directions1;

    /*====================Methode d'affichages des recettes multiples====================*/
    public File getFileName(){
        //Obtenir la référence de l'ImageView à l'intérieur de la HBox
        ImageView imageView = (ImageView) itemC.lookup("#imageView");
        Image image = imageView.getImage();

        //obtenir le lien de cette image
        String imagePath = image.getUrl();
        int lastIndex = imagePath.lastIndexOf('/');
        String fileName = imagePath.substring(lastIndex + 1);

        String[] img = fileName.split("\\.");

        // Construire le chemin du fichier texte de la recette correspondante
        Path recipesPath = Paths.get("Resources", "Recipes");
        Path recipeFilePath = recipesPath.resolve(img[0] + ".txt");
        File file= recipeFilePath.toFile();

        // Affichez le chemin du fichier texte dans la console

        return file;
    }
    /*====================Methode d'affichages des recettes multiples====================*/
    public void afficher(ActionEvent event) {
        File file = getFileName();
        try {
            showCurrentFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HomeController xx= loader.getController();
        Recipe recipe=new Recipe();
        try {
            recipe = showCurrentFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        xx.display(recipe);
        Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /////////////////////////////////////////////////
    public void update(ActionEvent event){
        File file = getFileName();
        try {
            showCurrentFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HomeController xx= loader.getController();
        Recipe recipe=new Recipe();
        try {
            recipe = showCurrentFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        xx.display1(recipe);
        Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /*====================Methode qui prend les infos d'une recette a partir de son fichier texte pour son affichage====================*/
    private Recipe showCurrentFile(File file) throws IOException {
        Recipe recipe= new Recipe();
        String title = "";
        String introduction = "";
        String ingredients = "";
        String directions = "";

        List<String> lines = Files.readAllLines(file.toPath());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isBetweenTitleAndIntroduction = false;
            boolean isBetweenIntroductionAndIngredients = false;
            boolean isBetweenIngredientsAndDirections = false;

            while ((line = br.readLine()) != null) {
                if (line.trim().equals("Title")) {
                    isBetweenTitleAndIntroduction = true;
                } else if (line.trim().equals("Introduction")) {
                    isBetweenTitleAndIntroduction = false;
                    isBetweenIntroductionAndIngredients = true;
                } else if (line.trim().equals("Ingredients")) {
                    isBetweenIntroductionAndIngredients = false;
                    isBetweenIngredientsAndDirections = true;
                } else if (line.trim().equals("Directions")) {
                    isBetweenIngredientsAndDirections = false;
                } else {
                    if (isBetweenTitleAndIntroduction) {
                        title = line.trim();
                    } else if (isBetweenIntroductionAndIngredients) {
                        introduction += line + "\n";
                    } else if (isBetweenIngredientsAndDirections) {
                        ingredients += line + "\n";
                    } else {
                        directions += line + "\n";
                    }
                }
            }

            recipe.title=title;
            recipe.introduction=introduction;
            recipe.ingredients=ingredients;
            recipe.directions=directions;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipe;
    }

    /*====================Methode pour la suppression d'une recette====================*/
    public void delete(ActionEvent event) {

            ImageView imageView = (ImageView) itemC.lookup("#imageView");
            Image image = imageView.getImage();
            String imagePath = image.getUrl().substring(5); // Removing "file:" prefix

            int lastIndex = imagePath.lastIndexOf('/');
            String fileName = imagePath.substring(lastIndex + 1);
            String[] img = fileName.split("\\.");

            File txtFile  = new File(".\\Resources\\Recipes\\"+img[0] + ".txt");
            File imgFile  =new File(".\\Resources\\images\\"+img[0] + ".jpg");

            if (imgFile.exists()) {
                if (imgFile.delete()) {
                    System.out.println("Image file deleted: " + imagePath);
                } else {
                    System.out.println("Failed to delete image file: " + imagePath);
                }
            } else {
                System.out.println("Image file not found: " + imagePath);
            }


            if (txtFile.exists()) {
                if (txtFile.delete()) {
                    System.out.println("Text file deleted: " + fileName);
                } else {
                    System.out.println("Failed to delete text file: " +fileName);
                }
            } else {
                System.out.println("Text file not found: " + fileName);
            }
        }

    }
