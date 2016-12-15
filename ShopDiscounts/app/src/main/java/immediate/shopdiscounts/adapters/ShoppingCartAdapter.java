package immediate.shopdiscounts.adapters;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import immediate.shopdiscounts.MainActivity;
import immediate.shopdiscounts.R;
import immediate.shopdiscounts.api.Item;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartHolder> {

    ArrayList<String> items;
    MainActivity mContext;

    public ShoppingCartAdapter(MainActivity context)
    {
        items = new ArrayList<>();
        mContext = context;
    }

    public void add (String what) {
        items.add(what);
        notifyItemInserted(items.size());
    }

    public void addAll(List<String> newItems) {
        int oldSz = items.size();
        items.clear();
        notifyItemRangeRemoved(0, oldSz);
        items.addAll(newItems);
        notifyItemRangeInserted(0, items.size());
    }


    public void remove(String what) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(what)) {
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public ArrayList<String> getDataCopy(){
        @SuppressWarnings("unchecked")
        ArrayList<String> itemsCopy = (ArrayList<String>) items.clone();

        return itemsCopy;
    }

    @Override
    public ShoppingCartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShoppingCartHolder(LayoutInflater.from(mContext).inflate(R.layout.shoppingcart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ShoppingCartHolder holder, final int position) {
        final String what = items.get(position);

        holder.name.setText(what);
        final List<Item> q = mContext.adapter.itemsMatchingQuery(what);
        if (q.size() > 0) {
            holder.rootView.setBackgroundColor(Color.GREEN);
            float minPrice = Float.MAX_VALUE;
            for (Item item : q) {
                if (item.newPrice < minPrice)
                {
                    minPrice = item.newPrice;
                }
            }
            holder.minPrice.setText(String.format("Самая дешёвая цена: %.2f руб.", minPrice));
        } else {
            holder.rootView.setBackgroundColor(Color.RED);
            holder.minPrice.setVisibility(View.INVISIBLE);
        }
        holder.associatedItems = q;

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (q.size() > 0) {
                    RecyclerView rcv = new RecyclerView(mContext);
                    rcv.setLayoutManager(new LinearLayoutManager(mContext));
                    ItemListAdapter _adapter = new ItemListAdapter(mContext);
                    _adapter.addAll(q);
                    rcv.setAdapter(_adapter);

                    new AlertDialog.Builder(mContext)
                            .setTitle("Продукты")
                            .setView(rcv)
                            .setCancelable(true)
                            .create()
                            .show();
                } else {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Увы")
                            .setMessage("Таких продуктов не нашлось")
                            .setCancelable(true)
                            .create()
                            .show();
                }
            }
        });

        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(what);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ShoppingCartHolder extends RecyclerView.ViewHolder {
        public View rootView;
        final public TextView name;
        public TextView minPrice;
        public List<Item> associatedItems;

        public ShoppingCartHolder(View itemView)
        {
            super(itemView);
            rootView = itemView;
            name = (TextView) rootView.findViewById(R.id.name);
            minPrice = (TextView) rootView.findViewById(R.id.minPrice);
        }

    }
}
