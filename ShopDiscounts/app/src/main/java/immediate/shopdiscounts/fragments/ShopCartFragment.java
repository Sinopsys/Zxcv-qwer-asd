package immediate.shopdiscounts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import immediate.shopdiscounts.EmptyRecyclerView;
import immediate.shopdiscounts.R;
import immediate.shopdiscounts.adapters.ItemListAdapter;


public class ShopCartFragment extends Fragment{

    public ShopCartFragment(){
    }

    EmptyRecyclerView shoppingCart;
    TextView emptyCartHint;

    public static ShopCartFragment newInstance() {
        return new ShopCartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopcart, container, false);
        shoppingCart = (EmptyRecyclerView) v.findViewById(R.id.shoppingCart);
        emptyCartHint = (TextView) v.findViewById(R.id.emptyLabel);

        shoppingCart.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingCart.setEmptyView(emptyCartHint);
        shoppingCart.setAdapter(new ItemListAdapter(getContext()));

        return v;
    }
}
