package com.serry.xprotect.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sing on 14-1-10. desc:
 */
public class AssetUtil {
	private Context context;

	public AssetUtil(Context context) {
		this.context = context;
	}

	/**
	 * 从asset目录下复制srcFileName到file
	 * 
	 * @param srcFileName
	 * @param file
	 * @param progressDialog
	 * @return
	 */
	public boolean copyFile(String srcFileName, File file, ProgressDialog progressDialog) {
		boolean bSuccess = false;

		try {
			AssetManager am = context.getAssets();
			InputStream is = am.open(srcFileName);
			int max = is.available();
			progressDialog.setMax(max);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			int progress = 0;
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				progress += len;
				progressDialog.setProgress(progress);
			}
			fos.flush();
			fos.close();
			bSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bSuccess;
	}

	/**
	 * 从资产目录拷贝文件
	 * 
	 * @param context
	 * @param filename
	 *            资产目录的文件的名称
	 * @param destfilename
	 *            目标文件的路径
	 * @return
	 */
	public static File copy1(Context context, String filename, String destfilename, ProgressDialog pd) {

		try {
			InputStream in = context.getAssets().open(filename);
			int max = in.available();
			if (pd != null) {
				pd.setMax(max);
			}

			File file = new File(destfilename);
			OutputStream out = new FileOutputStream(file);
			byte[] byt = new byte[1024];
			int len = 0;
			int total = 0;
			while ((len = in.read(byt)) != -1) {
				out.write(byt, 0, len);
				total += len;
				if (pd != null) {
					pd.setProgress(total);
				}
			}
			out.flush();
			out.close();
			in.close();

			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
