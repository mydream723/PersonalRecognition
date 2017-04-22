package com.mydream.project.personalrecognition.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.hs.cvr_100p550im.sdk.CVR_Utility;
import com.hs.cvr_100p550im.sdk.HsSerialPortSDK;
import com.hs.cvr_100p550im.sdk.IDCardInfo;

/**
 * 身份证识别
 * Created by MX on 2017/4/22.
 */

public class RecognitionUtils {
    private static final String TAG = "RecognitionUtils";
    /**
     * 初始化开始
     */
    private static final int FLAG_INITIAL_START = 0x1001;
    /**
     * 初始化成功
     */
    private static final int FLAG_INITIAL_COMPLETE = 0x1002;
    /**
     * 初始化失败
     */
    private static final int FLAG_INITIAL_ERROR = 0x1003;
    /**
     * 识别开始
     */
    private static final int FLAG_RECOGNITION_START = 0x1004;
    /**
     * 识别成功
     */
    private static final int FLAG_RECOGNITION_ERROR = 0x1005;
    /**
     * 识别失败
     */
    private static final int FLAG_RECOGNITION_COMPLETE = 0x1006;
    /**
     * 解析图像
     */
    private static final int FLAG_RECOGNITION_ANALYSISIAMGE = 0x1007;

    private static RecognitionUtils mRecognitionUtils;
    /**
     * 设备管理工具
     */
    private CVR_Utility cvrHelp;
    /**
     * 识别sdk
     */
    private HsSerialPortSDK coreSDK;
    /**
     * 是否正在识别身份证
     */
    private boolean isRecognitingID = false;
    /**
     * 识别初始化监听
     */
    private RecognitionInitialCallback mInitialListener;
    /**
     * 识别身份证监听
     */
    private RecognitionIDCallback mRecognitionIDListener;
    private RecognitionUtils() {
        cvrHelp = new CVR_Utility();
    }

    /**
     * 获得识别实例
     * @return
     */
    public static RecognitionUtils getInstance(){
        Log.d(TAG, "getInstance");
        if(null == mRecognitionUtils)
            mRecognitionUtils = new RecognitionUtils();
        return mRecognitionUtils;
    }

    /**
     * 识别系统初始化回调
     */
    public interface RecognitionInitialCallback{
        /**
         * 初始化开始
         */
        void initialStart();
        /**
         * 初始化失败
         */
        void initialError();

        /**
         * 初始化完成
         */
        void initialComplete();
    }

    /**
     * 身份证识别回调
     */
    public interface RecognitionIDCallback {
        /**
         * 识别开始
         */
        void recognitionStart();

        /**
         * 识别成功
         * @param infoWithoutImage 身份证信息（除了相片信息）
         */
        void recoginitionComplete(IDCardInfo infoWithoutImage);

        /**
         * 图像解析成功
         * @param info 身份证信息
         */
        void analysisImageComplete(IDCardInfo info);

        /**
         * 识别失败
         */
        void recognitionError();

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FLAG_INITIAL_START:
                    if(null != mInitialListener)
                        mInitialListener.initialStart();
                    break;
                case FLAG_INITIAL_COMPLETE:
                    if(null != mInitialListener)
                        mInitialListener.initialComplete();
                    break;
                case FLAG_INITIAL_ERROR:
                    if(null != mInitialListener)
                        mInitialListener.initialError();
                    break;
                case FLAG_RECOGNITION_START:
                    if(null != mRecognitionIDListener )
                        mRecognitionIDListener.recognitionStart();
                    isRecognitingID = true;
                    break;
                case FLAG_RECOGNITION_COMPLETE:
                    if(null != mRecognitionIDListener)
                        mRecognitionIDListener.recoginitionComplete((IDCardInfo)msg.obj);
                    isRecognitingID = false;
                    break;
                case FLAG_RECOGNITION_ERROR:
                    if(null != mRecognitionIDListener)
                        mRecognitionIDListener.recognitionError();
                    isRecognitingID = false;
                    break;
                case FLAG_RECOGNITION_ANALYSISIAMGE:
                    if(null != mRecognitionIDListener)
                        mRecognitionIDListener.analysisImageComplete( (null != msg.obj ? (IDCardInfo)msg.obj : null));
                    isRecognitingID = false;
                    break;
            }
        }
    };

    /**
     * 打开设备
     */
    public void openDevice(final Context context){

        new Thread() {
            @Override
            public void run() {
                super.run();
                cvrHelp.IDCardPonwer();
                if (coreSDK == null) {
                    try {
                        coreSDK = new HsSerialPortSDK(context);
                        mHandler.sendEmptyMessage(FLAG_INITIAL_START);

                    } catch (Exception e) {
                        mHandler.sendEmptyMessage(FLAG_INITIAL_ERROR);
                        return;
                    } finally {
                        SystemClock.sleep(1000);
                        int ret = coreSDK.init("/dev/ttyMT1", 115200, 0);
                        if (ret == 0) {
                            SystemClock.sleep(1500);
                            mHandler.sendEmptyMessage(FLAG_INITIAL_COMPLETE);
                        } else {
                            mHandler.sendEmptyMessage(FLAG_INITIAL_ERROR);
                        }
                    }
                } else {
                    mHandler.sendEmptyMessage(FLAG_INITIAL_COMPLETE);
                    return;
                }

            }
        }.start();
    }

    /**
     * 识别模块初始化回调函数
     * @param callback
     */
    public void setRecognitionInitialCallback(RecognitionInitialCallback callback){
        this.mInitialListener =  callback;
    }

    /**
     * 识别身份证模块回调函数
     * @param callback
     */
    public void setRecognitionIDCallback(RecognitionIDCallback callback){
        this.mRecognitionIDListener = callback;
    }

    /**
     * 识别身份证
     */
    public void toRecognitionID(){
        cvrHelp.USBPonOff();
        RecognitionIDTask task = new RecognitionIDTask();
        task.execute();
    }

    /**
     * 身份证识别
     */
    public class RecognitionIDTask extends AsyncTask<Void, Integer, Void> {
        /**
         * 开始识别时间
         */
        private long startTime;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mHandler.sendEmptyMessage(FLAG_RECOGNITION_START);
        }
        @Override
        protected Void doInBackground(Void... params) {
            startTime = System.currentTimeMillis();
            int i = 8;
            while (i > 0) {
                i--;
                if (coreSDK == null) {
                    break;
                }
                if (coreSDK.Authenticate(150) == 0) {
                    IDCardInfo ic = new IDCardInfo();
                    if (coreSDK.Read_Card(ic, 2300) == 0) {
                        long endTime = System.currentTimeMillis() - startTime;
                        i = 0;
                        mHandler.sendMessage(mHandler.obtainMessage(FLAG_RECOGNITION_COMPLETE, ic));
                        int ret = coreSDK.Unpack(ic.getwltdata());
                        if (ret == 0) {
                            mHandler.sendMessage(mHandler.obtainMessage(FLAG_RECOGNITION_ANALYSISIAMGE, ic));
                        } else {
                            mHandler.sendMessage(mHandler.obtainMessage(FLAG_RECOGNITION_ANALYSISIAMGE, null));
                        }
                    }
                } else {
                    if (i == 0) {
                        mHandler.sendEmptyMessage(FLAG_RECOGNITION_ERROR);
                        break;
                    }
                    SystemClock.sleep(200);
                    continue;
                }
            }
            cvrHelp.IDCardPonOff();
            cvrHelp.IDCardPonwer();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

    }
}
