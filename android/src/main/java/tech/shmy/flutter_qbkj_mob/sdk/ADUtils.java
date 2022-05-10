package tech.shmy.flutter_qbkj_mob.sdk;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.tb.mob.TbManager;
import com.tb.mob.bean.RewardPosition;
import com.tb.mob.config.TbInitConfig;
import com.tb.mob.config.TbInteractionConfig;
import com.tb.mob.config.TbRewardVideoConfig;
import com.tb.mob.config.TbSplashConfig;
import com.tb.mob.utils.RequestPermission;
import com.tb.mob.utils.ValueUtils;

import java.util.ArrayList;
import java.util.List;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import tech.shmy.flutter_qbkj_mob.R;

public class ADUtils {

    public static void load(Activity activity, MethodCall call, MethodChannel.Result result) {
        if (call.method.equals("init")) {
            ADUtils.init(activity, ValueUtils.getString(call.arguments), result);
        }
        if (call.method.equals("loadSplash")) {
            ADUtils.loadSplash(activity, ValueUtils.getString(call.arguments), result);
        }
        if (call.method.equals("loadInteraction")) {
            ADUtils.loadInteraction(activity, ValueUtils.getString(call.arguments), result);
        }
        if (call.method.equals("loadRewardVideo")) {
            List<String> arguments = ValueUtils.getValue(call.arguments, new ArrayList<>());
            ADUtils.loadRewardVideo(activity, arguments.get(0), arguments.get(1), arguments.get(2), arguments.get(3), result);
        }
    }

    public static void loadSplash(Activity activity, String codeId, MethodChannel.Result result) {
        Dialog dialog = new Dialog(activity, R.style.XlxVoiceTransparentAppTheme);
        dialog.setContentView(R.layout.dialog_fullscreen);
        FrameLayout mContainer = dialog.findViewById(R.id.mContainer);
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TbSplashConfig config = new TbSplashConfig.Builder()
                .codeId(codeId)
                .container(mContainer)
                .build();
        TbManager.loadSplash(config, activity, new TbManager.SplashLoadListener() {
            @Override
            public void onFail(String s) {
                dialog.dismiss();
                result.success(null);
            }

            @Override
            public void onDismiss() {
                dialog.dismiss();
                result.success(null);
            }


            @Override
            public void onExposure() {

            }

            @Override
            public void onClicked() {

            }
        });
    }

    public static void loadInteraction(Activity activity, String codeId, MethodChannel.Result result) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        TbInteractionConfig config = new TbInteractionConfig.Builder()
                .codeId(codeId)
                .viewWidth(width)
                .build();
        TbManager.loadInteraction(config, activity, new TbManager.InteractionLoadListener() {
            @Override
            public void onFail(String s) {
                result.success(null);

            }

            @Override
            public void onDismiss() {
                result.success(null);

            }

            @Override
            public void onClicked() {

            }

            @Override
            public void onExposure() {

            }

            @Override
            public void onVideoReady() {

            }

            @Override
            public void onVideoComplete() {

            }
        });
    }

    //VERTICAL||HORIZONTAL
    public static void loadRewardVideo(Activity activity, String codeId, String orientation, String userId, String callExtraData, MethodChannel.Result result) {
        TbManager.Orientation rvAdOrientation;
        if ("HORIZONTAL".equals(orientation)) {
            rvAdOrientation = TbManager.Orientation.VIDEO_HORIZONTAL;
        } else {
            rvAdOrientation = TbManager.Orientation.VIDEO_VERTICAL;
        }
        TbRewardVideoConfig config = new TbRewardVideoConfig.Builder()
                .playNow(true)
                .userId(userId)
                .callExtraData(callExtraData)
                .codeId(codeId)
                .orientation(rvAdOrientation)
                .build();
        TbManager.loadRewardVideo(config, activity, new TbManager.RewardVideoLoadListener() {
            @Override
            public void onFail(String s) {

            }

            @Override
            public void onClick() {

            }

            @Override
            public void onClose() {

            }

            @Override
            public void onExposure(String orderNo) {

            }

            @Override
            public void onRewardVideoCached(RewardPosition rewardPosition) {

            }

            @Override
            public void onRewardVerify() {
                //返回给flutter的参数
                result.success("1");
            }
        });
    }

    public static void init(Activity activity, String appId, MethodChannel.Result result) {
        TbInitConfig config = new TbInitConfig.Builder()
                .appId(appId)
                .build();
        TbManager.init(activity.getApplicationContext(), config, new TbManager.IsInitListener() {
            @Override
            public void onFail(String s) {
                result.success(false);
            }

            @Override
            public void onSuccess() {
                result.success(true);
            }
        });//SDK初始化
        RequestPermission.RequestPermissionIfNecessary(activity); //动态权限
    }
}
