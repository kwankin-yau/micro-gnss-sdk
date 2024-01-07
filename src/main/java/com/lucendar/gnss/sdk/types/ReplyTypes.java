/*
 * Copyright (c) 2024 lucendar.com.
 * All rights reserved.
 */
package com.lucendar.gnss.sdk.types;

import com.google.gson.reflect.TypeToken;
import com.lucendar.strm.common.strm.KeepStrmReqResult;
import info.gratour.common.types.rest.Reply;

import java.lang.reflect.Type;

public class ReplyTypes {

    public static final Type REPLY_TYPE__KEEP_STRM_RESULT = new TypeToken<Reply<KeepStrmReqResult>>(){}.getType();
}
