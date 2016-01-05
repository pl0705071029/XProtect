package com.serry.xprotect.data;

import java.util.ArrayList;
import java.util.List;

import com.serry.xprotect.R;
import com.serry.xprotect.transaction.AppInfo;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter extends BaseAdapter {
	private List<AppInfo> list = new ArrayList<AppInfo>();
	private Boolean ti = true;

	public List<AppInfo> getList() {
		return list;
	}

	public void setList(List<AppInfo> list) {
		this.list = list;
	}

	private Context context;
	private LayoutInflater la;

	public Adapter(List<AppInfo> list, Context context) {
		this.list = list;
		this.context = context;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		ViewTitle viewTitle = null;
		la = LayoutInflater.from(context);

		// if
		// (!list.get(position).getApp_name().equals("正在运行用户进程")&&!list.get(position).getApp_name().equals("正在运行系统进程"))
		// {
		// if(convertView!=null&&ti==true){
		// holder = (ViewHolder) convertView.getTag(R.string.first);
		// Log.v("title", list.get(position).getApp_name());
		// }
		// else{
		//
		// convertView=la.inflate(R.layout.list_item, null);
		// holder = new ViewHolder();
		// holder.imgage=(ImageView) convertView.findViewById(R.id.list_image);
		// holder.text = (TextView) convertView.findViewById(R.id.list_text);
		// holder.gprs_rev=(TextView) convertView.findViewById(R.id.gprs_down);
		// holder.gprs_send=(TextView)
		// convertView.findViewById(R.id.gprs_upload);
		// holder.gprs_total=(TextView)
		// convertView.findViewById(R.id.gprs_total);
		// holder.delete_process=(ImageButton)convertView.findViewById(R.id.delete_process);
		// holder.process_name = (TextView)
		// convertView.findViewById(R.id.process_name);
		// convertView.setTag(R.string.first,holder);
		// Log.v("title", list.get(position).getApp_name()+"ss");
		// }
		// final AppInfo appinfo = (AppInfo)list.get(position);
		// //设置图标
		// holder.imgage.setImageDrawable(appinfo.getApp_icon());
		// //设置程序名
		// holder.text.setText(appinfo.getApp_name());
		// holder.gprs_rev.setText(appinfo.getApp_rev());
		// holder.gprs_send.setText(appinfo.getApp_sent());
		// holder.gprs_total.setText(appinfo.getApp_traffic());
		// holder.process_name.setText(appinfo.getApp_package());
		//
		//
		// }
		// else{
		// if(convertView!=null&&ti==true){
		// viewTitle=(ViewTitle)convertView.getTag(R.string.second);
		//
		// }
		// else{
		// convertView=la.inflate(R.layout.list_title, null);
		// viewTitle=new ViewTitle();
		// viewTitle.title=(TextView)convertView.findViewById(R.id.title);
		// Log.v("title", list.get(position).getApp_name());
		// convertView.setTag(R.string.second,viewTitle);
		// ti=false;
		// }
		// viewTitle.title.setText(list.get(position).getApp_name());
		// }
		//
		// return convertView;
		//
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag(R.string.first);
			Log.v("title", list.get(position).getApp_name());
		} else {

			convertView = la.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.imgage = (ImageView) convertView.findViewById(R.id.list_image);
			holder.text = (TextView) convertView.findViewById(R.id.list_text);
			holder.gprs_rev = (TextView) convertView.findViewById(R.id.gprs_down);
			holder.gprs_send = (TextView) convertView.findViewById(R.id.gprs_upload);
			holder.gprs_total = (TextView) convertView.findViewById(R.id.gprs_total);
			holder.delete_process = (ImageButton) convertView.findViewById(R.id.delete_process);
			holder.process_name = (TextView) convertView.findViewById(R.id.process_name);
			convertView.setTag(R.string.first, holder);
			Log.v("title", list.get(position).getApp_name() + "ss");
		}
		final AppInfo appinfo = (AppInfo) list.get(position);
		// 设置图标
		holder.imgage.setImageDrawable(appinfo.getApp_icon());
		// 设置程序名
		holder.text.setText(appinfo.getApp_name());
		holder.gprs_rev.setText(appinfo.getApp_rev());
		holder.gprs_send.setText(appinfo.getApp_sent());
		holder.gprs_total.setText(appinfo.getApp_traffic());
		holder.process_name.setText(appinfo.getApp_package());
		return convertView;

	}

	class ViewHolder {
		TextView text, process_name;
		ImageView imgage;
		TextView gprs_send, gprs_rev, gprs_total;
		ImageButton delete_process;
	}

	class ViewTitle {
		TextView title;

	}
}