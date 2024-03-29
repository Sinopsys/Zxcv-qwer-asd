package immediate.shopdiscounts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import immediate.shopdiscounts.EmptyRecyclerView;
import immediate.shopdiscounts.MainActivity;
import immediate.shopdiscounts.R;
import immediate.shopdiscounts.adapters.ItemListAdapter;
import immediate.shopdiscounts.api.Interactor;
import immediate.shopdiscounts.api.Item;

import static immediate.shopdiscounts.MainActivity.ITEMS_KEY;


public class ItemsFragment extends Fragment {
    public ItemsFragment() {
    }

    EmptyRecyclerView itemList;

    public static Fragment newInstance() {
        return new ItemsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ((MainActivity) getActivity()).adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((MainActivity) getActivity()).adapter.filter(newText);
                return false;
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_items, container, false);

        itemList = (EmptyRecyclerView) rootView.findViewById(R.id.item_list);
        ProgressBar progress = (ProgressBar) rootView.findViewById(R.id.progress);

        itemList.setLayoutManager(new LinearLayoutManager(getContext()));
        itemList.setEmptyView(progress);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (((MainActivity)getActivity()).adapter != null) {
            outState.putParcelableArrayList(ITEMS_KEY, ((MainActivity) getActivity()).adapter.getDataCopy());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainActivity) getActivity()).adapter = new ItemListAdapter(
                (MainActivity) getActivity()
        );

        itemList.setAdapter(((MainActivity) getActivity()).adapter);

        if (savedInstanceState != null && savedInstanceState.containsKey(ITEMS_KEY)) {
            ArrayList<Item> restoredItems = savedInstanceState.getParcelableArrayList(ITEMS_KEY);
            ((MainActivity) getActivity()).adapter.addAll(restoredItems);
        } else {
            try {
                new Interactor((MainActivity) getActivity()).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
