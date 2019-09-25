package fu.com.parttimejob.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import fu.com.parttimejob.R;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewAdapter;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewHolder;
import fu.com.parttimejob.bean.KuaiDiBean;
import fu.com.parttimejob.databinding.ItemMyDuihuanBinding;
import fu.com.parttimejob.utils.GlideUtil;


public class DuiHuanListAdapter extends BaseRecyclerViewAdapter {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TouBaoHolder(parent, R.layout.item_my_duihuan);
    }


    class TouBaoHolder extends BaseRecyclerViewHolder<ItemMyDuihuanBinding> {

        public TouBaoHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(BaseRecyclerModel object, int position) {
            final KuaiDiBean kuaiDiBean = (KuaiDiBean) object;
            binding.name.setText(kuaiDiBean.getGoodsName());
            binding.time.setText("兑换时间：" + kuaiDiBean.getReceiveTime());
            if (TextUtils.isEmpty(kuaiDiBean.getTrackingNumber())) {
                binding.kuaidiNo.setText("快递单号： 暂无快递单号" );
            } else {
                binding.kuaidiNo.setText("快递单号：" + kuaiDiBean.getTrackingNumber());
            }

//            binding.openAd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(view.getContext(), AdListActivity.class);
//                    intent.putExtra("id",adListAdapter.getId());
//                    view.getContext().startActivity(intent);
//                }
//            });
            Glide.with(binding.getRoot().getContext())
                    .load(kuaiDiBean.getGoodsImg())
                    .placeholder(R.mipmap.defind)
                    .into(binding.img);
        }
    }
}
