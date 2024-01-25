package com.example.pop;

import java.util.*;

public class Recipe {
     String title, introduction, directions;
     String ingredients;

     public Recipe(String title) {
          this.title = title;
          this.ingredients = ""; // Initialize ingredients as an empty string
     }
     public Recipe(String title, String ingredients) {
          this.title = title;
          this.ingredients = ingredients; // Initialize ingredients as an empty string
     }
     public Recipe() {
          // Initialize variables, if necessary
          this.title = "";
          this.ingredients = "";
          // ... other initializations ...
     }

     public void addIntroduction(String introduction) { this.introduction = introduction; }

     public void addDirections(String directions) { this.directions = directions; }

     public void addIngredient(String quantity, String ingredient) {
          if (!this.ingredients.isEmpty()) {
               this.ingredients += "\n"; // Add a newline for each new ingredient if not the first
          }
          this.ingredients += quantity + "--" + ingredient;
     }

     public String getTitle() { return title; }

     public String getIntroduction() { return introduction; }

     public String getDirections() { return directions; }

     public String getIngredients() { return ingredients; }

     public void scale(boolean scaleUp, int amount) {
          String[] lines = ingredients.split("\n");
          StringBuilder scaledIngredients = new StringBuilder();
          for (String line : lines) {
               String[] parts = line.split("--");
               if (parts.length == 2) {
                    try {
                         int oldAmount = Integer.parseInt(parts[0].trim());
                         int newAmount = scaleUp ? oldAmount * amount : oldAmount / amount;
                         scaledIngredients.append(newAmount).append("--").append(parts[1]).append("\n");
                    } catch (NumberFormatException e) {
                         // Handle the case where the quantity isn't an integer
                         scaledIngredients.append(line).append("\n");
                    }
               } else {
                    scaledIngredients.append(line).append("\n");
               }
          }
          ingredients = scaledIngredients.toString();
     }

     public String amountOfIngredient(String ingredient) {
          String[] lines = ingredients.split("\n");
          for (String line : lines) {
               if (line.contains(ingredient)) {
                    String[] parts = line.split("--");
                    if (parts.length == 2) {
                         return parts[0].trim();
                    }
               }
          }
          return null;
     }
}
