package cn.chenzd.duerosdemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.orhanobut.logger.Logger;

import cn.chenzd.duerosdemo.androidsystemimpl.PlatformFactoryImpl;
import cn.chenzd.duerosdemo.androidsystemimpl.webview.BaseWebView;
import cn.chenzd.duerosdemo.devicemodule.screen.ScreenDeviceModule;
import cn.chenzd.duerosdemo.devicemodule.screen.message.RenderVoiceInputTextPayload;
import cn.chenzd.duerosdemo.devicemodule.voiceinput.VoiceInputDeviceModule;
import cn.chenzd.duerosdemo.framework.DcsFramework;
import cn.chenzd.duerosdemo.framework.DeviceModuleFactory;
import cn.chenzd.duerosdemo.framework.IResponseListener;
import cn.chenzd.duerosdemo.systeminterface.IMediaPlayer;
import cn.chenzd.duerosdemo.systeminterface.IPlatformFactory;
import cn.chenzd.duerosdemo.systeminterface.IWakeUp;
import cn.chenzd.duerosdemo.util.LogUtil;
import cn.chenzd.duerosdemo.wakeup.WakeUp;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "DcsDemoActivity";
    private Button voiceButton;
    private DcsFramework dcsFramework;
    private DeviceModuleFactory deviceModuleFactory;
    private IPlatformFactory platformFactory;
    private TextView tvRequest;
    private LinearLayout mTopLinearLayout;
    // 唤醒
    private WakeUp wakeUp;
    private boolean isPause = true;
    private long startTimeStopListen;
    private boolean isStopListenReceiving;
    private BaseWebView webView;
    private String mHtmlUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFramework();
    }

    private void initView() {
        voiceButton = findViewById(R.id.voiceBtn);
        tvRequest = findViewById(R.id.tv_request);
        webView = new BaseWebView(MainActivity.this.getApplicationContext());
        mTopLinearLayout =  findViewById(R.id.topLinearLayout);
        webView.setWebViewClientListen(new BaseWebView.WebViewClientListener() {
            @Override
            public BaseWebView.LoadingWebStatus shouldOverrideUrlLoading(WebView view, String url) {
                // 拦截处理不让其点击
                return BaseWebView.LoadingWebStatus.STATUS_TRUE;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
               Logger.i("url="+url);
                if (!url.equals(mHtmlUrl) && !"about:blank".equals(mHtmlUrl)) {
                    platformFactory.getWebView().linkClicked(url);
                }

                mHtmlUrl = url;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }
        });
        mTopLinearLayout.addView(webView);
    }

    public void onClick(View view) {
        if (isStopListenReceiving) {
            platformFactory.getVoiceInput().stopRecord();
            isStopListenReceiving = false;
            return;
        }
        isStopListenReceiving = true;
        startTimeStopListen = System.currentTimeMillis();
        platformFactory.getVoiceInput().startRecord();
        doUserActivity();
    }

    private void initFramework() {
        platformFactory = new PlatformFactoryImpl(this);
        platformFactory.setWebView(webView);
        dcsFramework = new DcsFramework(platformFactory);
        deviceModuleFactory = dcsFramework.getDeviceModuleFactory();

        deviceModuleFactory.createVoiceOutputDeviceModule();
        deviceModuleFactory.createVoiceInputDeviceModule();
        deviceModuleFactory.getVoiceInputDeviceModule().addVoiceInputListener(
                new VoiceInputDeviceModule.IVoiceInputListener() {
                    @Override
                    public void onStartRecord() {
                        LogUtil.d(TAG, "onStartRecord");
                        startRecording();
                    }

                    @Override
                    public void onFinishRecord() {
                        LogUtil.d(TAG, "onFinishRecord");
                        stopRecording();
                    }

                    @Override
                    public void onSucceed(int statusCode) {
                        LogUtil.d(TAG, "onSucceed-statusCode:" + statusCode);
                        if (statusCode != 200) {
                            stopRecording();
                            Toast.makeText(MainActivity.this,
                                    getResources().getString(R.string.voice_err_msg),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        LogUtil.d(TAG, "onFailed-errorMessage:" + errorMessage);
                        stopRecording();
                        Toast.makeText(MainActivity.this,
                                getResources().getString(R.string.voice_err_msg),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        deviceModuleFactory.createAlertsDeviceModule();

        deviceModuleFactory.createAudioPlayerDeviceModule();
        deviceModuleFactory.getAudioPlayerDeviceModule().addAudioPlayListener(
                new IMediaPlayer.SimpleMediaPlayerListener() {
                    @Override
                    public void onPaused() {
                        super.onPaused();
                        isPause = true;
                    }

                    @Override
                    public void onPlaying() {
                        super.onPlaying();
                        isPause = false;
                    }

                    @Override
                    public void onCompletion() {
                        super.onCompletion();
                        isPause = false;
                    }

                    @Override
                    public void onStopped() {
                        super.onStopped();
                        isPause = true;
                    }
                });

        deviceModuleFactory.createSystemDeviceModule();
        deviceModuleFactory.createSpeakControllerDeviceModule();
        deviceModuleFactory.createPlaybackControllerDeviceModule();
        deviceModuleFactory.createScreenDeviceModule();
        deviceModuleFactory.getScreenDeviceModule()
                .addRenderVoiceInputTextListener(new ScreenDeviceModule.IRenderVoiceInputTextListener() {
                    @Override
                    public void onRenderVoiceInputText(RenderVoiceInputTextPayload payload) {
                        tvRequest.setText(payload.text);
                    }

                });
        // init唤醒
        wakeUp = new WakeUp(platformFactory.getWakeUp(),
                platformFactory.getAudioRecord());
        wakeUp.addWakeUpListener(wakeUpListener);
        // 开始录音，监听是否说了唤醒词
        wakeUp.startWakeUp();
    }

    private IWakeUp.IWakeUpListener wakeUpListener = new IWakeUp.IWakeUpListener() {
        @Override
        public void onWakeUpSucceed() {
            Toast.makeText(MainActivity.this,
                    getResources().getString(R.string.wakeup_succeed),
                    Toast.LENGTH_SHORT)
                    .show();
            voiceButton.performClick();
        }
    };

    private void doUserActivity() {
        deviceModuleFactory.getSystemProvider().userActivity();
    }

    private void stopRecording() {
        wakeUp.startWakeUp();
        isStopListenReceiving = false;
        voiceButton.setText(getResources().getString(R.string.stop_record));
        long t = System.currentTimeMillis() - startTimeStopListen;
       // tvRequest.append(getResources().getString(R.string.time_record, t));
    }

    private void startRecording() {
        wakeUp.stopWakeUp();
        isStopListenReceiving = true;
        deviceModuleFactory.getSystemProvider().userActivity();
        voiceButton.setText(getResources().getString(R.string.start_record));
        tvRequest.setText("");
    }


    private IResponseListener playPauseResponseListener = new IResponseListener() {
        @Override
        public void onSucceed(int statusCode) {
            if (statusCode == 204) {
                Toast.makeText(MainActivity.this,
                        getResources().getString(R.string.no_directive),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onFailed(String errorMessage) {
            Toast.makeText(MainActivity.this,
                    getResources().getString(R.string.request_error),
                    Toast.LENGTH_SHORT)
                    .show();
        }
    };

//    private IResponseListener nextPreResponseListener = new IResponseListener() {
//        @Override
//        public void onSucceed(int statusCode) {
//            if (statusCode == 204) {
//                        getResources().getString(R.string.no_audio),
//                        Toast.LENGTH_SHORT)//                Toast.makeText(MainActivity.this,

//                        .show();
//            }
//        }
//
//        @Override
//        public void onFailed(String errorMessage) {
//            Toast.makeText(MainActivity.this,
//                    getResources().getString(R.string.request_error),
//                    Toast.LENGTH_SHORT)
//                    .show();
//        }
//    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 先remove listener  停止唤醒,释放资源
        wakeUp.removeWakeUpListener(wakeUpListener);
        wakeUp.stopWakeUp();
        wakeUp.releaseWakeUp();

        if (dcsFramework != null) {
            dcsFramework.release();
        }
        webView.setWebViewClientListen(null);
        mTopLinearLayout.removeView(webView);
        webView.removeAllViews();
        webView.destroy();
    }
}
