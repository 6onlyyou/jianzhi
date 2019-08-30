package fu.com.parttimejob.adapter;

import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import fu.com.parttimejob.R;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewAdapter;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewHolder;
import fu.com.parttimejob.bean.BillBean;
import fu.com.parttimejob.databinding.ItemBillBinding;


public class BillListAdapter extends BaseRecyclerViewAdapter {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BillHolder(parent, R.layout.item_bill);
    }


    class BillHolder extends BaseRecyclerViewHolder<ItemBillBinding> {
        public BillHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(BaseRecyclerModel object, int position) {
            final BillBean billBean = (BillBean) object;
            binding.title.setText(billBean.getName());
            binding.time.setText(billBean.getTime());
            binding.billState.setText(billBean.getCount());
            if ((billBean.getCount()+"").contains("-")) {
                binding.billState.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.colorRed));
            } else {
                binding.billState.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.colorPrimary));
            }

            binding.executePendingBindings();
        }
    }
}
