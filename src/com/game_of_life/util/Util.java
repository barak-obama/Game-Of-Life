package com.game_of_life.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by obama on 07/10/2016.
 */
public class Util {

    public static int[][] parseMatrix(String matrix){
        String[] s = matrix.split("\\n");
        int height = s.length;
        int[][] m = new int[height][];
        for(int i = 0; i < height; i++){
            String[] ss = s[i].split(" ");
            int[] row = new int[ss.length];
            for (int j = 0; j < ss.length; j++) {
                row[j] = Integer.parseInt(ss[j]);
            }
            m[i] = row;
        }
        return m;
    }

    public static String join(Object[] aArr, String sSep) {
        StringBuilder sbStr = new StringBuilder();
        int i = 0;

        for(int il = aArr.length; i < il; ++i) {
            if(i > 0) {
                sbStr.append(sSep);
            }

            sbStr.append(aArr[i]);
        }

        return sbStr.toString();
    }

    public static void copyFile(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }


}
