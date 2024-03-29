package immediate.shopdiscounts;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import immediate.shopdiscounts.adapters.ItemListAdapter;
import immediate.shopdiscounts.adapters.SectionsPagerAdapter;
import immediate.shopdiscounts.fragments.GroceryListFragment;

public class MainActivity extends AppCompatActivity {

    public static final String ITEMS_KEY = "items";
    public static final String SHOPPING_CART_ITEMS_KEY = "shopping_cart_items";
    public static final String GROCERY_LIST_ITEMS_KEY = "grocery_list";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public FloatingActionButton fab;
    public ItemListAdapter adapter;

    public ItemListAdapter shoppingCartAdapter;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2); // not destroy off-screen fragments while switching them

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 1) {
                    fab.show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GroceryListFragment)mSectionsPagerAdapter.getCurrentFragment()).onFabClicked();
            }
        });

        fab.hide(); // hide FAB initially

        shoppingCartAdapter = new ItemListAdapter(this);
    }

}
