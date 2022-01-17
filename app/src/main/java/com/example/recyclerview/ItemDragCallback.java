package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class ItemDragCallback extends ItemTouchHelper.Callback {
    private RecyclerView.Adapter adapter;
    private List list;

    public ItemDragCallback(RecyclerView.Adapter adapter, List list) {
        this.adapter = adapter;
        this.list = list;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //也就是说返回值是组合式的
        //makeMovementFlags (int dragFlags, int swipeFlags)，看下面的解释说明
        int swipeFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //如果也监控左右方向的话，swipFlag=ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
        int dragFlag = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        //等价于：0001&0010;多点触控标记触屏手指的顺序和个数也是这样标记哦
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if(viewHolder.getAdapterPosition() == list.size() - 1){
            return false;
        }

        //直接按照文档来操作啊，这文档写得太给力了,简直完美！
        adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        //注意这里有个坑的，itemView 都移动了，对应的数据也要移动
        Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
}