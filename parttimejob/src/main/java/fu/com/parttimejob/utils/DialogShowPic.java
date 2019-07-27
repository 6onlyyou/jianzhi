package fu.com.parttimejob.utils;



import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;


import cn.jzvd.JZVideoPlayerStandard;
import fu.com.parttimejob.R;


public class DialogShowPic implements OnClickListener,OnKeyListener {
	private Activity m_pContx = null;
	private DialogShowPicP m_pDialogMyIf = null;
	private Dialog m_pDialog = null;
	//private ViewDialogType0 m_pViewDialogType0 = null;
	private int m_nStyle = R.style.myDialogTheme1;
	private double m_dPercentW = (float) 1.00;	// 宽度高度设置为屏幕的百分比
	private double m_dPercentH = (float) 1.00;	// 宽度高度设置为屏幕的百分比

	public DialogShowPic(Context context) {
		m_pContx = (Activity)context;
	}

	public void InitDialog(DialogShowPicP pDialogMyIf) {
		m_pDialogMyIf = pDialogMyIf;
		if (m_pContx != null) {
			m_pDialog = new Dialog(m_pContx, m_nStyle);
			//m_pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);	// 设置没有 title
			m_pDialogMyIf.SetDialogView(m_pDialog,this);
			Window pWindow = m_pDialog.getWindow();
			WindowManager.LayoutParams params = pWindow.getAttributes();	// 获取对话框当前的参数值
			WindowManager pWindowManager = m_pContx.getWindowManager();
			Display pDisplay = pWindowManager.getDefaultDisplay();	// 获取屏幕宽、高用
			params.width = (int) (pDisplay.getWidth() * m_dPercentW);	// 宽度设置为屏幕的0.65
			if (m_dPercentH != 0) {
				params.height = (int) (pDisplay.getHeight() * m_dPercentH);		// 高度设置为屏幕的0.65
			}
			pWindow.setAttributes(params);
			m_pDialog.setOnKeyListener(this);
		}
	}

	public void SetStyle(int nStyle) {
		m_nStyle = nStyle;
	}

	public void SetWidthHeight(double dPercentW,double dPercentH) {
		m_dPercentW = dPercentW;
		m_dPercentH = dPercentH;
	}

	public void Show() {
		if (m_pDialog != null && !m_pDialog.isShowing()) {
			m_pDialog.show();
		}
	}

	public void Destroy() {
		if (m_pDialog != null) {
			JZVideoPlayerStandard.releaseAllVideos();
			m_pDialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		m_pDialogMyIf.SetOnClickListener(v,this);
	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		m_pDialogMyIf.SetOnKeyListener(keyCode, event);
		return false;
	}
}
