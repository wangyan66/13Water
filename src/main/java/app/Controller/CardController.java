package app.Controller;

import app.Card;
import app.Player;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yjn
 * @creatTime 2019/10/4 - 0:33
 */
@Controller
@RestController
public class CardController {

    @ResponseBody
    @RequestMapping("/hello")
    public String card(@RequestBody String str){
        List<Card> cards = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(str);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject object = (JSONObject)jsonArray.get(i);
                cards.add(new Card(object.getInt("rank"),object.getInt("type")));
            }
            Player player = new Player();
            player.change(cards);
            JSONArray result = new JSONArray();
            for (int i = 0;i < player.choice.head.size(); i++){
                Card card = player.choice.head.get(i);
                JSONObject cardJson = new JSONObject();
                cardJson.put("type",card.getType());
                cardJson.put("rank",card.getRank());
                result.put(cardJson);
            }
            for (int i = 0;i < player.choice.mid.size(); i++){
                Card card = player.choice.mid.get(i);
                JSONObject cardJson = new JSONObject();
                cardJson.put("type",card.getType());
                cardJson.put("rank",card.getRank());
                result.put(cardJson);
            }
            for (int i = 0;i < player.choice.end.size(); i++){
                Card card = player.choice.end.get(i);
                JSONObject cardJson = new JSONObject();
                cardJson.put("type",card.getType());
                cardJson.put("rank",card.getRank());
                result.put(cardJson);
            }
            return result.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @ResponseBody
    @RequestMapping("/hello2")
    public String card2(@RequestBody String str){
        List<Card> cards = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(str);
            String cardStr = jsonObject.getString("card");
            String[] cardSplit = cardStr.split(" ");
            for (int i = 0; i < cardSplit.length; i++){
                cards.add(new Card(cardSplit[i]));
            }
            Player player = new Player();
            player.change(cards);
            JSONObject result = new JSONObject();
            JSONArray cardArray= new JSONArray();
            String str1 = "";
            String str2 = "";
            String str3 = "";
            for (int i = 0;i < player.choice.head.size(); i++){
                Card card = player.choice.head.get(i);
                str1 +=  card.toString();
                if (i < player.choice.head.size() - 1)
                    str1 += " ";
            }
            for (int i = 0;i < player.choice.mid.size(); i++){
                Card card = player.choice.mid.get(i);
                str2 +=  card.toString();
                if (i < player.choice.mid.size() - 1)
                    str2 += " ";
            }
            for (int i = 0;i < player.choice.end.size(); i++){
                Card card = player.choice.end.get(i);
                str3 +=  card.toString();
                if (i < player.choice.end.size() - 1)
                    str3 += " ";
            }
            cardArray.put(str1);
            cardArray.put(str2);
            cardArray.put(str3);
            result.put("card",cardArray);
            return result.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
