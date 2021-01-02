package xyz.net7.sampleroom.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import xyz.net7.sampleroom.R;
import xyz.net7.sampleroom.model.DataItem;
import xyz.net7.sampleroom.model.DataViewModel;


public class ListFragment extends Fragment {

    private DataViewModel viewModel;
    private List<DataItem> mDataItemList;
    private ListRecyclerViewAdapter mListAdapter;
    private OnListFragmentInteractionListener mListener;

    public void setListData(List<DataItem> dataItemList) {
        //if data changed, set new list to adapter of recyclerview

        if (mDataItemList == null) {
            mDataItemList = new ArrayList<>();
        }
        mDataItemList.clear();
        mDataItemList.addAll(dataItemList);

        if (mListAdapter != null) {
            mListAdapter.setListData(dataItemList);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        Context context = view.getContext();
        //set recyclerview
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mListAdapter = new ListRecyclerViewAdapter(mListener);

        if (mDataItemList != null) {
            mListAdapter.setListData(mDataItemList);
        }
        recyclerView.setAdapter(mListAdapter);

        //Add new item to db
        Button addButton = (Button) view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.insertItem(new DataItem());

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //get viewmodel
        viewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        //bind to Livedata
        viewModel.getAllData().observe(this, new Observer<List<DataItem>>() {
            @Override
            public void onChanged(@Nullable List<DataItem> dataItems) {
                if (dataItems != null) {
                    setListData(dataItems);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        //onClick items of list
        void onListClickItem(DataItem dataItem);

        //onClick delte item from list
        void onListFragmentDeleteItemById(long idItem);
    }
}
