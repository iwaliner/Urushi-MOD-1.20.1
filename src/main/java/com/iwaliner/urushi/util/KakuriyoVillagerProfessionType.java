package com.iwaliner.urushi.util;

public  enum KakuriyoVillagerProfessionType {
    Jobless(0),
    RiceDealer(1),
    Fisherman(2),
    Lumberjack(3),
    Cook(4),
    KnickKnackDealer(5),
    Miner(6);

        private int id;

        private KakuriyoVillagerProfessionType(int id) {
            this.id = id;
        }
        public static KakuriyoVillagerProfessionType getType(int id){
            return switch (id) {
                case 1 -> RiceDealer;
                case 2 -> Fisherman;
                case 3 -> Lumberjack;
                case 4 -> Cook;
                case 5 -> KnickKnackDealer;
                case 6 -> Miner;
                default -> Jobless;
            };

        }
        public int getID()
        {
            return this.id;
        }
    }

