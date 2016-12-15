package immediate.shopdiscounts.api;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Item implements Parcelable {
    public String category;
    public String name;
    public String imageURL;
    public String date;
    public String condition;
    public String discount;
    public float oldPrice;
    public float newPrice;

    public static Item fromJSONObject(JSONObject jsonObject) {
        Item item = new Item();
        try {
            item.name = jsonObject.getString("name");
            item.category = jsonObject.getString("category");
            item.imageURL = jsonObject.getString("img_url");
            item.date = jsonObject.getString("date");
            item.condition = jsonObject.getString("condition");
            item.oldPrice = Float.parseFloat(jsonObject.getString("old_price"));
            item.newPrice = Float.parseFloat(jsonObject.getString("new_price"));
            item.discount = jsonObject.getString("discount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Item(Parcel in) {
        category = in.readString();
        name = in.readString();
        imageURL = in.readString();
        date = in.readString();
        condition = in.readString();
        discount = in.readString();
        oldPrice = in.readFloat();
        newPrice = in.readFloat();
    }

    private Item() {
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(category);
        out.writeString(name);
        out.writeString(imageURL);
        out.writeString(date);
        out.writeString(condition);
        out.writeString(discount);
        out.writeFloat(oldPrice);
        out.writeFloat(newPrice);
    }
    public static final Parcelable.Creator<Item> CREATOR
            = new Parcelable.Creator<Item>() {

        @Override
        public Item createFromParcel(Parcel parcel) {
            return new Item(parcel);
        }

        @Override
        public Item[] newArray(int i) {
            return new Item[i];
        }
        };
}
