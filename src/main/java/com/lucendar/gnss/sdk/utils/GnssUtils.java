package com.lucendar.gnss.sdk.utils;

import com.lucendar.common.utils.CommonUtils;

public class GnssUtils {

    /**
     * 创建一个令牌。
     *
     * @return 新的令牌字符串。
     */
    public static String newToken() {
        return CommonUtils.uuidBase64NoPadding();
    }

}
