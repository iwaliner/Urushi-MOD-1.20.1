package com.iwaliner.urushi.json;

import com.google.common.collect.Lists;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.iwaliner.urushi.ModCoreUrushi;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.Item;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;


public class NormalBlockItemJsonMaker {
    public static final NormalBlockItemJsonMaker INSTANCE = new NormalBlockItemJsonMaker();
    public static String itemName;

    public void registerBlockModel(Item item,String blockPathName) {
        if (item == null||blockPathName==null)
            return;
            String registoryName = item.getDescriptionId().toUpperCase();
            this.BuildItemJsonModel(blockPathName, registoryName);

    }

    private static void BuildItemJsonModel(String blockPathName, String fileName) {
        // デバッグ環境でなければ実行しない
        if (!ModCoreUrushi.isDebug)
            return;

        // models/item/フォルダに生成する
        File dir = new File(ModCoreUrushi.assetsDirectory, "models/item/");
        if (dir != null ) {
            ItemModel model = INSTANCE.new ItemModel("urushi:block/"+blockPathName);

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

        if (dir2 != null  ) {
            ItemModel model = INSTANCE.new ItemModel("urushi:block/"+blockPathName);

            File f = new File(dir2 + "/" + fileName + ".json");

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



        // data/urushi/loot_tables/block/フォルダに生成する
        File dir3 = new File(ModCoreUrushi.dataDirectory, "urushi/loot_tables/blocks/");
        if (dir3 != null ) {
            itemName=fileName;
            BlockLootTable model = INSTANCE.new BlockLootTable();

            File f = new File(dir3 + "/" + fileName + ".json");

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


        // data/urushi/loot_tables/block/フォルダに生成する
        File dir4 = new File(ModCoreUrushi.dataInBuildDirectory, "urushi/loot_tables/blocks/");
        if (dir4 != null ) {
            itemName=fileName;
            BlockLootTable model = INSTANCE.new BlockLootTable();

            File f = new File(dir4 + "/" + fileName + ".json");

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


        ModCoreUrushi.logger.info("Output JSON model: " + fileName);
    }

    public class ItemModel {
        final String parent;

        private ItemModel(String p) {
            parent = p;
        }
    }
    public class BlockLootTable {
         String type="minecraft:block";

        List<BlockLootTablePools> pools= Lists.newArrayList(new BlockLootTablePools());


        private class BlockLootTablePools{
            int rolls=1;
            List<BlockLootTableEntries> entries= Lists.newArrayList(new BlockLootTableEntries());
            List<BlockLootTableConditions> conditions= Lists.newArrayList(new BlockLootTableConditions());

            private class BlockLootTableEntries{
                String type="minecraft:item";
                String name="urushi:"+itemName;

            }
            private class BlockLootTableConditions{
                String condition="minecraft:survives_explosion";

            }
        }
    }


}
