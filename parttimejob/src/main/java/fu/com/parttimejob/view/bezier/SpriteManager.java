package fu.com.parttimejob.view.bezier;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * create by yao.cui at 2016/12/2
 */
public class SpriteManager {

    private WeakReference<Context> mContextRef;
    private static int mPWidth;
    private static int mPHeight;

    private boolean isOver;

    private ArrayList<BaseSprite> mSprites = new ArrayList<>();

    private static class SingleTonHolder {
        private static final SpriteManager INSTANCE = new SpriteManager();
    }

    public static SpriteManager getInstance() {
        return SingleTonHolder.INSTANCE;
    }
    
    private SpriteManager() {
    }

    public void init(Context context, int pwidth, int pheight){
        this.mPWidth = pwidth;
        this.mPHeight = pheight;

        isOver = false;
        this.mContextRef = new WeakReference<Context>(context);

    }

    /**
     * 添加红包精灵
     */

    public void addBoom(int x, int y){
        if (isOver){
            return;
        }
        BoomSprite boomSprite = new BoomSprite(mContextRef.get(), mPWidth, mPHeight);
        boomSprite.setPosition(x,y);
        mSprites.add(boomSprite);
    }


    /**
     * 更新倒计时时间
     * @param time
     */


    /**
     * 清除脏数据
     */
    public void cleanData(){
        List<BaseSprite> oldSprites = new ArrayList<>();

        for (int i = 0, size = mSprites.size(); i < size; i++){
            if (mSprites.get(i).isOver){
                oldSprites.add(mSprites.get(i));
            }
        }

        for (int i = 0, size=oldSprites.size();i<size;i++){
            oldSprites.get(i).recycle();
            mSprites.remove(oldSprites.get(i));
        }
    }

    /**
     * 判断坐标是否点击到某个精灵
     * @param x
     * @param y
     * @return
     */
    public BaseSprite isContains(float x, float y){
        for (int i = 0, size = mSprites.size(); i < size; i++){
            BaseSprite baseSpite = mSprites.get(i);
            if (baseSpite.isContains(x,y)&& baseSpite.clickable){
                return baseSpite;
            };
        }
        return null;
    }

    /**
     * 停止
     */
    public void stop(){
        isOver = true;
        recycle();
    }

    /**
     * 回收
     */
    public void recycle(){
        for (int i = 0, size = mSprites.size(); i< size; i++){
            mSprites.get(i).recycle();
        }
        mSprites.clear();

    }


}
