package com.example.game;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private static final int[][] images = new int[][]{
        { R.drawable.chrome9, R.drawable.chrome8, R.drawable.chrome7, R.drawable.chrome6, R.drawable.chrome5, R.drawable.chrome4, R.drawable.chrome3, R.drawable.chrome2, R.drawable.chrome1 },
        { R.drawable.android9, R.drawable.android8, R.drawable.android7, R.drawable.android6, R.drawable.android5, R.drawable.android4, R.drawable.android3, R.drawable.android2, R.drawable.android1 }
    };

    private final List<Integer> mItems = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;
    private final OnWinListener mWinListener;
    private static int level = 0;

    public RecyclerListAdapter(Fragment fragment) {
        mDragStartListener = (OnStartDragListener) fragment;
        mWinListener = (OnWinListener) fragment;
        loadLevel();
    }

    private static boolean isWin(List<Integer> items) {
        boolean isWinner = true;
        for (int i = 0; i < images[level].length; i++) {
            if (items.get(i) != images[level][i]) {
                isWinner = false;
                break;
            }
        }
        return isWinner;
    }

    public void loadLevel() {
        mItems.clear();
        for (int i = 0; i < images[level].length; i++) {
            mItems.add(i, images[level][i]);
        }
        Collections.shuffle(mItems);
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.imageView.setImageResource(mItems.get(position));
        holder.imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) ==
                        MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public void onSelectedChanged() {
        if (!isWin(mItems)) {
            return;
        }
        level = Math.min(images.length - 1, ++level);
        mWinListener.onWin();
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
