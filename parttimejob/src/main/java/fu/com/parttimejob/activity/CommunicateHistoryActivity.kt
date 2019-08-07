package fu.com.parttimejob.activity

import android.content.Intent
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import com.yanzhenjie.recyclerview.OnItemMenuClickListener
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.JobAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import fu.com.parttimejob.bean.JobInfoBean
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.SPUtil
import kotlinx.android.synthetic.main.activity_communicate_history.*


class CommunicateHistoryActivity : BaseActivity() {

    companion object {
        val CommuniHistory = "沟通历史"
        val PublishHistory = "我的发布"
    }

    lateinit var homeJobListAdapter: JobAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_communicate_history
    }

    override fun initViewParams() {


        titleTv.text = "沟通历史"

        back.setOnClickListener {
            finish()
        }

        homeJobListAdapter = JobAdapter()
        swipeList.layoutManager = LinearLayoutManager(this)

        var mSwipeMenuCreator: SwipeMenuCreator = SwipeMenuCreator { leftMenu, rightMenu, position ->
            var deleteItem: SwipeMenuItem = SwipeMenuItem(this@CommunicateHistoryActivity)
            deleteItem.text = "刪除"
            deleteItem.height = 213
            deleteItem.width = 216
            deleteItem.setTextColor(ContextCompat.getColor(this, R.color.colorRed))
            deleteItem.setBackground(R.drawable.delete_shape)
            rightMenu?.addMenuItem(deleteItem) // 在Item左侧添加一个菜单。

        }

        swipeList.setSwipeMenuCreator(mSwipeMenuCreator)
        swipeList.addItemDecoration(SpaceItemDecoration(24, 45, 45))


        // 菜单点击监听。
        val mItemMenuClickListener = OnItemMenuClickListener { menuBridge, position ->
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu()
        }
        swipeList.setOnItemMenuClickListener(mItemMenuClickListener)

        swipeList.adapter = homeJobListAdapter

        var list: ArrayList<BaseRecyclerModel> = ArrayList()
        homeJobListAdapter.addAll(list)


//        if (activity_title.equals(CommuniHistory)) {
            queryCommunicationRecord()
//        }



        homeJobListAdapter.setOnItemClickListener { view, t, position ->
            val intent = Intent(view.context, JobInfoActivity::class.java)
            intent.putExtra("id", (homeJobListAdapter.data.get(position) as JobInfoBean).id)
            view.context.startActivity(intent)
        }

    }

    private fun queryPublishRecord() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().myRecruitmentList(SPUtil.getString(this, "thirdAccount", ""))).subscribe({
            homeJobListAdapter.addAll(it as List<BaseRecyclerModel>?)
            homeJobListAdapter.notifyDataSetChanged()
        }, {
            ToastUtils.showLongToast(this, it.message.toString())
        })
    }

    private fun queryCommunicationRecord() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryCommunicationRecord(SPUtil.getString(this, "thirdAccount", ""))).subscribe({
            homeJobListAdapter.clear();
            homeJobListAdapter.notifyDataSetChanged() ;
            homeJobListAdapter.addAll(it as List<BaseRecyclerModel>?)
            homeJobListAdapter.notifyDataSetChanged()
            if(it.size<1){
                konkonshuj.visibility = View.VISIBLE
            }else{
                konkonshuj.visibility = View.GONE
            }
        }, {
            ToastUtils.showLongToast(this, it.message.toString())
        })

    }

    override fun initViewClick() {
    }

    internal inner class SpaceItemDecoration(var bottom: Int, var left: Int, var right: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = bottom
            outRect.left = left
            outRect.right = right
        }
    }
}
