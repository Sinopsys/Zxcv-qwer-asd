package immediate.shopdiscounts.api;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {

    public String category;
    public String name;
    public String imageURL;
    public String date;
    public String condition;
    public String discount;
    public float oldPrice;
    public float newPrice;

    public static Item fromJSONObject (JSONObject jsonObject) {
        Item item = new Item();
        try {
            item.name = jsonObject.getString("name");
            item.category = jsonObject.getString("category");
            item.imageURL = jsonObject.getString("img url");
            item.date = jsonObject.getString("date");
            item.condition = jsonObject.getString("condition");
            item.oldPrice = Float.parseFloat(jsonObject.getString("old price"));
            item.newPrice = Float.parseFloat(jsonObject.getString("new price"));
            item.discount = jsonObject.getString("discount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }
}
