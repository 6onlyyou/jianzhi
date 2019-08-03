package fu.com.parttimejob.bean;

import android.os.Parcel;
import android.os.Parcelable;

import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;

/**
 * Description:
 * Data：2019/7/14-0:57
 * Author: fushuaige
 */
public class ExchangeBean  extends BaseRecyclerModel implements Parcelable {
    private String id;
    private String goodsName;
    private String goodsImg;
    private int goodsPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.goodsName);
        dest.writeString(this.goodsImg);
        dest.writeInt(this.goodsPrice);
    }

    public ExchangeBean() {
    }

    protected ExchangeBean(Parcel in) {
        this.id = in.readString();
        this.goodsName = in.readString();
        this.goodsImg = in.readString();
        this.goodsPrice = in.readInt();
    }

    public static final Parcelable.Creator<ExchangeBean> CREATOR = new Parcelable.Creator<ExchangeBean>() {
        @Override
        public ExchangeBean createFromParcel(Parcel source) {
            return new ExchangeBean(source);
        }

        @Override
        public ExchangeBean[] newArray(int size) {
            return new ExchangeBean[size];
        }
    };
}
