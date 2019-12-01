package com.example.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;


public class GameAreaFragment extends Fragment implements
        OnStartDragListener, OnWinListener {
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerListAdapter adapter;
    private Button mButton;

    @Override
    public void onViewCreated(View view, Bundle icicle) {
        super.onViewCreated(view, icicle);

        mButton = view.findViewById(R.id.button);
        adapter = new RecyclerListAdapter(this);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onWin() {
        mButton.setText("Winner");
        mButton.setBackgroundResource(R.color.colorPrimary);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.loadLevel();
            }
        });
    }

    @Override
    public void onReset() {
        mButton.setText("Restart");
        mButton.setBackgroundResource(R.color.colorAccent);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.shuffle();
            }
        });
    }
}
