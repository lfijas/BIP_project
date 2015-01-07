package com.example.bipapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

public class DateRangeDialogFragment extends DialogFragment {
	private View mView;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    builder.setTitle("Select date range");
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout

	    mView = inflater.inflate(R.layout.date_range, null);
	    builder.setView(mView)
	    // Add action buttons
	           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   BrowsePurchaseHistoryActivity browsePurchaseHistoryActivity = (BrowsePurchaseHistoryActivity) getActivity();
	            	   String userId = browsePurchaseHistoryActivity.getUserId();
	            	   DatePicker datePickerFrom = (DatePicker) mView.findViewById(R.id.date_picker_from);
	           	       DatePicker datePickerTill = (DatePicker) mView.findViewById(R.id.date_picker_till);
	            	   browsePurchaseHistoryActivity.applyFilter(userId, datePickerFrom.getYear() + "-" 
	            			   + (datePickerFrom.getMonth() + 1) + "-" + datePickerFrom.getDayOfMonth(), 
	            			   datePickerTill.getYear() + "-" + (datePickerTill.getMonth() + 1)
	            			   + "-" + datePickerTill.getDayOfMonth());
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   DateRangeDialogFragment.this.getDialog().cancel();
	               }
	           });      
	    return builder.create();
	}
}
