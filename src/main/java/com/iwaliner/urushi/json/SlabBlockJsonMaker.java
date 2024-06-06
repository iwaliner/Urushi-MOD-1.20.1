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
import java.util.*;

public class SlabBlockJsonMaker {
    public static final SlabBlockJsonMaker INSTANCE = new SlabBlockJsonMaker();
    public static String slabNameString;

    public void registerSlabBlockModel(Block slabBlock,String underFile, Block doubleBlock, String upperFile, String texture) {
        if (doubleBlock == null)
            return;
        String doubleFile = doubleBlock.getDescriptionId().toUpperCase();
        String slabFile = slabBlock.getDescriptionId().toUpperCase();
        this.BuildSlabBlockJsonModel(slabFile,underFile,doubleFile,upperFile,texture);
    }

    private static void BuildSlabBlockJsonModel(String slabName,String underFileName, String doubleFileName, String upperFileName,String textureName) {
        // デバッグ環境でなければ実行しない
        if (!ModCoreUrushi.isDebug)
            return;

        // models/block/フォルダに生成する
        File dir = new File(ModCoreUrushi.assetsDirectory, "models/block/");
        if (dir != null ) {
            Map<String, String> underMap = Maps.newLinkedHashMap();
            underMap.put("tex", "urushi:block/"+textureName);
            UnderSlabBlockModel underModel = INSTANCE.new UnderSlabBlockModel(underMap);
            File underF = new File(dir + "/" + underFileName + ".json");

            if (underF.exists())
                return;

            // ファイルを生成
            if (!underF.getParentFile().exists()) {
                underF.getParentFile().mkdirs();
            }

            try {
                underF.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }



            // JSONファイルを生成
            try {
                if (underF.canWrite()) {
                    FileOutputStream fos = new FileOutputStream(underF.getPath());
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    JsonWriter jsw = new JsonWriter(osw);

                    jsw.setIndent("  ");
                    Gson gson = new Gson();
                    gson.toJson(underModel, underModel.getClass(), jsw);

                    osw.close();
                    fos.close();
                    jsw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }






            Map<String, String> upperMap = Maps.newLinkedHashMap();
            upperMap.put("tex", "urushi:block/"+textureName);
            UpperSlabBlockModel upperModel = INSTANCE.new UpperSlabBlockModel(upperMap);
            File upperF = new File(dir + "/" + upperFileName + ".json");

            if (upperF.exists())
                return;

            // ファイルを生成
            if (!upperF.getParentFile().exists()) {
                upperF.getParentFile().mkdirs();
            }

            try {
                upperF.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }



            // JSONファイルを生成
            try {
                if (upperF.canWrite()) {
                    FileOutputStream fos = new FileOutputStream(upperF.getPath());
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    JsonWriter jsw = new JsonWriter(osw);

                    jsw.setIndent("  ");
                    Gson gson = new Gson();
                    gson.toJson(upperModel, upperModel.getClass(), jsw);

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

            Map<String, String> underMap = Maps.newLinkedHashMap();
            underMap.put("model", "urushi:block/"+underFileName);
            map.put("type=bottom", underMap);

            Map<String, String> doubleMap = Maps.newLinkedHashMap();
            doubleMap.put("model", "urushi:block/"+doubleFileName);
            map.put("type=double", doubleMap);

            Map<String, String> upperMap = Maps.newLinkedHashMap();
            upperMap.put("model", "urushi:block/"+upperFileName);
            map.put("type=top", upperMap);

            SlabBlockState model = INSTANCE.new SlabBlockState(map);

            File f = new File(dir3 + "/" + slabName + ".json");

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
            ItemModel model = INSTANCE.new ItemModel("urushi:block/"+underFileName);

            File f = new File(dir4 + "/" + slabName + ".json");

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
        File dir5 = new File(ModCoreUrushi.dataDirectory, "urushi/loot_tables/blocks/");
        if (dir5 != null ) {
            slabNameString=slabName;
            SlabBlockLootTable model = INSTANCE.new SlabBlockLootTable();

            File f = new File(dir5 + "/" + slabName + ".json");

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
        File dir6 = new File(ModCoreUrushi.assetsInBuildDirectory, "models/block/");
        if (dir6 != null ) {
            Map<String, String> underMap = Maps.newLinkedHashMap();
            underMap.put("tex", "urushi:block/"+textureName);
            UnderSlabBlockModel underModel = INSTANCE.new UnderSlabBlockModel(underMap);
            File underF = new File(dir6 + "/" + underFileName + ".json");

            if (underF.exists())
                return;

            // ファイルを生成
            if (!underF.getParentFile().exists()) {
                underF.getParentFile().mkdirs();
            }

            try {
                underF.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }



            // JSONファイルを生成
            try {
                if (underF.canWrite()) {
                    FileOutputStream fos = new FileOutputStream(underF.getPath());
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    JsonWriter jsw = new JsonWriter(osw);

                    jsw.setIndent("  ");
                    Gson gson = new Gson();
                    gson.toJson(underModel, underModel.getClass(), jsw);

                    osw.close();
                    fos.close();
                    jsw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }






            Map<String, String> upperMap = Maps.newLinkedHashMap();
            upperMap.put("tex", "urushi:block/"+textureName);
            UpperSlabBlockModel upperModel = INSTANCE.new UpperSlabBlockModel(upperMap);
            File upperF = new File(dir + "/" + upperFileName + ".json");

            if (upperF.exists())
                return;

            // ファイルを生成
            if (!upperF.getParentFile().exists()) {
                upperF.getParentFile().mkdirs();
            }

            try {
                upperF.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }



            // JSONファイルを生成
            try {
                if (upperF.canWrite()) {
                    FileOutputStream fos = new FileOutputStream(upperF.getPath());
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    JsonWriter jsw = new JsonWriter(osw);

                    jsw.setIndent("  ");
                    Gson gson = new Gson();
                    gson.toJson(upperModel, upperModel.getClass(), jsw);

                    osw.close();
                    fos.close();
                    jsw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }






        // blockstates/フォルダに生成する
        File dir7 = new File(ModCoreUrushi.assetsInBuildDirectory, "blockstates/");
        if (dir7 != null ) {
            Map<String, Map> map = Maps.newLinkedHashMap();

            Map<String, String> underMap = Maps.newLinkedHashMap();
            underMap.put("model", "urushi:block/"+underFileName);
            map.put("type=bottom", underMap);

            Map<String, String> doubleMap = Maps.newLinkedHashMap();
            doubleMap.put("model", "urushi:block/"+doubleFileName);
            map.put("type=double", doubleMap);

            Map<String, String> upperMap = Maps.newLinkedHashMap();
            upperMap.put("model", "urushi:block/"+upperFileName);
            map.put("type=top", upperMap);

            SlabBlockState model = INSTANCE.new SlabBlockState(map);

            File f = new File(dir7 + "/" + slabName + ".json");

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
        File dir8 = new File(ModCoreUrushi.assetsInBuildDirectory, "models/item/");
        if (dir8 != null ) {
            ItemModel model = INSTANCE.new ItemModel("urushi:block/"+underFileName);

            File f = new File(dir8 + "/" + slabName + ".json");

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
        File dir9 = new File(ModCoreUrushi.dataInBuildDirectory, "urushi/loot_tables/blocks/");
        if (dir9 != null ) {
            slabNameString=slabName;
            SlabBlockLootTable model = INSTANCE.new SlabBlockLootTable();

            File f = new File(dir9 + "/" + slabName + ".json");

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


    public class UnderSlabBlockModel {
        final String parent="urushi:block/half_slab_base";
        final Map<String, String> textures;
        private UnderSlabBlockModel(Map<String, String> tex) {
            textures=tex;
        }
    }
    public class UpperSlabBlockModel {
        final String parent="urushi:block/upper_slab_base";
        final Map<String, String> textures;
        private UpperSlabBlockModel(Map<String, String> tex) {
            textures=tex;
        }
    }
    public class SlabBlockState {
        final Map<String, Map> variants;
        private SlabBlockState(Map<String, Map> tex) {
            variants=tex;
        }
    }
    public class ItemModel {
        final String parent;

        private ItemModel(String p) {
            parent = p;
        }
    }
    public class SlabBlockLootTable {
        String type="minecraft:block";
        List<SlabBlockLootTablePools> pools= Lists.newArrayList(new SlabBlockLootTablePools());
        private class SlabBlockLootTablePools{
            int rolls=1;
            List<SlabBlockLootTableEntries> entries= Lists.newArrayList(new SlabBlockLootTableEntries());
            private class SlabBlockLootTableEntries{
                String type="minecraft:item";
                List<Map> functions;
               String name="urushi:"+slabNameString;
                private SlabBlockLootTableEntries() {
                    Map<String, String> b = Maps.newLinkedHashMap();
                    b.put("type", "double");
                    Map conditionsMap = Maps.newLinkedHashMap();
                    conditionsMap.put("condition","minecraft:block_state_property");
                    conditionsMap.put("block","urushi:"+slabNameString);
                    conditionsMap.put("properties",b);
                    Map p = Maps.newLinkedHashMap();
                    p.put("function", "minecraft:set_count");
                    p.put("conditions",Lists.newArrayList(conditionsMap));
                    p.put("count",2);
                    Map q = Maps.newLinkedHashMap();
                    q.put("function", "minecraft:explosion_decay");
                    functions=Lists.newArrayList(p,q);
                }
            }

        }
    }
}