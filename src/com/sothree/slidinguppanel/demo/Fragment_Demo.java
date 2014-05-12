package com.sothree.slidinguppanel.demo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.demo.VerticalViewPager.OnPageChangeListener;

public class Fragment_Demo extends Fragment {
    private static final String TAG = "chenee";
    public static final String SAVED_STATE_ACTION_BAR_HIDDEN = "saved_state_action_bar_hidden";
    private SlidingUpPanelLayout mLayout;
    private int slidingPanelHeight = 0;
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.slide_pane, null);

        initLayout(v);
        initPager(v);


        ListView lv2 = (ListView)v.findViewById(R.id.lv2);
        lv2.setAdapter(new ListAdapter(getActivity(), R.layout.item));

		return v;
	}
    private void initLayout(View v) {
        mLayout = (SlidingUpPanelLayout)v.findViewById(R.id.sliding_layout);
		slidingPanelHeight = mLayout.getPanelHeight();
		
		mLayout.setOverlayed(true);
		
        mLayout.setPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
//                setActionBarTranslation(mLayout.getCurrentParalaxOffset());
            }

            @Override
            public void onPanelExpanded(View panel) {
                Log.i(TAG, "onPanelExpanded");

            }

            @Override
            public void onPanelCollapsed(View panel) {
                Log.i(TAG, "onPanelCollapsed");

            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.i(TAG, "onPanelAnchored");

            }
        });

    }
    private class DynamicPagerAdapter extends FragmentStatePagerAdapter {
		public DynamicPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = Fragment.instantiate(getActivity(), Fragment_Pager1.class.getName());
				break;
			case 1:
				fragment = Fragment.instantiate(getActivity(), Fragment_Pager2.class.getName());
				break;
			default:
				break;
			}

			return fragment;
		}
	}
    
    private int mCurrentPage = -1;
    private float mPageOffset;
    private int mScrollState;
    public void initPager(View v) {
    	
		VerticalViewPager pager = (VerticalViewPager)v.findViewById(R.id.pager);
		pager.setAdapter(new DynamicPagerAdapter(getFragmentManager()));
		pager.setCurrentItem(1);
		
		pager.setOnPageChangeListener(new OnPageChangeListener(){
			
		    @Override
		    public void onPageScrollStateChanged(int state) {
		        mScrollState = state;
		
		    }
		
		    @Override
		    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		        mCurrentPage = position;
		        mPageOffset = positionOffset;
						Log.d("chenee", "onPageScrolled:"+position+".."+positionOffset +"..."+positionOffsetPixels);
		
						int h = (int) (slidingPanelHeight * (position + positionOffset));
						mLayout.setPanelHeight(h);
		    }
		
		    @Override
		    public void onPageSelected(int position) {
		        if (mScrollState == VerticalViewPager.SCROLL_STATE_IDLE) {
		            mCurrentPage = position;
						Log.d("chenee", "onPageSelected :"+position);
		        }
		
		    }

			
		});
		
	}
    @Override
	public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_STATE_ACTION_BAR_HIDDEN, mLayout.isExpanded());
    }	
	
}
