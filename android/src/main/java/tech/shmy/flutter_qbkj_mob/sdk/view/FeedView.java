package tech.shmy.flutter_qbkj_mob.sdk.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.tb.mob.TbManager;
import com.tb.mob.bean.FeedPosition;
import com.tb.mob.config.TbFeedConfig;
import com.tb.mob.utils.DensityUtil;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

public class FeedView implements PlatformView, MethodChannel.MethodCallHandler {
    private final View myNativeView;
    private final MethodChannel methodChannel;

    FeedView(Activity activity, Context context, BinaryMessenger messenger, int id, Map<String, Object> params) {
        int width = 0;
        int height = 0;
        if (params.containsKey("width")) {
            width = (int) params.get("width");
        }
        if (params.containsKey("height")) {
            height = (int) params.get("height");
        }
        FrameLayout container = new FrameLayout(context);
        container.setLayoutParams(new ViewGroup.LayoutParams(DensityUtil.dp2px(context, width), DensityUtil.dp2px(context, height)));
        this.myNativeView = container;
        String codeId = (String) params.get("codeId");
        loadFeed(activity, container, codeId, width, height);
        methodChannel = new MethodChannel(messenger, "flutter_qbkj_mob/feedview_" + id);
        methodChannel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        // 在接口的回调方法中可以接收到来自Flutter的调用
    }

    @Override
    public View getView() {
        return myNativeView;
    }

    @Override
    public void dispose() {
        myNativeView.destroyDrawingCache();
    }

    //加载信息流
    //"1330513366195650582"
    private void loadFeed(Activity activity, final FrameLayout container, String codeId, float width, float height) {
        TbFeedConfig config = new TbFeedConfig.Builder()
                .codeId(codeId)//平台申请的代码位id
                .viewWidth((int) width)//期望模板view的width，默认值350（单位dp）
                .viewHigh((int) height)//期望模板view的height，默认值0（自适应，单位dp）
                .build();
        System.out.println("加载信息流:");
        TbManager.loadFeed(config, activity, new TbManager.FeedLoadListener() {
            @Override
            public void onFail(String s) {
                //加载失败
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoad(FeedPosition feedPosition) {
                feedPosition.showFeed(activity, container);
            }

            @Override
            public void onDismiss() {
                methodChannel.invokeMethod("dismissed", null);
                //关闭
            }

            @Override
            public void onVideoReady() {
                //视频准备就绪开始播放（非视频不回调）
            }

            @Override
            public void onVideoComplete() {
                //视频播放完成（非视频不回调）
            }
        });
    }
}
