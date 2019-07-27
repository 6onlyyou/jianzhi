package fu.com.parttimejob.utils;

import android.app.Dialog;
import android.view.KeyEvent;
import android.view.View;

public interface DialogShowPicP {
	public void SetDialogView(Dialog pDialog, DialogShowPic pDialogMy);
	public void SetOnClickListener(View v, DialogShowPic pDialogMy);
	public void SetOnKeyListener(int keyCode, KeyEvent event);
}