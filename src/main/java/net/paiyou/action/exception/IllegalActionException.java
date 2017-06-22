package net.paiyou.action.exception;

import net.paiyou.action.Action;
import net.paiyou.entity.enums.PlayerLocation;
import net.paiyou.game.GameContext;

/**
 * 尝试执行非法动作时抛出此异常。
 *
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class IllegalActionException extends Exception {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    private Action action;

    public IllegalActionException(GameContext context, PlayerLocation location, Action action) {
        super(location + action.toString() + " context: " + context);
        this.action = action;
    }

}
