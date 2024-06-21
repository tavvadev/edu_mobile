package com.jds.eduTech.adapters;


import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jds.eduTech.R;

public class ViewAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images={R.drawable.splash_img1,R.drawable.splash_img2,R.drawable.splash_img3};
    private OnSliderChangeListener onSliderChangeListener;
    private static final int AUTO_SLIDE_DELAY = 5000; // Delay in milliseconds

    private Handler autoSlideHandler;
    private Runnable autoSlideRunnable;
    private ViewPager viewPager;


    public ViewAdapter(Context context,ViewPager viewPager)
    {
        this.context = context;
        autoSlideHandler = new Handler();
        autoSlideRunnable = new Runnable() {
            @Override
            public void run() {
                int currentPosition = viewPager.getCurrentItem();
                int newPosition = (currentPosition + 1) % images.length;
                viewPager.setCurrentItem(newPosition);
                autoSlideHandler.postDelayed(this, AUTO_SLIDE_DELAY);
            }
        };
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
        View view =layoutInflater.inflate(R.layout.indicators_item,null);
        ImageView imageView=view.findViewById(R.id.image_view);
        imageView.setImageResource(images[position]);
        ViewPager viewPager=(ViewPager) container;
        viewPager.addView(view,0);
        viewPager.postDelayed(autoSlideRunnable, AUTO_SLIDE_DELAY);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (onSliderChangeListener != null) {
                    onSliderChangeListener.onSliderChanged(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return view;
    }
    public interface OnSliderChangeListener {
        void onSliderChanged(int position);
    }
    public void setOnSliderChangeListener(OnSliderChangeListener listener) {
        this.onSliderChangeListener = listener;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager=(ViewPager) container;
        View view=(View) object;
        viewPager.removeView(view);
        autoSlideHandler.removeCallbacks(autoSlideRunnable);
    }
}

