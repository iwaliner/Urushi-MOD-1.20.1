package com.iwaliner.urushi.util;

public  enum ElementType {
    WoodElement(0),
    FireElement(1),
    EarthElement(2),
    MetalElement(3),
    WaterElement(4),
    FAIL(-1);

        private int id;

        private ElementType(int id) {
            this.id = id;
        }
        public static ElementType getType(int id){
            return switch (id) {
                case 0 -> WoodElement;
                case 1 -> FireElement;
                case 2 -> EarthElement;
                case 3 -> MetalElement;
                case 4 -> WaterElement;
                default -> FAIL;
            };

        }
        public int getID()
        {
            return this.id;
        }
    }

