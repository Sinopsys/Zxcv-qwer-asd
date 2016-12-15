package immediate.shopdiscounts.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import immediate.shopdiscounts.MainActivity;
import immediate.shopdiscounts.R;
import immediate.shopdiscounts.api.Item;


public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    private ArrayList<Item> items;
    private final ArrayList<Item> dataCopy;
    private MainActivity mContext;

    public List<Item> itemsMatchingQuery(String query) {
        ArrayList<Item> queryResult = new ArrayList<>();
        query = query.toLowerCase();

        if (!query.isEmpty()) {
            for (Item item : dataCopy) {
                if (item.name.toLowerCase().contains(query)) {
                    queryResult.add(item);
                }
            }
        }

        return queryResult;
    }

    public ArrayList<Item> getDataCopy ()
    {
        @SuppressWarnings("unchecked")
        ArrayList<Item> dataCopyClone = (ArrayList<Item>) dataCopy.clone();

        return dataCopyClone;
    }

    public float totalSumNew() {
        float sum = 0;
        for (Item i : items) {
            sum += i.newPrice;
        }
        return sum;
    }

    public float totalSumOld() {
        float sum = 0;
        for (Item i : items) {
            sum += i.oldPrice;
        }
        return sum;
    }


    public void filter(String query) {
        if (query.isEmpty()) {
            items.clear();
            items.addAll(dataCopy);
        } else {
            items.clear();
            items.addAll(itemsMatchingQuery(query));
        }
        notifyDataSetChanged();
    }

    public ItemListAdapter(MainActivity ctx)
    {
        items = new ArrayList<>();
        dataCopy = new ArrayList<>();
        mContext = ctx;
    }


    public void add(Item newItem) {
        items.add(newItem);
        notifyItemRangeInserted(items.size() - 1, 1);
        dataCopy.add(newItem);
    }


    public void addAll(List<Item> newItems) {
        int oldSz = items.size();
        items.clear();
        notifyItemRangeRemoved(0, oldSz);
        items.addAll(newItems);
        notifyItemRangeInserted(0, items.size());

        dataCopy.addAll(newItems);
    }

    public void removeAt(int adapterPosition) {
        items.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }



    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_view, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final Item item = items.get(position);

        holder.name.setText(item.name);
        holder.category.setText(item.category);
        //TODO: all placeholders
        Picasso.with(mContext).load(item.imageURL).into(holder.image);

        @SuppressWarnings("deprecation")
        Spanned s = Html.fromHtml(String.format("<font color='red'><s>%.2f</s> &#8381;</font> <big><big><font color='black'>%.2f &#8381;</font></big></big>", item.oldPrice, item.newPrice));

        holder.price.setText(s);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mContext.shoppingCartAdapter.add(item);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        TextView category;
        ImageView image;
        TextView price;

        ItemViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            category = (TextView) itemView.findViewById(R.id.category);
            image = (ImageView) itemView.findViewById(R.id.image);
            price = (TextView) itemView.findViewById(R.id.price);
        }
    }
}
