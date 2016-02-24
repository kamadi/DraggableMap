package me.kamadi.draggablemap.map;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by Madiyar on 29.01.2015.
 */
public class CustomMapFragment extends SupportMapFragment {

    private MapWrapperLayout mMapWrapperLayout;

    private View mOriginalView;

    public CustomMapFragment()
    {
    }

    public MapWrapperLayout getMapWrapperLayout()
    {
        return mMapWrapperLayout;
    }

    public View getView()
    {
        return mMapWrapperLayout;
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        mOriginalView = super.onCreateView(layoutinflater, viewgroup, bundle);
        mMapWrapperLayout = new MapWrapperLayout(getActivity());
        mMapWrapperLayout.addView(mOriginalView);
        return mMapWrapperLayout;
    }

    public void setOnDragListener(MapWrapperLayout.OnDragListener ondraglistener)
    {
        mMapWrapperLayout.setOnDragListener(ondraglistener);
    }
}
