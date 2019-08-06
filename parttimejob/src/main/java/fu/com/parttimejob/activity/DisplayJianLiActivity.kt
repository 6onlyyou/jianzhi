package fu.com.parttimejob.activity

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.os.AsyncTask
import android.os.Environment
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.heixiu.errand.net.RetrofitFactory
import com.iceteck.silicompressorr.SiliCompressor
import com.lljjcoder.citylist.Toast.ToastUtils
import com.longsh.optionframelibrary.OptionCenterDialog
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.permissions.RxPermissions
import com.luck.picture.lib.tools.PictureFileUtils
import fu.com.parttimejob.R
import fu.com.parttimejob.adapter.GridImageAdapter
import fu.com.parttimejob.base.BaseActivity
import fu.com.parttimejob.bean.FileInfoEntilty
import fu.com.parttimejob.retrofitNet.RxUtils
import fu.com.parttimejob.utils.FullyGridLayoutManager
import fu.com.parttimejob.utils.SPUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_display_jian_li.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URISyntaxException
import java.util.*


class DisplayJianLiActivity : BaseActivity() {
    private var selectList: List<LocalMedia> = ArrayList()
    var dialogPro: ProgressDialog? = null
    private var adapter: GridImageAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_display_jian_li
    }

    override fun initViewParams() {
        dialogPro = ProgressDialog(this)
        dialogPro!!.setCanceledOnTouchOutside(false)
        dialogPro!!.setMessage("小二加载中，大人请稍后~")
        dialogPro!!.setOnKeyListener(object : View.OnKeyListener, DialogInterface.OnKeyListener {
            override fun onKey(p0: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK && event!!.getAction() == KeyEvent.ACTION_DOWN) {
//                    val rxBusEntity = RxBusEntity()
//                    rxBusEntity.msg = "77"
//                    RxBus.getDefault().post(rxBusEntity)
//                    dialogPro!!.dismiss()
//                    finish()
                    return true;
                } else {
                    return false; //默认返回 false
                }
            }
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                return false
            }

        })
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getResumeInfo(SPUtil.getString(this,"thirdAccount",""),SPUtil.getString(this,"thirdAccount",""))).subscribe({
            jianliname.setText(it.name)
            var sexs = ""
            if(it.sex ==1){
                 sexs = "男"
            }else{
                 sexs = "女"
            }
            jianlisex.setText(sexs)
            jianliage.setText(it.age)
            jianlijianjie.setText(it.personalProfile)
        }, {
        })
        pushjianli.setOnClickListener {
            if(selectList.size == 0){
                ToastUtils.showLongToast(applicationContext, "请上传图片或者视频来介绍自己~")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(jianliname.text) || TextUtils.isEmpty(jianlisex.text)|| TextUtils.isEmpty(jianliage.text)|| TextUtils.isEmpty(jianlijianjie.text)||TextUtils.isEmpty(phonenum.text)){
                ToastUtils.showLongToast(applicationContext, "请您把信息填写完整才能确定~")
            }else{
                dialogPro!!.show()
                if (selectList!!.get(0).path.contains(".rmvb") || selectList!!.get(0).path.contains(".avi") || selectList!!.get(0).path.contains(".mkv") || selectList!!.get(0).path.contains(".mov") || selectList!!.get(0).path.contains(".flv") || selectList!!.get(0).path.contains(".3gp") || selectList!!.get(0).path.contains(".mp4")) {
//                    val folder = File(CameraApp.getInstance().getAllSdPaths(this@TakePictureActivity)[0] + "/CameraJXD")
//                    if (!folder.exists()) {
//                        folder.mkdirs()
//                    }
//                    val picture = File(CameraApp.getInstance().getAllSdPaths(this@TakePictureActivity)[0] + "/CameraJXD/" + fname)
//
//                    Log.d("jxd", "path " + picture.getPath() + "getAbsolutePath " + picture.getAbsolutePath())
//                    try {
//                        val fos = FileOutputStream(picture.getPath()) // Get file output stream
//                        fos.write(params[0]) // Written to the file
//                        fos.close()
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
                    val f = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).toString() + "/compressor/videos")
                    if (f.mkdirs() || f.isDirectory)
                        VideoCompressAsyncTask(this).execute(selectList!!.get(0).path, f.path, fileInfoEntilty!!.get(0).filewidth, fileInfoEntilty!!.get(0).fileheight)
                }else{
                    val builder: MultipartBody.Builder = MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                    if (selectList!!.size < 1) {
                        builder.addFormDataPart("picOrVedio", File(selectList!!.get(0).compressPath).name, RequestBody.create(MediaType.parse("image/*"), File(selectList!!.get(0).compressPath)));
                    } else {
                        for (i in selectList!!.indices) {
                            builder.addFormDataPart("picOrVedio", File(selectList!!.get(0).path).name, RequestBody.create(MediaType.parse("image/*"), File(selectList!!.get(0).path)));
                        }
                    }
                    val requestBody: RequestBody = builder.build();
                    var sex = 0
                    if( jianlisex.text.toString().equals("男")){
                        sex = 1
                    }else{
                        sex = 2
                    }
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().createPR(SPUtil.getString(this, "thirdAccount", "111"), jianliname.text.toString(), sex,jianliage.text.toString(), jianlijianjie.text.toString(),requestBody,SPUtil.getString(this@DisplayJianLiActivity,"city","廊坊市"),phonenum.text.toString())).subscribe(Consumer<String> {
                        ToastUtils.showLongToast(applicationContext, it.toString())
                        dialogPro!!.dismiss()
                        finish()
                    }, Consumer<Throwable> {
                        dialogPro!!.dismiss()
                        ToastUtils.showLongToast(applicationContext, it.message.toString())
                    })
                }


            }

        }
    }


private var themeId: Int = 0
override fun initViewClick() {
    jianlisex.setOnClickListener {
        val list = java.util.ArrayList<String>()
        list.add("男")
        list.add("女")
        val optionCenterDialog = OptionCenterDialog()
        optionCenterDialog.show(this, list)
        optionCenterDialog.setItemClickListener { parent, view, position, id ->
                if(position == 0){
                    jianlisex.setText("男")
                }else if(position == 1){
                    jianlisex.setText("女")
                }
            optionCenterDialog.dismiss() }
    }
    themeId = R.style.picture_default_style
    val manager = FullyGridLayoutManager(this@DisplayJianLiActivity, 1, GridLayoutManager.VERTICAL, false)
    recyclerView.setLayoutManager(manager)
    adapter = GridImageAdapter(this@DisplayJianLiActivity, onAddPicClickListener)
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
                        PictureSelector.create(this@DisplayJianLiActivity).themeStyle(themeId).openExternalPreview(position, selectList)
                    2 ->
                        // 预览视频
                        PictureSelector.create(this@DisplayJianLiActivity).externalPictureVideo(media.path)
                    3 ->
                        // 预览音频
                        PictureSelector.create(this@DisplayJianLiActivity).externalPictureAudio(media.path)
                }
            }
        }
    })

    // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
    val permissions = RxPermissions(this)
    permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(object : Observer<Boolean> {
        override fun onNext(aBoolean: Boolean) {
            if (aBoolean!!) {
                PictureFileUtils.deleteCacheDirFile(this@DisplayJianLiActivity)
            } else {
                Toast.makeText(this@DisplayJianLiActivity,
                        getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show()
            }
        }
//
        override fun onSubscribe(d: Disposable) {}


        override fun onError(e: Throwable) {}

        override fun onComplete() {}
    })
}

private val onAddPicClickListener = object : GridImageAdapter.onAddPicClickListener {
    override fun onAddPicClick() {
        PictureSelector.create(this@DisplayJianLiActivity)
                .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
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
                .openClickSound(true)// 是否开启点击声音
//                   .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                //                        .videoMaxSecond(15)
                //                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .cropCompressQuality(90)// 裁剪压缩质量 默认100
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

                getPlayTime(selectList.get(0).path, 0)
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
        }
    }
}
    var fileInfoEntilty: ArrayList<FileInfoEntilty>? = null
    fun getPlayTime(mUri: String, type: Int): String {
        fileInfoEntilty = ArrayList<FileInfoEntilty>()
        HashMap<String, String>()
        val mmr = MediaMetadataRetriever();
        try {
            val fileInfo = FileInfoEntilty()
            mmr.setDataSource(mUri);
            val duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
            val width = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);//宽
            val height = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);//高
            fileInfo.filewidth = width
            fileInfo.fileheight = height
            fileInfoEntilty!!.add(fileInfo)
//            Toast.makeText(this, "playtime:" + duration + "w=" + width + "h=" + height, Toast.LENGTH_SHORT).show();
            Log.e("duration", duration);

            return duration;
        } catch (ex: Exception) {
            Log.e("duration", ex.toString());
        } finally {
            mmr.release();
        }

        return "";
    }
    internal inner class VideoCompressAsyncTask(var mContext: Context) : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg paths: String): String? {
            var filePath: String? = null
            var width: Int? = paths[2].toInt()
            var hight: Int? = paths[3].toInt()
            try {
                if (paths[2].toInt() > 300) {
                    if (paths[2].toInt() > 1000) {
                        hight = paths[3].toInt() - 200
                        width = paths[2].toInt() - 200
                    } else {
                        hight = paths[3].toInt() - 100
                        width = paths[2].toInt() - 100
                    }
                }
                filePath = SiliCompressor.with(mContext).compressVideo(paths[0], paths[1], width!!, hight!!, 1800000)

            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            return filePath
        }


        override fun onPostExecute(compressedFilePath: String) {
            super.onPostExecute(compressedFilePath)
                val builder: MultipartBody.Builder = MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                if (selectList!!.size < 1) {
                    builder.addFormDataPart("picOrVedio", File(compressedFilePath).name, RequestBody.create(MediaType.parse("image/*"), File(compressedFilePath)));
                } else {
                    for (i in selectList!!.indices) {
                        builder.addFormDataPart("picOrVedio", File(compressedFilePath).name, RequestBody.create(MediaType.parse("image/*"), File(compressedFilePath)));
                    }
                }
                val requestBody: RequestBody = builder.build();
                var sex = 0
                if( jianlisex.text.toString().equals("男")){
                    sex = 1
                }else{
                    sex = 2
                }
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().createPR(SPUtil.getString(this@DisplayJianLiActivity, "thirdAccount", "111"), jianliname.text.toString(), sex,jianliage.text.toString(), jianlijianjie.text.toString(),requestBody,SPUtil.getString(this@DisplayJianLiActivity,"city","廊坊市"),phonenum.text.toString())).subscribe(Consumer<String> {
                    ToastUtils.showLongToast(applicationContext, it.toString())
                    dialogPro!!.dismiss()
                    finish()
                }, Consumer<Throwable> {
                    dialogPro!!.dismiss()
                    ToastUtils.showLongToast(applicationContext, it.message.toString())
                })

            }
        }
}
