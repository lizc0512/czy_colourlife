package cn.net.cyberway.utils;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class AdapterDiffCallBack extends DiffUtil.Callback {

    private List<String> mOldList;
    private List<String> mNewList;

    public AdapterDiffCallBack(List<String> mOldList, List<String> mNewList) {
        this.mOldList = mOldList;
        this.mNewList = mNewList;
    }


    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return mOldList.get(i).getClass().equals(mNewList.get(i1).getClass());
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return mOldList.get(i).equals(mNewList.get(i1));
    }

    public  void updateData(){
        List<String> mOldList = new ArrayList<>();
        List<String> mNewList = new ArrayList<>();
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new AdapterDiffCallBack(mOldList, mNewList));
        diffResult.dispatchUpdatesTo(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
    }
}
