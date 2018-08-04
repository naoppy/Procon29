import java.awt.*;

/**
 * プレイヤーを表すクラス
 */
public class Person {
    private Point nowPoint;

    private boolean isRedTeamMember;

    private boolean isFinishNextSelect = false;

    private Point applyPoint;

    private Selection selection;

    public Person(boolean isRedTeamMember) {
        this.isRedTeamMember = isRedTeamMember;
    }

    public boolean isRedTeamMember() {
        return isRedTeamMember;
    }

    public boolean isFinishNextSelect() {
        return isFinishNextSelect;
    }

    public boolean select(Selection selection, Point applyPoint) {
        switch (selection) {
            case WAIT:
                this.isFinishNextSelect = true;
                this.selection = selection;
                this.applyPoint = nowPoint;
                return true;
            case MOVE:
                break;
            case REMOVE:
                break;
        }
        return false;
    }
}
