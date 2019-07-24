package fu.com.parttimejob.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import fu.com.parttimejob.R;
import fu.com.parttimejob.activity.JobInfoActivity;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewAdapter;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewHolder;
import fu.com.parttimejob.bean.JobInfoBean;
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
            final JobInfoBean jobInfoBean = (JobInfoBean)object;
            binding.jobName.setText(jobInfoBean.getCompanyName());
            binding.jobReward.setText("赠送"+jobInfoBean.getNumberOfVirtualCoins()+"金币");
            binding.jobSalaryTv.setText(jobInfoBean.getSalaryAndWelfare()+"");
            binding.jobLocation.setText(jobInfoBean.getCity()+"");
            binding.timejob.setText(jobInfoBean.getPublichDate());
            binding.goJobinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), JobInfoActivity.class);
                    intent.putExtra("id",jobInfoBean.getId());
                    view.getContext().startActivity(intent);
                }
            });

        }
    }
}
