/*******************************************************************************
 *  Copyright (c) 2019, 2022 lucendar.com.
 *  All rights reserved.
 *
 *  Contributors:
 *     KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 *******************************************************************************/
package com.lucendar.gnss.sdk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lucendar.common.Consts;
import com.lucendar.strm.common.StreamingApi;
import info.gratour.jt808common.mat.TermCmdMaterializer;
import info.gratour.jt808common.mat.TermCmdStateChangedMaterializer;
import info.gratour.jt808common.spi.model.TermCmd;
import info.gratour.jt808common.spi.model.TermCmdStateChanged;

import java.util.ArrayList;

public class GnssConsts {

    public static GsonBuilder GSON_BUILDER() {
        return Consts.defaultGsonBuilder()
                .registerTypeAdapter(TermCmd.class, new TermCmdMaterializer())
                .registerTypeAdapter(TermCmdStateChanged.class, new TermCmdStateChangedMaterializer());
    }

    public static final Gson GSON = GSON_BUILDER().create();
    public static final Gson GSON_PRETTY = GSON_BUILDER().setPrettyPrinting().create();


    public static final String DEFAULT_APP_ID = StreamingApi.DEFAULT_APP_ID;

    public static class Features {

        private final boolean externalGateway;
        private final boolean batchOnlineTermsQryDisabled;
        private final boolean attrsQry;
        private final boolean avAttrsQry;
        private final boolean noQryLocationCmd;


        public static final String FEATURE__EXTERNAL_GATEWAY = "externalGateway";

        public static final String FEATURE__BATCH_ONLINE_TERMS_QRY_DISABLED = "batchOnlineTermsQryDisabled";

        /**
         * Whether terminal attributes query (8107) is supported.
         */
        public static final String FEATURE__ATTRS_QRY = "attrsQry";

        /**
         * Whether A/V attribute query (9003) is supported.
         */
        public static final String FEATURE__AV_ATTRS_QRY = "avAttrsQry";

        public static final String FEATURE__NO_QRY_LOCATE_CMD = "noQryLocateCmd";

        private final String[] features;

        public Features(
                boolean externalGateway,
                boolean batchOnlineTermsQryDisabled,
                boolean attrsQry,
                boolean avAttrsQry,
                boolean noQryLocationCmd) {
            this.externalGateway = externalGateway;
            this.batchOnlineTermsQryDisabled = batchOnlineTermsQryDisabled;
            this.attrsQry = attrsQry;
            this.avAttrsQry = avAttrsQry;
            this.noQryLocationCmd = noQryLocationCmd;

            var list = new ArrayList<String>();
            if (externalGateway)
                list.add(FEATURE__EXTERNAL_GATEWAY);
            if (batchOnlineTermsQryDisabled)
                list.add(FEATURE__BATCH_ONLINE_TERMS_QRY_DISABLED);
            if (attrsQry)
                list.add(FEATURE__ATTRS_QRY);
            if (avAttrsQry)
                list.add(FEATURE__AV_ATTRS_QRY);
            if (noQryLocationCmd)
                list.add(FEATURE__NO_QRY_LOCATE_CMD);

            features = list.toArray(new String[0]);
        }

        public boolean externalGateway() {
            return externalGateway;
        }

        public boolean batchOnlineTermsQryDisabled() {
            return batchOnlineTermsQryDisabled;
        }

        public boolean attrsQry() {
            return attrsQry;
        }

        public boolean avAttrsQry() {
            return avAttrsQry;
        }

        public boolean noQryLocationCmd() {
            return noQryLocationCmd;
        }

        public String[] features() {
            String[] r = new String[features.length];
            System.arraycopy(features, 0, r, 0, features.length);
            return r;
        }
    }

    /**
     * 常见终端制造商ID
     */
    public static class WellKnownFactories {

        /**
         * 亿程
         */
        public static final String YI_CHENG = "70101";

        /**
         * 华宝
         */
        public static final String HUA_BAO = "70107";

        /**
         * 伊爱
         */
        public static final String YI_AI = "70108";

        /**
         * 联和安业
         */
        public static final String LIAN_HE_AN_YE = "70110";

        /**
         * 博实结
         */
        public static final String BO_SHI_JIE = "70111";

        /**
         * 赛格
         */
        public static final String SAI_GE = "70112";

        /**
         * 慧视通
         */
        public static final String HUI_SHI_TONG = "70113";

        /**
         * 雅迅
         */
        public static final String YA_XUN = "70118";

        /**
         * 有为
         */
        public static final String YOU_WEI = "70218";

        /**
         * 易流
         */
        public static final String YI_LIU = "70220";

        /**
         * 飞田
         */
        public static final String FEI_TIAN = "70304";

        /**
         * 首航
         */
        public static final String SHOU_HANG = "70465";

        /**
         * 锐明
         */
        public static final String RUI_MING = "70504";

        /**
         * 通立
         */
        public static final String TONG_LI = "72302";

        public static String shortName(String factoryId) {
            if (factoryId == null)
                return null;

            return switch (factoryId) {
                case YI_CHENG -> "YiCheng";
                case HUA_BAO -> "HuaBao";
                case YI_AI -> "YiAi";
                case LIAN_HE_AN_YE -> "LianHeAY";
                case BO_SHI_JIE -> "BoShiJie";
                case SAI_GE -> "SiaGe";
                case HUI_SHI_TONG -> "HuiShiTong";
                case YA_XUN -> "YaXun";
                case YOU_WEI -> "YouWei";
                case YI_LIU -> "YiLiu";
                case FEI_TIAN -> "FeiTian";
                case SHOU_HANG -> "ShouHang";
                case RUI_MING -> "RuiMing";
                case TONG_LI -> "TongLi";
                default -> factoryId;
            };
        }
    }
}
