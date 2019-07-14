package fu.com.parttimejob.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fu.com.parttimejob.R;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewAdapter;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewHolder;
import fu.com.parttimejob.bean.ExchangeBean;
import fu.com.parttimejob.databinding.ItemExchangeShopBinding;
import fu.com.parttimejob.utils.GlideUtil;


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
            ExchangeBean exchangeBean = (ExchangeBean) object;
            GlideUtil.load(binding.getRoot().getContext(), exchangeBean.getGoodsImg(), (ImageView)binding.skuImgIv);
            binding.shopName.setText(exchangeBean.getGoodsName());
            binding.zan.setText("赠送"+exchangeBean.getGoodsPrice()+"金币");
            binding.exchangeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startActivity(Intent(context, JobActivity::class.java))
                }
            });
        }
    }
}
