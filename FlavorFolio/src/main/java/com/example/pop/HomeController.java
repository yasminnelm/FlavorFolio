package com.example.pop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import static java.lang.System.exit;

public class HomeController {

    @FXML
    public AnchorPane SplashScreen;

    /*====================Boutons du SideBar====================*/

    @FXML
    private Button displayRecipes;
    @FXML
    private Button addRecipe;
    @FXML
    private Button searchRecipe;
    @FXML
    private Button info;
    @FXML
    private Button Exit;

    /*====================Attributs d'affichage de recettes====================*/

    //page d'acceuil (affichage des recettes stockees)
    @FXML
    private AnchorPane Home;
    //page d'affichage pour une recette specifique
    @FXML
    private AnchorPane aff;

    @FXML
    private AnchorPane aff1;

    @FXML
    private VBox pnItems = null;
    @FXML
    private Label nmbr;
    private String displayStr;
    @FXML
    private Button affiche;

    //champs de texte pour l'affichage d'une recette specifique
    @FXML
    private TextArea title1;
    @FXML
    private TextArea introduction1;
    @FXML
    private TextArea ingredients1;
    @FXML
    private TextArea directions1;
    //champs de texte pour l'affichage d'une recette specifique
    @FXML
    private TextArea title11;
    @FXML
    private TextArea introduction11;
    @FXML
    private TextArea ingredients11;
    @FXML
    private TextArea directions11;


    /*====================Attributs d'ajout des recettes====================*/

    @FXML
    private AnchorPane AddRecipe;

    //boutons de gestions des differents actions liees a l'ajout
    public Button createButton;
    public Button clearButton;
    public Button addImage;

    //fichier ou l'image qui sera enregistree
    private File selectedImageFile;

    //champs de texte
    @FXML
    private TextField newTitle;
    @FXML
    private TextArea newIntroduction;
    @FXML
    private TextArea newIngredients;
    @FXML
    private TextArea newDirections;

    /*====================Attributs de recherche et filtrage des recettes====================*/

    @FXML
    private AnchorPane SortPanel;

    //boutons de gestion de recherche
    @FXML
    private Button clearButton1, confirmButton;

    //champs de texte pour le filtrage et l'affichage des resultats
    @FXML
    private TextField nameField;
    @FXML
    private TextArea displayArea;
    @FXML
    private ComboBox<String> typeBox;

    //Liste de stockage de recettes
    private ArrayList<Recipe> recipeArr;

    /*====================Attributs d'affichage des infos de l'app====================*/

    @FXML
    private AnchorPane infoPane;
    //champ du texte affiche sur cette fenetre
    @FXML
    private TextArea txtinfo;

    /*===========================================================================================*/

    public HomeController() {
    }

    /*====================Methode de gestion du click sur les boutons du sidebar====================*/
    public void handleClicks(ActionEvent actionEvent) throws IOException {

        //Bouton DisplayRecipes
        if (actionEvent.getSource() == displayRecipes) {
            initializee();
            System.out.println("Action du bouton Modifier la recette");
            Home.toFront();
        }

        //bouton AddRecipe
        if (actionEvent.getSource() == addRecipe) {
            AddRecipe.toFront();
        }

        //bouton SearchRecipe
        if (actionEvent.getSource() == searchRecipe) {
            System.out.println("Action du bouton Rechercher une recette");
            SortPanel.toFront();
        }

        //bouton Info
        if (actionEvent.getSource() == info) {
            displayStr = "" +
                    "\n \n Bienvenue dans le programme de recettes .\n"
                    + "ce programme vous permet de stocker et d'éditer des recettes.\n\n"
                    + "Il y a 3 'panneaux' ou 'pages' auxquels vous pouvez accéder.\n\n"
                    + "Voir/Afficher les recettes\n"
                    + "Cette page vous permet de visualiser les recettes ajoutées au dossier 'Recipes' situé "
                    + "dans le dossier racine du programme.\nCliquez sur les boutons Gauche et Droit pour faire défiler les recettes.\n\n"
                    + "Ajouter des recettes\n"
                    + "Cette page vous permet d'ajouter des recettes au dossier 'Recipes' afin que le programme puisse "
                    + "les afficher.\n Chaque recette est sauvegardée sous forme de fichier 'txt' consultable dans le dossier 'Recipes'.\n"
                    + "Saisissez les éléments de la recette tels que dictés.\n L'utilisation de '--' pour les ingrédients est importante "
                    + "pour pouvoir rechercher des recettes.\n\nTrier/Rechercher des recettes\n"
                    + "Cette page vous permet de trier/rechercher les recettes en fonction de celles qui contiennent un ingrédient spécifique "
                    + "ou en recherchant une recette ayant un nom spécifique.\nLes noms sont sensibles à la casse, et l'affichage présentera "
                    + "une liste des recettes correspondant au nom en début de liste.\n\n"
                    + "Il existe également plusieurs actions pouvant être effectuées sur les recettes.\nCes actions peuvent être réalisées en allant "
                    + "à 'Voir/Afficher les recettes' et en cliquant sur une action.\n\n"
                    + "'Modifier' vous permet de modifier la recette actuelle.\n"
                    + "'Supprimer' supprime la recette actuelle.\n"
                    + "'Mettre à l'échelle' vous permet de mettre à l'échelle les ingrédients d'une recette.\n Faites attention aux recettes que vous "
                    + "voulez mettre à l'échelle : certaines recettes ne fonctionnent pas bien après une mise à l'échelle linéaire.";
            txtinfo.setText(displayStr);
            infoPane.toFront();
        }

        //bouton Exit
        if (actionEvent.getSource() == Exit) {
            exit(0);
        }

        //bouton pour l'affichage des recettes
        if (actionEvent.getSource() == affiche) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            try {
                Parent root = loader.load();
                HomeController homeController = loader.getController();
                homeController.navigateToHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //gestion des boutons addImage,CreateButton et ClearButton de la fenetre d'ajout de recettes

        //ajouter une image
        if (actionEvent.getSource() == addImage) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image File");
            selectedImageFile = fileChooser.showOpenDialog(null);
        }

        //confirmer l'ajout d'une recette
        if (actionEvent.getSource() == createButton) {

            //stocker les informations de la recette entree
            String title = newTitle.getText();
            String intro = newIntroduction.getText();
            String ingredients = newIngredients.getText();
            String directions = newDirections.getText();

            //creer les fichiers pour le stockage de la recette sous forme texte et son image, les deux seront stockees dans des fichiers qui prennent le titre comme nom
            try {
                File dir = new File("Resources/Recipes");

                //creer le repertoire si il n'existe pas
                dir.mkdirs();

                //chemin vers le fichier texte
                String recipePath = dir + File.separator + title + ".txt";

                //creation du fichier texte
                File newRecipe = new File(recipePath);

                //stockage de la recette
                writeToFile(newRecipe, title, intro, ingredients, directions);

                if (selectedImageFile != null) {
                    //sauvegarde de l'image de la recette
                    renameAndSaveImage(selectedImageFile, title);
                }

                //popup du confirmation de l'ajout
                showSuccessAlert("Your recipe has been added successfully!");
            } catch (Exception e) {
                //popup en cas d'erreur
                showErrorAlert("Error: Recipe not created");
                e.printStackTrace();
            }

        }

        //vider les champs de texte dans la fenetre de l'ajout d'une recette
        if (actionEvent.getSource() == clearButton) {
            newTitle.setText(null);
            newIntroduction.setText(null);
            newIngredients.setText(null);
            newDirections.setText(null);
        }
    }


    /*====================Methode d'initialisation pour l'affichage des recettes====================*/
    public void initializee() {
        pnItems.getChildren().clear();
        int cntr = 0;
        File directory = new File("Resources/Recipes");
        File[] filesArray = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        System.out.println(directory);
        File directoryImg = new File("Resources/images");
        File[] ImgArray = directoryImg.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg"));

        if (filesArray != null) {
            Node[] nodes = new Node[filesArray.length];
            for (int i = 0; i < filesArray.length; i++) {
                cntr++;
                try {
                    final int j = i;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Item.fxml"));
                    nodes[i] = loader.load();

                    //Assigner un ID dynamique à chaque élément
                    HBox itemBox = (HBox) nodes[i];

                    //Chaque HBox a un élément ImageView
                    ImageView imageView = (ImageView) itemBox.lookup("#imageView");

                    //Charger l'image depuis le tableau des fichiers d'images
                    Image image = new Image(ImgArray[i].toURI().toString());
                    imageView.setImage(image);

                    //Donner des effets aux éléments
                    nodes[i].setOnMouseEntered(event -> {
                        itemBox.setStyle("-fx-background-color : #0A0E3F");
                    });
                    nodes[i].setOnMouseExited(event -> {
                        itemBox.setStyle("-fx-background-color : #02030A");
                    });

                    pnItems.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            nmbr.setText(String.valueOf(cntr));
        }
    }


    /*====================Methode utilisee lors du clique sur le bouton d'affichage de recettes====================*/
    public void navigateToHome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        try {
            Parent root = loader.load();

            //Acceder au controlleur
            HomeController homeController = loader.getController();

            //afficher les recettes
            AnchorPane affPane = (AnchorPane) root.lookup("#aff");
            affPane.toFront();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*====================Methode utilisee lors du clique sur le bouton d'affichage d'une recette specifique====================*/
    public void display(Recipe recipe) {

        aff.toFront();
        title1.setText("          " + recipe.title);
        introduction1.setText(recipe.introduction);
        ingredients1.setText(recipe.ingredients);
        directions1.setText(recipe.directions);
    }
    public void display1(Recipe recipe) {

        aff1.toFront();
        title11.setText("                                            " + recipe.title);
        introduction11.setText(recipe.introduction);
        ingredients11.setText(recipe.ingredients);
        directions11.setText(recipe.directions);
    }

    public  void submite(){
        System.out.println(title11.getText().trim());
        String title= title11.getText().trim();
        File file =new File(".\\Resources\\Recipes\\"+title+".txt");
        if(file.exists()) {
            writeToFile(file, title, introduction11.getText(), ingredients11.getText(), directions11.getText());
            showSuccessAlert("Your recipe has been updated successfully!");
        }
    }


    /*====================Methodes liees a l'ajout des recettes====================*/

    //methode qui ecrit la recette entree dans un fichier texte
    private void writeToFile(File recipeFile, String title, String intro, String ingredients, String directions) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(recipeFile, false));

            bufferedWriter.write("Title");
            bufferedWriter.newLine();
            bufferedWriter.write(title);
            bufferedWriter.newLine();
            bufferedWriter.write("Introduction");
            bufferedWriter.newLine();
            bufferedWriter.write(intro);
            bufferedWriter.newLine();
            bufferedWriter.write("Ingredients");
            bufferedWriter.newLine();
            bufferedWriter.write(ingredients);
            bufferedWriter.newLine();
            bufferedWriter.write("Directions");
            bufferedWriter.newLine();
            bufferedWriter.write(directions);
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println(e + " in writeToFile");
        }
    }

    //methode qui trouve l'extension d'un fichier passe en argument (sera utilisee dans la methode de gestion d'images
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }


    //methode de sauvegarde de l'image associee a une recette
    private void renameAndSaveImage(File selectedFile, String title) {
        if (selectedFile != null) {
            String destinationDirectory = "resources/images/";

            File destDir = new File(destinationDirectory);
            destDir.mkdirs();

            String destFilePath = destinationDirectory + title + getFileExtension(selectedFile.getName());

            try {
                Files.copy(selectedFile.toPath(), new File(destFilePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image saved successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                showErrorAlert("Error saving image.");
            }
        }
    }


    /*====================Methodes de gestion des boutons de la fenetre de recherche et filtrage====================*/

    //generer des recettes
    public void populateDummyRecipes() {
        recipeArr = new ArrayList<>();
        File directory = new File("Resources/Recipes");
        File[] filesArray = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (filesArray != null) {
            for (File file : filesArray) {
                try {
                    Recipe recipe = readRecipeFromFile(file);
                    recipeArr.add(recipe);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle exceptions or log errors as needed
                }
            }
        }
    }
    private Recipe readRecipeFromFile(File file) throws IOException {
        Recipe recipe = new Recipe();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().equals("Title")) {
                    recipe.title = scanner.nextLine().trim();
                } else if (line.trim().equals("Introduction")) {
                    recipe.introduction = scanner.nextLine().trim();
                } else if (line.trim().equals("Ingredients")) {
                    StringBuilder ingredientsBuilder = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        String ingredientLine = scanner.nextLine().trim();
                        if (ingredientLine.equals("Directions")) {
                            break; // Stop reading ingredients when "Directions" is encountered
                        }
                        ingredientsBuilder.append(ingredientLine).append("\n");
                    }
                    recipe.ingredients = ingredientsBuilder.toString().trim();
                } else if (line.trim().equals("Directions")) {
                    StringBuilder directionsBuilder = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        directionsBuilder.append(scanner.nextLine().trim()).append("\n");
                    }
                    recipe.directions = directionsBuilder.toString().trim();
                }
            }
        }
        return recipe;
    }

    //initialisation pour la fenetre de recherche et filtrage
    @FXML
    private void initialize() {
        typeBox.getItems().addAll("Ingredient", "Recipe");
        typeBox.setPromptText("Select an option");

        //Style du promptText
        typeBox.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 10;");
        typeBox.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    setFont(Font.font("Montserrat", 10));
                }
            }
        });


        typeBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setFont(Font.font("Montserrat", 10));
                }
            }
        });
        clearButton1.setOnAction(e -> handleClearAction());
        confirmButton.setOnAction(e -> handleConfirmAction());
        populateDummyRecipes();
    }

    //stockage des recettes dans une liste
    public void setRecipeArr(ArrayList<Recipe> recipeArr) {
        this.recipeArr = new ArrayList<>(recipeArr);
    }

    //gestion du bouton clear sur la fenetre de recherche et filtrage
    public void handleClearAction() {
        displayArea.clear();
    }

    //gestion du bouton de confirmation sur la fenetre de recherche et filtrage
    public void handleConfirmAction() {
        String typeStr = typeBox.getValue();
        String searchStr = nameField.getText();

        if ("Ingredient".equals(typeStr)) {
            handleIngredientSearch(searchStr);
        } else if ("Recipe".equals(typeStr)) {
            sortRecipesByName(searchStr);
        }
    }

    //methode de filtrage de recettes par ingredients
    private void handleIngredientSearch(String ingredient) {
        StringBuilder results = new StringBuilder();
        displayArea.clear();

        for (Recipe r : recipeArr) {
            String ingredientDetail = findIngredientDetail(r.getIngredients(), ingredient);
            if (ingredientDetail != null) {
                results.append(r.getTitle()).append(" needs ").append(ingredientDetail).append("\n");
            }
        }

        if (results.length() == 0) {
            displayArea.setText("No recipes found with ingredient: " + ingredient);
        } else {
            displayArea.setText(results.toString());
        }
    }

    // Helper method to find the ingredient detail in the ingredients string
    private String findIngredientDetail(String ingredients, String searchIngredient) {
        String[] lines = ingredients.split("\n");
        for (String line : lines) {
            if (line.toLowerCase().contains(searchIngredient.toLowerCase())) {
                return line; // Return the entire line containing the ingredient
            }
        }
        return null; // Return null if the ingredient is not found
    }
    //methode de filtrage de recettes par noms
    private void sortRecipesByName(String searchStr) {
        displayArea.clear();
        recipeArr.sort(Comparator.comparing(Recipe::getTitle));

        StringBuilder results = new StringBuilder();
        for (Recipe r : recipeArr) {
            if (r.getTitle().toLowerCase().contains(searchStr.toLowerCase())) {
                results.append(r.getTitle()).append("\n");
            }
        }

        if (results.length() == 0) {
            displayArea.setText("No recipes found with the name: " + searchStr);
        } else {
            displayArea.setText(results.toString());
        }
    }


    /*====================methodes pour afficher les popups de succes ou d'erreurs====================*/

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}


