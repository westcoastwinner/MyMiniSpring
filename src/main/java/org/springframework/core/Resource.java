package org.springframework.core;

import java.io.IOException;
import java.io.InputStream;
//实现类实际是封装了对应资源(文件、网络、类路径下)的输入流对象
public interface Resource {
    InputStream getInputStream() throws IOException;
}
