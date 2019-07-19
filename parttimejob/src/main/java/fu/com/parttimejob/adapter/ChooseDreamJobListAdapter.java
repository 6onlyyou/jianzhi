package fu.com.parttimejob.adapter;

import android.view.ViewGroup;

import java.util.ArrayList;

import fu.com.parttimejob.R;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewAdapter;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewHolder;
import fu.com.parttimejob.bean.GetLabelsBean;
import fu.com.parttimejob.databinding.ItemQiuzhiJobBinding;


public class ChooseDreamJobListAdapter extends BaseRecyclerViewAdapter {

    private ArrayList<Integer> selectPositions = new ArrayList<>();

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TouBaoHolder(parent, R.layout.item_qiuzhi_job);
    }

    public void changeSelectPosition(int position){
        if (selectPositions.contains(Integer.valueOf(position))){
            selectPositions.remove(Integer.valueOf(position));
        }else {
            selectPositions.add(position);
        }
        notifyDataSetChanged();
    }

    class TouBaoHolder extends BaseRecyclerViewHolder<ItemQiuzhiJobBinding> {

        public TouBaoHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(BaseRecyclerModel object, int position) {
//            GetLabelsBean object1 = (GetLabelsBean) object;
//            binding.jobName.setText(((GetLabelsBean) object1).getLabels());
//            if (selectPositions.contains(position)){
//                binding.jobName.setBackgroundResource(R.drawable.bg_round_bq);
//            }else {
//                binding.jobName.setBackgroundResource(R.drawable.item_qiuzhi_job);
//            }
        }
    }
}
