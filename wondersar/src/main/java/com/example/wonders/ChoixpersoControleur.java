package com.example.wonders;

import domain.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ChoixpersoControleur implements Initializable {
    @FXML
    private Button retour;
    @FXML
    private Button valider;
    @FXML
    private Button startGame;

    @FXML
    public ChoiceBox<String> myChoiceBox;
    public String[] NP ={"2","3","4","5","6","7"};

    @FXML
    private TextField nameTextField;
    @FXML
    Label labelBox;
    @FXML
    Label labelTextefield;
    @FXML
    Label labelinfo;
    @FXML
    Label labelinfo2;
    @FXML
    ArrayList<String> wonderName=new ArrayList<>();

    private Stage stage;
    private Scene scene;
    private final Image image = new Image(getClass().getResourceAsStream("images/imagejeu/retour.png"));
    private final ImageView icon = new ImageView(image);


    private final Image image2 = new Image(getClass().getResourceAsStream("images/imagejeu/valider1.png"));
    private final ImageView icon2 = new ImageView(image2);
    private final Image image3 = new Image(getClass().getResourceAsStream("images/imagejeu/Start games.png"));
    private final ImageView icon3 = new ImageView(image3);
    private Parent root;
    private int nombre;
    private String wonderChoice;
    private ArrayList<Player> players = new ArrayList<>();




    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        nameTextField.setVisible(false);
        startGame.setVisible(false);
        retour.setGraphic(icon);
        valider.setGraphic(icon2);

        myChoiceBox.getItems().addAll(NP);//chose box
        myChoiceBox.setOnAction(this::getNumber);

    }

    public void getNumber(Event event) {
        labelinfo.setText("Wonders :");
        labelinfo2.setText("Name : ");
        if (myChoiceBox.getValue()!=null) {

            nombre = Integer.parseInt(myChoiceBox.getValue());
            labelBox.setText("Vous avez choisi "+nombre+" joueur ");
            for (Wonder wonders:Wonder.values()){
                wonderName.add(wonders.getDisplayName());
            }
            nameTextField.setVisible(true);
            myChoiceBox.setValue(null);
            myChoiceBox.getItems().removeAll(NP);
            myChoiceBox.getItems().addAll(wonderName);

            myChoiceBox.setOnAction(this::getWonderChoice);
        }
    }

    public void getWonderChoice(Event event) {

        wonderChoice = myChoiceBox.getValue();

    }

    public void onConfirmButton() {

        String name = nameTextField.getText();
        players.add(new Player(name, Wonder.valueOf(wonderChoice), new Hand(),false, new ArrayList<>()));

        for(int i=0;i< players.size();i++){
            labelTextefield.setText(players.get(i).getName()+ " \n"+players.get(i).getWonder());
        }
        myChoiceBox.getItems().remove(wonderChoice);

        nameTextField.setText("");
        myChoiceBox.setValue("");

        if (players.size() == nombre) {
            startGame.setGraphic(icon3);
            valider.setVisible(false);
            startGame.setVisible(true);
            myChoiceBox.setDisable(true);
            nameTextField.setDisable(true);
        }

    }

    public void switchScene(Event event)throws IOException{
        //initialize game
        CardDecks mainDeck = new CardDecks("Main");
        mainDeck.shuffleDeck();
        Conflict conflict = new Conflict(players.size());

        ArrayList<CardDecks> playerDecks = new ArrayList<>();

        for (Player i : players) {
            String wonder = i.getWonder().getDisplayName();

            CardDecks cardDecks = new CardDecks(wonder);
            cardDecks.shuffleDeck();

            playerDecks.add(cardDecks);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameView.fxml"));
        root=loader.load();
        stage = (javafx.stage.Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);


        GameController gameController = loader.getController();
        gameController.startTurn(players, mainDeck, playerDecks, conflict, 0);

        stage.setScene(scene);
        stage.show();
        }



    public void retour (Event event) throws  IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);

        stage.show();
    }
}
