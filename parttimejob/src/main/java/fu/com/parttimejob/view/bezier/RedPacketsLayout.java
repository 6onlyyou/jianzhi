package fu.com.parttimejob.view.bezier;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.heixiu.errand.net.RetrofitFactory;
import com.lljjcoder.citylist.Toast.ToastUtils;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import fu.com.parttimejob.R;
import fu.com.parttimejob.activity.AdInfoActivity;
import fu.com.parttimejob.activity.JobInfoActivity;
import fu.com.parttimejob.bean.AdInfoBean;
import fu.com.parttimejob.bean.SameCityBean;
import fu.com.parttimejob.dialog.JobDialog;
import fu.com.parttimejob.dialog.RadDialog;
import fu.com.parttimejob.retrofitNet.RxUtils;
import fu.com.parttimejob.utils.SPUtil;
import io.reactivex.functions.Consumer;

/**
 * 自定义布局通过 属性动画(贝塞尔曲线)实现红包
 * <p>
 * create by yao.cui at 2016/11/28
 */
public class RedPacketsLayout extends RelativeLayout {
    private OnCloseListener listener;
    private int dHeight;//红包图片高度
    private int dWidth;//红包图片宽度
    private int mHeight;//view 高度
    private int mWidth;//view宽度
    private ProgressDialog dialogPro = new ProgressDialog(getContext());
    private LayoutParams mLp;//红包布局参数

    private Drawable[] mDrawables;//红包图片

    private Random mRandom = new Random();

    private Thread mRainThread;
    private LinkedBlockingQueue<View> mCacheQueue = new LinkedBlockingQueue();

    private boolean isStop;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                addPacket();
            }
        }
    };


    public interface OnCloseListener {
        void onClick(View view);
    }

    public RedPacketsLayout(Context context) {
        super(context);
        init();
    }

    public RedPacketsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RedPacketsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDrawables = new Drawable[2];
        mDrawables[0] = getResources().getDrawable(R.mipmap.red_envelope);
        mDrawables[1] = getResources().getDrawable(R.mipmap.red_envelope);

        dHeight = mDrawables[1].getIntrinsicHeight();
        dWidth = mDrawables[1].getIntrinsicWidth();

        mLp = new LayoutParams(dWidth, dHeight);
        mLp.addRule(ALIGN_PARENT_TOP, TRUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    private void addPacket() {

        dialogPro = new ProgressDialog(getContext());
        dialogPro.setCanceledOnTouchOutside(false);
        dialogPro.setMessage("小二加载中，大人请稍后~");
//        dialogPro.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//                if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
////                    val rxBusEntity = RxBusEntity()
////                    rxBusEntity.msg = "77"
////                    RxBus.getDefault().post(rxBusEntity)
//
////                    finish()
//                    return true;
//                } else {
//                    return false; //默认返回 false
//                }
//            }
//        });
        ImageView imageView = null;
        if (mCacheQueue.isEmpty()) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(mLp);

            imageView.setImageDrawable(mDrawables[mRandom.nextInt(mDrawables.length)]);
            imageView.setRotation(mRandom.nextInt(180));

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogPro.show();
                    removeView(view);
                    Random rdm = new Random();

                    if (rdm.nextBoolean()) {
                        RxUtils.wrapRestCall(RetrofitFactory.INSTANCE.getRetrofit().randomGetOne(SPUtil.getString(getContext(), "thirdAccount", ""))).subscribe(new Consumer<SameCityBean>() {
                            @Override
                            public void accept(final SameCityBean sameCityBean) throws Exception {
                                new JobDialog(getContext(), R.style.dialog, sameCityBean.getCompanyName(), new JobDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        Intent intent = new Intent(getContext(), JobInfoActivity.class);
                                        intent.putExtra("id", sameCityBean.getId());
                                        getContext().startActivity(intent);
                                        dialog.dismiss();
                                    }

                                })
                                        .setTitle(sameCityBean.getLabel()).show();
                                dialogPro.dismiss();
                            }

                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dialogPro.dismiss();
                                RxUtils.wrapRestCall(RetrofitFactory.INSTANCE.getRetrofit().randomGetOneAdvertisement(SPUtil.getString(getContext(), "thirdAccount", ""))).subscribe(new Consumer<AdInfoBean>() {
                                    @Override
                                    public void accept(final AdInfoBean adInfoBean) throws Exception {
                                        new RadDialog(getContext(), R.style.dialog, "恭喜获得" + adInfoBean.getNumberOfVirtualCoins() / adInfoBean.getRedEnvelopeNumber() + "金币", new RadDialog.OnCloseListener() {
                                            @Override
                                            public void onClick(Dialog dialog, boolean confirm) {
                                                Intent intent = new Intent(getContext(), AdInfoActivity.class);
                                                intent.putExtra("id", adInfoBean.getId());
                                                getContext().startActivity(intent);
                                                dialog.dismiss();
                                            }
                                        })
                                                .setTitle("").show();
                                        dialogPro.dismiss();
                                    }

                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        dialogPro.dismiss();
                                        ToastUtils.showLongToast(getContext(), throwable.getMessage().toString());
                                    }
                                });
//                                ToastUtils.showLongToast(getContext(), throwable.getMessage().toString());
                            }
                        });
                    } else {

                        RxUtils.wrapRestCall(RetrofitFactory.INSTANCE.getRetrofit().randomGetOneAdvertisement(SPUtil.getString(getContext(), "thirdAccount", ""))).subscribe(new Consumer<AdInfoBean>() {
                            @Override
                            public void accept(final AdInfoBean adInfoBean) throws Exception {
                                new RadDialog(getContext(), R.style.dialog, "恭喜获得" + adInfoBean.getNumberOfVirtualCoins() / adInfoBean.getRedEnvelopeNumber() + "金币", new RadDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        Intent intent = new Intent(getContext(), AdInfoActivity.class);
                                        intent.putExtra("id", adInfoBean.getId());
                                        getContext().startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                                        .setTitle("").show();
                                dialogPro.dismiss();
                            }

                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dialogPro.dismiss();
                                ToastUtils.showLongToast(getContext(), throwable.getMessage().toString());
                            }
                        });
                    }


                }
            });
        } else {
            imageView = (ImageView) mCacheQueue.poll();
        }

        addView(imageView);
        ValueAnimator set = genBezierAnimator(imageView);
        set.start();

    }

    public void startRain() {
        mRainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mHandler.sendEmptyMessage(1);
                }
            }
        });
        mRainThread.start();
    }

    public void stopRain() {
        isStop = true;
    }

    private ValueAnimator genBezierAnimator(View target) {
        BezierEvaluator evaluator = new BezierEvaluator(getPoint(1), getPoint(2));//传入中间两个点
        ValueAnimator valueAnimator = ValueAnimator.ofObject(evaluator, new PointF(mRandom.nextInt(mWidth - dWidth), -dHeight),
                new PointF(mRandom.nextInt(mWidth - dWidth), mHeight));//传入开始位置结束位置
        valueAnimator.addUpdateListener(new BezierListener(target));
        valueAnimator.addListener(new AnimatorEndListener(target));
        valueAnimator.setTarget(target);
        valueAnimator.setDuration(4000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        return valueAnimator;
    }

    private PointF getPoint(int scale) {
        PointF pointF = new PointF();
        pointF.x = mRandom.nextInt(mWidth - 50);

        pointF.y = mRandom.nextInt((mHeight - 50) * scale / 2);
        return pointF;
    }

    private class BezierListener implements ValueAnimator.AnimatorUpdateListener {
        private View mTarget;

        public BezierListener(View target) {
            this.mTarget = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            PointF pointF = (PointF) valueAnimator.getAnimatedValue();
            mTarget.setX(pointF.x);
            mTarget.setY(pointF.y);
        }
    }

    private class AnimatorEndListener extends AnimatorListenerAdapter {
        private View mTarget;

        public AnimatorEndListener(View view) {
            mTarget = view;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            removeView(mTarget);

            mCacheQueue.add(mTarget);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isStop = true;
    }
}
