package com.iwaliner.urushi.json;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.iwaliner.urushi.ModCoreUrushi;
import net.minecraft.world.level.block.Block;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

public class StairBlockJsonMaker {
    public static final StairBlockJsonMaker INSTANCE = new StairBlockJsonMaker();
    public static String stairsNameString;

    public void registerStairsBlockModel(String baseName, String texture) {
        if (baseName == null)
            return;
        this.BuildStairBlockJsonModel(baseName,texture);
    }

    private static void BuildStairBlockJsonModel(String baseStairsName,String textureName) {
        // デバッグ環境でなければ実行しない
        if (!ModCoreUrushi.isDebug)
            return;

        // models/block/フォルダに生成する
        File dir = new File(ModCoreUrushi.assetsDirectory, "models/block/");
        if (dir != null ) {
            Map<String, String> map = Maps.newLinkedHashMap();
            map.put("tex", "urushi:block/"+textureName);
            StairsBlockModel model = INSTANCE.new StairsBlockModel(map);
            File f = new File(dir + "/" + baseStairsName+"_stairs" + ".json");

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


        // models/block/フォルダに生成する
        if (dir != null ) {
            Map<String, String> map = Maps.newLinkedHashMap();
            map.put("tex", "urushi:block/"+textureName);
            InnerStairsBlockModel model = INSTANCE.new InnerStairsBlockModel(map);
            File f = new File(dir + "/" + baseStairsName+"_inner_stairs" + ".json");

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


        // models/block/フォルダに生成する
        if (dir != null ) {
            Map<String, String> map = Maps.newLinkedHashMap();
            map.put("tex", "urushi:block/"+textureName);
            OuterStairsBlockModel model = INSTANCE.new OuterStairsBlockModel(map);
            File f = new File(dir + "/" + baseStairsName+"_outer_stairs" + ".json");

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


        // blockstates/フォルダに生成する
        File dir3 = new File(ModCoreUrushi.assetsDirectory, "blockstates/");
        if (dir3 != null ) {
            Map<String, Map> map = Maps.newLinkedHashMap();


            Map map1 = Maps.newLinkedHashMap();
            map1.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map1.put("y",270);
            map1.put("uvlock",true);
            map.put("facing=east,half=bottom,shape=inner_left", map1);

            Map map2 = Maps.newLinkedHashMap();
            map2.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map.put("facing=east,half=bottom,shape=inner_right", map2);

            Map map3 = Maps.newLinkedHashMap();
            map3.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map3.put("y",270);
            map3.put("uvlock",true);
            map.put("facing=east,half=bottom,shape=outer_left", map3);

            Map map4 = Maps.newLinkedHashMap();
            map4.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map.put("facing=east,half=bottom,shape=outer_right", map4);

            Map map5 = Maps.newLinkedHashMap();
            map5.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map.put("facing=east,half=bottom,shape=straight", map5);

            Map map6 = Maps.newLinkedHashMap();
            map6.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map6.put("x",180);
            map6.put("uvlock",true);
            map.put("facing=east,half=top,shape=inner_left", map6);

            Map map7 = Maps.newLinkedHashMap();
            map7.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map7.put("x",180);
            map7.put("y",90);
            map7.put("uvlock",true);
            map.put("facing=east,half=top,shape=inner_right", map7);

            Map map8 = Maps.newLinkedHashMap();
            map8.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map8.put("x",180);
            map8.put("uvlock",true);
            map.put("facing=east,half=top,shape=outer_left", map8);

            Map map9 = Maps.newLinkedHashMap();
            map9.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map9.put("x",180);
            map9.put("y",90);
            map9.put("uvlock",true);
            map.put("facing=east,half=top,shape=outer_right", map9);

            Map map10 = Maps.newLinkedHashMap();
            map10.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map10.put("x",180);
            map10.put("uvlock",true);
            map.put("facing=east,half=top,shape=straight", map10);

            Map map11 = Maps.newLinkedHashMap();
            map11.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map11.put("y",180);
            map11.put("uvlock",true);
            map.put("facing=north,half=bottom,shape=inner_left", map11);

            Map map12 = Maps.newLinkedHashMap();
            map12.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map12.put("y",270);
            map12.put("uvlock",true);
            map.put("facing=north,half=bottom,shape=inner_right", map12);

            Map map13 = Maps.newLinkedHashMap();
            map13.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map13.put("y",180);
            map13.put("uvlock",true);
            map.put("facing=north,half=bottom,shape=outer_left", map13);

            Map map14 = Maps.newLinkedHashMap();
            map14.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map14.put("y",270);
            map14.put("uvlock",true);
            map.put("facing=north,half=bottom,shape=outer_right", map14);

            Map map15 = Maps.newLinkedHashMap();
            map15.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map15.put("y",270);
            map15.put("uvlock",true);
            map.put("facing=north,half=bottom,shape=straight", map15);

            Map map16 = Maps.newLinkedHashMap();
            map16.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map16.put("x",180);
            map16.put("y",270);
            map16.put("uvlock",true);
            map.put("facing=north,half=top,shape=inner_left", map16);

            Map map17 = Maps.newLinkedHashMap();
            map17.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map17.put("x",180);
            map17.put("uvlock",true);
            map.put("facing=north,half=top,shape=inner_right", map17);

            Map map18 = Maps.newLinkedHashMap();
            map18.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map18.put("x",180);
            map18.put("y",270);
            map18.put("uvlock",true);
            map.put("facing=north,half=top,shape=outer_left", map18);

            Map map19 = Maps.newLinkedHashMap();
            map19.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map19.put("x",180);
            map19.put("uvlock",true);
            map.put("facing=north,half=top,shape=outer_right", map19);

            Map map20 = Maps.newLinkedHashMap();
            map20.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map20.put("x",180);
            map20.put("y",270);
            map20.put("uvlock",true);
            map.put("facing=north,half=top,shape=straight", map20);

            Map map21 = Maps.newLinkedHashMap();
            map21.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map.put("facing=south,half=bottom,shape=inner_left", map21);

            Map map22 = Maps.newLinkedHashMap();
            map22.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map22.put("y",90);
            map22.put("uvlock",true);
            map.put("facing=south,half=bottom,shape=inner_right", map22);

            Map map23 = Maps.newLinkedHashMap();
            map23.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map.put("facing=south,half=bottom,shape=outer_left", map23);

            Map map24 = Maps.newLinkedHashMap();
            map24.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map24.put("y",90);
            map24.put("uvlock",true);
            map.put("facing=south,half=bottom,shape=outer_right", map24);

            Map map25 = Maps.newLinkedHashMap();
            map25.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map25.put("y",90);
            map25.put("uvlock",true);
            map.put("facing=south,half=bottom,shape=straight", map25);

            Map map26 = Maps.newLinkedHashMap();
            map26.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map26.put("x",180);
            map26.put("y",90);
            map26.put("uvlock",true);
            map.put("facing=south,half=top,shape=inner_left", map26);

            Map map27 = Maps.newLinkedHashMap();
            map27.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map27.put("x",180);
            map27.put("y",180);
            map27.put("uvlock",true);
            map.put("facing=south,half=top,shape=inner_right", map27);

            Map map28 = Maps.newLinkedHashMap();
            map28.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map28.put("x",180);
            map28.put("y",90);
            map28.put("uvlock",true);
            map.put("facing=south,half=top,shape=outer_left", map28);

            Map map29 = Maps.newLinkedHashMap();
            map29.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map29.put("x",180);
            map29.put("y",180);
            map29.put("uvlock",true);
            map.put("facing=south,half=top,shape=outer_right", map29);

            Map map30 = Maps.newLinkedHashMap();
            map30.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map30.put("x",180);
            map30.put("y",90);
            map30.put("uvlock",true);
            map.put("facing=south,half=top,shape=straight", map30);

            Map map31 = Maps.newLinkedHashMap();
            map31.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map31.put("y",90);
            map31.put("uvlock",true);
            map.put("facing=west,half=bottom,shape=inner_left", map31);

            Map map32 = Maps.newLinkedHashMap();
            map32.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map32.put("y",180);
            map32.put("uvlock",true);
            map.put("facing=west,half=bottom,shape=inner_right", map32);

            Map map33 = Maps.newLinkedHashMap();
            map33.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map33.put("y",90);
            map33.put("uvlock",true);
            map.put("facing=west,half=bottom,shape=outer_left", map33);

            Map map34 = Maps.newLinkedHashMap();
            map34.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map34.put("y",180);
            map34.put("uvlock",true);
            map.put("facing=west,half=bottom,shape=outer_right", map34);

            Map map35 = Maps.newLinkedHashMap();
            map35.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map35.put("y",180);
            map35.put("uvlock",true);
            map.put("facing=west,half=bottom,shape=straight", map35);

            Map map36 = Maps.newLinkedHashMap();
            map36.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map36.put("x",180);
            map36.put("y",180);
            map36.put("uvlock",true);
            map.put("facing=west,half=top,shape=inner_left", map36);

            Map map37 = Maps.newLinkedHashMap();
            map37.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map37.put("x",180);
            map37.put("y",270);
            map37.put("uvlock",true);
            map.put("facing=west,half=top,shape=inner_right", map37);

            Map map38 = Maps.newLinkedHashMap();
            map38.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map38.put("x",180);
            map38.put("y",180);
            map38.put("uvlock",true);
            map.put("facing=west,half=top,shape=outer_left", map38);

            Map map39 = Maps.newLinkedHashMap();
            map39.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map39.put("x",180);
            map39.put("y",270);
            map39.put("uvlock",true);
            map.put("facing=west,half=top,shape=outer_right", map39);

            Map map40 = Maps.newLinkedHashMap();
            map40.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map40.put("x",180);
            map40.put("y",180);
            map40.put("uvlock",true);
            map.put("facing=west,half=top,shape=straight", map40);

            StairsBlockState model = INSTANCE.new StairsBlockState(map);

            File f = new File(dir3 + "/" + baseStairsName+"_stairs" + ".json");

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


        // models/item/フォルダに生成する
        File dir4 = new File(ModCoreUrushi.assetsDirectory, "models/item/");
        if (dir4 != null ) {
            ItemModel model = INSTANCE.new ItemModel("urushi:block/"+baseStairsName+"_stairs");

            File f = new File(dir4 + "/" + baseStairsName+"_stairs" + ".json");

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
        File dir2 = new File(ModCoreUrushi.dataDirectory, "urushi/loot_tables/blocks/");
        if (dir2 != null ) {
            stairsNameString=baseStairsName+"_stairs";
            StairsBlockLootTable model = INSTANCE.new StairsBlockLootTable();

            File f = new File(dir2 + "/" + baseStairsName+"_stairs" + ".json");

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



        // models/block/フォルダに生成する
        File dir5 = new File(ModCoreUrushi.assetsInBuildDirectory, "models/block/");
        if (dir5 != null ) {
            Map<String, String> map = Maps.newLinkedHashMap();
            map.put("tex", "urushi:block/"+textureName);
            StairsBlockModel model = INSTANCE.new StairsBlockModel(map);
            File f = new File(dir5 + "/" + baseStairsName+"_stairs" + ".json");

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


        // models/block/フォルダに生成する
        if (dir5 != null ) {
            Map<String, String> map = Maps.newLinkedHashMap();
            map.put("tex", "urushi:block/"+textureName);
            InnerStairsBlockModel model = INSTANCE.new InnerStairsBlockModel(map);
            File f = new File(dir5 + "/" + baseStairsName+"_inner_stairs" + ".json");

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


        // models/block/フォルダに生成する
        if (dir5 != null ) {
            Map<String, String> map = Maps.newLinkedHashMap();
            map.put("tex", "urushi:block/"+textureName);
            OuterStairsBlockModel model = INSTANCE.new OuterStairsBlockModel(map);
            File f = new File(dir5 + "/" + baseStairsName+"_outer_stairs" + ".json");

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


        // blockstates/フォルダに生成する
        File dir6 = new File(ModCoreUrushi.assetsInBuildDirectory, "blockstates/");
        if (dir6 != null ) {
            Map<String, Map> map = Maps.newLinkedHashMap();


            Map map1 = Maps.newLinkedHashMap();
            map1.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map1.put("y",270);
            map1.put("uvlock",true);
            map.put("facing=east,half=bottom,shape=inner_left", map1);

            Map map2 = Maps.newLinkedHashMap();
            map2.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map.put("facing=east,half=bottom,shape=inner_right", map2);

            Map map3 = Maps.newLinkedHashMap();
            map3.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map3.put("y",270);
            map3.put("uvlock",true);
            map.put("facing=east,half=bottom,shape=outer_left", map3);

            Map map4 = Maps.newLinkedHashMap();
            map4.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map.put("facing=east,half=bottom,shape=outer_right", map4);

            Map map5 = Maps.newLinkedHashMap();
            map5.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map.put("facing=east,half=bottom,shape=straight", map5);

            Map map6 = Maps.newLinkedHashMap();
            map6.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map6.put("x",180);
            map6.put("uvlock",true);
            map.put("facing=east,half=top,shape=inner_left", map6);

            Map map7 = Maps.newLinkedHashMap();
            map7.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map7.put("x",180);
            map7.put("y",90);
            map7.put("uvlock",true);
            map.put("facing=east,half=top,shape=inner_right", map7);

            Map map8 = Maps.newLinkedHashMap();
            map8.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map8.put("x",180);
            map8.put("uvlock",true);
            map.put("facing=east,half=top,shape=outer_left", map8);

            Map map9 = Maps.newLinkedHashMap();
            map9.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map9.put("x",180);
            map9.put("y",90);
            map9.put("uvlock",true);
            map.put("facing=east,half=top,shape=outer_right", map9);

            Map map10 = Maps.newLinkedHashMap();
            map10.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map10.put("x",180);
            map10.put("uvlock",true);
            map.put("facing=east,half=top,shape=straight", map10);

            Map map11 = Maps.newLinkedHashMap();
            map11.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map11.put("y",180);
            map11.put("uvlock",true);
            map.put("facing=north,half=bottom,shape=inner_left", map11);

            Map map12 = Maps.newLinkedHashMap();
            map12.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map12.put("y",270);
            map12.put("uvlock",true);
            map.put("facing=north,half=bottom,shape=inner_right", map12);

            Map map13 = Maps.newLinkedHashMap();
            map13.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map13.put("y",180);
            map13.put("uvlock",true);
            map.put("facing=north,half=bottom,shape=outer_left", map13);

            Map map14 = Maps.newLinkedHashMap();
            map14.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map14.put("y",270);
            map14.put("uvlock",true);
            map.put("facing=north,half=bottom,shape=outer_right", map14);

            Map map15 = Maps.newLinkedHashMap();
            map15.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map15.put("y",270);
            map15.put("uvlock",true);
            map.put("facing=north,half=bottom,shape=straight", map15);

            Map map16 = Maps.newLinkedHashMap();
            map16.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map16.put("x",180);
            map16.put("y",270);
            map16.put("uvlock",true);
            map.put("facing=north,half=top,shape=inner_left", map16);

            Map map17 = Maps.newLinkedHashMap();
            map17.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map17.put("x",180);
            map17.put("uvlock",true);
            map.put("facing=north,half=top,shape=inner_right", map17);

            Map map18 = Maps.newLinkedHashMap();
            map18.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map18.put("x",180);
            map18.put("y",270);
            map18.put("uvlock",true);
            map.put("facing=north,half=top,shape=outer_left", map18);

            Map map19 = Maps.newLinkedHashMap();
            map19.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map19.put("x",180);
            map19.put("uvlock",true);
            map.put("facing=north,half=top,shape=outer_right", map19);

            Map map20 = Maps.newLinkedHashMap();
            map20.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map20.put("x",180);
            map20.put("y",270);
            map20.put("uvlock",true);
            map.put("facing=north,half=top,shape=straight", map20);

            Map map21 = Maps.newLinkedHashMap();
            map21.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map.put("facing=south,half=bottom,shape=inner_left", map21);

            Map map22 = Maps.newLinkedHashMap();
            map22.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map22.put("y",90);
            map22.put("uvlock",true);
            map.put("facing=south,half=bottom,shape=inner_right", map22);

            Map map23 = Maps.newLinkedHashMap();
            map23.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map.put("facing=south,half=bottom,shape=outer_left", map23);

            Map map24 = Maps.newLinkedHashMap();
            map24.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map24.put("y",90);
            map24.put("uvlock",true);
            map.put("facing=south,half=bottom,shape=outer_right", map24);

            Map map25 = Maps.newLinkedHashMap();
            map25.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map25.put("y",90);
            map25.put("uvlock",true);
            map.put("facing=south,half=bottom,shape=straight", map25);

            Map map26 = Maps.newLinkedHashMap();
            map26.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map26.put("x",180);
            map26.put("y",90);
            map26.put("uvlock",true);
            map.put("facing=south,half=top,shape=inner_left", map26);

            Map map27 = Maps.newLinkedHashMap();
            map27.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map27.put("x",180);
            map27.put("y",180);
            map27.put("uvlock",true);
            map.put("facing=south,half=top,shape=inner_right", map27);

            Map map28 = Maps.newLinkedHashMap();
            map28.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map28.put("x",180);
            map28.put("y",90);
            map28.put("uvlock",true);
            map.put("facing=south,half=top,shape=outer_left", map28);

            Map map29 = Maps.newLinkedHashMap();
            map29.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map29.put("x",180);
            map29.put("y",180);
            map29.put("uvlock",true);
            map.put("facing=south,half=top,shape=outer_right", map29);

            Map map30 = Maps.newLinkedHashMap();
            map30.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map30.put("x",180);
            map30.put("y",90);
            map30.put("uvlock",true);
            map.put("facing=south,half=top,shape=straight", map30);

            Map map31 = Maps.newLinkedHashMap();
            map31.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map31.put("y",90);
            map31.put("uvlock",true);
            map.put("facing=west,half=bottom,shape=inner_left", map31);

            Map map32 = Maps.newLinkedHashMap();
            map32.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map32.put("y",180);
            map32.put("uvlock",true);
            map.put("facing=west,half=bottom,shape=inner_right", map32);

            Map map33 = Maps.newLinkedHashMap();
            map33.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map33.put("y",90);
            map33.put("uvlock",true);
            map.put("facing=west,half=bottom,shape=outer_left", map33);

            Map map34 = Maps.newLinkedHashMap();
            map34.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map34.put("y",180);
            map34.put("uvlock",true);
            map.put("facing=west,half=bottom,shape=outer_right", map34);

            Map map35 = Maps.newLinkedHashMap();
            map35.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map35.put("y",180);
            map35.put("uvlock",true);
            map.put("facing=west,half=bottom,shape=straight", map35);

            Map map36 = Maps.newLinkedHashMap();
            map36.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map36.put("x",180);
            map36.put("y",180);
            map36.put("uvlock",true);
            map.put("facing=west,half=top,shape=inner_left", map36);

            Map map37 = Maps.newLinkedHashMap();
            map37.put("model", "urushi:block/"+baseStairsName+"_inner_stairs");
            map37.put("x",180);
            map37.put("y",270);
            map37.put("uvlock",true);
            map.put("facing=west,half=top,shape=inner_right", map37);

            Map map38 = Maps.newLinkedHashMap();
            map38.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map38.put("x",180);
            map38.put("y",180);
            map38.put("uvlock",true);
            map.put("facing=west,half=top,shape=outer_left", map38);

            Map map39 = Maps.newLinkedHashMap();
            map39.put("model", "urushi:block/"+baseStairsName+"_outer_stairs");
            map39.put("x",180);
            map39.put("y",270);
            map39.put("uvlock",true);
            map.put("facing=west,half=top,shape=outer_right", map39);

            Map map40 = Maps.newLinkedHashMap();
            map40.put("model", "urushi:block/"+baseStairsName+"_stairs");
            map40.put("x",180);
            map40.put("y",180);
            map40.put("uvlock",true);
            map.put("facing=west,half=top,shape=straight", map40);

            StairsBlockState model = INSTANCE.new StairsBlockState(map);

            File f = new File(dir6 + "/" + baseStairsName+"_stairs" + ".json");

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


        // models/item/フォルダに生成する
        File dir7 = new File(ModCoreUrushi.assetsInBuildDirectory, "models/item/");
        if (dir7 != null ) {
            ItemModel model = INSTANCE.new ItemModel("urushi:block/"+baseStairsName+"_stairs");

            File f = new File(dir7 + "/" + baseStairsName+"_stairs" + ".json");

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
        File dir8 = new File(ModCoreUrushi.dataInBuildDirectory, "urushi/loot_tables/blocks/");
        if (dir8 != null ) {
            stairsNameString=baseStairsName+"_stairs";
            StairsBlockLootTable model = INSTANCE.new StairsBlockLootTable();

            File f = new File(dir8 + "/" + baseStairsName+"_stairs" + ".json");

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
    }

    public class StairsBlockModel {
        final String parent="urushi:block/stairs_base";
        final Map<String, String> textures;
        private StairsBlockModel(Map<String, String> tex) {
            textures=tex;
        }
    }
    public class InnerStairsBlockModel {
        final String parent="urushi:block/inner_stairs_base";
        final Map<String, String> textures;
        private InnerStairsBlockModel(Map<String, String> tex) {
            textures=tex;
        }
    }
    public class OuterStairsBlockModel {
        final String parent="urushi:block/outer_stairs_base";
        final Map<String, String> textures;
        private OuterStairsBlockModel(Map<String, String> tex) {
            textures=tex;
        }
    }
    public class ItemModel {
        final String parent;

        private ItemModel(String p) {
            parent = p;
        }
    }
    public class StairsBlockLootTable {
        String type="minecraft:block";

        List<StairsBlockLootTablePools> pools= Lists.newArrayList(new StairsBlockLootTablePools());


        private class StairsBlockLootTablePools{
            int rolls=1;
            List<StairsBlockLootTableEntries> entries= Lists.newArrayList(new StairsBlockLootTableEntries());
            List<StairsBlockLootTableConditions> conditions= Lists.newArrayList(new StairsBlockLootTableConditions());

            private class StairsBlockLootTableEntries{
                String type="minecraft:item";
                String name="urushi:"+stairsNameString;

            }
            private class StairsBlockLootTableConditions{
                String condition="minecraft:survives_explosion";

            }
        }
    }
    public class StairsBlockState {
        final Map<String, Map> variants;
        private StairsBlockState(Map<String, Map> tex) {
            variants=tex;
        }
    }


}