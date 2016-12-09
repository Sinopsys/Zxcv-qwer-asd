package immediate.shopdiscounts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import immediate.shopdiscounts.fragments.ItemsFragment;
import immediate.shopdiscounts.fragments.ShopCartFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ItemsFragment.newInstance();
        }
        else if (position == 1) {
            return ShopCartFragment.newInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Продукты";
            case 1:
                return "Корзина";
        }
        return null;
    }
}
