package com.stephentuso.welcome.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

/**
 * Created by stephentuso on 11/15/15.
 */
public class WelcomeScreenPage implements ViewPager.OnPageChangeListener {

    private final WelcomeFragmentHolder fragmentHolder;
    private final BackgroundColor backgroundColor;
    private int index = -2;

    /**
     * Interface to be implemented by fragments that are part of a WelcomeActivity
     */
    public interface OnChangeListener {

        /**
         * // TODO: Fix this for RTL?
         * Called when this page is coming into view
         * @param pageIndex The index in the ViewPager of the fragment that implements this
         * @param offset The % offset of this page, negative if page is off the screen on the right, positive if off on the left
         * @param offsetPixels The offset of this page in pixels, negative if page is off the screen on the right, positive if off on the left
         */
        void onScrolled(int pageIndex, float offset, int offsetPixels);

        /**
         * Called when the fragment that implements this is selected in the ViewPager
         * @param pageIndex The index of this page
         */
        void onSelected(int pageIndex);

        /**
         * Called when the scroll state of the ViewPager changes
         */
        void onScrollStateChanged(int state);
    }

    public WelcomeScreenPage(WelcomeFragmentHolder fragmentHolder, BackgroundColor backgroundColor) {
        this.fragmentHolder = fragmentHolder;
        this.backgroundColor = backgroundColor;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Fragment getFragment() {
        return fragmentHolder.getFragment();
    }

    public Fragment createFragment() {
        return fragmentHolder.createFragment();
    }

    public BackgroundColor getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (getFragment() != null && getFragment() instanceof OnChangeListener && position - index <= 1) {
            Fragment fragment = getFragment();

            int fragmentWidth = 0;
            if (fragment.getView() != null) {
                fragmentWidth = fragment.getView().getWidth();
            }

            boolean lowerPosition = position < index;
            float offset = lowerPosition ? -(1 - positionOffset) : positionOffset;
            int offsetPixels = lowerPosition ? -(fragmentWidth - positionOffsetPixels) : positionOffsetPixels;

            ((OnChangeListener) fragment).onScrolled(index, offset, offsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (getFragment() != null && getFragment() instanceof OnChangeListener && index == position) {
            ((OnChangeListener) getFragment()).onSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (getFragment() != null && getFragment() instanceof OnChangeListener) {
            ((OnChangeListener) getFragment()).onScrollStateChanged(state);
        }
    }
}