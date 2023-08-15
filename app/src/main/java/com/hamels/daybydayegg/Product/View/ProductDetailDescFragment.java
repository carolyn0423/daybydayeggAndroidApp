package com.hamels.daybydayegg.Product.View;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.MemberMessage;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.xml.sax.XMLReader;

public class ProductDetailDescFragment extends BaseFragment {
    public static final String TAG = ProductDetailDescFragment.class.getSimpleName();

    private static ProductDetailDescFragment fragment;
    private Product product;

    private TextView tvTitle, tvContent;

    public static ProductDetailDescFragment getInstance(Product product) {
        if (fragment == null) {
            fragment = new ProductDetailDescFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(Product.TAG, product);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail_desc, container, false);
        if (getArguments() != null && getArguments().containsKey(Product.TAG)) {
            product = getArguments().getParcelable(Product.TAG);
        }
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (product != null) {
            tvTitle.setText(product.getProduct_name());
            //tvContent.setText(Html.fromHtml(product.getDesc()));
        }
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.product_desc);
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);

        tvTitle = view.findViewById(R.id.tv_product_title);
        tvContent = view.findViewById(R.id.tv_product_content);

        if (product != null) {
            tvTitle.setText(product.getProduct_name());

            final Drawable placeholder = ContextCompat.getDrawable(getActivity(), R.drawable.gift);
            placeholder.setBounds(0, 0, placeholder.getIntrinsicWidth(), placeholder.getIntrinsicHeight());

            Html.ImageGetter imageGetter = new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    // 使用 Picasso 加载网络图片
                    Target target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                            // 创建一个 ImageSpan 来显示图片
                            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);

                            // 替换 <img> 标签为 ImageSpan
                            SpannableStringBuilder builder = new SpannableStringBuilder(tvContent.getText());
                            builder.append(" "); // 这里不需要加空格
                            builder.setSpan(imageSpan, builder.length() - 1, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            // 处理其他文本内容，例如使用 MyTagHandler 处理 <strong> 和 <b> 标签
                            // 这里省略其他处理代码，请确保在 builder 内部处理其他文本
                            // ...

                            tvContent.setText(builder);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    };

                    Picasso.get().load(source).into(target);

                    // 返回一个占位符 Drawable，稍后会被替换
                    Drawable placeholder = ContextCompat.getDrawable(getActivity(), R.drawable.white_background);
                    placeholder.setBounds(0, 0, placeholder.getIntrinsicWidth(), placeholder.getIntrinsicHeight());
                    return placeholder;
                }
            };

            MyTagHandler tagHandler = new MyTagHandler();

            Spannable spannable = (Spannable) HtmlCompat.fromHtml(product.getDesc(), HtmlCompat.FROM_HTML_MODE_LEGACY, imageGetter, tagHandler);

            // 设置 Spannable 到 TextView
            tvContent.setText(spannable);
        }
    }

    private class MyTagHandler implements Html.TagHandler {
        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            // Handle custom HTML tags or styles here
            // For example, you can apply styles to <strong> and <em> tags
            // You can also handle <a> tags to make them clickable
            if (tag.equalsIgnoreCase("strong") || tag.equalsIgnoreCase("b")) {
                processStrongTag(opening, output);
            }
        }

        private void processStrongTag(boolean opening, Editable output) {
            int len = output.length();
            if (opening) {
                output.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), len, len, Spannable.SPAN_MARK_MARK);
            } else {
                Object obj = getLast(output, android.text.style.StyleSpan.class);
                int where = output.getSpanStart(obj);

                output.removeSpan(obj);

                if (where != len) {
                    output.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        private Object getLast(Editable text, Class kind) {
            Object[] objs = text.getSpans(0, text.length(), kind);

            if (objs.length == 0) {
                return null;
            } else {
                for (int i = objs.length; i > 0; i--) {
                    if (text.getSpanFlags(objs[i - 1]) == Spannable.SPAN_MARK_MARK) {
                        return objs[i - 1];
                    }
                }
                return null;
            }
        }
    }
}
