package com.iwaliner.urushi.json;


import com.google.common.collect.Lists;
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

public class LootTableGenerator {
    public static final LootTableGenerator INSTANCE = new LootTableGenerator();
    public static String itemName;

    public void registerSimpleBlockLootTable(String blockName) {
        itemName=blockName;
        this.BuildTag(blockName);
    }

    private void BuildTag(String blockName) {
         if (!ModCoreUrushi.isDebug)
            return;

        File dir = new File(ModCoreUrushi.dataDirectory, "urushi/loot_tables/blocks/");

        SimpleBlockLootTable model = INSTANCE.new SimpleBlockLootTable();
            File f = new File(dir + "/" + blockName + ".json");

            if (f.exists())
                return;

            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

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




        File dir2 = new File(ModCoreUrushi.dataInBuildDirectory, "urushi/loot_tables/blocks/");

        SimpleBlockLootTable model2 = INSTANCE.new SimpleBlockLootTable();
        File f2 = new File(dir2 + "/" + blockName + ".json");

          if (f.exists())
                return;

        if (!f2.getParentFile().exists()) {
            f2.getParentFile().mkdirs();
        }
        try {
            f2.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

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

        ModCoreUrushi.logger.info("Output block LootTable JSON: "+blockName);
    }


    public class SimpleBlockLootTable {

        String type="minecraft:block";

        List<BlockLootTablePools> pools= Lists.newArrayList(new BlockLootTablePools());


        private class BlockLootTablePools{

            int rolls=1;
            List<BlockLootTablePools.BlockLootTableEntries> entries= Lists.newArrayList(new BlockLootTablePools.BlockLootTableEntries());
            List<BlockLootTablePools.BlockLootTableConditions> conditions= Lists.newArrayList(new BlockLootTablePools.BlockLootTableConditions());

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
