package com.mydream.project.personalrecognition.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hs.cvr_100p550im.sdk.GetImg;
import com.hs.cvr_100p550im.sdk.IDCardInfo;
import com.mydream.project.personalrecognition.R;
import com.mydream.project.personalrecognition.activity.BaseActivity;
import com.mydream.project.personalrecognition.utils.RecognitionUtils;

import java.io.IOException;
import java.security.spec.ECField;

/**
 * 身份证识别Fragment
 */
public class RecognitionIDFragment extends BaseFragment implements RecognitionUtils.RecognitionInitialCallback, RecognitionUtils.RecognitionIDCallback{
    private static final String TAG = "RecognitionIDFragment";
    private FloatingActionButton recognitionButton;

    private OnFragmentInteractionListener mListener;
    /**
     * 识别工具类
     */
    private RecognitionUtils mRecognitionUtils;

    public RecognitionIDFragment() {
        // Required empty public constructor
    }

    public static RecognitionIDFragment newInstance(){
        RecognitionIDFragment fragment = new RecognitionIDFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recognition_id, container, false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecognitionUtils = RecognitionUtils.getInstance();
        mRecognitionUtils.openDevice(mContext);
        mRecognitionUtils.setRecognitionInitialCallback(this);
    }

    private void initView(View view){
        recognitionButton = (FloatingActionButton)view.findViewById(R.id.btn_idRecognitionFragment_idRecognition);
    }

    @Override
    protected void initData() {
        super.initData();
        mContext = getActivity();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        recognitionButton.setOnClickListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_idRecognitionFragment_idRecognition:
                //开始识别身份证
                Log.d(TAG,"onClick");
                if(null != mRecognitionUtils){
                    mRecognitionUtils.toRecognitionID();
                    mRecognitionUtils.setRecognitionIDCallback(this);
                }
                break;
        }
    }

    @Override
    public void initialStart() {
        Log.d(TAG, "initialStart");

    }

    @Override
    public void initialError() {
        Log.d(TAG, "initialError");
    }

    @Override
    public void initialComplete() {
        Log.d(TAG, "initialComplete");
    }

    @Override
    public void recognitionStart() {
        Log.d(TAG, "recognitionStart");
    }

    @Override
    public void recoginitionComplete(IDCardInfo infoWithoutImage) {
        Log.d(TAG, "recoginitionComplete" + infoWithoutImage.getIDCard());
    }

    @Override
    public void analysisImageComplete(IDCardInfo info) {
        Log.d(TAG, "analysisImageComplete" );
        if(null != info){
            try {
                GetImg.ShowBmp(info, mContext, 1);
            }catch (IOException e){

            }
        }


    }

    @Override
    public void recognitionError() {
        Log.d(TAG, "recognitionError" );
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
