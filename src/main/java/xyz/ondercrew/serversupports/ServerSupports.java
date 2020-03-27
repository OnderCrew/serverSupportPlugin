package xyz.ondercrew.serversupports;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerSupports extends JavaPlugin {
    private ItemStack[] mission1 = {
            new ItemStack(Material.OBSIDIAN, 2),
            new ItemStack(Material.ENDER_PEARL, 1),
            new ItemStack(Material.SADDLE, 1),
            new ItemStack(Material.MAGMA_CREAM, 1),
            new ItemStack(Material.GOLD_NUGGET, 2),
            new ItemStack(Material.BREAD, 3),
            new ItemStack(Material.GOLDEN_APPLE, 1)
    };

    private ItemStack[] mission2 = {
            new ItemStack(Material.SNOW_BALL, 16),
            new ItemStack(Material.OBSIDIAN, 3),
            new ItemStack(Material.CAKE, 1),
            new ItemStack(Material.GLASS_BOTTLE, 2),
            new ItemStack(Material.GOLDEN_CARROT, 2),
            new ItemStack(Material.CLAY, 3)
    };

    private ItemStack[] mission3 = {
            new ItemStack(Material.BED, 1),
            new ItemStack(Material.VINE, 10),
            new ItemStack(Material.GOLD_BARDING, 1),
            new ItemStack(Material.LOG, 4),
            new ItemStack(Material.SPECKLED_MELON, 2),
            new ItemStack(Material.WHEAT, 3)
    };

    private ItemStack[] mission4 = {
            new ItemStack(Material.GLASS, 4),
            new ItemStack(Material.CHORUS_FRUIT, 3),
            new ItemStack(Material.COOKED_CHICKEN, 3),
            new ItemStack(Material.EMERALD, 3),
            new ItemStack(Material.NETHER_BRICK, 5),
            new ItemStack(Material.GOLD_BLOCK, 1),
            new ItemStack(Material.SAPLING, 1)
    };

    private ItemStack[] mission5 = {
            new ItemStack(Material.GLOWSTONE, 3),
            new ItemStack(Material.MONSTER_EGG, 1),
            new ItemStack(Material.OBSIDIAN, 3),
            new ItemStack(Material.REDSTONE_BLOCK, 2),
            new ItemStack(Material.BONE_BLOCK, 14),
            new ItemStack(Material.BROWN_MUSHROOM, 3),
            new ItemStack(Material.GOLD_ORE, 1)
    };

    private ItemStack[] mission6 = {
            new ItemStack(Material.OBSIDIAN, 2),
            new ItemStack(Material.APPLE, 3),
            new ItemStack(Material.GOLD_PICKAXE, 1),
            new ItemStack(Material.HARD_CLAY, 10),
            new ItemStack(Material.POWERED_RAIL, 3),
            new ItemStack(Material.LEAVES, 10)
    };

    private ItemStack[][] mission = { null, mission1, mission2, mission3, mission4, mission5, mission6 };

    private boolean isAlready = false;

// - 흑요석 2개
// - 사과 3개
// - 금 곡괭이 1개
// - 독화살 1개 (독2 / 0:02)
// - 굳은 점토 10개
// - 전원 레일 3개
// - 참나무 잎 10개

    @Override
    public void onEnable() {
        // Plugin startup login
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                //Every tick the code put here will perform
                for (Player player : getServer().getOnlinePlayers()) {
                    Inventory inventory = player.getInventory();
                    for (int c = 1; c < mission.length; c++) {
                        for (ItemStack item : inventory) {
                            if (item != null) {
                                if (item.getType() == Material.BOOK_AND_QUILL) {
                                    String name = item.getItemMeta().getDisplayName();
                                    if (name != null) {
                                        if (name.equals(c + "번 미션지")) {
                                            int count = 0;
                                            for (ItemStack i : mission[c]) {
                                                for (ItemStack item2 : inventory) {
                                                    if (i != null && item2 != null) {
                                                        if (i.getType() == item2.getType() && i.getAmount() <= item2.getAmount()) {
                                                            count++;
                                                        }
                                                    }
                                                }
                                            }

                                            if (count >= mission[c].length) {
                                                inventory.remove(item);
                                                player.sendMessage(ChatColor.AQUA + "미션 " + c + "완료!");

                                                if (!isAlready) {
                                                    isAlready = true;
                                                    Location location = player.getLocation();
                                                    location.setY(location.getY() + 1);
                                                    ItemStack paper = new ItemStack(Material.PAPER, 1);
                                                    ItemMeta meta = paper.getItemMeta();
                                                    meta.setDisplayName("우승증명서");
                                                    paper.setItemMeta(meta);

                                                    location.getWorld().dropItem(location, paper);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 0L, 1L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
