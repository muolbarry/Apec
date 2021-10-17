package Apec.Components.Gui.GuiIngame.GuiElements;

import Apec.ApecMain;
import Apec.Events.ApecSettingChangedState;
import Apec.Utils.ApecUtils;
import Apec.Components.Gui.GuiIngame.GUIComponent;
import Apec.Components.Gui.GuiIngame.GUIComponentID;
import Apec.DataInterpretation.DataExtractor;
import Apec.Settings.SettingID;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.util.vector.Vector2f;

public class AirBar extends GUIComponent {

    public AirBar() {
        super(GUIComponentID.AIR_BAR);
    }

    @Override
    public void init() {
        super.init();
        this.editable = ApecMain.Instance.settingsManager.getSettingState(SettingID.SHOW_AIR_BAR);
    }

    @Override
    public void drawTex(DataExtractor.PlayerStats ps, DataExtractor.ScoreBoardData sd, DataExtractor.OtherData od, DataExtractor.TabStats ts, ScaledResolution sr,boolean editingMode) {

        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);

        GuiIngame gi = mc.ingameGUI;
        Vector2f StatBar = ApecUtils.scalarMultiply(this.getCurrentAnchorPoint(), oneOverScale);

        mc.renderEngine.bindTexture(new ResourceLocation(ApecMain.modId, "gui/statBars.png"));

        if (mc.thePlayer.isInsideOfMaterial(Material.water) || editingMode) {

            float airPrec = mc.thePlayer.getAir() * 0.00333f;
            if (airPrec < 0) airPrec = 0;

            gi.drawTexturedModalRect((int) StatBar.x, (int) StatBar.y, 0, 40, 182, 5);
            gi.drawTexturedModalRect((int) StatBar.x, (int) StatBar.y, 0, 45, (int) (182f * airPrec), 5);

        }


        GlStateManager.popMatrix();
    }

    @SubscribeEvent
    public void onSettingChanged(ApecSettingChangedState event) {
        if (event.settingID == SettingID.SHOW_AIR_BAR) {
            this.enabled = event.state;
        }
    }

    @Override
    public Vector2f getAnchorPointPosition() {
        return this.guiModifier.applyGlobalChanges(this,new Vector2f(g_sr.getScaledWidth() - 190, 72));
    }

    @Override
    public Vector2f getBoundingPoint() {
        return new Vector2f(182*scale,5*scale);
    }

}
