package fu.com.parttimejob.bean;

public class BannerInfo {

    /**
     * id : 1
     * imgAddress : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565601475194&di=97d62229350a54e7195eed5e4b2233b6&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F014d0f58a1683ba8012060c8560786.jpg%401280w_1l_2o_100sh.jpg
     * netAddress : www.baidu.com
     * title : 购物狂欢节
     */

    private int id;
    private String imgAddress;
    private String netAddress;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getNetAddress() {
        return netAddress;
    }

    public void setNetAddress(String netAddress) {
        this.netAddress = netAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
