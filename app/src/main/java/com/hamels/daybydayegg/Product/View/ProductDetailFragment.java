package com.hamels.daybydayegg.Product.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.VIew.LoginActivity;
import com.hamels.daybydayegg.Main.View.CustomViewsInfo;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.View.MailDetailFragment;
import com.hamels.daybydayegg.Product.Adapter.ProductConfList2Adapter;
import com.hamels.daybydayegg.Product.Adapter.ProductConfListAdapter;
import com.hamels.daybydayegg.Product.Contract.ProductDetailContract;
import com.hamels.daybydayegg.Product.Presenter.ProductDetailPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Repository.Model.ProductConf;
import com.hamels.daybydayegg.Repository.Model.ProductConfAmout;
import com.hamels.daybydayegg.Repository.Model.ProductPicture;
import com.hamels.daybydayegg.Repository.Model.ProductSpec;
import com.hamels.daybydayegg.Utils.WaterMaskUtils;
import com.stx.xhb.xbanner.XBanner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDetailFragment extends BaseFragment implements ProductDetailContract.View {
    public static final String TAG = ProductDetailFragment.class.getSimpleName();
    private static final String PRODUCT_ID = "product_id";
    private static ProductDetailFragment fragment;
    private XBanner mXBanner;
    private Spinner spinner_spec;
    private ConstraintLayout layout_plus, layout_minus, layout_size, layout_spec, constraintLayout_conf;
    private EditText edit_num, conf_qty;
    private ConstraintLayout layout_shopping, btn_conf_qty;
    private Button btn_freight_title;
    private TextView tv_price, tv_sale_price, tv_same_price, tv_store_name, tv_product_type, tv_dealer_product_id, tv_desc, tv_subtotal,tv_water_mask, tv_show_desc;
    private ImageView img_product_ribbon;
    private ProductDetailContract.Presenter presenter;
    private TabLayout tabLayout;
    private ListView listview;
    private ScrollView view_scroll;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<ProductConf>> expandableListDetail;
    ProductConfList2Adapter adapter;

    private String location_id = "0";
    private String product_type_main_id = "0";
    private int product_id = 0;
    private int quantity = 0;
    private int iAllConfPrice = 0;
    private  int iAllSoldout = 0;
    private final String[] Spec_ID = {""};
    private final String[] SpecQty = {""};
    private final String[] Stock = {""};
    private ArrayAdapter<String> ProductSpinnerArray;
    private DecimalFormat mDecimalFormat = new DecimalFormat("#,###");
    private String isETicket = "N";
    private static final String IS_E_TICKET = "isETicket";
    private ArrayList<ProductConfAmout> productList;
    private List<ProductConf> confArrayList = new ArrayList<>();
    private List<ProductConf> SelectConf = new ArrayList<>();
    public int SoldOutQty = 0;
    private int iLimitQuantity = 1;
    private Product product;

    public static ProductDetailFragment getInstance(int product_id, String mIsETicket) {
        if (fragment == null) {
            fragment = new ProductDetailFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_ID, product_id);
        bundle.putString(IS_E_TICKET, mIsETicket);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        if (getArguments() != null) {
            product_id = getArguments().getInt(PRODUCT_ID, 0);
            isETicket = getArguments().getString(IS_E_TICKET, "N");
            initView(view);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);

        mXBanner = view.findViewById(R.id.xbanner);
        img_product_ribbon = view.findViewById(R.id.img_product_ribbon);
        //tv_dealer_product_id = view.findViewById(R.id.tv_dealer_product_id);
        tv_price = view.findViewById(R.id.tv_price);
        tv_sale_price = view.findViewById(R.id.tv_sale_price);
        tv_same_price = view.findViewById(R.id.tv_same_price);
        tv_store_name = view.findViewById(R.id.tv_store_name);
        tv_product_type = view.findViewById(R.id.tv_product_type);
        //tv_desc = view.findViewById(R.id.tv_desc);
        tv_show_desc = view.findViewById(R.id.tv_show_desc);
        spinner_spec = view.findViewById(R.id.spinner_spec);
        layout_minus = view.findViewById(R.id.layout_minus);
        layout_plus = view.findViewById(R.id.layout_plus);
        edit_num = view.findViewById(R.id.edit_num);
        edit_num.setInputType(InputType.TYPE_NULL);
        edit_num.setText(Integer.toString(1 * iLimitQuantity));
        tv_subtotal = view.findViewById(R.id.tv_subtotal);
        layout_shopping = view.findViewById(R.id.btn_shopping);
        layout_spec = view.findViewById(R.id.constraintLayout6);
        tabLayout = view.findViewById(R.id.tab_layout);
        constraintLayout_conf = view.findViewById(R.id.constraintLayout_conf);
        expandableListView = view.findViewById(R.id.expandableListView);
        conf_qty = view.findViewById(R.id.conf_qty);
        conf_qty.setInputType(InputType.TYPE_NULL);
        conf_qty.setText(Integer.toString(1 * iLimitQuantity));
        btn_conf_qty = view.findViewById(R.id.btn_conf_qty);
        listview = view.findViewById(R.id.listview);
        view_scroll = view.findViewById(R.id.view_scroll);
        tv_water_mask = view.findViewById(R.id.tv_water_mask);
        if (isETicket.equals("Y")) {
            ((MainActivity) getActivity()).setAppTitle(R.string.tab_ticket);
            layout_spec.setVisibility(View.GONE);
            img_product_ribbon.setVisibility(View.VISIBLE);
            constraintLayout_conf.setVisibility(View.GONE);
        } else {
            ((MainActivity) getActivity()).setAppTitle(R.string.tab_shop);
            layout_spec.setVisibility(View.VISIBLE);
            img_product_ribbon.setVisibility(View.GONE);
            constraintLayout_conf.setVisibility(View.VISIBLE);
        }

        tv_show_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addFragment(ProductDetailDescFragment.getInstance(product));
            }
        });

        layout_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.valueOf(edit_num.getText().toString());
                quantity = quantity + 1;
                edit_num.setText(Integer.toString(quantity));
                // Leslie 客製數量 連動商品數量
                conf_qty.setText(Integer.toString(quantity));
                iAllConfPrice = 0;
                for (int i = 0; i < productList.size(); i++) {
                    Log.e(TAG, "" + productList.get(i).getPrice());

                    iAllConfPrice += Integer.parseInt(productList.get(i).getPrice().trim().split("\\$")[1].replace(",", ""));
                }
                int subTotal = Integer.parseInt(tv_sale_price.getText().toString().trim().split("NT\\$")[1].replace(",", ""));
                tv_subtotal.setText("$" + ((subTotal * quantity) + iAllConfPrice));
            }
        });

        layout_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.valueOf(edit_num.getText().toString());
                if (quantity > 0) {
                    quantity = quantity - 1;
                }
                edit_num.setText(Integer.toString(quantity));
                // Leslie 客製數量 連動商品數量
                conf_qty.setText(Integer.toString(quantity));

                iAllConfPrice = 0;
                for (int i = 0; i < productList.size(); i++) {
                    //Log.e(TAG, "" + productList.get(i).getPrice());

                    iAllConfPrice += Integer.parseInt(productList.get(i).getPrice().trim().split("\\$")[1].replace(",", ""));
                }
                int subTotal = Integer.parseInt(tv_sale_price.getText().toString().trim().split("NT\\$")[1].replace(",", ""));
                tv_subtotal.setText("$" + ((subTotal * quantity) + iAllConfPrice * iLimitQuantity));
            }
        });

        layout_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();
                final String conf_list = gson.toJson(productList);
                Log.e(TAG, "==============================" + conf_list);

                // Leslie 有選擇客製項目，但 無 點擊客製 加入
                if(!conf_qty.getText().toString().equals("") && !conf_qty.getText().toString().equals("0") && 0 < SelectConf.size())
                {
                    if(Integer.parseInt(conf_qty.getText().toString()) == 1) {
                        if(iAllSoldout == 0) {
                            // 若數量為1，直接加入
                            addConf(v);
                            // 加入購物車
                            addCart();
                        }else{
                            // 若有完售項目，POP
                            new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("今日完售商品僅適用預約取餐，是否確定？")
                                    .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            addConf(v);
                                            // 加入購物車
                                            addCart();
                                        }
                                    })
                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User cancelled the dialog
                                        }
                                    }).show();
                        }
                    } else {
                        // 若數量不為1，pop
                        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("是否將" + conf_qty.getText() + "個都做客製？")
                                .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(iAllSoldout == 0) {
                                            // 若數量為1，直接加入
                                            addConf(v);
                                            // 加入購物車
                                            addCart();
                                        }else{
                                            // 若有完售項目，POP
                                            new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("今日完售商品僅適用預約取餐，是否確定？")
                                                    .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            addConf(v);
                                                            // 加入購物車
                                                            addCart();
                                                        }
                                                    })
                                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            // User cancelled the dialog
                                                        }
                                                    }).show();
                                        }
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                    }
                                }).show();
                    }
                } else {
                    if(iAllSoldout == 0) {
                        // 加入購物車
                        addCart();
                    }else{
                        // 若有完售項目，POP
                        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("今日完售商品僅適用預約取餐，是否確定？")
                                .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 加入購物車
                                        addCart();
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                    }
                                }).show();
                    }
                }
            }
        });

        btn_conf_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConf(v);
                /*
                if (0 < SelectConf.size() && !conf_qty.getText().toString().equals("") && 0 < Integer.parseInt(conf_qty.getText().toString())) {
                    int iQtySum = 0;
                    for (int i = 0; i < productList.size(); i++) {
                        iQtySum += Integer.parseInt(productList.get(i).getQty());
                    }

                    iQtySum += Integer.parseInt(conf_qty.getText().toString());

                    if (iQtySum > Integer.parseInt(edit_num.getText().toString())) {
                        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("數量超過").setPositiveButton(android.R.string.ok, null).show();
                    } else {
                        String sTmpAllConfID = "";
                        String sTmpAllConfName = "";
                        int iTmpAllPrice = 0;
                        for (int i = 0; i < SelectConf.size(); i++) {
                            sTmpAllConfID += SelectConf.get(i).getId();
                            if (!(i == (SelectConf.size() - 1))) {
                                sTmpAllConfID += ",";
                            }
                            sTmpAllConfName += SelectConf.get(i).getConf_name();
                            if (!(i == (SelectConf.size() - 1))) {
                                sTmpAllConfName += "/";
                            }
                            iTmpAllPrice += SelectConf.get(i).getprice();
                        }
                        iTmpAllPrice = iTmpAllPrice * Integer.parseInt(conf_qty.getText().toString());
                        // Leslie 增加總客製數量
                        conf_quantity += Integer.parseInt(conf_qty.getText().toString());
                        Log.e(TAG, "conf_quantity: " + conf_quantity);

                        ProductConfAmout item1;
                        item1 = new ProductConfAmout(sTmpAllConfID, sTmpAllConfName, conf_qty.getText().toString(), "$" + iTmpAllPrice);
                        productList.add(item1);
                        adapter.notifyDataSetChanged();
                        setListViewHeightBasedOnChildren(listview);

                        // 清空
                        SelectConf = new ArrayList<>();

                        final HashMap<String, List<ProductConf>> expandableListDetail = new HashMap<String, List<ProductConf>>();
                        List<ProductConf> cricket = new ArrayList<ProductConf>();

                        String sLastMainConfName = "";
                        for (int i = 0; i < confArrayList.size(); i++) {
                            if ((!sLastMainConfName.equals("") && !sLastMainConfName.equals(confArrayList.get(i).getMain_conf_name())) || (i + 1) == confArrayList.size()) {
                                // 最後一筆
                                if ((i + 1) == confArrayList.size()) {
                                    cricket.add(confArrayList.get(i));
                                }

                                expandableListDetail.put(sLastMainConfName, cricket);

                                cricket = new ArrayList<ProductConf>();
                            }

                            cricket.add(confArrayList.get(i));

                            sLastMainConfName = confArrayList.get(i).getMain_conf_name();
                        }

                        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                        expandableListAdapter = new ProductConfListAdapter(getContext(), expandableListTitle, expandableListDetail, SelectConf);
                        expandableListView.setAdapter(expandableListAdapter);

                        setListViewHeight(expandableListView, -1);
                        for (int i = 0; i < expandableListTitle.size(); i++) {
                            setListViewHeight(expandableListView, i);
                            expandableListView.expandGroup(i);
                        }
                    }
                }
                */
            }
        });
    }

    private  void addCart(){
        // 加入購物車
        GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        final String conf_list = gson.toJson(productList);
        Log.e(TAG, "==============================" + conf_list);

        int iQtySum = 0;
        for (int i = 0; i < productList.size(); i++) {
            productList.get(i).setQty("" + Integer.parseInt(productList.get(i).getQty()) * iLimitQuantity);
            iQtySum += Integer.parseInt(productList.get(i).getQty());
        }

        String sQuantity = "" + (Integer.parseInt(edit_num.getText().toString()) * iLimitQuantity);

        presenter.addShoppingCart(Integer.toString(product_id), Spec_ID[0], location_id, SpecQty[0], Stock[0], sQuantity, isETicket.equals("Y") ? "E" : "G", conf_list, iQtySum);

        // set default
        //edit_num.setText(Integer.toString(1 * iLimitQuantity));
        //conf_qty.setText(Integer.toString(1 * iLimitQuantity));
        //int subTotal = Integer.parseInt(tv_sale_price.getText().toString().trim().split("NT\\$")[1].replace(",", ""));
        //tv_subtotal.setText("$" + subTotal);
    }

    private void addConf(@NonNull View v){
        if (0 < SelectConf.size() && !conf_qty.getText().toString().equals("") && 0 < Integer.parseInt(conf_qty.getText().toString())) {
            int iQtySum = 0;
            for (int i = 0; i < productList.size(); i++) {
                iQtySum += Integer.parseInt(productList.get(i).getQty());
            }

            iQtySum += Integer.parseInt(conf_qty.getText().toString()) * iLimitQuantity;

            if (iQtySum > Integer.parseInt(edit_num.getText().toString()) * iLimitQuantity) {
                new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("數量超過").setPositiveButton(android.R.string.ok, null).show();
            } else {
                String sTmpAllConfID = "";
                String sTmpAllConfName = "";

                int iTmpAllPrice = 0;
                for (int i = 0; i < SelectConf.size(); i++) {
                    sTmpAllConfID += SelectConf.get(i).getId();
                    if (!(i == (SelectConf.size() - 1))) {
                        sTmpAllConfID += ",";
                    }
                    sTmpAllConfName += SelectConf.get(i).getConf_name();
                    if (!(i == (SelectConf.size() - 1))) {
                        sTmpAllConfName += "/";
                    }
                    iTmpAllPrice += SelectConf.get(i).getprice() * iLimitQuantity;

                    if(SelectConf.get(i).getSoldOutQty().equals("Y")){
                        iAllSoldout += SelectConf.get(i).getSoldOutQty().equals("Y") ? 1 : 0;
                    }
                }
                iTmpAllPrice = iTmpAllPrice * Integer.parseInt(conf_qty.getText().toString()) * iTmpAllPrice;
                // Leslie 增加總客製數量
                //conf_quantity += Integer.parseInt(conf_qty.getText().toString());
                //Log.e(TAG, "conf_quantity: " + conf_quantity);

                ProductConfAmout item1;
                item1 = new ProductConfAmout(sTmpAllConfID, sTmpAllConfName, conf_qty.getText().toString(), "$" + iTmpAllPrice);
                productList.add(item1);
                adapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(listview);

                // 清空
                SelectConf = new ArrayList<>();

                final HashMap<String, List<ProductConf>> expandableListDetail = new HashMap<String, List<ProductConf>>();
                List<ProductConf> cricket = new ArrayList<ProductConf>();

                String sLastMainConfName = "";
                for (int i = 0; i < confArrayList.size(); i++) {
                    if ((!sLastMainConfName.equals("") && !sLastMainConfName.equals(confArrayList.get(i).getMain_conf_name())) || (i + 1) == confArrayList.size()) {
                        // 最後一筆
                        if ((i + 1) == confArrayList.size()) {
                            cricket.add(confArrayList.get(i));
                        }

                        expandableListDetail.put(sLastMainConfName, cricket);

                        cricket = new ArrayList<ProductConf>();
                    }

                    cricket.add(confArrayList.get(i));

                    sLastMainConfName = confArrayList.get(i).getMain_conf_name();
                }

                expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                expandableListAdapter = new ProductConfListAdapter(getContext(), expandableListTitle, expandableListDetail, SelectConf);
                expandableListView.setAdapter(expandableListAdapter);

                setListViewHeight(expandableListView, -1);
                for (int i = 0; i < expandableListTitle.size(); i++) {
                    setListViewHeight(expandableListView, i);
                    expandableListView.expandGroup(i);
                }
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ProductDetailPresenter(this, getRepositoryManager(getContext()));
        presenter.getProductDetailByID(Integer.toString(product_id), isETicket);
    }

    @Override
    public void setProductDetail(List<Product> productDetail) {
        this.product = productDetail.get(0);
        List<ProductPicture> productPictureList = productDetail.get(0).getPicture_url_list();
        List<CustomViewsInfo> data = new ArrayList<>();
        iLimitQuantity = productDetail.get(0).getLimitQuantity();
        iAllSoldout = productDetail.get(0).getSoldoutToday().equals("Y") ? 1 : 0;
        for (int i = 0; i < productPictureList.size(); i++) {
            data.add(new CustomViewsInfo(EOrderApplication.sApiUrl + productPictureList.get(i).getPictureurl(), productPictureList.get(i).getId()));
        }
        mXBanner.setBannerData(R.layout.layout_main_activity, data);
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                ImageView img_carousel = (ImageView) view.findViewById(R.id.img_carousel);
                Glide
                        .with(getActivity())
                        .load(((CustomViewsInfo) model).getXBannerUrl())
                        .into(img_carousel);

            }
        });

        if (iAllSoldout > 0){
            List<String> labels = new ArrayList<>();
            labels.add("今日完售");
            tv_water_mask.setBackground(new WaterMaskUtils(getContext(),labels,-30,18));
        }
        location_id = productDetail.get(0).getLocation_id();
        product_type_main_id = productDetail.get(0).getProductTypeMainID();
        tv_product_type.setText(productDetail.get(0).getProduct_name());
        tv_store_name.setText(productDetail.get(0).getProductTypeMainName() + " - " + productDetail.get(0).getTypeName());

        String sTicketSalePrice = mDecimalFormat.format((double) (product.getticket_sales_price() * iLimitQuantity));
        String sSalePrice = mDecimalFormat.format((double) (product.getSale_price() * iLimitQuantity));
        String sPrice = mDecimalFormat.format((double) (product.getPrice() * iLimitQuantity));

        if (isETicket.equals("Y")) {
            tv_same_price.setText("NT$" + sTicketSalePrice);
            tv_sale_price.setText("NT$" + sTicketSalePrice);
        } else {
            tv_same_price.setText("NT$" + sSalePrice);
            tv_sale_price.setText("NT$" + sSalePrice);
        }
        tv_price.setText("NT$" + sPrice);
        //tv_dealer_product_id.setText(productDetail.get(0).getDealer_product_id());
        //tv_desc.setText(Html.fromHtml(productDetail.get(0).getDesc()));
        tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        edit_num.setText(Integer.toString(1));
        if (isETicket.equals("Y")) {
            tv_subtotal.setText("$" + sTicketSalePrice);
        } else {
            tv_subtotal.setText("$" + sSalePrice);
        }
        conf_qty.setText(Integer.toString(1));

        if (productDetail.get(0).getSale_price() == productDetail.get(0).getPrice()) {
            tv_same_price.setVisibility(View.VISIBLE);
            tv_sale_price.setVisibility(View.GONE);
            tv_price.setVisibility(View.GONE);
        } else {
            tv_same_price.setVisibility(View.GONE);
            tv_sale_price.setVisibility(View.VISIBLE);
            tv_price.setVisibility(View.VISIBLE);
        }




        // SPEC ----------------------------------------------------------------------------------------------------
        final List<ProductSpec> specArrayList = productDetail.get(0).getSpec_list();

        // default
        if (specArrayList.size() > 0) {
            Spec_ID[0] = Integer.toString(specArrayList.get(0).getId());
            SpecQty[0] = Integer.toString(specArrayList.get(0).getSpec_qty());

            if (isETicket.equals("Y")) {
                Stock[0] = Integer.toString(specArrayList.get(0).getticket_stock());
            } else {
                Stock[0] = specArrayList.get(0).getStock();
            }
        }

        tabLayout.removeAllTabs();
        for (int i = 0; i < specArrayList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(specArrayList.get(i).getSpecname()).setTag(i));
        }

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(10, 0, 10, 0);
            tab.requestLayout();
        }

        //監聽觸發事件
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int iSpecKey = Integer.parseInt(tab.getTag().toString());

                Spec_ID[0] = Integer.toString(specArrayList.get(iSpecKey).getId());
                SpecQty[0] = Integer.toString(specArrayList.get(iSpecKey).getSpec_qty());

                if (isETicket.equals("Y")) {
                    Stock[0] = Integer.toString(specArrayList.get(iSpecKey).getticket_stock());
                } else {
                    Stock[0] = specArrayList.get(iSpecKey).getStock();
                }

                String sTicketSalePrice = mDecimalFormat.format((double) (specArrayList.get(iSpecKey).getticket_sales_price() * iLimitQuantity));
                String sSalePrice = mDecimalFormat.format((double) (specArrayList.get(iSpecKey).getsale_price() * iLimitQuantity));
                String sPrice = mDecimalFormat.format((double) (specArrayList.get(iSpecKey).getprice() * iLimitQuantity));

                if (isETicket.equals("Y")) {
                    tv_same_price.setText("NT$" + sTicketSalePrice);
                    tv_sale_price.setText("NT$" + sTicketSalePrice);
                } else {
                    tv_same_price.setText("NT$" + sSalePrice);
                    tv_sale_price.setText("NT$" + sSalePrice);
                }
                tv_price.setText("NT$" + sPrice);

                if (specArrayList.get(iSpecKey).getsale_price() == specArrayList.get(iSpecKey).getprice()) {
                    tv_same_price.setVisibility(View.VISIBLE);
                    tv_sale_price.setVisibility(View.GONE);
                    tv_price.setVisibility(View.GONE);
                } else {
                    tv_same_price.setVisibility(View.GONE);
                    tv_sale_price.setVisibility(View.VISIBLE);
                    tv_price.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        if(specArrayList.size() == 0 || specArrayList.size() == 1){
            layout_spec.setVisibility(getView().GONE);
        }

        // Conf btn ----------------------------------------------------------------------------------------------------
        SelectConf = new ArrayList<>();
        confArrayList = productDetail.get(0).getConf_list();

        final HashMap<String, List<ProductConf>> expandableListDetail = new HashMap<String, List<ProductConf>>();
        List<ProductConf> cricket = new ArrayList<ProductConf>();

        String sLastMainConfName = "";
        for (int i = 0; i < confArrayList.size(); i++) {
            if ((!sLastMainConfName.equals("") && !sLastMainConfName.equals(confArrayList.get(i).getMain_conf_name())) || (i + 1) == confArrayList.size()) {
                // 最後一筆
                if ((i + 1) == confArrayList.size()) {
                    cricket.add(confArrayList.get(i));
                }

                expandableListDetail.put(sLastMainConfName, cricket);

                cricket = new ArrayList<ProductConf>();
            }

            cricket.add(confArrayList.get(i));

            sLastMainConfName = confArrayList.get(i).getMain_conf_name();
        }

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new ProductConfListAdapter(getContext(), expandableListTitle, expandableListDetail, SelectConf);
        expandableListView.setAdapter(expandableListAdapter);

        setListViewHeight(expandableListView, -1);
        for (int i = 0; i < expandableListTitle.size(); i++) {
            setListViewHeight(expandableListView, i);
            expandableListView.expandGroup(i);
        }

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });


        // Conf List ----------------------------------------------------------------------------------------------------
        productList = new ArrayList<ProductConfAmout>();
        adapter = new ProductConfList2Adapter(getActivity(), productList, SelectConf);
        listview.setAdapter(adapter);

        setListViewHeightBasedOnChildren(listview);

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                quantity = Integer.valueOf(edit_num.getText().toString()).intValue();

                iAllConfPrice = 0;
                for (int i = 0; i < productList.size(); i++) {
                    //Log.e(TAG, "" + productList.get(i).getPrice());

                    iAllConfPrice += Integer.parseInt(productList.get(i).getPrice().trim().split("\\$")[1]) * iLimitQuantity;
                }
                int subTotal = Integer.parseInt(tv_sale_price.getText().toString().trim().split("NT\\$")[1]) * iLimitQuantity;

                tv_subtotal.setText("$" + ((subTotal * quantity) + iAllConfPrice));
            }
        });

        if(confArrayList.size() == 0){
            constraintLayout_conf.setVisibility(getView().GONE);
        }

        view_scroll.smoothScrollTo(0,0);
    }

    @Override
    public void intentToLogin(int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.getActivity().startActivityForResult(intent, requestCode);
    }

    @Override
    public void showErrorAlert(String message) {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
        edit_num.setText(Integer.toString(1));
        ((MainActivity) getActivity()).refreshBadge();
    }

    public void showAddCartSuccess(String message){
        new androidx.appcompat.app.AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(message)
                .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity) getActivity()).refreshBadge();
                        ((MainActivity) getActivity()).addFragment(ProductFragment.getInstance(location_id, Integer.parseInt(product_type_main_id), isETicket));
                    }
                }).show();
    }

    private void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;

        ProductConfList2Adapter listAdapter = (ProductConfList2Adapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onDestroy() {
        fragment = null;
        super.onDestroy();
    }
}