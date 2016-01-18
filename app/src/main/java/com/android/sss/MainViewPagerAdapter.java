package com.android.sss;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class MainViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;

    private int[] resources = {R.drawable.viewpager_1, R.drawable.viewpager_2, R.drawable.viewpager_3, R.drawable.viewpager_4};

    MainViewPagerAdapter(Context context) {
        mContext = context;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.main_viewpager_item_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewMainViewPager);
        imageView.setImageResource(resources[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
