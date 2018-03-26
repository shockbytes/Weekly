package at.shockbytes.weekly.view;


/**
 * @author Martin Macheiner
 * Date: 09.09.2015.
 */
public interface ItemTouchHelperAdapter {

    boolean onItemMove(int from, int to);

    void onItemDismiss(int position);

}
