/*
 * This file is part of the Illarion Mapeditor.
 *
 * Copyright © 2012 - Illarion e.V.
 *
 * The Illarion Mapeditor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Illarion Mapeditor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Illarion Mapeditor.  If not, see <http://www.gnu.org/licenses/>.
 */
package illarion.mapedit.gui;

import illarion.mapedit.Lang;
import illarion.mapedit.events.map.RepaintRequestEvent;
import illarion.mapedit.render.*;
import org.bushe.swing.event.EventBus;
import org.pushingpixels.flamingo.api.common.JCommandToggleButton;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.RibbonBandResizePolicy;

import javax.annotation.Nonnull;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * @author Tim
 */
public class ViewBand extends JRibbonBand {

    public ViewBand(@Nonnull final RendererManager manager) {
        super(Lang.getMsg("gui.viewband.Name"), null);

        final TileRenderer tileRenderer = new TileRenderer(manager);
        final ItemRenderer itemRenderer = new ItemRenderer(manager);
        final GridRenderer gridRenderer = new GridRenderer(manager);
        final MusicRenderer musicRenderer = new MusicRenderer(manager);
        final WarpRenderer warpRenderer = new WarpRenderer(manager);

        newRenderButton(manager, tileRenderer);
        newRenderButton(manager, itemRenderer);
        renderEmptyTilesButton(tileRenderer);
        newRenderButton(manager, gridRenderer);
        newRenderButton(manager, musicRenderer);
        newRenderButton(manager, warpRenderer);

        setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(
                new CoreRibbonResizePolicies.Mirror(getControlPanel()),
                new CoreRibbonResizePolicies.Mid2Low(getControlPanel()),
                new CoreRibbonResizePolicies.High2Low(getControlPanel())));
    }

    private void newRenderButton(@Nonnull final RendererManager manager, @Nonnull final AbstractMapRenderer renderer) {
        final JCommandToggleButton btn = new JCommandToggleButton(
                renderer.getLocalizedName(), renderer.getRendererIcon()
        );
        btn.getActionModel().setSelected(renderer.isDefaultOn());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (btn.getActionModel().isSelected()) {
                    manager.addRenderer(renderer);
                } else {
                    manager.removeRenderer(renderer);
                }
            }
        });
        if (renderer.isDefaultOn()) {
            manager.addRenderer(renderer);
        }
        addCommandButton(btn, renderer.getPriority());
    }

    private void renderEmptyTilesButton(@Nonnull final TileRenderer renderer)   {
        final JCommandToggleButton btn = new JCommandToggleButton(
                renderer.getEmptyTileLocalizedName(), renderer.getEmptyTileRendererIcon()
        );
        btn.getActionModel().setSelected(renderer.isEmptyTileDefaultOn());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                renderer.setRenderEmptyTiles(btn.getActionModel().isSelected());
                EventBus.publish(new RepaintRequestEvent());
            }
        });
        if (renderer.isEmptyTileDefaultOn()) {
            renderer.setRenderEmptyTiles(true);
        }
        addCommandButton(btn, renderer.getEmptyTilePriority());
    }
}