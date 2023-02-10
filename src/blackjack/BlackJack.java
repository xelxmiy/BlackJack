/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package blackjack;
import java.util.*;
/**
 * @author a.belski
 * @version 1.0.0
 * @since 10-Feb-2023
 */
public class BlackJack {
    //init variables
    static Random random = new Random();
    //main being required static making everything static is the absolute worst
    public static int[] DealersCards = {1, 2, 3 ,4}; 
    public static int[] PlayersCards;
    
    public static void main(String[] args) {      
        for(int x = 0; x <10; x++) {
            Hit("player");
        } 
    }
    // "Hit" action in blackjack, adds a 'card' to the player's hand
    public static void Hit(String player) {
        if(player.equals("player")) {
            addMember(random.nextInt(11), PlayersCards);
        }
        else {         
            addMember(random.nextInt(11), DealersCards);
        } 
    }
    //thanks stackoverflow, adding members to arrays is the fucking worst
    //adds int x to the given array
    public static void addMember(int x, int[] array) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList());  
        arrayList.add(x);  
        array = arrayList.stream().mapToInt(i->i).toArray(); 
        for(int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");   
        }
    }
}
