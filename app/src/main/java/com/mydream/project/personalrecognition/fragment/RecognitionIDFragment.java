package com.mydream.project.personalrecognition.fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hs.cvr_100p550im.sdk.GetImg;
import com.hs.cvr_100p550im.sdk.IDCardInfo;
import com.mydream.project.personalrecognition.R;
import com.mydream.project.personalrecognition.activity.BaseActivity;
import com.mydream.project.personalrecognition.adapter.IDCardImgAdapter;
import com.mydream.project.personalrecognition.db.DBManager;
import com.mydream.project.personalrecognition.dialog.LoadingDialog;
import com.mydream.project.personalrecognition.entity.HistroyInfo;
import com.mydream.project.personalrecognition.entity.PersonalInfo;
import com.mydream.project.personalrecognition.utils.Constances;
import com.mydream.project.personalrecognition.utils.FileUtils;
import com.mydream.project.personalrecognition.utils.RecognitionUtils;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 身份证识别Fragment
 */
public class RecognitionIDFragment extends BaseFragment implements RecognitionUtils.RecognitionInitialCallback, RecognitionUtils.RecognitionIDCallback {
    private static final String TAG = "RecognitionIDFragment";
    /**
     * 识别按键
     */
    private FloatingActionButton recognitionButton;
    private Bitmap[] bitmaps;
    /**
     * 身份证正面布局
     */
    private ImageView frontImageView;
    /**
     * 身份证反面布局
     */
    private ImageView backImageView;
    /**
     * 右出动画
     */
    private AnimatorSet mRightOutSet;
    /**
     * 左入动画
     */
    private AnimatorSet mLeftInSet;
    /**
     * 翻转按键
     */
    private Button turnButton;

    /**
     * 是否显示反面
     */
    private boolean mIsShowBack;
    /**
     * 姓名、身份证号、描述
     */
    private TextView nameTextView, idTextView, descTextView;
    /**
     * 识别结果
     */
    private ImageView recognitionFlagImageView;
    /**
     * 扫描结果提示内容
     */
    private TextView messageTextView;
    /**
     * 扫描结果布局
     */
    private LinearLayout resultLinearLayout;

    private OnFragmentInteractionListener mListener;
    /**
     * 识别工具类
     */
    private RecognitionUtils mRecognitionUtils;
    /**
     * 提示音
     */
    private SoundPool mSoundPool;
    /**
     * 加载dialog
     */
    private LoadingDialog loadingDialog;

    private boolean isTestData = false;

    public RecognitionIDFragment() {
        // Required empty public constructor
    }

    public static RecognitionIDFragment newInstance() {
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
        // 设置动画
        setAnimators();
        // 设置镜头距离
        setCameraDistance();

        initVirtualData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecognitionUtils = RecognitionUtils.getInstance();
//        mRecognitionUtils.openDevice(mContext);
        mRecognitionUtils.setRecognitionInitialCallback(this);

        mSoundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool.load(mContext, R.raw.bi, 1);
    }

    private void initView(View view) {
        recognitionButton = (FloatingActionButton) view.findViewById(R.id.btn_idRecognitionFragment_idRecognition);
        frontImageView = (ImageView) view.findViewById(R.id.iv_idRecognitionFragment_frontImg);
        backImageView = (ImageView) view.findViewById(R.id.iv_idRecognitionFragment_backImg);
        turnButton = (Button) view.findViewById(R.id.btn_idRecognitionFragment_overturn);
        nameTextView = (TextView) view.findViewById(R.id.tv_idRecognitionFragment_name);
        idTextView = (TextView) view.findViewById(R.id.tv_idRecognitionFragment_id);
        descTextView = (TextView) view.findViewById(R.id.tv_idRecognitionFragment_desc);
        recognitionFlagImageView = (ImageView) view.findViewById(R.id.iv_idRecognitionFragment_status);

        messageTextView = (TextView) view.findViewById(R.id.tv_idRecognitionFragment_message);
        resultLinearLayout = (LinearLayout) view.findViewById(R.id.ll_idRecognitionFragment_result);
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

        turnButton.setOnClickListener(this);
    }

    // 设置动画
    private void setAnimators() {
        mRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.anim_out);
        mLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.anim_in);

        // 设置点击事件
        mRightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                turnButton.setClickable(false);
            }
        });
        mLeftInSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                turnButton.setClickable(true);
            }
        });
    }

    // 改变视角距离, 贴近屏幕
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        frontImageView.setCameraDistance(scale);
        backImageView.setCameraDistance(scale);
    }

    // 翻转卡片
    public void flipCard() {
        // 正面朝上
        if (!mIsShowBack) {
            mRightOutSet.setTarget(frontImageView);
            mLeftInSet.setTarget(backImageView);
            mRightOutSet.start();
            mLeftInSet.start();
            mIsShowBack = true;
        } else { // 背面朝上
            mRightOutSet.setTarget(backImageView);
            mLeftInSet.setTarget(frontImageView);
            mRightOutSet.start();
            mLeftInSet.start();
            mIsShowBack = false;
        }
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
        switch (view.getId()) {
            case R.id.btn_idRecognitionFragment_idRecognition:
                //开始识别身份证
                Log.d(TAG, "onClick");
                isTestData = false;
                if (null != mRecognitionUtils) {
                    mRecognitionUtils.toRecognitionID();
                    mRecognitionUtils.setRecognitionIDCallback(this);
                }
                break;
            case R.id.btn_idRecognitionFragment_overturn:
                flipCard();
                break;
        }
    }

    @Override
    public void initialStart() {
        Log.d(TAG, "initialStart");
        showLoading();

    }

    @Override
    public void initialError() {
        Log.d(TAG, "initialError");
        mRecognitionUtils.openDevice(mContext);
    }

    @Override
    public void initialComplete() {
        Log.d(TAG, "initialComplete");
        loadingDialog.dismiss();
        BaseActivity.showToast(mContext, "初始化成功");
    }

    @Override
    public void recognitionStart() {
        Log.d(TAG, "recognitionStart");
        mSoundPool.play(1, 1, 1, 0, 0, 1);
    }

    @Override
    public void recoginitionComplete(IDCardInfo infoWithoutImage) {
        Log.d(TAG, "recoginitionComplete" + infoWithoutImage.getIDCard());

    }

    @Override
    public void analysisImageComplete(IDCardInfo info) {
        Log.d(TAG, "analysisImageComplete");
        if (null != info) {
            try {
                //設置圖片
                bitmaps = GetImg.GetBmp(info, mContext, 0);
                if (null != bitmaps && bitmaps.length == 2) {
                    frontImageView.setImageBitmap(bitmaps[0]);
                    backImageView.setImageBitmap(bitmaps[1]);


                    nameTextView.setText(info.getPeopleName());
                    idTextView.setText(info.getIDCard());

                    Random random = new Random();
                    boolean isAlert = random.nextInt(10) > 5 ? true : false;
                    setPersonFlag(isAlert);
                    //插入历史记录
                    insertHistory(info, isAlert);
                } else {
                    setRecognitionError();
                }
            } catch (IOException e) {
                Log.d(TAG, "IOException");
                bitmaps = null;
                setRecognitionError();

            }


        } else {
            Log.d(TAG, "info is null");
            bitmaps = null;
            setRecognitionError();
        }
    }


    @Override
    public void recognitionError() {
        Log.d(TAG, "recognitionError");
        setRecognitionError();
    }

    private void setRecognitionError() {
        frontImageView.setImageResource(R.drawable.img_idcard_front);
        backImageView.setImageResource(R.drawable.img_idcard_back);
        setRecognitionView(false);
        recognitionFlagImageView.setImageResource(R.drawable.ic_recognition_error);
        messageTextView.setText(R.string.recognition_error);
    }

    /**
     * 设置提示
     *
     * @param isDanger
     */
    private void setPersonFlag(boolean isDanger) {
        setRecognitionView(true);
        if (isDanger) {
            recognitionFlagImageView.setImageResource(R.drawable.ic_recognition_alert);
            descTextView.setText(R.string.recognition_alert);
            descTextView.setTextColor(Color.RED);
            mSoundPool.play(1, 1, 1, 0, 6, 1);
        } else {
            recognitionFlagImageView.setImageResource(R.drawable.ic_recognition_normal);
            descTextView.setText(R.string.recognition_normals);
            descTextView.setTextColor(Color.BLACK);
            mSoundPool.play(1, 1, 1, 0, 2, 1);
        }

    }

    private void setRecognitionView(boolean isSuccess) {
        if (isSuccess) {
            //成功
            resultLinearLayout.setVisibility(View.VISIBLE);
            messageTextView.setVisibility(View.GONE);
        } else {
            resultLinearLayout.setVisibility(View.GONE);
            messageTextView.setVisibility(View.VISIBLE);
        }
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

    public void showLoading() {
        loadingDialog = new LoadingDialog(mContext);

        loadingDialog.setMessage(mContext.getResources().getString(R.string.device_initial));
        loadingDialog.show();
    }

    /**
     * 插入歷史記錄
     */
    private void insertHistory(IDCardInfo scanInfo, boolean isMarked) {
        HistroyInfo info = new HistroyInfo();
        SimpleDateFormat sdf = new SimpleDateFormat(Constances.FORMAT_DATE_SECOND);
        String scanDate = sdf.format(new Date());
        info.setScanDate(scanDate);
        info.setIsDeleted(0);
        info.setIsUploaded(0);
        info.setIsMarked(isMarked ? 1 : 0);
        PersonalInfo personalInfo = DBManager.getInstance().getmPersonalInfoDao().queryExistPersonal(scanInfo.getIDCard(), scanInfo.getStrartDate(), scanInfo.getEndDate());
        long pid = 0;
        if (null == personalInfo) {
            //没有该条身份证信息
            pid = insertCardInfo(scanInfo);
        } else {
            pid = personalInfo.getId();
        }
        if (pid != 0) {
            info.setPid(pid);
            DBManager.getInstance().getmHistroyInfoDao().insert(info);
        }


    }

    /**
     * 插入身份證信息
     *
     * @param scanInfo
     */
    private long insertCardInfo(IDCardInfo scanInfo) {
        PersonalInfo info = new PersonalInfo();
        info.setName(scanInfo.getPeopleName());
        info.setGender(scanInfo.getSex().equals("女") ? 1 : 0);
        info.setNation(scanInfo.getPeople());
        info.setBirthday(scanInfo.getBirthDay());
        info.setAddress(scanInfo.getAddr());
        info.setCardId(scanInfo.getIDCard());
        info.setSignDepartment(scanInfo.getDepartment());
        info.setValidStartDate(scanInfo.getStrartDate());
        info.setValidEndDate(scanInfo.getEndDate());
        String headerPath ="";
        if(!isTestData){
            headerPath = getHeaderPath(scanInfo.getIDCard());
            String headerOriginalPath = Environment.getExternalStorageDirectory() + Constances.PATH_HEADER_ORIGINAL;
            FileUtils.copyFile(headerOriginalPath, headerPath);
        }
        //頭像

        info.setHeader(headerPath);
        return DBManager.getInstance().getmPersonalInfoDao().insert(info);
    }


    /**
     * 获得头像存储路径
     *
     * @param fileName
     * @return
     */
    private String getHeaderPath(String fileName) {
        String rootPath = Environment.getExternalStorageDirectory().getPath();
        String headPath = rootPath + Constances.PATH_APPROOT + Constances.PATH_HEADER;
        File headFile = new File(headPath);
        if (!headFile.isDirectory())
            headFile.mkdirs();
        return headPath + fileName + ".bmp";
    }

    private void initVirtualData(){
        isTestData = true;
        IDCardInfo scanInfo = new IDCardInfo();
        scanInfo.setPeopleName("张三");
        scanInfo.setSex("男");
        scanInfo.setPeople("汉");
        scanInfo.setBirthDay("19870302");
        scanInfo.setAddr("天津市南开区天拖大厦");
        scanInfo.setIDCard("120101198703021021");
        scanInfo.setDepartment("天津市公安局南开分局");
        scanInfo.setStrartDate("2011.02.02");
        scanInfo.setEndDate("2021.02.02");
        insertHistory(scanInfo,false);


        scanInfo = new IDCardInfo();
        scanInfo.setPeopleName("李四");
        scanInfo.setSex("男");
        scanInfo.setPeople("汉");
        scanInfo.setBirthDay("19760302");
        scanInfo.setAddr("天津市南开区天拖大厦");
        scanInfo.setIDCard("120104197603021023");
        scanInfo.setDepartment("天津市公安局南开分局");
        scanInfo.setStrartDate("2011.02.02");
        scanInfo.setEndDate("2021.02.02");
        insertHistory(scanInfo,true);

        scanInfo = new IDCardInfo();
        scanInfo.setPeopleName("王五");
        scanInfo.setSex("男");
        scanInfo.setPeople("回");
        scanInfo.setBirthDay("19921022");
        scanInfo.setAddr("天津市南开区天拖大厦");
        scanInfo.setIDCard("120104199210221023");
        scanInfo.setDepartment("天津市公安局南开分局");
        scanInfo.setStrartDate("2011.02.02");
        scanInfo.setEndDate("2021.02.02");
        insertHistory(scanInfo,false);

        scanInfo = new IDCardInfo();
        scanInfo.setPeopleName("张华");
        scanInfo.setSex("女");
        scanInfo.setPeople("汉");
        scanInfo.setBirthDay("19961012");
        scanInfo.setAddr("天津市南开区天拖大厦");
        scanInfo.setIDCard("120104199610122023");
        scanInfo.setDepartment("天津市公安局南开分局");
        scanInfo.setStrartDate("2011.02.02");
        scanInfo.setEndDate("2021.02.02");
        insertHistory(scanInfo,false);

        scanInfo = new IDCardInfo();
        scanInfo.setPeopleName("王五");
        scanInfo.setSex("男");
        scanInfo.setPeople("回");
        scanInfo.setBirthDay("19921022");
        scanInfo.setAddr("天津市南开区天拖大厦");
        scanInfo.setIDCard("120104199210221023");
        scanInfo.setDepartment("天津市公安局南开分局");
        scanInfo.setStrartDate("2011.02.02");
        scanInfo.setEndDate("2021.02.02");
        insertHistory(scanInfo,false);


        scanInfo = new IDCardInfo();
        scanInfo.setPeopleName("王红");
        scanInfo.setSex("女");
        scanInfo.setPeople("回");
        scanInfo.setBirthDay("19821021");
        scanInfo.setAddr("天津市南开区天拖大厦");
        scanInfo.setIDCard("120104198210211023");
        scanInfo.setDepartment("天津市公安局南开分局");
        scanInfo.setStrartDate("2011.02.02");
        scanInfo.setEndDate("2021.02.02");
        insertHistory(scanInfo,true);

        scanInfo = new IDCardInfo();
        scanInfo.setPeopleName("李四");
        scanInfo.setSex("男");
        scanInfo.setPeople("汉");
        scanInfo.setBirthDay("19760302");
        scanInfo.setAddr("天津市南开区天拖大厦");
        scanInfo.setIDCard("120104197603021023");
        scanInfo.setDepartment("天津市公安局南开分局");
        scanInfo.setStrartDate("2011.02.02");
        scanInfo.setEndDate("2021.02.02");
        insertHistory(scanInfo,true);

        scanInfo = new IDCardInfo();
        scanInfo.setPeopleName("王五");
        scanInfo.setSex("男");
        scanInfo.setPeople("回");
        scanInfo.setBirthDay("19921022");
        scanInfo.setAddr("天津市南开区天拖大厦");
        scanInfo.setIDCard("120104199210221023");
        scanInfo.setDepartment("天津市公安局南开分局");
        scanInfo.setStrartDate("2011.02.02");
        scanInfo.setEndDate("2021.02.02");
        insertHistory(scanInfo,false);
    }

}
