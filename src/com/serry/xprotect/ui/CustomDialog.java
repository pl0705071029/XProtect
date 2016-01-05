package com.serry.xprotect.ui;

import com.serry.xprotect.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CustomDialog extends Dialog {

	private Context context;
	private EditText over_day, over_traffic;
	private ImageView yes, no;

	public CustomDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;

	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.custom_dialog);
		setTitle("设置月结日与月流量");
		over_day = (EditText) findViewById(R.id.editText1);
		over_traffic = (EditText) findViewById(R.id.editText2);
		SharedPreferences preferences = context.getSharedPreferences("small_setting", Context.MODE_PRIVATE);
		final SharedPreferences.Editor editor = preferences.edit();
		over_day.setText(preferences.getInt("overday", 1) + "");
		over_traffic.setText(preferences.getInt("usertraffic", 30) + "");
		yes = (ImageView) findViewById(R.id.yes);
		no = (ImageView) findViewById(R.id.no);
		yes.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editor.putInt("overday", Integer.parseInt(over_day.getText().toString()));
				editor.putInt("usertraffic", Integer.parseInt(over_traffic.getText().toString()));
				editor.commit();
				dismiss();
				Toast.makeText(context, "修改个人月信息成功！", 1).show();

			}
		});
		no.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}

}