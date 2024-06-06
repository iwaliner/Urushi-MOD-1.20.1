package com.iwaliner.urushi.json;


import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.iwaliner.urushi.ModCoreUrushi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class MineableTagGenerator {
    public static final MineableTagGenerator INSTANCE = new MineableTagGenerator();


    public void registerPickaxeMineableTag( List list) {
        this.BuildTag(list, "pickaxe");
    }
    public void registerAxeMineableTag( List list) {
        this.BuildTag(list, "axe");
    }
    public void registerShovelMineableTag( List list) {
        this.BuildTag(list, "shovel");
    }
    public void registerHoeMineableTag( List list) {
        this.BuildTag(list, "hoe");
    }

    private static void BuildTag(List<String> list,String fileName) {
        // デバッグ環境でなければ実行しない
        if (!ModCoreUrushi.isDebug)
            return;

        // 指定フォルダ内に生成する
        File dir = new File(ModCoreUrushi.dataDirectory, "minecraft/tags/blocks/mineable/");

        Mineable model = INSTANCE.new Mineable(list);
            File f = new File(dir + "/" + fileName + ".json");

         /*   if (f.exists())
                return;*/
            // ファイルを生成
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // JSONファイルを生成
            try {
                if (f.canWrite()) {
                    FileOutputStream fos = new FileOutputStream(f.getPath());
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    JsonWriter jsw = new JsonWriter(osw);

                    jsw.setIndent("  ");
                    Gson gson = new Gson();
                    gson.toJson(model, model.getClass(), jsw);

                    osw.close();
                    fos.close();
                    jsw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        // 指定フォルダ内に生成する
        File dir2 = new File(ModCoreUrushi.dataInBuildDirectory, "minecraft/tags/blocks/mineable/");

        Mineable model2 = INSTANCE.new Mineable(list);
        File f2 = new File(dir2 + "/" + fileName + ".json");

         /*   if (f.exists())
                return;*/
        // ファイルを生成
        if (!f2.getParentFile().exists()) {
            f2.getParentFile().mkdirs();
        }
        try {
            f2.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // JSONファイルを生成
        try {
            if (f2.canWrite()) {
                FileOutputStream fos = new FileOutputStream(f2.getPath());
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                JsonWriter jsw = new JsonWriter(osw);

                jsw.setIndent("  ");
                Gson gson = new Gson();
                gson.toJson(model2, model2.getClass(), jsw);

                osw.close();
                fos.close();
                jsw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ModCoreUrushi.logger.info("Output mineable tag JSON");
    }

    public class Mineable {
        final Boolean replace=false;
        List<String> values;
        private Mineable(List<String> list) {
            values=list;
        }

    }


}
