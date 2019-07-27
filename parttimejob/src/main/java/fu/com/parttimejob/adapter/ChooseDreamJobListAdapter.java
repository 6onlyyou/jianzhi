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

    /**
     * 多选
     * @param position
     */
    public void changeSelectPosition(int position){
        if (selectPositions.contains(Integer.valueOf(position))){
            selectPositions.remove(Integer.valueOf(position));
        }else {
            selectPositions.add(position);
        }
        notifyDataSetChanged();
    }

    /**
     * 单选
     * @param position
     */
    public void chooseAloneSelectPosition(int position){
        if (selectPositions.contains(Integer.valueOf(position))){
            selectPositions.remove(Integer.valueOf(position));
        }else {
            selectPositions.clear();
            selectPositions.add(position);
        }
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getSelectPositions(){
        return selectPositions;
    }
    class TouBaoHolder extends BaseRecyclerViewHolder<ItemQiuzhiJobBinding> {

        public TouBaoHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(BaseRecyclerModel object, int position) {
            GetLabelsBean object1 = (GetLabelsBean) object;
            binding.jobName.setText(object1.getLabels());
            if(object1.getLabelssel()==null){
                binding.jobName.setBackgroundResource(R.drawable.item_qiuzhi_job);
            }else{
                if(object1.getLabelssel()){
                    binding.jobName.setBackgroundResource(R.drawable.bg_round_bq);
                }else{
                    binding.jobName.setBackgroundResource(R.drawable.item_qiuzhi_job);
                }
            }

            if (selectPositions.contains(position)){
                binding.jobName.setBackgroundResource(R.drawable.bg_round_bq);
            }else {
                binding.jobName.setBackgroundResource(R.drawable.item_qiuzhi_job);
            }
        }
    }
}
