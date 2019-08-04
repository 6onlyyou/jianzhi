package fu.com.parttimejob.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lljjcoder.citylist.Toast.ToastUtils;

import fu.com.parttimejob.R;
import fu.com.parttimejob.activity.MyMoneyActivity;
import fu.com.parttimejob.activity.ShouHuoActivity;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewAdapter;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewHolder;
import fu.com.parttimejob.bean.ExchangeBean;
import fu.com.parttimejob.databinding.ItemExchangeShopBinding;
import fu.com.parttimejob.dialog.HintDialog;
import fu.com.parttimejob.utils.GlideUtil;
import fu.com.parttimejob.utils.SPUtil;


public class ExchangeShopListAdapter extends BaseRecyclerViewAdapter {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TouBaoHolder(parent, R.layout.item_exchange_shop);
    }


    class TouBaoHolder extends BaseRecyclerViewHolder<ItemExchangeShopBinding> {

        public TouBaoHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(BaseRecyclerModel object, int position) {
            final ExchangeBean exchangeBean = (ExchangeBean) object;
            GlideUtil.load(binding.getRoot().getContext(), exchangeBean.getGoodsImg(), (ImageView) binding.skuImgIv);
            binding.shopName.setText(exchangeBean.getGoodsName());
            binding.zan.setText("需要" + exchangeBean.getGoodsPrice() + "金币");
            binding.layclack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new HintDialog(binding.getRoot().getContext(), R.style.dialog, "需要支付" + exchangeBean.getGoodsPrice() + "个金币是否继续？", new HintDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                if (exchangeBean.getGoodsPrice() > SPUtil.getInt(binding.getRoot().getContext(), "totalCount", 0)) {
                                    ToastUtils.showLongToast(binding.getRoot().getContext(), "余额不足请充值");
                                    Intent i = new Intent(binding.getRoot().getContext(), MyMoneyActivity.class);
                                    binding.getRoot().getContext().startActivity(i);
                                } else {
                                    Intent i = new Intent(binding.getRoot().getContext(), ShouHuoActivity.class);
                                    i.putExtra("exchangeBean", exchangeBean);
                                    binding.getRoot().getContext().startActivity(i);
                                }
                            }

                        }
                    }).show();


                }
            });
        }
    }
}
