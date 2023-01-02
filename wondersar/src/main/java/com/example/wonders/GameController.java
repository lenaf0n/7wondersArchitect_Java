package com.example.wonders;

import domain.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Objects;

public class GameController {
    Card leftDeckCard;
    @FXML
    private final ImageView leftDeckCardImage = new ImageView();
    Card rightDeckCard;
    @FXML
    private final ImageView rightDeckCardImage = new ImageView();
    Card mainDeckCard;
    @FXML
    private final ImageView mainDeckImage = new ImageView();
    private final Image mainDeckBackPNG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/wonders/images/cards/card-back/card-back-question.png")));

    private Player player;
    private final Actions actions = new Actions();
    Conflict conflict;
    ArrayList<CardDecks> playerDecks = new ArrayList<>();
    CardDecks mainDeck;


    public void startTurn(ArrayList<Player> players, CardDecks mainCardDeck, ArrayList<CardDecks> allPlayerDecks, int turn) {
        //initialize game
        player = players.get(turn);
        System.out.println(player.getName());

        mainDeck = mainCardDeck;

        playerDecks.addAll(allPlayerDecks);

        ArrayList<CardDecks> options = actions.cardDecksOption(playerDecks, turn);

        //Card choice info
        leftDeckCard = options.get(0).getCard(0);
        Image leftDeckCardPNG = new Image(Objects.requireNonNull(getClass().getResourceAsStream(leftDeckCard.front.imageResource)));
        leftDeckCardImage.setImage(leftDeckCardPNG);

        rightDeckCard = options.get(1).getCard(0);
        Image rightDeckCardPNG = new Image(Objects.requireNonNull(getClass().getResourceAsStream(rightDeckCard.front.imageResource)));
        rightDeckCardImage.setImage(rightDeckCardPNG);

        mainDeckCard = mainDeck.getCard(0);
        Image mainDeckFrontPNG = new Image(Objects.requireNonNull(getClass().getResourceAsStream(mainDeckCard.front.imageResource)));

        if (player.getChat()) {
            mainDeckImage.setImage(mainDeckFrontPNG);
        }
        else {
            mainDeckImage.setImage(mainDeckBackPNG);
        }
    }

    public void onButtonLeftDeck() {
        player.addCard(leftDeckCard, conflict.getAllConflicts());
        playerDecks.get(0).chooseCard();
        System.out.println("left");
    }

    public void onButtonRightDeck() {
        player.addCard(rightDeckCard, conflict.getAllConflicts());
        playerDecks.get(1).chooseCard();
        System.out.println("right");
    }

    public void onButtonMainDeck() {
        player.getAllPlayerCards().add(mainDeckCard);
        mainDeck.chooseCard();
        System.out.println("main");
    }
}
