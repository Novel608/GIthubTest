package com.haha.telephonecontacts;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactsManager {

	Context context;
	ContentResolver contentResolver;

	String phone = "vnd.android.cursor.item/phone_v2";
	String name = "vnd.android.cursor.item/name";
	String email = "vnd.android.cursor.item/email_v2";
	String address = "vnd.android.cursor.item/postal-address_v2";
	String photo = "vnd.android.cursor.item/photo";

	public ContactsManager(Context context) {
		this.context = context;
		contentResolver = context.getContentResolver();
	}

	/**
	 * ��ѯͨѶ¼��Ϣ
	 */
	public void selectContact() {

		/**
		 * 1��Uri����
		 * 
		 * 2��������ʾ����
		 * 
		 * 3����ѯ������
		 * 
		 * 4����ѯ�ľ����ֵ
		 * 
		 * 5������
		 */
		Uri uri1 = ContactsContract.Contacts.CONTENT_URI;

		Uri uri = Uri.parse("content://com.android.contacts/data");

		Cursor cursor = contentResolver.query(uri, null, null, null, null);
		while (cursor.moveToNext()) {
			int _id = cursor.getInt(cursor.getColumnIndex("_id"));
			System.out.println("_id   :    " + _id);

			String phoneNum = cursor.getString(cursor.getColumnIndex("data4"));
			System.out.println("phoneNum   :   " + phoneNum);

			String phoneMessage = cursor.getString(cursor
					.getColumnIndex("data2"));
			System.out.println("phoneMessage   :   " + phoneMessage);

			String mimeType = cursor.getString(cursor
					.getColumnIndex("mimetype")) + "";
			// System.out.println("mimetype    :   "+mimeType);
			if (mimeType.equals("vnd.android.cursor.item/name")) {
				System.out.println("����    :   " + mimeType);
			}
			if (mimeType.equals("vnd.android.cursor.item/phone_v2")) {
				System.out.println("�绰����    :   " + mimeType);
			}
			System.out
					.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
	}

	/**
	 * ����idɾ������ raw_contacts->����->_id->data->raw_contact_id
	 */
	public void deleteContactById() {
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Cursor cursor = contentResolver.query(uri, new String[] { "_id" },
				"display_name=?", new String[] { "���ſͷ�" }, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			System.out.println("id----->" + id);
			contentResolver.delete(uri, "_id=?", new String[] { id + "" });
			uri = Uri.parse("content://com.android.contacts/data");
			contentResolver.delete(uri, "raw_contact_id=?", new String[] { id
					+ "" });
		}
	}

	/**
	 * ������ϵ������
	 * raw_contacts->ContentValues->values->insert->ContnetUris->clear->data
	 * ->uri->values->insert
	 */
	public void insertDta() {
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentValues values = new ContentValues();
		values.put("_id", 658);
		values.put("display_name", "���ſͷ�");
		Uri u = contentResolver.insert(uri, values);
		int raw_contact_id = (int) ContentUris.parseId(u);
		System.out.println(raw_contact_id);
		uri = Uri.parse("content://com.android.contacts/data");
		values.clear();
		values.put("raw_contact_id", raw_contact_id);
		values.put("mimetype", name);
		values.put("data1", "���ſͷ�");
		contentResolver.insert(uri, values);

		values.clear();
		values.put("raw_contact_id", raw_contact_id);
		values.put("mimetype", phone);
		values.put("data1", "10000");
		contentResolver.insert(uri, values);
	}

	public void updateData() {
		Uri uri = Uri.parse("content://com.android.contacts/data");
		ContentValues values = new ContentValues();
		values.put("data1", "�й�����");
		contentResolver.update(uri, values, "mimetype=? and raw_contact_id=?",
				new String[] { name, 658 + "" });

	}

}
