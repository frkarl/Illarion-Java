/*
 * This file is part of the Illarion Client.
 *
 * Copyright © 2012 - Illarion e.V.
 *
 * The Illarion Client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Illarion Client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Illarion Client.  If not, see <http://www.gnu.org/licenses/>.
 */
package illarion.client.net.server;

import java.io.IOException;

import illarion.client.net.CommandFactory;
import illarion.client.net.CommandList;
import illarion.client.net.NetCommReader;
import illarion.client.net.client.CloseDialogInputCmd;

/**
 * Servermessage: Text Request ( {@link illarion.client.net.CommandList#MSG_DIALOG_INPUT}).
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public final class DialogInputMsg
        extends AbstractReply {
    /**
     * The title that is supposed to be displayed in the dialog.
     */
    private String title;

    /**
     * The flag if the text input is supposed to be multi-lined or not.
     */
    private boolean multiLine;

    /**
     * The maximal amount of characters that are valid to be input.
     */
    private int maxCharacters;

    /**
     * The ID of this request.
     */
    private int requestId;

    /**
     * Default constructor for the effect message.
     */
    public DialogInputMsg() {
        super(CommandList.MSG_DIALOG_INPUT);
    }

    /**
     * Create a new instance of the text request message as recycle object.
     *
     * @return a new instance of this message object
     */
    @Override
    public DialogInputMsg clone() {
        return new DialogInputMsg();
    }

    /**
     * Decode the text request the receiver got and prepare it for the execution.
     *
     * @param reader the receiver that got the data from the server that needs to be decoded
     * @throws IOException thrown in case there was not enough data received to decode the full message
     */
    @Override
    public void decode(final NetCommReader reader)
            throws IOException {
        title = reader.readString();
        multiLine = (reader.readByte() != 0);
        maxCharacters = reader.readUShort();
        requestId = reader.readInt();
    }

    /**
     * Execute the text request message and send the decoded data to the rest of the client.
     *
     * @return true if the execution is done, false if it shall be called again
     */
    @Override
    public boolean executeUpdate() {
        final CloseDialogInputCmd cmd = (CloseDialogInputCmd) CommandFactory.getInstance().getCommand(CommandList







                                                                                                              .CMD_CLOSE_DIALOG_INPUT);
        cmd.setText("This client has no intension of talking with you.");
        cmd.setDialogId(requestId);
        cmd.send();

        return true;
    }

    /**
     * Clean up all references that are not needed anymore.
     */
    @Override
    public void reset() {
        title = null;
    }

    /**
     * Get the data of this text request message as string.
     *
     * @return the string that contains the values that were decoded for this message
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
        return toString("Text Request: " + requestId);
    }
}
