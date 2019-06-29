package fu.com.parttimejob.activity

import fu.com.parttimejob.R
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.dialog.abelPopWindowL
import kotlinx.android.synthetic.main.activity_job.*
import android.graphics.Color
import com.lljjcoder.citypickerview.widget.CityPicker


class JobActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_job
    }

    override fun initViewParams() {
    }

    override fun initViewClick() {
        job_city.setOnClickListener {
            selectAddress()
        }
        job_work.setOnClickListener {
            val addPopWindow = abelPopWindowL(this@JobActivity)
            addPopWindow.showPopupWindow(job_work)
        }


    }
    private fun selectAddress() {
        val cityPicker = CityPicker.Builder(this@JobActivity)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(false)
                .cityCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(true)
                .build()
        cityPicker.show()
        cityPicker.setOnCityItemClickListener(object :CityPicker.OnCityItemClickListener{
            override fun onSelected(vararg citySelected: String?) {
                val province = citySelected[0]
                //城市
                val city = citySelected[1]
                //区县（如果设定了两级联动，那么该项返回空）
                cityname.setText(city)
            }

            override fun onCancel() {
            }

        })
            //监听方法，获取选择结果
    }
}
