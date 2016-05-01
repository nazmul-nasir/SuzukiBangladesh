package www.icebd.com.suzukibangladesh.spare_parts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import www.icebd.com.suzukibangladesh.R;

import www.icebd.com.suzukibangladesh.bikelist.BikeList;
import www.icebd.com.suzukibangladesh.bikelist.BikeListSwipeListAdapter;
import www.icebd.com.suzukibangladesh.menu.MyBikeFragment;
import www.icebd.com.suzukibangladesh.menu.SpareParts;
import www.icebd.com.suzukibangladesh.utilities.APIFactory;
import www.icebd.com.suzukibangladesh.utilities.ConnectionManager;
import www.icebd.com.suzukibangladesh.utilities.Constant;
import www.icebd.com.suzukibangladesh.utilities.CustomDialog;
import www.icebd.com.suzukibangladesh.utilities.JsonParser;


public class SparePartsList extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    Context context;

    private EditText inputSparePartsSearch;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private TextView no_spare_parts_item;
    private SparePartsListSwipeListAdapter sparePartsListSwipeListAdapter;

    private List<SparePartsListObject.SparePartsItem> listSparePartsItem;
    private FetchSparePartsListTask fetchSparePartsListTask = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    APIFactory apiFactory;
    CustomDialog customDialog;
    ProgressDialog progressDialog;
    SharedPreferences pref ;
    SharedPreferences.Editor editor;

    public static SparePartsList newInstance() {
        SparePartsList fragment = new SparePartsList();
        return fragment;
    }

    public SparePartsList(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spare_parts_list, container,
                false);
        context = getActivity().getApplicationContext();
        pref = context.getSharedPreferences("SuzukiBangladeshPref", getActivity().MODE_PRIVATE);
        editor = pref.edit();
        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = this.getArguments();
        int myInt = bundle.getInt("selectedTab", 0);

        inputSparePartsSearch = (EditText) view.findViewById(R.id.inputSparePartsSearch);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        listView = (ListView) view.findViewById(R.id.listView);
        no_spare_parts_item = (TextView) view.findViewById(R.id.no_spare_parts_item);
        no_spare_parts_item.setVisibility(View.VISIBLE);

        listSparePartsItem = new ArrayList<>();
        //sparePartsListSwipeListAdapter = new SparePartsListSwipeListAdapter(context, listSparePartsItem,SparePartsList.this);
        //listView.setAdapter(sparePartsListSwipeListAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        apiFactory = new APIFactory();
                                        customDialog = new CustomDialog(getActivity());
                                        fetchSparePartsListTask = new FetchSparePartsListTask(pref.getString("auth_key",null));
                                        fetchSparePartsListTask.execute((Void) null);
                                        //fetchBikeList();
                                    }
                                }
        );

        /*boolean isItemClickAddToCart = false;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if( ((SparePartsListSwipeListAdapter.Holder)view.getTag()).txtSparePartsAddToCart.getText().equals(getResources().getString(R.string.fa_shopping_add_to_cart)) )
                {
                    Toast.makeText(context, "You Clicked "+listSparePartsItem.get(position).getSpare_parts_name().toString(), Toast.LENGTH_LONG).show();
                    SparePartsListObject obj_sparePartsList = new SparePartsListObject();
                    SparePartsListObject.SparePartsItem obj_sparePartsItem = obj_sparePartsList.new SparePartsItem();
                    int qnt = 0;
                    if( obj_sparePartsItem.getSpare_parts_id().equals(listSparePartsItem.get(position).getSpare_parts_id()) )
                    {
                        qnt += 1;
                    }
                    MyCartObject myCartObject = new MyCartObject(obj_sparePartsItem,qnt);

                    Constant.listMyCartObj.add(myCartObject);

                    Iterator<MyCartObject> iteratorMyCartObject = null;
                    iteratorMyCartObject = Constant.listMyCartObj.iterator();
                    while (iteratorMyCartObject.hasNext()) {
                        MyCartObject myCartObjectIn = iteratorMyCartObject.next();

                        System.out.println(element);
                    }

                }
            }
        });*/
        inputSparePartsSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                System.out.println("Text ["+s+"]");
                sparePartsListSwipeListAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });


        return view;
    }

    @Override
    public void onRefresh()
    {
        apiFactory = new APIFactory();
        customDialog = new CustomDialog(getActivity());
        fetchSparePartsListTask = new FetchSparePartsListTask(pref.getString("auth_key",null));
        fetchSparePartsListTask.execute((Void) null);
    }

    public class FetchSparePartsListTask extends AsyncTask<Void, Void, String>
    {
        private String RESULT = "OK";
        private ArrayList<SparePartsListObject> returnJsonData;
        private ArrayList<NameValuePair> nvp2=null;
        private String device_type = "1";
        private String device_token = "device_token";
        private String udid = "udid";
        private InputStream response;
        private JsonParser jsonParser;

        private String methodName = "";
        private String GCMkey = "";
        private String DeviceID = "udid";
        private String auth_key;

        //UserLoginTask(String email, String password)
        FetchSparePartsListTask(String auth_key)
        {
            this.auth_key = auth_key;
        }

        @Override
        protected void onPreExecute() {

            //progressDialog = ProgressDialog.show(getActivity(), null, null);
        }
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                if (ConnectionManager.hasInternetConnection())
                {
                    auth_key = "b78c0c986e4a3d962cd220427bc8ff07";
                    nvp2 = apiFactory.getSparePartsListInfo(auth_key);
                    methodName = "spareList";
                    response = ConnectionManager.getResponseFromServer(methodName, nvp2);
                    jsonParser = new JsonParser();

                    System.out.println("server response : "+response);
                    returnJsonData = jsonParser.parseAPIgetSparePartsListInfo(response);
                    System.out.println("return data : " + returnJsonData);

                }
                else
                {
                    RESULT = getString(R.string.error_no_internet);
                    return RESULT;
                }
                return RESULT;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                Log.e("APITask:", ex.getMessage());
                RESULT = getString(R.string.error_sever_connection);
                return RESULT;
            }
        }

        @Override
        protected void onPostExecute(String result)
        {
            //progressDialog.dismiss();
            swipeRefreshLayout.setRefreshing(false);
            if(RESULT.equalsIgnoreCase("OK"))
            {
                try
                {
                    //finish();

                    if (returnJsonData.size() > 0 && returnJsonData != null && returnJsonData.get(0).isStatus() == true )
                    {
                        //preferenceUtil.setPINstatus(1);
                        Toast.makeText(getActivity(), returnJsonData.get(0).getMessage(), Toast.LENGTH_SHORT).show();

                        listSparePartsItem = returnJsonData.get(0).getSparePartsItemsList();
                        sparePartsListSwipeListAdapter = new SparePartsListSwipeListAdapter(context, listSparePartsItem,SparePartsList.this);
                        listView.setAdapter(sparePartsListSwipeListAdapter);

                        sparePartsListSwipeListAdapter.notifyDataSetChanged();

                    } else
                    {
                        System.out.println("data return : " + returnJsonData);
                        Toast.makeText(getActivity(), returnJsonData.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                        listView.setVisibility(View.GONE);
                        no_spare_parts_item.setVisibility(View.VISIBLE);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    Log.e("APITask data error :", ex.getMessage());
                }
            }
            else {

                customDialog.alertDialog("ERROR", result);
            }
        }
        @Override
        protected void onCancelled()
        {
            fetchSparePartsListTask = null;
            //progressDialog.dismiss();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
