package org.example.common.models;

import org.example.server.models.AnimalProperty.AnimalEnclosure;
import org.example.server.models.AnimalProperty.Barn;
import org.example.server.models.AnimalProperty.Coop;
import org.example.server.models.Cell;
import org.example.server.models.Item;
import org.example.server.models.ProcessedProduct;
import org.example.server.models.enums.Plants.*;
import org.example.server.models.enums.items.*;
import org.example.server.models.enums.items.products.AnimalProduct;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.CraftingProduct;
import org.example.server.models.enums.items.products.ProcessedProductType;

public class ItemManager {
    public static Item getItemByName(String itemName) {
        Item result = ToolType.getItem(itemName);
        if (result != null)
            return result;

        itemName = itemName.replace(" ", "");

        result = AnimalProduct.getItem(itemName);
        if (result != null)
            return result;

        result = CookingProduct.getItem(itemName);
        if (result != null)
            return result;

        result = CraftingProduct.getItem(itemName);
        if (result != null)
            return result;

        result = ProcessedProductType.getItem(itemName);
        if (result != null) {
            if (result.getPrice() == null)
                return new ProcessedProduct((ProcessedProductType) result, 0, 0);
            return new ProcessedProduct(
                    (ProcessedProductType) result,
                    result.getPrice(),
                    ((ProcessedProductType) result).getEnergy()
            );
        }

        result = AnimalType.getItem(itemName);
        if (result != null)
            return result;

        result = BuildingType.getItem(itemName);
        if (result != null)
            return result;

        result = FishType.getItem(itemName);
        if (result != null)
            return result;

        result = MineralType.getItem(itemName);
        if (result != null)
            return result;

        result = ShopItems.getItem(itemName);
        if (result != null)
            return result;

        result = SeedType.getItem(itemName);
        if (result != null)
            return result;

        result = SaplingType.getItem(itemName);
        if (result != null)
            return result;

        result = FruitType.getItem(itemName);
        if (result != null)
            return result;

        result = Recipe.getItem(itemName);
        if (result != null)
            return result;

        return null;
    }

    public static Object getForagingType(String typeName) {
        MineralType mineral = MineralType.getItem(typeName);
        if (mineral != null)
            return mineral;

        CropType crop = CropType.getItem(typeName);
        if (crop != null)
            return crop;

        TreeType tree = TreeType.getItem(typeName);
        return tree;
    }

    public static Plant getPlant(String type) {
        CropType cropType = CropType.getItem(type);
        if (cropType != null)
            return new Crop(cropType);
        TreeType treeType = TreeType.getItem(type);
        if (treeType != null)
            return new Tree(treeType);
        return null;
    }

    public static AnimalEnclosure getEnclosure(Cell topLeft, String type) {
        BuildingType buildingType = BuildingType.getItem(type);
        assert buildingType != null;
        if (buildingType.isBarn())
            return new Barn(buildingType, topLeft);
        else if (buildingType.isCoop())
            return new Coop(buildingType, topLeft);
        return null;
    }
}
