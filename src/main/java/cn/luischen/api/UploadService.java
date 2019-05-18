package cn.luischen.api;

import cn.luischen.service.ServerConfig;
import cn.luischen.utils.TaleUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by Donghua.Chen on 2018/5/1.
 */
@Component
public class UploadService {

    @Autowired
    private ServerConfig serverConfig;

    @Value("${file.path}")
    public String CACHE_PATH;

    @Value("${file.host}")
    public String HOST;

    /**
     * 七牛云外网访问地址
     */
    @Value("${qiniu.cdn.url}")
    public String QINIU_UPLOAD_SITE;

    public String upload(MultipartFile file, String fileName) {
        String target = CACHE_PATH + File.separator + file.getOriginalFilename();
        System.out.println("target:" + target);
        try {
            File file1 = new File(target);
            if (file1.exists()) {
                file1.delete();
            }
            file.transferTo(file1);
            String url = HOST + "/image/cache/" +file.getOriginalFilename();
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
