package com.sty.rcv.gridview.title;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sty.rcv.gridview.title.adapter.RcvGridTitleAdapter;
import com.sty.rcv.gridview.title.model.GridItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<GridItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews(){
        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);

        dataList = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            GridItem gridItem = new GridItem();
            gridItem.setText("测试啊测试");
            gridItem.setDrawableId(R.drawable.operate_analyse);
            dataList.add(gridItem);
        }

        RcvGridTitleAdapter adapter = new RcvGridTitleAdapter(this, dataList);
        //position: 插入item的位置，注意这里的下标是包含title的（title算一个item，并且所有item随着插入的title的增多而改变），
        //即该position参数可以理解为包含title的所有item中title所处于的插入的位置
        adapter.addTitle(0, "第一个标题");
        adapter.addTitle(4, "第二个标题");
        adapter.addTitle(13, "第三个标题");

        recyclerView.setAdapter(adapter);
    }
}
