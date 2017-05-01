package com.mydream.project.personalrecognition.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mydream.project.personalrecognition.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MX on 2017/4/27.
 */

public class IDCardImgAdapter extends PagerAdapter {
    private Context mContext;
    private List<View> viewList;
    private Bitmap[] bitmaps;
    public IDCardImgAdapter(Context context, List<View> viewList, Bitmap[] bitmaps){
        mContext = context;
        this.viewList = viewList;
        this.bitmaps = bitmaps;
    }
    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(viewList.get(position));

    }

    @Override
    public int getItemPosition(Object object) {

        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return "";
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder holder = new ViewHolder();
        View view = viewList.get(position);
        holder.idcardImageView = (ImageView)view.findViewById(R.id.iv_idCardView_cardImg);
        if(null != bitmaps && bitmaps.length == 2){
            //获得正反面图片成功
            holder.idcardImageView.setImageBitmap(bitmaps[position]);
        }
        container.addView(viewList.get(position));
        return viewList.get(position);
    }


    class ViewHolder {
        ImageView idcardImageView;
    }
}
