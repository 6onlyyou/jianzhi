package fu.com.parttimejob.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import fu.com.parttimejob.R;
import fu.com.parttimejob.activity.WebActivity;


/**
 * Description:
 * Data：2018/3/31-13:51
 * Author: fushuaige
 */
public class JurisdictionDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;

    public JurisdictionDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public JurisdictionDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public JurisdictionDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }
    public JurisdictionDialog(Context context, int themeResId , OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }
    protected JurisdictionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public JurisdictionDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public JurisdictionDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public JurisdictionDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ju);
        setCanceledOnTouchOutside(false);

        initView();
    }

    private void initView() {
        contentTxt = findViewById(R.id.mine_content);
        titleTxt = findViewById(R.id.title);
        submitTxt = findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);
        contentTxt.setMovementMethod(ScrollingMovementMethod.getInstance());
        contentTxt.setText(content);
        contentTxt.setOnClickListener(this);

        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if (listener != null) {
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                break;
            case R.id.mine_content:
                    WebActivity.startSelf(getContext(), "隐私协议", "http://www.jjqhkj.com/appservice/user_agreement.html");
                break;

        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}