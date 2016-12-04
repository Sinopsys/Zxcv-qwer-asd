package immediate.shopdiscounts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import immediate.shopdiscounts.ItemsFragment;
import immediate.shopdiscounts.MainActivity;

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
            return MainActivity.PlaceholderFragment.newInstance(position + 1);
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
                return "Корзина (ещё не готово)";
        }
        return null;
    }
}
