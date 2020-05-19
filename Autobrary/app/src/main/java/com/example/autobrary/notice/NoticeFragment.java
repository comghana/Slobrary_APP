package com.example.autobrary.notice;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.autobrary.R;
import com.example.autobrary.main.Rpage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Vector;


public class NoticeFragment extends Fragment {
    private NoticeAdapter adapter;
    private Rpage activity;
    private ListView listView;
    private ImageView searchBt;
    private Spinner searchType;
    private String searchString = "전체";
    private BootstrapEditText searchQuery;
    private Vector<NoticeInfo> getNotice;
    private Context context;
    private Notice notice = new Notice();

    public static NoticeFragment newInstance() {
        return new NoticeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Rpage) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_notice, container, false);
        context = container.getContext();
        adapter = new NoticeAdapter();
        listView = (ListView) rootView.findViewById(R.id.noticeList);
        searchBt = (ImageView) rootView.findViewById(R.id.searchBt);
        searchType = (Spinner) rootView.findViewById(R.id.searchType);
        searchQuery = (BootstrapEditText) rootView.findViewById(R.id.searchQuery);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("title", getNotice.get(position).getTitle());
                bundle.putString("id", getNotice.get(position).getId());
                bundle.putString("owner", getNotice.get(position).getName());
                bundle.putString("contents", getNotice.get(position).getContents());
                bundle.putString("date", getNotice.get(position).getDate());
                ((Rpage)getActivity()).replaceFragment(Notice2Fragment.newInstance(), bundle);
            }
        });
        
        ArrayAdapter searchAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.noticeSearchOption, android.R.layout.simple_spinner_item);
        searchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchType.setAdapter(searchAdapter);
        searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    searchNotice(searchString, searchQuery.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            initialize();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        return rootView;
    }

    private void initialize() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        if (getNotice()) {
            for (NoticeInfo info : getNotice) {
                adapter.addItem(info);
            }
        }
    }

    private void searchNotice(String searchType, String searchQuery) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        adapter.clearItem();
        switch (searchType) {
            case "전체": //전체 검색
                for (NoticeInfo info : getNotice) {
                    adapter.addItem(info);
                }
                break;
            case "제목": //제목 검색
                for (NoticeInfo info : getNotice) {
                    if (info.getTitle().contains(searchQuery)) {
                        adapter.addItem(info);
                    }
                }
                break;
            case "작성자": //작성자 검색
                for (NoticeInfo info : getNotice) {
                    if (info.getName().contains(searchQuery)) {
                        adapter.addItem(info);
                    }
                }
                break;
            default: //그 외 검색 (오류 또는 변조 -> 대부분 변조)
                Toast.makeText(context, "뭐여? 해킹한겨? 바꿔줄 생각 없어. 안돼 돌아가.", Toast.LENGTH_LONG).show();
                Log.e("OutOfType", "-- Out of search type at notice activity. --");
                android.os.Process.killProcess(android.os.Process.myPid());
        }
        adapter.notifyDataSetChanged();
    }

    private boolean getNotice() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        boolean result;
        adapter.clearItem();
        getNotice = notice.execute();
        if (getNotice.isEmpty()) {
            Toast.makeText(context, "공지사항이 없거나 인터넷연결이 불안정합니다.", Toast.LENGTH_LONG).show();
            result = false;
        } else {
            result = true;
        }
        return result;
    }
}
