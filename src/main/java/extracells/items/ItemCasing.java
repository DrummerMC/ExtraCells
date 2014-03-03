package extracells.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extracells.Extracells;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemCasing extends Item
{

	// Localization suffixes
	public static final String[] suffixes = new String[]
	{ "physical", "fluid" };

	// Icons
	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public ItemCasing(int id)
	{
		super(id);
		this.setCreativeTab(Extracells.ModTab);
		this.setMaxStackSize(64);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int dmg)
	{
		return dmg > icons.length ? null : icons[dmg];
	}

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		this.icons = new Icon[suffixes.length];

		for (int i = 0; i < suffixes.length; ++i)
		{
			this.icons[i] = iconRegister.registerIcon("extracells:" + "casing.advanced." + suffixes[i]);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemID, CreativeTabs creativeTab, List itemList)
	{
		for (int j = 0; j < suffixes.length; ++j)
		{
			itemList.add(new ItemStack(itemID, 1, j));
		}
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack)
	{
		return StatCollector.translateToLocal(this.getUnlocalizedName(itemstack));
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int i = itemstack.getItemDamage();
		return "item.casing.advanced." + (i < suffixes.length ? suffixes[i] : "NULL");
	}

	public EnumRarity getRarity(ItemStack par1)
	{
		return EnumRarity.uncommon;
	}
}
