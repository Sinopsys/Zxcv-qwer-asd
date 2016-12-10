package immediate.shopdiscounts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import immediate.shopdiscounts.fragments.GroceryListFragment;
import immediate.shopdiscounts.fragments.ItemsFragment;
import immediate.shopdiscounts.fragments.ShoppingCartFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private Fragment mCurrentFragment;

    public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ItemsFragment.newInstance();
        }
        else if (position == 1) {
            return GroceryListFragment.newInstance();
        }
        else if (position == 2) {
            return ShoppingCartFragment.newInstance();
        }

        return null;
    }




    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Продукты";
            case 1:
                return "Список покупок";
            case 2:
                return "Корзина";
        }
        return null;
    }
}
