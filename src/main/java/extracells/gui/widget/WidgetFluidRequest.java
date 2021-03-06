package extracells.gui.widget;

import cpw.mods.fml.common.network.PacketDispatcher;
import extracells.gui.GuiTerminalFluid;
import extracells.network.packet.PacketTerminalFluid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class WidgetFluidRequest extends AbstractFluidWidget
{

	public WidgetFluidRequest(GuiTerminalFluid guiTerminalFluid, Fluid fluid)
	{
		super(guiTerminalFluid, 18, 18, fluid);
	}

	@Override
	public void drawWidget(int posX, int posY)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		if (fluid != null && fluid.getIcon() != null)
		{
			drawTexturedModelRectFromIcon(posX + 1, posY + 1, fluid.getIcon(), sizeX - 2, sizeY - 2);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			String str = StatCollector.translateToLocal("AppEng.Terminal.Craft");
			str = WordUtils.capitalize(str.toLowerCase());
			Minecraft.getMinecraft().fontRenderer.drawString(EnumChatFormatting.WHITE + str, 52 + posX - str.length(), posY + 24, 0);
		}
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	@Override
	public void mouseClicked(int posX, int posY, int mouseX, int mouseY)
	{
		TileEntity tileEntity = guiTerminalFluid.tileEntity;
		PacketDispatcher.sendPacketToServer(new PacketTerminalFluid(tileEntity.worldObj, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, new FluidStack(fluid, 1000)).makePacket());
	}

	@Override
	public void drawTooltip(int posX, int posY, int mouseX, int mouseY)
	{
		if (fluid != null && isPointInRegion(posX, posY, sizeX, sizeY, mouseX, mouseY))
		{
			if (fluid != null)
			{
				List<String> description = new ArrayList<String>();
				description.add(StatCollector.translateToLocal("AppEng.GuiITooltip.Craftable"));
				description.add(fluid.getLocalizedName());
				drawHoveringText(description, mouseX - guiTerminalFluid.guiLeft(), mouseY - guiTerminalFluid.guiTop(), Minecraft.getMinecraft().fontRenderer);
			}
		}
	}
}
