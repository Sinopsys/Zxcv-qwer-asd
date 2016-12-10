package immediate.shopdiscounts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import immediate.shopdiscounts.EmptyRecyclerView;
import immediate.shopdiscounts.MainActivity;
import immediate.shopdiscounts.R;
import immediate.shopdiscounts.adapters.ItemListAdapter;

public class ShoppingCartFragment extends Fragment {

    EmptyRecyclerView cart;
    TextView total;
    ItemListAdapter adapter;


    public ShoppingCartFragment(){

    }

    public void updateTotal() {
        float _old = adapter.totalSumOld();
        float _new = adapter.totalSumNew();

        if (_old != 0.0) {
            Spanned s = Html.fromHtml(String.format("<big><big>Сумма:</big></big> <font color='red'><s>%.2f</s> &#8381;</font> <big><big><font color='black'>%.2f &#8381;</font></big></big>", adapter.totalSumOld(), adapter.totalSumNew()));
            total.setText(s);
        } else {
            total.setText(String.format("Сумма: %.2f \u20BD", _new));
        }
    }

    public static ShoppingCartFragment newInstance () {
        return new ShoppingCartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopcart, container, false);
        cart = (EmptyRecyclerView) rootView.findViewById(R.id.item_list);

        adapter = ((MainActivity)getActivity()).shoppingCartAdapter;
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                updateTotal();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                updateTotal();
            }

            @Override
            public void onChanged() {
                updateTotal();
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int i = viewHolder.getAdapterPosition();
                adapter.removeAt(i);
            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(cart);

        cart.setAdapter(adapter);
        cart.setLayoutManager(new LinearLayoutManager(getContext()));

        total = (TextView) rootView.findViewById(R.id.total);
        TextView emptyLabel = (TextView) rootView.findViewById(R.id.emptyLabel);

        cart.setEmptyView(emptyLabel);



        updateTotal();

        return rootView;
    }

}
