package com.rv.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    String exec1 = null;
	String data = "";
	Process p=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		Button button = (Button)findViewById(R.id.button);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText editText = (EditText)findViewById(R.id.edit_text);
				exec1 = editText.getText().toString();
				execommand();
				editText.setText("");
			}
		});
		

	}
    public void execommand(){
    	TextView textView  = (TextView)findViewById(R.id.text_view);
    	textView.setTextColor(Color.BLACK);
    	message();
    	
    	
        if(exec1.length()>0){        	
		try {
    		p = Runtime.getRuntime().exec(exec1);
		} catch (IOException e) {
			data += "输入  "+exec1+"  错误,请重新输入命令！";
			textView.setTextColor(Color.RED);
			 textView.setText(data);
			Toast.makeText(this, "输入  "+exec1+"  错误,请重新输入命令！", Toast.LENGTH_SHORT).show();
			return ;
		} catch (Exception e) {
			// TODO: handle exception
			data += "输入全空格错误！！！";
			textView.setTextColor(Color.RED);
			 textView.setText(data);
			Toast.makeText(this, "输入全空格错误！！！", Toast.LENGTH_SHORT).show();
			return;
		}  
		
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));  
		BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));       
		String error = null;  
		try {
			while ((error = ie.readLine()) != null       
					&& !error.equals("null")) {     
				data += error + "\n";             
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		String line = null;     
		try {
			while ((line = in.readLine()) != null &&  !line.equals("null")) {         
				data += line + "\n";            
				}
		    } catch (IOException e) {
			// TODO Auto-generated catch block
		    	textView.setText("读取失败，请重试！");
		    	return;
		}       
		
        textView.setText(data);
		
        }else{
        	textView.setText("please enter command:");
        	Toast.makeText(this, "命令不能为空", Toast.LENGTH_SHORT).show();
        	return;
        }
    }
	
	public void message(){
			data = "你输入的命令是："+exec1+"\n"+"\n";
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_settings:
//			Intent intent = new Intent(MainActivity.this,History.class);
			Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
            
		}
		return super.onOptionsItemSelected(item);
	}
	
      @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(keyCode==KeyEvent.KEYCODE_BACK){
    		AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
    		dialog.setMessage("确定要退出吗?会清空内容！！！");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					MainActivity.this.finish();
				}
			});
             dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
             dialog.show();
    		
    	}
    	return true;
    }
}
