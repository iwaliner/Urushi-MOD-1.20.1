package com.iwaliner.urushi.json;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.iwaliner.urushi.ConfigUrushi;
import com.iwaliner.urushi.ModCoreUrushi;
import net.minecraft.world.item.Item;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class GeneratedItemJsonMaker {
    public static final GeneratedItemJsonMaker INSTANCE = new GeneratedItemJsonMaker();

    /**
     * ItemのJSONモデル生成と登録をまとめて行うメソッド。
     */
    public void registerItemModel(Item item, String tex) {
        if (item == null)
            return;
        String fileName = item.getDescriptionId().toUpperCase();
        // JSONの生成
        this.BuildJsonModel(item, fileName,tex);

    }

    private static void BuildJsonModel(Item item, String fileName,String textureName) {
        // デバッグ環境でなければ実行しない
        if (!ModCoreUrushi.isDebug)
            return;

        // models/itemフォルダに生成する
        File dir = new File(ModCoreUrushi.assetsDirectory, "models/item/");

        if (dir != null && item != null ) {
            Map<String, String> map = Maps.newLinkedHashMap();
            map.put("layer0", "urushi:item/"+textureName);
            ItemModel model = INSTANCE.new ItemModel("item/generated", map);
            File f = new File(dir + "/" + fileName + ".json");
            if (f.exists())
                return;
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
        }




        // models/itemフォルダに生成する
        File dir2 = new File(ModCoreUrushi.assetsInBuildDirectory, "models/item/");

        if (dir2 != null && item != null ) {
            Map<String, String> map = Maps.newLinkedHashMap();
            map.put("layer0", "urushi:item/"+textureName);
            ItemModel model = INSTANCE.new ItemModel("item/generated",map);
            File f = new File(dir2 + "/" + fileName + ".json");
            if (f.exists())
                return;
            // ファイルを生成。
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
        }

    }

    public class ItemModel {
        final String parent;
        final Map<String, String> textures;

        private ItemModel(String p, Map<String, String> tex) {
            parent = p;
            textures = tex;
        }
    }
}

