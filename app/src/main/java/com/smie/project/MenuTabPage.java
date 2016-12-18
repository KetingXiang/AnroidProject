package com.smie.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/17.
 */

public class MenuTabPage extends Fragment {

    private View mViewRoot;
    private List<MenuPersonItem> list = new ArrayList<MenuPersonItem>();
    private List< Map<String ,Integer > >  foundIndex = new ArrayList<Map<String,Integer> >();
    // 搜索关键字的起始和终止位置
    private static final String baseurl ="http://172.18.57.116:8000/";
    private static final int SHOW_RESPONSE = 0;
    private ListView listView;
    private TextView sort_by_place;
    private TextView sort_by_price;
    private TextView sort_by_evaluate;
    private EditText edit_found;
    private ImageView found;
    private String personId;
    private String programId;

    public void setId(Bundle bundle){
        personId = bundle.getString("id");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //初始建立
        mViewRoot = inflater.inflate(R.layout.activity_menu, container, false);
        initView(mViewRoot);
        return mViewRoot;
    }

    @Override
    public void onStart() {
        //进入页面
        super.onStart();
        sendRequestWithHttpURLConnection();
    }

    private void initView(View root) {
        findViews(root);

        MenuTabPage.SortListener sortListener = new MenuTabPage.SortListener();
        sort_by_evaluate.setOnClickListener(sortListener);
        sort_by_price.setOnClickListener(sortListener);
        sort_by_place.setOnClickListener(sortListener);
        found.setOnClickListener(sortListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                programId = list.get(position).getMenu_programId();
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",personId);
                bundle.putString("programId",programId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void findViews(View root){
        listView = (ListView)root.findViewById(R.id.menu_main_listview);
        sort_by_place = (TextView)root.findViewById(R.id.menu_sort_by_place);
        sort_by_evaluate = (TextView)root.findViewById(R.id.menu_sort_by_evalute);
        sort_by_price = (TextView)root.findViewById(R.id.menu_sort_by_price);
        edit_found = (EditText)root.findViewById(R.id.menu_main_edit_found);
        found = (ImageView)root.findViewById(R.id.menu_main_found);
    }

    /**zackzhao
     *
     */
    private void initPersonItem(List<Map<String,String>> myItem){
        Log.i("tag","init");
        list.clear();
        for (int i = 0;i < myItem.size();i++){
            int menu_personItemId = R.mipmap.ic_launcher;
            int menu_location_icon = R.mipmap.menu_main_item_location2;
            String menu_programId = myItem.get(i).get("programId");
            String menu_personName = myItem.get(i).get("name");
            String menu_personAddress = myItem.get(i).get("address");
            String menu_evaluate = "评价";
            float menu_evaluta_bar = Float.parseFloat(myItem.get(i).get("evaluate"));
            String menu_personDescription = myItem.get(i).get("description");
            String menu_go_to_connect = "￥："+myItem.get(i).get("price");

            MenuPersonItem person1 = new MenuPersonItem(
                    menu_programId,
                    menu_personItemId,
                    menu_location_icon,
                    menu_personName,
                    menu_personAddress,
                    menu_evaluate,
                    menu_evaluta_bar,
                    menu_personDescription,
                    menu_go_to_connect);
            list.add(person1);
        }

        MenuPersonItemAdapter adapter = new MenuPersonItemAdapter(getActivity(),list,foundIndex);
        listView.setAdapter(adapter);
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_RESPONSE:
                    ArrayList<String> response = (ArrayList<String>) msg.obj;
                    for(String item:response){
                        Log.i("response1",""+item);
                    }
                    List<Map<String,String> > myItem = get_person_item(response);
                    initPersonItem(myItem);
                    break;
            }
        }
    };

    private List<Map<String,String> > get_person_item(ArrayList<String> response){

        List<Map<String,String> > myItems = new ArrayList<Map<String,String> >();

        int program_num = Integer.parseInt(response.get(0));
        for(int i = 0;i < program_num;i++){
            int bisa = i*7;
            Map<String,String> item = new HashMap<String,String>();
            item.put("programId",response.get(1+bisa));
            item.put("name",response.get(2+bisa));
            item.put("address",response.get(3+bisa));
            item.put("evaluate",response.get(4+bisa));
            item.put("description",response.get(6+bisa));
            item.put("price",response.get(5+bisa));
            myItems.add(item);
        }

        return myItems;
    }

    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                findprograms();
            }
        }).start();
    }

    private void findprograms(){
        String url = "";
        url = "http://172.18.57.116:8000/findallprograms/";
        Log.i("tag",url);
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection)((new URL(url.toString())).openConnection());
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            connection.setRequestProperty("Charset","UTF-8");
//            connection.setDoOutput(true);

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder response = new StringBuilder();
            while((line = reader.readLine()) != null){
                response.append(line);
            }

            Message message = new Message();
            message.what = SHOW_RESPONSE;
            Log.i("tag",""+(response.toString()));
            message.obj = parseXMLWithPull(response.toString());
            handler.sendMessage(message);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    /**解析xml
     *
     * @param xmlData :完整的xml字符串文本
     * @return 储存需要信息的数组
     */
    private ArrayList<String> parseXMLWithPull(String xmlData){
        Log.i("tag2",xmlData);
        ArrayList<String> info = new ArrayList<String>();
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));

            int eventType = xmlPullParser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if ("string".equals(nodeName)){
                            info.add(xmlPullParser.nextText());
                            Log.i("infofff",""+info.isEmpty());
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        break;
                    default:
                        break;

                }
                eventType = xmlPullParser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        for(String item:info){
            Log.i("info222",""+item);
        }
        return info;
    }

    /**
     * 初始化comp 时传入排序关键字和升序asc,默认升序
     */
    public class ListMapSortComparator implements Comparator {

        private String key;

        private String order;

        public ListMapSortComparator(String key,String order) {
            this.key = key;
            this.order = order;
        }

        /*
         * (non-Javadoc)
         *
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(Object o1, Object o2){
            MenuPersonItem person1 = (MenuPersonItem)o1;
            MenuPersonItem person2 = (MenuPersonItem)o2;
            switch(key){
                case "price":
                    if (order.equals("asc")) {
                        return person1.getMenu_go_to_connect().toString().compareTo(person2.getMenu_go_to_connect().toString());
                    }else {
                        return person2.getMenu_go_to_connect().toString().compareTo(person1.getMenu_go_to_connect().toString());
                    }

                case "evalute":
                    if (order.equals("asc")) {
                        return (((Float)person1.getMenu_evaluta_bar()).compareTo(((Float)person2.getMenu_evaluta_bar())));
                    }else {
                        return (((Float)person2.getMenu_evaluta_bar()).compareTo(((Float)person1.getMenu_evaluta_bar())));
                    }

                default:
                    if (order.equals("asc")) {
                        return person1.getMenu_go_to_connect().toString().compareTo(person2.getMenu_go_to_connect().toString());
                    }else {
                        return person2.getMenu_go_to_connect().toString().compareTo(person1.getMenu_go_to_connect().toString());
                    }

            }

        }

    }

    /*
    获取匹配之后的关键字位置
     */
    private List<MenuPersonItem> getFoundIndex(){
        List<MenuPersonItem> found_list = new ArrayList<>();
        String keyword = edit_found.getText().toString();
        for (int i = 0; i < list.size();i++){
            String program_name = list.get(i).getMenu_personName();
            Log.i("tag",""+program_name);
            int start = program_name.indexOf(keyword);
            Log.i("tag",""+start);
            if (start == -1){
                ;
            }else{
                int end = start+keyword.length();
                Log.i("tag",""+end);
                Map<String , Integer> map_item = new HashMap<>();
                map_item.put("start",new Integer(start));
                map_item.put("end",new Integer(end));
                foundIndex.add(map_item);
                found_list.add(list.get(i));
            }

        }
        return found_list;
    }

    class SortListener implements View.OnClickListener{
        List<MenuPersonItem> found_list = new ArrayList<>();
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.menu_sort_by_place:
                    break;
                case R.id.menu_sort_by_price:
                    Collections.sort(list,new MenuTabPage.ListMapSortComparator("price" ,"desc"));
                    found_list = list;
                    break;
                case R.id.menu_sort_by_evalute:
                    Collections.sort(list,new MenuTabPage.ListMapSortComparator("evalute" ,"desc"));
                    Collections.sort(list,new MenuTabPage.ListMapSortComparator("evalute" ,"desc"));
                    found_list = list;
                    break;
                case R.id.menu_main_found:

                    found_list = getFoundIndex();
                    break;
            }

            MenuPersonItemAdapter adapter = new MenuPersonItemAdapter(getActivity(),found_list,foundIndex);
            listView.setAdapter(adapter);
        }
    }
}
