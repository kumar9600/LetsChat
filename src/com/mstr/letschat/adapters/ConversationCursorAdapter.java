package com.mstr.letschat.adapters;

import java.text.DateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.mstr.letschat.R;
import com.mstr.letschat.databases.ChatContract.ConversationTable;

public class ConversationCursorAdapter extends ResourceCursorAdapter {
	private DateFormat dateFormat;
	
	public ConversationCursorAdapter(Context context, Cursor c, int flags) {
		super(context, R.layout.conversation_list_item, c, flags);
		
		dateFormat = DateFormat.getDateInstance();
	}
	
	@Override
	public void bindView(View view, Context context, final Cursor cursor) {
		ViewHolder viewHolder = (ViewHolder)view.getTag();
		
		viewHolder.nameText.setText(cursor.getString(cursor.getColumnIndex(ConversationTable.COLUMN_NAME_NICKNAME)));
		viewHolder.messageText.setText(cursor.getString(cursor.getColumnIndex(ConversationTable.COLUMN_NAME_LATEST_MESSAGE)));
		viewHolder.dateText.setText(dateFormat.format(
				new Date(cursor.getLong(cursor.getColumnIndex(ConversationTable.COLUMN_NAME_TIME)))));
		
		int unreadCount = cursor.getInt(cursor.getColumnIndex(ConversationTable.COLUMN_NAME_UNREAD));
		if (unreadCount == 0) {
			viewHolder.unreadCountText.setVisibility(View.GONE);
			viewHolder.dateText.setEnabled(false);
			viewHolder.messageText.setEnabled(false);
		} else {
			viewHolder.unreadCountText.setText(String.valueOf(unreadCount));
			viewHolder.unreadCountText.setVisibility(View.VISIBLE);
			viewHolder.dateText.setEnabled(true);
			viewHolder.messageText.setEnabled(true);
		}
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = super.newView(context, cursor, parent);
		
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.nameText = (TextView)view.findViewById(R.id.tv_nickname);
		viewHolder.messageText = (TextView)view.findViewById(R.id.tv_message);
		viewHolder.dateText = (TextView)view.findViewById(R.id.tv_date);
		viewHolder.unreadCountText = (TextView)view.findViewById(R.id.tv_unread_count);
		view.setTag(viewHolder);
		
		return view;
	}
	
	static class ViewHolder {
		TextView nameText;
		TextView messageText;
		TextView dateText;
		TextView unreadCountText;
	}
}