package com.lubasu.barakamachumu.crdbtrans;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CrdbFragment extends Fragment {

    private ArrayList<String> smsList = new ArrayList<String>();
    private ListView mListView;
    private ArrayAdapter<String> adapter;
    private static final String INBOX_URI = "content://sms/inbox";
    private static CrdbFragment activity;
    public CrdbFragment() {
        // Required empty public constructor
    }

    public static CrdbFragment instance() {
        return activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crdb, container, false);
        // Inflate the layout for this fragment
        mListView =  view.findViewById(R.id.list);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter =  new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,smsList);

        mListView.setAdapter(adapter);
        readSMS();
    }

    public void readSMS() {

        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse(INBOX_URI), null, null, null, null);
        int senderIndex = smsInboxCursor.getColumnIndex("address");
        int messageIndex = smsInboxCursor.getColumnIndex("body");
        if (messageIndex < 0 || !smsInboxCursor.moveToFirst()) return;
        adapter.clear();
        do {
            String sender = smsInboxCursor.getString(senderIndex);
            if (sender.equals("CRDB BANK")){
                String message = smsInboxCursor.getString(messageIndex);

                String  date= "2018-07-20";
                if (message.contains("Ref:")){

                    if (message.contains("TZS")){
                       message= message.replace("TZS","");
                    }
                    String str = "Nom for 3 Oscar, dom for 234235 Oscars";
                    Pattern pattern = Pattern.compile("sent(.*?)from");
                    Matcher matcher = pattern.matcher(message);
                    while (matcher.find()) {

                        String formattedText = String.format(getResources().getString(R.string.sms_message), sender, matcher.group(1),date);
                        adapter.add(Html.fromHtml(formattedText).toString());
                    }

                }
            }

        } while (smsInboxCursor.moveToNext());
    }
    public void updateList(final String newSms) {
        adapter.insert(newSms, 0);
        adapter.notifyDataSetChanged();
    }
}
