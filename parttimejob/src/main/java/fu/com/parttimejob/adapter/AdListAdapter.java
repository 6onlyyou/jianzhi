package fu.com.parttimejob.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fu.com.parttimejob.R;
import fu.com.parttimejob.activity.AdListActivity;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewAdapter;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewHolder;
import fu.com.parttimejob.bean.MAdvertisingBean;
import fu.com.parttimejob.databinding.ItemAdBinding;
import fu.com.parttimejob.utils.GlideUtil;


public class AdListAdapter extends BaseRecyclerViewAdapter {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TouBaoHolder(parent, R.layout.item_ad);
    }


    class TouBaoHolder extends BaseRecyclerViewHolder<ItemAdBinding> {

        public TouBaoHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(BaseRecyclerModel object, int position) {
            final MAdvertisingBean adListAdapter = (MAdvertisingBean) object;
            binding.name.setText(adListAdapter.getCompanyName());
            binding.time.setText("发布时间："+adListAdapter.getPublichDate());
            binding.adContent.setText(adListAdapter.getAdvertisementContent());
            binding.lookAd.setText(adListAdapter.getViewCount());
            binding.unjbi.setText(adListAdapter.getUnclaimedVirtualCoins()+"");
            binding.forwordad.setText(adListAdapter.getForwordCount());
            binding.openAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AdListActivity.class);
                    intent.putExtra("id",adListAdapter.getId());
                    view.getContext().startActivity(intent);
                }
            });
            GlideUtil.load(binding.getRoot().getContext(), adListAdapter.getHeadImg(), (ImageView) binding.ava);
        }
    }
}
