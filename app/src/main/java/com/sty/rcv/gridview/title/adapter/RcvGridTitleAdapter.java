package com.sty.rcv.gridview.title.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sty.rcv.gridview.title.R;
import com.sty.rcv.gridview.title.model.GridItem;

import java.util.List;

/**
 * Created by tian on 2018/10/8.
 */

public class RcvGridTitleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private SparseArray<String> titles = new SparseArray<>();
    private List<GridItem> dataList;
    private Context mContext;

    public RcvGridTitleAdapter(Context context, List<GridItem> dataList){
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(isTitle(viewType)){
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_grid_title, parent, false);
            TitleViewHolder titleViewHolder = new TitleViewHolder(v);
            return titleViewHolder;
        }else {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_grid_management, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);
            return myViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isTitle(position)){
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.tv.setText(titles.get(position));
            return;
        }
        //获取空过去的item（获取真实的item的位置，即去掉title后的真实位置）
        for (int i = 0; i < titles.size(); i++){
            int key = titles.keyAt(i);
            if(position > titles.keyAt(titles.size() -1)){  //item在最后一个title之后的情况
                position -= titles.size();
                break;
            }else if(key < position && position < titles.keyAt(i + 1)){ //item在某两个title之间的情况
                position -= (i + 1);
                break;
            }
        }
        final int s = position;
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv.setText(dataList.get(s).getText());
        myViewHolder.iv.setImageResource(dataList.get(s).getDrawableId());
        myViewHolder.myItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "第 " + (s + 1) + " 个item is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size() + titles.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isTitle(position)) {
            return position;  //返回title的位置，仅用作onCreateViewHolder方法中的判断--是title
        }else{
            return position + 100000; //返回title+100000的位置，仅用作onCreateViewHolder方法中的判断--不是title
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        //如果是title就占据设置的spanCount个单元格
        final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        //Sets the source to get the number of spans occupied by each item in the adapter.
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(isTitle(position)){
                    return layoutManager.getSpanCount();
                }
                return 1;
            }
        });

    }

    private boolean isTitle(int position){
        return titles.get(position) == null ? false : true;
    }

    /**
     *
     * @param position 插入item的位置，注意这里的下标是包含title的（title算一个item，并且所有item随着插入的title的增多而改变），
     *                 即该position参数可以理解为包含title的所有item中title所处于的插入的位置
     * @param title
     */
    public void addTitle(int position, String title){
        titles.put(position, title);
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder{
        TextView tv;

        public TitleViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_grid_item_title);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        ImageView iv;
        View myItemView;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_text);
            iv = (ImageView) itemView.findViewById(R.id.iv_pic);
            myItemView = itemView;
        }
    }
}
