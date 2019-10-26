package fu.com.parttimejob.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lljjcoder.citylist.Toast.ToastUtils;

import fu.com.parttimejob.R;
import fu.com.parttimejob.activity.MyMoneyActivity;
import fu.com.parttimejob.activity.ShouHuoActivity;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewAdapter;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerViewHolder;
import fu.com.parttimejob.bean.ExchangeBean;
import fu.com.parttimejob.databinding.ItemExchangeShopBinding;
import fu.com.parttimejob.dialog.HintDialog;
import fu.com.parttimejob.dialog.ProtocolDialog;
import fu.com.parttimejob.utils.GlideUtil;
import fu.com.parttimejob.utils.SPUtil;


public class ExchangeShopListAdapter extends BaseRecyclerViewAdapter {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TouBaoHolder(parent, R.layout.item_exchange_shop);
    }


    class TouBaoHolder extends BaseRecyclerViewHolder<ItemExchangeShopBinding> {
        public TouBaoHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }
        @Override
        public void onBindViewHolder(BaseRecyclerModel object, int position) {
            final ExchangeBean exchangeBean = (ExchangeBean) object;
            Glide.with(binding.getRoot().getContext())
                    .load(exchangeBean.getGoodsImg())
                    .placeholder(R.mipmap.defind)
                    .into( (ImageView) binding.skuImgIv);
            binding.shopName.setText(exchangeBean.getGoodsName());
            binding.zan.setText("需要" + exchangeBean.getGoodsPrice() + "金币");
            binding.layclack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(SPUtil.getString(binding.getRoot().getContext(),"protocol","").equals("")){
                        new ProtocolDialog(binding.getRoot().getContext(), R.style.dialog, "商品退换规定：\n" +
                                "本规定依据《中华人民共和国消费者权益保护法》、《中华人民共和国产品质量法》及其它相关规定制定。凡是在我们的网站兑换商品的客户，请仔细阅读此规定的详细介绍。如果产品页面无特殊退换货规定，则将按以下条款执行。 我们兑换商品全部享受质量保证，顾客一旦发现任何质量问题，请立刻直接与我站取得联系。 我们保证严格按《中华人民共和国消费者权益保护法》、《中华人民共和国产品质量法》及其它相关规定制定受理退换货。 特别提示：\n" +
                                "在退换货前，请您事先通过电话、e-mail或qq、微信的方式与我站取得联系，（必要时请用邮件提供实物图片，以便我们更快为您解决问题）我站视具体情况尽快妥善为您解决您 的问题。\n" +
                                "1、所退商品必须是出现非人为的质量问题，（兑换商品不支持无理由退换条例）要求具备商品完整的外包装、附件（比如：随商品的赠品等）、说明书、发货清单、退货原因详细的说明。；请注明您的姓名、地址、订单号码及详细的退货原因。 \n" +
                                "2、用户所兑换商品到达之日起,7日内发现有如下问题问题,我们无条件给您退货：\n" +
                                "非人为碎裂的质量问题。 非人为水钻脱落的质量问题。 非人为配件脱落的质量问题。 温馨提示：\n" +
                                "a.您兑换产品到家时，外包装是否完整？我们承诺货物发出时外包装完好无缺。如商品在配送过程中产生脏残破损，我们将协助您向配送机构进行索赔。 b.如果经过更换的商品还存在质量问题，您可以选择退货或再次换货。\n" +
                                "3、出现下列任何一种情况的用户将不能享受我们的网站退货承诺：\n" +
                                "产品曾被非正常使用； 超过退换货期限的退换货要求；人为造成的产品质量问题。非正常条件下存储； 非正常条件下误用、疏忽、滥用、事故、改动; 不可抗力导致的损坏；客户在退货之前未与我们的网站取得联系； 退回产品外包装不完整; 退回产品的配件及所附资料不全; 退回产品的发货清单丢失; 产品并非由我们的网站提供的产品。我站视代收人等同于订购人进行验货，否则出现非质量问题本站不负责退换。请您在收到货物时当场验货，如果发现货物在运输途中破损，您有权让配送人员拿回货物重新送或者要求递送机构出示证明，向递送机构索赔。 当您确认符合我们商品退换的条件时，请尽快和我们的客户服务中心进行联系，然后将货物邮寄往我们的退货地址。当我们收到所退商品，会转交质量检验部核定并记录，然后依照您的要求将所应调换商品寄出，或将账户金币退给客户，作为下一次购买兑换使用。 退货制度 \n" +
                                "1、请您在退换货之前与本站取得联系,否则本站有权拒绝没有任何原因说明的退货要求。您可以采用电话、邮件等方式协商处理。 \n" +
                                "2、商品一旦兑换售出后，我们原则上不接受换货,除非有以下情况：\n" +
                                "所收到商品的实物与所订货物描述严重不符。 所收到商品有明显的质量问题。(此条也可要求退货) \n" +
                                "3、原则上,要求换货的商品,我们在顾客下次订购时一并发出,如顾客要求我站收到退回商品时即补发,由此产生的运输费用由顾客承担.本站兑换产品退换货的原则将依据《中华人民共和国消费者权益保护法》 和《中华人民共和国产品质量法》的细则处理。 \n", new ProtocolDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    SPUtil.putString(binding.getRoot().getContext(), "protocol", "1");
                                    new HintDialog(binding.getRoot().getContext(), R.style.dialog, "需要支付" + exchangeBean.getGoodsPrice() + "个金币是否继续？", new HintDialog.OnCloseListener() {
                                        @Override
                                        public void onClick(Dialog dialog, boolean confirm) {
                                            if (confirm) {
                                                if (exchangeBean.getGoodsPrice() > SPUtil.getInt(binding.getRoot().getContext(), "totalCount", 0)) {
                                                    ToastUtils.showLongToast(binding.getRoot().getContext(), "余额不足请充值");
                                                    Intent i = new Intent(binding.getRoot().getContext(), MyMoneyActivity.class);
                                                    binding.getRoot().getContext().startActivity(i);
                                                } else {
                                                    Intent i = new Intent(binding.getRoot().getContext(), ShouHuoActivity.class);
                                                    i.putExtra("exchangeBean", exchangeBean);
                                                    binding.getRoot().getContext().startActivity(i);
                                                }
                                            }
                                            dialog.dismiss();
                                        }
                                    }).show();
                                }
                                dialog.dismiss();
                            }
                        }).show();
                    }else {
                        new HintDialog(binding.getRoot().getContext(), R.style.dialog, "需要支付" + exchangeBean.getGoodsPrice() + "个金币是否继续？", new HintDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    if (exchangeBean.getGoodsPrice() > SPUtil.getInt(binding.getRoot().getContext(), "totalCount", 0)) {
                                        ToastUtils.showLongToast(binding.getRoot().getContext(), "余额不足请充值");
                                        Intent i = new Intent(binding.getRoot().getContext(), MyMoneyActivity.class);
                                        binding.getRoot().getContext().startActivity(i);
                                    } else {
                                        Intent i = new Intent(binding.getRoot().getContext(), ShouHuoActivity.class);
                                        i.putExtra("exchangeBean", exchangeBean);
                                        binding.getRoot().getContext().startActivity(i);
                                    }
                                }
                                dialog.dismiss();
                            }
                        }).show();
                    }

                }
            });
        }
    }
}
