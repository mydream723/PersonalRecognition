package com.mydream.project.personalrecognition.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mydream.project.personalrecognition.R;
import com.mydream.project.personalrecognition.activity.BaseActivity;
import com.mydream.project.personalrecognition.activity.PersonalDetailActivity;
import com.mydream.project.personalrecognition.adapter.HistoryDetailAdapter;
import com.mydream.project.personalrecognition.adapter.HistoryItemAdapter;
import com.mydream.project.personalrecognition.db.DBManager;
import com.mydream.project.personalrecognition.entity.HistroyInfo;
import com.mydream.project.personalrecognition.utils.Constances;
import com.mydream.project.personalrecognition.view.DividerItemDecoration;

import java.util.List;

public class HistoryAllFragment extends BaseFragment implements HistoryItemAdapter.HistoryItemOnClickListener{
    private static final String TAG = HistoryAllFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;

    private List<HistroyInfo> historyList;
    private RecyclerView mRecyclerView;
    private HistoryItemAdapter mRecyclerAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, null);
        initView(view);
        initEvent();

        mRecyclerAdapter = new HistoryItemAdapter(mContext, historyList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL_LIST));

        mRecyclerAdapter.setHistoryItemClickListener(this);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mContext = getActivity();

        historyList = DBManager.getInstance().getmHistroyInfoDao().queryHistoryAll();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rcv_historyFragment_list);
    }

    @Override
    public void onItemClick(View v, int pos) {
        Intent detailIntent = new Intent(mContext, PersonalDetailActivity.class);
        HistroyInfo info = historyList.get(pos);
        if(null != info)
            Log.e(TAG, info.getPersonalInfo().getName());
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constances.INTENT_PERSONAL, info);
        detailIntent.putExtras(bundle);
        startActivity(detailIntent);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
