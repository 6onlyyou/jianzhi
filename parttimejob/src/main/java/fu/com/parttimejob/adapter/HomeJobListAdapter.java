package fu.com.parttimejob.adapter;

import android.view.ViewGroup;

import fu.com.parttimejob.R;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewAdapter;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewHolder;
import fu.com.parttimejob.databinding.ItemHomeJobBinding;


public class HomeJobListAdapter extends BaseRecyclerViewAdapter {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TouBaoHolder(parent, R.layout.item_home_job);
    }


    class TouBaoHolder extends BaseRecyclerViewHolder<ItemHomeJobBinding> {

        public TouBaoHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(BaseRecyclerModel object, int position) {

//            TouBaoInfoBean touBaoInfoBean = (TouBaoInfoBean) object;
//            binding.toubaoId.setText("卡号:     " + touBaoInfoBean.getInsuranceNo() + "");
//            binding.toubaoMoney.setText("密码:     " + touBaoInfoBean.getInterestMoney() + "");
//            binding.toubaoTime.setText("发放日期:     " + touBaoInfoBean.getGiveTime()+"");
        }
    }
}
