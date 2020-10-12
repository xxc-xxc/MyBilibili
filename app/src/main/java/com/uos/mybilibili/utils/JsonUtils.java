package com.uos.mybilibili.utils;

import java.io.InputStream;

/**
 * @author xxc
 * @date 创建时间：2020年10月12日14:39:10
 * 描述:
 */

public class JsonUtils {
    public static String readJson(String jsonFile) {
        InputStream inputStream = FileUtils.openAssetFile(AppUtils.getAppContext(), jsonFile);
        return IOUtils.streamToString(inputStream);
    }
}
