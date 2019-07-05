package fu.com.parttimejob.adapter;

import android.view.ViewGroup;

import fu.com.parttimejob.R;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewAdapter;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewHolder;
import fu.com.parttimejob.databinding.ItemQiuzhiJobBinding;


public class ChooseDreamJobListAdapter extends BaseRecyclerViewAdapter {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TouBaoHolder(parent, R.layout.item_qiuzhi_job);
    }


    class TouBaoHolder extends BaseRecyclerViewHolder<ItemQiuzhiJobBinding> {

        public TouBaoHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(BaseRecyclerModel object, int position) {
            binding.jobName.setText(object.getViewTypeSt());
        }
    }
}
