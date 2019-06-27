package fu.com.parttimejob.activity

import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.yanzhenjie.recyclerview.OnItemMenuClickListener
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.HomeJobListAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel
import kotlinx.android.synthetic.main.activity_communicate_history.*


class CommunicateHistoryActivity : BaseActivity() {

    lateinit var adapter: HomeJobListAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_communicate_history
    }

    override fun initViewParams() {
        back.setOnClickListener({
            finish()
        })

        adapter = HomeJobListAdapter()
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

        swipeList.adapter = adapter

        var list: ArrayList<BaseRecyclerModel> = ArrayList()
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        list.add(BaseRecyclerModel())
        adapter.addAll(list)
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