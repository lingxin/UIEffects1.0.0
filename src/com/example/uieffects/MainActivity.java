package com.example.uieffects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	private List<Map<String, Object>> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		// MySlidingDrawer drawer = (MySlidingDrawer)findViewById(R.id.drawer);
		ListView listViewDown = (ListView) findViewById(R.id.listViewDown);
		PrepareData();
		SimpleAdapter adapterDown = new SimpleAdapter(this, data, R.layout.list_item, new String[] { "����", "�Ա�" }, new int[] { R.id.item, R.id.item2 });
		listViewDown.setAdapter(adapterDown);
		ListView listViewUp = (ListView) findViewById(R.id.listViewUp);
		PrepareData();
		SimpleAdapter adapterUp = new SimpleAdapter(this, data, R.layout.list_item, new String[] { "����", "�Ա�" }, new int[] { R.id.item, R.id.item2 });
		listViewUp.setAdapter(adapterUp);
	}

	private void PrepareData() {
		data = new ArrayList<Map<String, Object>>();
		Map<String, Object> item;
		item = new HashMap<String, Object>();
		item.put("����", "����С����");
		item.put("�Ա�", "��");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "����ͬѧ");
		item.put("�Ա�", "��");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "С��ʦ��");
		item.put("�Ա�", "Ů");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "С��ʦ��");
		item.put("�Ա�", "Ů");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "С��ʦ��");
		item.put("�Ա�", "Ů");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "С��ʦ��");
		item.put("�Ա�", "Ů");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "С��ʦ��");
		item.put("�Ա�", "Ů");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "С��ʦ��");
		item.put("�Ա�", "Ů");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "С��ʦ��");
		item.put("�Ա�", "Ů");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "С��ʦ��");
		item.put("�Ա�", "Ů");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "С��ʦ��");
		item.put("�Ա�", "Ů");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "С��ʦ��");
		item.put("�Ա�", "Ů");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "С��ʦ��");
		item.put("�Ա�", "Ů");
		data.add(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
