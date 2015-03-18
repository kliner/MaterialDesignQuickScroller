package com.kliner.mdquickscroller.widget;

/**
 * Interface required for FastTrack.
 * 
 * @author andraskindler
 *
 */
public interface Scrollable {

	/**
	 * This function returns the corresponding String to display at any given position
	 * <p>
	 * 
	 * @param childPosition
	 *            equals childPosition if used with ExpandableListView, position otherwise.
	 * @param groupPosition
	 *            equals groupPosition if used with ExpandableListView, zero otherwise.
	 */
	String getIndicatorForPosition(final int childPosition, final int groupPosition);

	/**
	 * This second function is responsible for is for implementing scroll behaviour. This can be used to perform special tasks, e.g. if you want to snap to the first item starting with a letter in an alphabetically ordered list or jump between groups in an ExpandableListView. If you want the normal approach, simply return childposition.
	 * <p>
	 * 
	 * @param childPosition
	 *            equals childPosition if used with ExpandableListView, position otherwise.
	 * @param groupPosition
	 *            equals groupPosition if used with ExpandableListView, zero otherwise.
	 */
	int getScrollPosition(final int childPosition, final int groupPosition);

}
