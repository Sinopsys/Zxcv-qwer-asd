package immediate.shopdiscounts.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import immediate.shopdiscounts.EmptyRecyclerView;
import immediate.shopdiscounts.MainActivity;
import immediate.shopdiscounts.R;
import immediate.shopdiscounts.adapters.ShoppingCartAdapter;

import static immediate.shopdiscounts.MainActivity.GROCERY_LIST_ITEMS_KEY;


public class GroceryListFragment extends Fragment{

    public GroceryListFragment(){
    }

    EmptyRecyclerView shoppingCart;
    TextView emptyCartHint;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<String> lst = ((ShoppingCartAdapter)shoppingCart.getAdapter()).getDataCopy();
        outState.putSerializable(GROCERY_LIST_ITEMS_KEY, lst);
    }

    public static GroceryListFragment newInstance() {
        return new GroceryListFragment();
    }


    public void onFabClicked () {
        final EditText text = new EditText(getContext());

        new AlertDialog.Builder(getContext())
                .setTitle("Введите название продукта:")
                .setView(text)
                .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((ShoppingCartAdapter)shoppingCart.getAdapter()).add(text.getText().toString());
                    }
                })
                .setCancelable(true)
                .create()
                .show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grocerylist, container, false);
        shoppingCart = (EmptyRecyclerView) v.findViewById(R.id.shoppingCart);
        emptyCartHint = (TextView) v.findViewById(R.id.emptyLabel);

        shoppingCart.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingCart.setEmptyView(emptyCartHint);
        shoppingCart.setAdapter(new ShoppingCartAdapter((MainActivity)getActivity()));

        if (savedInstanceState != null && savedInstanceState.containsKey(GROCERY_LIST_ITEMS_KEY))
        {
            @SuppressWarnings("unchecked")
            ArrayList<String> lst = (ArrayList<String>) savedInstanceState.getSerializable(GROCERY_LIST_ITEMS_KEY);
            ((ShoppingCartAdapter)shoppingCart.getAdapter()).addAll(lst);
        }
        return v;
    }

}
