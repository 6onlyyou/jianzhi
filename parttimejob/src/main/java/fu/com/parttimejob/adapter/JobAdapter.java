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
import fu.com.parttimejob.bean.SameCityBean;
import fu.com.parttimejob.databinding.ItemHomeJobBinding;


public class JobAdapter extends BaseRecyclerViewAdapter {
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
            binding.timejob.setText(jobInfoBean.getPublichDate());
            binding.jobName.setText(jobInfoBean.getLabel());
            binding.jobReward.setText("奖励"+jobInfoBean.getNumberOfVirtualCoins()+"金币");
            binding.jobSalaryTv.setText("工资："+jobInfoBean.getSalaryAndWelfare()+"");
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
