package com.lucendar.gnss.sdk.db;

import com.lucendar.common.utils.CommonUtils;

public class UuidRecIdProvider implements RecIdProvider {

    @Override
    public String nextId() {
        return CommonUtils.uuidBase64NoPadding();
    }

}
