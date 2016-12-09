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
import android.widget.Toast;

import immediate.shopdiscounts.EmptyRecyclerView;
import immediate.shopdiscounts.MainActivity;
import immediate.shopdiscounts.R;
import immediate.shopdiscounts.adapters.ShoppingCartAdapter;


public class ShopCartFragment extends Fragment{

    public ShopCartFragment(){
    }

    EmptyRecyclerView shoppingCart;
    TextView emptyCartHint;

    public static ShopCartFragment newInstance() {
        return new ShopCartFragment();
    }


    public void onFabClicked () {
        Toast.makeText(getContext(), "you clicked a fab in second fragment", Toast.LENGTH_SHORT).show();

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
        View v = inflater.inflate(R.layout.fragment_shopcart, container, false);
        shoppingCart = (EmptyRecyclerView) v.findViewById(R.id.shoppingCart);
        emptyCartHint = (TextView) v.findViewById(R.id.emptyLabel);

        shoppingCart.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingCart.setEmptyView(emptyCartHint);
        shoppingCart.setAdapter(new ShoppingCartAdapter((MainActivity)getActivity()));

        return v;
    }

}
