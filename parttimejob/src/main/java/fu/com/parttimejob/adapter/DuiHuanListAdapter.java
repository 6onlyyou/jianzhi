package fu.com.parttimejob.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

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
            binding.name.setText(kuaiDiBean.getName());
            binding.time.setText("兑换时间："+kuaiDiBean.getReceiveTime());
//            binding.kuaidiNo.setText(kuaiDiBean.get().toString());

//            binding.openAd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(view.getContext(), AdListActivity.class);
//                    intent.putExtra("id",adListAdapter.getId());
//                    view.getContext().startActivity(intent);
//                }
//            });
            GlideUtil.load(binding.getRoot().getContext(), kuaiDiBean.getAddress(), (ImageView) binding.img);
        }
    }
}
