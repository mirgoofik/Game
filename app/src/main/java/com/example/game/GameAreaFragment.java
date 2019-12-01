package com.example.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new SimpleGridLayoutManager(getContext(), 3));
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
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
                onReset();
                adapter.loadLevel();
            }
        });
    }

    public void onReset() {
        mButton.setText("Restart");
        mButton.setBackgroundResource(R.color.colorAccent);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.loadLevel();
            }
        });
    }
}
