package fu.com.parttimejob.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.amap.api.services.core.PoiItem
import com.heixiu.errand.net.RetrofitFactory
import com.lljjcoder.citylist.Toast.ToastUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.GridImageAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.Pickers
import fu.com.parttimejob.dialog.HintDialog
import fu.com.parttimejob.dialog.abelPopWindowL
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.FullyGridLayoutManager
import fu.com.parttimejob.utils.RegexUtils
import fu.com.parttimejob.utils.SPUtil
import fu.com.parttimejob.view.PickerScrollView.onSelectListener
import kotlinx.android.synthetic.main.activity_publish_job.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


class PublishJobActivity : BaseActivity() {
    private var selectList: List<LocalMedia> = ArrayList()
    //    private val pickerscrlllview: PickerScrollView? = null // 滚动选择器
    private var adapter: GridImageAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_publish_job
    }

    override fun initViewParams() {

    }

    private var themeId: Int = 0
    override fun initViewClick() {
        initData()
        themeId = R.style.picture_default_style
        picker_yes.setOnClickListener(onClickListener);
        pushjianli.setOnClickListener(onClickListener);
        styles.setOnClickListener(onClickListener);
        back.setOnClickListener {
            finish()
        }

//        pickerscrlllview.setOnSelectListener(pickerListener);
        val manager = FullyGridLayoutManager(this@PublishJobActivity, 1, GridLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(manager)

        adapter = GridImageAdapter(this@PublishJobActivity, onAddPicClickListener)
        adapter!!.setList(selectList)
        adapter!!.setSelectMax(1)
        recyclerView.setAdapter(adapter)
        adapter!!.setOnItemClickListener(object : GridImageAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, v: View) {
                if (selectList.size > 0) {
                    val media = selectList[position]
                    val pictureType = media.pictureType

                    val mediaType = PictureMimeType.pictureToVideo(pictureType)
                    when (mediaType) {
                        1 ->
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(this@PublishJobActivity).themeStyle(themeId).openExternalPreview(position, selectList)
                        2 ->
                            // 预览视频
                            PictureSelector.create(this@PublishJobActivity).externalPictureVideo(media.path)
                        3 ->
                            // 预览音频
                            PictureSelector.create(this@PublishJobActivity).externalPictureAudio(media.path)
                    }
                }
            }
        })

        location.setOnClickListener {
            startActivityForResult(Intent(this@PublishJobActivity, ChosseMapPositionActivity::class.java), CHOOSE_LOCATION)
        }
    }


    private var list: ArrayList<Pickers>? = null // 滚动选择器数据
    private var id: ArrayList<String>? = null
    private var name: ArrayList<String>? = null
    /**
     * 初始化数据
     */
    private fun initData() {
        var strarr: List<String>
        var lists: ArrayList<String> = ArrayList()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getLabel()).subscribe({

            if (it.labels != null && !it.labels.equals("")) {
                strarr = it.labels.substring(0, it.labels.length).split(",")
                var index = 0;
                while (index < strarr.size - 1) {
                    index++//自增
                    lists.add(strarr[index])
                }
                name = lists
                list = ArrayList<Pickers>()
                for (i in 0 until name!!.size) {
                    list!!.add(Pickers(name!![i], i.toString()))
                }
                // 设置数据，默认选择第一条
//                pickerscrlllview.setData(list)
//                pickerscrlllview.setSelected(0)
            }
        }, {
            ToastUtils.showLongToast(applicationContext, it.message.toString())
        })
    }

    // 滚动选择器选中事件
//    var pickerListener: onSelectListener = onSelectListener { pickers ->
//        styles.setText(pickers.showConetnt)
//    }

    // 点击监听事件
    var onClickListener: View.OnClickListener = object : View.OnClickListener {

        override fun onClick(v: View) {
            if (v === styles) {
                val addPopWindow = abelPopWindowL(this@PublishJobActivity)
                addPopWindow.showPopupWindow(styles) { jobName ->
                    styles.text = if (TextUtils.isEmpty(jobName)) {
                        ""
                    } else {
                        jobName
                    }

                }
//                picker_rel.visibility = View.VISIBLE
            } else if (v === pushjianli) {

                if (Integer.parseInt(money.text.toString()) < 10) {
                    showToast("红包金额不能小于10金币")
                    return
                }
                if (Integer.parseInt(size.text.toString()) < 1) {
                    showToast("红包数量至少1个")
                    return
                }
                if(!RegexUtils.isBasePhone(phone.text.toString())){
                    showToast("请输入正确的手机号")
                    return
                }

                if (TextUtils.isEmpty(nameEt.text) || TextUtils.isEmpty(styles.text) || TextUtils.isEmpty(money.text) || TextUtils.isEmpty(size.text) || TextUtils.isEmpty(sizepe.text) || TextUtils.isEmpty(salary.text) || TextUtils.isEmpty(workTime.text) || TextUtils.isEmpty(phone.text.toString())  || TextUtils.isEmpty(jianlijianjie.text)) {
                    showToast("您的信息未填写完整~")
                } else {
                    if (Integer.parseInt(money.text.toString()) < Integer.parseInt(size.text.toString())) {
                        showToast("红包数量不能高于金币数量~")
                    } else {
                        HintDialog(this@PublishJobActivity, R.style.dialog, "需要支付" + money.text.toString() + "个金币是否继续？", object : HintDialog.OnCloseListener {
                            override fun onClick(dialog: Dialog, confirm: Boolean) {
                                if (confirm) {
                                    if (Integer.parseInt(money.text.toString()) > SPUtil.getInt(this@PublishJobActivity, "totalCount", 0)) {
                                        showToast("金币不足请充值")
                                        startActivity(Intent(this@PublishJobActivity, MyMoneyActivity::class.java))
                                    } else {
                                        val builder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                                        var requestBody: RequestBody
                                        if (selectList.isNotEmpty()) {
                                            builder.addFormDataPart("img", File(selectList!!.get(0).compressPath).name, RequestBody.create(MediaType.parse("image/*"), File(selectList!!.get(0).compressPath)));
                                            requestBody = builder.build()
                                            if (poiItem== null ) {
                                                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().publichInfo(SPUtil.getString(this@PublishJobActivity, "thirdAccount", "111"), nameEt.text.toString(), styles.text.toString(), money.text.toString(), size.text.toString(), sizepe.text.toString(), salary.text.toString(), phone.text.toString(), detailLocation.text.toString(), jianlijianjie.text.toString(), requestBody, SPUtil.getString(this@PublishJobActivity, "city", ""), workTime.text.toString())).subscribe({
                                                    ToastUtils.showLongToast(this@PublishJobActivity, it)
                                                    SPUtil.putInt(this@PublishJobActivity, "totalCount", SPUtil.getInt(this@PublishJobActivity, "totalCount", 0) - Integer.parseInt(money.text.toString()))
                                                    finish()
                                                }, {
                                                    ToastUtils.showLongToast(this@PublishJobActivity, it.message.toString())
                                                })
                                            }else{
                                                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().publichInfo(SPUtil.getString(this@PublishJobActivity, "thirdAccount", "111"), nameEt.text.toString(), styles.text.toString(), money.text.toString(), size.text.toString(), sizepe.text.toString(), salary.text.toString(), phone.text.toString(), detailLocation.text.toString(), poiItem!!.latLonPoint.longitude.toString(), poiItem!!.latLonPoint.latitude.toString(), jianlijianjie.text.toString(), requestBody, SPUtil.getString(this@PublishJobActivity, "city", ""), workTime.text.toString())).subscribe({
                                                    ToastUtils.showLongToast(this@PublishJobActivity, it)
                                                    SPUtil.putInt(this@PublishJobActivity, "totalCount", SPUtil.getInt(this@PublishJobActivity, "totalCount", 0) - Integer.parseInt(money.text.toString()))
                                                    finish()
                                                }, {
                                                    ToastUtils.showLongToast(this@PublishJobActivity, it.message.toString())
                                                })
                                            }

                                        } else {
                                            if (poiItem== null ) {
                                                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().publichNoImgInfo(SPUtil.getString(this@PublishJobActivity, "thirdAccount", "111"), nameEt.text.toString(), styles.text.toString(), money.text.toString(), size.text.toString(), sizepe.text.toString(), salary.text.toString(), phone.text.toString(), detailLocation.text.toString(),  jianlijianjie.text.toString(), SPUtil.getString(this@PublishJobActivity, "city", ""), workTime.text.toString())).subscribe({
                                                    ToastUtils.showLongToast(this@PublishJobActivity, it)
                                                    SPUtil.putInt(this@PublishJobActivity, "totalCount", SPUtil.getInt(this@PublishJobActivity, "totalCount", 0) - Integer.parseInt(money.text.toString()))
                                                    finish()
                                                }, {
                                                    ToastUtils.showLongToast(this@PublishJobActivity, it.message.toString())
                                                })
                                            } else {
                                                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().publichNoImgInfo(SPUtil.getString(this@PublishJobActivity, "thirdAccount", "111"), nameEt.text.toString(), styles.text.toString(), money.text.toString(), size.text.toString(), sizepe.text.toString(), salary.text.toString(), phone.text.toString(), detailLocation.text.toString(), poiItem!!.latLonPoint.longitude.toString(), poiItem!!.latLonPoint.latitude.toString(), jianlijianjie.text.toString(), SPUtil.getString(this@PublishJobActivity, "city", ""), workTime.text.toString())).subscribe({
                                                    ToastUtils.showLongToast(this@PublishJobActivity, it)
                                                    SPUtil.putInt(this@PublishJobActivity, "totalCount", SPUtil.getInt(this@PublishJobActivity, "totalCount", 0) - Integer.parseInt(money.text.toString()))
                                                    finish()
                                                }, {
                                                    ToastUtils.showLongToast(this@PublishJobActivity, it.message.toString())
                                                })
                                            }

                                        }
                                    }

                                } else {

                                }
                                dialog.dismiss()
                            }
                        })
                                .setTitle("").show()
                    }


//                    else {
//                        for (i in selectList.indices) {
//                            builder.addFormDataPart("businessLicenseImg", File(selectList!!.get(0).compressPath).name, RequestBody.create(MediaType.parse("image/*"), File(selectList!!.get(0).compressPath)));
//                        }
//                    }

                }

            } else if (v === picker_yes) {
                picker_rel.visibility = View.GONE
            }
        }
    }
    private val onAddPicClickListener = object : GridImageAdapter.onAddPicClickListener {
        override fun onAddPicClick() {
            PictureSelector.create(this@PublishJobActivity)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(1)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(
                            PictureConfig.SINGLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .previewVideo(true)// 是否可预览视频
                    .enablePreviewAudio(true) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                    .isGif(true)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(true)// 是否圆形裁剪
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(false)// 是否开启点击声音
//                   .selectionMedia(selectList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                    //                        .videoMaxSecond(15)
                    //                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    //.recordVideoSecond()//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data)
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的

                    for (media in selectList) {
                        Log.i("图片-----》", media.getPath())
                    }
                    adapter!!.setList(selectList)
                    adapter!!.notifyDataSetChanged()
                }
                CHOOSE_LOCATION -> {
                    poiItem = data?.getParcelableExtra("location")
                    location.text = poiItem?.title

                }
            }
        }


    }

    var poiItem: PoiItem? = null
    val CHOOSE_LOCATION = 10000

}
