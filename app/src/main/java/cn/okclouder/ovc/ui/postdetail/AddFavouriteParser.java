package cn.okclouder.ovc.ui.postdetail;

import com.alibaba.fastjson.JSON;

public class AddFavouriteParser {

    public static AddFavouriteResp parseResponse(String response) {
        return JSON.parseObject(response, AddFavouriteResp.class);
    }
}
