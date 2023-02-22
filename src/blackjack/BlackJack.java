package blackjack;

import java.util.*;
import javax.swing.*;

/**
 * BlackJack - simulates a simple game of blackjack (21) with basic dealer logic
 * this isn't exactly what the assignment asked for but it's more accurate for
 * the actual rules of 21 (except for splitting because i don't want to)
 * 
 * @author Adam Belski
 * @version 1.0.0
 * @since 10-Feb-2023
 */
//this is some of the worst code i've ever written in my life
public class BlackJack {

    //init variables
    static Random random = new Random();
    static String[] args;
    static String[] options = {"Hit", "Stay", "Fold"};
    static String Turn = "player";
    static int PlayerTotal;
    static int DealerTotal;
    static String dealerCards;
    static String myCards;
    //if you look at the git history of this, you will how DealersCards and PlayersCards used to be arrays
    //i got so annoyed at the arrays i switched to lists and it's been infinitely easier
    static List<Integer> DealersCards = new ArrayList<>();
    static List<Integer> PlayersCards = new ArrayList<>();
    static int PlayerWins = 0;
    static int DealerWins = 0;

    public static void main(String[] args) {
        //intro message
        JOptionPane.showMessageDialog(null,
                "Welcome to BlackJack!\n"
                + "otherwise known as 21, the aim of the game is to get as close\n"
                + "as possible to 21 without going over, if you go over you lose!"
                + "\nclosest player to 21 wins (in case of a tie dealer wins)!\n\n"
                + "\"hit\" gives you another card.\n"
                + "\"stay\" ends your turn, and means the dealer gets thier turn.\n"
                + "\"fold\" means you give up your hand.\n\n"
                + "additionally, 11's double as 1's in case you go over",
                "BlackJack",
                JOptionPane.INFORMATION_MESSAGE);
        StartBJLoop();
    }

    public static void StartBJLoop() {
        //kinda wierd to do it like this
        //give each player thier two starting cards
        Hit("Dealer");
        Hit("Dealer");
        Hit("player");
        Hit("player");
        //player's turn! 
        while (Turn.equals("player") && PlayerTotal < 22) {

            //string object with our cards, it's mostly cosmetic
            myCards = "";
            for (int i = 0; i < PlayersCards.size(); i++) {
                if (i != PlayersCards.size() - 1) {
                    myCards += PlayersCards.get(i) + ", ";
                } else {
                    myCards += "and a " + PlayersCards.get(i);
                }
            }

            int result = JOptionPane.showOptionDialog(null,
                    "The dealer's hand is showing a " + DealersCards.get(0)
                    + " and he's got " + (DealersCards.size() - 1)
                    + " more hidden card\n\nyou have a " + myCards + " for a total of "
                    + PlayerTotal, "BlackJack",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, options, null);

            switch (result) {
                case JOptionPane.YES_OPTION:
                    Hit("player");
                    if (PlayerTotal > 21) {
                        if (PlayersCards.indexOf(11) != -1) {
                            PlayersCards.remove(PlayersCards.indexOf(11));
                            PlayersCards.add(1);
                            PlayerTotal = 0;
                            for (int i = 0; i < PlayersCards.size(); i++) {
                                PlayerTotal += PlayersCards.get(i);
                            }
                        }
                        GameEnd("player", true);
                        continue;
                    } else {
                        continue;
                    }
                case JOptionPane.NO_OPTION:
                    Turn = "dealer";
                    break;
                case JOptionPane.CANCEL_OPTION:
                    GameEnd("fold", false);
                    break;
            }
            //dealer's turn!
            while (Turn.equals("dealer") && DealerTotal < 22) {

                JOptionPane.showMessageDialog(null,
                        "It's the dealer's turn\nThe dealer's hand is showing a "
                        + DealersCards.get(0)
                        + " and he's got " + (DealersCards.size() - 1)
                        + " more hidden card\n\nyou have a " + myCards + " for a total of "
                        + PlayerTotal, "BlackJack",
                        JOptionPane.PLAIN_MESSAGE);

                //"if the dealer hits, hit the dealer" sounds more like a way to start a fight then code logic
                //get dealer's choise on wether or not to hit
                if (DealersHits()) {
                    Hit("Dealer");
                    if (DealerTotal > 21) {
                        GameEnd("dealer", true);
                    }
                } else {
                    dealerCards = "";
                    for (int i = 0; i < DealersCards.size(); i++) {
                        if (i != DealersCards.size() - 1) {
                            myCards += DealersCards.get(i) + ", ";
                        } else {
                            dealerCards += "and a " + DealersCards.get(i);
                        }
                    }
                    // dealer decides to stay
                    JOptionPane.showMessageDialog(null,
                            "The dealer declairs he's going to stay, and declairs to reveal the cards!\n\n"
                            + "the dealer flips his cards to reveal a " + dealerCards + " for a total of " + DealerTotal
                            + "\n\nyou have a " + myCards + " for a total of " + PlayerTotal,
                             "BlackJack",
                            JOptionPane.PLAIN_MESSAGE);
                    GameEnd("player", false);
                }
            }
        }
    }

    //Game end method, Takes in the player and a bust method, if bust is false then
    //player doesn't matter.
    public static void GameEnd(String Player, boolean Bust) {
        String[] options1 = {"again", "quit"};
        if (Player == "fold") {
            DealerWins++;
            int choice = JOptionPane.showOptionDialog(null,
                    "You Folded!\n\n" + PlayerWins + ":" + DealerWins,
                    "BlackJack",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);
            // play again
            if (choice == JOptionPane.YES_OPTION) {
                Turn = "player";
                DealersCards.removeAll(DealersCards);
                PlayersCards.removeAll(PlayersCards);
                PlayerTotal = 0;
                DealerTotal = 0;
                myCards = "";
                dealerCards = "";
                main(args);
            } else {
                System.exit(0);
            }
        }
        if (Player.equals("player") && Bust) {
            DealerWins++;
            int choice = JOptionPane.showOptionDialog(null,
                    "You went over 21!\n\n" + PlayerWins + ":" + DealerWins,
                    "BlackJack",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);
            // play again
            if (choice == JOptionPane.YES_OPTION) {
                Turn = "player";
                DealersCards.removeAll(DealersCards);
                PlayersCards.removeAll(PlayersCards);
                PlayerTotal = 0;
                DealerTotal = 0;
                myCards = "";
                dealerCards = "";
                main(args);
            } else {
                System.exit(0);
            }
        }

        //if the player wins
        if (PlayerTotal > DealerTotal || Player.equals("dealer") && Bust) {

            PlayerWins++;

            int choice = JOptionPane.showOptionDialog(null,
                    "You Win!\n\n" + PlayerWins + ":" + DealerWins,
                    "BlackJack",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);
            // play again
            if (choice == JOptionPane.YES_OPTION) {
                Turn = "player";
                DealersCards.removeAll(DealersCards);
                PlayersCards.removeAll(PlayersCards);
                PlayerTotal = 0;
                DealerTotal = 0;
                myCards = "";
                dealerCards = "";
                main(args);
            } else {
                System.exit(0);
            }
        } //if the dealer wins
        else {
            DealerWins++;
            int choice = JOptionPane.showOptionDialog(null,
                    "You Lose :(!\n\n" + PlayerWins + ":" + DealerWins,
                    "BlackJack",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);
            //play again
            if (choice == JOptionPane.YES_OPTION) {
                Turn = "player";
                DealersCards.removeAll(DealersCards);
                PlayersCards.removeAll(PlayersCards);
                PlayerTotal = 0;
                DealerTotal = 0;
                myCards = "";
                dealerCards = "";
                main(args);
            } else {
                System.exit(0);
            }
        } // jesus fuck nesting

    }

    //dealer's choise logic, i'm pretty bad at blackjack if you couldn't tell so i'm making this up
    public static boolean DealersHits() {
        if (DealerTotal >= 16) {
            return false;
        }
        if (DealerTotal == 15 && PlayersCards.get(0) > 7) {
            return true;
        }
        if (PlayersCards.get(0) > 10) {
            return true;
        }
        if (PlayersCards.get(0) < 8 && PlayersCards.get(0) > 4) {
            return true;
        }
        if (DealerTotal <= 14) {
            return true;
        }
        return false;
    }

    // "Hit" action in blackjack, adds a 'card' to the player's hand
    public static void Hit(String player) {
        if (player.equals("player")) {
            //give player a new card
            //PlayersCards = addMember(random.nextInt(11) + 1, PlayersCards);
            PlayersCards.add(random.nextInt(10) + 2);
            //update the total of the player's cards
            PlayerTotal = 0;
            for (int i = 0; i < PlayersCards.size(); i++) {
                PlayerTotal += PlayersCards.get(i);
            }
        } else {
            //give dealer a new card
            DealersCards.add(random.nextInt(10) + 2);
            //update the total of the dealer's cards
            DealerTotal = 0;
            for (int i = 0; i < DealersCards.size(); i++) {
                DealerTotal += DealersCards.get(i);
            }
        }
    }
}
