package com.hjc.herol.util.page;

public class PageContext extends Page {

	private static final long serialVersionUID = 1L;
	private static ThreadLocal<PageContext> context = new ThreadLocal<PageContext>();

	/**
	 * 将构造方法设置为私有
	 */
	private PageContext() {
	}

	public static PageContext getContext() {
		return getContext(null);
	}

	public static PageContext getContext(Page page) {
		PageContext ci = context.get();
		if (ci == null) {
			ci = new PageContext();
			ci.setPage(page);
			context.set(ci);
		}
		return ci;
	}

	public static void removeContext() {
		context.remove();
	}

	protected void initialize() {

	}

}
