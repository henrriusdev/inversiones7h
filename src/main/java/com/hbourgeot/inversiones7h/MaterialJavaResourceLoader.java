package com.hbourgeot.inversiones7h;

import java.net.URL;

public class MaterialJavaResourceLoader {

    public static URL loadURL(String path) {
        return MaterialJavaResourceLoader.class.getResource(path);
    }

    public static String load(String path) {
        return loadURL(path).toString();
    }

}