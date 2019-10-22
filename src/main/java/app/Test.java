package app;

import app.Controller.CardController;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author yjn
 * @creatTime 2019/10/15 - 0:42
 */
public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.next();
        List<Card> allCards = new ArrayList<>();
        Random random = new Random();
        CardController cardController = new CardController();
        for (int i = 1;i <= 4; i++){
            for (int j = 1; j <= 13; j++){
                allCards.add(new Card(j,i));
            }
        }

        for (int i = 0; i < 100; i++){
            List<Card> tempCards = new ArrayList<>();
            List<Card> cards = new ArrayList<>();
            tempCards.addAll(allCards);
            for (int j = 0; j < 13; j++){
                Card card = tempCards.get(random.nextInt(52 - j));
                cards.add(card);
                tempCards.remove(card);
            }
            String cardStr = "";
            for (int j = 0; j < 13; j++){
                Card card = cards.get(j);
                cardStr += card.toString();
                if (j < 12)
                    cardStr += " ";
            }
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id",0);
                jsonObject.put("card",cardStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(cardStr);
            System.out.println(cardController.card2(jsonObject.toString()));;
        }
        scanner.next();
    }
}
