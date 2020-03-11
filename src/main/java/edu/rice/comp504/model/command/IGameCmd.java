package edu.rice.comp504.model.command;

import edu.rice.comp504.model.gameobj.MovingObj;

import java.awt.*;

/**
 * Game command interface.
 */
public interface IGameCmd {
    void execute(MovingObj src);
}
