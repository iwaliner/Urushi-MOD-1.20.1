package com.iwaliner.urushi.json;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.iwaliner.urushi.ModCoreUrushi;
import net.minecraft.world.level.block.Block;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class CubeAllBlockJsonMaker {
    public static final CubeAllBlockJsonMaker INSTANCE = new CubeAllBlockJsonMaker();


    public void registerBlockModel(Block block, String texture) {
        if (block == null)
            return;
        String blockName = block.getDescriptionId().toUpperCase();
        this.BuildBlockJsonModel(block, blockName,texture);
    }

    private static void BuildBlockJsonModel(Block block, String fileName,String textureName) {
        if (!ModCoreUrushi.isDebug)
            return;

        File dir = new File(ModCoreUrushi.assetsDirectory, "models/block/");
        if (dir != null && block != null) {
            Map<String, String> map = Maps.newLinkedHashMap();
            map.put("all", "urushi:block/"+textureName);
            CubeAllBlockModel model = INSTANCE.new CubeAllBlockModel(map);
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




        // models/block/フォルダに生成する
        File dir2 = new File(ModCoreUrushi.assetsInBuildDirectory, "models/block/");
        if (dir2 != null && block != null) {
            Map<String, String> map = Maps.newLinkedHashMap();
            map.put("all", "urushi:block/"+textureName);
            CubeAllBlockModel model = INSTANCE.new CubeAllBlockModel(map);
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


        // blockstates/フォルダに生成する
        File dir3 = new File(ModCoreUrushi.assetsDirectory, "blockstates/");
        if (dir3 != null && block != null) {
            Map<String, Map> map = Maps.newLinkedHashMap();
            Map<String, String> childMap = Maps.newLinkedHashMap();
            childMap.put("model", "urushi:block/"+fileName);
            map.put("", childMap);
            CubeAllBlockState model = INSTANCE.new CubeAllBlockState(map);

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


        // blockstates/フォルダに生成する
        File dir4 = new File(ModCoreUrushi.assetsInBuildDirectory, "blockstates/");
        if (dir4 != null && block != null) {
            Map<String, Map> map = Maps.newLinkedHashMap();
            Map<String, String> childMap = Maps.newLinkedHashMap();
            childMap.put("model", "urushi:block/"+fileName);
            map.put("", childMap);
            CubeAllBlockState model = INSTANCE.new CubeAllBlockState(map);

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

    }

    public class CubeAllBlockModel {
        final String parent="block/cube_all";
        final Map<String, String> textures;
        private CubeAllBlockModel(Map<String, String> tex) {
            textures=tex;
        }
    }
    public class CubeAllBlockState {
        final Map<String, Map> variants;
        private CubeAllBlockState(Map<String, Map> tex) {
            variants=tex;
        }
    }


}